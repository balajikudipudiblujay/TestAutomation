package global.reusables;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;

public class BackUpTestCaseHistory {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		GenericMethods.backUpHistoryFile();
		//setDefaultCount("D:\\Workspace\\KF\\KF_3.2\\Configurations\\KFPICTScenario_Chrome_history.xlsx");

	}

	public static void backUpHistoryFile(String dir,String bkpDir) {
		int daysInMonth=0;
		int isleapYear=0;
      
        //getting time, date, day of week and other details in GMT timezone
        Calendar gmtCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        //rest of stuff are same
        int currentDay = gmtCalendar.get(Calendar.DATE);
       // int currentDay = 10;
        int currentMonth = gmtCalendar.get(Calendar.MONTH) + 1;
        int currentYear = gmtCalendar.get(Calendar.YEAR);
        int currentDayOfWeek = gmtCalendar.get(Calendar.DAY_OF_WEEK);
        int currentDayOfMonth = gmtCalendar.get(Calendar.DAY_OF_MONTH);
        int CurrentDayOfYear = gmtCalendar.get(Calendar.DAY_OF_YEAR);
        String folder=Integer.toString(currentDay)+"-"+Integer.toString(currentMonth)+"-"+Integer.toString(currentYear);
       /* System.out.println("Current Date and time details in local timezone");
        System.out.println("Current Day: " + currentDay);
        System.out.println("Current Month: " + currentMonth);
        System.out.println("Current Year: " + currentYear);
        System.out.println("Current Day of Week: " + currentDayOfWeek);
        System.out.println("Current Day of Month: " + currentDayOfMonth);
        System.out.println("Current Day of Year: " + CurrentDayOfYear);*/
		BackUpTestCaseHistory historyFiles = new BackUpTestCaseHistory();  
		if((currentYear % 400 == 0) || ((currentYear % 4 == 0) && (currentYear % 100 != 0))){
           // System.out.println("Year " + currentYear + " is a leap year");
            isleapYear=1;
		 daysInMonth = 31 - ((currentMonth == 2) ?
			      (3 - isleapYear) : ((currentMonth - 1) % 7 % 2));
		}
		else if(currentMonth == 2){
			 daysInMonth = 31 - ((currentMonth == 2) ?
				      (3 - isleapYear) : ((currentMonth - 1) % 7 % 2));
		}
	    else
	    {			daysInMonth = 31 - (currentMonth - 1) % 7 % 2;
	            //System.out.println("Year " + currentYear + " is not a leap year");
	    }
		System.out.println("daysInMonth:::::"+daysInMonth);
         historyFiles.backUpFilesOlderThanNdays(daysInMonth,currentDay,dir,bkpDir,folder);
	}
	
	public void backUpFilesOlderThanNdays(int daysBack, int calDate,String dirWay,String bkpDir,String folder)  
    {  
        File directory = new File(dirWay);  
        if(directory.exists())  
        {     
            File[] listFiles = directory.listFiles();  
            long purgeTime = System.currentTimeMillis() - ((long)daysBack * 24 * 60 * 60 * 1000);   
            for(File listFile : listFiles)  
            { // System.out.println("listFile.lastModified()===="+listFile.lastModified());
            	if( listFile.getName().endsWith(".xlsx")){
	                if(daysBack==calDate)  
	                {   
	                   // System.out.println("File or directory name: " + listFile);  
	                    if (listFile.isFile())  
	                    {  
	                    	copyExcel(listFile.getName(),dirWay,bkpDir,folder);
	                        /*System.out.println("This is a file: " + listFile);  
	                        System.out.println("daysBack" + daysBack);  
	                        System.out.println("calDate: " + calDate);*/  
	                        
	                    }    
	                    else  
	                    {  
	                    	backUpFilesOlderThanNdays(daysBack,calDate, listFile.getAbsolutePath(),bkpDir,folder);  
	                    }  
	                }  else{
	                	System.out.println("daysBack:::::"+daysBack+" is greater than calDate::::"+calDate);
	                }
	            } 
            }
        }   
        else  
        	System.out.println("Files were not deleted, directory " + dirWay + " does'nt exist!");  
    } 
	
	public static void copyExcel(String fileName,String dirWay,String bkpDir,String folder)
	{ 	

		
		String bkpFile =dirWay+"\\"+fileName;
		File dest= new File(bkpDir+"\\"+folder+"\\"+fileName);
		File source=new File(bkpFile);
		try {
			FileUtils.copyFile(source, dest);
			try {
				setDefaultCount(bkpFile);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		

	}
	
	public static void setDefaultCount(String bkpFile) throws ClassNotFoundException, SQLException{
		Connection UpdateCon = DBConnectionManager
			.getConnection(bkpFile);
		try {
			 System.out.println("update bkpFile "+bkpFile);
			 
			 Statement mainScenarioSTMT = UpdateCon.createStatement();
			ResultSet mainScenariors = mainScenarioSTMT
		        .executeQuery("select * from [Driver$] where Slno ='1'" );
			 while (mainScenariors.next()) {
			System.out.println(mainScenariors.getString("TestCaseHistory"));
			 }
			
			 int TestCaseHistory=1;
			 UpdateCon.prepareStatement(
			 			"update [Driver$] set TestCaseHistory = '"+TestCaseHistory+"'").execute();
		
		
			 System.out.println("Updated Succesfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       finally{
    	   UpdateCon.close();
       }
	}
}
