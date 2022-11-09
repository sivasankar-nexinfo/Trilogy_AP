
package CashManagements;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Set;

import javax.swing.text.StyledEditorKit.StyledTextAction;
import org.apache.camel.Navigate;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.Select;
import Objects.CashManagementPage;
import Utilities.Selenium_Appium_Keywords;

public class Functionalities extends Common_Utility {
	public String supplierName ="";
	public String invoiceName="";
	public void createSupplierAndManageSupplier() {
		/*
		 * String suppliersText="Test123455"+randomNumbers(); String
		 * businessRelationship="Spend Authorized"; String
		 * taxOrganizationType="Corporation"; String country="United States"; String
		 * country1="United States US"; String addressName="Sample"+getRandomNumber();
		 * String supplierType="Supplier";
		 */
		String suppliersText=getFrameworkConfigProperty("Supplier")+randomNumbers();
		String businessRelationship=getTextData("drpBusinessRelationship");
		String taxOrganizationType=getTextData("drpTaxOrgType");
		String country=getTextData("txtCountry");
		String country1=getTextData("drpCountry");
		String addressName=getTextData("txtAddressName")+getRandomNumber();
		String supplierType=getTextData("drpSupplierType");
		String address1=getTextData("txtAddr1");
		String address2=getTextData("txtAddr2");
		String city=getTextData("txtCity");
		String state=getTextData("txtState");
		String postalCode=getTextData("txtPostalCode");
		String paymentTerms = getTextData("Payment Terms");
		String paymentTermsOptions=getTextData("Payment terms option");
		String clientBU=getTextData("Client BU");
		
		clickOnHiddenElement("Home link@xpath=//a[@title='Home']");
		sleep(2);
		clickOnHiddenElement("Procurement link@xpath=//div[@id='groupNode_procurement']");
		sleep(2);
		clickOnHiddenElement("Suppliers link@id=itemNode_procurement_suppliers");
		clickOnHiddenElement(CashManagementPage.taskPanel);
		clickOnHiddenElement("Create Supplier link @xpath=(//a[contains(text(),'Create Supplier')])[1]");
		sendKeys("Suppliers textbox@xpath=//label[contains(text(),'Supplier')]/parent::td/parent::tr//input", suppliersText);
		selectTextOptionFromDropdown("Business Relationship dropdwon@xpath=//label[contains(text(),'Business Relationship')]/parent::td/parent::tr//select", businessRelationship);
		selectTextOptionFromDropdown("Tax Organization Type dropdown@xpath=//label[contains(text(),'Tax Organization Type')]/parent::td/parent::tr//select", taxOrganizationType);
		clickOnHiddenElement("Create button@xpath=//button[text()='Create']");
		clickOnHiddenElement("Addresses link@xpath=(//a[contains(text(),'Addresses')])/parent::div/parent::div[contains(@id,'Tab')]");
		clickOnHiddenElement("Add symbol button@xpath=//img[@title='Create']");
		sendKeys("Address name testxbox@xpath=//label[contains(text(),'Address Name')]/parent::td/parent::tr//input",addressName);
		sendKeys("Country textbox@xpath=//label[contains(text(),'Country')]/parent::td/parent::tr//input",country);
		clickOnHiddenElement(country1+" option link@xpath=//li[text()='"+country1+"']");
		
		sendKeys("Address name testxbox@xpath=//label[contains(text(),'Address Name')]/parent::td/parent::tr//input",addressName);
		clickOnHiddenElement("Ordering@xpath=//label[contains(text(),'Ordering')]/parent::span//input");
		clickOnHiddenElement("Remit to@xpath=//label[contains(text(),'Remit to')]/parent::span//input");
		clickOnHiddenElement("RFQ or Bidding@xpath=//label[contains(text(),'RFQ or Bidding')]/parent::span//input");
		
		//sendKeys("Address 1 testxbox@xpath=//label[contains(text(),'Address Line 1')]/parent::td/parent::tr//input",address1);
		//sendKeys("Address 2 testxbox@xpath=//label[contains(text(),'Address Line 2')]/parent::td/parent::tr//input",address2);
		sleep(5);
		sendKeys("City testxbox@xpath=//label[contains(text(),'City')]/parent::td/parent::tr//input",city);
		sendKeys("State testxbox@xpath=//label[contains(text(),'State')]/parent::td/parent::tr//input",state);
		sendKeys("Postal Code testxbox@xpath=//label[contains(text(),'Postal Code')]/parent::td/parent::tr//input",postalCode);
		
		
		takeScreenshot("");
		//clickElement("Save button@xpath=//span[contains(text(),'Save')]");
		clickElement("Save and close button@xpath=//span[contains(text(),'ave and Close')]");
		waitForElementToDisplay("Addresses link@xpath=//a[contains(text(),'"+addressName+"')]", pageElementLoadWaitTime);
		sleep(5);
		takeScreenshot("");
		clickOnHiddenElement("Confirmation popup Ok button@xpath=//button[contains(@id,'ok')]");
		clickElement("Sites link@xpath=(//a[contains(text(),'Sites')])/parent::div/parent::div[contains(@id,'Tab')]");
		waitForElementToDisplay("Procurement BU table header@xpath=//span[contains(text(),'Procurement BU')]", pageElementLoadWaitTime);
		sleep(3);
		clickOnHiddenElement("Add symbol button@xpath=//img[@title='Create']");
		sendKeys("Address Name textbox@xpath=//label[contains(text(),'Address Name')]/parent::td/parent::tr//input",addressName);
		clickOnHiddenElement(addressName+" option link@xpath=(//li[contains(text(),'"+addressName+"')])[1]");
		clickElement("Save and close button@xpath=(//span[contains(text(),'ave and Close')])[2]");
		sleep(10);
		clickElement("Save button@xpath=(//span[contains(text(),'Save')])[1]");
		sleep(5);
		clickOnHiddenElement("Invoicing tab@xpath=(//a[contains(text(),'Invoicing')])[1]");
		sendKeys("Payment Terms textbox@xpath=//label[contains(text(),'Payment Terms')]/parent::td/parent::tr//input",paymentTerms);
		clickOnHiddenElement(paymentTermsOptions+" option link@xpath=//li[text()='"+paymentTermsOptions+"']");
		takeScreenshot("");
		clickOnHiddenElement("Invoicing tab@xpath=(//a[contains(text(),'Site Assignments')])[1]");
		sleep(3);
		clickOnHiddenElement("Add symbol button@xpath=//img[@title='Add']");
		sleep(3);
		clickOnHiddenElement("Client BU dropdown@xpath=//a[@title='Search: Client BU']");
		sleep(3);
		clickOnHiddenElement("Client BU dropdown@xpath=//a[@title='Search: Client BU']");
		takeScreenshot("");
		clickOnHiddenElement(clientBU+" option link@xpath=//span[text()='"+clientBU+"']");
		sleep(3);
		//takeScreenshot("");
		clickElement("Save and close button@xpath=(//span[contains(text(),'ave and Close')])[2]");
		/*
		 * clickOnHiddenElement("Site textbox@xpath=(//label[contains(text(),'Site')]/parent::td/parent::tr//input[@type='text'])[1]"
		 * );
		 */
		sleep(5);
		takeScreenshot("");
		clickElement("Save and close button@xpath=(//span[contains(text(),'ave and Close')])[2]");
		waitForElementToDisplay("Sites link@xpath=(//a[contains(text(),'Sites')])/parent::div/parent::div[contains(@id,'Tab')]", pageElementLoadWaitTime);
		sleep(5);
		takeScreenshot("");
		clickOnHiddenElement("Confirmation popup Ok button@xpath=//button[contains(@id,'ok')]");
		stepPassed("Verified sucessfully created supplier with addresses and sites");
		supplierName=suppliersText;
		
		clickOnHiddenElement("Home link@xpath=//a[@title='Home']");
		sleep(2);
		clickOnHiddenElement("Procurement link@xpath=//div[@id='groupNode_procurement']");
		sleep(2);
		clickOnHiddenElement("Suppliers link@id=itemNode_procurement_suppliers");
		clickOnHiddenElement(CashManagementPage.taskPanel);
		clickOnHiddenElement("Manage Suppliers link @xpath=(//a[contains(text(),'Manage Suppliers')])[1]");
		sendKeys("Keywords textbox@xpath=//label[contains(text(),'Keywords')]/parent::td/parent::tr//input", suppliersText);
		clickOnHiddenElement("Search button@xpath=//button[contains(text(),'Search')]");
		clickOnHiddenElement(suppliersText+"@xpath=//a[text()='"+suppliersText+"']");
		selectTextOptionFromDropdown("Supplier type dropdown@xpath=//label[text()='Supplier Type']/parent::td/parent::tr//select", supplierType);
		sleep(5);
		takeScreenshot("");
		clickElement("Save and close button@xpath=(//span[contains(text(),'ave and Close')])[2]");
		waitForElementToDisplay("Keywords textbox@xpath=//label[contains(text(),'Keywords')]/parent::td/parent::tr//input", pageElementLoadWaitTime);
		sleep(5);
		takeScreenshot("");
		clickOnHiddenElement("Confirmation popup Ok button@xpath=(//button[contains(@id,'ok')])[1]");
		stepPassed("Verified sucessfully updated supplier type");
	}
	
	public void createInVoice()
	{
		//String supplierName="Test Supplier-1559240961";
		//String supplierText="Test Supplier-1559240961";
		if((supplierName.equalsIgnoreCase("")))
		{
			stepFailed("Create supplier testcase not executed or failed");
		}
		
		String number=getTextData("txtInvoiceName")+getRandomNumber();
		String amount=getTextData("txtAmount");
		
		//String disturbutionID="14-0100-000-21510-00-00-000";
		String disturbutionID=getFrameworkConfigProperty("Distribution combination");
		clickOnHiddenElement("Home link@xpath=//a[@title='Home']");
		sleep(2);
		clickOnHiddenElement("Payables link@xpath=//*[contains(text(),'Payables')]");
		sleep(2);
		clickOnHiddenElement("Invoices link@xpath=//a[contains(text(),'Invoices')]");
		clickOnHiddenElement(CashManagementPage.taskPanel);
		clickOnHiddenElement("Create Supplier link @xpath=(//a[contains(text(),'Create Invoice')])[1]");
		sendKeys("Supplier textbox@xpath=(//label[contains(text(),'Supplier')]/parent::td/parent::tr//input)[1]", supplierName);
		clickOnHiddenElement(supplierName+" option link@xpath=(//li[contains(text(),'"+supplierName+"')])[1]");
		sendKeys("Amount textbox@xpath=(//label[contains(text(),'Amount')]/parent::td/parent::tr//input)[1]", amount);
		sendKeys("Number textbox@xpath=(//label[contains(text(),'Number')]/parent::td/parent::tr//input)[1]", number);
		
		sleep(5);
		clickOnHiddenElement("Expand Lines @xpath=//h1[text()='Lines']/parent::div/parent::td/parent::tr//a");
		sendKeys("Amount textbox@xpath=(//label[text()='Amount']/parent::span/input)[2]", amount);
		sendKeys("Distribution Combination@xpath=//input[@aria-label='Distribution Combination ID']", disturbutionID);
		clickOnHiddenElement("Distribution Combination ID image@xpath=//img[contains(@title,'Distribution Combination ID')]");
		clickOnHiddenElement("Ok button@xpath=//span[text()='K']");
		sleep(5);
		clickElement("Save buuton@xpath=//span[text()='Save']");
		waitForElement("Not validated link@xpath=//a[contains(text(),'Not validated')]");
		clickOnHiddenElement("Invoice actions@xpath=//a[contains(text(),'Invoice Actions')]");
		clickOnHiddenElement("Approval link@xpath=//td[text()='Approval']");
		clickOnHiddenElement("Force Approve link@xpath=//td[text()='Force Approve']");
		clickElement("Save and close button@xpath=(//span[contains(text(),'ave and Close')])[1]");
		waitForElementToDisplay(CashManagementPage.taskPanel, pageElementLoadWaitTime);
		takeScreenshot("");
		stepPassed("Created invoice successfully");
		invoiceName=number;
		
	}
	public void createAssetInVoice()
	{
		//String supplierName="Test Supplier-1559240961";
		//String number="TestingInvoice"+getRandomNumber();
		if((supplierName.equalsIgnoreCase("")))
		{
			stepFailed("Create supplier testcase not executed or failed");
		}
		
		
		if((invoiceName.equalsIgnoreCase("")))
		{
			stepFailed("Create invoice testcase not executed or failed");
		}
		invoiceName=getTextData("txtInvoiceName")+getRandomNumber();
		String amount=getTextData("txtAmount");
	//	String disturbutionID="14-0100-000-21510-00-00-000";
		String disturbutionID=getFrameworkConfigProperty("Distribution combination");
		clickOnHiddenElement("Home link@xpath=//a[@title='Home']");
		sleep(2);
		clickOnHiddenElement("Payables link@xpath=//*[contains(text(),'Payables')]");
		sleep(2);
		clickOnHiddenElement("Invoices link@xpath=//a[contains(text(),'Invoices')]");
		clickOnHiddenElement(CashManagementPage.taskPanel);
		clickOnHiddenElement("Create Supplier link @xpath=(//a[contains(text(),'Create Invoice')])[1]");
		sendKeys("Supplier textbox@xpath=(//label[contains(text(),'Supplier')]/parent::td/parent::tr//input)[1]", supplierName);
		clickOnHiddenElement(supplierName+" option link@xpath=(//li[contains(text(),'"+supplierName+"')])[1]");
		sendKeys("Amount textbox@xpath=(//label[contains(text(),'Amount')]/parent::td/parent::tr//input)[1]", amount);
		sendKeys("Number textbox@xpath=(//label[contains(text(),'Number')]/parent::td/parent::tr//input)[1]", invoiceName);
		sleep(7);
		clickOnHiddenElement("Expand Lines @xpath=//h1[text()='Lines']/parent::div/parent::td/parent::tr//a");
		sendKeys("Amount textbox@xpath=(//label[text()='Amount']/parent::span/input)[2]", amount);
		sendKeys("Distribution Combination@xpath=//input[@aria-label='Distribution Combination ID']", disturbutionID);
		clickOnHiddenElement("Distribution Combination ID image@xpath=//img[contains(@title,'Distribution Combination ID')]");
		clickOnHiddenElement("Ok button@xpath=//span[text()='K']");
		sleep(5);
		clickOnHiddenElement("Asset checkbox@xpath=(//label[text()='Book']/parent::span/parent::span/parent::td/parent::tr//input[@type='checkbox'])[2]/parent::span//div");
		takeScreenshot("");
		clickElement("Save and close button@xpath=(//span[contains(text(),'ave and Close')])[1]");
		waitForElementToDisplay(CashManagementPage.taskPanel, pageElementLoadWaitTime);
		takeScreenshot("");
		stepPassed("Created Asset invoice successfully");
		
	}
	public void runMassadditions()
	{
		String todayDate=getTextData("txtDate");
		String assetBook=getFrameworkConfigProperty("Asset Book");
		
		clickOnHiddenElement("Home link@xpath=//a[@title='Home']");
		sleep(2);
		clickOnHiddenElement("Payables link@xpath=//*[contains(text(),'Payables')]");
		sleep(2);
		clickOnHiddenElement("Invoices link@xpath=//a[contains(text(),'Invoices')]");
		clickOnHiddenElement(CashManagementPage.taskPanel);
		clickOnHiddenElement("Create Supplier link @xpath=(//a[contains(text(),'Create Mass Additions')])[1]");
		sendKeys("Accounting Date textbox@xpath=(//label[contains(text(),'Accounting Date')]/parent::td/parent::tr//input)[1]", todayDate);
		selectTextOptionFromDropdown("Asset book dropdown@xpath=//label[text()='Asset Book']/parent::td/parent::tr//select", assetBook);
		clickOnHiddenElement("Submit@xpath=//span[contains(text(),'Sub')]");
		sleep(5);
		String processID = getElementText("Confirmation popup Containtainer @xpath=//div[contains(@id,'confirmPopup')]//td[contains(@id,'contentContainer')]//label").replaceAll("[^0-9]", "");
		clickOnHiddenElement("Ok button@xpath=//div[contains(@id,'confirmSubmitDialog')]//button[text()='OK']");
		takeScreenshot("");
		clickOnHiddenElement("Home link@xpath=//a[@title='Home']");
		sleep(2);
		clickOnHiddenElement("Payables link@xpath=//*[contains(text(),'Tools')]");
		sleep(2);
		clickOnHiddenElement("Invoices link@xpath=//a[contains(text(),'Scheduled Processes')]");
		sleep(5);
		clickOnHiddenElement("Expand search@xpath=//h1[text()='Search']/parent::div/parent::td/parent::tr//a");
		sendKeys("Process ID textbox@xpath=(//label[contains(text(),'Process ID')]/parent::td/parent::tr//input)[1]", processID);
		clickOnHiddenElement("Search button@xpath=//button[text()='Search']");
		String processIDroText=getElementText("@xpath=//span[text()='"+processID+"']/parent::td/parent::tr");
		takeScreenshot("");
		stepPassed(processIDroText);
		
	}
	public void manageInvoice()
	{
		//String supplierName="Test Supplier-1559240961";
		//String invoiceName="TestingInvoice103";
		if((supplierName.equalsIgnoreCase("")))
		{
			stepFailed("Create supplier testcase not executed or failed");
		}
		
		
		if((invoiceName.equalsIgnoreCase("")))
		{
			stepFailed("Create invoice testcase not executed or failed");
		}
		//String amount="100";
		//String disturbutionID="14-0100-000-21510-00-00-000";
		String amount=getTextData("txtAmount");
		//String disturbutionID="14-0100-000-21510-00-00-000";
	    String disturbutionID=getFrameworkConfigProperty("Distribution combination");
		
		
		clickOnHiddenElement("Home link@xpath=//a[@title='Home']");
		sleep(2);
		clickOnHiddenElement("Payables link@xpath=//*[contains(text(),'Payables')]");
		sleep(2);
		clickOnHiddenElement("Invoices link@xpath=//a[contains(text(),'Invoices')]");
		clickOnHiddenElement(CashManagementPage.taskPanel);
		clickOnHiddenElement("Create Supplier link @xpath=(//a[contains(text(),'Manage Invoices')])[1]");
		sendKeys("Invoice Number textbox@xpath=(//label[contains(text(),'Invoice Number')]/parent::td/parent::tr//input)[1]", invoiceName);
		clickOnHiddenElement("Search button@xpath=//button[text()='Search']");
		clickOnHiddenElement(invoiceName+" link@xpath=//a[text()='"+invoiceName+"']");
		waitForElementToDisplay("Actions dropdown@xpath=//a[contains(text(),'Actions')]", pageElementLoadWaitTime);
		sleep(5);
		clickOnHiddenElement("Actions dropdown@xpath=//a[contains(text(),'Actions')]");
		clickOnHiddenElement("Edit link@xpath=//td[contains(text(),'Edit')]");
		int beforeRowCount=getElementMatchingXpathCount("//tr[@_afrrk]");
		clickOnHiddenElement("Add button@xpath=//img[@title='Add Row']");
		sleep(3);
		sendKeys("Amount textbox@xpath=((//label[text()='Amount']/parent::span/input)[2])[1]", amount);
		sendKeys("Distribution Combination@xpath=(//input[@aria-label='Distribution Combination ID'])[1]", disturbutionID);
		clickOnHiddenElement("Distribution Combination ID image@xpath=(//img[contains(@title,'Distribution Combination ID')])[1]");
		clickOnHiddenElement("Ok button@xpath=//span[text()='K']");
		sleep(3);
		takeScreenshot("");
		stepPassed("Added new row");
		clickOnHiddenElement("Delete button@xpath=//img[@title='Delete']");
		sleep(8);
		takeScreenshot("");
		stepPassed("Deleted newly created row");
		clickElement("Save and close button@xpath=(//span[contains(text(),'ave and Close')])[1]");
		waitForElementToDisplay("Invoice Details@xpath=//h1[text()='Invoice Details']", pageElementLoadWaitTime);
		takeScreenshot("");	
	}
	public void createCreditMemo()
	{
		//String supplierName="Test Supplier-1559240961";
		//String number="TestingInvoice"+getRandomNumber();
		if((supplierName.equalsIgnoreCase("")))
		{
			stepFailed("Create supplier testcase not executed or failed");
		}
		
		
		if((invoiceName.equalsIgnoreCase("")))
		{
			stepFailed("Create invoice testcase not executed or failed");
		}
		invoiceName=getTextData("txtInvoiceName")+getRandomNumber();
		//String amount="-100";
		//String disturbutionID="14-0100-000-21510-00-00-000";
		String amount=getTextData("txtAmount");
		//String disturbutionID="14-0100-000-21510-00-00-000";
	    String disturbutionID=getFrameworkConfigProperty("Distribution combination");
		String type="Credit memo";
		clickOnHiddenElement("Home link@xpath=//a[@title='Home']");
		sleep(2);
		clickOnHiddenElement("Payables link@xpath=//*[contains(text(),'Payables')]");
		sleep(2);
		clickOnHiddenElement("Invoices link@xpath=//a[contains(text(),'Invoices')]");
		clickOnHiddenElement(CashManagementPage.taskPanel);
		clickOnHiddenElement("Create Supplier link @xpath=(//a[contains(text(),'Create Invoice')])[1]");
		clickElement("Show More link @xpath=//a[text()='Show More']");
		sleep(5);
		sendKeys("Supplier textbox@xpath=(//label[contains(text(),'Supplier')]/parent::td/parent::tr//input)[1]", supplierName);
		clickOnHiddenElement(supplierName+" option link@xpath=(//li[contains(text(),'"+supplierName+"')])[1]");
		sendKeys("Amount textbox@xpath=(//label[contains(text(),'Amount')]/parent::td/parent::tr//input)[1]", amount);
		sendKeys("Number textbox@xpath=(//label[contains(text(),'Number')]/parent::td/parent::tr//input)[1]", invoiceName);
		
		selectTextOptionFromDropdown("Type dropdown@xpath=//label[contains(text(),'Type')]/parent::td/parent::tr//select", type);
		waitForElementToDisplay("Message dialog@xpath=//div[contains(@id,'msgDlg')]//span", pageElementLoadWaitTime);
		
		takeScreenshot("");
		clickOnHiddenElement("Ok button@xpath=//div[contains(@id,'msgDlg')]//button");
		clickOnHiddenElement("Expand Lines @xpath=//h1[text()='Lines']/parent::div/parent::td/parent::tr//a");
		sendKeys("Amount textbox@xpath=(//label[text()='Amount']/parent::span/input)[2]", amount);
		sendKeys("Distribution Combination@xpath=//input[@aria-label='Distribution Combination ID']", disturbutionID);
		clickOnHiddenElement("Distribution Combination ID image@xpath=//img[contains(@title,'Distribution Combination ID')]");
		clickOnHiddenElement("Ok button@xpath=//span[text()='K']");
		sleep(5);
		clickElement("Save buuton@xpath=//span[text()='Save']");
		waitForElement("Not validated link@xpath=//a[contains(text(),'Not validated')]");
		clickOnHiddenElement("Invoice actions@xpath=//a[contains(text(),'Invoice Actions')]");
		clickOnHiddenElement("Approval link@xpath=//td[text()='Approval']");
		clickOnHiddenElement("Force Approve link@xpath=//td[text()='Force Approve']");
		clickElement("Save and close button@xpath=(//span[contains(text(),'ave and Close')])[1]");
		waitForElementToDisplay(CashManagementPage.taskPanel, pageElementLoadWaitTime);
		takeScreenshot("");
		stepPassed("Created invoice successfully");
		
	}
	
	public void createPaymentProcessTemplate()
	{
		String payThroughDays=getTextData("txtPayThroughDays");
		String fromPaymentPriority=getTextData("txtFromPaymentPriority");
		String toPaymentPriority=getTextData("txtToPaymentPriority");
		//String name = "NIGS_ Check_Template"+getRandomNumber();
		String name = getFrameworkConfigProperty("Payment Process Request Template Name")+getRandomNumber();
		String description ="Testing";
		//String disbursementBankAccount="TRIL INVESTOR";
		String disbursementBankAccount=getFrameworkConfigProperty("Disbursement Bank Account");
		
		String paymentDocument=getFrameworkConfigProperty("Payment Document");
		String paymentProcessProfile=getFrameworkConfigProperty("Payment Process Profile");
		
		clickOnHiddenElement("Home link@xpath=//a[@title='Home']");
		clickOnHiddenElement("Payables link@xpath=//*[contains(text(),'Payables')]");
		clickOnHiddenElement("Invoices link@xpath=//a[contains(text(),'Payments')]");
		clickOnHiddenElement(CashManagementPage.taskPanel);
		clickOnHiddenElement("Manage Payment Process Request Templates link @xpath=(//a[contains(text(),'Manage Payment Process Request Templates')])[1]");
		
		
		
		
		clickOnHiddenElement("Actions dropdown@xpath=//a[contains(text(),'Actions')]");
		clickOnHiddenElement("Create link@xpath=//td[contains(text(),'Create')]");
		
		clickOnHiddenElement("Selection Criteria link@xpath=//a[contains(text(),'Selection Criteria')]");
		sendKeys("Number textbox@xpath=(//label[contains(text(),'Pay Through Days')]/parent::td/parent::tr//input)[1]", payThroughDays);
		clickOnHiddenElement("Pay Groups@xpath=//label[contains(text(),'Pay Groups')]/parent::td/parent::tr//input");
		clickOnHiddenElement("Currencies@xpath=//label[contains(text(),'Currencies')]/parent::td/parent::tr//input");
		clickOnHiddenElement("Business Units@xpath=//label[contains(text(),'Business Units')]/parent::td/parent::tr//input");
		clickOnHiddenElement("Legal Entities@xpath=//label[contains(text(),'Legal Entities')]/parent::td/parent::tr//input");
		clickOnHiddenElement("Sources@xpath=//label[contains(text(),'Sources')]/parent::td/parent::tr//input");
		sendKeys("Name textbox@xpath=(//label[contains(text(),'Name')]/parent::td/parent::tr//input)[1]", name);
		sendKeys("Dscription textbox@xpath=(//label[contains(text(),'Description')]/parent::td/parent::tr//textarea)[1]", description);
		clickOnHiddenElement("Payment and Processing Options link@xpath=//a[contains(text(),'Payment and Processing Options')]");
		sendKeys("Disbursement Bank Account textbox@xpath=(//label[contains(text(),'Disbursement Bank Account')]/parent::td/parent::tr//input)[1]", disbursementBankAccount);
		clickOnHiddenElement(disbursementBankAccount+" option link@xpath=(//li[contains(text(),'"+disbursementBankAccount+"')])[1]");
		sleep(5);
		clickOnHiddenElement("Payment Date@xpath=//label[contains(text(),'Payment Date')]/parent::td/parent::tr//input/following-sibling::label[text()='Use request date']");
		clickOnHiddenElement("Payment Document textbox@xpath=(//label[contains(text(),'Payment Document')]/parent::td/parent::tr//a)[1]");
		sleep(2);
		sendKeysHidden("Payment Document textbox@xpath=(//label[contains(text(),'Payment Document')]/parent::td/parent::tr//input)[1]", paymentDocument);
		sendKeysHidden("Payment Process Profile textbox@xpath=(//label[contains(text(),'Payment Process Profile')]/parent::td/parent::tr//input)[1]", paymentProcessProfile);
		clickOnHiddenElement("Review installments checkbox@xpath=//label[text()='Review installments']");
		clickOnHiddenElement("Review proposed payments checkbox@xpath=//label[text()='Review proposed payments']");
		clickOnHiddenElement("Create payment files immediately checkbox@xpath=//label[text()='Create payment files immediately']");
		clickElement("Save and close button@xpath=(//span[contains(text(),'ave and Close')])[1]");
		waitForElementToDisplay("Actions dropdown@xpath=//a[contains(text(),'Actions')]",pageElementLoadWaitTime);
		sleep(5);
		takeScreenshot("");
		clickOnHiddenElement("Confirmation popup Ok button@xpath=(//button[contains(@id,'ok')])[1]");
		stepPassed("Verified popup message");
		
		
	}
		
		

}
