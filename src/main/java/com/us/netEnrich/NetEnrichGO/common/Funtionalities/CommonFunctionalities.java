package com.us.netEnrich.NetEnrichGO.common.Funtionalities;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.MessageFormat;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import com.us.netEnrich.NetEnrichGO.Utilities.*;

//This class contains functions which can be used across application while automating.
//We can reuse same function in many places as per need.
public class CommonFunctionalities {
	
			//Creating objects to required classes
			GlobalMethods globalobj=new GlobalMethods();
			static Logger log4j=Logger.getLogger(CommonFunctionalities.class.getName());
			ReadConfigFile rc=new ReadConfigFile();
	
	       //This method is useful to login into NetenrichGo application based on the test data values
			public void loginNetenrichapplication(WebDriver driver,String username,String password) {
				
				try {
					//enter the username and password and login into application
					driver.findElement(globalobj.getbjectLocator("ClientUsername")).sendKeys(username);
					driver.findElement(globalobj.getbjectLocator("SubmitLogin")).click();
					driver.findElement(globalobj.getbjectLocator("ClientPassword")).sendKeys(password);
					driver.findElement(globalobj.getbjectLocator("SubmitLogin")).click();
					Thread.sleep(7000);
				}
				catch(Exception e) {
					System.out.println("Catch block of loginNetenrichapplication");
					log4j.error("Unable to login into application successfully "+e);
					
				}
				
				
			}
			//This Method is useful to logout from the application
			public void logOut(WebDriver driver) {
				try {
					
					driver.findElement(globalobj.getbjectLocator("LogOut")).click();
					Thread.sleep(2000);
					driver.findElement(globalobj.getbjectLocator("SignOut")).click();
				}
				catch(Exception e){
					System.out.println("Catch block of logout");
					log4j.error("User is unable to logout from the application "+e);
				}
			}
			//This message is useful to enter the message chatInput text field
			public void enterChatMessage(String chatMessage,WebDriver driver) {
				try {
					//Enter message in chatInput text field
					driver.findElement(globalobj.getbjectLocator("ChatInputbox")).sendKeys(chatMessage);
					Thread.sleep(3000);
					driver.findElement(globalobj.getbjectLocator("ChatInputbox")).sendKeys(Keys.ENTER);
					Thread.sleep(4000);
					
				}
				catch(Exception e) {
					System.out.println("Catch block of enterChatMessage");
					log4j.error("ChatInput text field is not displaying" +e);
					
				}
			}
			
			//This Method is useful to validate the chat message entered by logged in user 
			public boolean validateChatMessageandClick(String messageChat,WebDriver driver) {
				boolean validatemessageflag=false;
				String chatMessage="";
				//driver.findElement(By.xpath(".//*[@id=\"idOrgRegPath\"]")).click();
				try {
					Thread.sleep(5000);
					List<WebElement> conversationList=driver.findElements(globalobj.getbjectLocator("ListofCollabarationMessages"));
					int cnt=conversationList.size();
					System.out.println("The count of conversation list is " +cnt);
					for (int i=1;i<=cnt;i++){
						String ChatXpath=rc.getChatMessageXpath();
						String chatMessageId=rc.getChatMessageIdXpath();
						ChatXpath=MessageFormat.format(ChatXpath,"'","'",i);
						System.out.println(ChatXpath);
						//chatMessageId=MessageFormat.format(chatMessageId,"'","'",i);
						chatMessage=driver.findElement(By.xpath(ChatXpath)).getText();
						if(chatMessage.equalsIgnoreCase(messageChat)) {
							driver.findElement(By.xpath(ChatXpath)).click();
							validatemessageflag=true;
						}
						
					}
					
				}
				catch(Exception e){
					System.out.println("Catch block of validateChatMessageandClick");
					System.out.println(e);
					log4j.error("Chart Message entered by user is not identified by selenium webdriver"+e);
					
				}
				return validatemessageflag;
			}
			
		

}
