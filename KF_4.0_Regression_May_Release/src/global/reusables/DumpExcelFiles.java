package global.reusables;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class DumpExcelFiles {
	public static String configurationStructurePath = System.getProperty("user.dir")+"\\Configurations\\base.properties";
	
	/*@Modified By: Sandeep Jain
	 * @Modified description: copying the workbooks which are present in Primary TestData to Virtual Testdata.
	 * @Modified Date Oct 27th 2014
	 */
	public static void main(String[] args)  {
		String SchemaNames=null;
		/*SchemaNames = GenericMethods.getPropertyValue("SchemaName",configurationStructurePath);
		System.out.println("schema names ="+SchemaNames);
		String[] tb=SchemaNames.split(",");
		for(int i=0;i<tb.length;i++)
		{
			copyExcel(tb[i]);
		}*/
		String resultXl;
		String relPath=System.getProperty("user.dir");
		String sourcePath=relPath+"\\TestData\\PrimaryTestData\\";
		resultXl=relPath+"\\TestData\\VirtualTestData\\";
		File source=new File(sourcePath);
		File dest= new File(resultXl);
		try {

			FileUtils.copyDirectory(source, dest);
			//FileUtils.copyFile(source, dest);

		} catch (IOException e) {

			e.printStackTrace();
		}
		

	}
	/**
	 * This method will copy the Schemas from primaryTestData to VirtualTestData.
	 * @param schemaName Schema Name to be copied.
	 * @author Priyaranjan
	 */
	public static void copyExcel(String schemaName)
	{ 	

		String resultXl;
		String relPath=System.getProperty("user.dir");
		String sourcePath=relPath+"\\TestData\\PrimaryTestData\\"+schemaName;
		resultXl=relPath+"\\TestData\\VirtualTestData\\"+schemaName;
		File source=new File(sourcePath);
		File dest= new File(resultXl);
		try {

			//FileUtils.copyDirectory(source, dest);
			FileUtils.copyFile(source, dest);

		} catch (IOException e) {

			e.printStackTrace();
		}
		

	}
	
	/**
	 * @param SourcePath = Provide the location path from where file need to be copied.
	 * @param DestinationPath= Provide the location path where file need to be copied.
	 * @author Sandeep Jain
	 * @Date April 22nd 2015
	 */
	public static void copyExcel(String SourcePath, String DestinationPath)
	{ 	
		/*String relPath=System.getProperty("user.dir");
		String sourcePath=relPath+"\\TestData\\PrimaryTestData\\"+schemaName;
		resultXl=relPath+"\\TestData\\VirtualTestData\\"+schemaName;*/
		File Source=new File(SourcePath);
		File Destination= new File(DestinationPath);
		try {
			FileUtils.copyFile(Source, Destination);
		} catch (IOException e) {e.printStackTrace();}
	}

}
