package global.reusables;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;
import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.swing.JTextField;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



import DriverMethods.StartUp;



/**
 * @author PriyaRanjanM
 *
 */
public class GenericMethods extends StartUp {

	static ScreenRecorder screenRecorder = null;
	static String filename=null;
	// public static int winCount = 1;
	private static String curState;
	public static String parent = null;
	public static String child = null;
	//Pavan--Below Variable is added
	static String currentdate,currentTime=null;
	public static AssertionResults assertResult = new AssertionResults();

	static HashSet<String> xlhs= new HashSet<String>();
	static HashSet<String> hs= new HashSet<String>();
	// public static Stack<String> existingWin = new Stack();
	/*
	 * public static void selectWindow(WebDriver driver) { handleInterrupt();
	 * Set handles = null; int i = 0; String title = null; pauseExecution(2000);
	 * handles = driver.getWindowHandles(); Iterator itr = handles.iterator();
	 * 
	 * int count = handles.size(); System.out.println("count="+count); if (count
	 * < winCount) { existingWin.pop(); driver =
	 * driver.switchTo().window((String)existingWin.peek()); winCount =
	 * handles.size(); return; } boolean flag = false; int timeoutInSec = 3;
	 * while (itr.hasNext()) { String handle = (String)itr.next(); if (handle !=
	 * null) { driver = driver.switchTo().window(handle); for (int j = 0; j <=
	 * timeoutInSec; ++j) { flag = false; try { title = driver.getTitle(); }
	 * catch (Exception e) { flag = true; }
	 * 
	 * if (!(flag)) break; pauseExecution(500); } if (i >= timeoutInSec) {
	 * driver.switchTo().window((String)existingWin.peek()); throw new
	 * TimeoutException("Expected Window is not loaded"); }
	 * System.out.println("Handle Title: " + title); if
	 * (!(existingWin.contains(handle))) { existingWin.push(handle); winCount =
	 * handles.size(); return; } } System.out.println("win title::" + title); }
	 * 
	 * if (winCount == handles.size()) { driver =
	 * driver.switchTo().window((String)existingWin.peek()); }
	 * handleInterrupt(); }
	 */
	/**
	 * This method will select the new window.
	 * 
	 * @param driver
	 *            Instance of webDriver.
	 *            @author priyaranjanm
	 */
	public static void selectWindow(WebDriver driver) {
		handleInterrupt();
		parent = null;
		child = null;
		Set<String> winHandel = driver.getWindowHandles();
		Iterator<String> it = winHandel.iterator();
		while (it.hasNext()) {
			parent = it.next();
			child = it.next();
		}

		driver.switchTo().window(child);
		handleInterrupt();

	}
	/**
	 * This Method Will Switch to Parent Window
	 * @param driver Instance of webDriver
	 * @author PriyaRanjanM
	 */
	public static void switchToParent(WebDriver driver)
	{
		driver.switchTo().window(parent);
	}

	/* This method will clean the text field and write the text value passed as
	 * parameter text.
	 * 
	 * @param driver
	 *            Instance of webDriver.
	 * @param by
	 *            The locating mechanism.
	 * @param elem
	 *            Should pass webElement or null if By is used and vice versa.
	 * @param key
	 *            Keboard tab details should be entered..
	 * @param timeoutInSec
	 *            Synchronization timeouts.
	 * @Date May 21 2014
	 * @author Sandeep Jain 
	 *
	 */
	public static void replaceTextofTextField(WebDriver driver, By by,
			WebElement elem, Keys key ,long timeoutInSec) {
		handleInterrupt();
		WebElement element = null;
		Exception ex = null;
		int counter=0;
		long maxTime = timeoutInSec * 1000;
		long timeSlice = 1000L;
		long elapsedTime = 0L;
		boolean result = false;
		do
			try {
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;
				if (elem == null) {
					element = locateElement(driver, by, 2L);
				} else {
					element = elem;
				}
				element.sendKeys(key);
				CyborgConstants.logger.info(element.toString()+"-"+key+" has been entered successfully.");
				result = true;
			} catch (NoSuchElementException e) {
				if(counter==0){
					CyborgConstants.logger.fatal("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					counter=1;
				}

				ex = e;
			} catch (StaleElementReferenceException e) {
				if(counter==0){
					CyborgConstants.logger.fatal("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					counter=1;
				}
				ex = e;
			} catch (TimeoutException e) {
				if(counter==0){
					CyborgConstants.logger.fatal("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					counter=1;
				}
				ex = e;
			} catch (ElementNotVisibleException e) {
				if(counter==0){
					CyborgConstants.logger.fatal("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					counter=1;
				}
				ex = e;
			} catch (WebDriverException e) {
				if (elapsedTime >= 2000L)
					throw e;
			} catch (Exception e) {
				if(counter==0){
					CyborgConstants.logger.fatal("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					counter=1;
				}
				ex = e;
			}
			while ((!(result)) && (elapsedTime < maxTime));
			if (elapsedTime >= maxTime) {
				CyborgConstants.logger.fatal(ex);
				throw new TimeoutException(ex);
			}

	}

	/**
	 * This method will clean the text field and write the text value passed as
	 * parameter text.
	 * 
	 * @param driver
	 *            Instance of webDriver.
	 * @param by
	 *            The locating mechanism.
	 * @param elem
	 *            Should pass webElement or null if By is used and vice versa.
	 * @param text
	 *            String Text to be entered in a webElement.
	 * @param timeoutInSec
	 *            Synchronization timeouts.
	 *            @author priyaranjanm
	 */
	public static void replaceTextofTextField(WebDriver driver, By by,
			WebElement elem, String text, long timeoutInSec) {
		handleInterrupt();
		WebElement element = null;
		Exception ex = null;
		int counter=0;
		long maxTime = timeoutInSec * 1000;
		long timeSlice = 1000L;
		long elapsedTime = 0L;
		boolean result = false;
		do
			try {
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;
				if (elem == null) {
					element = locateElement(driver, by, 2L);
				} else {
					element = elem;
				}
				highlightElement(element, driver);
				element.clear();
				element.sendKeys(new CharSequence[] { text });
				result = true;
				CyborgConstants.logger.info(element.toString()+"-"+text+" has been entered successfully.");
			} catch (NoSuchElementException e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					counter=1;
					CyborgConstants.logger.error("element with identifier " + by
							+ " not found retrying...");
				}
				ex = e;
				CyborgConstants.logger.error(ex);
			} catch (StaleElementReferenceException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					counter=1;
					CyborgConstants.logger.error("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
				}
				ex = e;
				CyborgConstants.logger.error(ex);
			} catch (TimeoutException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					counter=1;
					CyborgConstants.logger.error("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
				}
				ex = e;
				CyborgConstants.logger.error(ex);
			} catch (ElementNotVisibleException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					counter=1;
					CyborgConstants.logger.error("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
				}
				ex = e;
				CyborgConstants.logger.error(ex);
			} catch (WebDriverException e) {
				if (elapsedTime >= 2000L)
					throw e;
				CyborgConstants.logger.error(e);
			} catch (Exception e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					counter=1;
					CyborgConstants.logger.error("element with identifier " + by
							+ " not found retrying...");
				}
				ex = e;
				CyborgConstants.logger.error(ex);
			}
			while ((!(result)) && (elapsedTime < maxTime));
			if (elapsedTime >= maxTime) {
				CyborgConstants.logger.error(ex);
				throw new TimeoutException(ex);
			}

	}
	
	
	
	/**
	 * This method will write the text value without cleaning the text field 
	 * 
	 * @param driver
	 *            Instance of webDriver.
	 * @param by
	 *            The locating mechanism.
	 * @param elem
	 *            Should pass webElement or null if By is used and vice versa.
	 * @param text
	 *            String Text to be entered in a webElement.
	 * @param timeoutInSec
	 *            Synchronization timeouts.
	 *            @author priyaranjanm
	 */
	public static void replaceTextofTextFieldnoclear(WebDriver driver, By by,
			WebElement elem, String text, long timeoutInSec) {
		handleInterrupt();
		WebElement element = null;
		Exception ex = null;
		int counter=0;
		long maxTime = timeoutInSec * 1000;
		long timeSlice = 1000L;
		long elapsedTime = 0L;
		boolean result = false;
		do
			try {
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;
				if (elem == null) {
					element = locateElement(driver, by, 2L);
				} else {
					element = elem;
				}
				highlightElement(element, driver);
				
				element.sendKeys(new CharSequence[] { text });
				result = true;
				CyborgConstants.logger.info(element.toString()+"-"+text+" has been entered successfully.");
			} catch (NoSuchElementException e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					counter=1;
					CyborgConstants.logger.error("element with identifier " + by
							+ " not found retrying...");
				}
				ex = e;
				CyborgConstants.logger.error(ex);
			} catch (StaleElementReferenceException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					counter=1;
					CyborgConstants.logger.error("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
				}
				ex = e;
				CyborgConstants.logger.error(ex);
			} catch (TimeoutException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					counter=1;
					CyborgConstants.logger.error("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
				}
				ex = e;
				CyborgConstants.logger.error(ex);
			} catch (ElementNotVisibleException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					counter=1;
					CyborgConstants.logger.error("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
				}
				ex = e;
				CyborgConstants.logger.error(ex);
			} catch (WebDriverException e) {
				if (elapsedTime >= 2000L)
					throw e;
				CyborgConstants.logger.error(e);
			} catch (Exception e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					counter=1;
					CyborgConstants.logger.error("element with identifier " + by
							+ " not found retrying...");
				}
				ex = e;
				CyborgConstants.logger.error(ex);
			}
			while ((!(result)) && (elapsedTime < maxTime));
			if (elapsedTime >= maxTime) {
				CyborgConstants.logger.error(ex);
				throw new TimeoutException(ex);
			}

	}

	/**
	 * This method will pause execution for give time in milliseconds
	 * 
	 * @param timeInMilli
	 *            Pause time in milliseconds.
	 *            @author priyaranjanm
	 */
	public static void pauseExecution(int timeInMilli) {
		try {
			//timeInMilli=timeInMilli*1000;
			Thread.sleep(timeInMilli);
			//			CyborgConstants.logger.info("Execution was paused for "+timeInMilli+".");
		} catch (InterruptedException e) {
			e.printStackTrace();
			CyborgConstants.logger.error(e);
		}
		handleInterrupt();

	}

	/**
	 * LocateElement method will find the element in the web page and return a
	 * webElement.
	 * 
	 * @param driver
	 *            Instance of webDriver.
	 * @param by
	 *            The locating mechanism
	 * @param timeoutInSec
	 *            Synchronization timeouts.
	 * @return WebElement
	 * @author priyaranjanm
	 */
	public static WebElement locateElement(WebDriver driver, By by,
			long timeoutInSec) {
		handleInterrupt();
		Exception ex = null;
		WebElement result = null;
		int counter=0;
		long maxTime = timeoutInSec * 1000L;
		long timeSlice = 250L;
		long elapsedTime = 0L;
		do
			try {
				//explicitWait(driver, by, timeoutInSec);
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;
				result = driver.findElement(by);
				highlightElement(result, driver);
			} catch (NoSuchElementException e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
			} catch (WebDriverException e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				if (elapsedTime >= 500L)
					throw e;
			} catch (Exception e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
			}
			while ((result == null) && (elapsedTime < maxTime));
			if (elapsedTime >= maxTime) {
				CyborgConstants.logger.fatal(ex);
				throw new TimeoutException(ex);
			}

			return result;
	}

	/**
	 * This method will wait till the expected condition is met
	 * 
	 * @param driver
	 *            Instance of webDriver.
	 * @param by
	 *            The locating mechanism
	 * @param timeoutInSec
	 *            Synchronization timeouts.
	 * @author Sandeep
	 */
	public static void explicitWait(WebDriver driver, By element, long timeOutInSec)
	{
		WebDriverWait wait_element = new WebDriverWait(driver, timeOutInSec);
		wait_element.until(ExpectedConditions.visibilityOfElementLocated(element));
	}     


	/**
	 * Find all elements within the current context using the given mechanism.
	 * 
	 * @param driver
	 *            Instance of webDriver.
	 * @param by
	 *            The locating mechanism
	 * @param timeoutInSec
	 *            Synchronization timeouts.
	 * @return list of WebElement
	 * @author priyaranjanm
	 */
	public static List<WebElement> locateElements(WebDriver driver, By by,
			long timeoutInSec) {
		handleInterrupt();
		Exception ex = null;
		List<WebElement> result = null;
		int counter=0;
		long maxTime = timeoutInSec * 1000L;
		long timeSlice = 250L;
		long elapsedTime = 0L;
		do
			try {
				//				explicitWait(driver, by, timeoutInSec);
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;

				result = driver.findElements(by);
			} catch (NoSuchElementException e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
			} catch (WebDriverException e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				if (elapsedTime >= 500L)
					throw e;
			} catch (Exception e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
			}
			while ((result == null) && (elapsedTime < maxTime));
			if (elapsedTime >= maxTime) {
				CyborgConstants.logger.fatal(ex);
				throw new TimeoutException(ex);
			}

			return result;
	}

	/**
	 * This method will highLight the webElement .
	 * 
	 * @param element
	 *            webElement which need to be highlighted.
	 * @param driver
	 *            Instance of webDriver.
	 * @throws InterruptedException
	 * @author priyaranjanm
	 */
	public static void highlightElement(WebElement element, SearchContext driver)
	throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.backgroundColor='#00ff00'",
				element);
		js.executeScript("arguments[0].style.backgroundColor=''", element);
	}

	/**
	 * This method will read properties files value based on the key passed as
	 * an argument
	 * 
	 * @param key
	 *            key name in the properties file.
	 * @param relpath
	 *            Need to pass the relative path of the properties file.
	 * @return It will return value of the key as string
	 * @author priyaranjanm
	 */
	public static String getPropertyValue(String key, String relpath) {
		Properties pr = new Properties();
		try {
			pr.load(new FileInputStream(relpath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			CyborgConstants.logger.fatal(e);
		} catch (IOException e) {
			e.printStackTrace();
			CyborgConstants.logger.fatal(e);
		}
		String value = pr.getProperty(key);
		return value;
	}

	/**
	 * This method will write value into the properties file based on key passed
	 * as an argument
	 * 
	 * @param key
	 *            key name in the properties file.
	 * @param value
	 *            Need to pass the value to be written for the key.
	 * @param relpath
	 *            Need to pass the relative path of the properties file.
	 *            @author priyaranjanm
	 */
	public static void setPropertyValue(String key, String value, String relpath) {
		Properties pr = new Properties();
		try {
			pr.load(new FileInputStream(relpath));

			pr.setProperty(key, value);
			File file = new File(relpath);
			pr.store(new FileOutputStream(file), "");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			CyborgConstants.logger.error(e);
		} catch (IOException e) {

			e.printStackTrace();
			CyborgConstants.logger.error(e);
		}
	}

	/**
	 * This method will perform keyboard actions on the given webElement.
	 * 
	 * @param driver
	 *            Instance of webDriver.
	 * @param by
	 *            The locating mechanism
	 * @param elem
	 *            Should pass webElement or null if By is used and vice versa.
	 * @param key
	 *            Here we need to pass the Keyboard keys .
	 * @param timeoutInSec
	 *            Synchronization timeouts.
	 *            @author priyaranjanm
	 */
	public static void type(WebDriver driver, By by, WebElement elem, Keys key,
			int timeoutInSec) {
		handleInterrupt();
		WebElement element = null;
		Exception ex = null;
		int counter=0;
		long maxTime = timeoutInSec * 1000;
		long timeSlice = 1000L;
		long elapsedTime = 0L;
		boolean result = false;
		do
			try {
				if (elem == null) {
					element = locateElement(driver, by, 2L);
				} else {
					element = elem;
				}
				element.sendKeys(new CharSequence[] { key });
				result = true;
			} catch (NoSuchElementException e) {
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					counter=1;
				}
				ex = e;
			} catch (StaleElementReferenceException e) {
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					counter=1;
				}
				ex = e;
			} catch (TimeoutException e) {
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					counter=1;
				}
				ex = e;
			} catch (ElementNotVisibleException e) {
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					counter=1;
				}
				ex = e;
			} catch (WebDriverException e) {
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;
				if (elapsedTime >= 2000L)
					throw e;
			} catch (Exception e) {
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					counter=1;
				}
				ex = e;
			}
			while ((!(result)) && (elapsedTime < maxTime));
			if (elapsedTime >= maxTime)
				throw new TimeoutException(ex);
	}

	public static void handleInterrupt() {
		BufferedReader io = null;

		File file = null;
		BufferedWriter writer = null;
		boolean exist = false;
		while (true)
			try {
				file = new File("control.properties");
				exist = file.createNewFile();
				if (exist) {
					file.deleteOnExit();
					writer = new BufferedWriter(new FileWriter(file), 100);
					curState = "resume";
					writer.write("resume");
					writer.flush();
					writer.close();
					/*System.out.println("resume command sent");*/
					return;
				}
				io = new BufferedReader(new FileReader(file), 100);
				String intStatus = io.readLine();
				if ((curState == null) || (intStatus == null)
						|| (intStatus.equals(""))) {
					writer = new BufferedWriter(new FileWriter(file), 100);
					writer.write("resume");
					writer.flush();
					writer.close();
					curState = "resume";
					/*System.out.println("resume command sent");*/
					return;
				}
				if (intStatus.equals("pause")) {
					if (!(curState.equals("pause"))) {
						curState = "pause";
						/*System.out
						.println("Test execution paused successfully");*/
					}
					pauseExecution(1000);
				} else {
					if (intStatus.equals("stop"))
						throw new IllegalStateException("Stopped by User");
					if (intStatus.equals("resume")) {
						if (!(curState.equals("resume"))) {
							curState = "resume";
							/*System.out
							.println("Test execution resumed successfully");*/
						}
						return;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				CyborgConstants.logger.error(e);
			} catch (IOException e) {
				e.printStackTrace();
				CyborgConstants.logger.error(e);
			}
	}

	/**
	 * This method will start recording the screenshot's for scenario's.
	 * @author priyaranjanm
	 */
	public static void startRecording() {
		File file = new File(reportStructurePath + "\\RecordingFiles\\");
		GraphicsConfiguration gc = GraphicsEnvironment
		.getLocalGraphicsEnvironment().getDefaultScreenDevice()
		.getDefaultConfiguration();
		try {
			screenRecorder = new ScreenRecorder(gc, null, new Format(
					MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,
							ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
							CompressorNameKey,
							ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey,
							24, FrameRateKey, Rational.valueOf(15), QualityKey,
							1.0f, KeyFrameIntervalKey, 15 * 60), new Format(
									MediaTypeKey, MediaType.VIDEO, EncodingKey,
									"black", FrameRateKey, Rational.valueOf(30)), null,
									file);
		} catch (IOException e1) {
			e1.printStackTrace();
			CyborgConstants.logger.error(e1);
		} catch (AWTException e1) {
			e1.printStackTrace();
			CyborgConstants.logger.error(e1);
		}

		try {
			screenRecorder.start();
		} catch (IOException e1) {
			e1.printStackTrace();
			CyborgConstants.logger.error(e1);
		}
	}

	/**
	 * This Method will stop the recording process.
	 * @author priyaranjanm
	 */
	public static void stopRecording() {
		try {
			screenRecorder.stop();
		} catch (Exception e) {
			e.printStackTrace();
			CyborgConstants.logger.error(e);
		}
	}

	/**
	 * This method will click on particular webElement.
	 * 
	 * @param driver
	 *            Instance of webDriver.
	 * @param by
	 *            The locating mechanism
	 * @param elem
	 *            Should pass webElement or null if By is used and vice versa.
	 * @param timeOutInSec
	 *            Synchronization timeouts.
	 * @return boolean value true or false.
	 * @author priyaranjanm
	 */
	public static boolean clickElement(WebDriver driver, By by,
			WebElement elem, long timeOutInSec) {
		//System.out.println("element value:>>>>>>>" + elem);

		Exception ex = null;
		WebElement element = null;
		int counter=0;
		long maxTime = timeOutInSec * 1000L;
		long timeSlice = 250L;
		long elapsedTime = 0L;
		boolean result = false;
		do
			try {
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;
				if (elem == null) {
					element = driver.findElement(by);
				} else {
					element = elem;
				}
				highlightElement(element, driver);
				element.click();
				CyborgConstants.logger.info(element.toString()+"-Element has been clicked successfully.");
				result = true;
			} catch (NoSuchElementException e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
				CyborgConstants.logger.error(ex);
			} catch (StaleElementReferenceException e) {
				if(counter==0){
					System.out.println("element with identifier " + element
							+ " is changed due to javascript retrying...");
					CyborgConstants.logger.fatal("element with identifier " + element
							+ " is changed due to javascript retrying.");
					counter=1;
				}
				ex = e;
				CyborgConstants.logger.fatal(ex);
			} catch (WebDriverException e) {
				if(counter==0){
					System.out.println("element with identifier " + element
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + element
							+ " not found retrying.");
					counter=1;
				}
				ex = e;

			} catch (Exception e) {
				if(counter==0){
					System.out.println("element with identifier " + element
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + element
							+ " not found retrying.");
					counter=1;
				}
				ex = e;

			}
			while ((!(result)) && (elapsedTime < maxTime));
			if (elapsedTime >= maxTime) {
				CyborgConstants.logger.fatal(ex);
				throw new TimeoutException(ex);
			}
			return result;
	}

	/**
	 * This method will perform mouse move action on a webElement.
	 * 
	 * @param driver
	 *            Instance of webDriver.
	 * @param element
	 *            webElement where you need to move the mouse cursor.
	 *            @author priyaranjanm
	 */
	public static void mouseMove(WebDriver driver, WebElement element) {
		Actions act = new Actions(driver);
		act.moveToElement(element).build().perform();
		pauseExecution(1000);
	}

	/**
	 * This method is used to wait for a specific time till element present and
	 * visible.
	 * 
	 * @param driver
	 *            Instance of webDriver.
	 * @param by
	 *            The locating mechanism.
	 * @param timeoutInSec
	 *            Synchronization timeouts.
	 * @return webElement.
	 * @author priyaranjanm
	 */
	public static WebElement waitForElementPresentNVisible(WebDriver driver,
			By by, long timeoutInSec) {
		handleInterrupt();

		Exception ex = null;
		WebElement result = null;
		int counter=0;
		long maxTime = timeoutInSec * 1000L;
		long timeSlice = 250L;
		long elapsedTime = 0L;
		do
			try {
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;
				result = driver.findElement(by);

			} catch (NoSuchElementException e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
			} catch (WebDriverException e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying.");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				if (elapsedTime >= 500L)
					throw e;
			} catch (Exception e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying.");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
			}
			while ((result == null) && (elapsedTime < maxTime));
			if (elapsedTime >= maxTime) {
				CyborgConstants.logger.fatal(ex);
				throw new TimeoutException(ex);
			}
			return result;
	}

	/**
	 * This method is used to wait until an element is Located.
	 * 
	 * @param driver
	 *            Instance of webDriver.
	 * @param by
	 *            The locating mechanism.
	 * @param timeoutInSec
	 *            Synchronization timeouts.
	 *            @author priyaranjanm
	 */
	public static void waitForElementNotPresent(WebDriver driver, By by,
			long timeoutInSec) {
		handleInterrupt();
		for (int i = 1; i <= timeoutInSec; i++) {
			try {
				driver.findElement(by);
			} catch (Exception e) {
				CyborgConstants.logger.fatal(e);
				return;
			}
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
				CyborgConstants.logger.error(e);
			}
		}
		throw new TimeoutException();
	}

	/**
	 * This method will delete the file passed as parameter.
	 * 
	 * @param f
	 *            :File to be deleted.
	 * @throws IOException
	 * @author priyaranjanm
	 */
	public static void delete(File f) throws IOException {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				delete(c);
		}
		if (!f.delete())
			throw new FileNotFoundException("Failed to delete file: " + f);
	}

	/**
	 * This method will get the innerText of the webElement.
	 * 
	 * @param driver
	 *            Instance of webDriver.
	 * @param by
	 *            The locating mechanism.
	 * @param elem
	 *            Should pass webElement or null if By is used and vice versa.
	 * @param timeoutInSec
	 *            Synchronization timeouts.
	 * @return string text
	 * @author priyaranjanm
	 */
	public static String getInnerText(WebDriver driver, By by, WebElement elem,
			long timeoutInSec) {
		handleInterrupt();
		Exception ex = null;
		int counter=0;
		long maxTime = timeoutInSec * 1000L;
		long timeSlice = 1000L;
		long elapsedTime = 0L;
		String innerText = null;
		boolean result = false;
		do
			try {
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;
				if (elem == null) {
					innerText = locateElement(driver, by, 1L).getText();
				} else {
					innerText = elem.getText();
				}
				result = true;
			} catch (NoSuchElementException e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}

				ex = e;
			} catch (StaleElementReferenceException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
			} catch (TimeoutException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
			} catch (ElementNotVisibleException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
			} catch (WebDriverException e) {
				if (elapsedTime >= 2000L)
					CyborgConstants.logger.fatal(e);
				throw e;
			} catch (Exception e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
			}
			while ((!(result)) && (elapsedTime < maxTime));
			if (elapsedTime >= maxTime) {
				CyborgConstants.logger.fatal(ex);
				throw new TimeoutException(ex);
			}
			return innerText;
	}

	/**
	 * This method will select a value/option from dropDown.
	 * 
	 * @param driver
	 *            Instance of the webDriver.
	 * @param selectLocator
	 *            The locating mechanism.
	 * @param elem
	 *            Should pass webElement or null if By is used and vice versa.
	 * @param optionVisibleText
	 *            Visible text from the dropDown to be selected.
	 *            @author priyaranjanm
	 */
	public static void selectOptionFromDropDown(WebDriver driver,
			By selectLocator, WebElement elem, String optionVisibleText) {
		handleInterrupt();
		Exception ex = null;
		Select select = null;
		int counter=0;
		long maxTime = 20000L;
		long timeSlice = 250L;
		long elapsedTime = 0L;
		boolean result = false;
		do
			try {
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;
				if (elem == null) {
					select = new Select(waitForElementPresentNVisible(driver,
							selectLocator, 2L));
				} else {
					select = new Select(elem);
				}
				select.selectByVisibleText(optionVisibleText);
				CyborgConstants.logger.info(elem.toString()+"-"+optionVisibleText+" has been selected successfully.");
				result = true;
			} catch (NoSuchElementException e) {
				if(counter==0){
					System.out.println("element with identifier " + selectLocator
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + selectLocator
							+ " not found retrying.");
					counter=1;
				}
				CyborgConstants.logger.fatal(ex);
				ex = e;
			} catch (StaleElementReferenceException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ selectLocator
							+ " is changed due to javascript retrying...");
					CyborgConstants.logger.fatal("element with identifier " + selectLocator
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
				CyborgConstants.logger.fatal(ex);
			} catch (ElementNotVisibleException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ selectLocator
							+ " is changed due to javascript retrying...");
					CyborgConstants.logger.fatal("element with identifier " + selectLocator
							+ " not found retrying.");
					counter=1;
				}

				ex = e;
				CyborgConstants.logger.fatal(ex);
			} catch (WebDriverException e) {
				if(counter==0){
					System.out.println("element with identifier " + selectLocator
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + selectLocator
							+ " not found retrying.");
					counter=1;
				}
				if (elapsedTime >= 1000L)
					CyborgConstants.logger.fatal(optionVisibleText+" has been selected successfully.");
				throw e;
			} catch (Exception e) {
				if(counter==0){
					System.out.println("element with identifier " + selectLocator
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + selectLocator
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
				CyborgConstants.logger.fatal(ex);
			}
			while ((!(result)) && (elapsedTime < maxTime));
			if (elapsedTime >= maxTime) {
				CyborgConstants.logger.fatal(ex);
				throw new TimeoutException(ex);
			}
			handleInterrupt();
	}

	/**
	 * This method will read text from the textBox.
	 * 
	 * @param driver
	 *            Instance of the webDriver.
	 * @param elem
	 *            Should pass webElement or null if By is used and vice versa.
	 * @param by
	 *            The locating mechanism
	 * @return String Text
	 * @author priyaranjanm
	 * @modified Pavan Bikumandla
	 */
	public static String readValue(WebDriver driver, WebElement elem, By by) {

		WebElement element = null;

		if (elem == null) {

			element = locateElement(driver, by, 2);
		} else {
			element = elem;
		}

		String text = element.getAttribute("value");
		if (text != null) {
			text = text.trim();
		}
		/*Pavan Bikumandla Below else statement added if do not have getAttribute value it will fetch from getText*/
		else{
			text=element.getText();
			text = text.trim();
		}
		handleInterrupt();
		return text;

	}





	/**
	 * This method is to perform assertion for element available in the application with the testdata.
	 * @param driver: Instance of WebDriver.
	 * @param by: The locating mechanism.
	 * @param elem : Should pass webElement or null if By is used and vice versa.
	 * @param expectedText: Expected text in the alert box.
	 * @param ElementID: element name in the application.
	 * @param desc: purpose of the assertion.
	 * @param expected: expected value.
	 * @param ScenarioDetailsHashMap:Hashmap variable contains Scenario details.
	 * @return: It return true if assert is pass and false if assert is fail
	 * @Modified By: Sandeep Jain
	 * @Modified Description: Added Hashmap variable to serve parallel testing purpose.
	 */
	public static boolean assertValue(WebDriver driver, By by, WebElement elem,
			String expectedText, String ElementID,String desc,
			HashMap<String, String> ScenarioDetailsHashMap) 

	{
		try{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(CyborgConstants.TIME_FORMAT);
			String startTime = sdf.format(cal.getTime());
			WebElement element = null;
			handleInterrupt();
			if (elem == null) {
				element = locateElement(driver, by, 5);
			} else {
				element = elem;
			}
			String originalText = readValue(driver, element, by);
			//System.out.println("originalText:::::"+originalText);
			if (originalText != null) {
				originalText = originalText.trim();
				originalText = originalText.replaceAll("\n", " ");
			}
			if (expectedText != null) {
				expectedText = expectedText.trim();
				expectedText = expectedText.replaceAll("\n", " ");
			}
			String resultValue=null;
			String elemId = element.toString();
			elemId = elemId.replaceAll("]", "");
			String[] elemIdsplit = elemId.split("->");

			Calendar cal1 = Calendar.getInstance();
			String endtime =null;
			String time = null;
			if ((originalText.equals(expectedText))) {
				resultValue = "Pass";
				CyborgConstants.logger.info("Actual and expected values "+"( "+originalText+" , "+expectedText+" )are same.");
			}
			else
			{
				resultValue = "Fail";
				CyborgConstants.logger.warn("Actual and expected values "+"( "+originalText+" , "+expectedText+" )are different.");
			}

			endtime = sdf.format(cal1.getTime());
			time = Time(startTime, endtime);

			HashMap<String, String> resultDetailsHashMap = new HashMap<String, String>();
			resultDetailsHashMap.put("ScenarioName", ScenarioDetailsHashMap.get("ScenarioName"));
			resultDetailsHashMap.put("SubScenarioNo", ScenarioDetailsHashMap.get("SubScenarioNo"));
			resultDetailsHashMap.put("DataSetNo", ScenarioDetailsHashMap.get("DataSetNo"));
			resultDetailsHashMap.put("FunctionName", ScenarioDetailsHashMap.get("FunctionName"));
			resultDetailsHashMap.put("ClassName", ScenarioDetailsHashMap.get("ClassName"));
			resultDetailsHashMap.put("ElementId", ElementID);
			resultDetailsHashMap.put("Description", desc);
			resultDetailsHashMap.put("ExpectedValue", expectedText);
			resultDetailsHashMap.put("ActualValue", originalText);
			resultDetailsHashMap.put("Status", resultValue);
			resultDetailsHashMap.put("Time", time);
			AssertionResults.setAssertionResult(driver,resultDetailsHashMap,element,by,ScenarioDetailsHashMap.get("BrowserType"));
			resultDetailsHashMap.clear();
		}
		catch (Exception ex)
		{
			ex.getStackTrace();
			CyborgConstants.logger.error(ex);
		}
		return true;
	}

	/**
	 * This method will identify the iframe and it will switch to that iframe.
	 * 
	 * @param driver
	 *            Instance of the webDriver.
	 * @param frame
	 *            The locating mechanism.
	 * @param elem
	 *            Should pass webElement or null if By is used and vice versa.
	 * @param timeoutInSec
	 *            Synchronization timeouts.
	 * @param expected
	 *            is always false.
	 * @return
	 * @author priyaranjanm
	 */
	public static WebDriver selectFrame(WebDriver driver, By frame, WebElement elem,
			long timeoutInSec) {
		handleInterrupt();
		pauseExecution(1000);
		Exception ex = null;
		WebDriver result = null;
		long maxTime = timeoutInSec * 1000L;
		long timeSlice = 250L;
		long elapsedTime = 0L;
		WebElement element=null;
		try{
			if(frame == null)
			{
				//System.out.println("in if condition");
				element=elem;
			}else{
				//System.out.println("in elese condition");
				element=locateElement(driver, frame, timeoutInSec);
			}

			result = driver.switchTo().frame(element);
		}catch (Exception e) {
			System.out.println("Frame Not Found "+e.getMessage());
			CyborgConstants.logger.fatal("Frame Not Found "+e.getMessage());
		}
		return result;
	}

	/**
	 * This method is used to select value from AJAX dropdown box.
	 * 
	 * @param driver
	 *            Instance of the webDriver.
	 * @param by
	 *            The locating mechanism.
	 * @param elem
	 *            Should pass webElement or null if By is used and vice versa.
	 * @param selectLocator
	 *            It will contain ajax dropBox's locator value
	 * @param item
	 *            Item need to be selected from the ajax dropBox.
	 * @param timeoutInSec
	 *            Synchronization timeouts.
	 *            @author priyaranjanm
	 */
	public static void selectFromAJAXDropBox(WebDriver driver, By by, WebElement elem,
			By selectLocator, String item, long timeoutInSec) {
		Select select = null;
		replaceTextofTextField(driver, by, elem, item, timeoutInSec);
		pauseExecution(1000);
		select = new Select(waitForElementPresentNVisible(driver,
				selectLocator, 2L));
		item = item + "ION";
		select.selectByValue(item);
	}
	/**
	 * This method is used to select value from AJAX.
	 * 
	 * @param driver
	 *            Instance of the webDriver.
	 * @param by
	 *            The locating mechanism.
	 * @param elem
	 *            Should pass webElement or null if By is used and vice versa.
	 * @param selectLocator
	 *            It will contain ajax dropBox's locator value
	 * @param item
	 *            Item need to be selected from the ajax Menu.
	 * @param timeoutInSec
	 *            Synchronization timeouts.
	 *            @author priyaranjanm
	 */
	public static void selectFromAJAX(WebDriver driver, By by, WebElement elem,
			String item, ObjectRepository or,String ajaxElement,long timeoutInSec,HashMap<String, String> ScenarioDetailsHashMap) {
		replaceTextofTextField(driver, by, elem, item, timeoutInSec);
		pauseExecution(1000);
		clickElement(driver, null, or.getElement(driver, ajaxElement, ScenarioDetailsHashMap, timeoutInSec), 2);

	}
	/**
	 * This method will give the alert text.
	 * 
	 * @param driver
	 *            Instance of the webDriver.
	 * @return String Text Alert message.
	 * @author priyaranjanm
	 */
	public static String getAlertText(WebDriver driver) {
		String alertText = null;
		Alert alert = driver.switchTo().alert();
		alertText = alert.getText();
		return alertText;
	}

	/**
	 * To wait until the element if visible to be clicked.
	 * 
	 * @param driver
	 *            Instance of the webDriver.
	 * @param by
	 *            The locating mechanism.
	 * @return boolean value based on the presence of element.
	 * @author priyaranjanm
	 */
	public static boolean isElementVisible(WebDriver driver, By by) {
		handleInterrupt();
		WebElement element = null;
		boolean flag = false;
		try {
			element = (WebElement) new WebDriverWait(driver, 2L)
			.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			flag = false;
			CyborgConstants.logger.error(e);	
		}
		if (element != null)
			flag = true;
		else {
			flag = false;
			CyborgConstants.logger.error("Element is not visible.");	
		}
		return flag;
	}

	/**
	 * This method will perform double click action in a particular webElement.
	 * 
	 * @param driver
	 *            Instance of the webDriver.
	 * @param by
	 *            The locating mechanism.
	 * @param elem
	 *            Should pass webElement or null if By is used and vice versa.
	 * @param timeOuts
	 *            Synchronization timeouts.
	 *            @author priyaranjanm
	 */
	public static String doubleClickOnElement(WebDriver driver, By by,
			WebElement elem, long timeOuts, boolean expected) {
		handleInterrupt();
		WebElement element = null;
		int counter=0;
		long maxTime = timeOuts * 1000L;
		long timeSlice = 250L;
		long elapsedTime = 0L;
		Exception ex = null;
		try {
			pauseExecution((int) timeSlice);
			elapsedTime += timeSlice;
			if (elem == null) {
				element = locateElement(driver, by, timeOuts);
			} else {
				element = elem;
			}

			Actions action = new Actions(driver);
			action.doubleClick(element).perform();
			CyborgConstants.logger.info("Double click has been done successfully.");
		} catch (StaleElementReferenceException e) {
			if(counter==0){
				System.out.println("element with identifier " + by
						+ " not found retrying...");
				CyborgConstants.logger.fatal("element with identifier " + by
						+ " not found retrying.");
				counter=1;
			}
			ex = e;
		} catch (NoSuchElementException e) {
			if(counter==0){
				System.out.println("element with identifier " + by
						+ " not found retrying...");
				CyborgConstants.logger.fatal("element with identifier " + by
						+ " not found retrying.");
				counter=1;
			}
		}

		if ((elapsedTime < maxTime) || (expected))
			return "";
		CyborgConstants.logger.fatal(ex);
		throw new TimeoutException(ex);
	}


	/**
	 * This method is to perform innertext assert. 
	 * @param driver: Instance of WebDriver.
	 * @param by: The locating mechanism.
	 * @param elem : Should pass webElement or null if By is used and vice versa.
	 * @param expectedText: Expected text in the alert box.
	 * @param ElementID: element name in the application.
	 * @param desc: purpose of the assertion.
	 * @param ScenarioDetailsHashMap: Hashmap variable contains Scenario details.
	 * @return It return true if assert is pass and false if assert is fail
	 * @Modified By: Sandeep Jain
	 * @Modified Description: Added Hashmap variable to serve parallel testing purpose.
	 */
	public static String assertInnerText(WebDriver driver, By by,
			WebElement elem, String expectedText,String FieldName, String desc,
			HashMap<String, String> ScenarioDetailsHashMap) {

		Calendar cal_StartTime = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(CyborgConstants.TIME_FORMAT);
		String startTime = sdf.format(cal_StartTime.getTime());
		String resultValue=null;
		String innertext = getInnerText(driver, by, elem, 15L).trim();

		/*System.out.println("result of assert inner::"
				+ " original value of text::\"" + innertext
				+ "\" and Expected value is::\"" + expectedText + "\"");
		 */
		if (innertext.equals(expectedText)) {
			resultValue = "Pass";
			CyborgConstants.logger.info("Actual and expected values "+"( "+innertext+" , "+expectedText+" )are same.");	
		}else{
			resultValue = "Fail";
			CyborgConstants.logger.warn("Actual and expected values "+"( "+innertext+" , "+expectedText+" )are different.");
		}
		String endtime =null;
		String time = null;
		Calendar cal_EndTime = Calendar.getInstance();
		endtime = sdf.format(cal_EndTime.getTime());
		time = Time(startTime, endtime);

		HashMap<String, String> resultDetailsHashMap = new HashMap<String, String>();
		resultDetailsHashMap.put("ScenarioName", ScenarioDetailsHashMap.get("ScenarioName"));
		resultDetailsHashMap.put("SubScenarioNo", ScenarioDetailsHashMap.get("SubScenarioNo"));
		resultDetailsHashMap.put("DataSetNo", ScenarioDetailsHashMap.get("DataSetNo"));
		resultDetailsHashMap.put("FunctionName", ScenarioDetailsHashMap.get("FunctionName"));
		resultDetailsHashMap.put("ClassName", ScenarioDetailsHashMap.get("ClassName"));
		resultDetailsHashMap.put("ElementId", FieldName);
		resultDetailsHashMap.put("Description", desc);
		resultDetailsHashMap.put("ExpectedValue", expectedText);
		resultDetailsHashMap.put("ActualValue", innertext);
		resultDetailsHashMap.put("Status", resultValue);
		resultDetailsHashMap.put("Time", time);
		AssertionResults.setAssertionResult(driver,resultDetailsHashMap,elem,by,ScenarioDetailsHashMap.get("BrowserType"));
		resultDetailsHashMap.clear();
		return resultValue;
	}

	/**
	 * SetvalueDriverSheet method will set the functions execution result to the
	 * driver sheet for report generation.
	 * 
	 * @param scenarioName
	 *            Contains Scenario Name.
	 * @param subScenarioNo
	 *            Contains SubScenario No.
	 * @param functionName
	 *            Contains Function name.
	 * @param dataSetNo
	 *            Contains DataSet value for the same function.
	 * @param classFunctionName
	 *            Contains class name of the function in which it is defined.
	 * @param Status
	 *            Contains the Functions execution status.
	 *            @author priyaranjanm
	 *            @Modified By Sandeep L
	 */
	public static void setValueDriverSheet(String scenarioName,
			String subScenarioNo, String functionName, String dataSetNo,
			String classFunctionName, String Status, String functionExecutedTime) {
		try {
			String functionStatus = Status;
			String singleWorkBookPath = reportStructurePath + "\\"
			+ res.getExectionDateTime() + "\\" + scenarioName + ".xlsx";
			System.out.println("path::" + singleWorkBookPath);

			Connection con_select = DriverManager
			.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="
					+ singleWorkBookPath
					+ ";"
					+ "DriverID=22;readOnly=true");
			Statement functionNameSTMT = con_select.createStatement();
			ResultSet funcName = functionNameSTMT
			.executeQuery("Select Slno from [Driver$] where Status='Skipped' " +
					"and "+CyborgConstants.DRIVER_BOOK_TESTCASE_NAME+"='"+ classFunctionName + "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+" = '"+subScenarioNo+"'");

			Connection con_insert = DriverManager
			.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="
					+ singleWorkBookPath
					+ ";"
					+ "DriverID=22;readOnly=true");

			Connection conAssertionStatus = null;
			String RStatus = Status;
			if (RStatus.equalsIgnoreCase("Pass")) {
				conAssertionStatus = DriverManager
				.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="
						+ singleWorkBookPath
						+ ";"
						+ "DriverID=22;readOnly=true");
				Statement assertionStatusSTMT = conAssertionStatus
				.createStatement();
				ResultSet assertRS = assertionStatusSTMT
				.executeQuery("Select Status from [AssertionReports$] where "+CyborgConstants.DRIVER_BOOK_TESTCASE_NAME+"='"
						+ functionName
						+ "' and "+CyborgConstants.DRIVER_BOOK_SCENARIO_NAME+"='"
						+ subScenarioNo + "' and  Status='Fail'");
				int statusCount = 0;
				while (assertRS.next()) {
					statusCount = statusCount + 1;
				}
				//System.out.println("sdtat????????????" + statusCount);
				if (statusCount > 0) {
					RStatus = "Fail";
				}
			}
			//System.out.println("status value:::" + Status);

			List<Integer> functionNameList = new ArrayList<Integer>();
			while (funcName.next()) {
				int slNo = funcName.getInt("Slno");
				functionNameList.add(Integer.valueOf(slNo));
			}
			con_insert.prepareStatement("update [Driver$] set FunctionStatus = '"+functionStatus+"' where Slno ='"+functionNameList.get(0)+"'").execute();
			con_insert.prepareStatement(
					"update [Driver$] set Status = '" + RStatus
					+ "' where Slno ='"
					+ functionNameList.get(0) + "'").execute();

			con_insert.prepareStatement(
					"update [Driver$] set [Time] = '" + functionExecutedTime
					+ "' where Slno ='"
					+ functionNameList.get(0) + "'").execute();

			functionNameList.clear();
			con_select.close();
			con_insert.close();

		} catch (Exception e) {
			CyborgConstants.logger.error(e);
			e.printStackTrace();
		}

		//System.out.println("done with saving in exel sheet=++++++++++_--------++++++");
	}

	/**
	 * This method will take two date and time as startDate and stopDate and
	 * returns the difference between the two.
	 * 
	 * @param dateStart
	 *            Start date and time
	 * @param dateStop
	 *            End date and time
	 * @return dateDifferances
	 *  @author priyaranjanm
	 */
	public static String Time(String dateStart, String dateStop) {
		String dateDiff = null;
		// HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat(CyborgConstants.TIME_FORMAT);
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);
			// in milliseconds
			long diff = d2.getTime() - d1.getTime();
			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);
			dateDiff = diffHours + ":" + diffMinutes + ":"
			+ diffSeconds;
		} catch (Exception e) {
			e.printStackTrace();
			CyborgConstants.logger.error(e);
		}
		return dateDiff;
	}

	/**
	 * This method is used to split dataSet no and returns list of dataSet
	 * values.
	 * 
	 * @param dataSet
	 *            DataSet No
	 * @return datasetvaluesList
	 *  @author priyaranjanm
	 */

	public static List<String> getDataSetNo(String dataSet) {
		String[] datasetoccurences = null;
		List<String> dataSetValues = new ArrayList<String>();
		if (dataSet.contains("-")) {
			datasetoccurences = dataSet.split("-");
			int datasetstartvalue = Integer.parseInt(datasetoccurences[0]);
			int datasetendvalue = Integer.parseInt(datasetoccurences[1]);

			for (int s = datasetstartvalue; s <= datasetendvalue; s++) {
				dataSetValues.add(s + "");
			}
		} else if (dataSet.contains(",")) {
			datasetoccurences = dataSet.split(",");
			for (int s = 0; s < datasetoccurences.length; s++) {
				dataSetValues.add(datasetoccurences[s]);
			}
		} else {
			dataSetValues.add(dataSet);
		}
		return dataSetValues;
	}

	/**
	 * @Description:Below method will return true if field is enabled in the
	 *                    screen else it will return false
	 * @Date: April 14th 2014
	 * @param driver
	 *            = WebDriver variable
	 * @param by
	 *            = locator details variable
	 * @param elem
	 *            = locator details variable for Object Repository
	 * @param timeOutInSec
	 *            = number of seconds driver should wait till field is
	 *            displayed.
	 * @return fieldStatus= returns result True if field is enabled else it will
	 *         return False.
	 *          @author Sandeep L
	 */
	public static boolean isFieldEnabled(WebDriver driver, By by,
			WebElement elem, long timeOutInSec) {
		handleInterrupt();
		boolean fieldStatus = true;
		if (elem == null) {
			fieldStatus = locateElement(driver, by, 1L).isEnabled();
		} else {
			fieldStatus = elem.isEnabled();
		}
		return fieldStatus;
	}

	/**
	 * @Description:Below method is to enter value in textbox by copy and paste
	 *                    functionality
	 * @Date: April 14th 2014
	 * @param driver
	 *            = WebDriver variable
	 * @param by
	 *            = locator details variable
	 * @param elem
	 *            = locator details variable for Object Repository
	 * @param text
	 *            = Actual value or Testdata to be entered in field.
	 * @param timeoutInSec
	 *            = number of seconds driver should wait till field is
	 *            displayed.
	 *  @author Sandeep L
	 */
	public static void copyPasteInTextField(WebDriver driver, By by,
			WebElement elem, String text, long timeoutInSec) {
		handleInterrupt();
		WebElement element = null;
		Exception exceptionMessage = null;
		int counter=0;
		long maxTime = timeoutInSec * 1000;
		long timeSlice = 1000L;
		long elapsedTime = 0L;
		boolean result = false;
		do
			try {
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;
				if (elem == null) {
					element = locateElement(driver, by, 2L);
				} else {
					element = elem;
				}
				element.clear();
				element.sendKeys(new CharSequence[] { text });
				element.sendKeys(Keys.chord(Keys.CONTROL, "a"),
						Keys.chord(Keys.CONTROL, "c"));
				element.clear();
				highlightElement(element, driver);
				element.sendKeys(Keys.chord(Keys.CONTROL, "v"));
				result = true;
			} catch (NoSuchElementException e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying");
					CyborgConstants.logger.error("cannot click on element with identifier "
							+ by + " as Stale Element Reference occured retrying.");
					counter=1;
				}
				exceptionMessage = e;
			} catch (StaleElementReferenceException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " as Stale Element Reference occured retrying");
					CyborgConstants.logger.error("cannot click on element with identifier "
							+ by + " as Stale Element Reference occured retrying.");
					counter=1;
				}
				exceptionMessage = e;
			} catch (TimeoutException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " as Time out exception occured retrying");
					CyborgConstants.logger.fatal("cannot click on element with identifier "
							+ by + " as Stale Element Reference occured retrying.");
					counter=1;
				}
				exceptionMessage = e;
			} catch (ElementNotVisibleException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " as element is not visible retrying");
					CyborgConstants.logger.fatal("cannot click on element with identifier "
							+ by + " as Stale Element Reference occured retrying.");
					counter=1;
				}
				exceptionMessage = e;
			} catch (WebDriverException e) {
				if (elapsedTime >= 2000L)
					CyborgConstants.logger.fatal(e);
				throw e;
			} catch (Exception e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				exceptionMessage = e;
			}
			while ((!(result)) && (elapsedTime < maxTime));
			if (elapsedTime >= maxTime) {
				CyborgConstants.logger.fatal(exceptionMessage);
				throw new TimeoutException(exceptionMessage);
			}

	}

	/**
	 * @Description:Below method is to perform boundary value validation for
	 *                    maximum characters inside text field only.
	 * @Date: April 17th 2014
	 * @param driver
	 *            = WebDriver variable
	 * @param by
	 *            = locator details variable
	 * @param elem
	 *            = locator details variable for Object Repository
	 * @param text
	 *            = Actual value or Testdata to be entered in field.
	 * @param timeoutInSec
	 *            = number of seconds driver should wait till field is
	 *            displayed.
	 * @return Returns result True or False after performing validation.
	 *  @author Sandeep L
	 */
	public static boolean boundaryValidationMaxInTextField(WebDriver driver,
			By by, WebElement elem, String text, long timeoutInSec) {
		handleInterrupt();
		WebElement element = null;
		Exception exceptionMessage = null;
		int counter=0;
		long maxTime = timeoutInSec * 1000;
		long timeSlice = 1000L;
		long elapsedTime = 0L;
		boolean result = false;
		String actualValue = null;

		do
			try {
				elapsedTime += timeSlice;
				if (elem == null) {
					element = locateElement(driver, by, 2L);
				} else {
					element = elem;
				}
				element.clear();
				element.sendKeys(new CharSequence[] { text });
				actualValue = element.getAttribute("value");
				int expectedCharacterCount = text.length();
				int actualCharacterCount = actualValue.length();
				System.out.println("expected text count:"
						+ expectedCharacterCount);
				System.out.println("actual text count:" + actualCharacterCount);
				if (actualCharacterCount < expectedCharacterCount) {
					result = true;
				} else {
					result = false;
				}

			} catch (NoSuchElementException e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying");
					counter=1;
				}
				exceptionMessage = e;
			} catch (StaleElementReferenceException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " as Stale Element Reference occured retrying");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying");
					counter=1;
				}
				exceptionMessage = e;
			} catch (TimeoutException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " as Time out exception occured retrying");
					CyborgConstants.logger.fatal("cannot click on element with identifier "
							+ by + " as Stale Element Reference occured retrying.");
					counter=1;
				}
				exceptionMessage = e;
			} catch (ElementNotVisibleException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " as element is not visible retrying");
					CyborgConstants.logger.fatal("cannot click on element with identifier "
							+ by + " as Stale Element Reference occured retrying.");
					counter=1;
				}
				exceptionMessage = e;
			} catch (WebDriverException e) {
				if (elapsedTime >= 2000L)
					throw e;
			} catch (Exception e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					CyborgConstants.logger.fatal("cannot click on element with identifier "
							+ by + " as Stale Element Reference occured retrying.");
					counter=1;
				}
				exceptionMessage = e;
			}
			while ((!(result)) && (elapsedTime < maxTime));
			if (elapsedTime >= maxTime) {
				CyborgConstants.logger.fatal(exceptionMessage);
				throw new TimeoutException(exceptionMessage);
			}
			return result;
	}

	/**
	 * @Description:Below method will return list which will contain all the
	 *                    values available in dropdown
	 * @Date: April 18th 2014
	 * @param driver
	 *            = WebDriver variable
	 * @param by
	 *            = locator details variable
	 * @param elem
	 *            = locator details variable for Object Repository
	 * @param timeOutInSec
	 *            = number of seconds driver should wait till field is
	 *            displayed.
	 * @return = It will return list of dropdown values.
	 *  @author Sandeep L
	 */
	public List<WebElement> getAllDropdownValues(WebDriver driver, By by,
			WebElement elem, long timeoutInSec) {
		handleInterrupt();
		Exception ex = null;
		WebElement elementSelect = null;
		int counter=0;
		long maxTime = 20000L;
		long timeSlice = 250L;
		long elapsedTime = 0L;
		boolean result = false;
		List<WebElement> listOfOptionsOfDropdown = null;
		do
			try {
				pauseExecution((int) timeSlice);
				elapsedTime += timeSlice;

				if (elem == null) {
					elementSelect = locateElement(driver, by, 2L);
				} else {
					elementSelect = elem;
				}

				listOfOptionsOfDropdown = elementSelect.findElements(By
						.tagName("option"));
				result = true;
			} catch (NoSuchElementException e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
			} catch (StaleElementReferenceException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
			} catch (ElementNotVisibleException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier "
							+ by + " is changed due to javascript retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
			} catch (WebDriverException e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				if (elapsedTime >= 1000L)
					throw e;
			} catch (Exception e) {
				if(counter==0){
					System.out.println("element with identifier " + by
							+ " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + by
							+ " not found retrying.");
					counter=1;
				}
				ex = e;
			}
			while ((!(result)) && (elapsedTime < maxTime));
			if (elapsedTime >= maxTime) {
				CyborgConstants.logger.fatal(ex);
				throw new TimeoutException(ex);
			}
			handleInterrupt();
			return listOfOptionsOfDropdown;
	}


	/**
	 * Below method will clear a text filed.
	 * @date April 22th 2014  
	 * @param driver Instance of webDriver.
	 * @param by The locating mechanism.
	 * @param elem Should pass webElement or null if By is used and vice versa.
	 * @param timeoutInSec Synchronization timeouts.
	 * @author Priyaranjan
	 */
	public static void clearText(WebDriver driver,By by,WebElement elem,long timeoutInSec)
	{
		handleInterrupt();
		WebElement element = null;
		Exception ex = null;
		int counter=0;
		long maxTime = timeoutInSec * 1000;
		long timeSlice = 1000L;
		long elapsedTime = 0L;
		boolean result = false;
		do
			try {
				pauseExecution((int)timeSlice);
				elapsedTime += timeSlice;
				if(elem == null)
				{
					element = locateElement(driver, by, 2L);
					//					highlightElement(element, driver);
				}else{
					element=elem;
				}
				element.clear();
				result = true;
			} catch (NoSuchElementException e) {
				if(counter==0){
					System.out.println("element with identifier " + element + " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + element + " not found retrying.");
					counter=1;
				}
				ex = e;
			} catch (StaleElementReferenceException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier " + element + " is changed due to javascript retrying...");
					CyborgConstants.logger.fatal("element with identifier " + element + " not found retrying.");
					counter=1;
				}
				ex = e;
			} catch (TimeoutException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier " + element + " is changed due to javascript retrying...");
					CyborgConstants.logger.fatal("element with identifier " + element + " not found retrying.");
					counter=1;
				}
				ex = e;
			} catch (ElementNotVisibleException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier " + element + " is changed due to javascript retrying...");
					CyborgConstants.logger.fatal("element with identifier " + element + " not found retrying.");
					counter=1;}
				ex = e;
			} catch (WebDriverException e) {
				if (elapsedTime >= 2000L)
					CyborgConstants.logger.fatal(e);
				throw e;
			}
			catch (Exception e) {
				if(counter==0){
					System.out.println("element with identifier " + element + " not found retrying...");
					CyborgConstants.logger.fatal("element with identifier " + element + " not found retrying...");
					counter=1;
				}
				ex = e;
			}
			while ((!(result)) && (elapsedTime < maxTime));
			if (elapsedTime >= maxTime) {
				CyborgConstants.logger.fatal(ex);
				throw new TimeoutException(ex);
			}

	}

	/**
	 * Below method will accept or dismiss alert based on passed actions.
	 * @date April 22th 2014  
	 * @param driver Instance of webDriver.
	 * @param action Need to pass accept/dismiss.
	 * @author Priyaranjan
	 */
	public static void handleAlert(WebDriver driver,String action)
	{
		try{

			if(action.equalsIgnoreCase("accept"))
			{
				driver.switchTo().alert().accept();
			}else{
				driver.switchTo().alert().dismiss();
			}
		}catch(Exception e){
			CyborgConstants.logger.error(e);
		}
	}

	/**
	 * Below Method will give current Date.
	 * @date April 22th 2014  
	 * @return String date.
	 * @author Priyaranjan
	 */
	public static String currentDate()
	{
		String dateFormate=getPropertyValue("dateFormate", System.getProperty("user.dir")+"\\Configurations\\base.properties");
		SimpleDateFormat simpDateFormat= new SimpleDateFormat(dateFormate);
		Date date= new Date();
		String curdate=simpDateFormat.format(date);
		return curdate;
	}

	/**
	 * Below Method will give current Time.
	 * @date April 22th 2014  
	 * @return String Time.
	 * @author Priyaranjan
	 */
	public static String currentTime()
	{
		String dateFormate=getPropertyValue("timeFormate", System.getProperty("user.dir")+"\\Configurations\\base.properties");
		SimpleDateFormat simpDateFormat= new SimpleDateFormat(dateFormate);
		Calendar cal=Calendar.getInstance();
		String curdate=simpDateFormat.format(cal.getTime());
		return curdate;
	}


	/**
	 * This method is to perform assert in alert box.
	 * @param driver: Instance of WebDriver.
	 * @param expectedText : Expected text in the alert box.
	 * @param ElementID: element name in the application.
	 * @param desc: purpose of the assertion.
	 * @param ScenarioDetailsHashMap: Hashmap variable contains Scenario details.
	 * @return It return true if assert is pass and false if assert is fail
	 * @Modified By: Sandeep Jain
	 * @Modified Description: Added Hashmap variable to serve parallel testing purpose. 
	 */
	public static void assertAlertMessage(WebDriver driver,String expectedText,String ElementID,String desc,HashMap<String, String> ScenarioDetailsHashMap)
	{

		Calendar cal_StartTime = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(CyborgConstants.TIME_FORMAT);
		String startTime = sdf.format(cal_StartTime.getTime());
		String resultValue=null;
		String innertext = getAlertText(driver);

		/*System.out.println("result of assert inner::"
				+ " original value of text::\"" + innertext
				+ "\" and Expected value is::\"" + expectedText + "\"");
		 */
		if (innertext.equals(expectedText)) {
			resultValue = "Pass";
			CyborgConstants.logger.info("Actual and expected values "+"( "+innertext+" , "+expectedText+" )are same.");	
		}else{
			resultValue = "Fail";
			CyborgConstants.logger.warn("Actual and expected values "+"( "+innertext+" , "+expectedText+" )are different.");
		}
		String endtime =null;
		String time = null;
		Calendar cal_EndTime = Calendar.getInstance();
		endtime = sdf.format(cal_EndTime.getTime());
		time = Time(startTime, endtime);

		HashMap<String, String> resultDetailsHashMap = new HashMap<String, String>();
		resultDetailsHashMap.put("ScenarioName", ScenarioDetailsHashMap.get("ScenarioName"));
		resultDetailsHashMap.put("SubScenarioNo", ScenarioDetailsHashMap.get("SubScenarioNo"));
		resultDetailsHashMap.put("DataSetNo", ScenarioDetailsHashMap.get("DataSetNo"));
		resultDetailsHashMap.put("FunctionName", ScenarioDetailsHashMap.get("FunctionName"));
		resultDetailsHashMap.put("ClassName", ScenarioDetailsHashMap.get("ClassName"));
		resultDetailsHashMap.put("ElementId", ElementID);
		resultDetailsHashMap.put("Description", desc);
		resultDetailsHashMap.put("ExpectedValue", expectedText);
		resultDetailsHashMap.put("ActualValue", innertext);
		resultDetailsHashMap.put("Status", resultValue);
		resultDetailsHashMap.put("Time", time);
		AssertionResults.setAssertionResult(driver,resultDetailsHashMap,null,null,ScenarioDetailsHashMap.get("BrowserType"));
		resultDetailsHashMap.clear();
		//return false;
	}


	/**
	 * Below method will make the driver to come out of selected window or frame.
	 * @date April 23th 2014
	 * @param driver Instance of webDriver.
	 * @author Priyaranjan
	 */
	public static void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	/**
	 * This method is used to scroll up .
	 * @date April 22th 2014
	 * @param driver Instance of webDriver.
	 * @author Priyaranjan
	 */
	public static void scrollUp(WebDriver driver)
	{
		((JavascriptExecutor)driver).executeScript("window.scrollTo(0, 0);", new Object[0]);
	}
	/**
	 * This method is used to scroll Down.
	 * @date April 22th 2014
	 * @param driver Instance of webDriver.
	 * @author Priyaranjan
	 */
	public static void scrollDown(WebDriver driver)
	{
		JavascriptExecutor jsx = (JavascriptExecutor)driver;
		jsx.executeScript("window.scrollBy(0,450)", "");
	}

	/**
	 * This method will give the selected option from the dropDown.
	 * @date April 24th 2014.
	 * @param driver Instance of webDriver.
	 * @param selectLocator The locating mechanism.
	 * @param elem Should pass webElement or null if By is used and vice versa.
	 * @return String selected option text.
	 * @author priyaranjanm
	 */
	public static String getSelectedOptionOfDropDown(WebDriver driver, By selectLocator,WebElement elem)
	{
		Exception ex = null;
		Select select=null;
		String selectedOption=null;
		int counter=0;
		long maxTime = 20000L;
		long timeSlice = 250L;
		long elapsedTime = 0L;
		boolean result = false;
		do
			try {
				pauseExecution((int)timeSlice);
				elapsedTime += timeSlice;
				if(elem == null){
					select= new Select(waitForElementPresentNVisible(driver, selectLocator, 2L));
				}else{
					select= new Select(elem);
				}
				selectedOption=select.getFirstSelectedOption().getText();
				result = true;
			} catch (NoSuchElementException e) {
				if(counter==0){
					System.out.println("element with identifier " + selectLocator + " not found retrying...");
					CyborgConstants.logger.error("element with identifier " + selectLocator + " not found retrying.");
					counter=1;
				}
				ex = e;
			} catch (StaleElementReferenceException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier " + selectLocator + " is changed due to javascript retrying...");
					CyborgConstants.logger.error("element with identifier " + selectLocator + " not found retrying.");
					counter=1;
				}
				ex = e;
			} catch (ElementNotVisibleException e) {
				if(counter==0){
					System.out.println("cannot click on element with identifier " + selectLocator + " is changed due to javascript retrying...");
					CyborgConstants.logger.error("element with identifier " + selectLocator + " not found retrying.");
					counter=1;
				}
				ex = e;
			} catch (WebDriverException e) {
				if(counter==0){
					System.out.println("element with identifier " + selectLocator + " not found retrying...");
					CyborgConstants.logger.error("element with identifier " + selectLocator + " not found retrying.");
					counter=1;
				}
				if (elapsedTime >= 1000L)
					throw e;
			}
			catch (Exception e) {
				if(counter==0){
					System.out.println("element with identifier " + selectLocator + " not found retrying...");
					CyborgConstants.logger.error("element with identifier " + selectLocator + " not found retrying.");
					counter=1;
				}
				ex = e;
			}
			while ((!(result)) && (elapsedTime < maxTime));
			if (elapsedTime >= maxTime) {
				CyborgConstants.logger.fatal(ex);
				throw new TimeoutException(ex);
			}

			return selectedOption;
	}

	/**
	 * This method will capture screenShot.
	 * @date April 24th 2014
	 * @param driver Instance of WebDriver.
	 * @param destSht Destination file path
	 * @author priyaranjanm
	 */
	public static void captureScreenshot(WebDriver driver,File destSht)
	{
		try {
			File scrnsht =	((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrnsht, destSht);
			CyborgConstants.logger.info("Screenshot captured successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			CyborgConstants.logger.error(e);
		}
	}


	/**
	 * This method is to perform assertion in dropdown  field.
	 * @param driver: Instance of WebDriver.
	 * param by: The locating mechanism.
	 * @param elem : Should pass webElement or null if By is used and vice versa.
	 * @param expectedText : Expected text in the dropdown.
	 * @param ElementID:  element name in the application.
	 * @param desc: purpose of the assertion.
	 * @param ScenarioDetailsHashMap : Hashmap variable contains Scenario details.
	 * @return: It return true if assert is pass and false if assert is fail
	 * @Modified By: Sandeep Jain
	 * @Modified Description: Added Hashmap variable to serve parallel testing purpose. 
	 */
	public static String assertDropDownText(WebDriver driver, By by, WebElement elem,String expectedText, String ElementID,String desc, HashMap<String, String> ScenarioDetailsHashMap)
	{


		Calendar cal_StartTime = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(CyborgConstants.TIME_FORMAT);
		String startTime = sdf.format(cal_StartTime.getTime());
		String resultValue=null;
		String innertext = getSelectedOptionOfDropDown(driver, by, elem);

		/*System.out.println("result of assert inner::"
	    + " original value of text::\"" + innertext
	    + "\" and Expected value is::\"" + expectedText + "\"");
		 */
		if (innertext.equals(expectedText)) {
			resultValue = "Pass";
			CyborgConstants.logger.info("Actual and expected values "+"( "+innertext+" , "+expectedText+" )are same."); 
		}else{
			resultValue = "Fail";
			CyborgConstants.logger.warn("Actual and expected values "+"( "+innertext+" , "+expectedText+" )are different.");
		}
		String endtime =null;
		String time = null;
		Calendar cal_EndTime = Calendar.getInstance();
		endtime = sdf.format(cal_EndTime.getTime());
		time = Time(startTime, endtime);

		HashMap<String, String> resultDetailsHashMap = new HashMap<String, String>();
		resultDetailsHashMap.put("ScenarioName", ScenarioDetailsHashMap.get("ScenarioName"));
		resultDetailsHashMap.put("SubScenarioNo", ScenarioDetailsHashMap.get("SubScenarioNo"));
		resultDetailsHashMap.put("DataSetNo", ScenarioDetailsHashMap.get("DataSetNo"));
		resultDetailsHashMap.put("FunctionName", ScenarioDetailsHashMap.get("FunctionName"));
		resultDetailsHashMap.put("ClassName", ScenarioDetailsHashMap.get("ClassName"));
		resultDetailsHashMap.put("ElementId", ElementID);
		resultDetailsHashMap.put("Description", desc);
		resultDetailsHashMap.put("ExpectedValue", expectedText);
		resultDetailsHashMap.put("ActualValue", innertext);
		resultDetailsHashMap.put("Status", resultValue);
		resultDetailsHashMap.put("Time", time);
		AssertionResults.setAssertionResult(driver,resultDetailsHashMap,elem,by,ScenarioDetailsHashMap.get("BrowserType"));
		resultDetailsHashMap.clear();
		return resultValue;




	}



	/**
	 * Below Method will give current Date.
	 * @date May 19th 2014  
	 * @return String date.
	 * @author Pavan
	 */
	public static String getDate(int incrementFromCurDate){

		try {
			String dateFormt=getPropertyValue("dateFormate", System.getProperty("user.dir")+"\\Configurations\\base.properties");
			if ((dateFormt == null) || (dateFormt.equals(""))) {
				System.out.println("Please provide valid Date Format");
			}
			SimpleDateFormat dateFormat=new SimpleDateFormat(dateFormt);
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.DATE, incrementFromCurDate);
			currentdate=dateFormat.format(cal.getTime());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentdate;
	}

	/**
	 * Below Method will give current Time.
	 * @date May 19th 2014  
	 * @return String date.
	 * @author Pavan
	 */
	public static String getTime(int incrementFromCurTime){
		try{
			String timeFormt=getPropertyValue("timeFormate", System.getProperty("user.dir")+"\\Configurations\\base.properties");
			if((timeFormt==null)|| (timeFormt.equals(""))){
				System.out.println("Please provide valid Time Format");
			}
			SimpleDateFormat dateFormat=new SimpleDateFormat(timeFormt);
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.HOUR, incrementFromCurTime);
			currentTime=dateFormat.format(cal.getTime());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return currentTime;
	}

	/**
	 * The below method will copy file to ftp location.
	 * @param uName Username for ftp login.
	 * @param password Password for FTP login.
	 * @param filePath Path of the file in local system.
	 * @param uploadPath FTP path where the file need to be copied.
	 * @author PriyaranjanM
	 */
	public static void uploadFileToFTP(String uName,String password,String filePath,String uploadPath)
	{
		String ftpUrl = "ftp://%s:%s@%s/%s;type=a";
		String host = "4sqa.four-soft.com";
		String user = "4sqaftp";
		String pass = "visilog123";
		//		String filePath = System.getProperty("user.dir") + "\\FTPXmlFiles\\TO-DK.xml";
		//		String uploadPath = "xchange/import/to/in/TO-DK.xml";
		ftpUrl = String.format(ftpUrl, user, pass, host, uploadPath);
		System.out.println("Upload URL: " + ftpUrl);
		int BUFFER_SIZE = 1024;

		try {
			URL url = new URL(ftpUrl);
			URLConnection conn = url.openConnection();
			System.out.println("ABC===="+conn.getURL());
			FileInputStream inputStream = new FileInputStream(filePath);
			BufferedOutputStream  bos = new BufferedOutputStream( conn.getOutputStream() );
			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			bos.close();

			System.out.println("File uploaded");
		} catch (IOException ex) {
			ex.printStackTrace();
			CyborgConstants.logger.error(ex);
		}
	}

	public static List<String> getAllFileNamesFromFtp(String filedir,String uName,String password) throws SocketException, IOException
	{
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("4sqa.four-soft.com");
		ftpClient.login(uName, password);
		List<String> names = new ArrayList<String>();
		String[] files = ftpClient.listNames(filedir);
		if (files != null && files.length > 0) {
			for (String aFile: files) {
				String[] fileNames=aFile.split("/");
				names.add(fileNames[fileNames.length-1]);
			}
		}

		ftpClient.logout();
		ftpClient.disconnect();
		return names;
	}


	/**
	 * Below method will compare two values.
	 * @param originalText: actual Value to be compared.
	 * @param expectedText: Expected result value.
	 * @param desc: Purpose of  validating.
	 * @param ScenarioDetailsHashMap : Hashmap variable contains Scenario details.
	 * @author PriyaRanjanM
	 * @Modified By: Sandeep Jain
	 * @Modified Description: Added Hashmap variable to serve parallel testing purpose. 
	 */
	public static void assertTwoValues(String originalText,String expectedText,String desc,
			HashMap<String, String> ScenarioDetailsHashMap)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(CyborgConstants.TIME_FORMAT);
		String startTime = sdf.format(cal.getTime());
		String resultValue=null;

		if(originalText.equals(expectedText))
		{
			resultValue = "Pass";
			CyborgConstants.logger.info("Actual and expected values "+"( "+originalText+" , "+expectedText+" )are same.");
		}else{
			resultValue = "Fail";
			CyborgConstants.logger.warn("Actual and expected values "+"( "+originalText+" , "+expectedText+" )are different.");
		}

		Calendar cal1 = Calendar.getInstance();
		String endtime = sdf.format(cal1.getTime());
		String time = Time(startTime, endtime);
		HashMap<String, String> resultDetailsHashMap = new HashMap<String, String>();
		resultDetailsHashMap.put("ScenarioName", ScenarioDetailsHashMap.get("ScenarioName"));
		resultDetailsHashMap.put("SubScenarioNo", ScenarioDetailsHashMap.get("SubScenarioNo"));
		resultDetailsHashMap.put("DataSetNo", ScenarioDetailsHashMap.get("DataSetNo"));
		resultDetailsHashMap.put("FunctionName", ScenarioDetailsHashMap.get("FunctionName"));
		resultDetailsHashMap.put("ClassName", ScenarioDetailsHashMap.get("ClassName"));
		resultDetailsHashMap.put("ElementId", "DETAILS COMPARISION");
		resultDetailsHashMap.put("Description", desc);
		resultDetailsHashMap.put("ExpectedValue", expectedText);
		resultDetailsHashMap.put("ActualValue", originalText);
		resultDetailsHashMap.put("Status", resultValue);
		resultDetailsHashMap.put("Time", time);
		AssertionResults.setAssertionResult(driver,resultDetailsHashMap,null,null,ScenarioDetailsHashMap.get("BrowserType"));
		resultDetailsHashMap.clear();


	}


	/**
	 * @Description: Below method is to perform keyboard operation without passing element identifier.
	 * @param driver: WebDriver object
	 * @param key: which Keboard key need to pressed.
	 * @author: Sandeep Jain
	 * @modified by:PrasananM
	 * @Date: September 18th 2014
	 */
	public static void action_Key(WebDriver driver, Keys key)
	{
		Actions action = new Actions(driver);
		action.sendKeys(key);
		action.build().perform();
		pauseExecution(2000);
	}

	/**
	 * @Description: Below method is to reload the current page.
	 * @param driver: WebDriver object
	 * @author: Prasanna Modugu
	 * @Date: October 7th 2014
	 */
	public static void pageRefresh(WebDriver driver)
	{
		driver.navigate().refresh();
		pauseExecution(2000);
	}
	/**
	 * @Description: Below method is to switch to the active element of the page and it returns a WebElement.
	 * @param driver: WebDriver object
	 * @param element: WebElement object
	 * @author: Prasanna Modugu
	 * @Date: October 7th 2014
	 */
	public static WebElement switchToActiveElement(WebDriver driver)
	{
		WebElement element=driver.switchTo().activeElement();
		return element;
	}

	/**
	 * @Description: Below method is to perform mose drag and drop functionality.
	 * @param driver: WebDriver object
	 * @param element_drag: element location 
	 * @author: Sandeep Jain
	 * @Date: September 25th 2014
	 */
	public static void action_DragAndDrop(WebDriver driver,WebElement element_drag, WebElement element_drop)
	{
		Actions action = new Actions(driver);
		action.dragAndDrop(element_drag, element_drop);
		action.perform();
	}


	/**
	 * @Description: Below method is to enter text.
	 * @param driver: WebDriver object
	 * @param key: which Keboard key need to pressed.
	 * @author: Sandeep Jain
	 * @modified by:PrasananM
	 * @Date: September 18th 2014
	 */
	public static void action_SendKeys(WebDriver driver, String value)
	{
		Actions action = new Actions(driver);
		action.sendKeys(value);
		action.build().perform();
		pauseExecution(2000);
	}

	public static String getAttribute(WebElement Element,String AttributeName){
		String AttributeValue=null;
		try{
			System.out.println("Attr Val=="+Element.getText());
			AttributeValue=Element.getAttribute(AttributeName);
			return AttributeValue;	
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Attribute not available with the name: "+AttributeValue);
			return AttributeValue;	
		}
	}

	/**
	 * This method will perform double click action in a particular webElement.
	 * 
	 * @param driver
	 *            Instance of the webDriver.
	 * @param by
	 *            The locating mechanism.
	 * @param elem
	 *            Should pass webElement or null if By is used and vice versa.
	 * @param timeOuts
	 *            Synchronization timeouts.
	 *            @author priyaranjanm
	 */
	public static void doubleClickOnElement(WebDriver driver, By by,
			WebElement elem, long timeOuts) {
		handleInterrupt();
		WebElement element = null;
		int counter=0;
		long maxTime = timeOuts * 1000L;
		long timeSlice = 250L;
		long elapsedTime = 0L;
		Exception ex = null;
		try {
			pauseExecution((int) timeSlice);
			elapsedTime += timeSlice;
			if (elem == null) {
				element = locateElement(driver, by, timeOuts);
			} else {
				element = elem;
			}

			Actions action = new Actions(driver);
			action.doubleClick(element).perform();
		} catch (StaleElementReferenceException e) {
			if(counter==0){
				System.out.println("element with identifier " + by
						+ " not found retrying...");
				CyborgConstants.logger.error("element with identifier " + by
						+ " not found retrying...");
				counter=1;
			}
			ex = e;
		} catch (NoSuchElementException e) {
			if(counter==0){
				System.out.println("element with identifier " + by
						+ " not found retrying...");
				CyborgConstants.logger.fatal("element with identifier " + by
						+ " not found retrying...");
				counter=1;
			}
		}


	}

	/**
	 * @param driver: Below method is to perform Export the data into Excel Workbooka nd saving it in Report Folder path.
	 * @param ScenarioDetailsHashMap: Hashmap which contains browser information.
	 * @param ModuleName: For which module we are performing Export functionality, here only the value which is present in base.properties files should be provided. 
	 * @throws Exception
	 * @author Sandeep Jain
	 * @Date: October 15th 2014
	 */
	public static void Export_Excel(WebDriver driver,HashMap<String, String> ScenarioDetailsHashMap,String ModuleName) throws Exception
	{
		Robot rb=new Robot();
		if(ScenarioDetailsHashMap.get("BrowserType").equalsIgnoreCase("chrome"))
		{
			GenericMethods.pauseExecution(10000);
			rb.keyPress(KeyEvent.VK_HOME);
			rb.keyRelease(KeyEvent.VK_HOME);
			GenericMethods.pauseExecution(2000);
			String ReportFolder = System.getProperty("user.dir")+ "\\Reports\\" + GenericMethods.getPropertyValue("ExecutionTime",System.getProperty("user.dir")+"\\Configurations\\base.properties");
			parseChars(ReportFolder+"\\Chrome-", rb);
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
			GenericMethods.pauseExecution(3000);

			File ReportFolderPath = new File(ReportFolder);
			File[] listOfFiles = ReportFolderPath.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) 
			{
				if (listOfFiles[i].isFile()) 
				{
					if(listOfFiles[i].getName().toLowerCase().contains("chrome") && listOfFiles[i].getName().contains(ModuleName))
					{
						Desktop.getDesktop().open(new File(ReportFolderPath+"\\"+ listOfFiles[i].getName()));
					}
				} 
			}
			GenericMethods.pauseExecution(7000);
			rb.keyPress(KeyEvent.VK_ALT);
			rb.keyPress(KeyEvent.VK_F4);
			rb.keyRelease(KeyEvent.VK_F4);
			rb.keyRelease(KeyEvent.VK_ALT);

		}else if(ScenarioDetailsHashMap.get("BrowserType").equalsIgnoreCase("firefox")){
			rb.keyPress(KeyEvent.VK_ALT);
			rb.keyPress(KeyEvent.VK_O);
			GenericMethods.pauseExecution(2000);
			rb.keyRelease(KeyEvent.VK_O);
			rb.keyRelease(KeyEvent.VK_ALT);
			GenericMethods.pauseExecution(2000);
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
			GenericMethods.pauseExecution(5000);
			rb.keyPress(KeyEvent.VK_F12);
			rb.keyRelease(KeyEvent.VK_F12);
			GenericMethods.pauseExecution(2000);
			rb.keyPress(KeyEvent.VK_HOME);
			rb.keyRelease(KeyEvent.VK_HOME);
			GenericMethods.pauseExecution(2000);
			parseChars(System.getProperty("user.dir")+ "\\Reports\\" + GenericMethods.getPropertyValue("ExecutionTime",System.getProperty("user.dir")+"\\Configurations\\base.properties")+"\\Firefox-", rb);
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
			GenericMethods.pauseExecution(3000);
			rb.keyPress(KeyEvent.VK_ALT);
			rb.keyPress(KeyEvent.VK_F4);
			GenericMethods.pauseExecution(3000);
			rb.keyRelease(KeyEvent.VK_F4);
			rb.keyRelease(KeyEvent.VK_ALT);
		}
		else if(ScenarioDetailsHashMap.get("BrowserType").equalsIgnoreCase("ie")){
			rb.keyPress(KeyEvent.VK_ALT);
			rb.keyPress(KeyEvent.VK_O);
			GenericMethods.pauseExecution(2000);
			rb.keyRelease(KeyEvent.VK_O);
			rb.keyRelease(KeyEvent.VK_ALT);
			GenericMethods.pauseExecution(10000);
			rb.keyPress(KeyEvent.VK_F12);
			rb.keyRelease(KeyEvent.VK_F12);
			GenericMethods.pauseExecution(2000);
			rb.keyPress(KeyEvent.VK_SHIFT);
			rb.keyPress(KeyEvent.VK_TAB);
			rb.keyRelease(KeyEvent.VK_TAB);
			rb.keyRelease(KeyEvent.VK_SHIFT);
			GenericMethods.pauseExecution(2000);
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
			GenericMethods.pauseExecution(3000);
			rb.keyPress(KeyEvent.VK_HOME);
			rb.keyRelease(KeyEvent.VK_HOME);
			GenericMethods.pauseExecution(2000);
			parseChars(System.getProperty("user.dir")+ "\\Reports\\" + GenericMethods.getPropertyValue("ExecutionTime",System.getProperty("user.dir")+"\\Configurations\\base.properties")+"\\IE-", rb);
			rb.keyPress(KeyEvent.VK_ENTER);
			rb.keyRelease(KeyEvent.VK_ENTER);
			GenericMethods.pauseExecution(9000);
			rb.keyPress(KeyEvent.VK_ALT);
			rb.keyPress(KeyEvent.VK_F4);
			GenericMethods.pauseExecution(9000);
			rb.keyRelease(KeyEvent.VK_F4);
			rb.keyRelease(KeyEvent.VK_ALT);
		}
	}

	public static void parseChars(String path, Robot robot) throws Exception {
		String letter="";
		for (int i = 0; i < path.length(); i ++) {   
			char chary = path.charAt(i);  
			letter=Character.toString(chary);
			if (Character.isLetterOrDigit(letter.charAt(0))) {   
				try {   
					boolean upperCase = Character.isUpperCase(letter.charAt(0));   
					String variableName = "VK_" + letter.toUpperCase();   
					KeyEvent ke = new KeyEvent(new JTextField(), 0, 0, 0, 0, ' ');   
					Class clazz = ke.getClass();   
					Field field = clazz.getField(variableName);   
					int keyCode = field.getInt(ke);   
					robot.delay(80);   
					if (upperCase) robot.keyPress(KeyEvent.VK_SHIFT);   
					robot.keyPress(keyCode);   
					robot.keyRelease(keyCode);   
					if (upperCase) robot.keyRelease(KeyEvent.VK_SHIFT);   
				}   
				catch(Exception e) { System.out.println(e); 
				CyborgConstants.logger.fatal(e);
				}   
			}   
			else {
				if (letter.equals("!")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_1);    
					robot.keyRelease(KeyEvent.VK_1);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("@")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_2);    
					robot.keyRelease(KeyEvent.VK_2);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("#")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_3);    
					robot.keyRelease(KeyEvent.VK_3);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("#")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_3);    
					robot.keyRelease(KeyEvent.VK_3);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("$")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_4);    
					robot.keyRelease(KeyEvent.VK_4);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("%")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_5);    
					robot.keyRelease(KeyEvent.VK_5);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("^")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_6);    
					robot.keyRelease(KeyEvent.VK_6);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("&")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_7);    
					robot.keyRelease(KeyEvent.VK_7);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("*")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_8);    
					robot.keyRelease(KeyEvent.VK_8);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if (letter.equals("=")) {
					robot.keyPress(KeyEvent.VK_EQUALS);    
					robot.keyRelease(KeyEvent.VK_EQUALS);  
				}
				else if (letter.equals(" ")) {   
					robot.keyPress(KeyEvent.VK_SPACE);   
					robot.keyRelease(KeyEvent.VK_SPACE);   
				}   
				else if (letter.equals("/")) {
					robot.keyPress(KeyEvent.VK_BACK_SLASH);    
					robot.keyRelease(KeyEvent.VK_BACK_SLASH);  
				}
				else if (letter.equals("\\")){
					robot.keyPress(KeyEvent.VK_BACK_SLASH);    
					robot.keyRelease(KeyEvent.VK_BACK_SLASH);  
				}
				else if (letter.equals("_")) {
					robot.keyPress(KeyEvent.VK_SHIFT);    
					robot.keyPress(KeyEvent.VK_MINUS);    
					robot.keyRelease(KeyEvent.VK_MINUS);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if(letter.equals(":")) { 
					robot.keyPress(KeyEvent.VK_SHIFT); 
					robot.keyPress(KeyEvent.VK_SEMICOLON);    
					robot.keyRelease(KeyEvent.VK_SEMICOLON);   
					robot.keyRelease(KeyEvent.VK_SHIFT);  
				}
				else if(letter.equals(";")) { 
					robot.keyPress(KeyEvent.VK_SEMICOLON);    
					robot.keyRelease(KeyEvent.VK_SEMICOLON);  
				}
				else if(letter.equals(",")) { 
					robot.keyPress(KeyEvent.VK_COMMA);    
					robot.keyRelease(KeyEvent.VK_COMMA);  
				}
				else if(letter.equals("?")) { 
					robot.keyPress(KeyEvent.VK_SHIFT);   
					robot.keyPress(KeyEvent.VK_SLASH);    
					robot.keyRelease(KeyEvent.VK_SLASH);   
					robot.keyRelease(KeyEvent.VK_SHIFT); 
				}
				else if(letter.equals(".")) { 
					robot.keyPress(KeyEvent.VK_DECIMAL);   
					robot.keyRelease(KeyEvent.VK_DECIMAL);    
				}
				else if(letter.equals("-")) { 
					robot.keyPress(KeyEvent.VK_MINUS);   
					robot.keyRelease(KeyEvent.VK_MINUS);    
				}

			}   
		}   
	}
	/**
	 * @Description: Below method will return the status of the check box.
	 * @param Element: WebElement object
	 * @author: Prasanna Modugu
	 * @Date: October 14th 2014
	 */
	public static boolean isChecked(WebElement Element){
		boolean isChecked=false;
		try{
			isChecked = Element.isSelected();	
			return isChecked;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Unable to read the check box.");
			return isChecked;	
		}

	}
	/**
	 * @Description: Below method is to make a right click on web element.
	 * @param driver: WebDriver object
	 * @param key: which Keboard key need to pressed.
	 * @author: Prasanna Modugu
	 * @Date: October 29th 2014
	 */

	public static void action_RightClick(WebDriver driver, WebElement elementToClick)
	{
		Actions action= new Actions(driver);
		action.contextClick(elementToClick).build().perform();
	}
	/**
	 * @Description: Below method is to Fetch the application Url
	 * @author: Srikala Gudavalli
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @Date: November 11th 2014
	 */

	public static String getAppUrl() 
	{
		String url = null;
		String singleWorkBookPath = System.getProperty("user.dir")
		+ GenericMethods.getPropertyValue("singleworkbookPath",
				GenerateControlfiles.path);
		Connection con;
		try {
			con = DBConnectionManager
			.getConnection(singleWorkBookPath);

			Statement configStmt = con.createStatement();
			ResultSet config = configStmt
			.executeQuery("select * from [Configurations$]");
			while (config.next()) {
				url= config.getString("URL");
			}
			System.out.println("url===="+url);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;

	}

	/**
	 * @Description: Below method is to get architecture of the underlying OS on Windows whether it is 32bit or 64bit.
	 * @return returns a string (architecture of Operating System)
	 * @author: Prasanna Modugu
	 * @Date: October 29th 2014
	 */

	public static String getArchOfOSOnWindow ()
	{
		String arch = System.getenv("PROCESSOR_ARCHITECTURE");
		String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");
		String realArch = arch.endsWith("64")
		|| wow64Arch != null && wow64Arch.endsWith("64")
		? "64" : "32";
		return realArch;
	}
	/**
	 * @param ModuleName : Module Name for which excel workbook in exported.
	 * @param ColumnName : Column Name in the Excel Sheet.
	 * @param RowNo: Row Number for which cell details need to be fetched.
	 * @param ScenarioDetailsHashMap
	 * @return
	 * @author Sandeep Jain
	 * @Date December 2nd 2014
	 */
	public static String getExportedExcelvalue(String ModuleName,String ColumnName, int RowNo,HashMap<String, String> ScenarioDetailsHashMap) { 
		String ReportFolder = System.getProperty("user.dir")+ "\\Reports\\" + GenericMethods.getPropertyValue("ExecutionTime",System.getProperty("user.dir")+"\\Configurations\\base.properties");
		File ReportFolderPath = new File(ReportFolder);
		File[] listOfFiles = ReportFolderPath.listFiles();
		ResultSet RS_GetCellDetails =null;
		String WorkbookPath =null;
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if (listOfFiles[i].isFile()) 
			{
				if(listOfFiles[i].getName().toLowerCase().contains(ScenarioDetailsHashMap.get("BrowserType").toLowerCase()) && listOfFiles[i].getName().contains(ModuleName))
				{
					WorkbookPath = ReportFolderPath+"\\"+ listOfFiles[i].getName();
					break;
				}
			} 
		}
		File file=new File(WorkbookPath);
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Sheet sheet=wb.getSheet("Sheet0");
		int rowcount = sheet.getLastRowNum();
		Row row=sheet.getRow(0);
		row.getPhysicalNumberOfCells();

		HashMap<String, ArrayList<String>> ExportedExcelValues = new HashMap<String, ArrayList<String>>();

		String conentInExcel =null;
		for (int columname = 0; columname < row.getPhysicalNumberOfCells(); columname++) 
		{
			String Columnnvalue =row.getCell(columname).getStringCellValue();
			ArrayList<String> listrowvalues = new ArrayList<String>();
			Boolean Hashmap_Add = false;
			for (int rowno = 1; rowno < sheet.getPhysicalNumberOfRows(); rowno++) 
			{
				String      cellContent=null;
				Row row1=sheet.getRow(rowno);
				Cell cel=row1.getCell(columname);
				try
				{
					int cellType = cel.getCellType();
					if(cellType == Cell.CELL_TYPE_STRING)
					{
						cellContent=cel.getStringCellValue().toString();      
						if(!cellContent.equals("")&&!cellContent.equals(" "))
							Hashmap_Add = true;
					}
					else if (cellType == Cell.CELL_TYPE_NUMERIC)
					{
						if (DateUtil.isCellDateFormatted(cel)) 
						{
							String dateFormate=getPropertyValue("dateFormate", System.getProperty("user.dir")+"\\Configurations\\base.properties");
							SimpleDateFormat simpDateFormat= new SimpleDateFormat(dateFormate);
							Date date= new Date();
							String curdate=simpDateFormat.format(cel.getDateCellValue());
							cellContent=curdate;
						} else 
						{
							DecimalFormat df = new DecimalFormat("0.###");
							cellContent=df.format(cel.getNumericCellValue())+"";
						}
					}
				}
				catch(NullPointerException e)
				{cellContent="";}
				listrowvalues.add(cellContent);
			}
			if(Hashmap_Add){
				ExportedExcelValues.put(Columnnvalue, listrowvalues);}
		}

		Set<Entry<String, ArrayList<String>>> setExportedDetails = ExportedExcelValues.entrySet();
		Iterator<Entry<String,  ArrayList<String>>> iteratorMap = setExportedDetails.iterator();
		while(iteratorMap.hasNext()) 
		{
			Map.Entry<String, ArrayList<String>> entry =(Map.Entry<String, ArrayList<String>>) iteratorMap.next();
			String key = entry.getKey();
			List<String> values = entry.getValue();
			if (key.equalsIgnoreCase(ColumnName))
			{
				conentInExcel=values.get(RowNo-1);
				break;
			}
		}
		return conentInExcel;
	}
	/**
	 * Below method will set the TestCaseHistory value defaulted to Zero and back up the history of the TestCaseHistory counte on last day of every month
	 * @date Dec 08th 2014  
	 * @author Srikala Gudavalli
	 */
	public static void backUpHistoryFile(){
		try{

			String dest;
			String relPath=System.getProperty("user.dir");

			String sourcePath=relPath+"\\Configurations";
			dest=System.getProperty("user.dir")+"\\History\\";
			System.out.println(dest);
			BackUpTestCaseHistory.backUpHistoryFile(sourcePath,dest); 
		}catch (Exception e) {
			CyborgConstants.logger.fatal(e);
			// TODO: handle exception
		}
	}
	public static String randomNumericString(int stringLen)
	{
		String randomId = "";
		for (int j = 1; j <= stringLen; ++j) {
			randomId = randomId + "0";
		}
		randomId = "1" + randomId;
		int rndStr = new Random().nextInt(Integer.parseInt(randomId));
		randomId = "";
		int len = Integer.valueOf(rndStr).toString().length();
		if (len < stringLen) {
			for (int j = 1; j <= stringLen - len; ++j) {
				randomId = randomId + "0";
			}
		}
		randomId = randomId + Integer.valueOf(rndStr).toString();
		System.out.println("randomId::::"+randomId);
		return randomId;
	}
	/**
	 * Below method will Check Whether alert Present or Not
	 * @date July 03rd 2014  
	 * @param driver Instance of webDriver.
	 * @author Pavan Bikumandla
	 */
	public static boolean isAlertFound(WebDriver driver) {
		boolean present=false;
		try {
			driver.switchTo().alert();
			present=true;
		}catch (Exception e) {
			CyborgConstants.logger.error(e);
			System.out.println("No alert Found");
		}
		return present;
	}

	/**
	 * This method is to perform assertion of all values in dropdown  field.
	 * @param driver: Instance of WebDriver.
	 * param by: The locating mechanism.
	 * @param elem : Should pass webElement or null if By is used and vice versa.
	 * @param expectedText : List of Expected text in the dropdown.
	 * @param ElementID:  element name in the application.
	 * @param desc: purpose of the assertion.
	 * @param ScenarioDetailsHashMap : Hashmap variable contains Scenario details.
	 * @return: It return true if assert is pass and false if assert is fail
	 * @Modified By: Sangeeta M
	 * @Modified Description: Added Hashmap variable to serve parallel testing purpose. 
	 */
	public static boolean assertDropDownAllValues(WebDriver driver, By by, WebElement elem,String expectedText, String ElementID,String desc, HashMap<String, String> ScenarioDetailsHashMap)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(CyborgConstants.TIME_FORMAT);
		String startTime = sdf.format(cal.getTime());
		String resultValue=null;
		String [] arr= expectedText.split(",");
		for (int i = 0; i < arr.length; i++) {
			xlhs.add(arr[i]);
			// System.out.println("Excelvalue "+i+" "+arr[i]);
		}
		hs=  assertDropdown(driver,elem);
		//System.out.println("hs.equals(xlhs) ="+hs.equals(xlhs));
		if(hs.equals(xlhs))  {
			resultValue= "Pass";


		}
		else{
			resultValue= "Fail";
		}
		String expected=xlhs.toString();
		String actual=hs.toString();
		Calendar cal1 = Calendar.getInstance();
		String endtime = sdf.format(cal1.getTime());
		String time = Time(startTime, endtime);
		HashMap<String, String> resultDetailsHashMap = new HashMap<String, String>();
		resultDetailsHashMap.put("ScenarioName", ScenarioDetailsHashMap.get("ScenarioName"));
		resultDetailsHashMap.put("SubScenarioNo", ScenarioDetailsHashMap.get("SubScenarioNo"));
		resultDetailsHashMap.put("DataSetNo", ScenarioDetailsHashMap.get("DataSetNo"));
		resultDetailsHashMap.put("FunctionName", ScenarioDetailsHashMap.get("FunctionName"));
		resultDetailsHashMap.put("ClassName", ScenarioDetailsHashMap.get("ClassName"));
		resultDetailsHashMap.put("ElementId", ElementID);
		resultDetailsHashMap.put("Description", desc);
		resultDetailsHashMap.put("ExpectedValue", expected);
		resultDetailsHashMap.put("ActualValue", actual);
		resultDetailsHashMap.put("Status", resultValue);
		resultDetailsHashMap.put("Time", time);
		AssertionResults.setAssertionResult(driver,resultDetailsHashMap,null,null,ScenarioDetailsHashMap.get("BrowserType"));
		System.out.println("assert success");
		resultDetailsHashMap.clear();
		hs.clear();
		xlhs.clear();
		return false;
	}
	private static HashSet<String> assertDropdown(WebDriver driver, WebElement elem) {
		List<WebElement> selectOptions= new Select(elem).getOptions();
		for(int i = 0; i<selectOptions.size(); i++){
			String actualOption = selectOptions.get(i).getText();
			// System.out.println("actual value"+i+ " = " + actualOption );
			try{
				hs.add(actualOption);
				System.out.println("passed");
			}
			catch(Exception e)
			{
				System.out.println("fail");
			}
		}
		return hs;
	}


	/**
	 * This method is to compare two dates either both are equal or less or greater
	 * @param DateFormat : Format of the date(Example:"dd-MM-yyyy")
	 * @param Actual_Date: Date taking from application(First Date) 
	 * @param Expected_Date: Date taking from Excelsheet(Second Date)
	 * @param ExpectedDateCondition: Write what is the expected condition for validating two dates(Example: Less or Greater or Equal)
	 * @param SuccessValidationMessageText: Message text for pass date validartion 
	 * @param FailedValidationMessageText: Message text for fail date validartion 
	 * @param ScenarioDetailsHashMap: Hashmap variable contains Scenario details.
	 * @auther By: Sangeeta Mohanty
	 */
	public static void compareDatesByCompareTo(String DateFormat, String Actual_Date, String Expected_Date, String ExpectedDateCondition,String SuccessValidationMessageText,String  FailedValidationMessageText,HashMap<String, String> ScenarioDetailsHashMap) throws ParseException {

		DateFormat df = new SimpleDateFormat(DateFormat);
		Date Actualdate = df.parse(Actual_Date);
		Date Expecteddate = df.parse(Expected_Date);
		String oldDate = df.format(Actualdate); 
		String newDate = df.format(Expecteddate); 

		System.out.println("oldDate :"+oldDate);
		System.out.println("newDate :"+newDate);
		String CompareDateStatus="";
		if (Actualdate.compareTo(Expecteddate) == 0) {
			CompareDateStatus ="Equal";
		}
		else if (Actualdate.compareTo(Expecteddate) < 0) {
			CompareDateStatus ="Less";
		}
		else if (Actualdate.compareTo(Expecteddate) > 0) {
			CompareDateStatus ="Greater";
		}

		if(CompareDateStatus.equalsIgnoreCase(ExpectedDateCondition))
		{
			System.out.println("If ---");
			GenericMethods.assertTwoValuesForDates(oldDate+"", newDate+"", "Date Validation Sucess :-"+SuccessValidationMessageText, ScenarioDetailsHashMap,CompareDateStatus,ExpectedDateCondition);
		}
		else 
		{
			System.out.println("Else ---");
			GenericMethods.assertTwoValuesForDates(oldDate+"", newDate+"", "Date Validation Failed :-"+FailedValidationMessageText, ScenarioDetailsHashMap,CompareDateStatus,ExpectedDateCondition);
		}
	}

	/**
	 * Below method will compare two Date values.
	 * @param originalText: actual Value to be compared.
	 * @param expectedText: Expected result value.
	 * @param desc: Purpose of  validating.
	 * @param ScenarioDetailsHashMap : Hashmap variable contains Scenario details.
	 * @param ExpectedDateCondition: Write what is the expected condition for validating two dates(Example: Less or Greater or Equal)
	 * @param CompareDateStatus: Returning date condition from compareDatesByCompareTo method
	 * @author Sangeeta Mohanty
	 * 
	 */
	public static void assertTwoValuesForDates(String originalText,String expectedText,String desc,
			HashMap<String, String> ScenarioDetailsHashMap,String CompareDateStatus,String ExpectedDateCondition)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(CyborgConstants.TIME_FORMAT);
		String startTime = sdf.format(cal.getTime());
		String resultValue=null;
		System.out.println("assert CompareDateStatus"+CompareDateStatus);
		System.out.println("assert ExpectedDateCondition"+ExpectedDateCondition);

		if((CompareDateStatus.equalsIgnoreCase(ExpectedDateCondition)))
		{
			resultValue = "Pass";			
			CyborgConstants.logger.info("Actual and expected dates "+"( "+originalText+" , "+expectedText+" )are as per expected condition");
		}else{
			resultValue = "Fail";
			CyborgConstants.logger.warn("Actual and expected dates "+"( "+originalText+" , "+expectedText+" )are not as per expected condition");
		}

		Calendar cal1 = Calendar.getInstance();
		String endtime = sdf.format(cal1.getTime());
		String time = Time(startTime, endtime);
		HashMap<String, String> resultDetailsHashMap = new HashMap<String, String>();
		resultDetailsHashMap.put("ScenarioName", ScenarioDetailsHashMap.get("ScenarioName"));
		resultDetailsHashMap.put("SubScenarioNo", ScenarioDetailsHashMap.get("SubScenarioNo"));
		resultDetailsHashMap.put("DataSetNo", ScenarioDetailsHashMap.get("DataSetNo"));
		resultDetailsHashMap.put("FunctionName", ScenarioDetailsHashMap.get("FunctionName"));
		resultDetailsHashMap.put("ClassName", ScenarioDetailsHashMap.get("ClassName"));
		resultDetailsHashMap.put("ElementId", "DETAILS COMPARISION");
		resultDetailsHashMap.put("Description", desc);
		resultDetailsHashMap.put("ExpectedValue", expectedText);
		resultDetailsHashMap.put("ActualValue", originalText);
		resultDetailsHashMap.put("Status", resultValue);
		resultDetailsHashMap.put("Time", time);
		AssertionResults.setAssertionResult(driver,resultDetailsHashMap,null,null,ScenarioDetailsHashMap.get("BrowserType"));
		resultDetailsHashMap.clear();

	}

	/**
	 * Below method will compare two values.If Orginal not equal to expected then result will be pass
	 * @param originalText: actual Value to be compared.
	 * @param expectedText: Expected result value.
	 * @param desc: Purpose of  validating.
	 * @param ScenarioDetailsHashMap : Hashmap variable contains Scenario details.
	 * @author Pavan Bikumandla
	 * @Modified Description: Added Hashmap variable to serve parallel testing purpose. 
	 */
	public static void assertTwoValues_NotEqual(String originalText,String expectedText,String desc,
			HashMap<String, String> ScenarioDetailsHashMap)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(CyborgConstants.TIME_FORMAT);
		String startTime = sdf.format(cal.getTime());
		String resultValue=null;

		if(!originalText.equals(expectedText))
		{
			resultValue = "Pass";
			CyborgConstants.logger.info("Actual and expected values "+"( "+originalText+" , "+expectedText+" )are same.");
		}else{
			resultValue = "Fail";
			CyborgConstants.logger.warn("Actual and expected values "+"( "+originalText+" , "+expectedText+" )are different.");
		}

		Calendar cal1 = Calendar.getInstance();
		String endtime = sdf.format(cal1.getTime());
		String time = Time(startTime, endtime);
		HashMap<String, String> resultDetailsHashMap = new HashMap<String, String>();
		resultDetailsHashMap.put("ScenarioName", ScenarioDetailsHashMap.get("ScenarioName"));
		resultDetailsHashMap.put("SubScenarioNo", ScenarioDetailsHashMap.get("SubScenarioNo"));
		resultDetailsHashMap.put("DataSetNo", ScenarioDetailsHashMap.get("DataSetNo"));
		resultDetailsHashMap.put("FunctionName", ScenarioDetailsHashMap.get("FunctionName"));
		resultDetailsHashMap.put("ClassName", ScenarioDetailsHashMap.get("ClassName"));
		resultDetailsHashMap.put("ElementId", "DETAILS COMPARISION");
		resultDetailsHashMap.put("Description", desc);
		resultDetailsHashMap.put("ExpectedValue", expectedText);
		resultDetailsHashMap.put("ActualValue", originalText);
		resultDetailsHashMap.put("Status", resultValue);
		resultDetailsHashMap.put("Time", time);
		AssertionResults.setAssertionResult(driver,resultDetailsHashMap,null,null,ScenarioDetailsHashMap.get("BrowserType"));
		resultDetailsHashMap.clear();


	}

	//Sandeep - Below method is to update xml value directly to node name
	public static void UpdateXMLValues(String filename, String NodeName, String NodeValue) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{

		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document ;
		dbf = DocumentBuilderFactory.newInstance();
		db   = dbf.newDocumentBuilder();
		document = db.parse(new File(filename));
		Element rootElement = document.getDocumentElement();
		NodeList childNodes =  rootElement.getElementsByTagName("*");
		System.out.println("details---"+childNodes.getLength());
		for (int ElementTagID = 1; ElementTagID < childNodes.getLength(); ElementTagID++) 
		{
			Element Elementdetails = (Element) childNodes.item(ElementTagID);

			int childnodecount = Elementdetails.getChildNodes().getLength();
			if(childnodecount == 0 ||childnodecount == 1 )
			{

				if(Elementdetails.getNodeName().equals(NodeName))
				{
					Elementdetails.setTextContent(NodeValue);
				}
			}
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filename));
		transformer.transform(domSource, streamResult);
	}
	//Sandeep - Below method is to update xml value to node name with basis of Parent Node Attribute value
	public static void UpdateXMLValues_ParentNodeAttributeValue(String filename,String AttributeNode,String AttributeValue,String NodeName, String NodeValue) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{

		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document ;
		dbf = DocumentBuilderFactory.newInstance();
		db   = dbf.newDocumentBuilder();
		document = db.parse(new File(filename));
		Element rootElement = document.getDocumentElement();
		NodeList childNodes =  rootElement.getElementsByTagName("*");
		System.out.println("------------------------------------------------------------------");
		System.out.println("details---"+childNodes.getLength());
		for (int ElementTagID = 1; ElementTagID < childNodes.getLength(); ElementTagID++) 
		{
			Element Elementdetails = (Element) childNodes.item(ElementTagID);

			int childnodecount = Elementdetails.getChildNodes().getLength();
			if(childnodecount == 0 ||childnodecount == 1 )
			{
				System.out.println("P Elementdetails.getNodeName()="+Elementdetails.getNodeName());
				System.out.println("text value"+Elementdetails.getTextContent());

				/*if(Elementdetails.getAttributeNode("CommunicationType").getLocalName().equals("Telephone"))

				{
					System.out.println(Elementdetails.getAttributeNode("CommunicationType").getValue());
				}*/
				if(Elementdetails.getNodeName().equals(NodeName))
				{
					System.out.println("----------------------");
					NamedNodeMap attrs = Elementdetails.getParentNode().getAttributes();  
					for (int AttributeNo = 0; AttributeNo < Elementdetails.getParentNode().getAttributes().getLength(); AttributeNo++) 
					{
						Attr attribute = (Attr)attrs.item(AttributeNo); 
						System.out.println("P attribute name----"+ attribute.getName());
						System.out.println("P attribute value----"+ attribute.getValue());
						System.out.println("******P text="+attribute.getTextContent());
						/*if(attribute.getName().equals(AttributeNode) && attribute.getValue().equals(AttributeValue))
						{
							//Elementdetails.setTextContent(NodeValue);
							System.out.println("******Elementdetails.getNodeName()="+Elementdetails.getNodeName());
							System.out.println("******P text="+attribute.getTextContent());
						}*/
					}
					System.out.println("-------------------********************---------------------------------------");
				}
			}

		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filename));
		transformer.transform(domSource, streamResult);
	}

	//Sandeep - Below method is to update xml value to node name with basis of Current Node Attribute value
	public static void UpdateXMLValues_CurrentNodeAttributeValue(String filename,String AttributeNode,String AttributeValue,String NodeName, String NoveValue) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{

		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document ;
		dbf = DocumentBuilderFactory.newInstance();
		db   = dbf.newDocumentBuilder();
		document = db.parse(new File(filename));
		Element rootElement = document.getDocumentElement();
		NodeList childNodes =  rootElement.getElementsByTagName("*");
		System.out.println("details---"+childNodes.getLength());
		for (int ElementTagID = 1; ElementTagID < childNodes.getLength(); ElementTagID++) 
		{
			Element Elementdetails = (Element) childNodes.item(ElementTagID);

			int childnodecount = Elementdetails.getChildNodes().getLength();
			if(childnodecount == 0 ||childnodecount == 1 )
			{
				if(Elementdetails.getNodeName().equals(NodeName))
				{
					//System.out.println("parent node---"+Elementdetails.getParentNode().getAttributes());
					NamedNodeMap attrs = Elementdetails.getAttributes();  

					for (int AttributeNo = 0; AttributeNo < Elementdetails.getAttributes().getLength(); AttributeNo++) 
					{
						Attr attribute = (Attr)attrs.item(AttributeNo); 
						System.out.println("attribute name----"+ attribute.getName());
						System.out.println("attribute value----"+ attribute.getValue());
						if(attribute.getName().equals(AttributeNode) && attribute.getValue().equals(AttributeValue))
						{
							Elementdetails.setTextContent(NoveValue);
						}
					}
					System.out.println("-------");
				}
			}

		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filename));
		transformer.transform(domSource, streamResult);
	}

	/*Samdeep-Below method is to compare two xml files. */
	public static void CompareXMLFiles(String Expected_FileName, String Actual_FileName,HashMap<String, String> ScenarioDetailsHashMap) throws ParserConfigurationException, SAXException, IOException 
	{
		System.out.println("expected----"+Expected_FileName);
		System.out.println("actual------"+Actual_FileName);

		//Add values into the below list which need not have to be compared while performing xml to xml comparision.
		ArrayList<String> xmldetails = new ArrayList<String>();
		xmldetails.add("MessageID");
		xmldetails.add("ID");
		xmldetails.add("DocumentIdentifier");
		xmldetails.add("DateTime");
		xmldetails.add("ShipmentIdentifier");
		xmldetails.add("ReferenceInformation");
		xmldetails.add("MsgDateAndTime");
		xmldetails.add("ShipmentReference");
		xmldetails.add("MBLID");
		xmldetails.add("ETShimentOrderID");
		xmldetails.add("Date");
		xmldetails.add("ReferenceNo");
		xmldetails.add("VesselSchedule");
		xmldetails.add("PackId");
		xmldetails.add("ShipmentRef");
		xmldetails.add("HouseID");
		xmldetails.add("BookingID");
		xmldetails.add("ManualHouseID");
		xmldetails.add("BLReleaseDate");
		xmldetails.add("ShippingOrderNo");
		xmldetails.add("CarrierBookingRefNo");
		
		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document ;
		Document Actual_document ;

		dbf = DocumentBuilderFactory.newInstance();
		db   = dbf.newDocumentBuilder();

		document = db.parse(new File(Expected_FileName));
		Element rootElement = document.getDocumentElement();
		NodeList childNodes =  rootElement.getElementsByTagName("*");

		Actual_document = db.parse(new File(Actual_FileName));
		Element Actual_rootElement = Actual_document.getDocumentElement();
		NodeList Actual_childNodes =  Actual_rootElement.getElementsByTagName("*");

		System.out.println("details---"+childNodes.getLength());
		for (int ElementTagID = 1; ElementTagID < childNodes.getLength(); ElementTagID++) 
		{
			Element Elementdetails = (Element) childNodes.item(ElementTagID);
			Element Actual_Elementdetails = (Element) Actual_childNodes.item(ElementTagID);

			int childNodeCount = Elementdetails.getChildNodes().getLength();
			if(childNodeCount == 0 ||childNodeCount == 1 )
			{
				if(!xmldetails.contains(Elementdetails.getNodeName()))
				{
					if(!Elementdetails.getTextContent().equals(Actual_Elementdetails.getTextContent()))
					{
						System.out.println("COUNT----"+ElementTagID+"****PARENT NODE***:-"+Elementdetails.getParentNode().getNodeName()+">>"+Elementdetails.getNodeName());
						System.out.println("COUNT----"+ElementTagID+"****NODENAME***"+Elementdetails.getNodeName()+"--TEXTVALUE--"+Elementdetails.getTextContent());
						System.out.println("COUNT----"+ElementTagID+"****ACTUAL NODENAME***"+Actual_Elementdetails.getNodeName()+"--TEXTVALUE--"+Actual_Elementdetails.getTextContent());
						GenericMethods.assertTwoValues(Actual_Elementdetails.getTextContent(), Elementdetails.getTextContent(), "Verfying the XML Values Expected Node:"+Elementdetails.getNodeName()+", and Actual Node:"+Actual_Elementdetails.getNodeName()+"", ScenarioDetailsHashMap);
						System.out.println("-------------------------------------------------------------");
					}
				}
			}
		}
	}
	/**
	 * @Description: Below  method is to download/Copy the file from FTP Location to Local system.
	 * @param HostName : Provide HostName (Ex:- "10.200.35.11")
	 * @param FileDir : Provide File Directory in the FTP location(Ex:- "kf/outbound/inttra/si/in/")
	 * @param Filename : Provide the file name need to download from FTP location(Ex:- "680577_")
	 * @param DestinationPath : Provide the path where the file from FTP location is to be copied(Ex:- "D:\Sandeep\KFMigration\KF_March13")
	 * @param uName : Provide UserName to connect FTP location(Ex:- "kfftp")
	 * @param password : Provide Password to connect FTP location (Ex:- "aCmgRZ1%2Ycf")
	 * @Author: Sandeep Jain
	 * @Date : April 20th 2015
	 * 
	 */
	public static void CopyFileFromFtp(String HostName,String FileDir,String Filename,String DestinationPath,String uName,String password) throws SocketException, IOException
	{		
		//Ex: getAllFileFromFtp("10.200.35.11","kf/outbound/inttra/si/in/", "680577_",System.getProperty("user.dir")+"\\INTTRA\\ScenarioFiles\\","kfftp", "aCmgRZ1%2Ycf");
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(HostName);
		ftpClient.login(uName, password);
		ftpClient.enterLocalPassiveMode();
		String[] files = ftpClient.listNames(FileDir);
		GenericMethods.pauseExecution(5000);
		if (files != null && files.length > 0) {
			for (String FileDetails: files) 
			{
				if(FileDetails.contains(Filename))
				{
					String[] fileNames=FileDetails.split("/");
					System.out.println(DestinationPath);
					File RemoteFile = new File(DestinationPath+fileNames[fileNames.length-1]);
					OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(RemoteFile));
					ftpClient.retrieveFile(FileDetails, outputStream);
					outputStream.close();
				}
			}
		}
		ftpClient.logout();
		ftpClient.disconnect();
	}

	/**
	 * 
	 * @param FilePath : Provide the path where exactly file exists.
	 * @param ExpectedFilename: Provide the filename details.
	 * @return: It will return the complete path of file .
	 * @author Sandeep Jain
	 * @Date :April 21 2015
	 */
	public static String getFilePath(String FilePath,String ExpectedFilename)
	{
		String path = null ;
		File folder= new File(FilePath);
		File[] filelist=folder.listFiles();

		for(int FileDetails=0;FileDetails<filelist.length;FileDetails++)
		{

			if(filelist[FileDetails].getName().contains(ExpectedFilename))
			{
				path = FilePath+filelist[FileDetails].getName();
				System.out.println(path);	
			}
		}
		return path;
	}

	/**
	 * @param HostName : Provide HostName (Ex:- "10.200.35.11")
	 * @param UserName : Provide UserName to connect FTP location(Ex:- "kfftp")
	 * @param Password : Provide Password to connect FTP location (Ex:- "aCmgRZ1%2Ycf")
	 * @param UploadFilePath : Provide upload file path
	 * @param FTPFilePath : Provide FTP Location path with File Name.
	 * @Author: Sandeep Jain
	 * @Date : April 22nd 2015
	 */
	public static void UploadFileToFTP(String HostName,String UserName,String Password,String UploadFilePath,String FTPFilePath)
	{
		//Ex:- UploadFileToFTP("10.200.35.11", "kfftp", "aCmgRZ1%2Ycf","D:\\Sandeep\\KFMigration\\KF_March13\\KF_4.0_Regression_May_Release_April15\\INTTRA\\ScenarioFiles\\carrierstatus_Jet.xml","kf/inbound/inttra/import/acknowledgment/"+"carrierstatus_Je121212t.xml");
		FTPClient client = new FTPClient();
		FileInputStream fis = null;
		try {
			client.connect(HostName);
			client.login(UserName, Password);
			client.enterLocalPassiveMode();
			fis = new FileInputStream(UploadFilePath);
			client.storeFile(FTPFilePath, fis);
			client.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if (fis != null) {
					fis.close();
				}
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	//Sandeep- Below method is to get Date in specified format
	public static String GetTestdataDate(String Date)
	{	
		String FormattedDate="";
		try 
		{
			SimpleDateFormat sdf = new SimpleDateFormat(GenericMethods.getPropertyValue("dateFormate", System.getProperty("user.dir")+"\\Configurations\\base.properties"));
			Date parse;

			parse = sdf.parse(Date);
			Calendar c = Calendar.getInstance();
			c.setTime(parse);
			int actualmonth = c.get(Calendar.MONTH)+1;
			String parse1 ="";
			if(actualmonth < 10) 
			{
				parse1 = "0" + actualmonth;
			}
			else
			{
				parse1=actualmonth+"";
			}
			
			int actualDate = c.get(Calendar.DATE);
			String modifiedDate = "";
			if(actualDate<10)
			{
				modifiedDate = "0" + actualDate;
			}
			else
			{
				modifiedDate=actualDate+"";
			}
				
			System.out.println(c.get(Calendar.YEAR)+""+parse1+modifiedDate+"");
			FormattedDate = c.get(Calendar.YEAR)+""+parse1+modifiedDate;

		} catch (ParseException e) {e.printStackTrace();
		}
		return FormattedDate;
	}

	/**
	 * @param HostName : Provide HostName (Ex:- "10.200.35.11")
	 * @param FileDir : Folder Path in FTP Location. (Ex:- "kf/inbound/inttra/import/status/")
	 * @param Filename : File name in Folder Path of FTP Location. (Ex:- "2015-04-24_03-51-56_carrierstatus_Jet_AE")
	 * @param UserName : Provide UserName to connect FTP location(Ex:- "kfftp")
	 * @param Password : Provide Password to connect FTP location (Ex:- "aCmgRZ1%2Ycf")
	 * @param timeoutInMin
	 * @Author: Sandeep Jain
	 * 
	 */
	public static void FTP_RecordAvailability(String HostName,String FileDir,String Filename,String UserName,String Password,long timeoutInMin) throws SocketException, IOException, InterruptedException
	{
		//Ex:- FTP_RecordAvailability("10.200.35.11","kf/inbound/inttra/import/status/", "Aperak_","kfftp", "aCmgRZ1%2Ycf",5);
		boolean result = true;
		long TimeInMilliSec = (timeoutInMin * 60)*1000;
		long TimeSliceInMilliSec = 3000;
		long ElapsedTimeInMilliSec = 0;
		do
		{
			Thread.sleep(TimeSliceInMilliSec);
			result = FTP_RecordDetails(HostName,FileDir, Filename,UserName, Password);
			ElapsedTimeInMilliSec = ElapsedTimeInMilliSec+TimeSliceInMilliSec;
			/*System.out.println("TimeSliceInMilliSec---"+TimeSliceInMilliSec);
			System.out.println("ElapsedTimeInMilliSec---"+ElapsedTimeInMilliSec);*/
		}
		while ((result == true) && (ElapsedTimeInMilliSec < TimeInMilliSec));
		if (ElapsedTimeInMilliSec >= TimeInMilliSec || result == false) 
		{
			System.out.println("Record is Processed");
		}

	}
	
	/**
	 * @param HostName : Provide HostName (Ex:- "10.200.35.11")
	 * @param FileDir : Folder Path in FTP Location. (Ex:- "kf/inbound/inttra/import/status/")
	 * @param Filename : File name in Folder Path of FTP Location. (Ex:- "2015-04-24_03-51-56_carrierstatus_Jet_AE")
	 * @param UserName : Provide UserName to connect FTP location(Ex:- "kfftp")
	 * @param Password : Provide Password to connect FTP location (Ex:- "aCmgRZ1%2Ycf")
	 * @return
	 */
	public static boolean FTP_RecordDetails(String HostName,String FileDir,String Filename,String Username,String Password) throws SocketException, IOException
	{
		boolean recordavailability= false;
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(HostName);
		ftpClient.login(Username, Password);
		String[] files = ftpClient.listNames(FileDir);
		if (files != null && files.length > 0) {
			for (String FileDetails: files) 
			{
				/*System.out.println("filed****"+FileDetails);
				System.out.println("Filename****"+Filename);*/
				if(FileDetails.contains(Filename))
				{
					String[] fileNames=FileDetails.split("/");
					System.out.println("-----"+fileNames[fileNames.length-1]);
					recordavailability = true;
					break;
				}
			}
		}
		ftpClient.logout();
		ftpClient.disconnect();
		return recordavailability;
	}


	public static void UpdateXMLValues_ParentNodeValue(String filename,String AttributeNode,String AttributeValue,String NodeName, String NodeValue) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{

		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document ;
		dbf = DocumentBuilderFactory.newInstance();
		db   = dbf.newDocumentBuilder();
		document = db.parse(new File(filename));
		Element rootElement = document.getDocumentElement();
		NodeList childNodes =  rootElement.getElementsByTagName("*");
		System.out.println("------------------------------------------------------------------");
		System.out.println("details---"+childNodes.getLength());
		for (int ElementTagID = 1; ElementTagID < childNodes.getLength(); ElementTagID++) 
		{
			Element Elementdetails = (Element) childNodes.item(ElementTagID);

			int childnodecount = Elementdetails.getChildNodes().getLength();
			if(childnodecount == 0 ||childnodecount == 1 )
			{
				if(Elementdetails.getNodeName().equals(NodeName))
				{
					System.out.println("P Elementdetails.getNodeName()="+Elementdetails.getNodeName());
					System.out.println("P NodeName="+NodeName);

					NamedNodeMap attrs = Elementdetails.getParentNode().getAttributes();  

					for (int AttributeNo = 0; AttributeNo < Elementdetails.getParentNode().getAttributes().getLength(); AttributeNo++) 
					{
						Attr attribute = (Attr)attrs.item(AttributeNo); 
						System.out.println("P attribute name----"+ attribute.getName());
						System.out.println("P attribute value----"+ attribute.getValue());
						if(attribute.getName().equals(AttributeNode) && attribute.getValue().equals(AttributeValue))
						{
							Elementdetails.setTextContent(NodeValue);
						}
					}
					System.out.println("------------------------------------------------------------------");
				}
			}

		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filename));
		transformer.transform(domSource, streamResult);
	}

	/**
	 * @param filename : Provide filepath where xml exist
	 * @param NodeName : Provide NodeName on wchich tag you want to update values
	 * @param NodeValue : Provide what value you need to update 
	 * @param PrevNodeValue : Provide what previous value was present in respective tag
	 * @return
	 */
	//Sangeeta - Below method is to update xml value directly to node name
	public static void UpdateXMLduplicateTagValues(String filename, String NodeName, String PrevNodeValue, String NodeValue) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{

		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document ;
		dbf = DocumentBuilderFactory.newInstance();
		db   = dbf.newDocumentBuilder();
		document = db.parse(new File(filename));
		Element rootElement = document.getDocumentElement();
		NodeList childNodes =  rootElement.getElementsByTagName("*");
		System.out.println("details---"+childNodes.getLength());
		for (int ElementTagID = 1; ElementTagID < childNodes.getLength(); ElementTagID++) 
		{

			Element Elementdetails = (Element) childNodes.item(ElementTagID);
			//System.out.println(Elementdetails.getNodeName());
			if(Elementdetails.getNodeName().equals(NodeName)&& Elementdetails.getTextContent().equalsIgnoreCase(PrevNodeValue))
			{
				Elementdetails.setTextContent(NodeValue);
				//System.out.println("value updated");
				break;
			}
		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filename));
		transformer.transform(domSource, streamResult);
	}



	/**
	 * @param filename : Provide filepath where xml exist
	 * @param ParentNodeName : Provide Parent Node Name of which tag you want to update values
	 * @param ParentAttributeNode : Provide Parent Attribute Node name of above given parent node 
	 * @param ParentAttributeValue : Provide Parent Attribute Value of above given parent node
	 * @param ChildNodeName : Provide Child Node Name of which tag you want to update values
	 * @param ChildAttributeNode : Provide Child Attribute Node name of above given parent node 
	 * @param ParentAttributeValue : Provide Child Attribute Value of above given parent node  
	 * @return
	 */
	//Sangeeta - Below method is to update xml value directly to node name
	public static String getXMLValues_ParentNodeAttributeValue(String filename,String ParentNodeName,String ParentAttributeNode,String ParentAttributeValue,String ChildNodeName, String ChildAttributeNode, String ChildAttributeValue) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{

		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document ;
		dbf = DocumentBuilderFactory.newInstance();
		db   = dbf.newDocumentBuilder();
		document = db.parse(new File(filename));
		Element rootElement = document.getDocumentElement();
		NodeList childNodes =  rootElement.getElementsByTagName("*");
		String Textcontent = null;
		//System.out.println("------------------------------------------------------------------");
		//System.out.println("details---"+childNodes.getLength());
		boolean ParentAttributeDetails = false;
		boolean ChildAttributeDetails = false;
		for (int ElementTagID = 1; ElementTagID < childNodes.getLength(); ElementTagID++) 
		{
			Element Elementdetails = (Element) childNodes.item(ElementTagID);
			NamedNodeMap attrs = Elementdetails.getAttributes();  
			for (int AttributeNo = 0; AttributeNo < Elementdetails.getAttributes().getLength(); AttributeNo++) 
			{
				Attr attribute = (Attr)attrs.item(AttributeNo); 

				if(attribute.getName().equals(ParentAttributeNode) && attribute.getValue().equals(ParentAttributeValue))
				{
					ParentAttributeDetails =true;
				}

				if(attribute.getName().equals(ChildAttributeNode) && attribute.getValue().equals(ChildAttributeValue))
				{
					ChildAttributeDetails =true;
				}

				if((ParentAttributeDetails) && (ChildAttributeDetails))
				{

					//System.out.println(Elementdetails.getTextContent());
					Textcontent = Elementdetails.getTextContent();
					ParentAttributeDetails = false;
					ChildAttributeDetails = false;
				}
			}
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filename));
		transformer.transform(domSource, streamResult);
		return Textcontent;
	}

	/**
	 * @Description: Below  method is to download/Copy the file from FTP Location to Local system.
	 * @param HostName : Provide HostName (Ex:- "10.200.35.11")
	 * @param FileDir : Provide File Directory in the FTP location(Ex:- "kf/outbound/inttra/si/in/")
	 * @param Filename : Provide the file name need to download from FTP location(Ex:- "680577_")
	 * @param DestinationPath : Provide the path where the file from FTP location is to be copied(Ex:- "D:\Sandeep\KFMigration\KF_March13")
	 * @param uName : Provide UserName to connect FTP location(Ex:- "kfftp")
	 * @param password : Provide Password to connect FTP location (Ex:- "aCmgRZ1%2Ycf")
	 * @Author: Sangeeta Mohanty
	 * @Date : August 10th 2015
	 * 
	 */
	public static String GettingXMLFileNamewithPathFromFTP(String HostName,String FileDir,String Filename,String DestinationPath,String uName,String password) throws SocketException, IOException
	{		
		//Ex: getAllFileFromFtp("10.200.35.11","kf/outbound/inttra/si/in/", "680577_",System.getProperty("user.dir")+"\\INTTRA\\ScenarioFiles\\","kfftp", "aCmgRZ1%2Ycf");
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(HostName);
		ftpClient.login(uName, password);
		ftpClient.enterLocalPassiveMode();
		String[] files = ftpClient.listNames(FileDir);
		String XMLFileNamewithPath = null;
		GenericMethods.pauseExecution(7000);
		Robot rb = null;
		try {
			rb = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rb.keyPress(KeyEvent.VK_F5);
		rb.keyPress(KeyEvent.VK_F5);
		rb.keyRelease(KeyEvent.VK_F5);
		rb.keyRelease(KeyEvent.VK_F5);
		
		GenericMethods.pauseExecution(7000);
		if (files != null && files.length > 0) {
			for (String FileDetails: files) 
			{
				/*System.out.println("before FileDetails="+FileDetails);
				System.out.println("before files="+files);
				System.out.println("Before Filename="+Filename);*/
				if(FileDetails.contains(Filename))
				{
				
					
					String[] fileNames=FileDetails.split("/");
					System.out.println("after fileNames.length="+fileNames[fileNames.length-1]);
					File RemoteFile = new File(DestinationPath+fileNames[fileNames.length-1]);
					XMLFileNamewithPath = DestinationPath+fileNames[fileNames.length-1];
					System.out.println("after DestinationPath+fileNames[fileNames.length=]"+XMLFileNamewithPath);
					OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(RemoteFile));
					ftpClient.retrieveFile(FileDetails, outputStream);
					outputStream.close();
					
					
					
				}
			}
		}
		ftpClient.logout();
		ftpClient.disconnect();
		return XMLFileNamewithPath;
	}


	
	
	public static String copyUpdatedFileFromFTP(String HostName,String FileDir,String Filename,String DestinationPath,String uName,String password) throws SocketException, IOException
	{		
		//Ex: getAllFileFromFtp("10.200.35.11","kf/outbound/inttra/si/in/", "680577_",System.getProperty("user.dir")+"\\INTTRA\\ScenarioFiles\\","kfftp", "aCmgRZ1%2Ycf");
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect(HostName);
		ftpClient.login(uName, password);
		ftpClient.enterLocalPassiveMode();
		String[] files = ftpClient.listNames(FileDir);
		String UpdatedXMLFileName = null;
		GenericMethods.pauseExecution(35000);
		if (files != null && files.length > 0) {
			for (String FileDetails: files) 
			{
                 //2015-08-11_06-15-34_ETJournalController_Updated.fsc.xml
				//2015-08-11_06-15-34_753070_201508110838040205.xml
				System.out.println("b4 FileDetails="+FileDetails);
				System.out.println("b4 Filename="+Filename);
				if(FileDetails.contains(Filename))
				{
					System.out.println("After Filename="+Filename);
					System.out.println("After FileDetails="+FileDetails);
					String[] fileNames=FileDetails.split("/");
					//System.out.println("fileNames.length="+fileNames[fileNames.length-1]);
					UpdatedXMLFileName = DestinationPath+fileNames[fileNames.length-1];
					//System.out.println("UpdatedXMLFileName="+UpdatedXMLFileName);
					File RemoteFile = new File(DestinationPath+fileNames[fileNames.length-1]);
					//System.out.println("DestinationPath+fileNames[fileNames.length-1]"+DestinationPath+fileNames[fileNames.length-1]);
					OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(RemoteFile));
					ftpClient.retrieveFile(FileDetails, outputStream);
					outputStream.close();
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		ftpClient.logout();
		ftpClient.disconnect();
		return UpdatedXMLFileName;
	}

	public static void CopyXMLFiles(String OriginPath,String DestinationPath) throws IOException
	{	

		InputStream inStream = null;
		OutputStream outStream = null;
		try{
			File originfile =new File(OriginPath);
			File destfile =new File(DestinationPath);
			inStream = new FileInputStream(originfile);
			outStream = new FileOutputStream(destfile);
			byte[] buffer = new byte[1024];
			int length;
			//copy the file content in bytes 
			while ((length = inStream.read(buffer)) > 0){
				outStream.write(buffer, 0, length);
			}

			inStream.close();
			outStream.close();

			System.out.println("File is copied successful!");

		}catch(IOException e){
			e.printStackTrace();
		}

	}

	/**
	 * @Description: Below  method is to getting tag value  
	 * @param Filename : Provide the xml path with file name
	 * @param ParentAttributeNode : Provide the ParentAttributeNode name of required tag
	 * @param ParentAttributeValue : Provide the ParentAttributeNode value of required tag, if not available then keep "" in parameter
	 * @param ChildNodeName :  Provide the ChildNode Name of required tag , if not available then keep "" in parameter
	 * @param SubChildNodeName : Provide the Sub ChildNode Name of required tag, if not available then keep "" in parameter
	 * @param SubChildAttributeValue : Provide the Sub Child Attribute Value of required tag, if not available then keep "" in parameter
	 * @Author: Sangeeta Mohanty
	 * @Date : August 17th 2015
	 * 
	 */
	public static String GetXMLValuesFromInttraUpdatedInputFileWithANDOperation(String filename,String ParentAttributeNode,String ParentAttributeValue,String ChildNodeName, String SubChildNodeName, String SubChildAttributeValue) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document ;
		dbf = DocumentBuilderFactory.newInstance();
		db   = dbf.newDocumentBuilder();
		document = db.parse(new File(filename));
		Element rootElement = document.getDocumentElement();
		NodeList childNodes =  rootElement.getElementsByTagName("*");
		String TagTextValue = null;
		for (int ElementTagID = 1; ElementTagID < childNodes.getLength(); ElementTagID++) 
		{
			Element Elementdetails = (Element) childNodes.item(ElementTagID);
			//System.out.println(Elementdetails.getNodeName());
			NamedNodeMap attrs = Elementdetails.getAttributes();  
			for (int AttributeNo = 0; AttributeNo < Elementdetails.getAttributes().getLength(); AttributeNo++) 
			{
				Attr attribute = (Attr)attrs.item(AttributeNo); 

				if(attribute.getName().equals(ParentAttributeNode) && attribute.getValue().equals(ParentAttributeValue))
				{
					for (int i = 0; i <Elementdetails.getChildNodes().getLength(); i++) {
						//System.out.println(Elementdetails.getChildNodes().item(i).getNodeName());

						if(Elementdetails.getChildNodes().item(i).getNodeName().equalsIgnoreCase(ChildNodeName))
						{
							//System.out.println(Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getNodeName());
							if(Elementdetails.getChildNodes().item(i).hasChildNodes())
							{
								if(Elementdetails.getChildNodes().item(i).getChildNodes().getLength()==1){
									if(Elementdetails.getChildNodes().item(i).getNodeName().equalsIgnoreCase(ChildNodeName))
									{
									System.out.println(Elementdetails.getChildNodes().item(i).getTextContent());
									TagTextValue = Elementdetails.getChildNodes().item(i).getTextContent();
								}
								}
								else{
								for (int j = 0; j < Elementdetails.getChildNodes().item(i).getChildNodes().getLength(); j++) {
									if(!Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getNodeName().equalsIgnoreCase("#text")){
									NamedNodeMap nm = Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getAttributes();
									if(Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getNodeName().equalsIgnoreCase(SubChildNodeName)&&nm.getLength()>0){
									for (int k = 0; k < nm.getLength(); k++) {
										Attr attribute1 = (Attr)nm.item(k);
									if(attribute1.getValue().equalsIgnoreCase(SubChildAttributeValue))
									{
										System.out.println(Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getTextContent());
										TagTextValue = Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getTextContent();
									}
										
									}	
									//break;
									}else if(Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getNodeName().equalsIgnoreCase(SubChildNodeName)){
										System.out.println(Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getTextContent());
										TagTextValue = Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getTextContent();
									}
									}
								}
							}
							}
							else
							{
								System.out.println(Elementdetails.getChildNodes().item(i).getTextContent());
								TagTextValue = Elementdetails.getChildNodes().item(i).getTextContent();
							}
						}
					}
				}


			}
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filename));
		transformer.transform(domSource, streamResult);
		return TagTextValue;
	}
	
	

	/**
	 * @Description: Below  method is to getting tag value  
	 * @param Filename : Provide the xml path with file name
	 * @param ParentAttributeNode : Provide the ParentAttributeNode name of required tag
	 * @param ParentAttributeValue : Provide the ParentAttributeNode value of required tag, if not available then keep "" in parameter
	 * @param ChildNodeName :  Provide the ChildNode Name of required tag , if not available then keep "" in parameter
	 * @param SubChildNodeName : Provide the Sub ChildNode Name of required tag, if not available then keep "" in parameter
	 * @param SubChildAttributeValue : Provide the Sub Child Attribute Value of required tag, if not available then keep "" in parameter
	 * @Author: Sangeeta Mohanty
	 * @Date : August 17th 2015
	 * 
	 */
	public static String GetXMLValuesFromInttraUpdatedInputFileWithOROperation(String filename,String ParentAttributeNode,String ParentAttributeValue,String ChildNodeName, String SubChildNodeName, String SubChildAttributeValue) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document ;
		dbf = DocumentBuilderFactory.newInstance();
		db   = dbf.newDocumentBuilder();
		document = db.parse(new File(filename));
		Element rootElement = document.getDocumentElement();
		NodeList childNodes =  rootElement.getElementsByTagName("*");
		String TagTextValue = null;
		for (int ElementTagID = 1; ElementTagID < childNodes.getLength(); ElementTagID++) 
		{
			Element Elementdetails = (Element) childNodes.item(ElementTagID);
			//System.out.println(Elementdetails.getNodeName());
			NamedNodeMap attrs = Elementdetails.getAttributes();  
			for (int AttributeNo = 0; AttributeNo < Elementdetails.getAttributes().getLength(); AttributeNo++) 
			{
				Attr attribute = (Attr)attrs.item(AttributeNo); 

				if(attribute.getName().equals(ParentAttributeNode) || attribute.getValue().equals(ParentAttributeValue))
				{
					for (int i = 0; i <Elementdetails.getChildNodes().getLength(); i++) {
						//System.out.println(Elementdetails.getChildNodes().item(i).getNodeName());

						if(Elementdetails.getChildNodes().item(i).getNodeName().equalsIgnoreCase(ChildNodeName))
						{
							//System.out.println(Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getNodeName());
							if(Elementdetails.getChildNodes().item(i).hasChildNodes())
							{
								if(Elementdetails.getChildNodes().item(i).getChildNodes().getLength()==1){
									if(Elementdetails.getChildNodes().item(i).getNodeName().equalsIgnoreCase(ChildNodeName))
									{
									System.out.println(Elementdetails.getChildNodes().item(i).getTextContent());
									TagTextValue = Elementdetails.getChildNodes().item(i).getTextContent();
								}
								}
								else{
								for (int j = 0; j < Elementdetails.getChildNodes().item(i).getChildNodes().getLength(); j++) {
									if(!Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getNodeName().equalsIgnoreCase("#text")){
									NamedNodeMap nm = Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getAttributes();
									if(Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getNodeName().equalsIgnoreCase(SubChildNodeName)&&nm.getLength()>0){
									for (int k = 0; k < nm.getLength(); k++) {
										Attr attribute1 = (Attr)nm.item(k);
									if(attribute1.getValue().equalsIgnoreCase(SubChildAttributeValue))
									{
										System.out.println(Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getTextContent());
										TagTextValue = Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getTextContent();
									}
										
									}	
									//break;
									}else if(Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getNodeName().equalsIgnoreCase(SubChildNodeName)){
										System.out.println(Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getTextContent());
										TagTextValue = Elementdetails.getChildNodes().item(i).getChildNodes().item(j).getTextContent();
									}
									}
								}
							}
							}
							else
							{
								System.out.println(Elementdetails.getChildNodes().item(i).getTextContent());
								TagTextValue = Elementdetails.getChildNodes().item(i).getTextContent();
							}
						}
					}
				}


			}
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filename));
		transformer.transform(domSource, streamResult);
		return TagTextValue;
	}
	
	/**
	 * @Description: Below  method is to getting tag value  
	 * @param Filename : Provide the xml path with file name
	 * @param NodeName : Provide the Node name of required tag
	 * @param AttributeName : Provide the Attribute Name of required tag
	 * @param AttributeValue :  Provide the Attribute Value of required tag
	 * @param SheetName : Provide the SheetName of Excel sheet for comparing
	 * @param SubChildAttributeValue : Provide the Sub Child Attribute Value of required tag, if not available then keep "" in parameter
	 * @param UpdatedColumnName : Provide ColumnName of input tag value depend on condition
	 * @param ColumnName : Provide ColumnName of output tag value depend on condition
	 * @param LengthValidationNo : Provide the Length Validation No(Example 35) for checking Length Validation No of respective tag value 
	 * @Author: Sangeeta Mohanty
	 * @Date : August 17th 2015
	 * @Example :
	 * input tag:
	 *  <GoodsDescription>33 ( THIRTY THREE PALLETS ) ONLY CONTAINING: BRAKE PADS FOR MOTOR VEHICLE INV NO. : 1501017516 DT 30.06.2015 SB NO. : 1501299 DT 30.06.2015 IEC NO. : 0502015373 HS CODE NO. : 8708.33.00 FREIGHT COLLECT THC PREPAID</GoodsDescription>
	 * Output tag:
     <PackageDetailComments CommentType="GoodsDescription">33 ( THIRTY THREE PALLETS ) ONLY CO</PackageDetailComments>
     <PackageDetailComments CommentType="GoodsDescription">NTAINING: BRAKE PADS FOR MOTOR VEHI</PackageDetailComments>
     <PackageDetailComments CommentType="GoodsDescription">CLE INV NO. : 1501017516 DT 30.06.2</PackageDetailComments>
     <PackageDetailComments CommentType="GoodsDescription">015 SB NO. : 1501299 DT 30.06.2015</PackageDetailComments>
     <PackageDetailComments CommentType="GoodsDescription">IEC NO. : 0502015373 HS CODE NO. :</PackageDetailComments>
     <PackageDetailComments CommentType="GoodsDescription">8708.33.00 FREIGHT COLLECT THC PREP</PackageDetailComments>
     <PackageDetailComments CommentType="GoodsDescription">AID</PackageDetailComments>

     GenericMethods.getvalue(INTTRAUpdatedOutputMSGPath, "PackageDetailComments", "CommentType", "GoodsDescription", "XMLValidationSheet","GoodsDescription", "PackageDetailComments", 35 , ScenarioDetailsHashMap, RowNo);
	 * 
	 */
	public static String getvalueFromDirectNode_AttributeNode_AttrubuteValue(String filename,String NodeName, String AttributeName,String AttributeValue, String SheetName,String UpdatedColumnName, String ColumnName,int LengthValidationNo,HashMap<String, String> ScenarioDetailsHashMap,int RowNo) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document ;
		dbf = DocumentBuilderFactory.newInstance();
		db   = dbf.newDocumentBuilder();
		document = db.parse(new File(filename));
		Element rootElement = document.getDocumentElement();
		NodeList childNodes =  rootElement.getElementsByTagName("*");
		String ActualTagTextValue = null;
		int noOfGoddDesc=0;
		for (int ElementTagID = 1; ElementTagID < childNodes.getLength(); ElementTagID++) 
		{
			Element Elementdetails = (Element) childNodes.item(ElementTagID);
			//System.out.println(Elementdetails.getNodeName());
			if(Elementdetails.getNodeName().equalsIgnoreCase(NodeName))
			{
				NamedNodeMap attrs = Elementdetails.getAttributes();  
				for (int AttributeNo = 0; AttributeNo < Elementdetails.getAttributes().getLength(); AttributeNo++) 
				{
					Attr attribute = (Attr)attrs.item(AttributeNo);

					//System.out.println(attribute.getName());
					if(attribute.getName().equals(AttributeName) && attribute.getValue().equals(AttributeValue))
					{
						noOfGoddDesc++;
						System.out.println(Elementdetails.getTextContent());
						ActualTagTextValue = Elementdetails.getTextContent();
						System.out.println("No of Godds Desc : "+noOfGoddDesc);
						GenericMethods.assertDisplayTwoValues(ActualTagTextValue, ExcelUtils.getCellData(SheetName, RowNo, UpdatedColumnName, ScenarioDetailsHashMap), "Comparing output XML tag text value with input tag text value", ScenarioDetailsHashMap);
						GenericMethods.assertDisplayTwoValues(ActualTagTextValue.length()+"", (ExcelUtils.getCellData(SheetName, RowNo, UpdatedColumnName, ScenarioDetailsHashMap)).length()+"", "Comparing output XML tag text length value with input tag text length value", ScenarioDetailsHashMap);
						System.out.println("=================================================");
						GenericMethods.assertTwoforBooleanValues((ActualTagTextValue.length()<=LengthValidationNo)+"", ((ExcelUtils.getCellDataWithoutDataSet(SheetName, noOfGoddDesc, ColumnName, ScenarioDetailsHashMap).length()>=LengthValidationNo)||(ExcelUtils.getCellDataWithoutDataSet(SheetName, noOfGoddDesc, ColumnName, ScenarioDetailsHashMap).length()<=LengthValidationNo))+"", "Checking output XML tag text length value should not be exceed "+LengthValidationNo, ScenarioDetailsHashMap);
						System.out.println("=================================================");
					}
				}
			}
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filename));
		transformer.transform(domSource, streamResult);
		return ActualTagTextValue;
	}
	
	
	/**
	 * @Description: Below  method is to getting tag value  
	 * @param Filename : Provide the xml path with file name
	 * @param NodeName : Provide the Node name of required tag
	 * @param AttributeName : Provide the Attribute Name of required tag
	 * @param AttributeValue :  Provide the Attribute Value of required tag
	 * @param SheetName : Provide the SheetName of Excel sheet for comparing
	 * @param SubChildAttributeValue : Provide the Sub Child Attribute Value of required tag, if not available then keep "" in parameter
	 * @param UpdatedColumnName : Provide ColumnName of input tag value depend on condition
	 * @param ColumnName : Provide ColumnName of output tag value depend on condition
	 * @param LengthValidationNo : Provide the Length Validation No(Example 35) for checking Length Validation No of respective tag value 
	 * @Author: Sangeeta Mohanty
	 * @Date : August 17th 2015
	 * @Example :
	 * input tag:
	 * <SealNo1>278154</SealNo1>
       <SealNo2>D7033556</SealNo2>
	 * Output tag:
     <EquipmentSeal SealingParty="Carrier">278154 278154 2</EquipmentSeal>
     <EquipmentSeal SealingParty="Carrier">D7033556 D70335</EquipmentSeal>
               
     GenericMethods.getXMLTagvalueFromDirectNode_AttributeNode_AttrubuteValue(INTTRAUpdatedOutputMSGPath, "EquipmentSeal", "SealingParty", "Carrier", "XMLValidationSheet", "Carrier_SealingNumber", "Container_SealNo1", 15, ScenarioDetailsHashMap, RowNo);
	 * 
	 */
	public static String getXMLTagvalueFromDirectNode_AttributeNode_AttrubuteValue_WithValidation(String filename,String NodeName, String AttributeName,String AttributeValue, String SheetName,String UpdatedColumnName, String ColumnName,int LengthValidationNo,HashMap<String, String> ScenarioDetailsHashMap,int RowNo) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document ;
		dbf = DocumentBuilderFactory.newInstance();
		db   = dbf.newDocumentBuilder();
		document = db.parse(new File(filename));
		Element rootElement = document.getDocumentElement();
		NodeList childNodes =  rootElement.getElementsByTagName("*");
		String ActualTagTextValue = null;
		int noOfGoddDesc=0;
		for (int ElementTagID = 1; ElementTagID < childNodes.getLength(); ElementTagID++) 
		{
			Element Elementdetails = (Element) childNodes.item(ElementTagID);
			//System.out.println(Elementdetails.getNodeName());
			if(Elementdetails.getNodeName().equalsIgnoreCase(NodeName))
			{
				NamedNodeMap attrs = Elementdetails.getAttributes();  
				for (int AttributeNo = 0; AttributeNo < Elementdetails.getAttributes().getLength(); AttributeNo++) 
				{
					Attr attribute = (Attr)attrs.item(AttributeNo);

					//System.out.println(attribute.getName());
					if(attribute.getName().equals(AttributeName) && attribute.getValue().equals(AttributeValue))
					{
						noOfGoddDesc++;
						System.out.println(Elementdetails.getTextContent());
						ActualTagTextValue = Elementdetails.getTextContent();
						System.out.println("No of Godds Desc : "+noOfGoddDesc);
						GenericMethods.assertDisplayTwoValues(ActualTagTextValue, ExcelUtils.getCellDataWithoutDataSet(SheetName, noOfGoddDesc, UpdatedColumnName, ScenarioDetailsHashMap), "Comparing output XML tag text value with input tag text value", ScenarioDetailsHashMap);
						GenericMethods.assertDisplayTwoValues(ActualTagTextValue.length()+"", (ExcelUtils.getCellData(SheetName, RowNo, ColumnName, ScenarioDetailsHashMap)).length()+"", "Comparing output XML tag text length value with input tag text length value", ScenarioDetailsHashMap);
						System.out.println("=================================================");
						GenericMethods.assertTwoforBooleanValues((ActualTagTextValue.length()<=LengthValidationNo)+"", ((ExcelUtils.getCellDataWithoutDataSet(SheetName, noOfGoddDesc, ColumnName, ScenarioDetailsHashMap).length()>=LengthValidationNo)||(ExcelUtils.getCellDataWithoutDataSet(SheetName, noOfGoddDesc, ColumnName, ScenarioDetailsHashMap).length()<=LengthValidationNo))+"", "Checking output XML tag text length value should not be exceed "+LengthValidationNo, ScenarioDetailsHashMap);
						System.out.println("=================================================");
					}
				}
			}
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filename));
		transformer.transform(domSource, streamResult);
		return ActualTagTextValue;
	}
	
	
	public static String getTextContentFromDirectNode_AttributeNode_AttrubuteValue(String filename,String NodeName, String AttributeName,String AttributeValue) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document ;
		dbf = DocumentBuilderFactory.newInstance();
		db   = dbf.newDocumentBuilder();
		document = db.parse(new File(filename));
		Element rootElement = document.getDocumentElement();
		NodeList childNodes =  rootElement.getElementsByTagName("*");
		String ActualTagTextValue = null;
		int noOfGoddDesc=0;
		for (int ElementTagID = 1; ElementTagID < childNodes.getLength(); ElementTagID++) 
		{
			Element Elementdetails = (Element) childNodes.item(ElementTagID);
			//System.out.println(Elementdetails.getNodeName());
			if(Elementdetails.getNodeName().equalsIgnoreCase(NodeName))
			{
				NamedNodeMap attrs = Elementdetails.getAttributes();  
				for (int AttributeNo = 0; AttributeNo < Elementdetails.getAttributes().getLength(); AttributeNo++) 
				{
					Attr attribute = (Attr)attrs.item(AttributeNo);

					//System.out.println(attribute.getName());
					if(attribute.getName().equals(AttributeName) && attribute.getValue().equals(AttributeValue))
					{
						noOfGoddDesc++;
						System.out.println(Elementdetails.getTextContent());
						ActualTagTextValue = Elementdetails.getTextContent();
					}
				}
			}
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filename));
		transformer.transform(domSource, streamResult);
		return ActualTagTextValue;
	}
	
	/**
	 * Below method will compare two values.If Orginal not equal to expected then result will be pass
	 * @param originalText: actual Value to be compared.
	 * @param expectedText: Expected result value.
	 * @param desc: Purpose of  validating.
	 * @param ScenarioDetailsHashMap : Hashmap variable contains Scenario details.
	 * @author Pavan Bikumandla
	 * @Modified Description: Added Hashmap variable to serve parallel testing purpose. 
	 */
	public static void assertDisplayTwoValues(String originalText,String expectedText,String desc,
			HashMap<String, String> ScenarioDetailsHashMap)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(CyborgConstants.TIME_FORMAT);
		String startTime = sdf.format(cal.getTime());
		String resultValue=null;

		if(!originalText.equals(expectedText))
		{
			resultValue = "Pass";
			CyborgConstants.logger.info("Actual and expected values "+"( "+originalText+" , "+expectedText+" )are same.");
		}else{
			resultValue = "Pass";
			CyborgConstants.logger.warn("Actual and expected values "+"( "+originalText+" , "+expectedText+" )are different.");
		}

		Calendar cal1 = Calendar.getInstance();
		String endtime = sdf.format(cal1.getTime());
		String time = Time(startTime, endtime);
		HashMap<String, String> resultDetailsHashMap = new HashMap<String, String>();
		resultDetailsHashMap.put("ScenarioName", ScenarioDetailsHashMap.get("ScenarioName"));
		resultDetailsHashMap.put("SubScenarioNo", ScenarioDetailsHashMap.get("SubScenarioNo"));
		resultDetailsHashMap.put("DataSetNo", ScenarioDetailsHashMap.get("DataSetNo"));
		resultDetailsHashMap.put("FunctionName", ScenarioDetailsHashMap.get("FunctionName"));
		resultDetailsHashMap.put("ClassName", ScenarioDetailsHashMap.get("ClassName"));
		resultDetailsHashMap.put("ElementId", "DETAILS COMPARISION");
		resultDetailsHashMap.put("Description", desc);
		resultDetailsHashMap.put("ExpectedValue", expectedText);
		resultDetailsHashMap.put("ActualValue", originalText);
		resultDetailsHashMap.put("Status", resultValue);
		resultDetailsHashMap.put("Time", time);
		AssertionResults.setAssertionResult(driver,resultDetailsHashMap,null,null,ScenarioDetailsHashMap.get("BrowserType"));
		resultDetailsHashMap.clear();


	}
	/**
	 * Below method will compare two values.If Orginal not equal to expected then result will be pass
	 * @param originalText: actual Value to be compared.
	 * @param expectedText: Expected result value.
	 * @param desc: Purpose of  validating.
	 * @param ScenarioDetailsHashMap : Hashmap variable contains Scenario details.
	 * @author Pavan Bikumandla
	 * @Modified Description: Added Hashmap variable to serve parallel testing purpose. 
	 */
	public static void assertTwoforBooleanValues(String originalText,String expectedText,String desc,
			HashMap<String, String> ScenarioDetailsHashMap)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(CyborgConstants.TIME_FORMAT);
		String startTime = sdf.format(cal.getTime());
		String resultValue=null;

		if(!originalText.equals(expectedText))
		{
			if((originalText.equalsIgnoreCase("true"))&& (expectedText.equalsIgnoreCase("true")))
			{
			resultValue = "Pass";
			CyborgConstants.logger.info("Actual and expected values "+"( "+originalText+" , "+expectedText+" )are same.");
			}
			else
			{
				//when originalText = false and 
				resultValue = "Fail";
				CyborgConstants.logger.info("Actual and expected values "+"( "+originalText+" , "+expectedText+" )are same but Result got Failed");
			
			}
		}else{
			resultValue = "Pass";
			CyborgConstants.logger.warn("Actual and expected values "+"( "+originalText+" , "+expectedText+" )are different.");
		}

		Calendar cal1 = Calendar.getInstance();
		String endtime = sdf.format(cal1.getTime());
		String time = Time(startTime, endtime);
		HashMap<String, String> resultDetailsHashMap = new HashMap<String, String>();
		resultDetailsHashMap.put("ScenarioName", ScenarioDetailsHashMap.get("ScenarioName"));
		resultDetailsHashMap.put("SubScenarioNo", ScenarioDetailsHashMap.get("SubScenarioNo"));
		resultDetailsHashMap.put("DataSetNo", ScenarioDetailsHashMap.get("DataSetNo"));
		resultDetailsHashMap.put("FunctionName", ScenarioDetailsHashMap.get("FunctionName"));
		resultDetailsHashMap.put("ClassName", ScenarioDetailsHashMap.get("ClassName"));
		resultDetailsHashMap.put("ElementId", "DETAILS COMPARISION");
		resultDetailsHashMap.put("Description", desc);
		resultDetailsHashMap.put("ExpectedValue", expectedText);
		resultDetailsHashMap.put("ActualValue", originalText);
		resultDetailsHashMap.put("Status", resultValue);
		resultDetailsHashMap.put("Time", time);
		AssertionResults.setAssertionResult(driver,resultDetailsHashMap,null,null,ScenarioDetailsHashMap.get("BrowserType"));
		resultDetailsHashMap.clear();
	}
	
	public static void setAttributValue(String filename,String NodeName, String AttributeName,String attributeValueInput) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{

		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document ;
		dbf = DocumentBuilderFactory.newInstance();
		db   = dbf.newDocumentBuilder();
		document = db.parse(new File(filename));
		Element rootElement = document.getDocumentElement();
		NodeList childNodes =  rootElement.getElementsByTagName("*");
		int noOfGoddDesc=0;
		for (int ElementTagID = 1; ElementTagID < childNodes.getLength(); ElementTagID++) 
		{
			Element Elementdetails = (Element) childNodes.item(ElementTagID);
			//System.out.println(Elementdetails.getNodeName());
			if(Elementdetails.getNodeName().equalsIgnoreCase(NodeName))
			{
				NamedNodeMap attrs = Elementdetails.getAttributes();  
				for (int AttributeNo = 0; AttributeNo < Elementdetails.getAttributes().getLength(); AttributeNo++) 
				{
					System.out.println("AttributeNo------"+AttributeNo);
					Attr attribute = (Attr)attrs.item(AttributeNo);

					System.out.println(attribute.getName());
					if(attribute.getName().equals(AttributeName))
					{
						attribute.setValue(attributeValueInput);
						noOfGoddDesc++;
						
						System.out.println("No of Godds Description : "+noOfGoddDesc);
					}
				}
				
			}
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filename));
		transformer.transform(domSource, streamResult);
	}
	
	public static String getDirectNodeTextValue(String filename,String NodeName, String SheetName,String UpdatedColumnName,String ColumnName, int LengthValidationNo, HashMap<String, String> ScenarioDetailsHashMap,int RowNo) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{

		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document ;
		dbf = DocumentBuilderFactory.newInstance();
		db   = dbf.newDocumentBuilder();
		document = db.parse(new File(filename));
		Element rootElement = document.getDocumentElement();
		NodeList childNodes =  rootElement.getElementsByTagName("*");
		String ActualTagTextValue = null;
		int noOfGoddDesc=0;
		for (int ElementTagID = 1; ElementTagID < childNodes.getLength(); ElementTagID++) 
		{
			Element Elementdetails = (Element) childNodes.item(ElementTagID);
			//System.out.println(Elementdetails.getNodeName());
			if(Elementdetails.getNodeName().equalsIgnoreCase(NodeName))
			{
				System.out.println("------------------"+Elementdetails.getTextContent());
				ActualTagTextValue = Elementdetails.getTextContent();
				System.out.println("No of Godds Desc : "+noOfGoddDesc);
				GenericMethods.assertDisplayTwoValues(ActualTagTextValue, ExcelUtils.getCellData(SheetName, RowNo, UpdatedColumnName, ScenarioDetailsHashMap), "Comparing output XML tag text value with input tag text value", ScenarioDetailsHashMap);
				GenericMethods.assertDisplayTwoValues(ActualTagTextValue.length()+"", (ExcelUtils.getCellData(SheetName, RowNo, UpdatedColumnName, ScenarioDetailsHashMap)).length()+"", "Comparing output XML tag text length value with input tag text length value", ScenarioDetailsHashMap);
				System.out.println("=================================================");
				GenericMethods.assertTwoforBooleanValues((ActualTagTextValue.length()<=LengthValidationNo)+"", ((ExcelUtils.getCellDataWithoutDataSet(SheetName, noOfGoddDesc, ColumnName, ScenarioDetailsHashMap).length()>=LengthValidationNo)||(ExcelUtils.getCellDataWithoutDataSet(SheetName, noOfGoddDesc, ColumnName, ScenarioDetailsHashMap).length()<=LengthValidationNo))+"", "Checking output XML tag text length value should not be exceed "+LengthValidationNo, ScenarioDetailsHashMap);
				System.out.println("=================================================");
				
			}
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filename));
		transformer.transform(domSource, streamResult);
		return ActualTagTextValue;
	}


	public static String getAttributValue(String filename,String NodeName, String AttributeName, String SheetName,String ColumnName,HashMap<String, String> ScenarioDetailsHashMap,int RowNo, String validationDescription) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{

		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document ;
		dbf = DocumentBuilderFactory.newInstance();
		db   = dbf.newDocumentBuilder();
		document = db.parse(new File(filename));
		Element rootElement = document.getDocumentElement();
		NodeList childNodes =  rootElement.getElementsByTagName("*");
		String ActualAttributeValue = null;
		int noOfGoddDesc=0;
		for (int ElementTagID = 1; ElementTagID < childNodes.getLength(); ElementTagID++) 
		{
			Element Elementdetails = (Element) childNodes.item(ElementTagID);
			//System.out.println(Elementdetails.getNodeName());
			if(Elementdetails.getNodeName().equalsIgnoreCase(NodeName))
			{
				NamedNodeMap attrs = Elementdetails.getAttributes();  
				for (int AttributeNo = 0; AttributeNo < Elementdetails.getAttributes().getLength(); AttributeNo++) 
				{
					//System.out.println("AttributeNo------"+AttributeNo);
					Attr attribute = (Attr)attrs.item(AttributeNo);

					//System.out.println(attribute.getName());
					if(attribute.getName().equals(AttributeName))
					{
						ActualAttributeValue = attribute.getValue();
						System.out.println("**************"+ActualAttributeValue);
						noOfGoddDesc++;
						System.out.println("No of Godds Description : "+noOfGoddDesc);
						GenericMethods.assertTwoValues(ActualAttributeValue, ExcelUtils.getCellDataWithoutDataSet(SheetName, noOfGoddDesc, ColumnName, ScenarioDetailsHashMap), validationDescription, ScenarioDetailsHashMap);
					}
				}
				
			}
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filename));
		transformer.transform(domSource, streamResult);
		return ActualAttributeValue;
	}
	
	public static String GetXMLValues_WithoutAttribute(String filename, String NodeName) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{

		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		Document document ;
		dbf = DocumentBuilderFactory.newInstance();
		db   = dbf.newDocumentBuilder();
		document = db.parse(new File(filename));
		Element rootElement = document.getDocumentElement();
		NodeList childNodes =  rootElement.getElementsByTagName("*");
		String TagText = null;
		System.out.println("details---"+childNodes.getLength());
		for (int ElementTagID = 1; ElementTagID < childNodes.getLength(); ElementTagID++) 
		{
			Element Elementdetails = (Element) childNodes.item(ElementTagID);

			int childnodecount = Elementdetails.getChildNodes().getLength();
			if(childnodecount == 0 ||childnodecount == 1 )
			{

				if(Elementdetails.getNodeName().equals(NodeName))
				{
					TagText = Elementdetails.getTextContent();
				}
			}
		}
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(filename));
		transformer.transform(domSource, streamResult);
		return TagText;
	}
}
