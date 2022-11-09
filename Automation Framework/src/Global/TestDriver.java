package Global;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.testng.TestNG;

import Utilities.CommonFrameworkKeywords;
import Utilities.HtmlReportKeywords;
import Utilities.ConfigPropertiesFiles;

public class TestDriver extends CommonApplicationKeywords
{
	
	public static void main (String [] args) 
	{
		CommonFrameworkKeywords common = new CommonFrameworkKeywords();
		try
		{
			
			
			common.kickOff();
			TestNG testng = new TestNG();
			List<String> suites = new ArrayList<String>();
			suites.add("./config/testng.xml");
			testng.setOutputDirectory(testReportsDirectory+"/testng");
			testng.setTestSuites(suites);
			testng.run();	
			
		}
		catch(Exception e)
		{
			sendToLogFile("error", e.toString());
		}
		finally
		{
			try
			{
				deleteTempFiles();
				common.zipReport();
				common.sendMail();
				
			}
			catch(Exception e)
			{
				sendToLogFile("error", e.toString());
			}
			finally
			{
				sendToLogFile("INFO","###################################");
				sendToLogFile("INFO","Test Script Execution Completed");
				sendToLogFile("INFO","####################################");
	
			}
			
		}
	}
		
}
