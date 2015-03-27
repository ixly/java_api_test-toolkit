package nl.holder.ixly.example;

import java.io.File;  

import nl.holder.ixly.file.ReportIF;
import nl.holder.ixly.util.FileUtil;
import nl.holder.ixly.util.FileUtil.FileExtension;

import com.mashape.unirest.http.HttpResponse;

public class HtmlReport 
	implements ReportIF
{
	private final HttpResponse<String> m_response;
	
	public HtmlReport(HttpResponse<String> response)
	{
		m_response = response;
	}
	
	@Override
	public void saveTo(File destination)
	{
		FileUtil.writeTo(new File(destination.getAbsolutePath() + getExtension()), m_response.getRawBody());
	}

	@Override
	public String getExtension() {
		return FileExtension.HTML.getExtension();
	}
}
