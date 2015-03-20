package nl.holder.ixly.net;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public interface WebIF 
{
	public HttpResponse<JsonNode> get(String url);
	public HttpResponse<JsonNode> get(String url, RouteParams routeParams);
	public HttpResponse<JsonNode> get(String url, QueryString queryString);
	public HttpResponse<JsonNode> get(String url, RouteParams routeParams, QueryString queryString);
}
