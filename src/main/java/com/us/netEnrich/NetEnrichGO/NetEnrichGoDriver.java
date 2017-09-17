package com.us.netEnrich.NetEnrichGO;
//Importing required packages.
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.us.netEnrich.NetEnrichGO.Utilities.GlobalMethods;
import com.us.netEnrich.NetEnrichGO.Utilities.ReadConfigFile;

import org.apache.log4j.Logger;

//This is a main driver class will control entire test execution. This class is responsible
//of loading all the components of framework.

public class NetEnrichGoDriver{
	
	//Declaring variables to store the run time values
			int k=0;
			String module_Name;
			String subModule_Name,execute;
			String result_Path;
			String workspaceName;
			boolean isExceptiondriver=false;
			int expRowNumberdriver=1;
			DataFormatter formatter1;
			String testdatadriver;
			static Logger log4j=Logger.getLogger(NetEnrichGoDriver.class.getName());
			//creating the object for the globalMethods class which is implemented the reusableMethods interface.
			GlobalMethods globalobj = new GlobalMethods();
			/*
			This method is responsible for loading main module, submodule from Driver.xls sheet based on the
			condition in the Execute column.  It will also create the result folder and result file, and load the
			reusable action class to execute the corresponding module test cases.*/
			public void load_Module_Drive(){
				//Loading driver.xls to decide the modules which user wants to run
				try{
					ReadConfigFile rc=new ReadConfigFile();
					testdatadriver=rc.getDriverTestData();
					//This block of code is implemented using Apache POI api as Microsoft 2010 is used as a test data sheet.
					XSSFWorkbook wb = new  XSSFWorkbook(new FileInputStream(testdatadriver));
				    XSSFSheet sheet1 = wb.getSheetAt(0);
				    DataFormatter formatter1=new DataFormatter();
				    for (k = expRowNumberdriver; k <=sheet1.getLastRowNum(); k++) {
				    	System.out.println("the value of the k is   "+k);
				    	module_Name=formatter1.formatCellValue(sheet1.getRow(k).getCell(0));
				    	subModule_Name=formatter1.formatCellValue(sheet1.getRow(k).getCell(1));
				    	execute=formatter1.formatCellValue(sheet1.getRow(k).getCell(2));
				    	workspaceName=formatter1.formatCellValue(sheet1.getRow(k).getCell(3));
				    	if (execute.equalsIgnoreCase("Yes")){
				    		System.out.println("Userwants to load the module"+" "+module_Name+"and sub Module "+subModule_Name);
				    		result_Path=globalobj.resultFile(module_Name,subModule_Name);
				    	    Reusable_Action ra = new Reusable_Action();
				    		ra.load_Testcase_Method(globalobj,result_Path,module_Name,subModule_Name);
				    		
				    	}else
							System.out.println("Please open the Driver.xls sheet and select the Execute value as Yes to load the module"+" "+module_Name);
				    }
				}catch (Exception ex){
					//isExceptiondriver=true;
					log4j.error("Test Data file is doesn't exist in the expected folder"+"  " +testdatadriver);
					ex.printStackTrace();
				}
				finally{
					if(isExceptiondriver == true){
						System.out.println("Value of the k in finally blcok of driver class  "+k);
						expRowNumberdriver= k +1;
						isExceptiondriver = false;
						load_Module_Drive();
					}
				}
				
			}
			public static void main (String args[]){
				try{
					NetEnrichGoDriver d = new NetEnrichGoDriver();
				d.load_Module_Drive();
				}catch(Exception e){
			  		System.out.println("In catch block");
			  		e.printStackTrace();
				}
			}


    
}
