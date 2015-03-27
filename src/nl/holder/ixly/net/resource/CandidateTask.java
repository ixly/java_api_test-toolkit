package nl.holder.ixly.net.resource;

import org.json.JSONObject;

import nl.holder.ixly.net.security.Credentials;

public class CandidateTask 
{
	private final Credentials m_credentials;
	private final JSONObject m_jsonObject;
	
	public CandidateTask(Credentials credentials, JSONObject jsonObject)
	{
		m_credentials = credentials;
		m_jsonObject = jsonObject;
	}
	
	public Credentials getCredentials()
	{
		return m_credentials;
	}
	
	public String getTaskName()
	{
		return getStringValue("task_name");
	}
	
	public String getTaskKey()
	{
		return getStringValue("task_key");
	}
	
	public String getUrlBackToPortal()
	{
		return getStringValue("candidate_task_url_back_to_portal");
	}
	
	public String getUrlStateCallback()
	{
		return getStringValue("candidate_task_url_state_callback");
	}
	
	public String getState()
	{
		return getStringValue("state");
	}
	
	public String getUrlFinished()
	{
		return getStringValue("candidate_task_url_finished");
	}
	
	public String getApiId()
	{
		return getStringValue("api_candidate_identifier");
	}
	
	public String getApiCandidateTaskId()
	{
		return getStringValue("api_candidate_task_identifier");
	}
	
	public String getUrl()
	{
		return getStringValue("url");
	}
	
	public String getCompletedDate()
	{
		return getStringValue("completed_date");
	}
	
	private String getStringValue(String key)
	{
		return m_jsonObject.isNull(key) ? "" : m_jsonObject.getString(key);
	}
}
