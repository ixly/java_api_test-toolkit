package nl.holder.ixly.example;

import java.io.InputStream; 
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

import nl.holder.ixly.file.ReportIF;
import nl.holder.ixly.net.QueryString;
import nl.holder.ixly.net.Web;
import nl.holder.ixly.net.resource.Candidate;
import nl.holder.ixly.net.resource.CandidateTask;
import nl.holder.ixly.net.resource.Task;
import nl.holder.ixly.net.security.Credentials;
import nl.holder.ixly.util.FileUtil.FileExtension;

public class CandidateTaskManager 
{
	private CandidateTaskManager()
	{
	}
	
	public static CandidateTask create(Candidate candidate)
	{
		return null;
	}
	
	public static ReportIF defaultReport(CandidateTask candidateTask, FileExtension format)
	{
		try 
		{
			final String candidateTaskWithdrawUrl;
			final GetRequest getRequest;
			final HttpResponse<InputStream> response;
			
			final String apiCandidateIdentifier;
			final String apiCandidateTaskKey;
			
			final Credentials credentials;
			
			apiCandidateIdentifier = candidateTask.getApiId();
			apiCandidateTaskKey = candidateTask.getApiCandidateTaskId();
			
			candidateTaskWithdrawUrl = String.format("%s/candidates/%s/candidate_tasks/%s/default_report%s", Web.BASE_URL, apiCandidateIdentifier, apiCandidateTaskKey, format.getExtension());
			getRequest = Unirest.get(candidateTaskWithdrawUrl);
			
			credentials = candidateTask.getCredentials();
			
			if(credentials.getUsername() != null && credentials.getPassword() != null)
			{
				getRequest.basicAuth(credentials.getUsername(), credentials.getPassword());
			}

			response = getRequest.asBinary();
			
			if(response == null)
			{
				return null;
			}
			
			return convertToReport(response, format);
		} 
		catch (UnirestException e) 
		{
			return null;
		}
	}
	
	private static ReportIF convertToReport(HttpResponse response, FileExtension format)
	{
		if(format == FileExtension.HTML)
		{
			return new HtmlReport(response);
		}
		
		if(format == FileExtension.JSON)
		{
			return new JsonReport(response);
		}
		
		if(format == FileExtension.PDF)
		{
			return new PdfReport(response);
		}
		
		return null;
	}
	
	public static boolean destroy(CandidateTask candidateTask)
	{
		return withdraw(candidateTask);
	}
	
	public static boolean withdraw(CandidateTask candidateTask)
	{
		final String candidateTaskWithdrawUrl;
		final GetRequest getRequest;
		final HttpResponse<JsonNode> response;
		
		final String apiCandidateIdentifier;
		final String apiCandidateTaskKey;
		
		final Credentials credentials;
		
		apiCandidateIdentifier = candidateTask.getApiId();
		apiCandidateTaskKey = candidateTask.getApiCandidateTaskId();
		
		candidateTaskWithdrawUrl = String.format("%s/candidates/%s/candidate_tasks/%s/withdraw", Web.BASE_URL, apiCandidateIdentifier, apiCandidateTaskKey);
		getRequest = Unirest.get(candidateTaskWithdrawUrl);
		
		credentials = candidateTask.getCredentials();
		
		if(credentials.getUsername() != null && credentials.getPassword() != null)
		{
			getRequest.basicAuth(credentials.getUsername(), credentials.getPassword());
		}
		
		try 
		{
			final JSONObject jsonObject;
				
			response = getRequest.asJson();
			jsonObject = response.getBody().getObject();
			
			if(jsonObject == null || jsonObject.has("message"))
			{
				return true;
			}
		} 
		catch (UnirestException e) 
		{
			return false;
		}
		
		return false;
	}
	
	public static void destroy(Candidate candidate)
	{
		CandidateManager.destroy(candidate);	
	}
	
	public static CandidateTask status(CandidateTask candidateTask)
	{
		final String candidateTaskStatusUrl;
		final GetRequest getRequest;
		final HttpResponse<JsonNode> response;
		
		final String apiCandidateIdentifier;
		final String apiCandidateTaskKey;
		
		final Credentials credentials;
		
		apiCandidateIdentifier = candidateTask.getApiId();
		apiCandidateTaskKey = candidateTask.getApiCandidateTaskId();
		
		candidateTaskStatusUrl = String.format("%s/candidates/%s/candidate_tasks/%s/status", Web.BASE_URL, apiCandidateIdentifier, apiCandidateTaskKey);
		getRequest = Unirest.get(candidateTaskStatusUrl);
		
		credentials = candidateTask.getCredentials();
		
		if(credentials.getUsername() != null && credentials.getPassword() != null)
		{
			getRequest.basicAuth(credentials.getUsername(), credentials.getPassword());
		}
		
		try 
		{
			final JSONObject jsonObject;
				
			response = getRequest.asJson();
			jsonObject = response.getBody().getObject();
			
			if(jsonObject == null || jsonObject.has("message"))
			{
				return null;
			}
		} 
		catch (UnirestException e) 
		{
			return null;
		}
		
		return convertToCandidateTask(credentials, response);
	}
	
	public static CandidateTask create(Credentials credentials, Task task)
	{
		final String taskListUrl;
		final GetRequest getRequest;
		final HttpResponse<JsonNode> response;
		
		final String apiCandidateIdentifier;
		final String apiCandidateTaskId;
		
		final QueryString queryString;

		apiCandidateIdentifier = getApiIdentifier(credentials);
		apiCandidateTaskId = getApiIdentifier(credentials);
		
		taskListUrl = String.format("%s/candidates/%s/candidate_tasks/%s", Web.BASE_URL, apiCandidateIdentifier, apiCandidateTaskId);
		getRequest = Unirest.get(taskListUrl);
		
		if(credentials.getUsername() != null && credentials.getPassword() != null)
		{
			getRequest.basicAuth(credentials.getUsername(), credentials.getPassword());
		}
		
		queryString = new QueryString();
		queryString.add("task_key", task.getTaskKey());
		
		if(queryString != null)
		{
			for(Map.Entry<String, String> entry : queryString.getMap())
			{
				getRequest.queryString(entry.getKey(), entry.getValue());
			}
		}
		
		try 
		{
			final JSONObject jsonObject;
				
			response = getRequest.asJson();
			jsonObject = response.getBody().getObject();
			
			if(jsonObject == null || jsonObject.has("message"))
			{
				return null;
			}
		} 
		catch (UnirestException e) 
		{
			return null;
		}
		
		return convertToCandidateTask(credentials, response);
	}
	
	private static CandidateTask convertToCandidateTask(Credentials credentials, HttpResponse<JsonNode> response)
	{
		final JsonNode node;
		final JSONObject obj;
		
		node = response.getBody();
		obj = node.getObject();
		
		return new CandidateTask(credentials, obj);
	}
	
	public static List<Task> list(Credentials credentials)
	{
		final String taskListUrl;
		final GetRequest getRequest;
		final List<Task> emptyList;
		final HttpResponse<JsonNode> response;
		final JSONArray jsonArray;
		
		taskListUrl = String.format("%s/available_tasks/", Web.BASE_URL);
		getRequest = Unirest.get(taskListUrl);
		emptyList = new ArrayList<Task>();
		
		if(credentials.getUsername() != null && credentials.getPassword() != null)
		{
			getRequest.basicAuth(credentials.getUsername(), credentials.getPassword());
		}
		
		try 
		{
			response = getRequest.asJson();
			jsonArray = response.getBody().getArray();
			
			if(jsonArray == null)
			{
				return emptyList;
			}
		} 
		catch (UnirestException e) 
		{
			return emptyList;
		}
		
		return convertToTaskList(jsonArray);
	}
	
	private static List<Task> convertToTaskList(JSONArray jsonArray)
	{
		final List<Task> taskList;
		
		taskList = new ArrayList<Task>();
		
		for(int i=0; i<jsonArray.length(); i++)
		{
			final JSONObject jsonObject;
			final Task task;
			
			jsonObject = jsonArray.getJSONObject(i);
			task = new Task(jsonObject);
		
			taskList.add(task);
		}
			
		return taskList;
	}
	
	public static List<CandidateTask> list(Candidate candidate)
	{
		final String taskListUrl;
		final GetRequest getRequest;
		final HttpResponse<JsonNode> response;
		final Credentials credentials;
		final List<CandidateTask> emptyList;
		
		taskListUrl = String.format("%s/candidates/%s/candidate_tasks/", Web.BASE_URL, candidate.getApiId());
		getRequest = Unirest.get(taskListUrl);
		credentials = candidate.getCredentials();
		emptyList = new ArrayList<CandidateTask>();
		
		if(credentials.getUsername() != null && credentials.getPassword() != null)
		{
			getRequest.basicAuth(credentials.getUsername(), credentials.getPassword());
		}
		
		try 
		{
			final JSONObject jsonObject;
				
			response = getRequest.asJson();
			jsonObject = response.getBody().getObject();
			
			if(jsonObject == null || jsonObject.has("message"))
			{
				return emptyList;
			}
		} 
		catch (UnirestException e) 
		{
			return emptyList;
		}
		
		return convertToCandidateList(response);
	}
	
	private static List<CandidateTask> convertToCandidateList(HttpResponse<JsonNode> candidateTaskListResponse)
	{
		return new ArrayList<CandidateTask>();
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
		final String basicAuthUsername;
		final String basicAuthPassword;
		final Credentials credentials;
		final Candidate candidate;
		final CandidateTask newCandidateTask;
		final CandidateTask statusCheckCandidateTask;
		final List<CandidateTask> candidateTaskList;
		final List<Task> allAvailableTaskList;
		final Task task;
		ReportIF report;
		
		// Credentials
		basicAuthUsername = "jeroen+apitk@holder.nl";
		basicAuthPassword = "19b51857421a37c58cfd3aca7a5123a4";
		
		credentials = new Credentials(basicAuthUsername, basicAuthPassword);
		
		// List all available candidate tasks
		allAvailableTaskList = CandidateTaskManager.list(credentials);
		
		// List available candidate tasks scoped on candidate		
		candidate = CandidateManager.create(credentials);
		candidateTaskList = CandidateTaskManager.list(candidate);
		
		task = allAvailableTaskList.get(0);
		
		// Create a new candidate task
		newCandidateTask = CandidateTaskManager.create(credentials, task);
		
		// Possible solution:
		// Do the test at.. newCandidateTask.getUrl();
		// Check the status of a candidateTask
		 statusCheckCandidateTask = CandidateTaskManager.status(newCandidateTask);
		// Check for the state and poll every 10 sec or so
		
	    // Get default report
	    report = CandidateTaskManager.defaultReport(statusCheckCandidateTask, FileExtension.JSON);
	    report = CandidateTaskManager.defaultReport(statusCheckCandidateTask, FileExtension.HTML);
	    report = CandidateTaskManager.defaultReport(statusCheckCandidateTask, FileExtension.PDF);
	}
}
