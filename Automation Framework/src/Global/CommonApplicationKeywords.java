package Global;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;

import org.apache.camel.Navigate;
import org.openqa.selenium.By;

import Objects.CashManagementPage;

public class CommonApplicationKeywords extends Utilities.Selenium_Appium_Keywords {

	public boolean loginStatus=false;
	public String randomNumbers() {
		Random random = new Random();
		int randomInteger = 0;
		for (int i = 0; i < 3; i++) {
			randomInteger = random.nextInt();

		}
		return reportFileName;

	}

	public void login() {

		if(!(loginStatus))
		{
		String userName = getFrameworkConfigProperty("Username");
		String password = getFrameworkConfigProperty("Password");
		goTo(getFrameworkConfigProperty("URL"));
		sendKeys(CashManagementPage.userName, userName);
		sendKeys(CashManagementPage.password, password);
		clickOnHiddenElement(CashManagementPage.submit);
		loginStatus=true;
		}

	}

}