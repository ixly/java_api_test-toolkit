package nl.holder.ixly;


import java.io.File;
import java.util.List;

import nl.holder.ixly.example.CandidateTaskManager;
import nl.holder.ixly.file.ReportIF;
import nl.holder.ixly.net.resource.CandidateTask;
import nl.holder.ixly.net.resource.Task;
import nl.holder.ixly.net.security.Credentials;
import nl.holder.ixly.util.FileUtil.FileExtension;

public class Main 
{
	// This example displays the standard flow, which is:
	//
	// 1. Create a new candidate task with create/lookup/update candidate tasks. The system checks if there is
	// a candidate. If not, it will create one. After that the system will check if the candidate task, supplied, exists.
	//
	// 2.The application should display the url where the candidate can take the test.
	//
	// 3.Candidate takes the test and the application can poll for the result.
	//  OR
	// 4. Candidate takes the test and the System will signal the application via the url_state_callback (Not implemented in this example).  
	//
	// 5. The application retrieves the report with the default report method or get report method. 
	// 
	// (Each Manager object will have an own example which you refer to (eg. CandidateTaskmanager))
	// 
	public static void main(String args[]) throws InterruptedException
	{
		final String basicAuthUsername;
		final String basicAuthPassword;
		final Credentials credentials;
		final List<Task> allAvailableTaskList;
		final Task task;
		final File tmpDir;
		final String fileName;
		final File destination;
		ReportIF report;
		
		CandidateTask candidateTask;
		
		// Credentials
		basicAuthUsername = "jeroen+apitk@holder.nl";
		basicAuthPassword = "19b51857421a37c58cfd3aca7a5123a4";
		
		credentials = new Credentials(basicAuthUsername, basicAuthPassword);
		
		// List all available candidate tasks
		allAvailableTaskList = CandidateTaskManager.list(credentials);
		task = allAvailableTaskList.get(0);
		
		// Create a new candidate task
		candidateTask = CandidateTaskManager.create(credentials, task);
		
		// Possible solution:
		// Do the test at.. newCandidateTask.getUrl();
		// Check the status of a candidateTask
		System.out.println(String.format("Do the test at: \n %s \n\n", candidateTask.getUrl()));
		
		// Check for the state and poll every 10 sec or so
		// Checks if the test is taken.
		while(!(candidateTask = CandidateTaskManager.status(candidateTask)).getState().equals("finished")) {	Thread.sleep(4000); }
		
	    // Get default report and save it locally

	    tmpDir = new File(System.getProperty("java.io.tmpdir"));
	    fileName = "ixly_result";
	    destination = new File(tmpDir, fileName);		
		
	    report = CandidateTaskManager.defaultReport(candidateTask, FileExtension.JSON);
	    report.saveTo(destination);
	    
	    report = CandidateTaskManager.defaultReport(candidateTask, FileExtension.HTML);
	    report.saveTo(destination);
	    
	    report = CandidateTaskManager.defaultReport(candidateTask, FileExtension.PDF);
	    report.saveTo(destination);

	    System.out.println(String.format("Saved html file to: %s", tmpDir));
	}
}
