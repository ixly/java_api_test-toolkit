package nl.holder.ixly.net;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import nl.holder.ixly.net.security.Credentials;

public class SecureWeb implements WebIF
{
	private Credentials m_credentials;
	
	public SecureWeb(Credentials credentials)
	{
		m_credentials = credentials;
	}

	@Override
	public HttpResponse<JsonNode> get(String url) {
		return null;
	}

	@Override
	public HttpResponse<JsonNode> get(String url, RouteParams routeParams) {
		return null;
	}

	@Override
	public HttpResponse<JsonNode> get(String url, QueryString queryString) {
		return null;
	}

	@Override
	public HttpResponse<JsonNode> get(String url, RouteParams routeParams,
			QueryString queryString) {
		return null;
	}
}
