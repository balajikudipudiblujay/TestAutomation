package business.scenarios;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;

import org.apache.commons.collections.functors.OrPredicate;
import org.apache.poi.hssf.util.HSSFColor.ORANGE;
import org.apache.xml.utils.NSInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.opera.core.systems.scope.protos.EcmascriptProtos.EvalResult.Status;
import common.Assert;

import GlobalReusables.webElement;
import app.reuseables.AppReusableMethods;
import app.reuseables.ETransMenu;
import global.reusables.ExcelUtils;
import global.reusables.GenericMethods;
import global.reusables.ObjectRepository;

public class OceanAssertions extends GenericMethods {

	static ObjectRepository orCommon = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/CommonObjects.xml");
	static ObjectRepository orSeaBooking = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/SeaBooking.xml");
	static ObjectRepository orOceanPRS = new ObjectRepository(System.getProperty("user.dir") + "/ObjectRepository/OceanPRS.xml");
	static ObjectRepository orOceanArrival = new ObjectRepository(System.getProperty("user.dir")+ "/ObjectRepository/OceanArrivals.xml");
	static ObjectRepository orOceanExpOBL=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/OceanExportOBL.xml");
	static ObjectRepository orHBL=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/HBL.xml");
	static ObjectRepository orSalesInvoiceEnq=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/SalesInvoiceEnquiry.xml");
	static ObjectRepository orProfitShareSatlement=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/ProfitShareSatlement.xml");
	static ObjectRepository orChargeCost=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/ChargesAndCosts.xml");
	static ObjectRepository orSalesInvoice=new ObjectRepository(System.getProperty("user.dir")+"/ObjectRepository/SalesInvoice.xml");
	static ObjectRepository oROceanNsibObl = new ObjectRepository(System.getProperty("user.dir") + "/ObjectRepository/OceanImportNSIB.xml");

	public static void assertSeaDifferentUOM(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) {
		String intitialUOM=ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "DefaultUOM", ScenarioDetailsHashMap);
		selectOptionFromDropDown(driver, null,  orSeaBooking.getElement(driver, "DropDown_MaterUOM", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UOM", ScenarioDetailsHashMap));
		int rows=orSeaBooking.getElements(driver, "Grid_PackDetailsRows", ScenarioDetailsHashMap, 10).size();
		for (int i = 0; i < rows; i++) {

			String value="";
			if(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UOM", ScenarioDetailsHashMap).equals("IN")&& intitialUOM.equalsIgnoreCase("CM")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Length", ScenarioDetailsHashMap))*0.393701, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-8-"+i+"-box-text"), null, value+"", "Length", "Validating Lenght Conversion In UOM for CM To IN", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Width", ScenarioDetailsHashMap))*0.393701, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-9-"+i+"-box-text"), null, value+"", "Length", "Validating Width Conversion In UOM CM To IN", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Height", ScenarioDetailsHashMap))*0.393701, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-10-"+i+"-box-text"), null, value+"", "Length", "Validating Height Conversion In UOM CM To IN", ScenarioDetailsHashMap);

			}else if(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UOM", ScenarioDetailsHashMap).equals("FT")&& intitialUOM.equalsIgnoreCase("CM")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Length", ScenarioDetailsHashMap))*0.0328084, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-8-"+i+"-box-text"), null, value+"", "Length", "Validating Lenght Conversion In UOM CM To FT", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Width", ScenarioDetailsHashMap))*0.0328084, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-9-"+i+"-box-text"), null, value+"", "Length", "Validating Width Conversion In UOM CM To FT", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Height", ScenarioDetailsHashMap))*0.0328084, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-10-"+i+"-box-text"), null, value+"", "Length", "Validating Height Conversion In UOM CM To FT", ScenarioDetailsHashMap);

			}else if(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UOM", ScenarioDetailsHashMap).equals("CM")&& intitialUOM.equalsIgnoreCase("IN")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Length", ScenarioDetailsHashMap))*2.54, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-8-"+i+"-box-text"), null, value+"", "Length", "Validating Lenght Conversion In UOM IN To CM", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Width", ScenarioDetailsHashMap))*2.54, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-9-"+i+"-box-text"), null, value+"", "Length", "Validating Width Conversion In UOM IN To CM", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Height", ScenarioDetailsHashMap))*2.54, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-10-"+i+"-box-text"), null, value+"", "Length", "Validating Height Conversion In UOM IN To CM", ScenarioDetailsHashMap);

			}else if(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UOM", ScenarioDetailsHashMap).equals("FT")&& intitialUOM.equalsIgnoreCase("IN")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Length", ScenarioDetailsHashMap))*0.0833333, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-8-"+i+"-box-text"), null, value+"", "Length", "Validating Lenght Conversion In UOM IN To FT", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Width", ScenarioDetailsHashMap))*0.0833333, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-9-"+i+"-box-text"), null, value+"", "Length", "Validating Width Conversion In UOM IN To FT", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Height", ScenarioDetailsHashMap))*0.0833333, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-10-"+i+"-box-text"), null, value+"", "Length", "Validating Height Conversion In UOM IN To FT", ScenarioDetailsHashMap);

			}else if(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UOM", ScenarioDetailsHashMap).equals("CM")&& intitialUOM.equalsIgnoreCase("FT")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Length", ScenarioDetailsHashMap))*30.48, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-8-"+i+"-box-text"), null, value+"", "Length", "Validating Lenght Conversion In UOM FT TO CM", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Width", ScenarioDetailsHashMap))*30.48 , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-9-"+i+"-box-text"), null, value+"", "Length", "Validating Width Conversion In UOM FT TO CM", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Height", ScenarioDetailsHashMap))*30.48 , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-10-"+i+"-box-text"), null, value+"", "Length", "Validating Height Conversion In UOM FT TO CM", ScenarioDetailsHashMap);

			}else if(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UOM", ScenarioDetailsHashMap).equals("IN")&& intitialUOM.equalsIgnoreCase("FT")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Length", ScenarioDetailsHashMap))*12, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-8-"+i+"-box-text"), null, value+"", "Length", "Validating Lenght Conversion In UOM FT To IN", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Width", ScenarioDetailsHashMap))*12, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-9-"+i+"-box-text"), null, value+"", "Length", "Validating Width Conversion In UOM FT To IN", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "Height", ScenarioDetailsHashMap))*12, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-10-"+i+"-box-text"), null, value+"", "Length", "Validating Height Conversion In UOM FT To IN", ScenarioDetailsHashMap);

			}
		}
	}

	public static void assertSeaDifferentUOMHBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) {
		String intitialUOM=ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "DefaultUOM", ScenarioDetailsHashMap);
		selectOptionFromDropDown(driver, null,  orSeaBooking.getElement(driver, "DropDown_MaterUOM", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOM", ScenarioDetailsHashMap));
		int rows=orSeaBooking.getElements(driver, "Grid_PackDetailsRows", ScenarioDetailsHashMap, 10).size();
		for (int i = 0; i < rows; i++) {

			String value="";
			if(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOM", ScenarioDetailsHashMap).equals("IN")&& intitialUOM.equalsIgnoreCase("CM")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Length", ScenarioDetailsHashMap))*0.393701, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-8-"+i+"-box-text"), null, value+"", "Length", "Validating Lenght Conversion In UOM for IN & CM", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Width", ScenarioDetailsHashMap))*0.393701, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-9-"+i+"-box-text"), null, value+"", "Length", "Validating Width Conversion In UOM CM To IN", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Height", ScenarioDetailsHashMap))*0.393701, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-10-"+i+"-box-text"), null, value+"", "Length", "Validating Height Conversion In UOM CM To IN", ScenarioDetailsHashMap);

			}else if(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOM", ScenarioDetailsHashMap).equals("FT")&& intitialUOM.equalsIgnoreCase("CM")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Length", ScenarioDetailsHashMap))*0.0328084, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-8-"+i+"-box-text"), null, value+"", "Length", "Validating Lenght Conversion In UOM CM To FT", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Width", ScenarioDetailsHashMap))*0.0328084, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-9-"+i+"-box-text"), null, value+"", "Length", "Validating Width Conversion In UOM CM To FT", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Height", ScenarioDetailsHashMap))*0.0328084, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-10-"+i+"-box-text"), null, value+"", "Length", "Validating Height Conversion In UOM CM To FT", ScenarioDetailsHashMap);

			}else if(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOM", ScenarioDetailsHashMap).equals("CM")&& intitialUOM.equalsIgnoreCase("IN")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Length", ScenarioDetailsHashMap))*2.54, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-8-"+i+"-box-text"), null, value+"", "Length", "Validating Lenght Conversion In UOM IN To CM", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Width", ScenarioDetailsHashMap))*2.54, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-9-"+i+"-box-text"), null, value+"", "Length", "Validating Width Conversion In UOM IN To CM", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Height", ScenarioDetailsHashMap))*2.54, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-10-"+i+"-box-text"), null, value+"", "Length", "Validating Height Conversion In UOM IN To CM", ScenarioDetailsHashMap);

			}else if(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOM", ScenarioDetailsHashMap).equals("FT")&& intitialUOM.equalsIgnoreCase("IN")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Length", ScenarioDetailsHashMap))*0.0833333, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-8-"+i+"-box-text"), null, value+"", "Length", "Validating Lenght Conversion In UOM IN To FT", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Width", ScenarioDetailsHashMap))*0.0833333, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-9-"+i+"-box-text"), null, value+"", "Length", "Validating Width Conversion In UOM IN To FT", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Height", ScenarioDetailsHashMap))*0.0833333, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-10-"+i+"-box-text"), null, value+"", "Length", "Validating Height Conversion In UOM IN To FT", ScenarioDetailsHashMap);

			}else if(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOM", ScenarioDetailsHashMap).equals("CM")&& intitialUOM.equalsIgnoreCase("FT")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Length", ScenarioDetailsHashMap))*30.48, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-8-"+i+"-box-text"), null, value+"", "Length", "Validating Lenght Conversion In UOM FT To CM", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Width", ScenarioDetailsHashMap))*30.48 , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-9-"+i+"-box-text"), null, value+"", "Length", "Validating Width Conversion In UOM FT To CM", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Height", ScenarioDetailsHashMap))*30.48 , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-10-"+i+"-box-text"), null, value+"", "Length", "Validating Height Conversion In UOM FT To CM", ScenarioDetailsHashMap);

			}else if(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOM", ScenarioDetailsHashMap).equals("IN")&& intitialUOM.equalsIgnoreCase("FT")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Length", ScenarioDetailsHashMap))*12, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-8-"+i+"-box-text"), null, value+"", "Length", "Validating Lenght Conversion In UOM FT To IN", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Width", ScenarioDetailsHashMap))*12, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-9-"+i+"-box-text"), null, value+"", "Length", "Validating Width Conversion In UOM FT To IN", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "Height", ScenarioDetailsHashMap))*12, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-10-"+i+"-box-text"), null, value+"", "Length", "Validating Height Conversion In UOM FT To IN", ScenarioDetailsHashMap);

			}
		}
	}

	public static void assertSeaDifferentUOW(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) {
		String intitialUOM=ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "DefaultUOW", ScenarioDetailsHashMap);
		selectOptionFromDropDown(driver, null,  orSeaBooking.getElement(driver, "DropDown_MaterUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UOW", ScenarioDetailsHashMap));
		int rows=orSeaBooking.getElements(driver, "Grid_PackDetailsRows", ScenarioDetailsHashMap, 10).size();
		for (int i = 0; i < rows; i++) {
			String value="";
			if(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UOW", ScenarioDetailsHashMap).equals("LB")&& intitialUOM.equalsIgnoreCase("KG")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "GrossWeight", ScenarioDetailsHashMap))*2.20462262185, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap).replace(" ", ""));
				assertTwoValues((driver.findElement(By.id("myGrid1-cell-5-"+i+"-box-text")).getText()).replace(" ", ""), value, "Validating Gross Weight Conversion In UOW KG To LB", ScenarioDetailsHashMap);
				//assertInnerText(driver, By.id("myGrid1-cell-5-"+i+"-box-text"), null, value+"", "Length", "Validating Gross Weight Conversion In UOW KG To LB", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "NetWeight", ScenarioDetailsHashMap))*2.20462262185, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertTwoValues((driver.findElement(By.id("myGrid1-cell-7-"+i+"-box-text")).getText()).replace(" ", ""), value, "Validating Net Weight Conversion In UOW KG To LB", ScenarioDetailsHashMap);
				//assertInnerText(driver, By.id("myGrid1-cell-7-"+i+"-box-text"), null, value+"", "Length", "Validating Net Weight Conversion In UOW KG To LB", ScenarioDetailsHashMap);


			}else if(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UOW", ScenarioDetailsHashMap).equals("TON")&& intitialUOM.equalsIgnoreCase("KG")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "GrossWeight", ScenarioDetailsHashMap))*0.001, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap).replace(" ", ""));
				System.out.println("value :"+value);
				System.out.println("value :"+value.replace(" ", ""));
				assertInnerText(driver, By.id("myGrid1-cell-5-"+i+"-box-text"), null, value+"", "Length", "Validating Gross Weight Conversion In UOW KG To TON", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "NetWeight", ScenarioDetailsHashMap))*0.001 , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-7-"+i+"-box-text"), null, value+"", "Length", "Validating Net Weight Conversion In UOW KG To TON", ScenarioDetailsHashMap);


			}else if(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UOW", ScenarioDetailsHashMap).equals("KG")&& intitialUOM.equalsIgnoreCase("LB")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "GrossWeight", ScenarioDetailsHashMap))*0.45359237 , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-5-"+i+"-box-text"), null, value+"", "Length", "Validating Gross Weight Conversion In UOW LB To KG", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "NetWeight", ScenarioDetailsHashMap))*0.45359237 , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-7-"+i+"-box-text"), null, value+"", "Length", "Validating Net Weight Conversion In UOW LB To KG", ScenarioDetailsHashMap);


			}else if(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UOW", ScenarioDetailsHashMap).equals("TON")&& intitialUOM.equalsIgnoreCase("LB")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "GrossWeight", ScenarioDetailsHashMap))*0.00045359237  , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-5-"+i+"-box-text"), null, value+"", "Length", "Validating Gross Weight Conversion In UOW LB To TON", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "NetWeight", ScenarioDetailsHashMap))*0.00045359237  , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-7-"+i+"-box-text"), null, value+"", "Length", "Validating Net Weight Conversion In UOW LB To TON", ScenarioDetailsHashMap);


			}else if(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UOW", ScenarioDetailsHashMap).equals("KG")&& intitialUOM.equalsIgnoreCase("TON")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "GrossWeight", ScenarioDetailsHashMap))*1000 , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-5-"+i+"-box-text"), null, value+"", "Length", "Validating Gross Weight Conversion In UOW TON To KG", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "NetWeight", ScenarioDetailsHashMap))*1000  , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-7-"+i+"-box-text"), null, value+"", "Length", "Validating Net Weight Conversion In UOW  TON To KG", ScenarioDetailsHashMap);


			}else if(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UOW", ScenarioDetailsHashMap).equals("LB")&& intitialUOM.equalsIgnoreCase("TON")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "GrossWeight", ScenarioDetailsHashMap))*2204.622622 , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-5-"+i+"-box-text"), null, value+"", "Length", "Validating Gross Weight Conversion In UOW  TON To LB", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "NetWeight", ScenarioDetailsHashMap))*2204.622622 , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-7-"+i+"-box-text"), null, value+"", "Length", "Validating Net Weight Conversion In UOW TON To LB", ScenarioDetailsHashMap);


			}
		}
	}

	public static void assertSeaDifferentUOWHBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) {
		String intitialUOM=ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "DefaultUOW", ScenarioDetailsHashMap);
		selectOptionFromDropDown(driver, null,  orSeaBooking.getElement(driver, "DropDown_MaterUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOW", ScenarioDetailsHashMap));
		int rows=orSeaBooking.getElements(driver, "Grid_PackDetailsRows", ScenarioDetailsHashMap, 10).size();
		for (int i = 0; i < rows; i++) {
			String value="";
			if(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOW", ScenarioDetailsHashMap).equals("LB")&& intitialUOM.equalsIgnoreCase("KG")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "GrossWeight", ScenarioDetailsHashMap))*2.20462262185, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				//assertInnerText(driver, By.id("myGrid1-cell-5-"+i+"-box-text"), null, value, "Length", "Validating Gross Weight Conversion In UOW KG To LB", ScenarioDetailsHashMap);
				assertTwoValues((driver.findElement(By.id("myGrid1-cell-5-"+i+"-box-text")).getText()).replace(" ", ""), value, "Validating Gross Weight Conversion In UOW KG To LB", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "NetWeight", ScenarioDetailsHashMap))*2.20462262185, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertTwoValues((driver.findElement(By.id("myGrid1-cell-7-"+i+"-box-text")).getText()).replace(" ", ""), value, "Validating Net Weight Conversion In UOW KG To LB", ScenarioDetailsHashMap);
				//assertInnerText(driver, By.id("myGrid1-cell-7-"+i+"-box-text"), null, value+"", "Length", "Validating Net Weight Conversion In UOW KG To LB", ScenarioDetailsHashMap);


			}else if(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOW", ScenarioDetailsHashMap).equals("TON")&& intitialUOM.equalsIgnoreCase("KG")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "GrossWeight", ScenarioDetailsHashMap))*0.001, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				//assertInnerText(driver, By.id("myGrid1-cell-5-"+i+"-box-text"), null, value+"", "Length", "Validating Gross Weight Conversion In UOW KG To TON", ScenarioDetailsHashMap);
				assertTwoValues(driver.findElement(By.id("myGrid1-cell-5-"+i+"-box-text")).getText(), value, "Validating Gross Weight Conversion In UOW KG To TON", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "NetWeight", ScenarioDetailsHashMap))*0.001 , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-7-"+i+"-box-text"), null, value+"", "Length", "Validating Net Weight Conversion In UOW KG To TON", ScenarioDetailsHashMap);


			}else if(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOW", ScenarioDetailsHashMap).equals("KG")&& intitialUOM.equalsIgnoreCase("LB")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "GrossWeight", ScenarioDetailsHashMap))*0.45359237 , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-5-"+i+"-box-text"), null, value+"", "Length", "Validating Gross Weight Conversion In UOW LB To KG", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "NetWeight", ScenarioDetailsHashMap))*0.45359237 , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-7-"+i+"-box-text"), null, value+"", "Length", "Validating Net Weight Conversion In UOW LB To KG", ScenarioDetailsHashMap);


			}else if(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOW", ScenarioDetailsHashMap).equals("TON")&& intitialUOM.equalsIgnoreCase("LB")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "GrossWeight", ScenarioDetailsHashMap))*0.00045359237  , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-5-"+i+"-box-text"), null, value+"", "Length", "Validating Gross Weight Conversion In UOW LB To TON", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "NetWeight", ScenarioDetailsHashMap))*0.00045359237  , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-7-"+i+"-box-text"), null, value+"", "Length", "Validating Net Weight Conversion In UOW LB To TON", ScenarioDetailsHashMap);


			}else if(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOW", ScenarioDetailsHashMap).equals("KG")&& intitialUOM.equalsIgnoreCase("TON")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "GrossWeight", ScenarioDetailsHashMap))*1000 , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-5-"+i+"-box-text"), null, value+"", "Length", "Validating Gross Weight Conversion In UOW TON To KG", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "NetWeight", ScenarioDetailsHashMap))*1000  , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-7-"+i+"-box-text"), null, value+"", "Length", "Validating Net Weight Conversion In UOW  TON To KG", ScenarioDetailsHashMap);


			}else if(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOW", ScenarioDetailsHashMap).equals("LB")&& intitialUOM.equalsIgnoreCase("TON")){
				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "GrossWeight", ScenarioDetailsHashMap))*2204.622622 , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-5-"+i+"-box-text"), null, value+"", "Length", "Validating Gross Weight Conversion In UOW  TON To LB", ScenarioDetailsHashMap);

				value=AppReusableMethods.roundOffDecimalPlaces(Double.parseDouble(ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "NetWeight", ScenarioDetailsHashMap))*2204.622622 , ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap));
				assertInnerText(driver, By.id("myGrid1-cell-7-"+i+"-box-text"), null, value+"", "Length", "Validating Net Weight Conversion In UOW TON To LB", ScenarioDetailsHashMap);


			}
		}
	}
	public static void assertVolumeWeightCalculation(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) {
		double quantity=Double.parseDouble(readValue(driver, orSeaBooking.getElement(driver, "Text_MaterVolumeCBM", ScenarioDetailsHashMap, 10), null));
		selectOptionFromDropDown(driver, null,  orSeaBooking.getElement(driver, "DropDown_MaterUOW", ScenarioDetailsHashMap, 10), "KG");
		double grossWeight=Double.parseDouble(readValue(driver, orSeaBooking.getElement(driver, "Text_MaterGrossWeight", ScenarioDetailsHashMap, 10), null));
		double volumeWeight=0.0;
		selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterUOM", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UOM", ScenarioDetailsHashMap));
		if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10)).equals("6000"))
		{
			volumeWeight=quantity*166.67;
		}else if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10)).equals("7000")){
			volumeWeight=quantity*142.86;
		}else if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10)).equals("366")){
			volumeWeight=quantity*166.7315574;
		}else if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10)).equals("427")){
			volumeWeight=quantity*142.9127635;
		}else if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10)).equals("5000")){
			volumeWeight=quantity*200.004;
		}else if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10)).equals("166")){
			volumeWeight=quantity*166.591999;
		}else if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10)).equals("194")){
			volumeWeight=quantity*143.2050453;
		}
		selectOptionFromDropDown(driver, null,  orSeaBooking.getElement(driver, "DropDown_MaterUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "UOW", ScenarioDetailsHashMap));
		if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterUOW", ScenarioDetailsHashMap, 10)).equals("LB")){
			volumeWeight=volumeWeight*2.204622622;
			grossWeight=grossWeight*2.204622622;
		}else if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterUOW", ScenarioDetailsHashMap, 10)).equals("TON")){
			volumeWeight=volumeWeight*0.001;
			grossWeight=grossWeight*0.001;
		} 

		assertTwoValues(readValue(driver, orSeaBooking.getElement(driver, "Text_MaterVolumetricWeight", ScenarioDetailsHashMap, 10), null).replace(" ", ""),  AppReusableMethods.roundOffDecimalPlaces(volumeWeight, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap))+"",  "Validating Volume weight for UOW", ScenarioDetailsHashMap);
		//assertValue(driver, null, orSeaBooking.getElement(driver, "Text_MaterVolumetricWeight", ScenarioDetailsHashMap, 10), AppReusableMethods.roundOffDecimalPlaces(volumeWeight, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap))+"", "Volume Weight", "Validating Volume weight for UOW", ScenarioDetailsHashMap);
		assertTwoValues(readValue(driver, orSeaBooking.getElement(driver, "Text_MaterGrossWeight", ScenarioDetailsHashMap, 10), null).replace(" ", ""),  AppReusableMethods.roundOffDecimalPlaces(grossWeight, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap))+"",  "Validating Master Gross weight for UOW", ScenarioDetailsHashMap);
		//assertValue(driver, null, orSeaBooking.getElement(driver, "Text_MaterGrossWeight", ScenarioDetailsHashMap, 10),  AppReusableMethods.roundOffDecimalPlaces(grossWeight, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap))+"", "Gross Weight", "Validating Master Gross weight for UOW", ScenarioDetailsHashMap);
	}
	
	public static void assertVolumeWeightCalculationHBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) {
		double quantity=Double.parseDouble(readValue(driver, orSeaBooking.getElement(driver, "Text_MaterVolumeCBM", ScenarioDetailsHashMap, 10), null));
		selectOptionFromDropDown(driver, null,  orSeaBooking.getElement(driver, "DropDown_MaterUOW", ScenarioDetailsHashMap, 10), "KG");
		double grossWeight=Double.parseDouble(readValue(driver, orSeaBooking.getElement(driver, "Text_MaterGrossWeight", ScenarioDetailsHashMap, 10), null));
		double volumeWeight=0.0;
		selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterUOM", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOM", ScenarioDetailsHashMap));
		if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10)).equals("6000"))
		{
			volumeWeight=quantity*166.67;
		}else if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10)).equals("7000")){
			volumeWeight=quantity*142.86;
		}else if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10)).equals("366")){
			volumeWeight=quantity*166.7315574;
		}else if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10)).equals("427")){
			volumeWeight=quantity*142.9127635;
		}else if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10)).equals("5000")){
			volumeWeight=quantity*200.004;
		}else if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10)).equals("166")){
			volumeWeight=quantity*166.591999;
		}else if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10)).equals("194")){
			volumeWeight=quantity*143.2050453;
		}
		selectOptionFromDropDown(driver, null,  orSeaBooking.getElement(driver, "DropDown_MaterUOW", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "UOW", ScenarioDetailsHashMap));
		if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterUOW", ScenarioDetailsHashMap, 10)).equals("LB")){
			volumeWeight=volumeWeight*2.204622622;
			grossWeight=grossWeight*2.204622622;
		}else if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterUOW", ScenarioDetailsHashMap, 10)).equals("TON")){
			volumeWeight=volumeWeight*0.001;
			grossWeight=grossWeight*0.001;
		} 

//		assertValue(driver, null, orSeaBooking.getElement(driver, "Text_MaterVolumetricWeight", ScenarioDetailsHashMap, 10), AppReusableMethods.roundOffDecimalPlaces(volumeWeight, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap))+"", "Volume Weight", "Validating Volume weight for UOW", ScenarioDetailsHashMap);
		assertTwoValues(readValue(driver, orSeaBooking.getElement(driver, "Text_MaterVolumetricWeight", ScenarioDetailsHashMap, 10), null).replace(" ", ""),  AppReusableMethods.roundOffDecimalPlaces(volumeWeight, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap))+"", "Validating Volume weight for UOW", ScenarioDetailsHashMap);
		//assertValue(driver, null, orSeaBooking.getElement(driver, "Text_MaterGrossWeight", ScenarioDetailsHashMap, 10),  AppReusableMethods.roundOffDecimalPlaces(grossWeight, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap))+"", "Gross Weight", "Validating Master Gross weight for UOW", ScenarioDetailsHashMap);
		assertTwoValues(readValue(driver, orSeaBooking.getElement(driver, "Text_MaterGrossWeight", ScenarioDetailsHashMap, 10), null).replace(" ", ""),  AppReusableMethods.roundOffDecimalPlaces(volumeWeight, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap))+"", "Validating Master Gross weight for UOW", ScenarioDetailsHashMap);
	}

	public static void assertWeightScaleforOcean(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		String status="";
		if(!orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10).isEnabled()){
			status="Protected";
		}else{
			status="Unprotected";
		}
		assertTwoValues(status, ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "StatusOfWeightScale", ScenarioDetailsHashMap), "Validating Weight Scale Field protection status", ScenarioDetailsHashMap);
		String Value=getSelectedOptionOfDropDown(driver, null,orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10));
		System.out.println("Drop down Value"+Value);
		assertTwoValues(	Value,ExcelUtils.getCellData("SE_BookingCargoDetails", RowNo, "WeightScale", ScenarioDetailsHashMap), "Validating default Value For weight Scale field", ScenarioDetailsHashMap);

	}

	public static void assertWeightScaleforOceanHBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		String status="";
		if(!orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10).isEnabled()){
			status="Protected";
		}else{
			status="Unprotected";
		}
		assertTwoValues(status, ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "StatusOfWeightScale", ScenarioDetailsHashMap), "Validating Weight Scale Field protection status", ScenarioDetailsHashMap);
		String Value=getSelectedOptionOfDropDown(driver, null,orSeaBooking.getElement(driver, "DropDown_MaterWeightScale", ScenarioDetailsHashMap, 10));
		System.out.println("Drop down Value"+Value);
		assertTwoValues(	Value,ExcelUtils.getCellData("SE_MBLCargoPackDetails", RowNo, "WeightScale", ScenarioDetailsHashMap), "Validating default Value For weight Scale field", ScenarioDetailsHashMap);

	}
	public static void assertVolumeCalculations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		int rows=orSeaBooking.getElements(driver, "Grid_PackDetailsRows", ScenarioDetailsHashMap, 10).size();
		double totalVol = 0;
		for (int i = 0; i < rows; i++) {
			clickElement(driver, By.id("myGrid1-cell-0-"+i+"-box-text"), null, 10);
			int pack=Integer.parseInt(getInnerText(driver, By.id("myGrid1-cell-1-"+i+"-box-text"), null, 10));
			double len=Double.parseDouble(getInnerText(driver, By.id("myGrid1-cell-8-"+i+"-box-text"), null, 10));
			double width=Double.parseDouble(getInnerText(driver, By.id("myGrid1-cell-9-"+i+"-box-text"), null, 10));
			double height=Double.parseDouble(getInnerText(driver, By.id("myGrid1-cell-10-"+i+"-box-text"), null, 10));

			double volume=Double.parseDouble(AppReusableMethods.roundOffDecimalPlaces(pack*len*width*height/1000000, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForVolume", ScenarioDetailsHashMap)));
			assertInnerText(driver, By.id("myGrid1-cell-11-"+i+"-box-text"), null, AppReusableMethods.roundOffDecimalPlaces(volume, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForVolume", ScenarioDetailsHashMap)), "Volume CBM", "Validating calculate volume CBM  Value ", ScenarioDetailsHashMap);
			totalVol=totalVol+volume;
		}

		assertValue(driver, null, orSeaBooking.getElement(driver, "Text_MaterVolumeCBM", ScenarioDetailsHashMap, 10), AppReusableMethods.roundOffDecimalPlaces(totalVol, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForVolume", ScenarioDetailsHashMap)) , "Master Volume CBM", "Validating master Volume CBM", ScenarioDetailsHashMap);

	}

	public static void assertDecimalPrecesion(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo)
	{
		/*/////
		GenericMethods.clickElement(driver, null,orSeaBooking.getElement(driver, "Tab_CargoDetails", ScenarioDetailsHashMap,10), 2);
		GenericMethods.pauseExecution(6000);
		try{GenericMethods.handleAlert(driver, "accept");}catch (Exception e) {}
		GenericMethods.pauseExecution(6000);
		////
*/		
		int rows=orSeaBooking.getElements(driver, "Grid_PackDetailsRows", ScenarioDetailsHashMap, 10).size();
		System.out.println("row count"+rows);
		for (int i = 0; i < rows; i++) {
			clickElement(driver, By.id("myGrid1-cell-0-"+i+"-box-text"), null, 10);
			String len=getInnerText(driver, By.id("myGrid1-cell-8-"+i+"-box-text"), null, 10);
			String width=getInnerText(driver, By.id("myGrid1-cell-9-"+i+"-box-text"), null, 10);
			String height=getInnerText(driver, By.id("myGrid1-cell-10-"+i+"-box-text"), null, 10);
			String grossWeight=getInnerText(driver, By.id("myGrid1-cell-5-"+i+"-box-text"), null, 10);
			String netWeight=getInnerText(driver, By.id("myGrid1-cell-7-"+i+"-box-text"), null, 10);
			String volumeCBM=getInnerText(driver, By.id("myGrid1-cell-11-"+i+"-box-text"), null, 10);
			assertTwoValues(len.substring(len.indexOf(".")+1,len.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap), "Validating Decimals digits for length", ScenarioDetailsHashMap);
			assertTwoValues(width.substring(width.indexOf(".")+1,width.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap), "Validating Decimals digits for width", ScenarioDetailsHashMap);
			assertTwoValues(height.substring(height.indexOf(".")+1,height.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForDimensions", ScenarioDetailsHashMap), "Validating Decimals digits for height", ScenarioDetailsHashMap);
			assertTwoValues(grossWeight.substring(grossWeight.indexOf(".")+1,grossWeight.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for gross Weight", ScenarioDetailsHashMap);
			assertTwoValues(netWeight.substring(netWeight.indexOf(".")+1,netWeight.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for netWeight", ScenarioDetailsHashMap);
			assertTwoValues(volumeCBM.substring(volumeCBM.indexOf(".")+1,volumeCBM.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForVolume", ScenarioDetailsHashMap), "Validating Decimals digits for VolumeCBM", ScenarioDetailsHashMap);

		}

		String grossWeight=readValue(driver,  orSeaBooking.getElement(driver, "Text_MaterGrossWeight", ScenarioDetailsHashMap, 10),null);
		assertTwoValues(grossWeight.substring(grossWeight.indexOf(".")+1,grossWeight.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Master Gross Weight", ScenarioDetailsHashMap);
		String volumeCBM=readValue(driver, orSeaBooking.getElement(driver, "Text_MaterVolumeCBM", ScenarioDetailsHashMap, 10), null);
		assertTwoValues(volumeCBM.substring(volumeCBM.indexOf(".")+1,volumeCBM.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForVolume", ScenarioDetailsHashMap), "Validating Decimals digits for Master Volume CBM", ScenarioDetailsHashMap);
		String chargableWeight=readValue(driver, orSeaBooking.getElement(driver, "Text_MaterChargeableWeight", ScenarioDetailsHashMap, 10), null);
		assertTwoValues(chargableWeight.substring(chargableWeight.indexOf(".")+1,chargableWeight.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForVolume", ScenarioDetailsHashMap), "Validating Decimals digits for Master Chargeable Volume", ScenarioDetailsHashMap);
		String volumatricWeight=readValue(driver, orSeaBooking.getElement(driver, "Text_MaterVolumetricWeight", ScenarioDetailsHashMap, 10), null);
		assertTwoValues(volumatricWeight.substring(volumatricWeight.indexOf(".")+1,volumatricWeight.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Master Volume Weight", ScenarioDetailsHashMap);

		rows=orSeaBooking.getElements(driver, "Grid_InvoiceDetailsRows", ScenarioDetailsHashMap, 10).size();
		System.out.println("row count"+rows);
		for (int i = 0; i < rows; i++) {
			clickElement(driver, By.id("invPoGrid-cell-2-"+i+"-box-text"), null, 10);
			String qnty1=getAttribute(locateElement(driver, By.name("Qty1"), 10), "previousvalue");
			assertTwoValues(qnty1.substring(qnty1.indexOf(".")+1,qnty1.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForQuantity", ScenarioDetailsHashMap), "Validating Decimals digits for Stat Qty1", ScenarioDetailsHashMap);
			qnty1=getAttribute(locateElement(driver, By.name("Qty2"), 10), "previousvalue");
			assertTwoValues(qnty1.substring(qnty1.indexOf(".")+1,qnty1.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForQuantity", ScenarioDetailsHashMap), "Validating Decimals digits for Stat Qty1", ScenarioDetailsHashMap);
			qnty1=readValue(driver, orSeaBooking.getElement(driver, "Text_InvGrossweight", ScenarioDetailsHashMap, 10), null);
			assertTwoValues(qnty1.substring(qnty1.indexOf(".")+1,qnty1.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Invoice Gross Weight", ScenarioDetailsHashMap);

			qnty1=readValue(driver, orSeaBooking.getElement(driver, "Text_InvNetweight", ScenarioDetailsHashMap, 10), null);
			assertTwoValues(qnty1.substring(qnty1.indexOf(".")+1,qnty1.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Invoice Net weight", ScenarioDetailsHashMap);

		}

	}

	public static void assertChargeVolumecalculations(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		if(getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterUOM", ScenarioDetailsHashMap, 10)).equals("LB")||getSelectedOptionOfDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterUOM", ScenarioDetailsHashMap, 10)).equals("TON"))
		{
			selectOptionFromDropDown(driver, null, orSeaBooking.getElement(driver, "DropDown_MaterUOM", ScenarioDetailsHashMap, 10), "KG");
		}
		double volumeCBM=Double.parseDouble(readValue(driver, orSeaBooking.getElement(driver, "Text_MaterVolumeCBM", ScenarioDetailsHashMap, 10), null));
		double grossWeight=Double.parseDouble(readValue(driver, orSeaBooking.getElement(driver, "Text_MaterGrossWeight", ScenarioDetailsHashMap, 10), null).replace(" ", ""));
		if((grossWeight/1000) > volumeCBM){
			assertValue(driver, null, orSeaBooking.getElement(driver, "Text_MaterChargeableWeight", ScenarioDetailsHashMap, 10), AppReusableMethods.roundOffDecimalPlaces(grossWeight, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap)), "Charge Volume", "Validating Chargable volome based on volume calculation", ScenarioDetailsHashMap);

		}else{
			assertValue(driver, null, orSeaBooking.getElement(driver, "Text_MaterChargeableWeight", ScenarioDetailsHashMap, 10), AppReusableMethods.roundOffDecimalPlaces(volumeCBM, ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForVolume", ScenarioDetailsHashMap)), "Charge Volume", "Validating Chargable volome based on volume calculation", ScenarioDetailsHashMap);
		}

	}
	
	public static void assertPRSDecimalPrecesion(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		AppReusableMethods.selectMenu(driver, ETransMenu.PRS_Add,"PRS ( Pick Up / Delivery )",ScenarioDetailsHashMap);
		OceanPRS.pictOceanPRS_SearchList_Booking(driver, ScenarioDetailsHashMap, RowNo);
		clickElement(driver, null, orCommon.getElement(driver, "MainDetails", ScenarioDetailsHashMap, 10), 2);
		int rows=orOceanPRS.getElements(driver, "Grid_AlocationRows", ScenarioDetailsHashMap, 10).size();
		for (int i = 1; i <= rows; i++) {
			String val=getInnerText(driver, By.id("grossWeightStr"+i), null, 10);
			assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Gross Weight", ScenarioDetailsHashMap);
			 val=getInnerText(driver, By.id("volumeStr"+i), null, 10);
			 assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForVolume", ScenarioDetailsHashMap), "Validating Decimals digits for Volume(CBM)", ScenarioDetailsHashMap);
			 val=getInnerText(driver, By.id("chargeableWeightStr"+i), null, 10);
			 assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Chargeable Weight", ScenarioDetailsHashMap);
			 
		}
		clickElement(driver, null, orCommon.getElement(driver, "CloseButton", ScenarioDetailsHashMap, 10), 2);
		
	}
	
	public static void assertOceanArrivalDecimalPrecesion(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		AppReusableMethods.selectMenu(driver, ETransMenu.oceanArraival,"Arrival Confirmation", ScenarioDetailsHashMap);
		OceanArrivals.searchmbl(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "Tab_Arrival", ScenarioDetailsHashMap, 10), 2);
		String val=readValue(driver, orOceanArrival.getElement(driver, "Input_MasterTotWeight", ScenarioDetailsHashMap, 10), null);
		System.out.println("value="+val);
		assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for  Master Total Weight", ScenarioDetailsHashMap);
		val=readValue(driver, orOceanArrival.getElement(driver, "Input_MasterVolumeCBM", ScenarioDetailsHashMap, 10), null);
		System.out.println("value="+val);
		assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForVolume", ScenarioDetailsHashMap), "Validating Decimals digits for  Master Volume CBM", ScenarioDetailsHashMap);
		
		
		int rows=orOceanArrival.getElements(driver, "Grid_HOuseRows", ScenarioDetailsHashMap, 10).size();
		for (int i = 1; i <= rows;i++) {
			clickElement(driver, By.id("dtHouseDocNumber"+i), null, 10);
		
			 val=readValue(driver, orOceanArrival.getElement(driver, "Input_WeightTotal", ScenarioDetailsHashMap, 10), null);
				System.out.println("value="+val);
			assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Weight Total for House", ScenarioDetailsHashMap);
			val=readValue(driver, orOceanArrival.getElement(driver, "Input_WeightPrev", ScenarioDetailsHashMap, 10), null);
			System.out.println("value="+val);
			assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Weight Previous for House", ScenarioDetailsHashMap);
			val=readValue(driver, orOceanArrival.getElement(driver, "CurrentWeight", ScenarioDetailsHashMap, 10), null);
			System.out.println("value="+val);
			assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Weight Current for House", ScenarioDetailsHashMap);
			val=readValue(driver, orOceanArrival.getElement(driver, "Input_VolumeTotal", ScenarioDetailsHashMap, 10), null);
			System.out.println("value="+val);
			assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForVolume", ScenarioDetailsHashMap), "Validating Decimals digits for Volume Total for House", ScenarioDetailsHashMap);
			val=readValue(driver, orOceanArrival.getElement(driver, "Input_VolumePrev", ScenarioDetailsHashMap, 10), null);
			System.out.println("value="+val);
			assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForVolume", ScenarioDetailsHashMap), "Validating Decimals digits for Volume Previous for House", ScenarioDetailsHashMap);
			val=readValue(driver, orOceanArrival.getElement(driver, "CurrentVolume", ScenarioDetailsHashMap, 10), null);
			System.out.println("value="+val);
			assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForVolume", ScenarioDetailsHashMap), "Validating Decimals digits for Volume Current for House", ScenarioDetailsHashMap);
			
			
		}
		
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "Tab_MBLChargesCost", ScenarioDetailsHashMap, 10), 2);
		val=readValue(driver, orOceanArrival.getElement(driver, "Input_SummaryVolumeCBM", ScenarioDetailsHashMap, 10), null);
		System.out.println("value="+val);
		assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForVolume", ScenarioDetailsHashMap), "Validating Decimals digits for Volume CBM in MBL Cost and Charges", ScenarioDetailsHashMap);
		val=readValue(driver, orOceanArrival.getElement(driver, "Input_GrossWeight", ScenarioDetailsHashMap, 10), null);
		System.out.println("value="+val);
		assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Gross Weight in MBL Cost and Charges", ScenarioDetailsHashMap);
		val=readValue(driver, orOceanArrival.getElement(driver, "Input_ChargeableWeight", ScenarioDetailsHashMap, 10), null);
		System.out.println("value="+val);
		assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Charge Weight in MBL Cost and Charges", ScenarioDetailsHashMap);
		
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "Tab_ArrivalNotice", ScenarioDetailsHashMap, 10), 2);
		val=readValue(driver, orOceanArrival.getElement(driver, "Input_ArivalNoticeWeight", ScenarioDetailsHashMap, 10), null);
		System.out.println("value="+val);
		assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Weight in Arrival Notices", ScenarioDetailsHashMap);
		val=readValue(driver, orOceanArrival.getElement(driver, "Input_ArivalNoticeVolume", ScenarioDetailsHashMap, 10), null);
		System.out.println("value="+val);
		assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForVolume", ScenarioDetailsHashMap), "Validating Decimals digits for Volume CBM  in Arrival Notices", ScenarioDetailsHashMap);
		
		GenericMethods.clickElement(driver, null,orOceanArrival.getElement(driver, "Tab_DeliveryInstructions", ScenarioDetailsHashMap, 10), 2);
		val=readValue(driver, orOceanArrival.getElement(driver, "Input_DIGrossWeight", ScenarioDetailsHashMap, 10), null);
		System.out.println("value="+val);
		assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for GrossWeight in Delivery Instruction", ScenarioDetailsHashMap);
		val=readValue(driver, orOceanArrival.getElement(driver, "Input_DIChargeableWeight", ScenarioDetailsHashMap, 10), null);
		System.out.println("value="+val);
		assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for Chargeable Weight in Delivery Instruction", ScenarioDetailsHashMap);
		val=readValue(driver, orOceanArrival.getElement(driver, "totalHouseVolumeStr", ScenarioDetailsHashMap, 10), null);
		System.out.println("value="+val);
		assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForVolume", ScenarioDetailsHashMap), "Validating Decimals digits for VolumeCBM in Delivery Instruction", ScenarioDetailsHashMap);
		System.out.println("value="+val);
		val=readValue(driver, orOceanArrival.getElement(driver, "Input_HouseGrossWT", ScenarioDetailsHashMap, 10), null);
		System.out.println("value="+val);
		assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for House Total GrossWeight in Delivery Instruction", ScenarioDetailsHashMap);
		val=readValue(driver, orOceanArrival.getElement(driver, "Input_HouseChargeableWT", ScenarioDetailsHashMap, 10), null);
		System.out.println("value="+val);
		assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForWeight", ScenarioDetailsHashMap), "Validating Decimals digits for House Total Chargeable Weight in Delivery Instruction", ScenarioDetailsHashMap);
		val=readValue(driver, orOceanArrival.getElement(driver, "Input_HouseVolume", ScenarioDetailsHashMap, 10), null);
		System.out.println("value="+val);
		assertTwoValues(val.substring(val.indexOf(".")+1,val.length()).length()+"", ExcelUtils.getCellData("DecimalPrecision", RowNo, "DecimalsForVolume", ScenarioDetailsHashMap), "Validating Decimals digits for House Total Volume in Delivery Instruction", ScenarioDetailsHashMap);
		clickElement(driver, null, orCommon.getElement(driver, "CloseButton", ScenarioDetailsHashMap, 10), 2);
		
	}
	
	

	public static void assertEDocsUpload(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) throws IOException{
		clickElement(driver, null, orOceanArrival.getElement(driver, "TAB_E_DOC", ScenarioDetailsHashMap, 10), 2);
		clickElement(driver, null, orOceanArrival.getElement(driver, "Button_Browse", ScenarioDetailsHashMap, 10), 2);
		String filePath=System.getProperty("user.dir")+"\\Configurations\\"+ExcelUtils.getCellData("Ocean_EDOCS", RowNo, "FileName", ScenarioDetailsHashMap);
		System.out.println("file no"+filePath+"os="+System.getProperty("os.name"));
		if(System.getProperty("os.name").equalsIgnoreCase("Windows XP")){
			Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\Configurations\\upload.exe "+filePath);
		}else{
			Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\Configurations\\upload1.exe "+filePath);
		}
		
		
		GenericMethods.pauseExecution(20000);
		selectOptionFromDropDown(driver, null, orOceanArrival.getElement(driver, "DropDown_DocType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Ocean_EDOCS", RowNo, "DocumentType", ScenarioDetailsHashMap));
		selectOptionFromDropDown(driver, null, orOceanArrival.getElement(driver, "DropDown_DocCategory", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Ocean_EDOCS", RowNo, "DocumentCategory", ScenarioDetailsHashMap));
		clickElement(driver, null, orOceanArrival.getElement(driver, "Button_GridAdd", ScenarioDetailsHashMap, 10), 2);
		clickElement(driver, null, orCommon.getElement(driver, "ButtonSavecontinue", ScenarioDetailsHashMap, 10), 2);
		pauseExecution(2000);
		int rows=orOceanArrival.getElements(driver, "Grid_EdocRows", ScenarioDetailsHashMap, 10).size();
		String flag="Not Found";
		System.out.println("rows"+rows);
		for (int i = 1; i <= rows; i++) {
			if(getInnerText(driver, By.id("dtUserFileName"+i), null, 10).equals(ExcelUtils.getCellData("Ocean_EDOCS", RowNo, "FileName", ScenarioDetailsHashMap))){
				assertInnerText(driver, By.id("dtUserFileName"+i), null, ExcelUtils.getCellData("Ocean_EDOCS", RowNo, "FileName", ScenarioDetailsHashMap), "File Name", "Validating uploaded File Name In Grid", ScenarioDetailsHashMap);
				flag="Found";
				clickElement(driver, By.id("multiSelectCheckboxeDocGrid"+i), null, 10);
				pauseExecution(2000);
				clickElement(driver, By.id("dtFileSize"+i), null, 10);
				clickElement(driver, By.name("attachFileGoBtn"), null, 10);
//				locateElement(driver, By.xpath("//td[@id='dtDocView"+i+"']/a"), 10).sendKeys("\n");
				GenericMethods.pauseExecution(6000);
				Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\Configurations\\Close.exe");
				GenericMethods.pauseExecution(5000);
				selectWindow(driver);
				pauseExecution(1000);
				driver.close();
				switchToParent(driver);
				AppReusableMethods.selectMainFrame(driver, ScenarioDetailsHashMap);
				break;
			}
		}
		if(flag.equalsIgnoreCase("Not Found")){
			assertTwoValues(flag, "Found", "Validating File Uploaded is available in grid or not", ScenarioDetailsHashMap);
		}
		clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(10000);
	}

	public static void assertEDocsDefaultReports(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) throws IOException{
		clickElement(driver, null, orOceanArrival.getElement(driver, "TAB_E_DOC", ScenarioDetailsHashMap, 10), 2);
		int rows=orOceanArrival.getElements(driver, "Grid_EdocRows", ScenarioDetailsHashMap, 10).size();
		String flag="Not Found";
		for (int g = 1; g <= ExcelUtils.getCellDataRowCount("Ocean_EDOCS", ScenarioDetailsHashMap); g++) {
			for (int i = 1; i <= rows; i++) {
				if(getInnerText(driver, By.id("dtUserFileName"+i), null, 10).equals(ExcelUtils.getCellData("Ocean_EDOCS", g, "FileName", ScenarioDetailsHashMap))){
					assertInnerText(driver, By.id("dtUserFileName"+i), null, ExcelUtils.getCellData("Ocean_EDOCS", g, "FileName", ScenarioDetailsHashMap), "File Name", "Validating Defaulted Report File Name In Grid", ScenarioDetailsHashMap);
					flag="Found";
					clickElement(driver, By.xpath("//td[@id='dtDocView"+i+"']/a/img"), null, 10);
					GenericMethods.pauseExecution(6000);
					Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\Configurations\\Close.exe");
					GenericMethods.pauseExecution(5000);
					selectWindow(driver);
					pauseExecution(2000);
					driver.close();
					switchToParent(driver);
					AppReusableMethods.selectMainFrame(driver, ScenarioDetailsHashMap);
					break;
				}
				
			}
			if(flag.equalsIgnoreCase("Not Found")){
				assertTwoValues(flag, ExcelUtils.getCellData("Ocean_EDOCS", g, "FileName", ScenarioDetailsHashMap), "Validating Defaluted report file name is available in grid or not", ScenarioDetailsHashMap);
			}
		}
		clickElement(driver, null, orCommon.getElement(driver, "ButtonSavecontinue", ScenarioDetailsHashMap, 10), 2);
		pauseExecution(2000);
	}

	
	
	public static void assertEDocsRelatedModule(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo) throws IOException{
//		SeaHBL.seaHBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
		clickElement(driver, null, orOceanArrival.getElement(driver, "TAB_E_DOC", ScenarioDetailsHashMap, 10), 2);
		GenericMethods.pauseExecution(4000);
		for (int g = 1; g <= ExcelUtils.getCellDataRowCount("Ocean_EDOCS", ScenarioDetailsHashMap); g++) {
			String flag="Not Found";
			String val="";
			try{
			 val=ExcelUtils.getCellData("Ocean_EDOCS", g, "RelatedModuleFileName", ScenarioDetailsHashMap);
			}catch (Exception e) {
				val="";
			}
			int rows=orOceanArrival.getElements(driver, "Grid_EdocRows", ScenarioDetailsHashMap, 10).size();
			System.out.println("inside "+rows);
			for (int i = 1; i <= rows; i++) {
				System.out.println("inside "+val);
				if(!val.equals("")){
				if(getInnerText(driver, By.id("dtUserFileName"+i), null, 10).equals(ExcelUtils.getCellData("Ocean_EDOCS", g, "RelatedModuleFileName", ScenarioDetailsHashMap))){
					assertInnerText(driver, By.id("dtUserFileName"+i), null, ExcelUtils.getCellData("Ocean_EDOCS", g, "RelatedModuleFileName", ScenarioDetailsHashMap), "File Name", "Validating Related Module File Name In Grid in OBl", ScenarioDetailsHashMap);
					flag="Found";
					clickElement(driver, By.xpath("//td[@id='dtDocView"+i+"']/a/img"), null, 10);
					GenericMethods.pauseExecution(6000);
					Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\Configurations\\Close.exe");
					GenericMethods.pauseExecution(5000);
					selectWindow(driver);
					pauseExecution(1000);
					driver.close();
					switchToParent(driver);
					AppReusableMethods.selectMainFrame(driver, ScenarioDetailsHashMap);
					break;
				}}
			}
			if(flag.equalsIgnoreCase("Not Found")){
				assertTwoValues(flag, ExcelUtils.getCellData("Ocean_EDOCS", g, "RelatedModuleFileName", ScenarioDetailsHashMap), "Validating Related Module file name is available in grid or not in HBL", ScenarioDetailsHashMap);
			}
		}
		clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
	}
	
	public static void searchOBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		AppReusableMethods.selectMenu(driver, ETransMenu.OceanExport_Master, "Ocean Consolidation", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ShipmentRefNo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap) , 10);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
		GenericMethods.pauseExecution(5000);
	}
	/*public static void assertEDocsRelatedModuleMBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		AppReusableMethods.selectMenu(driver, ETransMenu.OceanExport_Master, "Ocean Consolidation", ScenarioDetailsHashMap);
		GenericMethods.replaceTextofTextField(driver, null, orOceanExpOBL.getElement(driver, "ShipmentRefNo", ScenarioDetailsHashMap, 10),ExcelUtils.getCellData("SE_OBL", RowNo, "Shipment_ReferenceId", ScenarioDetailsHashMap) , 10);
		GenericMethods.clickElement(driver, null,orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10),20);
		GenericMethods.pauseExecution(5000);
		clickElement(driver, null, orOceanArrival.getElement(driver, "TAB_E_DOC", ScenarioDetailsHashMap, 10), 2);
		int rows=orOceanArrival.getElements(driver, "Grid_EdocRows", ScenarioDetailsHashMap, 10).size();
		String flag="Not Found";
		for (int g = 1; g <= ExcelUtils.getCellDataRowCount("Ocean_EDOCS", ScenarioDetailsHashMap); g++) {
			for (int i = 1; i <= rows; i++) {
				if(getInnerText(driver, By.id("dtUserFileName"+i), null, 10).equals(ExcelUtils.getCellData("Ocean_EDOCS", RowNo, "RelatedModuleFileName", ScenarioDetailsHashMap))){
					assertInnerText(driver, By.id("dtUserFileName"+i), null, ExcelUtils.getCellData("Ocean_EDOCS", RowNo, "RelatedModuleFileName", ScenarioDetailsHashMap), "File Name", "Validating Related Module File Name In Grid in HBl", ScenarioDetailsHashMap);
					flag="Found";
					clickElement(driver, By.xpath("//td[@id='dtDocView"+i+"']/a/img"), null, 10);
					selectWindow(driver);
					pauseExecution(1000);
					driver.close();
					switchToParent(driver);
					AppReusableMethods.selectMainFrame(driver, ScenarioDetailsHashMap);
					break;
				}
			}
			if(flag.equalsIgnoreCase("Not Found")){
				assertTwoValues(flag, "Found", "Validating Related Module file name is available in grid or not in HBL", ScenarioDetailsHashMap);
			}
		}
	}*/
	
	public static void assertEDocsSendingDocs(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		clickElement(driver, null, orOceanArrival.getElement(driver, "TAB_E_DOC", ScenarioDetailsHashMap, 10), 2);
		clickElement(driver, null, orOceanArrival.getElement(driver, "CheckBox_MulitiSelect", ScenarioDetailsHashMap, 10), 2);
		clickElement(driver, null, orOceanArrival.getElement(driver, "Button_SendDocs", ScenarioDetailsHashMap, 10), 2);
		selectWindow(driver);
		replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "Input_Recipients", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Ocean_EDOCS", RowNo, "Recipients", ScenarioDetailsHashMap), 2);
		replaceTextofTextField(driver, null, orOceanArrival.getElement(driver, "Input_Subjects", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Ocean_EDOCS", RowNo, "Subject", ScenarioDetailsHashMap), 2);
		clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		//assertInnerText(driver, null, orOceanArrival.getElement(driver, "MSG_MailSent", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Ocean_EDOCS", RowNo, "MailSentMsg", ScenarioDetailsHashMap), "Summary Msg", "Validating Summary MSG After Sending Documents", ScenarioDetailsHashMap);
		String EdocSuccessMSG = GenericMethods.getInnerText(driver, null, orOceanArrival.getElement(driver, "MSG_MailSent", ScenarioDetailsHashMap, 10), 2);
		String EdocMSG = EdocSuccessMSG;
		if(EdocSuccessMSG.contains("sucess"))
		{
		System.out.println(EdocSuccessMSG.split(":")[0].trim());
		EdocMSG = EdocSuccessMSG.split(":")[0].trim();
		
		}
		else
		{
			System.out.println(EdocSuccessMSG.split(":")[1].trim());
			EdocMSG = EdocSuccessMSG.split(":")[1].trim();
		}
		GenericMethods.assertTwoValues(EdocMSG, ExcelUtils.getCellData("Ocean_EDOCS", RowNo, "MailSentMsg", ScenarioDetailsHashMap), "Validating Summary MSG After Sending Documents", ScenarioDetailsHashMap);
		
			driver.close();
		switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver, ScenarioDetailsHashMap);
		
	}
	
	
	public static void assertViewingShipment(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		clickElement(driver, null, orCommon.getElement(driver, "Button_View", ScenarioDetailsHashMap, 10), 2);
		String status="Not Protected";
		System.out.println(orSeaBooking.getElement(driver, "Shipper", ScenarioDetailsHashMap, 10).isEnabled()+"attrumnds"+getAttribute(orSeaBooking.getElement(driver, "Shipper", ScenarioDetailsHashMap, 10), "readonly"));
		if(getAttribute(orSeaBooking.getElement(driver, "Shipper", ScenarioDetailsHashMap, 10), "readonly").equals("true")){
			status="Protected";
		}
		assertTwoValues(status, "Protected", "Validating Shipper field protection Status", ScenarioDetailsHashMap);
		assertValue(driver, null, orSeaBooking.getElement(driver, "Shipper", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "Shipper_PartyId", ScenarioDetailsHashMap), "Shipper", "Validating Shipper Field", ScenarioDetailsHashMap);
		
		status="Not Protected";
		if(getAttribute(orSeaBooking.getElement(driver, "Consignee", ScenarioDetailsHashMap, 10), "readonly").equals("true")){
			status="Protected";
		}
		assertTwoValues(status, "Protected", "Validating Consignee field protection Status", ScenarioDetailsHashMap);
		assertValue(driver, null, orSeaBooking.getElement(driver, "Consignee", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap), "Consignee", "Validating Consignee Field", ScenarioDetailsHashMap);
		
		status="Not Protected";
		if(getAttribute(orSeaBooking.getElement(driver, "PortOfLanding", ScenarioDetailsHashMap, 10), "readonly").equals("true")){
			status="Protected";
		}
		assertTwoValues(status, "Protected", "Validating Port of Loading field protection Status", ScenarioDetailsHashMap);
		assertValue(driver, null, orSeaBooking.getElement(driver, "PortOfLanding", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PortOfLoading", ScenarioDetailsHashMap), "Port of Loading", "Validating Port of Loading Field", ScenarioDetailsHashMap);
		
		status="Not Protected";
		if(getAttribute(orSeaBooking.getElement(driver, "PortOfDischarge", ScenarioDetailsHashMap, 10), "readonly").equals("true")){
			status="Protected";
		}
		assertTwoValues(status, "Protected", "Validating Port of Discharge field protection Status", ScenarioDetailsHashMap);
		assertValue(driver, null, orSeaBooking.getElement(driver, "PortOfDischarge", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_BookingMainDetails", RowNo, "PortOfDischarge", ScenarioDetailsHashMap), "Port of Discharge", "Validating Port of Discharge Field", ScenarioDetailsHashMap);
		clickElement(driver, null, orCommon.getElement(driver, "CloseButton", ScenarioDetailsHashMap, 10), 2);
	}
	
	public static void assertViewingShipmentHBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		clickElement(driver, null, orCommon.getElement(driver, "Button_View", ScenarioDetailsHashMap, 10), 2);
		String status="Not Protected";
		if(getAttribute(orHBL.getElement(driver, "ShipperName", ScenarioDetailsHashMap, 10), "readonly").equals("true")){
			status="Protected";
		}
		assertTwoValues(status, "Protected", "Validating Shipper field protection Status In HBL.", ScenarioDetailsHashMap);
		assertValue(driver, null, orHBL.getElement(driver, "ShipperName", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ShipperNickName", ScenarioDetailsHashMap), "Shipper", "Validating Shipper Field In HBL.", ScenarioDetailsHashMap);
		
		status="Not Protected";
		if(getAttribute(orHBL.getElement(driver, "ConsigneeName", ScenarioDetailsHashMap, 10), "readonly").equals("true")){
			status="Protected";
		}
		assertTwoValues(status, "Protected", "Validating Consignee field protection Status In HBL.", ScenarioDetailsHashMap);
		assertValue(driver, null, orHBL.getElement(driver, "ConsigneeName", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsigneeNickName", ScenarioDetailsHashMap), "Consignee", "Validating Consignee Field In HBL.", ScenarioDetailsHashMap);
		
		status="Not Protected";
		if(getAttribute(orHBL.getElement(driver, "PortOfLoading", ScenarioDetailsHashMap, 10), "readonly").equals("true")){
			status="Protected";
		}
		assertTwoValues(status, "Protected", "Validating Port of Loading field protection Status In HBL.", ScenarioDetailsHashMap);
		assertValue(driver, null, orHBL.getElement(driver, "PortOfLoading", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PortofLoading", ScenarioDetailsHashMap), "Port of Loading", "Validating Port of Loading Field In HBL.", ScenarioDetailsHashMap);
		
		status="Not Protected";
		if(getAttribute(orHBL.getElement(driver, "PortOfDispatch", ScenarioDetailsHashMap, 10), "readonly").equals("true")){
			status="Protected";
		}
		assertTwoValues(status, "Protected", "Validating Port of Discharge field protection StatusIn HBL.", ScenarioDetailsHashMap);
		assertValue(driver, null, orHBL.getElement(driver, "PortOfDispatch", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "PortofDischarge", ScenarioDetailsHashMap), "Port of Discharge", "Validating Port of Discharge Field In HBL.", ScenarioDetailsHashMap);
		clickElement(driver, null, orCommon.getElement(driver, "CloseButton", ScenarioDetailsHashMap, 10), 2);
	}

	public static void assertCreditNote(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		DecimalFormat dF = new DecimalFormat("#0.00");
		AppReusableMethods.selectMenu(driver, ETransMenu.Sales_Invoice_Enquiry, "Sales Invoice Enquiry", ScenarioDetailsHashMap);
		replaceTextofTextField(driver, null, orSalesInvoiceEnq.getElement(driver, "Input_InvoiceNumber", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "SalesInvoiceNo", ScenarioDetailsHashMap), 2);
		clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		selectOptionFromDropDown(driver, null, orSalesInvoiceEnq.getElement(driver, "DropDown_NavigationList", ScenarioDetailsHashMap, 10),"Credit Invoice");
		clickElement(driver, null, orSalesInvoiceEnq.getElement(driver, "Image_Go", ScenarioDetailsHashMap, 10), 2);
		assertInnerText(driver, null, orCommon.getElement(driver, "BannerTitle", ScenarioDetailsHashMap,10), "Credit Note", "Page Titile", "Validating Credit Note Screen title", ScenarioDetailsHashMap);
		double tot=0.00;
		double taxvalue=0.00;
		selectOptionFromDropDown(driver, null, orSalesInvoiceEnq.getElement(driver, "DropDown_CreditNoteType", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "CRNoteType", ScenarioDetailsHashMap));
		replaceTextofTextField(driver, null, orSalesInvoiceEnq.getElement(driver, "Input_InternalRemarks", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "RemarkMsg", ScenarioDetailsHashMap), 2);
		if(ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "CRNoteType", ScenarioDetailsHashMap).equalsIgnoreCase("Partial")){
			int rows=orSalesInvoiceEnq.getElements(driver, "GridRows_CreditNote", ScenarioDetailsHashMap, 10).size();
			
			String tax="";
			
			for (int i = 1; i <= rows; i++) {
				if(i== 1){
					replaceTextofTextField(driver, By.xpath("//tr[@class='summaryHeader']/following-sibling::tr["+i+"]/td/input[@name='creditAmount']"), null, ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "InvoicedCreditValue", ScenarioDetailsHashMap), 10);
					replaceTextofTextField(driver, By.xpath("//tr[@class='summaryHeader']/following-sibling::tr["+i+"]/td/input[@name='creditAmount']"), null, Keys.TAB, 10);
					pauseExecution(3000);
					tax=readValue(driver, null, By.xpath("//tr[@class='summaryHeader']/following-sibling::tr["+i+"]/td/input[@name='totalTaxOnCreditAmount']")).replace(" ", "");
					
					
				}
				tot=tot+Double.parseDouble(getInnerText(driver, By.xpath("//tr[@class='summaryHeader']/following-sibling::tr["+i+"]/td[9]"), null, 10).replace(" ", ""));
				if(!readValue(driver, null, By.xpath("//tr[@class='summaryHeader']/following-sibling::tr["+i+"]/td/input[@name='taxAmountPerLine']")).equals("")){
				taxvalue=taxvalue+Double.parseDouble(readValue(driver, null, By.xpath("//tr[@class='summaryHeader']/following-sibling::tr["+i+"]/td/input[@name='taxAmountPerLine']")).replace(" ", ""));
				}
			}
			if(!tax.equals("")){
				String value=dF.format(Double.parseDouble(ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "InvoicedCreditValue", ScenarioDetailsHashMap))+Double.parseDouble(tax))+"";
				ExcelUtils.setCellData("Ocean_CreaditNote", RowNo, "InvoicedCreditValue", value, ScenarioDetailsHashMap);
			}
			tot=tot+taxvalue;
			assertTwoValues(readValue(driver, orSalesInvoiceEnq.getElement(driver, "Input_OsAmount", ScenarioDetailsHashMap, 10), null).replace(" ", ""), dF.format(tot-Double.parseDouble(ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "InvoicedCreditValue", ScenarioDetailsHashMap)))+"", "Validating Total outstanding amount including Tax", ScenarioDetailsHashMap);
			assertTwoValues(readValue(driver, orSalesInvoiceEnq.getElement(driver, "Input_CancelAmount", ScenarioDetailsHashMap, 10), null).replace(" ", ""), ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "InvoicedCreditValue", ScenarioDetailsHashMap), "Validating Total Cancellation Amount including Tax", ScenarioDetailsHashMap);
			clickElement(driver, null, orSalesInvoiceEnq.getElement(driver, "Button_Confirm", ScenarioDetailsHashMap, 10), 2);
			String msg=getInnerText(driver, null, orSalesInvoiceEnq.getElement(driver, "TextArea_Message", ScenarioDetailsHashMap, 10), 2);
			assertTwoValues(msg.split(":")[1].trim(), ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "RebillInvoiceMsg", ScenarioDetailsHashMap), "Validating CreditNote Invoiced Msg", ScenarioDetailsHashMap);
			
			String[] sMsg=msg.split(":");
			ExcelUtils.setCellData("Ocean_CreaditNote", RowNo, "CRNoteId", sMsg[1].trim(), ScenarioDetailsHashMap);
		
			
		}else if (ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "CRNoteType", ScenarioDetailsHashMap).equalsIgnoreCase("Complete")) {
			int rows=orSalesInvoiceEnq.getElements(driver, "GridRows_CreditNote", ScenarioDetailsHashMap, 10).size();
//			double taxvalue=0.00;
			for (int i = 1; i <= rows; i++) {
				
				tot=tot+Double.parseDouble(getInnerText(driver, By.xpath("//tr[@class='summaryHeader']/following-sibling::tr["+i+"]/td[9]"), null, 10).replace(" ", ""));
				taxvalue=taxvalue+Double.parseDouble(readValue(driver, null, By.xpath("//tr[@class='summaryHeader']/following-sibling::tr["+i+"]/td/input[@name='totalTaxOnCreditAmount']")).replace(" ", ""));
			}
			assertTwoValues(readValue(driver, orSalesInvoiceEnq.getElement(driver, "Input_OsAmount", ScenarioDetailsHashMap, 10), null).replace(" ", ""), ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "TotalOutStandingAmount", ScenarioDetailsHashMap), "Validating Total outstanding amount including Tax", ScenarioDetailsHashMap);
			assertTwoValues(readValue(driver, orSalesInvoiceEnq.getElement(driver, "Input_CancelAmount", ScenarioDetailsHashMap, 10), null).replace(" ", ""), dF.format(tot+taxvalue)+"", "Validating Total Cancellation Amount including Tax", ScenarioDetailsHashMap);
			
			clickElement(driver, null, orSalesInvoiceEnq.getElement(driver, "Button_Confirm", ScenarioDetailsHashMap, 10), 2);
			String msg=getInnerText(driver, null, orSalesInvoiceEnq.getElement(driver, "TextArea_Message", ScenarioDetailsHashMap, 10), 2);
			assertTwoValues(msg.split(":")[1].trim(), ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "RebillInvoiceMsg", ScenarioDetailsHashMap), "Validating CreditNote Invoiced Msg", ScenarioDetailsHashMap);
			
			String[] sMsg=msg.split(":");
			ExcelUtils.setCellData("Ocean_CreaditNote", RowNo, "CRNoteId", sMsg[1].trim(), ScenarioDetailsHashMap);
		
			
		}else if (ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "CRNoteType", ScenarioDetailsHashMap).equalsIgnoreCase("Credit/Rebill")) {
			
			replaceTextofTextField(driver, null, orSalesInvoiceEnq.getElement(driver, "Input_BillToParty", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "RebillParty", ScenarioDetailsHashMap), 2);
//			double taxvalue=0.00;
			int rows=orSalesInvoiceEnq.getElements(driver, "GridRows_CreditNote", ScenarioDetailsHashMap, 10).size();
			for (int i = 1; i <= rows; i++) {
				tot=tot+Double.parseDouble(getInnerText(driver, By.xpath("//tr[@class='summaryHeader']/following-sibling::tr["+i+"]/td[9]"), null, 10).replace(" ", ""));
				taxvalue=taxvalue+Double.parseDouble(readValue(driver, null, By.xpath("//tr[@class='summaryHeader']/following-sibling::tr["+i+"]/td/input[@name='totalTaxOnCreditAmount']")).replace(" ", ""));
				
			}
			assertTwoValues(readValue(driver, orSalesInvoiceEnq.getElement(driver, "Input_OsAmount", ScenarioDetailsHashMap, 10), null).replace(" ", ""), ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "TotalOutStandingAmount", ScenarioDetailsHashMap), "Validating Total outstanding amount including Tax", ScenarioDetailsHashMap);
			assertTwoValues(readValue(driver, orSalesInvoiceEnq.getElement(driver, "Input_CancelAmount", ScenarioDetailsHashMap, 10), null).replace(" ", ""), dF.format(tot+taxvalue)+"", "Validating Total Cancellation Amount including Tax", ScenarioDetailsHashMap);
			
			
			clickElement(driver, null, orSalesInvoiceEnq.getElement(driver, "Button_Confirm", ScenarioDetailsHashMap, 10), 2);
			String msg=getInnerText(driver, null, orSalesInvoiceEnq.getElement(driver, "TextArea_Message", ScenarioDetailsHashMap, 10), 2);
			assertTwoValues(msg.split(":")[1].trim(), ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "RebillInvoiceMsg", ScenarioDetailsHashMap), "Validating CreditNote Invoiced Msg", ScenarioDetailsHashMap);
			
			String[] sMsg=msg.split(":");
			ExcelUtils.setCellData("Ocean_CreaditNote", RowNo, "CRNoteId", sMsg[1].trim().substring(0,sMsg[1].trim().indexOf(" ")), ScenarioDetailsHashMap);
			ExcelUtils.setCellData("Ocean_CreaditNote", RowNo, "RebillInvoiceId", sMsg[2].trim(), ScenarioDetailsHashMap);
			System.out.println(sMsg[1].trim().substring(0,sMsg[1].trim().indexOf(" ")));
			System.out.println(sMsg[2].trim());
			System.out.println(msg.substring(0,msg.indexOf(":")));
		}
		
		clickElement(driver, null, orSalesInvoiceEnq.getElement(driver, "Button_Continue", ScenarioDetailsHashMap, 10), 2);
		/*replaceTextofTextField(driver, null, orSalesInvoiceEnq.getElement(driver, "Input_TransactionId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "CRNoteId", ScenarioDetailsHashMap), 2);
		clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
		assertInnerText(driver, By.id("dtInvoiceNumber1"), null, ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "CRNoteId", ScenarioDetailsHashMap), "Invoice Number", "Validating CreditNote Number from Grid in Sales Invoice Enquiry page", ScenarioDetailsHashMap);
		
		if(ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "CRNoteType", ScenarioDetailsHashMap).equalsIgnoreCase("Credit/Rebill")){
			replaceTextofTextField(driver, null, orSalesInvoiceEnq.getElement(driver, "Input_TransactionId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "RebillInvoiceId", ScenarioDetailsHashMap), 2);
			clickElement(driver, null, orCommon.getElement(driver, "SearchButton", ScenarioDetailsHashMap, 10), 2);
			assertInnerText(driver, By.id("dtInvoiceNumber1"), null, ExcelUtils.getCellData("Ocean_CreaditNote", RowNo, "RebillInvoiceId", ScenarioDetailsHashMap), "Invoice Number", "Validating Rebill CreditNote Numberfrom Grid in Sales Invoice Enquiry page", ScenarioDetailsHashMap);
		
		}*/
		
		
	}

	public static void assertProfitShareSettlement(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		DecimalFormat dF = new DecimalFormat("#0.00");
		AppReusableMethods.selectMenu(driver, ETransMenu.Profit_Share_Settlement, "Settlement of Profit Share", ScenarioDetailsHashMap);
		selectOptionFromDropDown(driver, null, orProfitShareSatlement.getElement(driver, "DropDown_ModeOfTransport", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Ocean_ProfitShareSatlement", RowNo, "ShipmentMode", ScenarioDetailsHashMap));
		clickElement(driver, null, orCommon.getElement(driver, "SearchButton2", ScenarioDetailsHashMap, 10), 2);
		pauseExecution(3000);
//		AppReusableMethods.selectValueFromLov(driver, orProfitShareSatlement.getElement(driver, "Image_OBLId", ScenarioDetailsHashMap, 10), orProfitShareSatlement, "LOV_ShipmentRefNo", ExcelUtils.getCellData("Ocean_ProfitShareSatlement", RowNo, "OblId", ScenarioDetailsHashMap), ScenarioDetailsHashMap);
		replaceTextofTextField(driver, null, orProfitShareSatlement.getElement(driver, "Input_MblId", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Ocean_ProfitShareSatlement", RowNo, "OblId", ScenarioDetailsHashMap), 2);
		clickElement(driver, null, orCommon.getElement(driver, "SearchButton2", ScenarioDetailsHashMap, 10), 2);
		clickElement(driver, null, orProfitShareSatlement.getElement(driver, "CheckBox_Obl", ScenarioDetailsHashMap, 10), 2);
		clickElement(driver, null, orProfitShareSatlement.getElement(driver, "Tab_MainDetails", ScenarioDetailsHashMap, 10), 2);

		int gridRows= orProfitShareSatlement.getElements(driver, "GridRows_ProfitShareCalculation", ScenarioDetailsHashMap, 10).size();
		//	  int rows=ExcelUtils.getCellDataRowCount("Ocean_ProfitShareSatlement", ScenarioDetailsHashMap);
		int rows= ExcelUtils.getSubScenarioRowCount("Ocean_ProfitShareSatlement", ScenarioDetailsHashMap);
		double totAmountHouse=0.00;
		double totAmountMaster=0.00;
		double agreedAmountHouse=0.00;
		double agreedAmountMaster=0.00;
		int counter=0;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for (int i = 1; i <= rows; i++) {
			for (int j = 0; j < gridRows; j++) {
				if(getInnerText(driver, By.id("aw51-cell-1-"+j+"-box-text"), null, 10).equals(ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "HBLId", ScenarioDetailsHashMap)) && !ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "HouseAmount", ScenarioDetailsHashMap).equals(""))
				{
					clickElement(driver, By.id("aw51-cell-0-"+j+"-box-text"), null, 10);
					assertValue(driver, null, orProfitShareSatlement.getElement(driver, "Input_AgentQuote", ScenarioDetailsHashMap, 10), ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "AgentQuote", ScenarioDetailsHashMap), "Agent Quote", "validating Agent Quote for the charge code", ScenarioDetailsHashMap);  
					
					assertInnerText(driver, By.id("aw51-cell-2-"+j+"-box-text"), null, ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "ChargeCodeDesc", ScenarioDetailsHashMap), "Charge code", "Validating Agent Charge Code From Grid", ScenarioDetailsHashMap);
					assertTwoValues(getInnerText(driver, By.id("aw51-cell-4-"+j+"-box-text"), null, 10).replace(" ", ""), ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "HouseRate", ScenarioDetailsHashMap),  "Validating House Rate From Grid", ScenarioDetailsHashMap);
					assertTwoValues(getInnerText(driver, By.id("aw51-cell-5-"+j+"-box-text"), null, 10).replace(" ", ""), ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "HouseAmount", ScenarioDetailsHashMap),  "Validating House Amount From Grid", ScenarioDetailsHashMap);
					assertTwoValues(getInnerText(driver, By.id("aw51-cell-6-"+j+"-box-text"), null, 10).replace(" ", ""), ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "MasterRate", ScenarioDetailsHashMap),  "Validating Master Rate From Grid", ScenarioDetailsHashMap);
					assertTwoValues(getInnerText(driver, By.id("aw51-cell-7-"+j+"-box-text"), null, 10).replace(" ", ""), ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "MasterAmount", ScenarioDetailsHashMap),  "Validating Master Amount From Grid", ScenarioDetailsHashMap);
					
					totAmountHouse=totAmountHouse+Double.parseDouble(getInnerText(driver, By.id("aw51-cell-5-"+j+"-box-text"), null, 10).replace(" ", ""));
					totAmountMaster=totAmountMaster+Double.parseDouble(getInnerText(driver, By.id("aw51-cell-7-"+j+"-box-text"), null, 10).replace(" ", ""));
					
					System.out.println("agreed House amount"+dF.format(Double.parseDouble(getInnerText(driver, By.id("aw51-cell-5-"+j+"-box-text"), null, 10).replace(" ", ""))+Double.parseDouble(ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "AgreedHouseAmount", ScenarioDetailsHashMap))));
					ExcelUtils.setCellData_Without_DataSet("Ocean_ProfitShareSatlement", 	i	, "AgreedHouseAmount", dF.format(Double.parseDouble(getInnerText(driver, By.id("aw51-cell-5-"+j+"-box-text"), null, 10).replace(" ", ""))+Double.parseDouble(ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "AgreedHouseAmount", ScenarioDetailsHashMap))), ScenarioDetailsHashMap);
					ExcelUtils.setCellData_Without_DataSet("Ocean_ProfitShareSatlement", i, "AgreedMasterAmount", dF.format(Double.parseDouble(getInnerText(driver, By.id("aw51-cell-7-"+j+"-box-text"), null, 10).replace(" ", ""))+Double.parseDouble(ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "AgreedMasterAmount", ScenarioDetailsHashMap))), ScenarioDetailsHashMap);
//					replaceTextofTextField(driver, By.id("aw51-cell-9-"+j+"-box-text"), null, Keys.TAB, 10);
					
					js.executeScript("arguments[0].scrollLeft = arguments[1];",locateElement(driver, By.id("aw51-scroll-box"), 10), 120);
					if(j<1){
					action_Key(driver, Keys.TAB);
					}else{
						WebElement element = driver.findElement( By.id("houseFreightAgreedAmount"+j));
					    Actions navigator = new Actions(driver);
					    navigator.click(element)
					        .sendKeys(Keys.HOME)
					        .keyDown(Keys.SHIFT)
					        .sendKeys(Keys.END)
					        .keyUp(Keys.SHIFT)
					        .sendKeys(Keys.BACK_SPACE)
					        .perform();
					}
					
					
					
					locateElement(driver,  By.id("houseFreightAgreedAmount"+j), 10).sendKeys(ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "AgreedHouseAmount", ScenarioDetailsHashMap));
//					replaceTextofTextField(driver, By.id("houseFreightAgreedAmount"+j), null, ExcelUtils.getCellData("Ocean_ProfitShareSatlement", i, "AgreedHouseAmount", ScenarioDetailsHashMap), 10);
					replaceTextofTextField(driver, By.id("houseFreightAgreedAmount"+j), null, Keys.TAB, 10);
					locateElement(driver,  By.id("masterFreightAgreedAmount"+j), 10).sendKeys(ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "AgreedMasterAmount", ScenarioDetailsHashMap));
//					replaceTextofTextField(driver, By.id("masterFreightAgreedAmount"+j), null, ExcelUtils.getCellData("Ocean_ProfitShareSatlement", i, "AgreedMasterAmount", ScenarioDetailsHashMap), 10);
					replaceTextofTextField(driver, By.id("masterFreightAgreedAmount"+j), null, Keys.TAB, 10);

					agreedAmountHouse=agreedAmountHouse+Double.parseDouble(readValue(driver, null, By.id("houseFreightAgreedAmount"+j)).replace(" ", ""));
					agreedAmountMaster=agreedAmountMaster+Double.parseDouble(readValue(driver, null,  By.id("masterFreightAgreedAmount"+j)).replace(" ", ""));
					assertValue(driver, By.id("agreedProfitShare"+j), null, dF.format(Double.parseDouble(ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "AgreedHouseAmount", ScenarioDetailsHashMap))-Double.parseDouble(ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "AgreedMasterAmount", ScenarioDetailsHashMap))), "Agreed Profit Sahre", "Validating agreed Profit Share", ScenarioDetailsHashMap);
					ExcelUtils.setCellData_Without_DataSet("Ocean_ProfitShareSatlement", i, "AgreedProfitShare", readValue(driver, null, By.id("agreedProfitShare"+j)), ScenarioDetailsHashMap);
					counter=counter+1;
					js.executeScript("arguments[0].scrollLeft = arguments[1];",locateElement(driver, By.id("aw51-scroll-box"), 10), 10);
					break;
				}else if(ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "HouseAmount", ScenarioDetailsHashMap).equals("")&& j==gridRows-1){
					counter=counter+1;
				}
			}
		}
		if(counter!= rows){
			assertTwoValues("House Not Found", "House Found", "Validating House availablity in grid only "+counter+" House Found", ScenarioDetailsHashMap);
		}
		//assertValue(driver, null, orProfitShareSatlement.getElement(driver, "Text_HouseFreightActAmount", ScenarioDetailsHashMap, 10), dF.format(totAmountHouse), "House Freight Act-Amount", "Validating Total House Freight Act-Amount", ScenarioDetailsHashMap);
		//assertTwoValues((GenericMethods.getInnerText(driver, null, orProfitShareSatlement.getElement(driver, "Text_HouseFreightActAmount", ScenarioDetailsHashMap, 2), 10)).replace(" ", ""), (dF.format(totAmountHouse)).replace(" ", ""), "Validating Total House Freight Act-Amount", ScenarioDetailsHashMap);
		System.out.println("value :"+readValue(driver, orProfitShareSatlement.getElement(driver, "Text_HouseFreightActAmount", ScenarioDetailsHashMap, 2), null).replace(" ", ""));
		System.out.println("getinnertext :"+GenericMethods.getInnerText(driver, null, orProfitShareSatlement.getElement(driver, "Text_HouseFreightActAmount", ScenarioDetailsHashMap, 2), 10).replace(" ", ""));
		assertTwoValues((readValue(driver, orProfitShareSatlement.getElement(driver, "Text_HouseFreightActAmount", ScenarioDetailsHashMap, 2), null)).replace(" ", ""), (dF.format(totAmountHouse)).replace(" ", ""), "Validating Total House Freight Act-Amount", ScenarioDetailsHashMap);
		
		//assertValue(driver, null, orProfitShareSatlement.getElement(driver, "Text_MasterFreightActAmount", ScenarioDetailsHashMap, 10), dF.format(totAmountMaster), "Master Freight Act-Amount", "Validating Total Master Freight Act-Amount", ScenarioDetailsHashMap);
		//assertTwoValues((GenericMethods.getInnerText(driver, null, orProfitShareSatlement.getElement(driver, "Text_MasterFreightActAmount", ScenarioDetailsHashMap, 2), 10)).replace(" ", ""), (dF.format(totAmountMaster)).replace(" ", ""), "Validating Total Master Freight Act-Amount", ScenarioDetailsHashMap);
		assertTwoValues((readValue(driver, orProfitShareSatlement.getElement(driver, "Text_MasterFreightActAmount", ScenarioDetailsHashMap, 2), null)).replace(" ", ""), (dF.format(totAmountMaster)).replace(" ", ""), "Validating Total Master Freight Act-Amount", ScenarioDetailsHashMap);
		
		//assertValue(driver, null, orProfitShareSatlement.getElement(driver, "Text_TotalActualProfitShare", ScenarioDetailsHashMap, 10), dF.format(totAmountHouse - totAmountMaster), "Actual Profit Share", "Validating Total Actual Profit Share", ScenarioDetailsHashMap);
		//assertTwoValues((GenericMethods.getInnerText(driver, null, orProfitShareSatlement.getElement(driver, "Text_TotalActualProfitShare", ScenarioDetailsHashMap, 2), 10)).replace(" ", ""), (dF.format(totAmountHouse - totAmountMaster)).replace(" ", ""), "Validating Total Actual Profit Share", ScenarioDetailsHashMap);
		assertTwoValues((readValue(driver, orProfitShareSatlement.getElement(driver, "Text_TotalActualProfitShare", ScenarioDetailsHashMap, 2), null)).replace(" ", ""), (dF.format(totAmountHouse - totAmountMaster)).replace(" ", ""), "Validating Total Actual Profit Share", ScenarioDetailsHashMap);
		
		//assertValue(driver, null, (orProfitShareSatlement.getElement(driver, "Text_TotalHouseAgreedAmount", ScenarioDetailsHashMap, 10)), dF.format(agreedAmountHouse), "House Freight Act-Amount 	", "Validating Total Agreed House Freight Act-Amount", ScenarioDetailsHashMap);
		//assertTwoValues((GenericMethods.getInnerText(driver, null, orProfitShareSatlement.getElement(driver, "Text_TotalHouseAgreedAmount", ScenarioDetailsHashMap, 2), 10)).replace(" ", ""), (dF.format(agreedAmountHouse)).replace(" ", ""), "Validating Total Agreed House Freight Act-Amount", ScenarioDetailsHashMap);
		assertTwoValues((readValue(driver, orProfitShareSatlement.getElement(driver, "Text_TotalHouseAgreedAmount", ScenarioDetailsHashMap, 2), null)).replace(" ", ""), (dF.format(agreedAmountHouse)).replace(" ", ""), "Validating Total Agreed House Freight Act-Amount", ScenarioDetailsHashMap);
		

		//assertValue(driver, null, orProfitShareSatlement.getElement(driver, "Text_TotalMasterAgreedAmount", ScenarioDetailsHashMap, 10), dF.format(agreedAmountMaster), "Master Freight Act-Amount", "Validating Total Agreed Master Freight Act-Amount", ScenarioDetailsHashMap);
		//assertTwoValues((GenericMethods.getInnerText(driver, null, orProfitShareSatlement.getElement(driver, "Text_TotalMasterAgreedAmount", ScenarioDetailsHashMap, 2), 10)).replace(" ", ""), dF.format(agreedAmountMaster).replace(" ", ""), "Validating Total Agreed Master Freight Act-Amount", ScenarioDetailsHashMap);
		assertTwoValues((readValue(driver, orProfitShareSatlement.getElement(driver, "Text_TotalMasterAgreedAmount", ScenarioDetailsHashMap, 2), null)).replace(" ", ""), dF.format(agreedAmountMaster).replace(" ", ""), "Validating Total Agreed Master Freight Act-Amount", ScenarioDetailsHashMap);
		
		//assertValue(driver, null, orProfitShareSatlement.getElement(driver, "Text_TotalAgreedProfitShare", ScenarioDetailsHashMap, 10), dF.format(agreedAmountHouse - agreedAmountMaster), "Actual Profit Share", "Validating Total Agreed  Actual Profit Share", ScenarioDetailsHashMap);
		//assertTwoValues((GenericMethods.getInnerText(driver, null, orProfitShareSatlement.getElement(driver, "Text_TotalAgreedProfitShare", ScenarioDetailsHashMap, 2), 10)).replace(" ", ""), dF.format(agreedAmountHouse - agreedAmountMaster).replace(" ", ""), "Validating Total Agreed  Actual Profit Share", ScenarioDetailsHashMap);
		assertTwoValues((readValue(driver, orProfitShareSatlement.getElement(driver, "Text_TotalAgreedProfitShare", ScenarioDetailsHashMap, 2), null)).replace(" ", ""), dF.format(agreedAmountHouse - agreedAmountMaster).replace(" ", ""), "Validating Total Agreed  Actual Profit Share", ScenarioDetailsHashMap);
		
		//ExcelUtils.setCellData("Ocean_ProfitShareSatlement", RowNo, "TotAgreedProfitShare", dF.format(agreedAmountHouse - agreedAmountMaster), ScenarioDetailsHashMap);

		clickElement(driver, null, orProfitShareSatlement.getElement(driver, "Tab_PSAllocation", ScenarioDetailsHashMap, 10), 2);
		gridRows=orProfitShareSatlement.getElements(driver, "Grid_PSAllocation", ScenarioDetailsHashMap, 10).size();
		rows= ExcelUtils.getSubScenarioRowCount("Ocean_ProfitShareSatlement", ScenarioDetailsHashMap);
		for (int i = 1; i <= rows; i++) {
			for (int j = 0; j < gridRows; j++) {
			if (getInnerText(driver, By.id("myGrid-cell-1-"+j+"-box-text"), null, 10).equals(ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "HBLId", ScenarioDetailsHashMap))) {
				double tot=Double.parseDouble(ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "AgreedProfitShare", ScenarioDetailsHashMap).replace(" ", ""))*(Double.parseDouble(ExcelUtils.getCellDataWithoutDataSet("Ocean_ProfitShareSatlement", i, "ProfitShareing", ScenarioDetailsHashMap).replace(" ", ""))/100);
				ExcelUtils.setCellData_Without_DataSet("Ocean_ProfitShareSatlement", i, "ProfitShareAmount", dF.format(tot), ScenarioDetailsHashMap);
				//assertInnerText(driver, By.id("myGrid-cell-5-"+j+"-box-text"), null,dF.format(tot) , "Profit Share Amount", "Validating Profit Share Amount in PS Allocation tab", ScenarioDetailsHashMap);
				assertTwoValues((GenericMethods.getInnerText(driver, By.id("myGrid-cell-5-"+j+"-box-text"), null, 2)).replace(" ", ""), (dF.format(tot)).replace(" ", ""), "Validating Profit Share Amount in PS Allocation tab", ScenarioDetailsHashMap);
				break;
			}
			}
		}


		clickElement(driver, null, orProfitShareSatlement.getElement(driver, "Button_RequestInvoice", ScenarioDetailsHashMap, 10), 2);
		try{
			selectWindow(driver);
			clickElement(driver, null, orProfitShareSatlement.getElement(driver, "Button_Post", ScenarioDetailsHashMap, 10), 2);
			
		}catch (Exception e) {
			
		}
		switchToParent(driver);
		AppReusableMethods.selectMainFrame(driver, ScenarioDetailsHashMap);
		assertInnerText(driver, null, orProfitShareSatlement.getElement(driver, "Text_MsgInvoiceCreation", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("Ocean_ProfitShareSatlement", RowNo, "InvoiceMgs", ScenarioDetailsHashMap), "Invoice Msg", "Validating Invoice creation Status Msg", ScenarioDetailsHashMap);

	}

	public static void assertProfitShareSatlementHblChargeCost(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		DecimalFormat dF = new DecimalFormat("#0.00");
		clickElement(driver, null, orCommon.getElement(driver, "Tab_Chargesacost", ScenarioDetailsHashMap, 10), 2);
		int rows=orProfitShareSatlement.getElements(driver, "Grid_CostchargesRows", ScenarioDetailsHashMap, 10).size();
		int flag=0;
		for (int i = 1; i <= rows; i++) {
			if(!readValue(driver, null, By.id("chargesVOList.chargeCode"+i)).equals("")){
				if(readValue(driver, null, By.id("chargesVOList.chargeCode"+i)).equals(ExcelUtils.getCellData("Ocean_ProfitShareSatlement", RowNo, "ChargeCodePs", ScenarioDetailsHashMap))){
					assertValue(driver, By.id("chargesVOList.chargeCode"+i), null, ExcelUtils.getCellData("Ocean_ProfitShareSatlement", RowNo, "ChargeCodePs", ScenarioDetailsHashMap), "Charges Code", "Validating Charges Code From Grid", ScenarioDetailsHashMap);
					assertValue(driver, By.id("chargesVOList.chargeDesc"+i), null, ExcelUtils.getCellData("Ocean_ProfitShareSatlement", RowNo, "Charges", ScenarioDetailsHashMap), "Charges", "Validating Charges From Grid", ScenarioDetailsHashMap);
					
					//assertValue(driver, By.id("chargesVOList.salesPriceAmt"+i), null, dF.format(Double.parseDouble(ExcelUtils.getCellData("Ocean_ProfitShareSatlement", RowNo, "ProfitShareAmount", ScenarioDetailsHashMap))*(-1)), "Price Amount", "Validating Price Amount From Grid", ScenarioDetailsHashMap);
					assertTwoValues((GenericMethods.getInnerText(driver, By.id("chargesVOList.salesPriceAmt"+i), null, 2)).replace(" ", ""), (dF.format(Double.parseDouble(ExcelUtils.getCellData("Ocean_ProfitShareSatlement", RowNo, "ProfitShareAmount", ScenarioDetailsHashMap))*(-1))).replace(" ", ""), "Validating Price Amount From Grid", ScenarioDetailsHashMap);
					flag=flag+1;
					break;
				}

			}else{
				break;
			}
		}
		/*if(flag!= rows){
			assertTwoValues("House Not Found", "Found", "Validating profit Share charge code availabilty in Charges Cost Screen for HBL found="+flag, ScenarioDetailsHashMap);
		}
*/

		/*selectOptionFromDropDown(driver, null, orProfitShareSatlement.getElement(driver, "DropDown_NavigationList", ScenarioDetailsHashMap, 10), "Profit Enquiry");
		clickElement(driver, null, orProfitShareSatlement.getElement(driver, "Image_GoNavigationList", ScenarioDetailsHashMap, 10), 2);
		try{
			handleAlert(driver, "accept");
		}catch (Exception e) {
		}*/

	}

	public static void addNewCharges(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		try{
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "Tab_Chargesacost", ScenarioDetailsHashMap, 10), 2);
		}catch (Exception e) {
			oROceanNsibObl.getElement(driver, "Tab_HBLChargesCode", ScenarioDetailsHashMap, 10).click();
		}
		pauseExecution(5000);
		int rows=orProfitShareSatlement.getElements(driver, "Grid_CostchargesRows", ScenarioDetailsHashMap, 10).size();
		System.out.println("rowcount"+ExcelUtils.getCellDataRowCount("CostAndCharges", ScenarioDetailsHashMap)+"row  "+rows);
		for (int h = 1; h <= ExcelUtils.getCellDataRowCount("CostAndCharges", ScenarioDetailsHashMap); h++) {
			for (int i = 1; i <= rows; i++) {
				if(readValue(driver, null, By.id("chargesVOList.chargeCode"+i)).equals("")){
					doubleClickOnElement(driver, By.id("chargeCode"+i), null, 10);
					replaceTextofTextField(driver, By.id("chargesVOList.chargeCode"+i),null , ExcelUtils.getCellData("CostAndCharges", h, "ChargeCode", ScenarioDetailsHashMap), 10);
					action_Key(driver, Keys.TAB);
					try{
						handleAlert(driver, "accept");
					}catch (Exception e) {
						System.out.println("no alert Found");
					}
					pauseExecution(3000);
										selectOptionFromDropDown(driver,  null,locateElement(driver, By.id("chargesVOList.prepaidAndCollect"+i), 10), ExcelUtils.getCellData("CostAndCharges", h, "PrepaidAndCollect", ScenarioDetailsHashMap));
					//					replaceTextofTextField(driver, By.id("chargesVOList.billToPartyMode"+i), null, ExcelUtils.getCellData("CostAndCharges", h, "BillToParty", ScenarioDetailsHashMap), 10);
					selectFromAJAX(driver, By.id("chargesVOList.billToPartyMode"+i), null, ExcelUtils.getCellData("CostAndCharges", h, "BillToParty", ScenarioDetailsHashMap), orChargeCost, "AJAX_Option", 10, ScenarioDetailsHashMap);
					replaceTextofTextField(driver, By.id("chargesVOList.salesPriceAmt"+i), null, ExcelUtils.getCellData("CostAndCharges", h, "SalesPriceAmount", ScenarioDetailsHashMap), 10);

					break;
				}
			}
		}
		clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
	}

	public static void acceptChargeCost(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		//		SeaHBL.seaHBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
		try{
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "Tab_Chargesacost", ScenarioDetailsHashMap, 10), 2);
			}catch (Exception e) {
				oROceanNsibObl.getElement(driver, "Tab_HBLChargesCode", ScenarioDetailsHashMap, 10).click();
			}
			int rows=orProfitShareSatlement.getElements(driver, "Grid_CostchargesRows", ScenarioDetailsHashMap, 10).size();
		System.out.println("rows="+rows);
		for (int i = 1; i <= rows; i++) {
			if(!readValue(driver, null, By.id("chargesVOList.chargeCode"+i)).equals("")){
				System.out.println("valueeeeee"+readValue(driver, null, By.id("chargesVOList.chargeCode"+i)));
				System.out.println("attributesdfsdf="+locateElement(driver, By.id("multiSelectCheckboxchargeGrid"+i), 10).isSelected());
				if(readValue(driver, null, By.id("chargesVOList.chargeCode"+i)).equals(ExcelUtils.getCellData("CostAndCharges", RowNo, "ChargeCode", ScenarioDetailsHashMap)) && getInnerText(driver, By.id("spanchargesVOList.salesStatus"+i), null, 10).equalsIgnoreCase("Ready")){
					System.out.println("attribute="+locateElement(driver, By.id("multiSelectCheckboxchargeGrid"+i), 10).isSelected());
//					if(locateElement(driver, By.id("multiSelectCheckboxchargeGrid"+i), 10).getAttribute("checked").equals(false)){
					if(!locateElement(driver, By.id("multiSelectCheckboxchargeGrid"+i), 10).isSelected()){
						clickElement(driver, By.id("multiSelectCheckboxchargeGrid"+i), null, 10);
					}
				}else{
					if(locateElement(driver, By.id("multiSelectCheckboxchargeGrid"+i), 10).isSelected()){
						clickElement(driver, By.id("multiSelectCheckboxchargeGrid"+i), null, 10);
					}
				}
			}else{
				break;
			}
		}
		clickElement(driver, null, orChargeCost.getElement(driver, "Button_Accept", ScenarioDetailsHashMap, 10	), 2);
		GenericMethods.pauseExecution(2000);
		clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
	}

	public static void assertConsolidationInvoice(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap, int RowNo){
		DecimalFormat dF = new DecimalFormat("#0.00");
		AppReusableMethods.selectMenu(driver, ETransMenu.SalesInvoice, "Invoice Summary", ScenarioDetailsHashMap);
		replaceTextofTextField(driver, null, orSalesInvoice.getElement(driver, "Text_BillToParty", ScenarioDetailsHashMap, 10), ExcelUtils.getCellData("CostAndCharges", RowNo, "BillToParty", ScenarioDetailsHashMap), 2);
		replaceTextofTextField(driver, By.id("executionDate"), null, ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLDate", ScenarioDetailsHashMap), 10);
		clickElement(driver, null, orCommon.getElement(driver, "SearchButton2", ScenarioDetailsHashMap, 10), 2);
		clickElement(driver, null, orSalesInvoice.getElement(driver, "Button_Deselect", ScenarioDetailsHashMap, 10), 2);
		int rows=orSalesInvoice.getElements(driver, "Grid_Rows", ScenarioDetailsHashMap, 10).size();
		for (int h = 1; h <= ExcelUtils.getSubScenarioRowCount("SE_HBLMainDetails", ScenarioDetailsHashMap) ; h++) {
			clickElement(driver, By.id("myGrid-cell-2-0-box"), null, 10);
			for (int i = 0; i <rows; i++) {
				if(getInnerText(driver, By.id("myGrid-cell-4-"+i+"-box-text"), null, 10).equals(ExcelUtils.getCellDataWithoutDataSet("SE_HBLMainDetails", h, "HBLId", ScenarioDetailsHashMap))){
					clickElement(driver, By.id("myGrid-cell-0-"+i+"-box-marker"), null, 10);
					break;

				}
				action_Key(driver, Keys.ARROW_DOWN);
			}
		}


		clickElement(driver, null, orSalesInvoice.getElement(driver, "Button_Request", ScenarioDetailsHashMap, 10), 2);
		double totAmount=0.0;
		for (int h = 1; h <= ExcelUtils.getSubScenarioRowCount("CostAndCharges", ScenarioDetailsHashMap) ; h++) {
			if(ExcelUtils.getCellDataWithoutDataSet("CostAndCharges", h, "ConsolidateInvoice", ScenarioDetailsHashMap).equalsIgnoreCase("yes")){
				totAmount=totAmount+Double.parseDouble(ExcelUtils.getCellDataWithoutDataSet("CostAndCharges", h, "SalesPriceAmount", ScenarioDetailsHashMap));
			}

		}
		System.out.println("tot="+totAmount);
		//		rows=orSalesInvoice.getElements(driver, "Grid_Rows", ScenarioDetailsHashMap, 10).size();
		//		assertTwoValues(rows+"", "1", "Validating NO of Invoice generated", ScenarioDetailsHashMap);
//		assertInnerText(driver, By.id("myGrid-cell-6-0-box-text"), null, dF.format(totAmount), "Invoice Amount", "Vlidating Total Invoice Amount for multiple houses", ScenarioDetailsHashMap);
		assertTwoValues(getInnerText(driver, By.id("myGrid-cell-6-0-box-text"), null, 10).replace(" ", ""), dF.format(totAmount), "Validating Total Invoice Amount for multiple houses with single invoice", ScenarioDetailsHashMap);


	}



	public static void hblChargesAndCost(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		clickElement(driver, null, orCommon.getElement(driver, "Tab_HBLMainDetails", ScenarioDetailsHashMap, 10), 2);
		String hblId=ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "HBLId", ScenarioDetailsHashMap);
		driver.findElement(By.xpath("//td[text()='"+hblId+"']")).click();
		GenericMethods.pauseExecution(2000);

	}
	
	public static void updateProfitShareAmountsHBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		DecimalFormat dF = new DecimalFormat("#0.00");
		DecimalFormat dFRate = new DecimalFormat("#0.000");
		SeaHBL.seaHBL_SearchList(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "Tab_Chargesacost", ScenarioDetailsHashMap, 10), 2);
		selectOptionFromDropDown(driver, null, orChargeCost.getElement(driver, "DropDown_GridViewType", ScenarioDetailsHashMap, 10), "Detailed");
		int rows=orProfitShareSatlement.getElements(driver, "Grid_CostchargesRows", ScenarioDetailsHashMap, 10).size();
		System.out.println("rows="+rows);
		double rate=0.0;
		for (int i = 1; i <= rows; i++) {
			if(!readValue(driver, null, By.id("chargesVOList.chargeCode"+i)).equals("")){
				System.out.println("valueeeeee"+readValue(driver, null, By.id("chargesVOList.chargeCode"+i)));
				System.out.println("attributesdfsdf="+locateElement(driver, By.id("multiSelectCheckboxchargeGrid"+i), 10).isSelected());
				if(readValue(driver, null, By.id("chargesVOList.chargeCode"+i)).equals(ExcelUtils.getCellData("Ocean_ProfitShareSatlement", RowNo, "ChargeCode", ScenarioDetailsHashMap))){
					rate=Double.parseDouble(getInnerText(driver, By.id("spanchargesVOList.salesRate"+i), null, 10))* Double.parseDouble(getInnerText(driver, By.id("spanchargesVOList.salesPriceExRate"+i), null, 10));
					ExcelUtils.setCellData("Ocean_ProfitShareSatlement", RowNo, "HouseRate", dFRate.format(rate), ScenarioDetailsHashMap);
					ExcelUtils.setCellData("Ocean_ProfitShareSatlement", RowNo, "HouseAmount", getInnerText(driver, By.id("spanchargesVOList.salesInvAmount"+i), null, 10), ScenarioDetailsHashMap);
					
					if (ExcelUtils.getCellData("SE_HBLMainDetails", RowNo, "ConsolidationORB2B", ScenarioDetailsHashMap).equalsIgnoreCase("B2B")) {
						rate=Double.parseDouble(getInnerText(driver, By.id("spanchargesVOList.costRate"+i), null, 10))* Double.parseDouble(getInnerText(driver, By.id("spanchargesVOList.costPriceExRate"+i), null, 10));
						ExcelUtils.setCellData("Ocean_ProfitShareSatlement", RowNo, "MasterRate", dFRate.format(rate), ScenarioDetailsHashMap);
						ExcelUtils.setCellData("Ocean_ProfitShareSatlement", RowNo, "MasterAmount", getInnerText(driver, By.id("spanchargesVOList.costInvAmount"+i), null	, 10), ScenarioDetailsHashMap);
						
					}
				}
			}else{
				break;
			}
		}
		clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		
	}
	
	public static void updateProfitShareAmountsMBL(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo)
	{
		DecimalFormat dF = new DecimalFormat("#0.00");
		DecimalFormat dFRate = new DecimalFormat("#0.000");
		searchOBL(driver, ScenarioDetailsHashMap, RowNo);
		GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "Tab_Chargesacost", ScenarioDetailsHashMap, 10), 2);
		selectOptionFromDropDown(driver, null, orChargeCost.getElement(driver, "DropDown_GridViewType", ScenarioDetailsHashMap, 10), "Detailed");
		int rows=orProfitShareSatlement.getElements(driver, "Grid_CostchargesRows", ScenarioDetailsHashMap, 10).size();
		System.out.println("rows="+rows);
		double rate=0.0;
		double amount=0.0;
		for (int i = 1; i <= rows; i++) {
			if(!readValue(driver, null, By.id("chargesVOList.chargeCode"+i)).equals("")){
				System.out.println("valueeeeee"+readValue(driver, null, By.id("chargesVOList.chargeCode"+i)));
				System.out.println("attributesdfsdf="+locateElement(driver, By.id("multiSelectCheckboxchargeGrid"+i), 10).isSelected());
				if(readValue(driver, null, By.id("chargesVOList.chargeCode"+i)).equals(ExcelUtils.getCellData("Ocean_ProfitShareSatlement", RowNo, "ChargeCode", ScenarioDetailsHashMap))){
					rate=Double.parseDouble(getInnerText(driver, By.id("spanchargesVOList.costRate"+i), null, 10))* Double.parseDouble(getInnerText(driver, By.id("spanchargesVOList.costPriceExRate"+i), null, 10));
					amount=Double.parseDouble(getInnerText(driver, By.id("spanchargesVOList.costInvAmount"+i), null	, 10))/ExcelUtils.getSubScenarioRowCount("Ocean_ProfitShareSatlement", ScenarioDetailsHashMap);
					for (int j = 1; j <= ExcelUtils.getSubScenarioRowCount("Ocean_ProfitShareSatlement", ScenarioDetailsHashMap); j++) {
						ExcelUtils.setCellData_Without_DataSet("Ocean_ProfitShareSatlement", j, "MasterRate", dFRate.format(rate), ScenarioDetailsHashMap);
						ExcelUtils.setCellData_Without_DataSet("Ocean_ProfitShareSatlement", j, "MasterAmount",dF.format(amount) , ScenarioDetailsHashMap);
						
					}
					
				}else {
					for (int j = 1; j <= ExcelUtils.getSubScenarioRowCount("Ocean_ProfitShareSatlement", ScenarioDetailsHashMap); j++) {
						ExcelUtils.setCellData_Without_DataSet("Ocean_ProfitShareSatlement", j, "MasterRate", "0.000", ScenarioDetailsHashMap);
						ExcelUtils.setCellData_Without_DataSet("Ocean_ProfitShareSatlement", j, "MasterAmount","0.00" , ScenarioDetailsHashMap);
						
					}
				}
			}else{
				break;
			}
		}
		clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
	}
	
	//Added for RAT 28 and RAT 52
	public static void assertExcangeRateAndUplift(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,int RowNo){
		DecimalFormat dF = new DecimalFormat("#0.000000000");
		try{
			GenericMethods.clickElement(driver, null, orCommon.getElement(driver, "Tab_Chargesacost", ScenarioDetailsHashMap, 10), 2);
			}catch (Exception e) {
				oROceanNsibObl.getElement(driver, "Tab_HBLChargesCode", ScenarioDetailsHashMap, 10).click();
			}
			pauseExecution(5000);
			int rows=orProfitShareSatlement.getElements(driver, "Grid_CostchargesRows", ScenarioDetailsHashMap, 10).size();
			System.out.println("rowcount"+ExcelUtils.getCellDataRowCount("CostAndCharges", ScenarioDetailsHashMap)+"row  "+rows);
			double clc=0.00;
			for (int h = 1; h <= ExcelUtils.getCellDataRowCount("CostAndCharges", ScenarioDetailsHashMap); h++) {
				
				for (int i = 1; i <= rows; i++) {
					if(readValue(driver, null, By.id("chargesVOList.chargeCode"+i)).equals("")){
						doubleClickOnElement(driver, By.id("chargeCode"+i), null, 10);
						replaceTextofTextField(driver, By.id("chargesVOList.chargeCode"+i),null , ExcelUtils.getCellData("CostAndCharges", h, "ChargeCode", ScenarioDetailsHashMap), 10);
						action_Key(driver, Keys.TAB);
						try{
							handleAlert(driver, "accept");
						}catch (Exception e) {
							System.out.println("no alert Found");
						}
						pauseExecution(3000);
											selectOptionFromDropDown(driver,  null,locateElement(driver, By.id("chargesVOList.prepaidAndCollect"+i), 10), ExcelUtils.getCellData("CostAndCharges", h, "PrepaidAndCollect", ScenarioDetailsHashMap));
						selectFromAJAX(driver, By.id("chargesVOList.billToPartyMode"+i), null, ExcelUtils.getCellData("CostAndCharges", h, "BillToParty", ScenarioDetailsHashMap), orChargeCost, "AJAX_Option", 10, ScenarioDetailsHashMap);
						replaceTextofTextField(driver, By.id("chargesVOList.salesPriceAmt"+i), null, ExcelUtils.getCellData("CostAndCharges", h, "SalesPriceAmount", ScenarioDetailsHashMap), 10);
						replaceTextofTextField(driver, By.id("chargesVOList.salesPriceCurrency"+i), null, ExcelUtils.getCellData("CostAndCharges", h, "SalesPriceCurrency", ScenarioDetailsHashMap), 10);
						replaceTextofTextField(driver, By.id("chargesVOList.salesInvCurrency"+i), null, ExcelUtils.getCellData("CostAndCharges", h, "SalesInvoiceCurrency", ScenarioDetailsHashMap), 10);
						pauseExecution(2000);
						action_Key(driver, Keys.TAB);
						assertInnerText(driver, By.id("spanchargesVOList.salesPriceExRate"+i), null, ExcelUtils.getCellData("CostAndCharges", h, "ExchnageRate", ScenarioDetailsHashMap), "Validating Exchange Rete", "Validating Price Exchange Rate for assigned invoice currency", ScenarioDetailsHashMap);
						
						pauseExecution(2000);
						action_Key(driver, Keys.ESCAPE);
						pauseExecution(5000);
						doubleClickOnElement(driver, By.id("chargeCode"+i), null, 10);
						
						selectFromAJAX(driver, By.id("chargesVOList.billToPartyMode"+i), null, ExcelUtils.getCellData("CostAndCharges", h, "BillToPartyUplift", ScenarioDetailsHashMap), orChargeCost, "AJAX_Option", 10, ScenarioDetailsHashMap);
						pauseExecution(2000);
						action_Key(driver, Keys.TAB);
						pauseExecution(2000);
						action_Key(driver, Keys.TAB);
						pauseExecution(2000);
						action_Key(driver, Keys.TAB);
						pauseExecution(2000);
						action_Key(driver, Keys.ESCAPE);
						pauseExecution(5000);
						String chargesVOList_salesPriceExRate = driver.findElement(By.id("spanchargesVOList.salesPriceExRate"+i)).getText();
						System.out.println("chargesVOList_salesPriceExRate"+chargesVOList_salesPriceExRate);
						clc=Double.parseDouble(ExcelUtils.getCellData("CostAndCharges", h, "ExchnageRate", ScenarioDetailsHashMap))+(Double.parseDouble(ExcelUtils.getCellData("CostAndCharges", h, "ExchnageRate", ScenarioDetailsHashMap))*Double.parseDouble(ExcelUtils.getCellData("CostAndCharges", h, "UpliftPercentage", ScenarioDetailsHashMap))/100);
						assertInnerText(driver, By.id("spanchargesVOList.salesPriceExRate"+i), null,dF.format(clc)  , "Validating Exchange Rate", "Validating Price Exchange Rate for assigned invoice currency for uplift party", ScenarioDetailsHashMap);
						assertTwoValues(chargesVOList_salesPriceExRate, dF.format(clc), "Validating Exchange Rate", ScenarioDetailsHashMap);

						pauseExecution(7000);
						
						break;
						
					}
				}
			}
			clickElement(driver, null, orCommon.getElement(driver, "SaveButton", ScenarioDetailsHashMap, 10), 2);
		
	}
}
