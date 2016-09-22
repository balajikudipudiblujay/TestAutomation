package global.reusables;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class ObjectRepository extends GenericMethods{
	
	WebElement element=null;
	List<WebElement> elements=null;
	DocumentBuilderFactory dbf;
	DocumentBuilder db;
	Document document ;

	/**
	 * It is a constructor which will create a parser by taking file path as an arguiment. 
	 * @param filename The path of the OR file.
	 * @author Priyaranjan,Prasanna
	 */
	public ObjectRepository(String filename)
	{
		
		try {
			dbf = DocumentBuilderFactory.newInstance();
			db   = dbf.newDocumentBuilder();
			document = db.parse(new File(filename));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("File not found.");
		}
	}

	/**
     * This method will read the OR xml file and get locator and value for the given elementName and finds the webElement based on the locator name and value.
     * @param driver Instance of webDriver.
     * @param ElementName Key name of the element in the xml file.
     * @param timeout Synchronization timeouts.
     * @return webElement.
     * @author Priyaranjan,Prasanna     * 
     * @Modified By Sandeep Jain
     * @Modified Date August 2014
     * @Modified Description:  Updated code to fetch details from xml file for "Browser" child node.  
      */
     public  WebElement getElement(WebDriver driver,String ElementValue,HashMap<String, String> ScenarioDetailsHashMap,long timeout) {
		try 
		{
			boolean flag=false;
			Element rootElement = document.getDocumentElement();
			NodeList nodeList = rootElement.getElementsByTagName("Element");
			String ORElementName = null;
			String BrowserName = null;
			for(int x=0;x< nodeList.getLength();  x++) 
			{
				Element BrowserElement = (Element)nodeList.item(x);
				NodeList childNodeList = BrowserElement.getElementsByTagName("Browser");
				ORElementName = BrowserElement.getAttribute("name");
				//System.out.println("ElementValue==="+ElementValue+"===ORElementName===="+ORElementName);
				if(ElementValue.equalsIgnoreCase(ORElementName))
				{
					int lastNameCount = BrowserElement.getElementsByTagName("Browser").getLength();
					for (int Browsercount = 0; Browsercount < lastNameCount; Browsercount++) 
					{
						Element childElement = (Element)childNodeList.item(Browsercount);
						BrowserName = childElement.getAttribute("name");
						if(ScenarioDetailsHashMap.get("BrowserType").equalsIgnoreCase(BrowserName))
						{
							String LocatorType =childElement.getAttribute("locator");
							String ElementName=childElement.getTextContent();
							if(LocatorType.equalsIgnoreCase("id"))
							{
								element=locateElement(driver,By.id(ElementName),timeout);   
								flag=true;
								break;
							}
							else if(LocatorType.equalsIgnoreCase("name"))
							{
								element=locateElement(driver,By.name(ElementName),timeout);
								flag=true;
								break;
							}
							else if(LocatorType.equalsIgnoreCase("className"))
							{
								element=locateElement(driver,By.className(ElementName),timeout);
								flag=true;
								break;
							}
							else if(LocatorType.equalsIgnoreCase("cssSelector"))
							{
								element=locateElement(driver,By.cssSelector(ElementName),timeout);
								flag=true;
								break;
							}
							else if(LocatorType.equalsIgnoreCase("xpath")){
								element=locateElement(driver,By.xpath(ElementName),timeout);
							//	System.out.println("inside element");
								flag=true;
								break;
							}
							else if(LocatorType.equalsIgnoreCase("linkText")){
								element=locateElement(driver,By.linkText(ElementName),timeout);
								flag=true;
								break;
							}
							else if(LocatorType.equalsIgnoreCase("partialLinkText")){
								element=locateElement(driver,By.partialLinkText(ElementName),timeout);
								flag=true;
								break;
							}
							else if(LocatorType.equalsIgnoreCase("tagName")){
								element=locateElement(driver,By.tagName(ElementName),timeout);
								flag=true;
								break;
							}
							else 
							{
								element = null;
							}
						}

					}
					//System.out.println("flag="+flag);
					if(flag==true){
						break;
					}
				}

			}     return element;   
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			//System.out.println("Element not found.");    
			return null;
		}
	} 

	
	/**@Description: This method will read the OR xml file and get locator and list of value for the given elementName and finds the webElements based on the locator name and value based on browser type.
	 * @param driver Instance of webDriver.
	 * @param ElementName Key name of the element in the xml file.
	 * @param timeout Synchronization timeouts.
	 * @return webElements.
	 * @author Prasanna Modugu
	 * @Modified By Priyaranjan Misra
	 */
	public  List<WebElement> getElements(WebDriver driver,String ElementValue,HashMap<String, String> ScenarioDetailsHashMap,long timeout) {
	 
		try 
		{
			boolean flag=false;
			Element rootElement = document.getDocumentElement();
			NodeList nodeList = rootElement.getElementsByTagName("Element");
			String ORElementName = null;
			String BrowserName = null;
			for(int x=0;x< nodeList.getLength();  x++) 
			{
				Element BrowserElement = (Element)nodeList.item(x);
				NodeList childNodeList = BrowserElement.getElementsByTagName("Browser");
				ORElementName = BrowserElement.getAttribute("name");
				if(ElementValue.equalsIgnoreCase(ORElementName))
				{
					int lastNameCount = BrowserElement.getElementsByTagName("Browser").getLength();
					for (int Browsercount = 0; Browsercount < lastNameCount; Browsercount++) 
					{
						/*System.out.println("Brwser Name"+BrowserName);
						System.out.println("Brwser Name==="+ScenarioDetailsHashMap.get("BrowserType"));*/
						Element childElement = (Element)childNodeList.item(Browsercount);
						BrowserName = childElement.getAttribute("name");
						if(ScenarioDetailsHashMap.get("BrowserType").equalsIgnoreCase(BrowserName))
						{
							String LocatorType =childElement.getAttribute("locator");
							String ElementName=childElement.getTextContent();
							if(LocatorType.equalsIgnoreCase("id"))
							{
								elements=locateElements(driver,By.id(ElementName),timeout);   
								flag=true;
								break;
							}
							else if(LocatorType.equalsIgnoreCase("name"))
							{
								elements=locateElements(driver,By.name(ElementName),timeout);
								flag=true;
								break;
							}
							else if(LocatorType.equalsIgnoreCase("className"))
							{
								elements=locateElements(driver,By.className(ElementName),timeout);
								flag=true;
								break;
							}
							else if(LocatorType.equalsIgnoreCase("cssSelector"))
							{
								elements=locateElements(driver,By.cssSelector(ElementName),timeout);
								flag=true;
								break;
							}
							else if(LocatorType.equalsIgnoreCase("xpath")){
								elements=locateElements(driver,By.xpath(ElementName),timeout);
								flag=true;
								break;
							}
							else if(LocatorType.equalsIgnoreCase("linkText")){
								elements=locateElements(driver,By.linkText(ElementName),timeout);
								flag=true;
								break;
							}
							else if(LocatorType.equalsIgnoreCase("partialLinkText")){
								elements=locateElements(driver,By.partialLinkText(ElementName),timeout);
								flag=true;
								break;
							}
							else if(LocatorType.equalsIgnoreCase("tagName")){
								elements=locateElements(driver,By.tagName(ElementName),timeout);
								flag=true;
								break;
							}
							else 
							{
								elements = null;
							}
						}

					}
					//System.out.println("flag="+flag);
					if(flag==true){
						break;
					}
				}

			}     return elements;   
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			//System.out.println("Element not found.");    
			return null;
		}
	} 
	public  String getSchemaColumnValue(String sheetName,String ElementValue) {
        String required = "";
        try 
        {
        
        
        Element rootElement = this.document.getDocumentElement();
        NodeList nodeList = rootElement.getElementsByTagName("Sheet");
        for (int i = 0; i < nodeList.getLength(); i++) {
              
              Element sheetElem = (Element)nodeList.item(i);
              NodeList sheetChildNodeList = sheetElem.getElementsByTagName("Element");
              String sheetElementName = sheetElem.getAttribute("name");
              //System.out.println("sheetName===="+sheetName+"--sheetElementName---"+sheetElementName);
              if(sheetElementName.equalsIgnoreCase(sheetName)){
                    int elemCount = sheetElem.getElementsByTagName("Element").getLength();
              for(int x=0;x< elemCount;  x++) 
              {
                    Element elem = (Element)sheetChildNodeList.item(x);
                    String ORElementName = elem.getAttribute("name");
                    
                    if(ElementValue.equalsIgnoreCase(ORElementName))
                    {
                                      
                          required = elem.getAttribute("mandatory");     
                          // System.out.println(" in IF block  ORElementName===="+ORElementName+"=====required======"+required); 
                          break;
                          }
                    else 
                    {
                          required = "N";
                    }
                          
                          
                    //System.out.println("ORElementName===="+ORElementName+"=====required======"+required);
                    
                    }
              }
        }
        
  }catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
  }
  return required;
}

	  public  String GetLocatorValue(WebDriver driver,String ElementName,HashMap<String, String> ScenarioDetailsHashMap,long timeout) {
			try 
			{
				NodeList nodeList = this.document.getElementsByTagName("Element");
				String ORElementName = null;
				String BrowserName = null;
				for(int x=0;x< nodeList.getLength();  x++) 
				{
					Element BrowserElement = (Element)nodeList.item(x);
					NodeList childNodeList = BrowserElement.getElementsByTagName("Browser");
					ORElementName = BrowserElement.getAttribute("name");
					if(ElementName.equalsIgnoreCase(ORElementName))
					{
						int lastNameCount = BrowserElement.getElementsByTagName("Browser").getLength();
						for (int Browsercount = 0; Browsercount < lastNameCount; Browsercount++) 
						{
							Element childElement = (Element)childNodeList.item(Browsercount);
							BrowserName = childElement.getAttribute("name");
							if(ScenarioDetailsHashMap.get("BrowserType").equalsIgnoreCase(BrowserName))
							{
								ElementName=childElement.getTextContent();
								break;
								
							}

						}

					}

				}     return ElementName;   
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				return null;
			}
		} 
	
	
}
