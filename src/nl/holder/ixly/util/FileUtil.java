package nl.holder.ixly.util;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil 
{
	public enum FileExtension
	{
		JSON, PDF, HTML;
		
		private FileExtension()
		{
		}
		
		public String getExtension()
		{
			return "." + this.toString().toLowerCase();
		}
	}
	
	public static void writeTo(File file, InputStream is)
	{
	    DataOutputStream out;
		try {
			out = new DataOutputStream(new  BufferedOutputStream(new FileOutputStream(file)));
		  int c;
		    while((c = is.read()) != -1) {
		        out.writeByte(c);
		    }
		    out.close();
		    is.close();
		} 
		catch (FileNotFoundException e) { e.printStackTrace(); } 
		catch (IOException e) { e.printStackTrace(); } 
		catch(Exception e) { e.printStackTrace(); }
	}
}
