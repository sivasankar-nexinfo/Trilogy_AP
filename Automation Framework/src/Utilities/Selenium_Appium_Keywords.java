package Utilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import Utilities.JXlKeywords;
import Utilities.CommonFrameworkKeywords;
import Utilities.ConfigPropertiesFiles;
public class Selenium_Appium_Keywords extends ConfigPropertiesFiles
{
	public String resultsDir="";
	public String currentExecutionName="";
	public RemoteWebDriver driver;
	public DesiredCapabilities capabilities = new DesiredCapabilities();
	public String identifier,locator,locatorDescription;
	public String currentBrowser="";

	public WebElement webElement;
	public boolean isTestFailure=false;
	public int pageTextLoadWaitTime,pageElementLoadWaitTime,driverWaitTime,pageLoadWaitTime=0;	
	public static List<String> cssReportsDir=new ArrayList<String>();
	public static String testReportsDirectory;
	public static int testcasefailureNo,testcaseManualScreenshotNo;
    public static identifierType identifierType;
    public static boolean isWindowreadyStateStatus=true;
	public static List<String> ipName=new ArrayList<String>();
	public static List<String> testName=new ArrayList<String>();
	public static List<String> operatingSystemName=new ArrayList<String>();
	public static List<String> browserName=new ArrayList<String>();
	public static List<String> browserVersionName=new ArrayList<String>();
	public static List<String> operstingSystemVersionName=new ArrayList<String>();
	public static List<String> testReportsDir=new ArrayList<String>();
	public static List<String> pageResultsDir=new ArrayList<String>();
	public String currentExecutionOS="";
	public String currentTestCaseModuleName="";
	public void TestStart(String hostName,String testCaseName)
	{	
		isTestFailure=false;
		currentTestCaseName=testCaseName;
		currentTestCaseModuleName=allTcModuleName.get(testCaseName);
		setEnvironmentTimeouts();
		testReportStart(hostName);
		iterationCount.clear();
		textCaseDataRowCount();
	}
	public void takeWindowScreenShot()
	{
		maximiseCurrentWindow();
		String scrPath="../../Screenshots";  
		  Selenium_Appium_Keywords.testcaseManualScreenshotNo++;
		windowScreenShot("ManualScreenshot"+Selenium_Appium_Keywords.testcaseManualScreenshotNo);
		writeTestStepReport("<a href='"+scrPath+"/ManualScreenshot"+Selenium_Appium_Keywords.testcaseManualScreenshotNo+".png'> ",currentExecutionName,currentTestCaseName);
		writeTestStepReport("<img src='"+scrPath+"/ManualScreenshot"+Selenium_Appium_Keywords.testcaseManualScreenshotNo+".png' height='200' width='200'/><br></br></a></font>",currentExecutionName,currentTestCaseName);
	
	}
	public void setup(String machineName, String host, String port,
			String browser, String os, String browserVersion, String osVersion) {
		setEnvironmentTimeouts();
		resultsFolder(machineName.replace(" ",""), host, port, browser, os, browserVersion,
				osVersion);
		currentExecutionMachineName(machineName.replace(" ",""));
		currentExecutionOS=os.toLowerCase();

		
		
	}
	
	 public void setEnvironmentTimeouts()
	 {
		 setTimeoutsToVariables();
	 }
	public void closeAllSessions()
	{
		driver.quit();
	}
	public enum platFormName
	{
		IOS,
		ANDROID,
		WINDOWS,
		MAC,
		LINUX
	}
	

	public enum browserType
	{
		FIREFOX,
		INTERNETEXPLORER,
		CHROME,
		OPERA,
		WEBVIEWAPP,
		SAFARILAUNCHER,
		SAFARI,
		SHB_MTC
	}

	public void currentExecutionMachineName(String machineName) {
		currentExecutionName = machineName;
	}
	
	public void resultsFolder(String machineName,String host,String port,String browser,String os,String browserVersion,String osVersion)
	{

		resultsDir=Selenium_Appium_Keywords.testReportsDirectory+"/"+machineName.replace(" ","");
		ipName.add(host);
		testName.add(browser);
		operatingSystemName.add(os);
		browserName.add(browser);
		browserVersionName.add(browserVersion);
		operstingSystemVersionName.add(osVersion);
		File dir = new File(resultsDir);
		dir.mkdir();
		(new File(resultsDir + "/Resultfiles/"))
		.mkdir();
		testReportsDir.add(resultsDir + "/Resultfiles/");
		pageResultsDir.add(resultsDir + "/"+machineName.replace(" ","")+".html");
		cssReportsDir.add(resultsDir+"/style.css");
		transferLogos();
		transferFiles();
	}
	public  void lanuchBrowser(String machineName,String host,String port,String browser,String os,String browserVersion,String osVersion)
	{
		
		platFormName b = platFormName.valueOf(os.toUpperCase().trim());		
		browserType browserNamee = browserType.valueOf(browser.toUpperCase().trim());
		currentBrowser=browser;
		sendToLogFile("INFO","Opening "+browser+" Browser...");
		try
		{
			capabilities.setCapability("newCommandTimeout",getFrameworkConfigProperty("AppiumTimeOut").toString().trim());
			
				switch (b)
				{
					case LINUX:
					capabilities.setCapability("browser","chrome");
					capabilities.setCapability("version",browserVersion);
					capabilities.setCapability("platform", Platform.LINUX);
					driver= new RemoteWebDriver(new URL("http://"+host+":"+port+"/wd/hub"),capabilities);
			        break;
					case IOS:
						
						capabilities.setCapability("platformName","iOS");
						capabilities.setCapability("platformVersion",osVersion);
						capabilities.setCapability("deviceName",machineName);
						capabilities.setCapability("nativeEvents",false);
							driver= (AppiumDriver<WebElement>) new RemoteWebDriver(new URL("http://"+host+":"+port+"/wd/hub"),capabilities);
						break;
						
					case ANDROID:
						capabilities.setCapability("deviceName","Android");
							if(getFrameworkConfigProperty("AppType").equalsIgnoreCase("web"))
							{
								capabilities.setCapability(CapabilityType.BROWSER_NAME, "chrome");
							}
							else
							{
								capabilities.setCapability("appPackage", getFrameworkConfigProperty("AppPackage"));
								capabilities.setCapability("appActivity",getFrameworkConfigProperty("AppActivity"));
								capabilities.setCapability("autoWebview",true);
								capabilities.setCapability("recreateChromeDriverSessions",true);
								
								
							}
						driver= new AndroidDriver<WebElement>(new URL("http://"+host+":"+port+"/wd/hub"),capabilities);
					
						break;
						
					case WINDOWS:
						
						
					
							if(browserNamee.toString().equalsIgnoreCase("CHROME"))
							{
								System.setProperty("webdriver.chrome.driver", "./Jars/chromedriver.exe");
								
								if(port.toLowerCase().contains("crossbrowsertesting"))
								{
									capabilities.setCapability("browser_api_name", "Chrome"+browserVersion);
									capabilities.setCapability("os_api_name", "Win"+osVersion);
									//capabilities.setCapability("record_video", "true");
									//capabilities.setCapability("record_network", "true");
								}
								if(port.toLowerCase().contains("browserstack"))
								{
									capabilities.setCapability("browser", "Chrome");
									capabilities.setCapability("browser_version", "52.0");
									capabilities.setCapability("os", "Windows");
									capabilities.setCapability("os_version", "7");
									capabilities.setCapability("resolution", "1024x768");
									capabilities.setCapability("browserstack.local", "true");
							
								}
								else{
									capabilities.setCapability("platform",os.trim().toUpperCase());
									 ChromeOptions options = new ChromeOptions();
									 options.addArguments("test-type");
									 capabilities.setCapability(ChromeOptions.CAPABILITY, options);
									 capabilities.setBrowserName("chrome");
								}
								 
							}
							else if(browserNamee.toString().equalsIgnoreCase("FIREFOX"))
							{
								System.setProperty("webdriver.gecko.driver", "./Jars/geckodriver.exe");
								if(port.toLowerCase().contains("crossbrowsertesting"))
								{
									capabilities.setCapability("browser_api_name", "FF"+browserVersion);
									capabilities.setCapability("os_api_name", "Win"+osVersion);
									capabilities.setCapability("record_video", "true");
									capabilities.setCapability("record_network", "true");
								}
								if(port.toLowerCase().contains("browserstack"))
								{
									capabilities.setCapability("browser", browser);
									capabilities.setCapability("browser_version", browserVersion);
									capabilities.setCapability("os", "Windows");
									capabilities.setCapability("os_version", osVersion);
									capabilities.setCapability("resolution", "1024x768");
								//	capabilities.setCapability("browserstack.local", "true");
									
								}
								else
								{
								capabilities.setBrowserName("firefox");
								}
							}
							else if(browserNamee.toString().equalsIgnoreCase("InternetExplorer"))
							{
								System.setProperty("webdriver.ie.driver", "./Jars/IEDriverServer.exe");
								if(port.toLowerCase().contains("crossbrowsertesting"))
								{
									capabilities.setCapability("browser_api_name", "IE"+browserVersion);
									capabilities.setCapability("os_api_name", "Win"+osVersion);
									capabilities.setCapability("record_video", "true");
									capabilities.setCapability("record_network", "true");
								}
							else{
							
							
								capabilities.setBrowserName("internet explorer");
							//	capabilities.setCapability("nativeEvents", false);
								capabilities.setCapability("ie.ensureCleanSession", true);
							}
							}
						
					//	driver=  new RemoteWebDriver(new URL("http://"+host+":"+port),capabilities);
						
						driver = new ChromeDriver();	
						maximiseCurrentWindow();
						break;
					case MAC:
						capabilities.setCapability("platform",os.trim().toUpperCase());
						if(browserNamee.toString().equalsIgnoreCase("CHROME"))
						{
							
							 ChromeOptions options = new ChromeOptions();
							 options.addArguments("test-type");
							 capabilities.setCapability(ChromeOptions.CAPABILITY, options);
							 capabilities.setBrowserName("chrome");
						}
						else if(browserNamee.toString().equalsIgnoreCase("SAFARI"))
						{
							capabilities.setBrowserName("safari");
							
						}					
						driver=new RemoteWebDriver(new URL("http://"+host+":"+port+"/wd/hub"),capabilities);		
						break;
				}
				driver.manage().timeouts().implicitlyWait(driverWaitTime, TimeUnit.SECONDS);
				sendToLogFile("INFO","Browser: Open Successful : "+currentBrowser.toLowerCase());
				if((b.toString().equalsIgnoreCase("android")) && (getFrameworkConfigProperty("AppType").equalsIgnoreCase("web")))
				{
					stepPassed("Lanuch App: "+getFrameworkConfigProperty("AppName"));
					goTo(getFrameworkConfigProperty("URL"),machineName);
				}
				else if(!(b.toString().equalsIgnoreCase("android")))
				{
					stepPassed("Lanuch App : "+currentBrowser.toLowerCase());
					goTo(getFrameworkConfigProperty("URL"),machineName);
				}
		
		}
		catch(TimeoutException e)
		{
			stepFailed("Page fail to load within in "+getFrameworkConfigProperty("pageLoadWaitTime")+" seconds");
		}
		catch (Exception e)
		{
			sendToLogFile("ERROR","Browser: Open Failure/Navigation cancelled, please check the application window.");			
			sendToLogFile("Error", e.toString());
			stepFailed("Browser: Open Failure/Navigation cancelled, please check the application window.");
            

		}
	}
	
	public void switchToContext(String contextName)
	{
		try{
    	for(int i=0;i<=60;i++)
    	{
    		try
    		{
    		
    			
    			((AppiumDriver<WebElement>) driver).context(contextName);
    			break;
    		}
    		catch(Exception e)
    		{
    			System.out.println(((AppiumDriver<WebElement>) driver).getContextHandles());
    			Thread.sleep(1000);
    			System.out.println(e.toString());
    		}
    		if(i>60)
    		{
    			break;
    		}
    	}
		}
		catch(Exception e)
		{
			stepFailed(e.toString());
		}
	}

	public  void goTo(String url)
	{
		try{
			
		
		sendToLogFile("INFO","Navigating to URL : "+url);
		driver.get(url);
		sendToLogFile("INFO","Navigation Successful : "+url);
		stepPassed("Go to : "+url);
		}
		catch(TimeoutException e)
		{
			stepFailed("Page fail to load within in "+pageLoadWaitTime+" seconds");
		}
		catch (Exception e)
		{
			sendToLogFile("ERROR","Browser: Open Failure/Navigation cancelled, please check the application window.");			
            stepFailed("Go to : "+url);

		}
		
	}
	public  void goTo(String url, String machineName)
	{
		try{
			
		
		sendToLogFile("INFO","Navigating to URL : "+url);
		driver.get(url);
		sendToLogFile("INFO","Navigation Successful : "+url);
		stepPassed("Go to : "+url);
		}
		catch(TimeoutException e)
		{
			stepFailed("Page fail to load within in "+pageLoadWaitTime+" seconds");
		}
		catch (Exception e)
		{
			
			sendToLogFile("ERROR","Browser: Open Failure/Navigation cancelled, please check the application window.");			
            stepFailed("Go to : "+url);

		}
		
	}
	public void closeBrowser()
	{
		try
		{
			sendToLogFile("INFO","Closing Browser...");
			if (currentBrowser.contains("chrome"))
			{
				deleteAllCookies();
				driver.quit();
			}
			else
			{
				deleteAllCookies();
				driver.close();
			}
			
			//selenium=null;
			sendToLogFile("INFO","Browser: Close Successful");
			stepPassed("Close Browser");
		}
		catch (Exception e)
		{
			sendToLogFile("ERROR","Browser: Close Failure");			
            stepFailed("Close Browser");
		}
	}
//##############
	public  void identifyBy(String identifier) {
        identifierType i = identifierType.valueOf(identifier);
        switch (i) {
        case xpath:
              webElement = driver.findElement(By.xpath(locator));
              break;
        case id:
              webElement = driver.findElement(By.id(locator));
              break;
        case name:
              webElement = driver.findElement(By.name(locator));
              break;
        case lnktext:
              webElement = driver.findElement(By.linkText(locator));
              break;
        case partiallinktext:
              webElement = driver.findElement(By.partialLinkText(locator));
              break;
        case classname:
              webElement = driver.findElement(By.className(locator));
              break;
        case cssselector:
              webElement = driver.findElement(By.cssSelector(locator));
              break;
        case tagname:
              webElement = driver.findElement(By.tagName(locator));
              break;
        default:
              sendToLogFile("Error", "Element not found '" + locator     + "'");
       }
  }


	
	public  void waitForElement(String objName) 
	{
		waitForElement(objName,pageElementLoadWaitTime);
	}
	public void waitTimeForException(int sec)
	{
		try{
			Thread.sleep(sec*1000);
		}
		catch(Exception e)
		{
			stepFailed(e.toString());
		}
	}
	public  void waitForElement(String objectName,int timeout) 
	{
		try{
		driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);

		for(int i=1;i<=timeout;i++)
		{
			
		try{
			//setting implicit wait for  element found

			findWebElement(objectName);
			break;
		}
		catch(InvalidSelectorException e)
		{
			sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(StaleElementReferenceException e)
		{
			sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
			waitTimeForException(1);
			System.out.println(e.toString());
		}
		catch(NoSuchElementException e)
		{

			sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(UnreachableBrowserException e)
		{
			stepFailed(e.toString());
		}
		catch(WebDriverException e){
			sendToLogFile("Info", "WebDriverException occured. Retrying..............");
			waitTimeForException(1);
			System.out.println(e.toString());
		}
		
		if(i==timeout)
		{
			stepFailed(locatorDescription+ " element not found within '"+timeout+"' seconds timeout ");
		}
		}
		}
		catch(Exception e)
		{
			stepFailed(e.toString());
			
		}
		finally
		{
			//Re-setting the implicit wait is set for the life of the WebDriver object instance
			driver.manage().timeouts().implicitlyWait(driverWaitTime, TimeUnit.SECONDS);
		}
			
  }
	
	public  void verifyElementTextIsPresent(String objectLocator,String expectedText)
	{

			waitForElement(objectLocator, pageElementLoadWaitTime);
			for(int i=1;i<=pageElementLoadWaitTime;i++)
			{
				try{
				if(webElement.getText().trim().equalsIgnoreCase(expectedText.trim()))
				{
					stepPassed("Verified the "+locatorDescription+" element contains text '"+expectedText+"'");
					break;
				}
				else
				{
					stepFailed("The "+locatorDescription+" element does not contains text '"+expectedText+"'");
					break;
				}
				}
				catch(InvalidSelectorException e)
				{
					sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(StaleElementReferenceException e)
				{
					sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(NoSuchElementException e)
				{

					sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
					waitTimeForException(1);
				}
				
				catch(UnreachableBrowserException e)
				{
					stepFailed(e.toString());
				}
				catch(WebDriverException e){
					sendToLogFile("Info", "WebDriverException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(Exception e)
				{
					stepFailed(e.toString());
				}
				if(i==pageElementLoadWaitTime)
				{
					stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
				}
			}
			
	
	}
	public void findWebElement(String objectLocator)
	{
	  	parseidentifyByAndlocator(objectLocator);
        identifyBy (identifier);
		
	}
	
  public  void sendKeys(String objectLocator, String inputValue)
  {
	  waitForElementToDisplay(objectLocator, pageElementLoadWaitTime);
		 for(int i=1;i<=pageElementLoadWaitTime;i++)
		 {
			try
		  {
			
			webElement.click();
			webElement.clear();
			webElement.sendKeys(inputValue);
			Reporter.log("<Font >Click on :"+locatorDescription);
			stepPassed("Type '" + inputValue + "' in : " + locatorDescription);
			sendToLogFile("INFO", "Typing '" + inputValue + "' in : " + locatorDescription);
		     break;
		  }
		 
			catch(InvalidSelectorException e)
			{
				sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
				waitTimeForException(1);
			}
			catch(StaleElementReferenceException e)
			{
				sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
				waitTimeForException(1);
			}
			catch(NoSuchElementException e)
			{

				sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
				waitTimeForException(1);
			}
			
			catch(UnreachableBrowserException e)
			{
				stepFailed(e.toString());
			}
			catch(WebDriverException e){
				sendToLogFile("Info", "WebDriverException occured. Retrying..............");
				waitTimeForException(1);
			}
			catch(Exception e)
			{
				stepFailed("Exception Error '"+e.toString()+"'");
			}
			if(i==pageElementLoadWaitTime)
			{
				stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
			}
		 }
  }
  public  void sendKeysHidden(String objectLocator, String inputValue)
  {
	  waitForElement(objectLocator, pageElementLoadWaitTime);
		 for(int i=1;i<=pageElementLoadWaitTime;i++)
		 {
			try
		  {
			
			webElement.click();
			webElement.clear();
			webElement.sendKeys(inputValue);
			Reporter.log("<Font >Click on :"+locatorDescription);
			stepPassed("Type '" + inputValue + "' in : " + locatorDescription);
			sendToLogFile("INFO", "Typing '" + inputValue + "' in : " + locatorDescription);
		     break;
		  }
		 
			catch(InvalidSelectorException e)
			{
				sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
				waitTimeForException(1);
			}
			catch(StaleElementReferenceException e)
			{
				sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
				waitTimeForException(1);
			}
			catch(NoSuchElementException e)
			{

				sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
				waitTimeForException(1);
			}
			
			catch(UnreachableBrowserException e)
			{
				stepFailed(e.toString());
			}
			catch(WebDriverException e){
				sendToLogFile("Info", "WebDriverException occured. Retrying..............");
				waitTimeForException(1);
			}
			catch(Exception e)
			{
				stepFailed("Exception Error '"+e.toString()+"'");
			}
			if(i==pageElementLoadWaitTime)
			{
				stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
			}
		 }
  }
  
  public  void refreshCurrentPage()
  {
	  
		for(int i=1;i<=pageElementLoadWaitTime;i++)
		{
	  try
		  	{
              driver.navigate().refresh();
              sendToLogFile("INFO","Sucessfully Refreshed browser");
              break;
		  	}

	  catch(InvalidSelectorException e)
		{
			sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(StaleElementReferenceException e)
		{
			sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(NoSuchElementException e)
		{

			sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
			waitTimeForException(1);
		}
		
		catch(UnreachableBrowserException e)
		{
			stepFailed(e.toString());
		}
		catch(WebDriverException e){
			sendToLogFile("Info", "WebDriverException occured. Retrying..............");
			waitTimeForException(1);
		}
			catch(Exception e)
			{
				stepFailed(e.toString());
			}
			if(i==pageElementLoadWaitTime)
			{
				stepFailed("Error refreshing browser");
			}
  }
  }
  
  public  void clickBackButton()
  {
      for(int i=1;i<=pageElementLoadWaitTime;i++)
      {
	  try
        {
              driver.navigate().back();
              stepPassed("Click browser back button");
              sendToLogFile("INFO","Sucessfully moved to 'Back' page");
              break;
        }
	  catch(InvalidSelectorException e)
		{
			sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(StaleElementReferenceException e)
		{
			sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(NoSuchElementException e)
		{

			sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
			waitTimeForException(1);
		}
		
		catch(UnreachableBrowserException e)
		{
			stepFailed(e.toString());
		}
		catch(WebDriverException e){
			sendToLogFile("Info", "WebDriverException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(Exception e)
	  {
			stepFailed(e.toString());
		}
		if(i==pageElementLoadWaitTime)
		{
			stepFailed("Error moving to 'Back' page");
		}
       
      }
  }
  public  void clickForwardButton()
  {
      for(int i=1;i<=pageElementLoadWaitTime;i++)
      {
	  try
        {
              driver.navigate().forward();
              sendToLogFile("INFO","Sucessfully moved to 'Forward' page");
              stepPassed("Click browser forward button");
              break;
        }
	  catch(InvalidSelectorException e)
		{
			sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(StaleElementReferenceException e)
		{
			sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(NoSuchElementException e)
		{

			sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
			waitTimeForException(1);
		}
		
		catch(UnreachableBrowserException e)
		{
			stepFailed(e.toString());
		}
		catch(WebDriverException e){
			sendToLogFile("Info", "WebDriverException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(Exception e)
		{
			stepFailed(e.toString());
		}
		if(i==pageElementLoadWaitTime)
		{
			stepFailed("Error moving to 'Forward' page");
		}
       
      }
       
  }

  public void clickAlertOk()
  {
      for(int i=1;i<=pageElementLoadWaitTime;i++)
      {
	  try
        {
              Alert alert = driver.switchTo().alert();
              alert.accept();
              //sendToLogFile("INFO","Sucessfully clicked on Alert OK button");
              //testReporter("Green", "Click on Alert OK button");
              stepPassed("Click Alert OK button");
              break;
        }
	  catch(InvalidSelectorException e)
		{
			sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(StaleElementReferenceException e)
		{
			sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(NoSuchElementException e)
		{

			sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
			waitTimeForException(1);
		}
		
		catch(UnreachableBrowserException e)
		{
			stepFailed(e.toString());
		}
		catch(WebDriverException e){
			sendToLogFile("Info", "WebDriverException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(Exception e)
		{
			stepFailed(e.toString());
		}
		if(i==pageElementLoadWaitTime)
		{
			stepFailed("Unable to click Alert OK button");
		}
       
      }
      
  }
  
  public  void clickAlertCancel()
  {
      for(int i=1;i<=pageElementLoadWaitTime;i++)
      {
    	  
	  try
        {
              Alert alert = driver.switchTo().alert();
              alert.dismiss();
              //sendToLogFile("INFO","Sucessfully clicked on Alert Cancel button");
              //testReporter("Green", "Click on Alert Cancel button");
              stepPassed("Click Alert Cancel button");
              break;
        }
	  catch(InvalidSelectorException e)
		{
			sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(StaleElementReferenceException e)
		{
			sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(NoSuchElementException e)
		{

			sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
			waitTimeForException(1);
		}
		
		catch(UnreachableBrowserException e)
		{
			stepFailed(e.toString());
		}
		catch(WebDriverException e){
			sendToLogFile("Info", "WebDriverException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(Exception e)
		{
			stepFailed("Exception Error '"+e.toString()+"'");
		}
		if(i==pageElementLoadWaitTime)
		{
			stepFailed("Unable to click Alert Cancel button");
		}
       
      }
  }
  public  boolean isAlertWindowPresent() 
  { 
      try 
      { 
          driver.switchTo().alert(); 
          return true; 
      }  
      
      catch (Exception E) 
      { 
          return false; 
      }   
  } 	
  
  public  void verifyElementIsPresent(String objLocator) 
  {	
	 
				waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
				for(int i=1;i<=pageElementLoadWaitTime;i++)
				{
		  	try
		  		{
		  			//sendToLogFile("INFO", "Element '"+locatorDescription+"' is present as expected");
		  			//testReporter("Green", "Verify Element : "+locatorDescription);
		  			stepPassed("Verified the '"+locatorDescription+"' element  is present");
		  			break;
		  		}
		  	catch(InvalidSelectorException e)
			{
				sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
				waitTimeForException(1);
			}
			catch(StaleElementReferenceException e)
			{
				sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
				waitTimeForException(1);
			}
			catch(NoSuchElementException e)
			{

				sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
				waitTimeForException(1);
			}
			
			catch(UnreachableBrowserException e)
			{
				stepFailed(e.toString());
			}
			catch(WebDriverException e){
				sendToLogFile("Info", "WebDriverException occured. Retrying..............");
				waitTimeForException(1);
			}
			catch(Exception e)
			{
				stepFailed("Exception Error '"+e.toString()+"'");
			}
			if(i==pageElementLoadWaitTime)
			{
				stepFailed("The '"+locatorDescription+"' element  is not present");
			}
	       
	      }
		  	
		
}

public void mouseOver(String objLocator) 
{
	  
				waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
	for(int i=1;i<=pageElementLoadWaitTime;i++)
	{
	try
	{
      Actions builder = new Actions(driver);
      builder.moveToElement(webElement).build().perform();
      //sendToLogFile("INFO", "Successfully moved mouse over '" + locatorDescription+"'");
      //testReporter("Green", "Move the mouse over '" + locatorDescription+"'");
      stepPassed("Mouse over  to '" + locatorDescription+"' element");
      break;
	}
	catch(InvalidSelectorException e)
	{
		sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
		waitTimeForException(1);
	}
	catch(StaleElementReferenceException e)
	{
		sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
		waitTimeForException(1);
	}
	catch(NoSuchElementException e)
	{

		sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
		waitTimeForException(1);
	}
	
	catch(UnreachableBrowserException e)
	{
		stepFailed(e.toString());
	}
	catch(WebDriverException e){
		sendToLogFile("Info", "WebDriverException occured. Retrying..............");
		waitTimeForException(1);
	}
	catch(Exception e)
	{
		stepFailed("Exception Error '"+e.toString()+"'");
	}
	if(i==pageElementLoadWaitTime)
	{
		 stepFailed("Unable to mouse over  to '" + locatorDescription+"' element");
	}
   
  }

}

  	public void sleep(long waittime)
	{
		sendToLogFile("INFO","Waiting for "+waittime+" seconds...");
		try {
			Thread.sleep(waittime*1000);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			sendToLogFile("ERROR","Thread.sleep operation failed, during waitTime function call");
		}
	}
  	public  void selectValueOptionFromDropdown(String objLocator,String valueToSelect) 
	{
				waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
				try{
					driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
			
		for(int i=1;i<=pageElementLoadWaitTime;i++)
		{
				try
		{
			//webElement.click();
			Select select = new Select(webElement);
			select.selectByValue(valueToSelect);
			
				//sendToLogFile("INFO","Successfully Selected "+valueToSelect+ " from : "+locatorDescription);
				//testReporter("Green", "Select '" + valueToSelect+"' from : "+locatorDescription);
				stepPassed("Select '" + valueToSelect+"' option  from : "+locatorDescription+"'");
				break;
			
		}
				catch(InvalidSelectorException e)
				{
					sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(StaleElementReferenceException e)
				{
					sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(NoSuchElementException e)
				{

					sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
					waitTimeForException(1);
				}
				
				catch(UnreachableBrowserException e)
				{
					stepFailed(e.toString());
				}
				catch(WebDriverException e){
					sendToLogFile("Info", "WebDriverException occured. Retrying..............");
					waitTimeForException(1);
				}
		catch(Exception e)
		{
			stepFailed("Exception Error '"+e.toString()+"'");
		}
		if(i==pageElementLoadWaitTime)
		{
			sendToLogFile("Error","Could not select '"+valueToSelect+ "' from : "+locatorDescription);		
            stepFailed("Unable to Select '" + valueToSelect+"' option  from : "+locatorDescription+"' drop down");
		}
	   
	  }
				}
				catch(Exception e)
				{
					stepFailed(e.toString());
				}
				finally
				{
					//Re-setting the implicit wait is set for the life of the WebDriver object instance
					driver.manage().timeouts().implicitlyWait(driverWaitTime, TimeUnit.SECONDS);
				}
	}
	
	public  void selectTextOptionFromDropdown(String objLocator,String visibleValueToSelect) 
	{
				waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
				try{
					driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
			
		for(int i=1;i<=pageElementLoadWaitTime;i++)
		{
				try
		{
			//webElement.click();
			Select select = new Select(webElement);
			select.selectByVisibleText(visibleValueToSelect);
			
				//sendToLogFile("INFO","Successfully Selected "+valueToSelect+ " from : "+locatorDescription);
				//testReporter("Green", "Select '" + valueToSelect+"' from : "+locatorDescription);
				stepPassed("Select '" + visibleValueToSelect+"' option  from : "+locatorDescription+"'");
				break;
			
		}
				catch(InvalidSelectorException e)
				{
					sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(StaleElementReferenceException e)
				{
					sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(NoSuchElementException e)
				{

					sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
					waitTimeForException(1);
				}
				
				catch(UnreachableBrowserException e)
				{
					stepFailed(e.toString());
				}
				catch(WebDriverException e){
					sendToLogFile("Info", "WebDriverException occured. Retrying..............");
					waitTimeForException(1);
				}
		catch(Exception e)
		{
			stepFailed("Exception Error '"+e.toString()+"'");
		}
		if(i==pageElementLoadWaitTime)
		{
			sendToLogFile("Error","Could not select '"+visibleValueToSelect+ "' from : "+locatorDescription);		
            stepFailed("Unable to Select '" + visibleValueToSelect+"' option  from : "+locatorDescription+"' drop down");
		}
	   
	  }
				}
				catch(Exception e)
				{
					stepFailed(e.toString());
				}
				finally
				{
					//Re-setting the implicit wait is set for the life of the WebDriver object instance
					driver.manage().timeouts().implicitlyWait(driverWaitTime, TimeUnit.SECONDS);
				}
	}
	
	
	
	public  void selectOptionNoFromDropdown(String objLocator,int indexNumber) 
	{

		waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
		try{
			driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);
	
for(int i=1;i<=pageElementLoadWaitTime;i++)
{
		try
{
	//webElement.click();
	Select select = new Select(webElement);
	select.selectByIndex(indexNumber);
	//String selectedOption=getTextSelectedOption(objLocator);
	//sendToLogFile("INFO","Successfully Selected "+indexNumber+ " option from : "+locatorDescription);
	//testReporter("Green", "Select '" + indexNumber+"' option from : "+locatorDescription);
	stepPassed("Select '" + indexNumber+"' option from '"+locatorDescription+"'");
	break;
	
}
		catch(InvalidSelectorException e)
		{
			sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(StaleElementReferenceException e)
		{
			sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(NoSuchElementException e)
		{

			sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
			waitTimeForException(1);
		}
		
		catch(UnreachableBrowserException e)
		{
			stepFailed(e.toString());
		}
		catch(WebDriverException e){
			sendToLogFile("Info", "WebDriverException occured. Retrying..............");
			waitTimeForException(1);
		}
catch(Exception e)
{
	stepFailed("Exception Error '"+e.toString()+"'");
}
if(i==pageElementLoadWaitTime)
{
	sendToLogFile("Error","Could not select '"+indexNumber+ "' from : "+locatorDescription);		
    stepFailed("Unable to select '" +indexNumber+"' from : '"+locatorDescription+"' element");
}

}
		}
		catch(Exception e)
		{
			stepFailed(e.toString());
		}
		finally
		{
			//Re-setting the implicit wait is set for the life of the WebDriver object instance
			driver.manage().timeouts().implicitlyWait(driverWaitTime, TimeUnit.SECONDS);
		}
	}
	
    public  void verifyCurrentPageTitle(String partialTitle)
    {
        for(int i=1;i<=pageElementLoadWaitTime;i++)
        {
    	try{
    	if (driver.getTitle().contains(partialTitle)) 
        {
          //sendToLogFile("INFO","'"+partialTitle+"' is present in the page title : "+driver.getTitle());
         // testReporter("Green", "Verify if the page title contains text '"+partialTitle+"'");
    		stepPassed("Verified the page title contains text '"+partialTitle+"'");
          break;
        }
        else {
            sendToLogFile("ERROR","'"+partialTitle+"' is not present in the page title : "+driver.getTitle());            
            stepFailed("The page title does not contains text '"+partialTitle+"'");}
    	break;
    	}
    	catch(InvalidSelectorException e)
		{
			sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(StaleElementReferenceException e)
		{
			sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(NoSuchElementException e)
		{

			sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
			waitTimeForException(1);
		}
		
		catch(UnreachableBrowserException e)
		{
			stepFailed(e.toString());
		}
		catch(WebDriverException e){
			sendToLogFile("Info", "WebDriverException occured. Retrying..............");
			waitTimeForException(1);
		}
    	catch(Exception e)
    	{
    		stepFailed("Exception Error '"+e.toString()+"'");
    	}
    	
    		if(i==pageElementLoadWaitTime)
			{
				stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
			}
        }
  }

	

	
	public  void verifyElementLinkText(String txt)
	{
		try
		{
		driver.findElement(By.linkText(txt));
        //sendToLogFile("INFO","The link '"+txt+"' is present");
        //testReporter("Green", "Verify if link '"+txt+"' is present");
		stepPassed("Verified the link '"+txt+"' is present");
	
		}
		
		catch (Exception e)
		{
	        sendToLogFile("ERROR","The link '"+txt+"' is not present");	        
            stepFailed("The  link '"+txt+"' is not present");			
		}
	}
	
	public  void verifyElementAttribute(String objLocator, String attributeType, String expectedAttributeValue)
	{
		 
			  waitForElement(objLocator, pageElementLoadWaitTime);
			for(int i=1;i<=pageElementLoadWaitTime;i++)
			{
			  try{
				String attribute="";
				 attribute = webElement.getAttribute(attributeType);
				 
				 if (!attribute.trim().equalsIgnoreCase(expectedAttributeValue.trim()) ) 
				 {
				
	                sendToLogFile("Error", "Error retrieving '"+attribute+"' and/or value '"+expectedAttributeValue+"'");
	                stepFailed("The attribute value "+attribute+" is not present in '"+locatorDescription+"'element");
	                break;
				 }
				 else
				 {
					 //sendToLogFile("INFO", "'"+attribute+"' has the value '"+expectedAttributeValue+"' as expected");
					// testReporter("Green", "Verify attribute of '"+attribute+"' for value '"+expectedAttributeValue+"'");
					 stepPassed("Verified the attribute value "+attribute+" is present in '"+locatorDescription+"'element");
					 break;
				 }
			  }
			  catch(InvalidSelectorException e)
				{
					sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(StaleElementReferenceException e)
				{
					sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(NoSuchElementException e)
				{

					sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
					waitTimeForException(1);
				}
				
				catch(UnreachableBrowserException e)
				{
					stepFailed(e.toString());
				}
				catch(WebDriverException e){
					sendToLogFile("Info", "WebDriverException occured. Retrying..............");
					waitTimeForException(1);
				}
		    	catch(Exception e)
		    	{
		    		stepFailed("Exception Error '"+e.toString()+"'");
		    	}
		    	
		    		if(i==pageElementLoadWaitTime)
					{
						stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
					}
			  }
			  
				 
  }


	
	public  void waitForPageText(String txt)
	{
		waitForPageText(txt,pageTextLoadWaitTime);
	}
	
	public  void waitForPageText(String txt, int timeout)
	{
		int second;
        for (second = 0;second<timeout; second++)
        { 
        	/*try{
        		driver.getCurrentUrl();
        	}
        	catch(Exception e){stepFailed("WebDriver is not found");}*/
        	if (second == timeout-1) {
                  sendToLogFile("Error","Text is not found ' "+txt+"' within "+pageTextLoadWaitTime+" seconds timeouts");                    
     		   	 	stepFailed("'"+txt+"' text is not found  within "+pageTextLoadWaitTime+" seconds timeouts");
                    break;
              }
              try { 
            	  	
                    if (driver.getPageSource().contains(txt)){ 
                          sendToLogFile("INFO","Text: '"+txt+"' is present");
                          break; 
                          } 
              }catch (Exception e) {
              }
              try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
  }
  
  

	public void clickAndHoldElement(String objLocator)
	{
				waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
		for(int i=1;i<=pageElementLoadWaitTime;i++)
		{
				try
		{
	      Actions builder = new Actions(driver);
	      builder.clickAndHold(webElement).build().perform();
	      sendToLogFile("INFO", "Successfully clicked and hold '" + locatorDescription+"'");
	      stepPassed("click and hold  '" + locatorDescription+"' element");
	      break;
		}
				catch(InvalidSelectorException e)
				{
					sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(StaleElementReferenceException e)
				{
					sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(NoSuchElementException e)
				{

					sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
					waitTimeForException(1);
				}
				
				catch(UnreachableBrowserException e)
				{
					stepFailed(e.toString());
				}
				catch(WebDriverException e){
					sendToLogFile("Info", "WebDriverException occured. Retrying..............");
					waitTimeForException(1);
				}
    	catch(Exception e)
    	{
    		stepFailed("Exception Error '"+e.toString()+"'");
    	}
    	
    		if(i==pageElementLoadWaitTime)
			{
    			stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
			}
        }
	}
  
	public  void clickElement(String objLocator)
	{
		 waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
		for(int i=1;i<=pageElementLoadWaitTime;i++)
		{
			try
			{
			  		/*	if(currentExecutionName.toLowerCase().contains("iphone") || currentExecutionName.toLowerCase().contains("ipad"))
			  			{
			  				Actions actions = new Actions(driver);
			  				actions.moveToElement(webElement);
			  				actions.click().build().perform();
			  				stepPassed("Click the '"+locatorDescription+"' element");
			  					
			  			}
			  			else{*/
							
			  				webElement.click();
			  				stepPassed("Click the '"+locatorDescription+"'");
		  					//stepPassed();
			  			//}
	        break;
			}
		  		catch(InvalidSelectorException e)
				{
					sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(StaleElementReferenceException e)
				{
					sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(NoSuchElementException e)
				{

					sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
					waitTimeForException(1);
				}
				
				catch(UnreachableBrowserException e)
				{
					stepFailed(e.toString());
				}
				catch(WebDriverException e){
					sendToLogFile("Info", "WebDriverException occured. Retrying..............");
					waitTimeForException(1);
				}
    	catch(Exception e)
    	{
    		stepFailed("Exception Error '"+e.toString()+"'");
    	}
    	
    		if(i==pageElementLoadWaitTime)
			{
				stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
			}
        }
		
	
	}
	public void waitForNativeElementToDisplay(String objLocator,int timeout)
	{
		boolean webElementStatus=false;
		try{
			driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);

			for(int i=1;i<=timeout;i++)
			{
			try{
				//setting implicit wait for  element found
				if(!webElementStatus)
				{
					findWebElement(objLocator);
					
				//	((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",webElement);
				
					webElementStatus=true;
				}
				if(webElement.isDisplayed())
				{
					break;
				}
				else
				{
					sleep(1);
				}
			}
			catch(InvalidSelectorException e)
			{
				sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
				waitTimeForException(1);
			}
			catch(StaleElementReferenceException e)
			{
				sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
				waitTimeForException(1);
			}
			catch(NoSuchElementException e)
			{

				sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
				waitTimeForException(1);
			}
			
			catch(UnreachableBrowserException e)
			{
				stepFailed(e.toString());
			}
			catch(WebDriverException e){
				sendToLogFile("Info", "WebDriverException occured. Retrying..............");
				waitTimeForException(1);
			}
			if(i==timeout)
			{
				if(webElementStatus)
				{
					stepFailed(locatorDescription+" element is present but its not in clickable/editable state within '"+timeout+"' seconds timeout");
					
				}
				else
				{
					stepFailed(locatorDescription+ " element not found in '"+timeout+"' seconds timeout ");
				}
			}
			}
			}
			catch(Exception e)
			{
				stepFailed("Exception error '"+e.toString()+"'");
				
			}
			finally
			{
				webElementStatus=false;
				//Re-setting the implicit wait is set for the life of the WebDriver object instance
				driver.manage().timeouts().implicitlyWait(driverWaitTime, TimeUnit.SECONDS);
			}
			
		
		
	}
	public  void clickNativeElement(String objLocator)
	{
		waitForNativeElementToDisplay(objLocator, pageElementLoadWaitTime);
		for(int i=1;i<=pageElementLoadWaitTime;i++)
		{
			try
			{
			  		/*	if(currentExecutionName.toLowerCase().contains("iphone") || currentExecutionName.toLowerCase().contains("ipad"))
			  			{
			  				Actions actions = new Actions(driver);
			  				actions.moveToElement(webElement);
			  				actions.click().build().perform();
			  				stepPassed("Click the '"+locatorDescription+"' element");
			  					
			  			}
			  			else{*/
							
			  				webElement.click();
			  				stepPassed("Click the '"+locatorDescription+"'");
		  					//stepPassed();
			  			//}
	        break;
			}
		  		catch(InvalidSelectorException e)
				{
					sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(StaleElementReferenceException e)
				{
					sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(NoSuchElementException e)
				{

					sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
					waitTimeForException(1);
				}
				
				catch(UnreachableBrowserException e)
				{
					stepFailed(e.toString());
				}
				catch(WebDriverException e){
					sendToLogFile("Info", "WebDriverException occured. Retrying..............");
					waitTimeForException(1);
				}
    	catch(Exception e)
    	{
    		stepFailed("Exception Error '"+e.toString()+"'");
    	}
    	
    		if(i==pageElementLoadWaitTime)
			{
				stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" seconds timeouts");
			}
        }
		
	
	}
	public void switchWindow(String objTitle) {
		try
		{
			Set<String> AllHandle = driver.getWindowHandles();
	        for (String han : AllHandle) {
	              driver.switchTo().window(han);
	              try {
	                    Thread.sleep(2000);
	              } catch (InterruptedException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	              }
	              String getTitle = driver.getTitle();
	              if (getTitle.contains(objTitle)) {
	            	  stepPassed("Switch to window :"+objTitle);
	                  
	            	  break;
	              }
	        }
			
		}
		catch(Exception e)
		{
			
         	 sendToLogFile("Error","Error switching to window..."+objTitle);         	
		   	 stepFailed("Switch to window :"+objTitle);

		}
        return ;
  }

    public  void dragAndDrop(String sourceObjLocator, String destinationObjLocator) {
    	String sourceDesc="",destinationDesc="";
    	try
    	{
    	findWebElement(sourceObjLocator);
    	WebElement source = webElement;
    	sourceDesc=locatorDescription;
    	findWebElement(destinationObjLocator);
        WebElement target = webElement;
    	destinationDesc=locatorDescription;
    	// (new Actions(driver)).dragAndDrop(source, ).perform();
       // (new Actions(driver)).dragAndDrop(source, target).perform();
   	 //sendToLogFile("Info","Drag and drop successful");
   	 //testReporter("Green", "Drag '"+sourceDesc+"' and drop on '"+destinationDesc+"'");
        stepPassed("Drag '"+sourceDesc+"' and drop on '"+destinationDesc+"'");
    	}
    	catch (Exception e)
    	{
        	 sendToLogFile("Error","Error during drag and drop");        	
		   	 stepFailed("Drag '"+sourceDesc+"' and drop on '"+destinationDesc+"'");
    		
    	}
  }
    
    public void switchDefaultFrame()
    {
    	try
    	{
    		driver.switchTo().defaultContent();
    	}
    	catch(Exception e)
    	{
    		stepFailed(e.toString());
    	}
    }
    
	
	public  void switchFrame(String objectLocator)
	{
		findWebElement(objectLocator);
		for(int i=1;i<=pageElementLoadWaitTime;i++)
		{
		try
		{
		driver.switchTo().frame(webElement);
	   	 break;
		}
		catch(InvalidSelectorException e)
		{
			sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(StaleElementReferenceException e)
		{
			sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(NoSuchElementException e)
		{

			sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
			waitTimeForException(1);
		}
		
		catch(UnreachableBrowserException e)
		{
			stepFailed(e.toString());
		}
		catch(WebDriverException e){
			sendToLogFile("Info", "WebDriverException occured. Retrying..............");
			waitTimeForException(1);
		}
    	catch(Exception e)
    	{
    		stepFailed("Exception Error '"+e.toString()+"'");
    	}
    	
    		if(i==pageElementLoadWaitTime)
			{
    			sendToLogFile("Error","Error Switching to frame :"+locatorDescription);		   	 
   		   	 stepFailed("Switch to frame :"+locatorDescription);
			}
        }
	}
	
	
	
	public  void clearTextBox(String objLocator)
	{	
		
				waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
				for(int i=1;i<=pageElementLoadWaitTime;i++)
				{
		try
		  {
			webElement.click();
	        webElement.clear();
	        sendToLogFile("INFO", "Clear Text Box '"+ "' in : " + locatorDescription);
	        break;
		  }
		catch(InvalidSelectorException e)
		{
			sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(StaleElementReferenceException e)
		{
			sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(NoSuchElementException e)
		{

			sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
			waitTimeForException(1);
		}
		
		catch(UnreachableBrowserException e)
		{
			stepFailed(e.toString());
		}
		catch(WebDriverException e){
			sendToLogFile("Info", "WebDriverException occured. Retrying..............");
			waitTimeForException(1);
		}
    	catch(Exception e)
    	{
    		stepFailed("Exception Error '"+e.toString()+"'");
    	}
    	
    		if(i==pageElementLoadWaitTime)
			{
    			stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
			}
        }
		  
	}
	public  void rightClick(String objLocator)
	{
		 
				waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
				for(int i=1;i<=pageElementLoadWaitTime;i++)
				{
			 try{
				 
				 Actions builder = new Actions(driver);
			 	builder.contextClick(webElement).build().perform();
			 	//sendToLogFile("INFO", "Successfully right clicked '" + locatorDescription+"'");
			 	//testReporter("Green", "Right Clicked '" + locatorDescription+"'");
			 	stepPassed("Right Click '" + locatorDescription+"' element");
			 	break;
			 }
			 catch(InvalidSelectorException e)
				{
					sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(StaleElementReferenceException e)
				{
					sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(NoSuchElementException e)
				{

					sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
					waitTimeForException(1);
				}
				
				catch(UnreachableBrowserException e)
				{
					stepFailed(e.toString());
				}
				catch(WebDriverException e){
					sendToLogFile("Info", "WebDriverException occured. Retrying..............");
					waitTimeForException(1);
				}
		    	catch(Exception e)
		    	{
		    		stepFailed("Exception Error '"+e.toString()+"'");
		    	}
		    	
		    		if(i==pageElementLoadWaitTime)
					{
		    			stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
					}
		        }
	}
	public  void doubleClick(String objLocator)
	{
				waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
		for(int i=1;i<=pageElementLoadWaitTime;i++)
		{
				try
		{
	      Actions builder = new Actions(driver);
	      builder.doubleClick(webElement).build().perform();
	      //sendToLogFile("INFO", "Successfully double clicked '" + locatorDescription+"'");
	      //testReporter("Green", "Double Clicked '" + locatorDescription+"'");
	      stepPassed("Double Click '" + locatorDescription+"' the element");
	      break;
		}
				catch(InvalidSelectorException e)
				{
					sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(StaleElementReferenceException e)
				{
					sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(NoSuchElementException e)
				{

					sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
					waitTimeForException(1);
				}
				
				catch(UnreachableBrowserException e)
				{
					stepFailed(e.toString());
				}
				catch(WebDriverException e){
					sendToLogFile("Info", "WebDriverException occured. Retrying..............");
					waitTimeForException(1);
				}
    	catch(Exception e)
    	{
    		stepFailed("Exception Error '"+e.toString()+"'");
    	}
    	
    		if(i==pageElementLoadWaitTime)
			{
    			stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
			}
        }
	}
	public  void mouseClick(String objLocator)
	{
				waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
		for(int i=1;i<=pageElementLoadWaitTime;i++)
		{
				try
		{
	      Actions builder = new Actions(driver);
	      builder.contextClick(webElement).build().perform();
	      //sendToLogFile("INFO", "Successfully double clicked '" + locatorDescription+"'");
	      //testReporter("Green", "Double Clicked '" + locatorDescription+"'");
	      stepPassed("Mouse Click '" + locatorDescription+"' the element");
	      break;
		}
				catch(InvalidSelectorException e)
				{
					sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(StaleElementReferenceException e)
				{
					sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(NoSuchElementException e)
				{

					sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
					waitTimeForException(1);
				}
				
				catch(UnreachableBrowserException e)
				{
					stepFailed(e.toString());
				}
				catch(WebDriverException e){
					sendToLogFile("Info", "WebDriverException occured. Retrying..............");
					waitTimeForException(1);
				}
    	catch(Exception e)
    	{
    		stepFailed("Exception Error '"+e.toString()+"'");
    	}
    	
    		if(i==pageElementLoadWaitTime)
			{
    			stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
			}
        }
	}
	public boolean isElementPresent(String objectLocator)
	{
	      
		try
	       {
					findWebElement(objectLocator);
					return true;
	       }
		catch(NoSuchElementException e)
		{

			return false;
		}
	       catch (Exception e)
	       {
	            stepFailed(e.toString());
	            return false;
	       }

	}
	public  void verifyPageShouldContainText(String text)
	{
	      
	             if(driver.getPageSource().contains(text))
	             {

	            	 stepPassed("Verified the'"+text+"' text is present");
	             } 
	             else
	             {
	            	 sendToLogFile("ERROR",""+text+"' text is not present");	            	 
	            	 stepFailed("The'"+text+"' text is not present");
	             }
	     
	}
	public  void verifyPageShouldNotContainText(String text)
	{
	      
	             if(driver.getPageSource().contains(text))
	             {
	            	 sendToLogFile("ERROR","'"+text+"' text is present");	            	
	            	 stepFailed("Verify if page '"+text+"' text is not present");
	             } 
	             else
	             {
	            	 //sendToLogFile("INFO", "Verify if page '"+text+"' text is not present");
	            	 //testReporter("Green", "Verify if page '"+text+"' text is not present");
	            	 stepPassed("Verify if page '"+text+"' text is not present");
	            	
	             }
	     
	}

	
	
	public  String getElementTextSelectedOption(String objLocator)
	{
		
			 waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
			 String SelectText="";
			 for(int i=1;i<=pageElementLoadWaitTime;i++)
			 {
			 try{
			 Select select = new Select(webElement);
			 SelectText=select.getFirstSelectedOption().getText().toString();
			 return SelectText;
			 }
			 catch(InvalidSelectorException e)
				{
					sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(StaleElementReferenceException e)
				{
					sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
					waitTimeForException(1);
				}
				catch(NoSuchElementException e)
				{

					sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
					waitTimeForException(1);
				}
				
				catch(UnreachableBrowserException e)
				{
					stepFailed(e.toString());
				}
				catch(WebDriverException e){
					sendToLogFile("Info", "WebDriverException occured. Retrying..............");
					waitTimeForException(1);
				}
		    	catch(Exception e)
		    	{
		    		stepFailed("Exception Error '"+e.toString()+"'");
		    	}
		    	
		    		if(i==pageElementLoadWaitTime)
					{
		    			stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
					
					}
		        }
			 return SelectText;
		 
			
	}
	
   
   public void verifyAlertWindowTextShouldContain(String expectedAlertText)
   {
   	 Alert alert =driver.switchTo().alert();		 
   	 String alertText=alert.getText();
   	 if(alertText.contains(expectedAlertText))
   	 {
   		// sendToLogFile("INFO","Verify if '"+expectedAlertText+"' alert text is present ");
   	     //testReporter("green", "Verify if '"+expectedAlertText+"' alert text is present ");  
   		 stepPassed("Verified the alert window text '"+expectedAlertText+"' is present ");
   		 
   	 }
   	 else
   	 {
   		 sendToLogFile("Error","'"+expectedAlertText+"' alert text is not present ");   		
   	     stepFailed("'"+expectedAlertText+"' alert text is not present");
   	 }
   
   }
   public void verifyTextFieldValueShouldContain(String objectLocator,String expectedValue)
   {
		   waitForElement(objectLocator, pageElementLoadWaitTime);
		   
			   for(int i=1;i<=pageElementLoadWaitTime;i++)
			   {
				   try{
		   String actualValue=getElementAttributeValue(objectLocator, "value");
		   if(actualValue.contains(expectedValue))
		   {
			   //sendToLogFile("INFO","Verify if '"+expectedValue+"' is present in "+locatorDescription+" textfield");
		   	   //testReporter("green", "Verify if '"+expectedValue+"' is present in "+locatorDescription+" textfield");
			   stepPassed( "Verified the '"+expectedValue+"' is present in "+locatorDescription+" textfield");
		   	   break;
		   }
		   else
		   {
			   	 sendToLogFile("Error","'"+expectedValue+"' is not present in "+locatorDescription+" textfield");		   		
		   	     stepFailed("'"+expectedValue+"' is not present in "+locatorDescription+" textfield");
		   	     break;
		   }
		   }
				   catch(InvalidSelectorException e)
					{
						sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
						waitTimeForException(1);
					}
					catch(StaleElementReferenceException e)
					{
						sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
						waitTimeForException(1);
					}
					catch(NoSuchElementException e)
					{

						sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
						waitTimeForException(1);
					}
					
					catch(UnreachableBrowserException e)
					{
						stepFailed(e.toString());
					}
					catch(WebDriverException e){
						sendToLogFile("Info", "WebDriverException occured. Retrying..............");
						waitTimeForException(1);
					}
		    	catch(Exception e)
		    	{
		    		stepFailed("Exception Error '"+e.toString()+"'");
		    	}
		    	
		    		if(i==pageElementLoadWaitTime)
					{
		    			stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
					
					}
		   }
   }
   public void verifyTextFieldValueShouldNotContain(String objectLocator,String expectedValue)
   {
	  
		   	waitForElement(objectLocator, pageElementLoadWaitTime);
		   for(int i=1;i<=pageElementLoadWaitTime;i++)
		   {
		   	try{
			   
		   	String textValue=getElementAttributeValue(objectLocator, "value");
		   if(!textValue.contains(expectedValue))
		   {
			  // sendToLogFile("INFO","Verify if '"+expectedValue+"' is not present in "+locatorDescription+" textfield");
		   	  // testReporter("green", "Verify if '"+expectedValue+"' is not present in "+locatorDescription+" textfield");
			   stepPassed("Verified the '"+expectedValue+"' is not present in "+locatorDescription+" textfield");
		   	   break;
		   }
		   else
		   {
			   	 sendToLogFile("Error","'"+expectedValue+"' is present in "+locatorDescription+" textfield");		   		 
		   	     stepFailed("'"+expectedValue+"' is present in "+locatorDescription+" textfield");
		   	     break;
		   }
   }
		   	catch(InvalidSelectorException e)
			{
				sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
				waitTimeForException(1);
			}
			catch(StaleElementReferenceException e)
			{
				sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
				waitTimeForException(1);
			}
			catch(NoSuchElementException e)
			{

				sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
				waitTimeForException(1);
			}
			
			catch(UnreachableBrowserException e)
			{
				stepFailed(e.toString());
			}
			catch(WebDriverException e){
				sendToLogFile("Info", "WebDriverException occured. Retrying..............");
				waitTimeForException(1);
			}
	    	catch(Exception e)
	    	{
	    		stepFailed("Exception Error '"+e.toString()+"'");
	    	}
	    	
	    		if(i==pageElementLoadWaitTime)
				{
	    			stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
				
				}
	   }
	
   }
	


	public  void closeAllBrowser() 
	{
		deleteAllCookies();
		Set<String> windowhandles = driver.getWindowHandles();
		for (String handle : windowhandles) 
		{
			driver.switchTo().window(handle);
			driver.close();
		}
		stepPassed("Close all browser");
	}
	public  void closeChildBrowser(String windowTitle)
	{
	    try{
	    	for(String winHandle : driver.getWindowHandles())
	        {
	       	 driver.switchTo().window(winHandle);
	       	 if(driver.getTitle().equalsIgnoreCase(windowTitle))
	       	 {
	       		 driver.close();
	       		 //sendToLogFile("INFO","Browser: Close Successful");
	       		 //testReporter("Green","Close Browser");
	       		 stepPassed("Close Browser '"+windowTitle+"'");
	       		 break;
	       	 }
	        }
	    }
	    catch(Exception e)
	    {
	    	sendToLogFile("ERROR","Browser: Close Failure");			
            stepFailed("Close Browser");
	    }
		

	}
	
	
	

	public  String getElementText(String objLocator) {

		String getText = null;
		 for(int i=1;i<=pageElementLoadWaitTime;i++)
		 {
		try {
			waitForElement(objLocator, pageElementLoadWaitTime);
			getText = webElement.getText();
			sendToLogFile("Info", "Sucessfully got the text '" + getText + "'");
			break;
		} 
		catch(InvalidSelectorException e)
		{
			sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(StaleElementReferenceException e)
		{
			sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(NoSuchElementException e)
		{

			sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
			waitTimeForException(1);
		}
		
		catch(UnreachableBrowserException e)
		{
			stepFailed(e.toString());
		}
		catch(WebDriverException e){
			sendToLogFile("Info", "WebDriverException occured. Retrying..............");
			waitTimeForException(1);
		}
    	catch(Exception e)
    	{
    		stepFailed("Exception Error '"+e.toString()+"'");
    	}
    	
    		if(i==pageElementLoadWaitTime)
			{
    			stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
			
			}
   }
		return getText;
		
	}
	
	public String getElementAttributeValue(String objLocator,String attributeName) {

		String getAttributeValue = null;
		  for(int i=1;i<=pageElementLoadWaitTime;i++)
		  {
		try {
			waitForElement(objLocator, pageElementLoadWaitTime);
			getAttributeValue = webElement.getAttribute(attributeName);
			sendToLogFile("Info", "Sucessfully got the attribute value '" + getAttributeValue + "'");
			break;
		}
		catch(InvalidSelectorException e)
		{
			sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(StaleElementReferenceException e)
		{
			sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(NoSuchElementException e)
		{

			sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
			waitTimeForException(1);
		}
		
		catch(UnreachableBrowserException e)
		{
			stepFailed(e.toString());
		}
		catch(WebDriverException e){
			sendToLogFile("Info", "WebDriverException occured. Retrying..............");
			waitTimeForException(1);
		}
    	catch(Exception e)
    	{
    		stepFailed("Exception Error '"+e.toString()+"'");
    	}
    	
    		if(i==pageElementLoadWaitTime)
			{
    			stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
			
			}
   }
		return getAttributeValue;
		 	
	}

	public  int getElementMatchingXpathCount(String objLocator) {

        List<WebElement> xpathCount = null;
        for(int i=1;i<=pageElementLoadWaitTime;i++)
        {
        try {
        		
                xpathCount = (List<WebElement>) driver.findElements(By.xpath(objLocator));
                sendToLogFile("Info", "Sucessfully got the matchingxPath Count'"+ xpathCount + "'");
                break;

        }
        catch(InvalidSelectorException e)
		{
			sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(StaleElementReferenceException e)
		{
			sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
			waitTimeForException(1);
		}
		catch(NoSuchElementException e)
		{

			sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
			waitTimeForException(1);
		}
		
		catch(UnreachableBrowserException e)
		{
			stepFailed(e.toString());
		}
		catch(WebDriverException e){
			sendToLogFile("Info", "WebDriverException occured. Retrying..............");
			waitTimeForException(1);
		}
    	catch(Exception e)
    	{
    		stepFailed("Exception Error '"+e.toString()+"'");
    	}
    	
    		if(i==pageElementLoadWaitTime)
			{
    			stepFailed(locatorDescription+" element found but its not in editable/clickable state within "+pageElementLoadWaitTime+" timeouts");
			
			}
   }
        return xpathCount.size();
		
    	
}
	
	
	
	public  void waitForAlertWindow(int timeout)
	{
		for(int i=0;i<=timeout;i++)
		{
			if(isAlertWindowPresent())
			{	
				break;
			}
			else
			{
				sleep(1);
			}
			if(i==timeout)
			{
				stepFailed("Alert Window is not present within '"+timeout+"' timeout");
			}
		
		}

	}
	public void waitForAlertWindow(String alertTitle,int timeout)
	{
		for(int i=0;i<=timeout;i++)
		{
			if(isAlertWindowPresent())
			{	
				break;
			}
			else
			{
				sleep(1);
			}
			if(i==timeout)
			{
				stepFailed(alertTitle+" alert Window is not present within '"+timeout+"' timeout");
			}
		
		}

	}
	
	public  void waitForChildWindow(String windowTitle,int timeout)
	{
		for(int i=1;i<=timeout;i++)
		{
			String loopstatus="false";
			if(i==timeout)
			{
				sendToLogFile("Info",windowTitle+ "window is not present within '"+timeout+"' timeout");         	
			   	 stepFailed(windowTitle+ "window is not present within '"+timeout+"' timeout");
			}

			Set<String> AllHandle = driver.getWindowHandles();
	        for (String han : AllHandle)
	        {
	              driver.switchTo().window(han);
	              String getTitle = driver.getTitle();
	              if (getTitle.trim().equalsIgnoreCase(windowTitle))
	              {
	               	loopstatus="true";
	            	  break;
	              }
	        }
	        if(loopstatus.equalsIgnoreCase("true"))
	        {
	        	break;
	        }
			sleep(1);
		}
	}
	
	public void waitForElementToDisplay(String objLocator,int timeout)
	{
		boolean webElementStatus=false;
		try{
			driver.manage().timeouts().implicitlyWait(0,TimeUnit.SECONDS);

			for(int i=1;i<=timeout;i++)
			{
			try{
				//setting implicit wait for  element found
				if(!webElementStatus)
				{
					findWebElement(objLocator);
					((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",webElement);
					webElementStatus=true;
				}
				if(webElement.isDisplayed())
				{
					break;
				}
				else
				{
					sleep(1);
				}
			}
			catch(InvalidSelectorException e)
			{
				sendToLogFile("Info", "InvalidSelectorException occured. Retrying..............");
				waitTimeForException(1);
			}
			catch(StaleElementReferenceException e)
			{
				sendToLogFile("Info", "StaleElementReferenceException occured. Retrying..............");
				waitTimeForException(1);
				System.out.println(e.toString());
			}
			catch(NoSuchElementException e)
			{

				sendToLogFile("Info", "NoSuchElementException occured. Retrying..............");
				waitTimeForException(1);
			}
			
			catch(UnreachableBrowserException e)
			{
				stepFailed(e.toString());
			}
			catch(WebDriverException e){
				sendToLogFile("Info", "WebDriverException occured. Retrying..............");
				waitTimeForException(1);
				System.out.println(e.toString());
			}
			if(i==timeout)
			{
				if(webElementStatus)
				{
					stepFailed(locatorDescription+" element is present but its not in clickable/editable state within '"+timeout+"' timeout");
					
				}
				else
				{
					stepFailed(locatorDescription+ " element not found within '"+timeout+"' seconds timeout ");
				}
			}
			}
			}
			catch(Exception e)
			{
				stepFailed("Exception error '"+e.toString()+"'");
				
			}
			finally
			{
				webElementStatus=false;
				//Re-setting the implicit wait is set for the life of the WebDriver object instance
				driver.manage().timeouts().implicitlyWait(driverWaitTime, TimeUnit.SECONDS);
			}
			
		
		
	}
	
	public void clickOnHiddenElement(String objectLocator)
	 {
	  try{
		  waitForElement(objectLocator, pageElementLoadWaitTime);
	   JavascriptExecutor executor = (JavascriptExecutor)driver;
	   executor.executeScript("arguments[0].click();", webElement);
	   //testReporter("green","Click on :"+locatorDescription);
	  stepPassed("Click on :"+locatorDescription);   
	  }
	      catch(Exception e)
	      {
	       stepFailed("Click on :"+locatorDescription);
	      }
	 }
	
	public boolean isElementDisplayed(String objectLocator)
	{
		 	 findWebElement(objectLocator);
		
	          if(webElement.isDisplayed())
	          {
	        	  return true;
	          }
	          else
	          {
	        	  return false; 
	          }
		
	}
	
	public  boolean isTextPresent(String expectedText)
	{

		
	          if(driver.getPageSource().contains(expectedText))
	          {
	        	  return true;
	          }
	          else
	          {
	        	  return false; 
	          }
		
	}
	
	public void deleteAllCookies()
	{
		try{
			driver.manage().deleteAllCookies();
			sendToLogFile("INFO", "Successfully deleted all cookies");
		}
		catch(Exception e)
		{
			isWindowreadyStateStatus=false;
			stepFailed("Delete All cookies keyword exception error"+e.toString());
		}
	}
	
	public  void maximiseCurrentWindow()
	{
		
		try{
		driver.manage().window().maximize();
		sendToLogFile("INFO", "Successfully Maximised Browser Window");
		}
		catch(Exception e)
		{
			/*windowreadyStateStatus=false;
			stepFailed("Maximise window keyword exception error"+e.toString());*/
		}
	
	}
	
	 public  void selectCheckBox(String objLocator)
	 {
		 waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
		try
		{
		 if(!webElement.isSelected())
		 {
			 webElement.click();
		 }
		 stepPassed("Checked on the "+locatorDescription+" checkbox");
		}
		catch(Exception e)
		{
			stepFailed(e.toString());
		}
	 }
	 
	 public  void unSelectCheckBox(String objLocator)
	 {
		 waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
		 try
		 {
		 if(webElement.isSelected())
		 {
			 webElement.click();
		 }
		 stepPassed("Unchecked the "+locatorDescription+" checkbox");
		 }
		 catch(Exception e)
		 {
			 stepFailed(e.toString());
		 }
	 }
	 
	 public void verifyCheckBoxIsChecked(String objLocator)
	 {
		 waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
		try
		{
		 if(webElement.isSelected())
		 {
			 stepPassed("Verified that "+locatorDescription+" is checked");
		 }
		 else
		 {
			 stepFailed(locatorDescription+" is not checked");
		 }
		 
		}
		catch(Exception e)
		{
			stepFailed(e.toString());
		}
	 }
	 
	 public void verifyCheckBoxIsUnChecked(String objLocator)
	 {
		 waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
		try
		{
		 if(!webElement.isSelected())
		 {
			 stepPassed("Verified that "+locatorDescription+" is Unchecked");
		 }
		 else
		 {
			 stepFailed(locatorDescription+" is  checked");
		 }
		 
		}
		catch(Exception e)
		{
			stepFailed(e.toString());
		}
	 }
	 
	
	 public void capturePageScreenShot(String filename)
		{
			File scrFile = null;
			String scrPath=Selenium_Appium_Keywords.testReportsDirectory+"/Screenshots/"+currentTestCaseModuleName+"/"+currentTestCaseName+"/";
			File file = new File(scrPath);
		    file.mkdir();
		   // maximiseWindow();
		    if (driver.getClass().getName().equals("org.openqa.selenium.remote.RemoteWebDriver")) {
		    	driver = (RemoteWebDriver) new Augmenter().augment(driver);
		    } else {
		    	//driver = driver;
		    }
		    //Augmenter augmenter = new Augmenter(); 
		    try{
		    	scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    	 FileUtils.copyFile(scrFile, new File(scrPath+filename+".png"));
		    }
		    catch(org.openqa.selenium.UnhandledAlertException e)
		    {
		    	maximiseCurrentWindow();
		    	
		    	 windowScreenShot("TestFailure"+Selenium_Appium_Keywords.testcasefailureNo);
		    }
		    catch(Exception e)
		    {
		    	System.out.println(e.toString());
		    	//screenShot(filename);
		    	
		    }
		    finally
		    {
		    	 if (scrFile == null) {
	                 System.out.println("This WebDriver does not support screenshots");
	                //get us outa here
	             }
		    }
		    
				
		}
	
	 public  void stepFailed(String report)
		{		 
		 	isTestFailure=true;
		 	if((currentExecutionOS.equalsIgnoreCase("android")) && (getFrameworkConfigProperty("AppType").equalsIgnoreCase("hybird")))
			{
		 		switchToContext("NATIVE_APP");
			}
	       Selenium_Appium_Keywords.testcasefailureNo++;	
		   String scrPath="../../Screenshots/"+currentTestCaseModuleName+"/"+currentTestCaseName+"/";  

			    writeTestStepReport("<font style='color:red'>stepNo"+report+"</font><br/>",currentExecutionName,currentTestCaseName);		 		
		         if(!Selenium_Appium_Keywords.isWindowreadyStateStatus)
			   {
				   windowScreenShot("TestFailure"+Selenium_Appium_Keywords.testcasefailureNo);
				   Selenium_Appium_Keywords.isWindowreadyStateStatus=true;
			   }
			   else
			   {
				   capturePageScreenShot("TestFailure"+Selenium_Appium_Keywords.testcasefailureNo);
			   }
		       writeTestStepReport("<a href='"+scrPath+"/TestFailure"+Selenium_Appium_Keywords.testcasefailureNo+".png'> ",currentExecutionName,currentTestCaseName);
			   writeTestStepReport("<img src='"+scrPath+"/TestFailure"+Selenium_Appium_Keywords.testcasefailureNo+".png' height='200' width='200'/><br></br></a></font>",currentExecutionName,currentTestCaseName);
		       testCaseFailed();    
		}
	 public  void stepPassed(String message)
		{	       
		    sendToLogFile("Info", message);   
			//testReporter("Green", errMessage);
		    writeTestStepReport("<font style='color:green'>stepNo"+message+"</font><br/>",currentExecutionName,currentTestCaseName);
		}
	 
	
		public enum identifierType
		{
			xpath,
			name,
			id,
			lnktext,
			partiallinktext,
			classname,
			cssselector,
			tagname
		}
		
	 public void parseidentifyByAndlocator(String identifyByAndLocator) {
			
	        sendToLogFile("INFO", "Parsing : " + identifyByAndLocator);
			try
			{
				locatorDescription=identifyByAndLocator.substring(0,identifyByAndLocator.indexOf("@"));
				identifyByAndLocator=identifyByAndLocator.substring(identifyByAndLocator.indexOf("@") + 1);
			}
			catch (Exception e)
			{
				locatorDescription="";
			}
			finally
			{
				identifier = identifyByAndLocator.substring(0,identifyByAndLocator.indexOf("=",0)).toLowerCase();
				locator = identifyByAndLocator.substring(identifyByAndLocator.indexOf("=",0) + 1);
		        sendToLogFile("INFO", currentExecutionName+"-Identify By : " + identifier);
		        sendToLogFile("INFO", currentExecutionName+"-Locator : " + locator);
		        sendToLogFile("INFO", currentExecutionName+"Locator Description : " + locatorDescription);
		        Selenium_Appium_Keywords.identifierType = identifierType.valueOf(identifier);
			}
	  }
	 
	 public void setTimeoutsToVariables()
	 {
		
		 	pageElementLoadWaitTime=Integer.parseInt(getFrameworkConfigProperty("ElementLoadWaitTime").toString().trim());
			pageTextLoadWaitTime=Integer.parseInt(getFrameworkConfigProperty("TextLoadWaitTime").toString().trim());
			pageLoadWaitTime=Integer.parseInt(getFrameworkConfigProperty("PageLoadWaitTime").toString().trim());
			driverWaitTime=Integer.parseInt(getFrameworkConfigProperty("ImplicitlyWaitTime").toString().trim());
			sendToLogFile("INFO","Element time out set");
	 }
	
	 public void testReportStart(String machineName) 
		{
			
			
			Selenium_Appium_Keywords.sendToLogFile("INFO","##### Start of Test Case : "+currentTestCaseName+ " #####");		
			writeTestStepReport("<B></B><h1 align='center' style='color:SaddleBrown'>"+ConfigPropertiesFiles.allTcDescription.get(currentTestCaseName)+"</h1><br/>",currentExecutionName,currentTestCaseName);		
			
		}
	 
	 public void stopProcess(String process) {

			CommandLine command = new CommandLine("cmd");
			command.addArgument("/c");
			command.addArgument("taskkill");
			command.addArgument("/F");
			command.addArgument("/IM");
			command.addArgument(process);

			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(1);

			try {
				executor.execute(command, resultHandler);
			} catch (IOException e) {

			}

		}
		public boolean verifyTask(String processName) {
			String line;
			int processCount = 0;
			boolean flag = true;

			Process process;
			try {

				process = Runtime.getRuntime().exec(System.getenv("windir") + "/system32/" + "tasklist.exe");
				BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));

				while ((line = input.readLine()) != null) {
					if (line.startsWith(processName)) {
						processCount += 1;
					}
				}
				if (processCount == 0) {
					flag = false;
				}
				input.close();
			} catch (IOException e) {

			}
			return flag;

		}
		 public void startServer(String host,String port) {
				
				if (verifyTask("node.exe")) {
					stopProcess("node.exe");
				}
				if (verifyTask("chromedriver.exe")) {
					stopProcess("chromedriver.exe");
				}
				if (verifyTask("adb.exe")) {
					// stopProcess("adb.exe");
				}
				try {
				String appiumNode = getFrameworkConfigProperty("AppiumNodePath");
				String appiumServer =getFrameworkConfigProperty("AppiumServerPath");
				String myStr;
		    	myStr=Selenium_Appium_Keywords.testReportsDirectory.substring(Selenium_Appium_Keywords.testReportsDirectory.length()-22);
				CommandLine command = new CommandLine("cmd");
				command.addArgument("/c");
				command.addArgument(appiumNode);
				command.addArgument(appiumServer);
				command.addArgument("--address");
				command.addArgument(host);
				command.addArgument("--port");
				command.addArgument(port);
				// command.addArgument("--no-reset");
				/*command.addArgument("--log");
				command.addArgument("./TestReports/"+myStr+"/AppiumLogs.log");
				*/DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
				DefaultExecutor executor = new DefaultExecutor();
				executor.setExitValue(1);
			
					executor.execute(command, resultHandler);
				} catch (Exception e) {
						System.out.println(e.toString());
				}
			}
		
		 public  void takeScreenshot(String report)
			{		 
			 	if((currentExecutionOS.equalsIgnoreCase("android")) && (getFrameworkConfigProperty("AppType").equalsIgnoreCase("hybird")))
				{
			 		switchToContext("NATIVE_APP");
				}
		       Selenium_Appium_Keywords.testcaseManualScreenshotNo++;	
			   String scrPath="../../Screenshots/"+currentTestCaseModuleName+"/"+currentTestCaseName+"/";  
			   if(!(currentExecutionOS.equalsIgnoreCase("android")) && !(getFrameworkConfigProperty("AppType").equalsIgnoreCase("hybird")))
				{
				   ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(By.xpath("(//html)[1]")));
				}
				   /* writeTestStepReport("<font style='color:Blue'>stepNo"+report+"</font><br/>",currentExecutionName,currentTestCaseName);	*/	 		
					   capturePageScreenShot("ManualScreenshotNo"+Selenium_Appium_Keywords.testcaseManualScreenshotNo);
			       writeTestStepReport("<a href='"+scrPath+"/ManualScreenshotNo"+Selenium_Appium_Keywords.testcaseManualScreenshotNo+".png'> ",currentExecutionName,currentTestCaseName);
				   writeTestStepReport("<img src='"+scrPath+"/ManualScreenshotNo"+Selenium_Appium_Keywords.testcaseManualScreenshotNo+".png' height='200' width='200'/><br></br></a></font>",currentExecutionName,currentTestCaseName);   
			}
		 public void swipe(int startx,int starty,int endx,int endy,int duration)
		 {
			 try
			 {
				 	Runtime r = Runtime.getRuntime();
					Process pr = r.exec("adb shell input swipe "+startx+" "+starty+" "+endx+" "+endy+" "+duration);
					pr.waitFor();
			 }
			 catch(Exception e)
			 {
				 stepFailed(e.toString());
			 }
		 }
		 
		 public void selectTextContainsOption(String objLocator,String optionText)
		 {
			 waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
			
			try{	
				for(int i=1;i<=pageTextLoadWaitTime;i++)
				{
					if(driver.getPageSource().contains(optionText))
					{
						break;
						
					}
					else
					{
						sleep(1);
					}
					if(i==pageTextLoadWaitTime)
					{
						stepFailed("Invalid "+locatorDescription+" dropdown option '"+optionText+"'");
					}
				}
			Select select= new Select(webElement);
				
				for(int i=1;i<=select.getOptions().size();i++)
				{
					if(select.getOptions().get((i-1)).getText().contains(optionText))
					{
						select.selectByIndex((i-1));
						stepPassed("Select '" + optionText+"' option from '"+locatorDescription+"'");
						break;
					}
					if(i==select.getOptions().size())
					{
						stepFailed("Wrong option -"+optionText);
					}
				}
			}
			catch(Exception e)
			{
				stepFailed(e.toString());
			}
		 }
		 public void waitForPageText(String txt1,String txt2)
			{
				try{
				for(int i=1;i<=pageTextLoadWaitTime;i++)
				{
					if(driver.getPageSource().contains(txt1) || driver.getPageSource().contains(txt2))
					{
						break;
					}
					else
					{
						sleep(1);
					}
					if(i==pageTextLoadWaitTime)
					{
						stepFailed("'"+txt1+"' '"+txt2+"' texts is not found in the page within "+pageLoadWaitTime+" seconds");
					}
				}
				}
				catch(Exception e)
				{
					stepFailed(e.toString());
				}
			}
			
			public  boolean isFileExist(String path,String txt1,String txt2)
			{
				for(int j=1;j<=pageElementLoadWaitTime;j++)
				{
				try{
				File folder = new File(path);
				File[] listOfFiles = folder.listFiles();

				    for (int i = 0; i < listOfFiles.length; i++) {
				      if (listOfFiles[i].isFile()) {
				    	  if(listOfFiles[i].getName().contains(txt1) && listOfFiles[i].getName().contains(txt2))
				    	  {
				    		  System.out.println("File " + listOfFiles[i].getName());
				    		 
				    		 return true;
				
				    	  }
				        
				      } else if (listOfFiles[i].isDirectory()) {
				        System.out.println("Directory " + listOfFiles[i].getName());
				      }
				    }
					
				}
				catch(Exception e)
				{
					stepFailed(e.toString());
				}
				sleep(1);
				}
				return false;
			}

			public  void deleteFiles(String path, String txt1,String txt2)
			{
				File folder = new File(path);
				File[] listOfFiles = folder.listFiles();

				    for (int i = 0; i < listOfFiles.length; i++) {
				      if (listOfFiles[i].isFile()) {
				    	  if(listOfFiles[i].getName().contains(txt1) && listOfFiles[i].getName().contains(txt2))
				    	  {
				    		  System.out.println("File " + listOfFiles[i].getName());
				    		  listOfFiles[i].delete();
				    	  }
				        
				      } else if (listOfFiles[i].isDirectory()) {
				        System.out.println("Directory " + listOfFiles[i].getName());
				      }
				    }
			}
public void selectHiddenElement(String objLocator,String optionText)
	 {
		try{	
			for(int i=1;i<=pageTextLoadWaitTime;i++)
			{
				if(driver.getPageSource().contains(optionText))
				{
					break;
					
				}
				else
				{
					sleep(1);
				}
				if(i==pageTextLoadWaitTime)
				{
					stepFailed("Invalid "+locatorDescription+" dropdown option '"+optionText+"'");
				}
			}
		Select select= new Select(webElement);
			
			for(int i=1;i<=select.getOptions().size();i++)
			{
				if(select.getOptions().get((i-1)).getText().contains(optionText))
				{
					select.selectByIndex((i-1));
					stepPassed("Select '" + optionText+"' option from '"+locatorDescription+"'");
					break;
				}
				if(i==select.getOptions().size())
				{
					stepFailed("Wrong option -"+optionText);
				}
			}
		}
		catch(Exception e)
		{
			stepFailed(e.toString());
		}
	 }


 public void setAttributeValue(String objLocator, String value){
		 try{
		 waitForElementToDisplay(objLocator, pageElementLoadWaitTime);
		 JavascriptExecutor  js = (JavascriptExecutor) driver; 

		    String scriptSetAttrValue = "arguments[0].setAttribute(arguments[1],arguments[2])";

		    js.executeScript(scriptSetAttrValue, webElement, "value", value);
		 }
		 catch(Exception e)
		 {
			 stepFailed(e.toString());
		 }

		}

public  void waitForPageNotContainText(String txt)
	{
		waitForPageNotContainText(txt,pageTextLoadWaitTime);
	}
	
	public  void waitForPageNotContainText(String txt, int timeout)
	{
		int second;
        for (second = 0;second<timeout; second++)
        { 
        	try{
        		driver.getCurrentUrl();
        	}
        	catch(Exception e){stepFailed("WebDriver is not found");}
        	if (second == timeout-1) {
                  sendToLogFile("Error","Text is found ' "+txt+"'");                    
     		   	 	stepFailed("Text is found ' "+txt+"'");
                    break;
              }
              try { 
            	  	
                    if (!(driver.getPageSource().contains(txt))){ 
                          sendToLogFile("INFO","Text: '"+txt+"' is not present");
                          break; 
                          } 
              }catch (Exception e) {
              }
              try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
  }
	
	
	public void verifyTableValues(String name,String value,String page)
	{
		if(getElementText(name+" label@xpath=//*[contains(text(),'"+name+"')]//parent::tr//td[2]").contains(value))
		{
			stepPassed("Verfied the "+name+" name value is '"+value+"' in "+page+" page");
		}
		else
		{
			stepFailed("The "+name+" name value is not '"+value+"' in "+page+" page");
		}
	}

public String getRandomNumber() {
		Random random = new Random();
		int minRange = 100;
		int maxRange = 999;
		int randomNo = random.nextInt((maxRange - minRange) + 1) + minRange;
		return reportFileName;
	}


public void waitAndSwitchChildWindow(String childWindowPageTxt)
	{
		boolean isWindowSwitched=false;
		for(int i=0;i<=pageLoadWaitTime;i++)
		{
		try
		{
			for(String win:driver.getWindowHandles())
			{
				driver.switchTo().window(win);
				if(driver.getPageSource().contains(childWindowPageTxt))
				{
					isWindowSwitched=true;
					break;
				}
			}
		}
		catch(Exception e)
		{
			sleep(1);
		}
		if(isWindowSwitched)
		{
			break;
		}
		}
	}

}
	

	

