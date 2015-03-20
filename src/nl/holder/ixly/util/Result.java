package nl.holder.ixly.util;

import java.util.ArrayList;
import java.util.List;

public class Result 
{
	private boolean m_isValid;
	private List<Throwable> m_exceptionList;
	
	public Result(boolean isValid)
	{
		m_isValid = isValid;
		m_exceptionList = new ArrayList<Throwable>();
	}
	
	public boolean isTrue()
	{
		return m_isValid;
	}
	
	public void addException(Throwable exception)
	{
		m_exceptionList.add(exception);
	}
	
}
