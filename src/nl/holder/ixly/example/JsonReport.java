package nl.holder.ixly.example;

import java.io.File; 

import nl.holder.ixly.file.ReportIF;
import nl.holder.ixly.util.FileUtil;
import nl.holder.ixly.util.FileUtil.FileExtension;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

public class JsonReport
	implements ReportIF
{
	
	// {"document_options":{},"ce_norm_group_key":"select","report_content":"\n\n<h2>debug info<\/h2>\n<h3>Test information<\/h3>\n<pre>--- !ruby/hash:VarHash&#x000A;variables:&#x000A;  variable1: 6&#x000A;  variable2: 0&#x000A;  variable3: 0&#x000A;answers:&#x000A;  single_answer_question_1: option_3&#x000A;  short_open_question_7: test&#x000A;  sort_question_8:&#x000A;  - option_9&#x000A;  - option_10&#x000A;  - option_11&#x000A;  likert_question_13: 2&#x000A;document_options: {}&#x000A;task_info:&#x000A;  task_key: form_test&#x000A;  task_name: TEST Forms test (kort)&#x000A;ce_norm_group:&#x000A;  name: select&#x000A;  norm_group_type: selection&#x000A;  key: select<\/pre>\n<h3>Instance variables<\/h3>\n<pre>---&#x000A;- :@_config&#x000A;- :@view_renderer&#x000A;- :@_assigns&#x000A;- :@_controller&#x000A;- :@view_flow&#x000A;- :@output_buffer&#x000A;- :@virtual_path&#x000A;- :@haml_buffer&#x000A;- :@t&#x000A;- :@parameters<\/pre>\n","api_candidate_identifier":"3b66c0d35576257615aef4d7c584ba3a","api_candidate_task_identifier":"3b66c0d35576257615aef4d7c584ba3a","ce_norm_group_type":"selection"}
	private final HttpResponse<JsonNode> mi_response;
	
	protected JsonReport(HttpResponse<JsonNode> response)
	{
		mi_response = response;
	}
	
	@Override
	public void saveTo(File destination)
	{
		FileUtil.writeTo(new File(destination.getAbsolutePath() + getExtension()), mi_response.getRawBody());
	}

	@Override
	public String getExtension() {
		return FileExtension.JSON.getExtension();
	}
}