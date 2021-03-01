package com.makeMyTrip.e2e;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test {

	public static void main(String[] args) {
//		String oldDate = "Thu Feb 25 2021";
//		System.out.println("Date before Addition: "+oldDate);
//		//Specifying date format that matches the given date
//		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy");
//		Calendar c = Calendar.getInstance();
//		try{
//		   //Setting the date to the given date
//		   c.setTime(sdf.parse(oldDate));
//		}catch(ParseException e){
//			e.printStackTrace();
//		 }
//		   
//		//Number of Days to add
//		c.add(Calendar.DAY_OF_MONTH, 2);  
//		//Date after adding the days to the given date
//		String newDate = sdf.format(c.getTime());  
//		//Displaying the new Date after addition of Days
//		System.out.println("Date after Addition: "+newDate);
		
		String f = "Thu Feb 25 2021";
		Date date = new Date(f); 
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM");
		String g = sdf.format(date);
		System.out.println(g);
		

	}

}
