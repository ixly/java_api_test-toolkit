package nl.holder.ixly.net;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class QueryString 
{
	private final Map<String, String> m_map;
	
	public QueryString()
	{
		m_map = new HashMap<String, String>();
	}
	
	public void add(String key, String value)
	{
		m_map.put(key, value);
	}
	
	public boolean isEmpty()
	{
		return m_map.isEmpty();
	}
	
	public Set<Map.Entry<String,String>>  getMap()
	{
		return m_map.entrySet();
	}
}
