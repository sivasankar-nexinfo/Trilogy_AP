package Objects;

public interface CashManagementPage {
	String userName = "Username textbox@id=userid";
	String password="Password textbox@id=password";
	String submit = "Submit button@id=btnActive";
	String navigator="Navigator menu @xpath=//a[@title='Navigator']";
	String bankStatementsAndRecon="bank_statements_and_reconciliation @xpath=//*[contains(@id,'bank_statements_and_reconciliation')]";
	String taskPanel="Task panel@xpath=//div[@title='Tasks']";
	String createBankStatement="Create bank statements link @xpath=(//a[contains(text(),'Create Bank Statement')])[1]";
	String bankAccount="Bank Account dropdown@xpath=//input[contains(@id,'bankaccountnameId')]";
	String saveAndClose="Save and Close@xpath=//button[contains(text(),'Close')]";
	String periodStartDate="Period start date textbox@xpath=(//input[contains(@id,'periodStartDateId')])[1]";
	String periodEndDate="Period start date textbox@xpath=(//input[contains(@id,'periodEndDateId')])[1]";
	String statementId="Statement Id textbox@xpath=//input[contains(@id,'cbsinputText1')]";
}
