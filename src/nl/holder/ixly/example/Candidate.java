package nl.holder.ixly.example;

import nl.holder.ixly.net.security.Credentials;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class Candidate 
{
	public enum Params
	{
		ID("candidate"),
		LAST_NAME("candidate_name_last"),
		LANGUAGE("candidate_language"),
		FIRST_NAME("candidate_name_first"),
		USER("user"),
		API_ID("api_candidate_identifier"),
		NAME_INSERTION("candidate_name_insertion"),
		TEST_SITUATION("candidate_test_situation"),
		URL("url");
		
		final String mi_keyName;
		
		private Params(String keyName)
		{
			mi_keyName = keyName;
		}
		
		public String getKeyName()
		{
			return mi_keyName;
		}
	}
	
	private final HttpResponse<JsonNode> m_response;
	private final Credentials m_credentials;
	
	protected Candidate(HttpResponse<JsonNode> response, Credentials credentials)
	{
		m_response = response;
		m_credentials = new Credentials(credentials.getUsername(), credentials.getPassword(), getApiId(), null);
	}

	public int getId()
	{
		return getIntValue(Params.ID);
	}
	
	public String getLastName()
	{
		return getStringValue(Params.LAST_NAME);
	}
	
	public String getLanguage()
	{
		return getStringValue(Params.LANGUAGE);
	}
	
	public String getFirstName()
	{
		return getStringValue(Params.FIRST_NAME);
	}
	
	public String getUser()
	{
		return getStringValue(Params.USER);
	}
	
	public String getApiId()
	{
		return getStringValue(Params.API_ID);
	}
	
	public String getNameInsertion()
	{
		return getStringValue(Params.NAME_INSERTION);
	}
	
	public String getTestSituation()
	{
		return getStringValue(Params.TEST_SITUATION);
	}
	
	public String getUrl()
	{
		return getStringValue(Params.URL);
	}
	
	public Credentials getCredentials()
	{
		return m_credentials;
	}
	
	private int getIntValue(Params paramKey)
	{
		return Integer.valueOf(getStringValue(paramKey));
	}
	
	private String getStringValue(Params paramKey)
	{
		final JsonNode node;
		final JSONObject obj;
		final Object value;
		
		if(m_response == null)
		{
			return "";
		}
		
		node = m_response.getBody();
		
		if(node == null)
		{
			return "";
		}
		
		obj = node.getObject();
		
		if(obj == null)
		{
			return "";
		}
		
		value = obj.get(paramKey.getKeyName());
		
		if(value == null)
		{
			return "";
		}
		
		return String.valueOf(value);
	}
	
	@Override
	public String toString()
	{
		final JsonNode jsonNode;
		final String noData;
		
		noData = "No data available";
		
		if(m_response == null)
		{
			return noData;
		}
		
		jsonNode = m_response.getBody();
		
		if(jsonNode == null)
		{
			return noData;
		}
		
		return jsonNode.toString();
	}	
}
