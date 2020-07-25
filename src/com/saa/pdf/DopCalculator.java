package com.saa.pdf;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DopCalculator {
	
	public List<String> getScheduledDates(String dop,String startDate,String endDate)
	{
		List<String> result =new ArrayList<String>();
		
		try
		{
		boolean entered = false;
		String days = "";
		String weekDays = getWeekDays(dop);
		DateTimeFormatter frmt = DateTimeFormatter.ofPattern("ddMMMyy");
		LocalDate date1 = LocalDate.parse(startDate, frmt);
		LocalDate date2 = LocalDate.parse(endDate, frmt);
		//System.out.println(date1.plusDays(1));
		LocalDate tempDate = date1;
		int currMonth =  date1.getMonthValue();
		//tempDate.isAfter(date2)
		//while(!tempDate.equals(date2))
		while(!tempDate.isAfter(date2))
		{
			if(weekDays.contains(tempDate.getDayOfWeek().toString()))
			{
				days += String.valueOf(tempDate.getDayOfMonth())+", ";
				entered=false;
			}
			
			tempDate =tempDate.plusDays(1);
			if(currMonth != tempDate.getMonthValue())
			{
				//System.out.println(days+" "+tempDate.minusDays(1).getMonth());
				result.add(days+"-"+tempDate.minusDays(1).getMonth());
				currMonth = tempDate.getMonthValue();
				days="";
				entered =true;
			}
		}
//		if(weekDays.contains(tempDate.getDayOfWeek().toString()))
//		{
//			days += String.valueOf(tempDate.getDayOfMonth())+", ";
//			
//		}
		
		//System.out.println(days+" "+tempDate.minusDays(1).getMonth());
		result.add(days+"-"+tempDate.minusDays(1).getMonth());
		
		
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return result;
	
	}
	
	private String getWeekDays(String days)
	{
		String[] dayArr=new String[7];
		String result = "";
		for(int i=0;i<7;i++)
		{
			switch(days.charAt(i))
			{
				
				case '1':dayArr[i]="MONDAY";
					break;
				case '2':dayArr[i]="TUESDAY";
					break;
				case '3':dayArr[i]="WEDNESDAY";
					break;
				case '4':dayArr[i]="THURSDAY";
					break;
				case '5':dayArr[i]="FRIDAY";
					break;
				case '6':dayArr[i]="SATURDAY";
					break;
				case '7':dayArr[i]="SUNDAY";
					break;
				default:dayArr[i]="NA";
			}
		}
		for(int i=0;i<7;i++)
			result+=dayArr[i]+"  ";
		
		return result;
		
	}


}
