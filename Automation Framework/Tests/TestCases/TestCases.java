package TestCases;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.parsers.ParserConfigurationException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CashManagements.Functionalities;

import org.testng.annotations.Listeners;

import Utilities.HtmlReportKeywords;
import Utilities.Selenium_Appium_Keywords;

@Listeners({ Utilities.HtmlReportKeywords.class })
public class TestCases extends Functionalities {
	String machineName = "";
	// Functionalities functionalities=new Functionalities();

	ExtentReports report;

	@BeforeClass
	@Parameters({ "selenium.machinename", "selenium.host", "selenium.port", "selenium.browser", "selenium.os",
			"selenium.browserVersion", "selenium.osVersion", "selenium.sheetNo" })
	public void precondition(String machineName, String host, String port, String browser, String os,
			String browserVersion, String osVersion, String sheetNo) {
		try {
			report = new ExtentReports("./extentReport.html");
			// extent.attachReporter(htmlReporter);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		testDataSheetNo = Integer.parseInt(sheetNo);

		setup(machineName, host, port, browser, os, browserVersion, osVersion);
		lanuchBrowser(machineName, host, port, browser, os, browserVersion, osVersion);

	}

	@AfterClass
	public void closeSessions() {

		// closeAllSessions();
	}

	@Test
	@Parameters({ "selenium.machinename" })
	public void AP_TS_001(String machineName, Method method) {
		ExtentTest test;
		test = report.startTest("Zen_T1");
		test.log(LogStatus.PASS, "Navigated to the specified URL");
		report.endTest(test);
		report.flush();

		try {

			TestStart(machineName, method.getName());
			int loginCount = 1;
			try {
				for (int i = 0; i < iterationCount.size(); i++) {
					dataRowNo = Integer.parseInt(iterationCount.get(i).toString());
					writeTestStepReport("<font size=4 style='color:orange'>TestDataSet:" + (i + 1) + "</font><br/>",
							currentExecutionName, currentTestCaseName);
					if (loginCount == 1) {
						login();
						loginCount++;
					}
				//	createSupplierAndManageSupplier();

				}

			} finally {
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
	
	@Test
	@Parameters({ "selenium.machinename" })
	public void AP_TS_002(String machineName, Method method) {
		ExtentTest test;
		test = report.startTest("Zen_T1");
		test.log(LogStatus.PASS, "Navigated to the specified URL");
		report.endTest(test);
		report.flush();

		try {

			TestStart(machineName, method.getName());
			int loginCount = 1;
			try {
				for (int i = 0; i < iterationCount.size(); i++) {
					dataRowNo = Integer.parseInt(iterationCount.get(i).toString());
					writeTestStepReport("<font size=4 style='color:orange'>TestDataSet:" + (i + 1) + "</font><br/>",
							currentExecutionName, currentTestCaseName);
					if (loginCount == 1) {
						login();
						loginCount++;
					}
					createInVoice();
					System.out.print("");

				}

			} finally {
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
	@Test
	@Parameters({ "selenium.machinename" })
	public void AP_TS_005(String machineName, Method method) {
		ExtentTest test;
		test = report.startTest("Zen_T1");
		test.log(LogStatus.PASS, "Navigated to the specified URL");
		report.endTest(test);
		report.flush();

		try {

			TestStart(machineName, method.getName());
			int loginCount = 1;
			try {
				for (int i = 0; i < iterationCount.size(); i++) {
					dataRowNo = Integer.parseInt(iterationCount.get(i).toString());
					writeTestStepReport("<font size=4 style='color:orange'>TestDataSet:" + (i + 1) + "</font><br/>",
							currentExecutionName, currentTestCaseName);
					if (loginCount == 1) {
						login();
						loginCount++;
					}
					createAssetInVoice();

				}

			} finally {
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	@Test
	@Parameters({ "selenium.machinename" })
	public void AP_TS_006(String machineName, Method method) {
		ExtentTest test;
		test = report.startTest("Zen_T1");
		test.log(LogStatus.PASS, "Navigated to the specified URL");
		report.endTest(test);
		report.flush();

		try {

			TestStart(machineName, method.getName());
			int loginCount = 1;
			try {
				for (int i = 0; i < iterationCount.size(); i++) {
					dataRowNo = Integer.parseInt(iterationCount.get(i).toString());
					writeTestStepReport("<font size=4 style='color:orange'>TestDataSet:" + (i + 1) + "</font><br/>",
							currentExecutionName, currentTestCaseName);
					if (loginCount == 1) {
						login();
						loginCount++;
					}
					runMassadditions();

				}

			} finally {
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
	@Test
	@Parameters({ "selenium.machinename" })
	public void AP_TS_008(String machineName, Method method) {
		ExtentTest test;
		test = report.startTest("Zen_T1");
		test.log(LogStatus.PASS, "Navigated to the specified URL");
		report.endTest(test);
		report.flush();

		try {

			TestStart(machineName, method.getName());
			int loginCount = 1;
			try {
				for (int i = 0; i < iterationCount.size(); i++) {
					dataRowNo = Integer.parseInt(iterationCount.get(i).toString());
					writeTestStepReport("<font size=4 style='color:orange'>TestDataSet:" + (i + 1) + "</font><br/>",
							currentExecutionName, currentTestCaseName);
					if (loginCount == 1) {
						login();
						loginCount++;
					}
					manageInvoice();

				}

			} finally {
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
	
	@Test
	@Parameters({ "selenium.machinename" })
	public void AP_TS_009(String machineName, Method method) {
		ExtentTest test;
		test = report.startTest("Zen_T1");
		test.log(LogStatus.PASS, "Navigated to the specified URL");
		report.endTest(test);
		report.flush();

		try {

			TestStart(machineName, method.getName());
			int loginCount = 1;
			try {
				for (int i = 0; i < iterationCount.size(); i++) {
					dataRowNo = Integer.parseInt(iterationCount.get(i).toString());
					writeTestStepReport("<font size=4 style='color:orange'>TestDataSet:" + (i + 1) + "</font><br/>",
							currentExecutionName, currentTestCaseName);
					if (loginCount == 1) {
						login();
						loginCount++;
					}
					createCreditMemo();

				}

			} finally {
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
	@Test
	@Parameters({ "selenium.machinename" })
	public void AP_TS_010(String machineName, Method method) {
		ExtentTest test;
		test = report.startTest("Zen_T1");
		test.log(LogStatus.PASS, "Navigated to the specified URL");
		report.endTest(test);
		report.flush();

		try {

			TestStart(machineName, method.getName());
			int loginCount = 1;
			try {
				for (int i = 0; i < iterationCount.size(); i++) {
					dataRowNo = Integer.parseInt(iterationCount.get(i).toString());
					writeTestStepReport("<font size=4 style='color:orange'>TestDataSet:" + (i + 1) + "</font><br/>",
							currentExecutionName, currentTestCaseName);
					if (loginCount == 1) {
						login();
						loginCount++;
					}
					createPaymentProcessTemplate();

				}

			} finally {
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

}
