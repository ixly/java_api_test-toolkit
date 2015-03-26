package nl.holder.ixly.example;

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
	
	private String getName()
	{
		return m_jsonObject.getString("name");
	}
	
	private String getTaskKey()
	{
		return m_jsonObject.getString("task_key");
	}
	
	private int getCredits()
	{
		return m_jsonObject.getInt("credits");
	}
	
	private List<Report> getReportList()
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
