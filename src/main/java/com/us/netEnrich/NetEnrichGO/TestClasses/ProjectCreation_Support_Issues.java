package com.us.netEnrich.NetEnrichGO.TestClasses;
import com.us.netEnrich.NetEnrichGO.NetEnrichGoDriver;
import com.us.netEnrich.NetEnrichGO.Utilities.GlobalMethods;
import com.us.netEnrich.NetEnrichGO.Utilities.ReadConfigFile;
import java.io.FileInputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProjectCreation_Support_Issues {
	//Declaring the local variables to store the runtime data.
	String testData,runTestcase,testCaseId,strDesc,strExpres,strActres,strStatus;
	String clientUserName,clientPassword,serviceReqMessbyClient;
	int expRowNumber =1;
	boolean isException = false;
	WebDriver driver;
	int rownumber=0;
	XSSFWorkbook testdatawb;
	int testdatarown=0;
	//creating the instance to ReadConfigFile.java to read configuration properties
	ReadConfigFile rc = new ReadConfigFile();
	//Creating the instance to log4j class to log the messages
	static Logger log4j=Logger.getLogger(ProjectCreation_Support_Issues.class.getName());
	//Declaring a method to retrieve the test data (cases) and execute these test cases
	public void projectCreationandSupoort(GlobalMethods globalobj,String result_Path,String module_Name,String subModule_Name) {
		
		try {
			testData=globalobj.testDataPath(module_Name,subModule_Name);
			testdatawb =new  XSSFWorkbook(new FileInputStream(testData));
			DataFormatter formatter = new DataFormatter();
			XSSFSheet s = testdatawb.getSheetAt(0);
			//iterate all test cases available in the test data sheet 
			for (rownumber = expRowNumber; rownumber <=s.getLastRowNum(); rownumber++) {
				
				runTestcase=formatter.formatCellValue(s.getRow(rownumber).getCell(0));
				testCaseId= formatter.formatCellValue(s.getRow(rownumber).getCell(1));
				strDesc= formatter.formatCellValue(s.getRow(rownumber).getCell(2));
				clientUserName=formatter.formatCellValue(s.getRow(rownumber).getCell(4));
				clientPassword=formatter.formatCellValue(s.getRow(rownumber).getCell(5));
				serviceReqMessbyClient=formatter.formatCellValue(s.getRow(rownumber).getCell(6));
				serviceReqMessbyClient=globalobj.uniqueString(serviceReqMessbyClient);
				if (runTestcase.equalsIgnoreCase("Yes")){
					driver = globalobj.openBrowser(rc.getApplicationURL());
					globalobj.loginNetenrichapplication(driver, clientUserName, clientPassword);
					driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
					//Enter message in chatInput text field
					driver.findElement(globalobj.getbjectLocator("ChatInputbox")).sendKeys(serviceReqMessbyClient);
					Thread.sleep(3000);
					driver.findElement(globalobj.getbjectLocator("ChatInputbox")).sendKeys(Keys.ENTER);
					Thread.sleep(4000);
					//calling a method to validate the message displayed in Collabaration Area
					boolean chatmsflag=globalobj.validateChatMessageandClick(serviceReqMessbyClient);
					if (chatmsflag) {
						strExpres="Chat Message entered by client user should be displayed in Collabaration chat Area";
						strActres= "Chat Message entered by client user is displayed in Collabaration chat Area";
						strStatus="Pass";
						//Updating the test results.
						globalobj.updateTestResult(module_Name, subModule_Name, testCaseId, strDesc, strExpres, strActres, strStatus, result_Path);
						log4j.info("Chat Message entered by client user is displayed in Collabaration chat Area");
					}
					else {
						strExpres="Chat Message entered by client user should be displayed in Collabaration chat Area";
						strActres= "Chat Message entered by client user is not displayed in Collabaration chat Area";
						strStatus="Fail";
						//Updating the test results.
						globalobj.updateTestResult(module_Name, subModule_Name, testCaseId, strDesc, strExpres, strActres, strStatus, result_Path);
						log4j.info("Chat Message entered by client user is not displayed in Collabaration chat Area");
						
						
					}
					//Logout from the application and Close the browser
					globalobj.logOut(driver);
					globalobj.closeBrowser(driver);
				}
				else {
					
				}
				
			}
			
		}
		catch(Exception e) {
			System.out.println("The error message is" +e);
			
		}
		
	}

}
