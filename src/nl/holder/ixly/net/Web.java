package nl.holder.ixly.net;

import nl.holder.ixly.net.security.Credentials; 

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Web implements WebIF
{
	private Credentials m_credentials;
	public static String BASE_URL = "https://l.test-toolkit.nl/api";
	
	public Web(Credentials credentials) throws UnirestException
	{
		m_credentials = credentials;
		checkCredentials();
	}
	
	public Web()
	{
	}
	
	@Override
	public HttpResponse<JsonNode> get(String url)
	{
		return get(url, null, null);
	}
	
	@Override
	public HttpResponse<JsonNode> get(String url, RouteParams routeParams)
	{
		return get(url, routeParams, null);
	}
	
	@Override
	public HttpResponse<JsonNode> get(String url, QueryString queryString)
	{
		return get(url, null, queryString);
	}
	
	@Override
	public HttpResponse<JsonNode> get(String url, RouteParams routeParams, QueryString queryString)
	{
		return null;
	}
	
	private void checkCredentials() throws UnirestException
	{
		Unirest.get("https://l.test-toolkit.nl/api/check_auth").basicAuth(m_credentials.getUsername(), m_credentials.getPassword()).asJson();
	}
}
