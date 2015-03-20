package nl.holder.ixly.net.security;

public class Credentials 
{
	private final String m_basicAuthUsername;
	private final String m_basicAuthPassword;
	private final String m_apiCandidateIdentifier;
	private final String m_apiCandidateTaskIdentifier;
	
	public Credentials(String basicAuthUsername, String basicAuthPassword, String apiCandidateIdentifier, String apiCandidateTaskIdentifier)
	{
		m_basicAuthUsername = basicAuthUsername;
		m_basicAuthPassword = basicAuthPassword;
		m_apiCandidateIdentifier = apiCandidateIdentifier;
		m_apiCandidateTaskIdentifier = apiCandidateTaskIdentifier;
	}
	
	public Credentials(String basicAuthUsername, String basicAuthPassword)
	{
		this(basicAuthUsername, basicAuthPassword, null, null);
	}
	
	public String getUsername()
	{
		return m_basicAuthUsername;
	}
	
	public String getPassword()
	{
		return m_basicAuthPassword;
	}
	
	public String getApiCandidateIdentifier()
	{
		return m_apiCandidateIdentifier;
	}
	
	public String getApiCandidateTaskIdentifier()
	{
		return m_apiCandidateTaskIdentifier;
	}
}
