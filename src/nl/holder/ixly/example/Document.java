package nl.holder.ixly.example;

import nl.holder.ixly.net.Resource; 

public class Document
{
	public enum DocumentFormat
	{
		JSON("json"), 
		HTML("html"), 
		PDF("pdf");
		
		private String mi_name;
		
		private DocumentFormat(String name)
		{
			mi_name = name;
		}
		
		private String getName()
		{
			return mi_name;
		}
		
		private String getExtension()
		{
			return "." + getName();
		}
	}
	
	public Document()
	{
	}
	
	public Resource create()
	{
		return null;
	}
	
	public Resource lookup()
	{
		return null;
	}
	
	public Resource update()
	{
		return null;
	}
	
	public static void main(String args[])
	{
		new Document().create();
	}
}
