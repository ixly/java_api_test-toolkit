package nl.holder.ixly.net;

import com.mashape.unirest.http.exceptions.UnirestException;

import nl.holder.ixly.net.security.Credentials;

public class WebFactory 
{
	public WebIF createWebInstance(Protocol protocol, Credentials credentials) throws UnirestException
	{
		if(protocol.equals(Protocol.HTTPS))
		{
			return new SecureWeb(credentials);
		}
		
		return new Web(credentials);
	}
}
