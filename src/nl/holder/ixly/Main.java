package nl.holder.ixly;


import java.io.UnsupportedEncodingException; 

import nl.holder.ixly.example.Candidate;
import nl.holder.ixly.example.CandidateManager;
import nl.holder.ixly.net.QueryString;
import nl.holder.ixly.net.security.Credentials;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Main 
{
	public static void main(String args[])
	{
		try {
			credentialsCheck();
			
			System.out.println();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static HttpResponse<JsonNode> credentialsCheck() throws UnirestException, UnsupportedEncodingException
	{
		// For an example per entity (eg. Candidate) see the main method of the corresponding manager
		
		return null;
	}
}
