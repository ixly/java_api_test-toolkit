package nl.holder.ixly.example;

import nl.holder.ixly.net.Resource; 

public class CandidateTask 
{
	private String m_apiCandidateIdentiefier;
	private String m_apiCandidateTaskIdentifier;
	
	public CandidateTask(String apiCandidateIdentiefier, String apiCandidateTaskIdentifier)
	{
		m_apiCandidateIdentiefier = apiCandidateIdentiefier;
		m_apiCandidateTaskIdentifier = apiCandidateTaskIdentifier;
	}
	
	public Resource create()
	{
		return null;
	}
	
	public Resource lookup()
	{
		return null;
	}
	
	public Resource update()
	{
		return null;
	}
	
	public Document getDefaultReport()
	{
		return null;
	}
	
	public Document getReport()
	{
		return null;
	}
	
	public static void main(String args[])
	{
	}
}
