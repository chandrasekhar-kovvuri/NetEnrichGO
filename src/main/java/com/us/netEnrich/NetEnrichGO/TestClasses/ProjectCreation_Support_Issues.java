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
import com.us.netEnrich.NetEnrichGO.common.Funtionalities.*;

public class ProjectCreation_Support_Issues {
	//Declaring the local variables to store the runtime data.
	String testData,runTestcase,testCaseId,strDesc,strExpres,strActres,strStatus;
	String clientUserName,clientPassword,serviceReqMessbyClient;
	String partnerUserId,partnerPassword,serReqReplybyPartner,partnerSerReqMessToSpUser;
	int expRowNumber =1;
	boolean isException = false;
	WebDriver driver;
	int rownumber=0;
	XSSFWorkbook testdatawb;
	int testdatarown=0;
	//creating the instance to ReadConfigFile.java to read configuration properties
	ReadConfigFile rc = new ReadConfigFile();
	//creating the instance to CommonFunctionalities to access the methods
	CommonFunctionalities common=new CommonFunctionalities();
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
				
				//Reading test data from Collaboration module data sheet.
				read_testdata_Collaboration(s,rownumber,formatter,globalobj);
				//Based on this condition test cases will be executed
				if (runTestcase.equalsIgnoreCase("Yes")){
					//Calling method to open browser
					driver = globalobj.openBrowser(rc.getApplicationURL());
					//Calling method to login into application
					common.loginNetenrichapplication(driver, clientUserName, clientPassword);
					//Waiting for page load
					driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
					//Calling method to enter the chat message into ChatInput text field
					common.enterChatMessage(serviceReqMessbyClient,driver);
					//calling a method to validate the message displayed in Collabaration Area
					boolean chatmsflag=common.validateChatMessageandClick(serviceReqMessbyClient,driver);
					if (chatmsflag) {
						strExpres="Chat Message entered by client user should be displayed in Collaboration chat Area";
						strActres= "Chat Message entered by client user is displayed in Collaboration chat Area";
						strStatus="Pass";
						//Updating the test results.
						globalobj.updateTestResult(module_Name, subModule_Name, testCaseId, strDesc, strExpres, strActres, strStatus, result_Path);
						log4j.info("Chat Message entered by client user is displayed in Collabaration chat Area");
					}
					else {
						strExpres="Chat Message entered by client user should be displayed in Collaboration chat Area";
						strActres= "Chat Message entered by client user is not displayed in Collaboration chat Area";
						strStatus="Fail";
						//Updating the test results.
						globalobj.updateTestResult(module_Name, subModule_Name, testCaseId, strDesc, strExpres, strActres, strStatus, result_Path);
						log4j.info("Chat Message entered by client user is not displayed in Collaboration chat Area");
									
					}
					//Logout from the application and Close the browser
					/*common.logOut(driver);
					globalobj.closeBrowser(driver);
					//Calling method to Open browser and logging as Partner user
					driver = globalobj.openBrowser(rc.getApplicationURL());
					common.loginNetenrichapplication(driver, partnerUserId, partnerPassword);
					driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
					//Clicking Channel to display the respective conversation messages
					driver.findElement(globalobj.getbjectLocator("ClientChannel")).click();
					Thread.sleep(7000);
					//Calling method to validate the chat message is displayed or not for partner user
					boolean chatmsflag1=common.validateChatMessageandClick(serviceReqMessbyClient,driver);
					//Updating the test results 
					if (chatmsflag1) {
						strExpres="Chat Message entered by client user should be displayed to associated partner user";
						strActres= "Chat Message entered by client user is displayed to associate partner user";
						strStatus="Pass";
						//Updating the test results.
						globalobj.updateTestResult(module_Name, subModule_Name, testCaseId, strDesc, strExpres, strActres, strStatus, result_Path);
						log4j.info("Chat Message entered by client user is displayed to associate partner user");
					}
					else {
						strExpres="Chat Message entered by client user should be displayed to associated partner user";
						strActres= "Chat Message entered by client user is not displayed to associate partner user";
						strStatus="Fail";
						//Updating the test results.
						globalobj.updateTestResult(module_Name, subModule_Name, testCaseId, strDesc, strExpres, strActres, strStatus, result_Path);
						log4j.info("Chat Message entered by client user is not displayed to associate partner user");
					}
					//Calling Method to reply the client message by partner user
					common.enterChatMessage(serReqReplybyPartner,driver);*/
					//calling Method to invite the Service provider user to look into the client service request
				}
				else {
					
				}
				
			}
			
		}
		catch(Exception e) {
			System.out.println("Catch block of ProjectCreation_Support_Issues" );
			System.out.println("The error message is" +e);
			
		}
		
	}
	//This method is useful to Read the test data from Collaboration excel sheet
	public void read_testdata_Collaboration(XSSFSheet s,int rownumber,DataFormatter formatter,GlobalMethods globalobj) {
		try {
			runTestcase=formatter.formatCellValue(s.getRow(rownumber).getCell(0));
			testCaseId= formatter.formatCellValue(s.getRow(rownumber).getCell(1));
			strDesc= formatter.formatCellValue(s.getRow(rownumber).getCell(2));
			clientUserName=formatter.formatCellValue(s.getRow(rownumber).getCell(4));
			clientPassword=formatter.formatCellValue(s.getRow(rownumber).getCell(5));
			serviceReqMessbyClient=formatter.formatCellValue(s.getRow(rownumber).getCell(6));
			serviceReqMessbyClient=globalobj.uniqueString(serviceReqMessbyClient);
			partnerUserId=formatter.formatCellValue(s.getRow(rownumber).getCell(7));
			partnerPassword=formatter.formatCellValue(s.getRow(rownumber).getCell(8));
			serReqReplybyPartner=formatter.formatCellValue(s.getRow(rownumber).getCell(9));
			partnerSerReqMessToSpUser=formatter.formatCellValue(s.getRow(rownumber).getCell(10));
			
		}
		catch(Exception e) {
			log4j.error("Unable to read the data from test data sheet of Collaborations" +e);
			
		}
	}

}
