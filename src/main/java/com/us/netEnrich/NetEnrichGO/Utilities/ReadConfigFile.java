package com.us.netEnrich.NetEnrichGO.Utilities;
//importing required packages
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.us.netEnrich.NetEnrichGO.NetEnrichGoDriver;



public class ReadConfigFile {
	//Declaring properties class to load the configuration properties
	Properties propertyFile = new Properties();
	//Declaring the log4j instance 
	static Logger log4j=Logger.getLogger(NetEnrichGoDriver.class.getName());
	
	public ReadConfigFile() {		

		try {
			File src = new File("./ConfigFile.properties");
			FileInputStream fs = new FileInputStream(src);
			propertyFile.load(fs);
		 } catch (Exception ex) {
			 log4j.info("::::::: ConfigFile is Not Found in the Path ::::: " +ex);
		 }			
	}
	//This method will return the path of the chrome driver.exe
	public String getChromePath()  {

		String path = null;
		try {
			path = propertyFile.getProperty("ChromeDriver");

		} catch (Exception ex) {
			log4j.info("::::::: ChromeDriver Not Found in the Path ::::: " + path);
		}
		return path;
	}
	//this method will return the path of firefox driver.exe
	public String getFirefoxPath()  {
		String path = null;
		try {
			path = propertyFile.getProperty("FireFoxDriver");

		} catch (Exception ex) {
			log4j.error("::::::: FireFoxDriver Not Found in the Path ::::: " + path);
		}
		return path;
	}
	//This method will return the Internet explorer path.
	public String getIEPath()  {

		String path = null;
		try {
			path = propertyFile.getProperty("IEDriver");

		} catch (Exception ex) {
			log4j.info("::::::: IEDriver Not Found in the Path ::::: " +path);
		}
		return path;
	}
	//This method will return the application URL
	public String getApplicationURL()  {
		String url = null;
		try {
			url = propertyFile.getProperty("ApplicationUrl");
			return url;
		} catch (Exception e) {
			log4j.info("::::::: Application URL Not Found  ::::: " +url);
		}
		return url;
	}
	//This method will return the Driver.xlsx file path
	public String getDriverTestData()  {
		String drivertestdata = null;
		try {
			drivertestdata = propertyFile.getProperty("DriverExcel");
			return drivertestdata;
		} catch (Exception e) {
			log4j.info("::::::: Driver.xlsx file Not Found in the path  ::::: " +drivertestdata);
		}
		return drivertestdata;
	}
	//This method will return the Module specific test data sheet.
	public String getModuleTestData()  {
		String Moduletestdata = null;
		try {
			Moduletestdata = propertyFile.getProperty("ModuleTestDataPath");
			return Moduletestdata;
		} catch (Exception e) {
			log4j.info("::::::: Module specific test data sheet Not Found in the path  ::::: " +Moduletestdata);
		}
		return Moduletestdata;
	}
	
	//This method will return the test results file path.
		public String getResultFilePath()  {
			String resultFilePath = null;
			try {
				resultFilePath = propertyFile.getProperty("ResultFilePath");
				return resultFilePath;
			} catch (Exception e) {
				log4j.info("::::::: Result folder is not there in project repository  ::::: " +resultFilePath);
			}
			return resultFilePath;
		}
		//This method will return the path of collabrationpage objects.
		public String getCollabarationPageObjects()  {
			String collaPageObjects = null;
			try {
				collaPageObjects = propertyFile.getProperty("CollabrationPage");
				return collaPageObjects;
			} catch (Exception e) {
				log4j.info("::::::: Path for Collabaration page objects is not there in project repository  ::::: " +collaPageObjects);
			}
			return collaPageObjects;
		}
		//This method will return the path of Login page objects.
		public String getLoginPageObjects()  {
			String LoginPageObjects = null;
			try {
				LoginPageObjects = propertyFile.getProperty("LoginPage");
				return LoginPageObjects;
			} catch (Exception e) {
				log4j.info("::::::: Path for Login page objects is not there in project repository  ::::: " +LoginPageObjects);
			}
			return LoginPageObjects;
		}
		//This method will return the path of WorkAreaPage page objects.
		public String getWorkAreaPageobjects()  {
			String workAreaPage = null;
			try {
				workAreaPage = propertyFile.getProperty("WorkAreaPage");
				return workAreaPage;
			} catch (Exception e) {
				log4j.info("::::::: Path for WorkAreaPage page objects file is not there in project repository  ::::: " +workAreaPage);
			}
			return workAreaPage;
		}
		
		//This method will return the path of workModePannel page objects.
		public String getWorkModePannelPageobjects()  {
			String workModePannel = null;
			try {
				workModePannel = propertyFile.getProperty("WorkModePannel");
				return workModePannel;
			} catch (Exception e) {
				log4j.info("::::::: Path for WorkModePannel page objects file is not there in project repository  ::::: " +workModePannel);
			}
			return workModePannel;
		}
		//This method will return the path of the chrome driver.exe
		public String getChatMessageXpath()  {

			String path = null;
			try {
				File src = new File("./ObjectRepository/LoginPage.properties");
				FileInputStream fs = new FileInputStream(src);
				propertyFile.load(fs);
				path = propertyFile.getProperty("ChatMessage");

			} catch (Exception ex) {
				log4j.info("::::::: Chat Message Xpath is Not Found in the Path ::::: " + path);
			}
			return path;
		}
		//This method will return the path of the chrome driver.exe
				public String getChatMessageIdXpath()  {

					String path = null;
					try {
						File src = new File("./ObjectRepository/LoginPage.properties");
						FileInputStream fs = new FileInputStream(src);
						propertyFile.load(fs);
						path = propertyFile.getProperty("ChatMessageId");

					} catch (Exception ex) {
						log4j.info("::::::: Chat Message ID Xpath is Not Found in the Path ::::: " + path);
					}
					return path;
				}
		
}
