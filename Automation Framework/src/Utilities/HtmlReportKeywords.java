package Utilities;





import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.testng.IExecutionListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;







public class HtmlReportKeywords extends TestListenerAdapter implements IExecutionListener  {

	public int totalTCSuccess=0;
	public int totalTCFail=0;
	public int totalTCSkip=0;
	String projectName="";
	String clientLogoPath="/Logos/client_logo.jpg";
	String LogoPath="/Logos/Vayana_Logo.jpg";
	long totalDurationInMillis=0;
	String totalDuration="";
	String buildNo="";
	String machine="";
	String os="";
	String osVersion="";
	String browser="";
	String browserVersion="";
	int totalTC=ConfigPropertiesFiles.tcNames.size();
	int totalPassPercentage=0;
	int totalFailPercentage=0;
	int totalSkipPercentage=0;
	static long totalDurationAllMachines=0;
	public HashMap<String, String> testCaseStatus = new HashMap<String, String>();
	public HashMap<String, String> testCaseDuration = new HashMap<String, String>();
	public static HashMap<String, String> operatingSytsem = new HashMap<String, String>();
	public static HashMap<String, String> operatingSytsemVersion = new HashMap<String, String>();
	public static HashMap<String, String> browserName = new HashMap<String, String>();
	public static HashMap<String, String> browserVersionName = new HashMap<String, String>();
	public static HashMap<String, String> totalExecutedInOs = new HashMap<String, String>();
	public static HashMap<String, String> totalPassedInOS = new HashMap<String, String>();
	public static HashMap<String, String> totalFailedInOS = new HashMap<String, String>();
	public static HashMap<String, String> totalSkippedInOS = new HashMap<String, String>();
	public static HashMap<String, String> totalDurationInOS = new HashMap<String, String>();
	public String getTodaysDate()
	{
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		   //get current date time with Date()
		   Date date = new Date();
		  return dateFormat.format(date);
	}
	public String addTableRowInPage()
	{
		
		String temp="";
		 String a="";
		 int sNo=1;
		 try {
			
			 
			 for(String tcName : ConfigPropertiesFiles.tcNames)
			 {
				 if(testCaseDuration.containsKey(tcName))
				 {
					 String tdStatus="";
					 if(testCaseStatus.get(tcName).equalsIgnoreCase("fail"))
					 {
		
						 tdStatus="<TR style='color:red'><TD >"+sNo+"</TD><TD >"+ConfigPropertiesFiles.allTcModuleName.get(tcName)+"</TD><TD><a style='color:red' href='./Resultfiles/"+tcName+".html'>"+tcName+"</a></TD><TD>      "+ConfigPropertiesFiles.allTcDescription.get(tcName)+"    </TD><td>"+testCaseDuration.get(tcName).replace("null", "pending")+"</td><TD>"+testCaseStatus.get(tcName).replace("null", "pending")+"</TD></TR>";
					 }
					 if(testCaseStatus.get(tcName).equalsIgnoreCase("pass"))
					 {
						 tdStatus="<TR style='color:green'><TD >"+sNo+"</TD><TD >"+ConfigPropertiesFiles.allTcModuleName.get(tcName)+"</TD><TD><a style='color:green' href='./Resultfiles/"+tcName+".html'>"+tcName+"</a></TD><TD>      "+ConfigPropertiesFiles.allTcDescription.get(tcName)+"    </TD><td>"+testCaseDuration.get(tcName).replace("null", "pending")+"</td><TD>"+testCaseStatus.get(tcName).replace("null", "pending")+"</TD></TR>";
					 }
					 if(testCaseStatus.get(tcName).equalsIgnoreCase("skip"))
					 {
						 tdStatus="<TR style='color:blue'><TD >"+sNo+"</TD><TD >"+ConfigPropertiesFiles.allTcModuleName.get(tcName)+"</TD><TD><a style='color:blue' href='./Resultfiles/"+tcName+".html'>"+tcName+"</a></TD><TD>      "+ConfigPropertiesFiles.allTcDescription.get(tcName)+"    </TD><td>"+testCaseDuration.get(tcName).replace("null", "pending")+"</td><TD>"+testCaseStatus.get(tcName).replace("null", "pending")+"</TD></TR>";
					 }
					 temp=tdStatus;
				}
				 else
				 {
					 temp="<TR><TD>"+sNo+"</TD><TD >"+ConfigPropertiesFiles.allTcModuleName.get(tcName)+"</TD><TD><a href='./Resultfiles/"+tcName+".html'>"+tcName+"</a></TD><TD>      "+ConfigPropertiesFiles.allTcDescription.get(tcName)+"    </TD><td>pending</td><TD>pending</TD></TR>";
						
				 }
				 a=a+temp;
				 sNo++;
			 }
	        } catch (Exception e) {
	            // ...
	        } 
		return a;
		
	}
	
	public String addTableRowInReportPage()
	{
		
		String temp="";
		 String a="";
		 int sNo = 1;
		 try {
			 
			 for(String machineName : ConfigPropertiesFiles.machineNames)
			 {
				 if(operatingSytsem.containsKey(machineName))
				 {
					 temp="<tr><td><a href='"+getTestSuitePath(machineName.replace(" ", ""))+"' title='"+machineName+"' >"+machineName+"</a></td><td>"+operatingSytsem.get(machineName)+"</td><td>"+operatingSytsemVersion.get(machineName)+"</td><td>"+browserName.get(machineName)+"</td><td>"+browserVersionName.get(machineName)+"</td><td>"+totalExecutedInOs.get(machineName)+"</td><td>"+totalPassedInOS.get(machineName)+"</td><td>"+totalFailedInOS.get(machineName)+"</td><td>"+totalSkippedInOS.get(machineName)+"</td><td>"+totalDurationInOS.get(machineName)+"</td></tr>";
					 
				 }
				 else
				 {
					 temp="<tr><td><a href='"+getTestSuitePath(machineName.replace(" ", ""))+"'title='"+machineName+"' >"+machineName+"</a></td><td>pending</td><td>pending</td><td>pending</td><td>pending</td><td>pending</td><td>pending</td><td>pending</td><td>pending</td><td>pending</td></TR>";
						 
				 }
				 a=a+temp;
				 sNo++;
			 }
	        } catch (Exception e) {
	            // ...
	        } 
		return a;
		
	}

	
	
	
	public String addReportLink()
	{
		String txt="";

			txt="<br><a href='./../Report.html'>Click here to see the main page</a>";
		return txt;
	}
	public static String addHeadTagLinks()
	{
		String txt="";

			//txt="<link rel='stylesheet' type='text/css' href='../Extras/style.css'></br>";
txt="<head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>	<meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>	<title>Html Reporting</title>	<!-- Favicon -->    <link rel='shortcut icon' href='./Extras/img/favicon.ico' type='image/x-icon'>    <link rel='icon' href='./Extras/img/favicon.ico' type='image/x-icon'>	<!-- Library Css Files-->	<link rel='stylesheet' href='./Extras/css/bootstrap.min.css'>    <!-- Font Awesome -->    <link rel='stylesheet' href='./Extras/css/font-awesome.min.css'>    <!-- Common Style-->	<link rel='stylesheet' href='./Extras/css/style.css'>	<!-- Js Library-->	<script src='./Extras/js/jquery-1.9.1.js'></script>	<script src='./Extras/js/bootstrap.min.js'></script></head>";
		return txt;
	}
	
	public  void machinePageReport(String path)
	{
		try{
			PrintWriter writer = new PrintWriter(path);
			writer.print("");
			writer.close();
			//Writer output;
			BufferedWriter output;
			output = new BufferedWriter(new FileWriter(path,true));  //clears file every time
			output.write("<html lang='en'>"+addHeadTagLinks().replace("./Extras", "../Extras")+"<body><div class='header_bg header'><div class='container'><div class='row'><div class='col-md-6 col-sm-6'><h1 class='logo'/></div>	<div class='col-md-6 col-sm-6'><span class='client_logo pull-right' /></div></div></div></div><div class='content'><div class='container'><h2>"+projectName+"</h2>"
					+ "<div class='row'><div class='col-md-12 col-sm-12' id='ddb'><table class='table table-bordered text-center'><tr class='default'><th><b>Executed Date</b></th><th>Total Executed Duration</th><th>Build no</th></tr><tr><td>"+getTodaysDate()+"</td><td>"+getDuration(totalDurationAllMachines).replace("00hrs:", "").replace("00mins:", "")+"</td><td>"+buildNo+"</td></tr></table></div><div class='col-md-6 col-sm-6'  id='exectable'>"
                    +"  <table class='table table-bordered'><tr><td><b>System/Device Name</b></td><td>"+machine+"</td></tr><tr><td><b>Operating System</b></td><td>"+os+"</td></tr><tr><td><b>Browser/Application Name</b></td><td>"+browser+"</td></tr></table></div></div><h3>Test Execution Status</h3>"            	
					+"<table class='table table-bordered text-center'> <tr class='default'><th>Executed</th><th>Passed</th><th>Failed</th><th>Skipped</th></tr>"
					+"<tr><td>"+totalTC+"</td><td>"+totalTCSuccess+"</td><td>"+totalTCFail+"</td><td>"+totalTCSkip+"</td></tr><tr><td>Percentage</td><td>"+totalPassPercentage+"%"+"</td><td>"+totalFailPercentage+"%"+"</td><td>"+totalSkipPercentage+"%"+"</td></tr>"
							+ "</table><br/>"
					+ "<table class='table table-bordered  text-center'>"
					+ "<tr class='default'><th>S.No</th><th>Module Name</th><th>Test case ID</th><th>Test case description</th><th>Executed Duration</th><th>Status</th></tr>"
					+ ""+addTableRowInPage()
					+"</table>"
					+ "<p><a href='../Report.html' class='btn btn-warning'>Click here to see the main report</a></p>"
					+ "</div></div><script src='../Extras/js/common.js'></script></body></html>"
					);
					
			output.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}

	public  void mainPageReport(String path)
	{
		try{
			PrintWriter writer = new PrintWriter(path);
			writer.print("");
			writer.close();
			//Writer output;
			BufferedWriter output;
			output = new BufferedWriter(new FileWriter(path,true));  //clears file every time
			output.write("<html lang='en'>"+addHeadTagLinks().replace("../","./")+"<body><div class='header_bg header'><div class='container'><div class='col-md-6 col-sm-6'><h1 class='logo'/></div>	<div class='col-md-6 col-sm-6'><span class='client_logo pull-right' /></div></div></div></div><div class='content'><div class='container'><h2>"+projectName+"</h2>"
					+ "<div class='row'><div class='col-md-12 col-sm-12' id='ddb'><table class='table table-bordered text-center'><tr class='default'><th><b>Executed Date</b></th><th>Total Executed Duration</th><th>Build no</th></tr><tr><td>"+getTodaysDate()+"</td><td>"+getDuration(totalDurationAllMachines).replace("00hrs:", "").replace("00mins:", "")+"</td><td>"+buildNo+"</td></tr></table></div></div>"                      
							+ "<div id='exectable'><h3>Test Execution Status</h3><table class='table table-bordered text-center'><tr class='default'><th>System/Device Name</th><th>Operating System</th><th>OS Version</th><th>Browser/Application Name</th><th>Browser/Application Version</th><th>Executed</th><th>Passed</th><th>Failed</th><th>Skipped</th><th>Executed Duration</th></tr>"+addTableRowInReportPage()+" </table></div></div></div><script src='./Extras/js/common.js'></script>"
							+ "</body></html>");
		output.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
	}

	
	
	public void updateReport(ITestResult result)
	{
	
		for(int i=0;i<Selenium_Appium_Keywords.pageResultsDir.size();i++)
		   {
			   if(Selenium_Appium_Keywords.pageResultsDir.get(i).toLowerCase().contains(result.getMethod().getXmlTest().getParameter("selenium.machinename").toLowerCase().replace(" ","")))
			   {
				   machinePageReport(Selenium_Appium_Keywords.pageResultsDir.get(i));
			   		mainPageReport(Selenium_Appium_Keywords.testReportsDirectory+"/Report.html");
			   		//failureReport(GenericKeywords.failurePageResultsDir.get(i));
			   		
			   }
		   }
		 
	}
	
	
	public String getTestSuitePath(String machineName)
	{
		String path="";
		 for(int i=0;i<Selenium_Appium_Keywords.pageResultsDir.size();i++)
		   {
			   if(Selenium_Appium_Keywords.pageResultsDir.get(i).toLowerCase().contains(machineName.toLowerCase()))
			   {
				   path=	Selenium_Appium_Keywords.pageResultsDir.get(i).replace(Selenium_Appium_Keywords.testReportsDirectory, ".");
				 
			   		
			   }
		   }
		return path;
		 
	}
   @Override
   public void onTestFailure(ITestResult result) 
   {
	   
	   totalTCFail++;
	   updateReportVariables(result);
	   int percentage=(int) (((double)totalTCFail/totalTC)*100);
	   totalFailPercentage=percentage;
	   testCaseStatus.put(result.getMethod().getMethodName(), "Fail");
	   updateReport(result);

   }
   
   @Override
   public void onTestSuccess(ITestResult result) {
	  
	   totalTCSuccess++;
	   updateReportVariables(result);
	   int percentage=(int) (((double)totalTCSuccess/totalTC)*100);
	   totalPassPercentage=percentage;
	   testCaseStatus.put(result.getMethod().getMethodName(), "Pass");
	   updateReport(result);

	   
	   
   }
   
   @Override
   public void onTestSkipped(ITestResult result) {
	   totalTCSkip++;
	   updateReportVariables(result);
	    int percentage=(int) (((double)totalTCSkip/totalTC)*100);
	   totalSkipPercentage=percentage;
	   testCaseStatus.put(result.getMethod().getMethodName(),"Skip");
	   updateReport(result);

   }
   
   public void updateReportVariables(ITestResult result)
   {
	   long time = result.getEndMillis() - result.getStartMillis();
	   testCaseDuration.put(result.getMethod().getMethodName(), getDuration(time).replace("00hrs:", "").replace("00mins:", ""));
	   totalDurationInMillis=totalDurationInMillis+time;
	   if(totalDurationInMillis>totalDurationAllMachines)
	   {
		   totalDurationAllMachines=totalDurationInMillis;
	   }
	   totalDuration=getDuration(totalDurationInMillis).replace("00hrs:", "").replace("00mins:", "");
	   machine=result.getTestClass().getXmlTest().getParameter("selenium.machinename");
	   os=result.getTestClass().getXmlTest().getParameter("selenium.os");
	   osVersion=result.getTestClass().getXmlTest().getParameter("selenium.osVersion");
	   browserVersion=result.getTestClass().getXmlTest().getParameter("selenium.browserVersion");
	   browser=result.getTestClass().getXmlTest().getParameter("selenium.browser");
	   operatingSytsem.put(machine, os);
	   operatingSytsemVersion.put(machine, osVersion);
	   browserName.put(machine, browser);
	   browserVersionName.put(machine, browserVersion);
	   
	   totalExecutedInOs.put(machine, ""+ConfigPropertiesFiles.tcNames.size());
	   totalPassedInOS.put(machine, ""+totalTCSuccess);
	   totalFailedInOS.put(machine,""+totalTCFail);
	   totalSkippedInOS.put(machine,""+totalTCSkip);
	   totalDurationInOS.put(machine,totalDuration);
	   buildNo=ConfigPropertiesFiles.getFrameworkConfigProperty("Version_Name");
	   projectName=ConfigPropertiesFiles.getFrameworkConfigProperty("Project_Name");
	
   }
  
   public String getDuration(long millis){
	   String hms ="";
	   try{
	   hms = String.format("%02dhrs:%02dmins:%02dsecs", TimeUnit.MILLISECONDS.toHours(millis),
	            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
	            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	    
	   }
	   catch(Exception e)
	   {
		   System.out.println(e.toString());
	   }
	return hms;
	   
	}
   
   @Override
   public void onExecutionFinish() {
	   for(String machineName : ConfigPropertiesFiles.machineNames)
		 {
			 if(HtmlReportKeywords.operatingSytsem.containsKey(machineName))
			 {
				
				 mainPageReport(Selenium_Appium_Keywords.testReportsDirectory+"/Report.html");
			 }
		 }
       
   }
@Override
public void onExecutionStart() {
	// TODO Auto-generated method stub
	
}

}