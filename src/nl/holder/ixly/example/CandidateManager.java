package nl.holder.ixly.example;

import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

import nl.holder.ixly.net.QueryString;
import nl.holder.ixly.net.Web;
import nl.holder.ixly.net.resource.Candidate;
import nl.holder.ixly.net.security.Credentials;

public final class CandidateManager 
{
	private CandidateManager()
	{
	}

	public static Candidate create(String basicAuthUsername, String basicAuthPassword)
	{
		return create(new Credentials(basicAuthUsername, basicAuthPassword));
	}
	
	public static Candidate create(Credentials credentials)
	{
		return create(credentials, null);
	}
	
	public static Candidate create(Credentials credentials, QueryString queryString)
	{
		final HttpResponse<JsonNode> response;
		
		response = doCreateUpdateOrLookup(credentials, queryString);
		
		return new Candidate(response, credentials);
	}
	
	public static Candidate update(Candidate candidate, QueryString queryString)
	{
		final HttpResponse<JsonNode> response;
		final Credentials credentials;
		
		if(queryString == null || queryString.isEmpty())
		{
			return candidate;
		}
		
		credentials = candidate.getCredentials();
		response = doCreateUpdateOrLookup(credentials, queryString);
		
		return new Candidate(response, credentials);
	}
	
	public static boolean destroy(Candidate candidate)
	{
		final HttpResponse<JsonNode> response;
		
		response = doDestroy(candidate);
		
		return response.getStatus() != 404;
	}
	
	private static HttpResponse<JsonNode> doDestroy(Candidate candidate)
	{
		final String destroyUrl;
		final Credentials credentials;
		
		HttpResponse<JsonNode> response;
		credentials = candidate.getCredentials();
		
		destroyUrl = String.format("%s/candidates/%s/destroy", Web.BASE_URL, candidate.getApiId());
		
		try 
		{
			final GetRequest getRequest;
			
			getRequest = Unirest.get(destroyUrl);
			
			if(credentials.getUsername() != null && credentials.getPassword() != null)
			{
				getRequest.basicAuth(credentials.getUsername(), credentials.getPassword());
			}
			
			response = getRequest.asJson();
		} 
		catch (UnirestException e) 
		{
			response = null;
		}
		
		return response;
	}
	
	private static HttpResponse<JsonNode> doCreateUpdateOrLookup(Credentials credentials, QueryString queryString)
	{
		final String createUrl;
		
		HttpResponse<JsonNode> response;
		
		createUrl = String.format("%s/candidates/%s", Web.BASE_URL, getApiIdentifier(credentials));
		
		try 
		{
			final GetRequest getRequest;
			
			getRequest = Unirest.get(createUrl);
			
			if(queryString != null)
			{
				for(Map.Entry<String, String> entry : queryString.getMap())
				{
					getRequest.queryString(entry.getKey(), entry.getValue());
				}
			}
			
			if(credentials.getUsername() != null && credentials.getPassword() != null)
			{
				getRequest.basicAuth(credentials.getUsername(), credentials.getPassword());
			}
			
			response = getRequest.asJson();
		} 
		catch (UnirestException e) 
		{
			response = null;
		}
		
		return response;
	}
	
	public static Candidate lookup(Credentials credentials)
	{
		if(credentials.getApiCandidateIdentifier() != null && !credentials.getApiCandidateIdentifier().isEmpty())
		{
			return null;
		}
		
		return new Candidate(doCreateUpdateOrLookup(credentials, null), credentials);
	}

	public static boolean isAvailable(Credentials credentials)
	{
		final String existsUrl;
		final GetRequest getRequest;
		final HttpResponse<JsonNode> response;
		
		existsUrl = String.format("%s/candidates/%s/exists", Web.BASE_URL, getApiIdentifier(credentials));
		getRequest = Unirest.get(existsUrl);
		
		if(credentials.getUsername() != null && credentials.getPassword() != null)
		{
			getRequest.basicAuth(credentials.getUsername(), credentials.getPassword());
		}
		
		try 
		{
			response = getRequest.asJson();
			if(response.getBody().getObject().has("message"))
			{
				return false;
			}
		} 
		catch (UnirestException e) 
		{
			return false;
		}
		
		return true;
	}
	
	private static String getApiIdentifier(Credentials credentials)
	{
		final String identifier;
		
		if(credentials == null)
		{
			return generatedIdentifier();
		}
		
		identifier = credentials.getApiCandidateIdentifier();
		
		if(identifier == null || identifier.equals(""))
		{
			return generatedIdentifier();
		}
		
		return identifier;
	}
	
	private static String generatedIdentifier()
	{
		final String currentTimeString;
		
		currentTimeString = String.valueOf(System.currentTimeMillis());
		return DigestUtils.md5Hex(currentTimeString);
	}
	
	
	public static void main(String args[])
	{
		final QueryString queryString;
		final Candidate candidateByCreate;
		final Candidate candidateByUpdate;
		final Candidate candidateByLookup;
		final Credentials credentials;
		final boolean isNotAvailable;
		final boolean isAvailable;
		
		boolean isDestroyed;
		
		final String basicAuthUsername;
		final String basicAuthPassword;
		
		// Create a candidate
		basicAuthUsername = "jeroen+apitk@holder.nl";
		basicAuthPassword = "19b51857421a37c58cfd3aca7a5123a4";
		
		credentials = new Credentials(basicAuthUsername, basicAuthPassword);
		candidateByCreate = CandidateManager.create(credentials);
		
		// Update an attribute
		queryString = new QueryString();
		
		queryString.add(Candidate.Params.FIRST_NAME.getKeyName(), "bryan");
		queryString.add(Candidate.Params.LAST_NAME.getKeyName(), "oemar");
		
		candidateByUpdate = CandidateManager.update(candidateByCreate, queryString);
		
		// Lookup a candidate
		candidateByLookup = CandidateManager.lookup(credentials);
		
		// See if a candidate exists
		isNotAvailable = !CandidateManager.isAvailable(credentials);
		isAvailable = CandidateManager.isAvailable(candidateByCreate.getCredentials());
		
		// Destroy a candidate
		// Will be destroyed 
		isDestroyed = CandidateManager.destroy(candidateByCreate);
		
		// Is already destroyed, so will return false
		isDestroyed = CandidateManager.destroy(candidateByUpdate);

		// Will be destroyed
		isDestroyed = CandidateManager.destroy(candidateByLookup);
	}
}
