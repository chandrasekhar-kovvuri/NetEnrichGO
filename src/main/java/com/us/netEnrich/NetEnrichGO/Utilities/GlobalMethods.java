package com.us.netEnrich.NetEnrichGO.Utilities;
//Importing the required packages
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.us.netEnrich.NetEnrichGO.*;
//import org.openqa.selenium.firefox.FirefoxDriver;

interface reusableMethods{
	//Declaring a variables to store constant values
	//public static final String URL = "file://C:/Chandra/Selenium_Automation/CreateAccount.htm";
	//Declaring a method to create an excel file to store the result (pass/fail status)
	public String resultFile(String mainModule,String subModule) throws IOException;
	//Declaring a method to start a Browser
	//public DefaultSelenium openBrowser()throws Exception;
	public WebDriver openBrowser(String Url)throws Exception;
	//Declaring a method to close the Browser
	public void closeBrowser(WebDriver driver) throws Exception;
	//Declaring a method to return a test data sheet path
	public String testDataPath(String module_Name,String subModule_Name) throws Exception;
	//Declaring a method to write the test results into an excel sheet.
	//public void updateTestResult(module_Name,subModule_Name)throws Exception;//,testCaseId,strDesc,strExpres,strActres
	public void updateTestResult(String module_Name,String subModule_Name,String testCaseId,String strDesc,String strExpres,String strActres,String strStatus,String result_Path)throws Exception;
	//Declaring a method to read the object repository values from Object Repository propertiels file and 
	//Declaring a method to buiding the tag value
	public By getbjectLocator(String locatorName);
	//Declaring a method to Login into application
	public void loginNetenrichapplication(WebDriver driver,String username,String password);
	
}



public class GlobalMethods implements reusableMethods {
	//Declaring a local variables to store the run time values
		//public DefaultSelenium selenium;
		public String file_Path,test_Res_Path;
		public File file,file1,file2;
		WebDriver driver;
		//creating the instance to ReadConfigFile.jave to load configuration properties.
		ReadConfigFile rc=new ReadConfigFile();
		//creating the static instance to log4j class
		static Logger log4j=Logger.getLogger(GlobalMethods.class.getName());
		
		//This method is useful to create a excel file and save into the local drive.
		public String resultFile(String mainModule,String subModule){
			try{
				file_Path=rc.getResultFilePath();
				if (subModule.isEmpty()) {
					//file_Path=file_Path+"\\Result\\"+mainModule+"\\";
					file_Path=file_Path+mainModule+"\\";
					file = new File(file_Path);
					if(file.exists()==false){ 
						file.mkdir(); 
			        } 
					file_Path=file_Path+mainModule+".xlsx";
					test_Res_Path=file_Path;
				}else{
					//file_Path=file_Path+"\\Maven_Automation"+"\\Result\\"+mainModule+"\\"+subModule+"\\";
					//file_Path=file_Path+"\\Result\\"+mainModule+"\\"+subModule+"\\";
					file_Path=file_Path+mainModule+"\\"+subModule+"\\";
					file = new File(file_Path); 
					if(file.exists()==false){ 
						file.mkdirs(); 
			        } 
					file_Path=file_Path+mainModule+"_"+subModule+".xlsx";
					test_Res_Path=file_Path;
				}
				file1 = new File(test_Res_Path);
				if (!file1.exists()){
					XSSFWorkbook wb = new XSSFWorkbook(); 
					XSSFSheet resultsheet=wb.createSheet("Result");
					XSSFRow row= resultsheet.createRow(0);
					row.createCell(0).setCellValue("TestcaseID");
					row.createCell(1).setCellValue("Step_Description");
					row.createCell(2).setCellValue("Expected_Result");
					row.createCell(3).setCellValue("Actual_Result");
					row.createCell(4).setCellValue("Status");
					FileOutputStream fileOut = new FileOutputStream(file1); 
			        wb.write(fileOut); 
			        fileOut.close(); 
			        wb.close();
				    file_Path ="";
				    }
				
			}catch (IOException e){
				log4j.error("Result file is not created in resultFile method of globalmethods.java file");
				System.out.println("Please check the result path:"+e);
				e.printStackTrace();
			}
			return test_Res_Path;
			
		}
		
		//This method is useful to update the test result into the excel sheet.
		public void updateTestResult(String module_Name,String subModule_Name,String testCaseId,String strDesc,String strExpres,String strActres,String strStatus,String result_Path){
			
			try{
				XSSFWorkbook wb = new  XSSFWorkbook(new FileInputStream(result_Path));
			    XSSFSheet sheet1 = wb.getSheetAt(0);
			    int lastrowno=sheet1.getLastRowNum();
			    XSSFRow row= sheet1.createRow(lastrowno+1);
			    row.createCell(0).setCellValue(testCaseId);
			    row.createCell(1).setCellValue(strDesc);
			    row.createCell(2).setCellValue(strExpres);
			    row.createCell(3).setCellValue(strActres);
			    row.createCell(4).setCellValue(strStatus);
			    file2 = new File(result_Path);
			    FileOutputStream fileOut = new FileOutputStream(file2); 
		        wb.write(fileOut); 
		        fileOut.close();
		        wb.close();
			    
			}catch (IOException e){
				log4j.error("test results file is created or updating in updateTestResult method of global methods.jave file ");
				e.printStackTrace();
			}
		}
		//This method is useful to return the test data path based on the module name
		public String testDataPath(String module_Name,String subModule_Name){
			String testdataPath=rc.getModuleTestData();
			try {
				System.out.println("The path of test data sheet is  "+testdataPath);
				//String testdataPath="F:/DWAutomationGithub/Maven_Automation/TestData/";
				testdataPath =testdataPath+module_Name+".xlsx";
				System.out.println("The path of test data sheet is  "+testdataPath);
				
				
			}
			catch(Exception e) {
				log4j.error("Module test data file path is wrong in testDataPath method of global methods.jave file ");
				
			}
			return testdataPath;
			
		}
		//This method is useful to open the application 
		public WebDriver openBrowser(String URL) throws WebDriverException{
			
			System.setProperty("webdriver.chrome.driver",rc.getChromePath());
			driver=new ChromeDriver();
			try {
				driver.manage().window().maximize();
				driver.navigate().to(URL);
				driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
				
			}
			catch(WebDriverException e){
				log4j.error("Application is not responding or timeout. Refer OPenBrowser method "+e);
			}
			return driver;	
		}
		//This Method is useful to logout from the application
		public void logOut(WebDriver driver) {
			try {
				
				driver.findElement(getbjectLocator("LogOut")).click();
				Thread.sleep(2000);
				driver.findElement(getbjectLocator("SignOut")).click();
			}
			catch(Exception e){
				log4j.error("User is unable to logout from the application "+e);
			}
		}
		
		//This method is useful to login into NetenrichGo application based on the test data values
		public void loginNetenrichapplication(WebDriver driver,String username,String password) {
			
			try {
				//enter the username and password and login into application
				driver.findElement(getbjectLocator("ClientUsername")).sendKeys(username);
				driver.findElement(getbjectLocator("SubmitLogin")).click();
				driver.findElement(getbjectLocator("ClientPassword")).sendKeys(password);
				driver.findElement(getbjectLocator("SubmitLogin")).click();
				
			}
			catch(Exception e) {
				log4j.error("Unable to login into application successfully "+e);
				
			}
			
			
		}
	
		//This method is useful to close the Browser and stop the selenium server.
		public void closeBrowser(WebDriver driver){
			try{
				driver.close();
			}
				
	        catch (WebDriverException e)
	        {
	            log4j.error("Not able to close the browser. Refer closeBrowser method "+e);
			}
			
	    }
		
		//This Method is useful to read the webElements from .properties file and return that locator to corresponding method
		public By getbjectLocator(String locatorName){
			By locator=null;
			try{
				FileInputStream stream;
				String RepositoryFile;
				//RepositoryFile=System.getProperty("user.dir");
				RepositoryFile=rc.getLoginPageObjects();
				Properties propertyFile = new Properties();
	            //RepositoryFile =RepositoryFile+"\\ObjectRepository\\ObjectRepository.properties";
	            stream = new FileInputStream(RepositoryFile);
				propertyFile.load(stream);
				String locatorProperty = propertyFile.getProperty(locatorName);
				String locatorType = locatorProperty.split(":")[0];
				String locatorValue = locatorProperty.split(":")[1];
	            switch(locatorType)
				{
				case "Id":
					locator = By.id(locatorValue);
					break;
				case "Name":
					locator = By.name(locatorValue);
					break;
				case "CssSelector":
					locator = By.cssSelector(locatorValue);
					break;
				case "LinkText":
					locator = By.linkText(locatorValue);
					break;
				case "PartialLinkText":
					locator = By.partialLinkText(locatorValue);
					break;
				case "TagName":
					locator = By.tagName(locatorValue);
					break;
				case "Xpath":
					locator = By.xpath(locatorValue);
					break;
				}
				
			}catch (Exception e)
	        {
				log4j.error("Not able to read the webelement value from objectrepository.properties file"+e);
				e.printStackTrace();
			}
			return locator;
			
		}
		//This Method is useful to validate the chat message entered by logged in user 
		public boolean validateChatMessageandClick(String messageChat) {
			boolean validatemessageflag=false;
			String chatMessage="";
			//driver.findElement(By.xpath(".//*[@id=\"idOrgRegPath\"]")).click();
			try {
				Thread.sleep(5000);
				List<WebElement> conversationList=driver.findElements(getbjectLocator("ListofCollabarationMessages"));
				int cnt=conversationList.size();
				for (int i=1;i<=cnt;i++){
					String ChatXpath=rc.getChatMessageXpath();
					ChatXpath=MessageFormat.format(ChatXpath,"'","'",i);
					chatMessage=driver.findElement(By.xpath(ChatXpath)).getText();
					if(chatMessage.equalsIgnoreCase(messageChat)) {
						driver.findElement(By.xpath(ChatXpath)).click();
						validatemessageflag=true;
					}
					
				}
				
			}
			catch(Exception e){
				System.out.println(e);
				log4j.error("Chart Message entered by user is not identified by selenium webdriver"+e);
				
			}
			return validatemessageflag;
		}
		//This Method will return the unique string 
		public String uniqueString(String chatMessage) {
			
			String msChat=chatMessage;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
				Date date = new Date();
				msChat=msChat.concat(sdf.format(date));
				String s= String.valueOf(System.currentTimeMillis());
				msChat=msChat.concat(s.substring(7));
		}
		catch(Exception e) {
			log4j.info("Unique String is not created.  There is some problem in java code"+e);
		}
		return msChat;	
			
		}
			
		
	
}
