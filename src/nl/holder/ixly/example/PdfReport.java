package nl.holder.ixly.example;

import java.io.File; 
import java.io.InputStream;

import com.mashape.unirest.http.HttpResponse;

import nl.holder.ixly.file.ReportIF;
import nl.holder.ixly.util.FileUtil;
import nl.holder.ixly.util.FileUtil.FileExtension;

public class PdfReport 
	implements ReportIF
{
	private final HttpResponse<InputStream> m_response;
	
	public PdfReport(HttpResponse<InputStream> response)
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
		return FileExtension.PDF.getExtension();
	}

}
