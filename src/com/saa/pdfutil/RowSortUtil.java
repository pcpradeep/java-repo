package com.saa.pdfutil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RowSortUtil {
	public static List<Row> sortByMonth(List<Row> dList)
	{
		List<Row> newList=new ArrayList<Row>();
		
		String route="";
		List<Row> dTemp=null;
		for(Row d:dList)
		{
			if(!d.getRoute().isEmpty())
			{
				if(dTemp!=null)
				{
					Collections.sort(dTemp, Row.sortMonth);
					dTemp=sortByTime(dTemp);
					clearRouts(dTemp);
					dTemp.get(0).setRoute(route);
					//sortByFlight(dList);
					newList.addAll(dTemp);
					
				}
				dTemp=new ArrayList<Row>();
				dTemp.add(d);
				route=d.getRoute();
			}
			else
			{
				dTemp.add(d);
			}
				
		}
		if(dTemp!=null)
		{
			Collections.sort(dTemp, Row.sortMonth);
			dTemp=sortByTime(dTemp);
			clearRouts(dTemp);
			dTemp.get(0).setRoute(route);
			newList.addAll(dTemp);
			
		}
		for(Row d:newList)
		{
			System.out.println(d.getMonth());
		}
		return newList;
	
	}
	static List<Row> clearRouts(List<Row> d)
	{
		for(Row dt:d)
		{
			dt.setRoute("");
		}
		return d;
	}
	static List<Row> sortByTime(List<Row> dList)
	{
		String prevMonth= dList.get(0).getMonth();
		
		List<Row> newList=new ArrayList<Row>();
		List<Row> tempList=new ArrayList<Row>();
		boolean entered =false;
		for(Row d:dList)
		{
			if(!prevMonth.equals(d.getMonth()))
			{
				entered=true;
				Collections.sort(tempList, Row.sortTime);
				newList.addAll(tempList);
				tempList=new ArrayList<Row>();
				tempList.add(d);
				prevMonth=d.getMonth();
				
			}
			else
			{
				tempList.add(d);
				prevMonth=d.getMonth();
				entered=false;
			}
			
		}
		
			Collections.sort(tempList, Row.sortTime);
			newList.addAll(tempList);
		
		return newList;
	}

}
