package com.us.netEnrich.NetEnrichGO;

import com.us.netEnrich.NetEnrichGO.TestClasses.ProjectCreation_Support_Issues;
import com.us.netEnrich.NetEnrichGO.Utilities.GlobalMethods;
import org.apache.log4j.Logger;

public class Reusable_Action {
	
	//Declaring the log4j object.
	static Logger log4j=Logger.getLogger(Reusable_Action.class.getName());
	
	public void load_Testcase_Method(GlobalMethods globalobj,String result_Path,String module_Name,String subModule_Name)throws Exception{
		//writing switch case statement and loading the corresponding java class 
		//This class is responsible for drive the test cases related to module
		if (module_Name.equalsIgnoreCase("Collaboration")){
			Reusable_Action.check_Sub_Module_Execution(globalobj,result_Path,module_Name,subModule_Name);
		}else if(module_Name.equalsIgnoreCase("DWWorkspace")){
	    	Reusable_Action.check_Sub_Module_Execution(globalobj,result_Path,module_Name,subModule_Name);
		}
	
	}
	public static void check_Sub_Module_Execution(GlobalMethods globalobj,String result_Path,String module_Name,String subModule_Name) throws Exception{
		//in this class checking that whether main module is having sub module or not
		//If main module is having any sub module then creating a class and loading the corresponding method to execute
		//the test cases.
		if (subModule_Name.equalsIgnoreCase("CreateProjectandChat")){
			ProjectCreation_Support_Issues ps=new ProjectCreation_Support_Issues();
			ps.projectCreationandSupoort(globalobj,result_Path,module_Name,subModule_Name);
			System.out.println("executing the submodule test cases."+subModule_Name);
			log4j.info("************************"+"executing the submodule test cases."+subModule_Name+"**************************");
		}else if(subModule_Name.equalsIgnoreCase("WorkspaceCreation")){
			//DWworkspace_Creation wc=new DWworkspace_Creation();
			
		}//closing else if
		else{
			System.out.println("Submodule is not a valid module of"+" "+module_Name+"Open the Driver.xlsx and verify the modules");
		    log4j.error("Submodule is not a valid module of"+" "+module_Name+"Open the Driver.xlsx and verify the modules");
		}
	}//closing method

}//closing class
