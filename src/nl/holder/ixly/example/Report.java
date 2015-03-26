package nl.holder.ixly.example;

import org.json.JSONObject;

public class Report
{
	private final JSONObject mi_jsonObject;
	
	protected Report(JSONObject jsonObject)
	{
		mi_jsonObject = jsonObject;
	}
	
	public boolean isDefault()
	{
		return mi_jsonObject.getBoolean("default");
	}
	
	public int getCredits()
	{
		return mi_jsonObject.getInt("credits");
	}
	
	public String getReportKey()
	{
		return mi_jsonObject.getString("report_key");
	}
}