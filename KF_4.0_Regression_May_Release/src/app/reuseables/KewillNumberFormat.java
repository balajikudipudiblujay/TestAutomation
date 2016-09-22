/**
 *
 * Copyright (c) 2000-2009 by FourSoft Ltd. All Rights Reserved.
 * This software is the proprietary information of FourSoft, Ltd.
 * Use is subject to license terms.
 *
 * eTrans - v2.7
 *
 * FourSoftNumberFormat.java
 */
package app.reuseables;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class KewillNumberFormat implements java.io.Serializable {

	/* 
	 this is default constructor that will set the number to 0.0 and round to false;
	 */
	public KewillNumberFormat() {
		stdFormat="######.##";
		round=true;
	}

	public KewillNumberFormat(String format) throws java.lang.NumberFormatException {
		if(format == "" || format.length() == 0){
			this.stdFormat=format;
			return;
		}
		if(format.indexOf("#") < 0){
			java.lang.NumberFormatException nf=new java.lang.NumberFormatException("Format is not supported");
			throw nf;
		}
		if((format.substring(0,format.indexOf("#"))).indexOf(".") >= 0 || (format.substring(0,format.indexOf("#"))).indexOf(",") >= 0){
			java.lang.NumberFormatException nf=new java.lang.NumberFormatException(
					"Format is not supported \n dot (.) or comma (,) should  not be first charactor");
			throw nf;
		}
		if(format.charAt(format.length() - 1) == '.' || format.charAt(format.length() - 1) == ','){
			java.lang.NumberFormatException nf=new java.lang.NumberFormatException(
					"Format is not supported \n dot (.) or comma (,) should not be last charactor");
			throw nf;
		}
		String dPoint=".";
		int dIndex=-1;
		//	code to identify the index of decimalpoint
		int formatLength=format.length();//Added By Giridhar For Code Review Fixes on 29-04-10
		for(int i=formatLength - 1;i >= 0;i--){
			if(format.charAt(i) == '.' || format.charAt(i) == ','){
				dIndex=i;
				break;
			}
		}
		if(dIndex != -1) dPoint=format.charAt(dIndex) + "";//get the decimal point
		// end code to identify the index of decimalpoint
		//if(format.indexOf(dPoint)<0) format=format+dPoint+"##"; 
		this.stdFormat=format;
		this.round=true;
	}

	//---------------------------------------------------
	private String format(String formattedNumber) {
		if(this.stdFormat == "" || this.stdFormat.length() == 0) return formattedNumber;
		return toFormat(parseNumber(formattedNumber));
	}

	//---------------------------------------------------
	public String format(double number) {
		if(this.stdFormat == "" || this.stdFormat.length() == 0) return number + "";
		return toFormat(number + "");
	}

	//-------------------------------------------------------
	//@@ Added by Vamsi Mahadasa on 20070830 for PR-ET-1439 Currency Conversion
	public String format(BigDecimal number) {
		if(this.stdFormat == "" || this.stdFormat.length() == 0) return number.toString() + "";
		return toFormat(number.toString() + "");
	}

	//@@ 20070830 for PR-ET-1439 Currency Conversion
	//---------------------------------------------------
	public String format(long number) {
		if(this.stdFormat == "" || this.stdFormat.length() == 0) return number + "";
		return toFormat(number + "");
	}

	//---------------------------------------------------
	private String format(String formattedNumber, boolean round) {
		if(this.stdFormat == "" || this.stdFormat.length() == 0) return formattedNumber;
		boolean temp=this.round;
		this.round=round;
		String toReturn=toFormat(parseNumber(formattedNumber));
		this.round=temp;
		return toReturn;
	}

	//---------------------------------------------------
	public String format(double number, boolean round) {
		if(this.stdFormat == "" || this.stdFormat.length() == 0) return number + "";
		boolean temp=this.round;
		this.round=round;
		String toReturn=toFormat(number + "");
		this.round=temp;
		return toReturn;
	}

	//---------------------------------------------------
	public String format(long number, boolean round) {
		if(this.stdFormat == "" || this.stdFormat.length() == 0) return number + "";
		boolean temp=this.round;
		this.round=round;
		String toReturn=toFormat(number + "");
		this.round=temp;
		return toReturn;
	}

	//-------------------------------------- 20051230 start
	public String format(double number, int decimalDigits) {
		if(this.stdFormat == "" || this.stdFormat.length() == 0) return number + "";
		String toReturn="";
		int temp=noOfDigitAfterDecimal;
		this.noOfDigitAfterDecimal=decimalDigits;
		try{
			toReturn=toFormat(number + "");
		}catch(Exception e){
			e.printStackTrace();
		}
		this.noOfDigitAfterDecimal=temp;
		return toReturn;
	}

	//---------------------------------------
	public String format(long number, int decimalDigits) {
		if(this.stdFormat == "" || this.stdFormat.length() == 0) return number + "";
		String toReturn="";
		int temp=noOfDigitAfterDecimal;
		this.noOfDigitAfterDecimal=decimalDigits;
		try{
			toReturn=toFormat(number + "");
		}catch(Exception e){
			e.printStackTrace();
		}
		this.noOfDigitAfterDecimal=temp;
		return toReturn;
	}

	//----------------------------------------------
	private String format(String formattedNumber, int decimalDigits) {
		if(this.stdFormat == "" || this.stdFormat.length() == 0) return formattedNumber;
		String toReturn="";
		int temp=noOfDigitAfterDecimal;
		this.noOfDigitAfterDecimal=decimalDigits;
		try{
			toReturn=toFormat(parseNumber(formattedNumber));
		}catch(Exception e){
			e.printStackTrace();
		}
		this.noOfDigitAfterDecimal=temp;
		return toReturn;
	}

	//------------------------------------------- 20051230 end
	//------------------------------
	private String toFormat(String number) {
		String format=this.stdFormat;
		String dPoint=".";
		int dIndex=-1;
		//	code to identify the index of decimalpoint
		int formatLength=format.length();//Added By Giridhar For Code Review Fixes on 29-04-10
		for(int i=formatLength - 1;i >= 0;i--){
			if(format.charAt(i) == '.' || format.charAt(i) == ','){
				dIndex=i;
				break;
			}
		}
		if(dIndex != -1) dPoint=format.charAt(dIndex) + "";//get the decimal point
		// end code to identify the index of decimalpoint
		String orgFormat=format;
		if(format.indexOf("#") < 0) return null;
		if(format.indexOf(dPoint) < 0) format=format + dPoint + "##";
		String tmpNum=expoToDecimal(new Double(number).toString());
		if(round == true) tmpNum=roundOff(tmpNum,format);
		char tmpNumInitial[]=new char[1];
		if(tmpNum.toCharArray()[0] == '-') tmpNumInitial[0]='-';
		char tmpNumInteger[];
		if(tmpNum.charAt(0) == '-') tmpNumInteger=(tmpNum.substring(1,tmpNum.indexOf("."))).toCharArray();
		else
			tmpNumInteger=(tmpNum.substring(0,tmpNum.indexOf("."))).toCharArray();
		char tmpNumDecimal[]=(tmpNum.substring(tmpNum.indexOf(".") + 1)).toCharArray();
		char tmpFormatInitial[]=(format.substring(0,format.indexOf("#"))).toCharArray();
		char tmpFormatInteger[]=(format.substring(format.indexOf("#"),format.lastIndexOf(dPoint))).toCharArray();
		boolean flag=false;
		int tmpNumDecimalLength=tmpNumDecimal.length;//Added By Giridhar For Code Review Fixes on 29-04-10
		for(int i=0;i < tmpNumDecimalLength;i++){
			if(tmpNumDecimal[i] > 48 && tmpNumDecimal[i] <= 57){
				flag=true;
				break;
			}
		}
		if(flag == false) tmpNumDecimal=new char[0];
		int tempLength=0;
		if((format.substring(format.lastIndexOf(dPoint) + 1)).length() > noOfDigitAfterDecimal) tempLength=(format.substring(format.lastIndexOf(dPoint) + 1))
				.length();
		else
			tempLength=noOfDigitAfterDecimal;
		char tmpFormatDecimal[]=new char[tempLength];
		int tmpFormatDecimalLength=tmpFormatDecimal.length;//Added By Giridhar For Code Review Fixes on 29-04-10
		for(int i=0;i < tmpFormatDecimalLength;i++){
			if(i < (format.substring(format.lastIndexOf(dPoint) + 1)).length()) tmpFormatDecimal[i]=(format.substring(format.lastIndexOf(dPoint) + 1))
					.charAt(i);
			else
				tmpFormatDecimal[i]='#';
		}
		List lstInteger=new ArrayList();
		List lstDecimal=new ArrayList();
		List lstInitial=new ArrayList();
		int j=tmpFormatInteger.length - 1;
		int index=0;
		lstDecimal.add(index++,new Character(dPoint.charAt(0)));
		int ic=0;
		tmpFormatDecimalLength=tmpFormatDecimal.length;//Added By Giridhar For Code Review Fixes on 29-04-10
		for(ic=0;ic < tmpFormatDecimalLength;ic++){
			if(tmpFormatDecimal[ic] == '#'){
				if(ic < tmpNumDecimal.length) lstDecimal.add(index++,new Character(tmpNumDecimal[ic]));
				else
					lstDecimal.add(index++,new Character('0'));
			}else
				lstDecimal.add(index++,new Character(tmpFormatDecimal[ic]));
		}
		index=0;
		int temp=0;
		j=tmpFormatInteger.length - 1;
		for(int i=tmpNumInteger.length - 1;i >= 0;){
			if(j < 0){
				j=temp;
			}
			if(tmpFormatInteger[j] == '#'){
				lstInteger.add(index++,new Character(tmpNumInteger[i--]));
				j--;
			}else{
				lstInteger.add(index++,new Character(tmpFormatInteger[j]));
				temp=j;
				j--;
			}
		}
		index=0;
		int tmpFormatInitialLength=tmpFormatInitial.length;//Added By Giridhar For Code Review Fixes on 29-04-10
		for(int i=0;i < tmpFormatInitialLength;i++){
			if(tmpFormatInitial[i] == '-'){
				if(tmpNumInitial[0] == '-') lstInitial.add(index++,new Character('-'));
				else
					lstInitial.add(index++,new Character(' '));
			}else{
				lstInitial.add(index++,new Character(tmpFormatInitial[i]));
			}
		}
		String str="";
		int lstInitialSize=lstInitial.size();//Added By Giridhar For Code Review Fixes on 29-04-10
		for(int i=0;i < lstInitialSize;i++){
			str+=lstInitial.get(i).toString();
		}
		if(str.indexOf("-") < 0 && tmpNumInitial[0] == '-') str+="-";
		// code for removing "(" and ")" from str if str doesn't having "-"
		if(str.indexOf("-") < 0){
			if(str.indexOf("(") >= 0 && str.indexOf(")") >= 0){
				str=str.replace('(',' ');
				str=str.replace(')',' ');
			}
			if(str.indexOf("{") >= 0 && str.indexOf("}") >= 0){
				str=str.replace('{',' ');
				str=str.replace('}',' ');
			}
			if(str.indexOf("[") >= 0 && str.indexOf("]") >= 0){
				str=str.replace('[',' ');
				str=str.replace(']',' ');
			}
		}
		// end here
		int lstIntegerSize=lstInteger.size();//Added By Giridhar For Code Review Fixes on 29-04-10
		for(int i=lstIntegerSize - 1;i >= 0;i--){
			str+=lstInteger.get(i).toString();
		}
		if(orgFormat.indexOf(dPoint) > 0){
			int lstDecimalSize=lstDecimal.size();//Added By Giridhar For Code Review Fixes on 29-04-10
			for(int i=0;i < lstDecimalSize;i++){
				str+=lstDecimal.get(i).toString();
			}
		}
		//added condition 20051230
		//str=removeLeadingZeros(str);
		if(noOfDigitAfterDecimal == 0 && str.indexOf(dPoint) >= 0) str=str.substring(0,str.indexOf(dPoint));
		//Added by Murugan
		if(lstInteger != null) lstInteger.clear();
		lstInteger=null;
		if(lstDecimal != null) lstDecimal.clear();
		lstDecimal=null;
		if(lstInitial != null) lstInitial.clear();
		lstInitial=null;
		return str.trim();
	}

	public String toString() {
		return stdFormat;
	}

	public String parseString(String formattedNumber) {
		if(this.stdFormat == "" || this.stdFormat.length() == 0) return formattedNumber;
		return parseNumber(formattedNumber);
	}
	
	public boolean isNullEmpty(String str) {
		if(str != null){
			str=str.trim();
		}
		return str == null || str.length() == 0 || "null".equalsIgnoreCase(str);
	}
	
	public int parseInt(String formattedNumber) {
		if(isNullEmpty(formattedNumber)) return 0;
		if(this.stdFormat == "" || this.stdFormat.length() == 0) return Integer.parseInt(formattedNumber);
		return (int) Double.parseDouble(parseNumber(formattedNumber));
	}

	public long parseLong(String formattedNumber) {
		if(isNullEmpty(formattedNumber)) return 0;
		if(this.stdFormat == "" || this.stdFormat.length() == 0) return Long.parseLong(formattedNumber);
		return (long) Double.parseDouble(parseNumber(formattedNumber));
	}

	public double parseDouble(String formattedNumber) {
		if(isNullEmpty(formattedNumber)) return 0;
		if(this.stdFormat == "" || this.stdFormat.length() == 0) return Double.parseDouble(formattedNumber);
		return Double.parseDouble(parseNumber(formattedNumber));
	}

	private String parseNumber(String formatedNumber) {
		String format=stdFormat;
		String dPoint=".";//set the decimal point variable to "."
		int dIndex=-1;
		//Rollback change done for  88140   by selvendhiran R  (format) FTN EXCEL Sheet issue 
		//to identify the index of decimal point 
		//@@ Modified(replaced format with formatedNumber) by Priyanka for Issue Id 88140 on 11/10/2007 
		int formatLength=format.length();//Added By Giridhar For Code Review Fixes on 29-04-10
		for(int i=formatLength - 1;i >= 0;i--){
			if(format.charAt(i) == '.' || format.charAt(i) == ','){
				dIndex=i;
				break;
			}
		}
		if(dIndex != -1) dPoint=format.charAt(dIndex) + ""; //getting the decimal point 
		//	@@  for Issue Id 88140 on 11/10/2007 
		//end to identify the index of decimal point
		char cDpoint='.';
		if(dPoint.equals(",")) cDpoint=',';
		/*finding the start index of actual number i.e if number is (-)123.23 
		 then the index is 3 ,first index of number. this is because we will divide 
		 the number into four part 1.initial 2.Integer 3.decimal 4. last part*/
		//get an temporary array of characters of formated number
		char temp[]=formatedNumber.toCharArray();
		//find the initial index of actual number
		int indexInitial=-1;
		int tempLength=temp.length;//Added By Giridhar For Code Review Fixes on 29-04-10
		for(int i=0;i < tempLength;i++){
			if((temp[i] > 48 && temp[i] <= 57) || temp[i] == cDpoint){
				indexInitial=i;
				break;
			}
		}
		//find the last index of actual number
		int indexLast=-1;
		for(int i=temp.length - 1;i >= 0;i--){
			if(temp[i] >= 48 && temp[i] <= 57){
				indexLast=i;
				break;
			}
		}
		if(indexInitial == -1)//if formated number doesn't have any digit
		{
			return "0.0";
		}
		String initialPart=formatedNumber.substring(0,indexInitial);
		String lastPart=formatedNumber.substring(indexLast + 1);
		formatedNumber=formatedNumber.substring(indexInitial,indexLast + 1);
		/*if formated number doesn't have decimal point then force fully put the decimal point into it
		 i.e. if formatednumber is 123.45 and format is ###,## then it put the ,00 to the end of formated number
		 i.e. number will 123.45,00
		 because in this case the decimal point is "," and formatednumber doesn't have decimal point*/
		if(formatedNumber.indexOf(dPoint) < 0) formatedNumber=formatedNumber + dPoint + "00";
		String integerPart=formatedNumber.substring(0,formatedNumber.lastIndexOf(dPoint));
		if(integerPart.trim().length() == 0) integerPart="0";
		String decimalPart=formatedNumber.substring(formatedNumber.lastIndexOf(dPoint) + 1);
		char decimals[]=decimalPart.toCharArray();
		char numbers[]=integerPart.toCharArray();
		String strNum="";
		// find wether the number contain the "-" or not.if it is then put into a string strNum
		if(initialPart.indexOf("-") >= 0 || lastPart.indexOf("-") >= 0) strNum="-";
		//get the digits from array of integer part of formated number and put into strNum
		int numbersLength=numbers.length;//Added By Giridhar For Code Review Fixes on 29-04-10
		for(int i=0;i < numbersLength;i++){
			if(numbers[i] >= 48 && numbers[i] <= 57){
				strNum+=numbers[i];
			}
		}
		//put "." into strrNum
		strNum+=".";
		//put decimal part of number into strNum
		int decimalsLength=decimals.length;//Added By Giridhar For Code Review Fixes on 29-04-10
		for(int i=0;i < decimalsLength;i++){
			if(decimals[i] >= 48 && decimals[i] <= 57){
				strNum+=decimals[i];
			}
		}
		return strNum;
	}

	public String expoToDecimal(String tmpNum) {
		if(tmpNum.indexOf("E") >= 0){
			String show="";
			int t;
			int i;
			t=Integer.parseInt(tmpNum.substring(tmpNum.indexOf("E") + 1));
			String strInitial=tmpNum.substring(0,tmpNum.indexOf("."));
			show=strInitial;
			char last[]=(tmpNum.substring(tmpNum.indexOf(".") + 1,tmpNum.indexOf("E"))).toCharArray();
			for(i=0;i < t;i++){
				if(i < last.length) show+=last[i];
				else
					show+="0";
			}
			if(i < last.length){
				show+=".";
				int lastLength=last.length;//Added By Giridhar For Code Review Fixes on 29-04-10
				while(i < lastLength){
					show+=last[i++];
				}
			}
			if(show.indexOf(".") < 0) show+=".00";
			return show;
		}else
			return tmpNum;
	}

	private String roundOff(String tmpNum, String format) {
		//@@ chanchal modify for issue number 15326
		double number;
		String strNumber="";
		double tmpNumber=Double.parseDouble(tmpNum);
		String toCheck=tmpNumber + "";
		if(toCheck.indexOf("E") < 0) strNumber=Math.round(tmpNumber * Math.pow(10,noOfDigitAfterDecimal)) / Math.pow(10,noOfDigitAfterDecimal) + "";
		else
			strNumber=tmpNum;
		if(strNumber.indexOf(".") < 0) strNumber=strNumber + ".0";
		return strNumber;
	}

	//@@ Added by Amar for WPBN-Issue 112052 on 20080320
	//@@ NumberFormat for int is not working for the format ### ### ###,## on 20080320
	public String parseNumber(String formatedNumber, String format) {
		String dPoint=".";//set the decimal point variable to "."
		int dIndex=-1;
		if(format == "" || format.length() == 0) return formatedNumber;
		//to identify the index of decimal point 
		int formatLength=format.length();//Added By Giridhar For Code Review Fixes on 29-04-10
		for(int i=formatLength - 1;i >= 0;i--){
			if(format.charAt(i) == '.' || format.charAt(i) == ','){
				dIndex=i;
				break;
			}
		}
		//System.out.print(" dIndex  "+dIndex);
		/*
		 if(dIndex!=-1)
		 dPoint=format.charAt(dIndex)+""; //getting the decimal point 
		 */
		//@@ Modified by Amar for WPBN-Issue 113334 ON 20080401
		if(formatedNumber.indexOf('.') != -1){
			dPoint=formatedNumber.charAt(formatedNumber.indexOf('.')) + "";
		}else if(formatedNumber.indexOf(',') != -1){
			dPoint=formatedNumber.charAt(formatedNumber.indexOf(',')) + "";
		}else if(dIndex != -1) dPoint=format.charAt(dIndex) + ""; //getting the decimal point 
		//System.out.print(" dPoint  "+dPoint);
		//end to identify the index of decimal point
		char cDpoint='.';
		if(dPoint.equals(",")) cDpoint=',';
		//System.out.print(" dPoint  "+dPoint);
		/*finding the start index of actual number i.e if number is (-)123.23 
		 then the index is 3 ,first index of number. this is because we will divide 
		 the number into four part 1.initial 2.Integer 3.decimal 4. last part*/
		//get an temporary array of characters of formated number
		char temp[]=formatedNumber.toCharArray();
		//find the initial index of actual number
		int indexInitial=-1;
		int tempLength=temp.length;//Added By Giridhar For Code Review Fixes on 29-04-10
		for(int i=0;i < tempLength;i++){
			//System.out.print(" temp["+i+"]  "+temp[i]);
			if((temp[i] > 48 && temp[i] <= 57) || temp[i] == cDpoint){
				indexInitial=i;
				break;
			}
		}
		//System.out.print(" indexInitial  "+indexInitial);
		//find the last index of actual number
		int indexLast=-1;
		for(int i=temp.length - 1;i >= 0;i--){
			//System.out.print(" temp["+i+"]  "+temp[i]);
			if(temp[i] >= 48 && temp[i] <= 57){
				indexLast=i;
				break;
			}
		}
		//System.out.print(" indexLast  "+indexLast);
		if(indexInitial == -1)//if formated number doesn't have any digit
		{
			return "0.0";
		}
		String initialPart=formatedNumber.substring(0,indexInitial);
		//System.out.print("\n initialPart "+initialPart);
		String lastPart=formatedNumber.substring(indexLast + 1);
		// System.out.print("\n lastPart "+lastPart);
		formatedNumber=formatedNumber.substring(indexInitial,indexLast + 1);
		//	 System.out.print("\n formatedNumber "+formatedNumber);
		/*if formated number doesn't have decimal point then force fully put the decimal point into it
		 i.e. if formatednumber is 123.45 and format is ###,## then it put the ,00 to the end of formated number
		 i.e. number will 123.45,00
		 because in this case the decimal point is "," and formatednumber doesn't have decimal point*/
		// System.out.print("\n formatedNumber.indexOf(dPoint) "+formatedNumber.indexOf(dPoint));
		if(formatedNumber.indexOf(dPoint) < 0) formatedNumber=formatedNumber + dPoint + "00";
		//System.out.print("\n ***** formatedNumber "+formatedNumber);
		String integerPart=formatedNumber.substring(0,formatedNumber.lastIndexOf(dPoint));
		// System.out.print("\n **   integerPart "+integerPart);
		if(integerPart.trim().length() == 0) integerPart="0";
		String decimalPart=formatedNumber.substring(formatedNumber.lastIndexOf(dPoint) + 1);
		//System.out.print("\n **   decimalPart "+decimalPart);
		char decimals[]=decimalPart.toCharArray();
		char numbers[]=integerPart.toCharArray();
		String strNum="";
		// find wether the number contain the "-" or not.if it is then put into a string strNum
		if(initialPart.indexOf("-") >= 0 || lastPart.indexOf("-") >= 0) strNum="-";
		//get the digits from array of integer part of formated number and put into strNum
		int numbersLength=numbers.length;//Added By Giridhar For Code Review Fixes on 29-04-10
		for(int i=0;i < numbersLength;i++){
			if(numbers[i] >= 48 && numbers[i] <= 57){
				strNum+=numbers[i];
			}
		}
		//System.out.print("\n ** for numbers  strNum "+strNum);
		//put "." into strrNum
		strNum+=".";
		//put decimal part of number into strNum
		int decimalsLength=decimals.length;//Added By Giridhar For Code Review Fixes on 29-04-10
		for(int i=0;i < decimalsLength;i++){
			if(decimals[i] >= 48 && decimals[i] <= 57){
				strNum+=decimals[i];
			}
		}
		//System.out.print(" In parseNumber() decimals [strNum] "+strNum); 
		return strNum;
	}

	public int parseInt(String formattedNumber, String format) {
		//System.out.print(" In parseInt(String formatedNumber, String format) [formattedNumber ] "+formattedNumber+ " [format ] "+format);
		if(format == "" || format.length() == 0) return Integer.parseInt(formattedNumber);
		int result=(int) Double.parseDouble(parseNumber(formattedNumber,format));
		//System.out.print("\n **   result "+result);
		return result;
	}

	//@@ For ### ### ###,## Number Format on 20080320
	private String stdFormat;
	private boolean round;
	private int noOfDigitAfterDecimal=2;

	public String getStdFormat() {
		return stdFormat;
	}

	public void setStdFormat(String stdFormat) {
		this.stdFormat=stdFormat;
	}

	public double roundOffWeight(double Rval) {
		int Rpl=1;
		double p=(double) Math.pow(10,Rpl);
		Rval=Rval * p;
		float tmp=Math.round(Rval);
		return (double) tmp / p;
	}

	public double roundOffWeight(double Rval, int Rpl) {
		double p=(double) Math.pow(10,Rpl);
		Rval=Rval * p;
		float tmp=Math.round(Rval);
		return (double) tmp / p;
	}

	/**Method is used to parse Double w/o converting it to exponential**/
	public String parseDoubleFromString(String formattedNumber, KewillNumberFormat nFormat) {
		if(this.stdFormat == "" || this.stdFormat.length() == 0) return("0");
		if(formattedNumber == null || "".equals(formattedNumber)) return("0");
		double tempVal=nFormat.parseDouble(formattedNumber);
		String temp1=expoToDecimal(new Double(tempVal).toString());
		return temp1;
	}
	//Added By Srikanth Reddy K for CR_ET_NAS_55 on 05-06-2012
	
	//end CR_ET_NAS_55
}