package Utilities;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.Reporter;




public class CommonFrameworkKeywords 
{
	//public static String timeStampName="";
	public static String reportFileName="";
	public static Logger logs;
	public static String am_pm,minutes,hour,seconds,year,month,day;
  	public int testCaseStepNo=0;
  	public List<String> mailfileList = new ArrayList<String>();
	String testStepReport="";
	public String sourceMailFolder="";
	
	public static String getFrameworkConfigProperty(String keyword)
	{
		Properties properties=new Properties();
		try 
		{
			properties.load(new FileInputStream("./config/FrameworkConfiguration.properties"));
		} 
		catch (FileNotFoundException e) 
		{
			sendToLogFile("ERROR","File Not Found Exception thrown while getting value of "+keyword+" from Test Configuration file");
		} catch (IOException e) 
		{
			sendToLogFile("ERROR","IO Exception thrown while getting value of "+keyword+" from Test Configuration file");
		}
		sendToLogFile("INFO","Getting value of "+keyword+" from Test Configuration file : "+properties.getProperty(keyword));
		
		return properties.getProperty(keyword).trim();		
	}
	
	public static void sendToLogFile(String type, String message)
	{
		String t=type.toUpperCase();
		if (t.equalsIgnoreCase("DEBUG"))
		{
			Selenium_Appium_Keywords.logs.debug(message);
		}
		else if (t.equalsIgnoreCase("INFO"))
		{
			Selenium_Appium_Keywords.logs.info(message);
		}
		else if (t.equalsIgnoreCase("WARN"))
		{
			Selenium_Appium_Keywords.logs.warn(message);
		}
		else if (t.equalsIgnoreCase("ERROR"))
		{
			Selenium_Appium_Keywords.logs.error(message);
		}
		else if (t.equalsIgnoreCase("FATAL"))
		{
			Selenium_Appium_Keywords.logs.fatal(message);
		}
		else
			Selenium_Appium_Keywords.logs.error("Invalid log Type :"+type+". Unable to log the message.");
	}
	public static void deleteTempFiles() {
		File directory = new File(Selenium_Appium_Keywords.testReportsDirectory+"/testng");
		File directory1 = new File(Selenium_Appium_Keywords.testReportsDirectory+"/testng" +"/Suite");
		File directory2 = new File(Selenium_Appium_Keywords.testReportsDirectory+"/testng" +"/old");
		File directory3 = new File(Selenium_Appium_Keywords.testReportsDirectory+"/testng" +"/junitreports");
		File directory4 = new File(Selenium_Appium_Keywords.testReportsDirectory+"/testng" +"/old/Suite");
		File file1 = new File(Selenium_Appium_Keywords.testReportsDirectory
				+ "/copyfile.xml");
		File file2 = new File(Selenium_Appium_Keywords.testReportsDirectory + "/temp.xml");
		File file3 = new File(Selenium_Appium_Keywords.testReportsDirectory
				+ "/emailable-report.html");
		File file4 = new File(Selenium_Appium_Keywords.testReportsDirectory + "/index.html");
		File file5 = new File(Selenium_Appium_Keywords.testReportsDirectory
				+ "/testng-results.xml");
		File file6 = new File(Selenium_Appium_Keywords.testReportsDirectory
				+ "/testng-failed.xml");
		File file7 = new File(Selenium_Appium_Keywords.testReportsDirectory + "/testng.css");
		File file8 = new File(Selenium_Appium_Keywords.testReportsDirectory
				+ "/PdfReport.html");
		File file9 = new File(Selenium_Appium_Keywords.testReportsDirectory
				+ "/pdfReport.pdf");
		File file10 = new File(Selenium_Appium_Keywords.testReportsDirectory
				+ "/pdfReport1.pdf");
		File file11 = new File(Selenium_Appium_Keywords.testReportsDirectory
				+ "/TestCaseResults.pdf");
		File file12 = new File(Selenium_Appium_Keywords.testReportsDirectory
				+ "/pdffile.xml");
		file1.delete();
		file2.delete();
		file3.delete();
		file4.delete();
		file5.delete();
		file6.delete();
		file7.delete();
		file8.delete();
		file9.delete();
		file10.delete();
		file11.delete();
		file12.delete();
		if (directory.isDirectory()) {

			String files[] = directory.list();
			for (String temp : files) {
				File fileDelete = new File(directory, temp);
				fileDelete.delete();
			}
			if (directory.list().length == 0) {
				directory.delete();

			} else {

			}
		}
		if (directory1.isDirectory()) {

			String files1[] = directory1.list();
			for (String temp1 : files1) {
				File fileDelete1 = new File(directory1, temp1);
				fileDelete1.delete();
			}
			if (directory1.list().length == 0) {
				directory1.delete();

			} else {

			}
		}
		if (directory2.isDirectory()) {

			String files1[] = directory2.list();
			for (String temp1 : files1) {
				File fileDelete1 = new File(directory2, temp1);
				fileDelete1.delete();
			}
			if (directory2.list().length == 0) {
				directory2.delete();

			} else {

			}
		}
		if (directory3.isDirectory()) {

			String files1[] = directory3.list();
			for (String temp1 : files1) {
				File fileDelete1 = new File(directory3, temp1);
				fileDelete1.delete();
			}
			if (directory3.list().length == 0) {
				directory3.delete();

			} else {

			}
		}
		if (directory4.isDirectory()) {

			String files1[] = directory4.list();
			for (String temp1 : files1) {
				File fileDelete1 = new File(directory4, temp1);
				fileDelete1.delete();
			}
			if (directory4.list().length == 0) {
				directory4.delete();

			} else {

			}
		}
		directory4.delete();
		directory1.delete();
		directory2.delete();
		directory3.delete();
		directory.delete();
	}
	public void kickOff()
	{
		createTestReportDirectory();
		try {
			ConfigPropertiesFiles.createPropertiesFiles();
			
		} catch (Exception e) 
		{
		sendToLogFile("INFO","Startup activities - Done...");
		}
	}

	public static void cleanupFiles()
	{
		
		deleteTempFiles();
		sendToLogFile("INFO","Cleanup activites...");
		sendToLogFile("INFO","Cleanup activities - Done...");
		
		
	}


	public static void windowScreenShot(String filename)
	{
		String scrPath=Selenium_Appium_Keywords.testReportsDirectory+"/Screenshots";
		File file = new File(scrPath);
	    file.mkdir();
	    try {
	        Robot robot = new Robot();
	        Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	        BufferedImage bufferedImage = robot.createScreenCapture(captureSize);
	        File outputfile = new File(scrPath+"/"+filename+".png");
	        ImageIO.write(bufferedImage, "png", outputfile);       
       		Selenium_Appium_Keywords.sendToLogFile("INFO", "Taken screenshot of failing screen");
	    }
	    catch(AWTException e) {
	        Selenium_Appium_Keywords.sendToLogFile("ERROR", "AWT Exception : While taking screenshot of the failing test case");
	    } catch (IOException e) {
	        Selenium_Appium_Keywords.sendToLogFile("ERROR", "IO Exception : While taking screenshot of the failing test case");
		}
	}
	
	public static void testCaseFailed()
	{
	                        
		Assert.fail("Hello");
   
	}
	/*public void useExcelSheet(String pathOfExcel)
	{
		useExcelSheet(pathOfExcel);
	}*/
	public void closeExcelSheet()
	{
		closeExcelSheet();
	}
	
	
	public void writeTestStepReport(String report,String currentMachineName, String currentTestCaseName)
	{
		try{
		Writer output;
		for(String machineNames:Selenium_Appium_Keywords.testReportsDir)
		{
			if(machineNames.contains(currentMachineName))
			{
				if(report.contains("<B></B>") || report.toLowerCase().contains("dataset"))
				{
					testCaseStepNo=0;
					if(report.contains("<B></B>"))
					{
						testStepReport="";
					}
					
				}
				testStepReport=testStepReport.replace("<center><a href='../"+currentMachineName+".html'>back</a></center></body></html>", "")+report.replace("stepNo", testCaseStepNo+".");
				PrintWriter writer = new PrintWriter(machineNames+"./"+currentTestCaseName+".html");
				writer.print("");
				writer.close();
				output = new BufferedWriter(new FileWriter(machineNames+"./"+currentTestCaseName+".html",true));  //clears file every time
				
				output.write(HtmlReportKeywords.addHeadTagLinks().replace("../", "../../")+testStepReport);
			//	output.write(testStepReport);
				output.write("<center><a href='../"+currentMachineName+".html'>back</a></center></body></html>");
				if(!(report.contains("TestFailure")))
				{
					testCaseStepNo++;
				}
				
				output.close();
			}
			
			
		}
		
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	public static void transferLogos()
	{
		
		try 
		{
			for(int i=0;i<Selenium_Appium_Keywords.ipName.size();i++)
			{
			(new File(Selenium_Appium_Keywords.testReportsDirectory+"/Logos/")).mkdir();
			
			
			File sourcecompanyLogo = new File("./"+CommonFrameworkKeywords.getFrameworkConfigProperty("NexInfo_logo_path"));
			File sourceclientLogo = new File("./"+CommonFrameworkKeywords.getFrameworkConfigProperty("Zenoti_logo_path"));
			File designationcompanyLogo = new File(Selenium_Appium_Keywords.testReportsDirectory+"/"+CommonFrameworkKeywords.getFrameworkConfigProperty("NexInfo_logo_path"));
			File designationclientLogo = new File(Selenium_Appium_Keywords.testReportsDirectory+"/"+CommonFrameworkKeywords.getFrameworkConfigProperty("Zenoti_logo_path"));
			InputStream in = new FileInputStream(sourcecompanyLogo);
			OutputStream out = new FileOutputStream(designationcompanyLogo);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			InputStream in1 = new FileInputStream(sourceclientLogo);
			OutputStream out1 = new FileOutputStream(designationclientLogo);
			byte[] buf1 = new byte[1024];
			int len1;
			while ((len1 = in1.read(buf1)) > 0) {
				out1.write(buf1, 0, len1);
			}
			in1.close();
			out1.close();
			}
		}

		catch (FileNotFoundException ex) 
		{

			System.out.println(ex.getMessage()+ " in  the specified directory.");
			System.exit(0);

		}

		catch (IOException e)
		{

			System.out.println(e.getMessage());

		} 

	}

	public static void transferFiles()
	{
		
		try 
		{
			for(int i=0;i<Selenium_Appium_Keywords.ipName.size();i++)
			{
				(new File(Selenium_Appium_Keywords.testReportsDirectory+"/Extras/")).mkdir();
				(new File(Selenium_Appium_Keywords.testReportsDirectory+"/Extras/css")).mkdir();
				(new File(Selenium_Appium_Keywords.testReportsDirectory+"/Extras/fonts")).mkdir();
			//	(new File(Selenium_Appium_Keywords.testReportsDirectory+"/Extras/js")).mkdir();
				/*transferFile("./Extras/js/bootstrap.min.js",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/js/bootstrap.min.js");
				transferFile("./Extras/js/common.js",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/js/common.js");
				transferFile("./Extras/js/jquery-1.9.1.js",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/js/jquery-1.9.1.js");
				transferFile("./Extras/js/npm.js",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/js/npm.js");
				*/transferFile("./Extras/fonts/glyphicons-halflings-regular.eot",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/fonts/glyphicons-halflings-regular.eot");
				transferFile("./Extras/fonts/glyphicons-halflings-regular.svg",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/fonts/glyphicons-halflings-regular.svg");
				transferFile("./Extras/fonts/glyphicons-halflings-regular.ttf",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/fonts/glyphicons-halflings-regular.ttf");	
				transferFile("./Extras/fonts/glyphicons-halflings-regular.woff",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/fonts/glyphicons-halflings-regular.woff");
				transferFile("./Extras/fonts/glyphicons-halflings-regular.woff2",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/fonts/glyphicons-halflings-regular.woff2");
				transferFile("./Extras/fonts/OpenSans-Regular.eot",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/fonts/OpenSans-Regular.eot");	
				transferFile("./Extras/fonts/OpenSans-Regular.ttf",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/fonts/OpenSans-Regular.ttf");
				transferFile("./Extras/fonts/OpenSans-Semibold.eot",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/fonts/OpenSans-Semibold.eot");
				transferFile("./Extras/fonts/OpenSans-Semibold.ttf",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/fonts/OpenSans-Semibold.ttf");
				transferFile("./Extras/css/bootstrap.min.css",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/css/bootstrap.min.css");	
				transferFile("./Extras/css/bootstrap.min.css.map",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/css/bootstrap.min.css.map");
				transferFile("./Extras/css/font-awesome.min.css",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/css/font-awesome.min.css");
				transferFile("./Extras/css/style.css",Selenium_Appium_Keywords.testReportsDirectory+"/Extras/css/style.css");
				
			
			
			}
			
		}

		catch (Exception ex) 
		{

			System.out.println(ex.getMessage());
			System.exit(0);

		}

		

	}

	public static void transferFile(String sourceFile,String designationFile)
	{
		try{
		File source= new File(sourceFile);
		File designation = new File(designationFile);
		InputStream in = new FileInputStream(source);
		OutputStream out = new FileOutputStream(designation);
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	public static void transferCssFile()
	{
		
		try 
		{
			for(int i=0;i<Selenium_Appium_Keywords.ipName.size();i++)
			{
			(new File(Selenium_Appium_Keywords.testReportsDirectory+"/Extras/")).mkdir();
			
			File source= new File("./Extras/style.css");
			File designation = new File(Selenium_Appium_Keywords.testReportsDirectory+"/Extras/style.css");
			InputStream in = new FileInputStream(source);
			OutputStream out = new FileOutputStream(designation);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			}
		}

		catch (FileNotFoundException ex) 
		{

			System.out.println(ex.getMessage()+ " in  the specified directory.");
			System.exit(0);

		}

		catch (IOException e)
		{

			System.out.println(e.getMessage());

		} 

	}
	
	public static void createTestReportDirectory()
	{
	//testResultsDirectory="";
	File curdir = new File(".");
  
  	Calendar calendar = new GregorianCalendar();
  	//String am_pm,min,hr,sec,yr,mon,day;
  	
  	hour=("0"+calendar.get(Calendar.HOUR));
  	hour=hour.substring(hour.length()-2);

  	minutes=("0"+calendar.get(Calendar.MINUTE));
  	minutes=minutes.substring(minutes.length()-2);
  

  	seconds=("0"+calendar.get(Calendar.SECOND));
  	seconds=seconds.substring(seconds.length()-2);

  	year=""+calendar.get(Calendar.YEAR);
  	//yr=yr.substring(yr.length()-2);

  	month=("0"+(calendar.get(Calendar.MONTH)+1));
  	month=month.substring(month.length()-2);

  	day=("0"+calendar.get(Calendar.DAY_OF_MONTH));
  	day=day.substring(day.length()-2);

  	if(calendar.get(Calendar.AM_PM) == 0)
	  	am_pm = "AM";
  	else
	  	am_pm = "PM";

  	
  	try 
  	{
  		reportFileName=day +"_"+ month+"_" +year+"_"+hour+"_"+minutes+"_"+seconds+"_"+am_pm;
  		Selenium_Appium_Keywords.testReportsDirectory = curdir.getCanonicalPath()+ "/TestReports/" + reportFileName;
  	} 
  	catch (IOException e) 
  	{
  		// TODO Auto-generated catch block
	  	System.out.println("IO Error while creating Output Directory : "+Selenium_Appium_Keywords.testReportsDirectory);
  	}

  		createTestLogFile();
}


public static void createTestLogFile() 
{
    Properties props = new Properties();
    String propsFileName = "./config/log4j.properties";

    try 
    {
    	FileInputStream configStream = new FileInputStream(propsFileName);
    	props.load(configStream);
    	
      
    	String myStr;
    	myStr=Selenium_Appium_Keywords.testReportsDirectory.substring(Selenium_Appium_Keywords.testReportsDirectory.length()-22);
      
    	myStr="./TestReports/"+myStr+"/Logs.log";
    	//System.out.println(myStr);
		
    	props.setProperty("log4j.appender.FA.File",myStr);
    	FileOutputStream output = new FileOutputStream(propsFileName);
    	props.store(output, "Output Directory updated : "+Selenium_Appium_Keywords.testReportsDirectory);
    	//System.out.println(props.getProperty("log4j.appender.FA.File"));
		
    	output.close();
    	configStream.close();
   
    	//props.clear();
    	PropertyConfigurator.configure(propsFileName);
		//System.out.println(props.getProperty("log4j.appender.FA.File"));
    	Selenium_Appium_Keywords.logs=Logger.getLogger(myStr);
    	Selenium_Appium_Keywords.sendToLogFile("INFO","Startup activites...");
		
    	Selenium_Appium_Keywords.sendToLogFile("INFO", "Test Output Directory creation successful :"+Selenium_Appium_Keywords.testReportsDirectory);
    	Selenium_Appium_Keywords.sendToLogFile("INFO", "Log File creation successful : LogFile.log");
    } 
    catch (IOException ex) 
    {
    	System.out.println("There was an error creating the log file");
    }
}
public  void zipReport()
{
	 String OUTPUT_ZIP_FILE =Selenium_Appium_Keywords.testReportsDirectory+".zip";
	 String SOURCE_FOLDER = Selenium_Appium_Keywords.testReportsDirectory;
	 sourceMailFolder=SOURCE_FOLDER;
	 generateFileList(new File(SOURCE_FOLDER));
	 zipIt(OUTPUT_ZIP_FILE,SOURCE_FOLDER);
}

/**
 * Zip it
 * @param zipFile output ZIP file location
 */
public void zipIt(String zipFile,String SourceFolder){
	
 byte[] buffer = new byte[1024];

 try{

FileOutputStream fos = new FileOutputStream(zipFile);
ZipOutputStream zos = new ZipOutputStream(fos);

System.out.println("Output to Zip : " + zipFile);

for(String file : mailfileList){

System.out.println("File Added : " + file);
ZipEntry ze= new ZipEntry(file);
    zos.putNextEntry(ze);

    FileInputStream in = 
                   new FileInputStream(SourceFolder + File.separator + file);

    int len;
    while ((len = in.read(buffer)) > 0) {
    zos.write(buffer, 0, len);
    }

    in.close();
}

zos.closeEntry();
//remember close it
zos.close();

System.out.println("Done");
}catch(IOException ex){
   ex.printStackTrace();   
}
}

/**
 * Traverse a directory and get all files,
 * and add the file into fileList  
 * @param node file or directory
 */
public void generateFileList(File node){

//add file only
if(node.isFile()){
mailfileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
}

if(node.isDirectory()){
String[] subNote = node.list();
for(String filename : subNote){
generateFileList(new File(node, filename));
}
}

}

/**
 * Format the file path for zip
 * @param file file path
 * @return Formatted file path
 */
private String generateZipEntry(String file){
return file.substring(sourceMailFolder.length()+1, file.length());
}


public void sendMail(){  
	  
	/*
	 * String[] to=getFrameworkConfigProperty("MailToAddress").split(";");//change
	 * accordingly final String
	 * user=getFrameworkConfigProperty("MailUserID");//change accordingly final
	 * String password=getFrameworkConfigProperty("MailPassword");//change
	 * accordingly String projectName=getFrameworkConfigProperty("Project_Name");
	 */
	   
	   
	   String[] to= {"sivasankar9008@gmail.com"};
		  final String user="sivasankar.srinivasan@nexinfo.com";
		  final String password="Ssed3f4567!@#$";
		   String projectName="Oracle Automation Result";
	   
	  //1) get the session object    
	  Properties props = new Properties();    
	  props.put("mail.smtp.host", "smtp.gmail.com");    
	  props.put("mail.smtp.socketFactory.port", "465");    
	  props.put("mail.smtp.socketFactory.class",    
	            "javax.net.ssl.SSLSocketFactory");    
	  props.put("mail.smtp.auth", "true");    
	  props.put("mail.smtp.port", "465");   
	  
	  Session session = Session.getInstance(props,  
	   new javax.mail.Authenticator() {  
	   protected PasswordAuthentication getPasswordAuthentication() {  
	   return new PasswordAuthentication(user,password);  
	   }  
	  });  
	     
	  //2) compose message     
	  try{  
	    MimeMessage message = new MimeMessage(session);  
	    message.setFrom(new InternetAddress(user));  
	    for(int i=0;i<to.length;i++)
	    {
	    message.addRecipient(Message.RecipientType.TO,new InternetAddress(to[i]));  
	    }
	    message.setSubject(projectName);  
	      
	    //3) create MimeBodyPart object and set your message text     
	    BodyPart messageBodyPart1 = new MimeBodyPart();  
	    messageBodyPart1.setText("Please find the attached automation test report file");  
	      
	    //4) create new MimeBodyPart object and set DataHandler object to this object      
	    MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
	  
	    String filename = Selenium_Appium_Keywords.testReportsDirectory+".zip";//change accordingly  
	    DataSource source = new FileDataSource(filename);  
	    messageBodyPart2.setDataHandler(new DataHandler(source));  
	    messageBodyPart2.setFileName(reportFileName+".zip");  
	     
	     
	    //5) create Multipart object and add MimeBodyPart objects to this object      
	    Multipart multipart = new MimeMultipart();  
	    multipart.addBodyPart(messageBodyPart1);  
	    multipart.addBodyPart(messageBodyPart2);  
	  
	    //6) set the multiplart object to the message object  
	    message.setContent(multipart );  
	     
	    //7) send message  
	    Transport.send(message);  
	   
	   System.out.println("Mail sent....");  
	   }catch (MessagingException ex) {ex.printStackTrace();}  
	 }  




}
