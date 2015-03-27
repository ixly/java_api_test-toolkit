package nl.holder.ixly.net.resource;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Task 
{
	private final JSONObject m_jsonObject;
	
	public Task(JSONObject jsonObject)
	{
		m_jsonObject = jsonObject;
	}
	
	protected String getName()
	{
		return m_jsonObject.getString("name");
	}
	
	public String getTaskKey()
	{
		return m_jsonObject.getString("task_key");
	}
	
	protected int getCredits()
	{
		return m_jsonObject.getInt("credits");
	}
	
	protected List<Report> getReportList()
	{
		final List<Report> reportList;
		final JSONArray jsonArray;
		
		jsonArray = m_jsonObject.getJSONArray("reports");
		reportList = new ArrayList<Report>();
		
		for(int i=0; i < jsonArray.length(); i++)
		{
			reportList.add(new Report(jsonArray.getJSONObject(i)));
		}
				
		return reportList;
	}
}
