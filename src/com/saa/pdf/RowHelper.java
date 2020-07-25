package com.saa.pdf;

import java.io.FileNotFoundException;













import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.saa.pdfutil.Row;
import com.saa.pdfutil.RowSortUtil;




public class RowHelper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RowHelper u = new RowHelper();
		List<Row> rowList = new ArrayList<Row>();
		
		rowList.add(new Row("SIN-MNL","JANUARY","28","18:30","22:10","SQ 920"));
		rowList.add(new Row("SIN-MNL","JANUARY","22 23 24 25 26 27","18:30","22:10","SQ 920"));
		rowList.add(new Row("MNL-SIN","FEBRUARY","5","23:50","03:45","SQ 915"));
		u.removeDuplicates(rowList);
	}
	
	public List<Row>  removeDuplicates(List<Row> rowList)
	{
		Map<String,List<Row>> dataMap = new HashMap<String, List<Row>>();
		Set<String> keys=null;
		List<Row> rList=null;
		List<Row> newRowList=new ArrayList<Row>();
		
		for(Row r:rowList)
		{
			System.out.println(r.getRoute()+r.getMonth()+r.getDepartureTime()+r.getArrivalTime()+r.getFlightNumber());
			if(dataMap.get(r.getRoute()+r.getMonth()+r.getDepartureTime()+r.getArrivalTime()+r.getFlightNumber())!=null)
			{
				dataMap.get(r.getRoute()+r.getMonth()+r.getDepartureTime()+r.getArrivalTime()+r.getFlightNumber()).add(r);
			}
			else
			{
				rList=new ArrayList<Row>();
				rList.add(r);
				dataMap.put(r.getRoute()+r.getMonth()+r.getDepartureTime()+r.getArrivalTime()+r.getFlightNumber(), rList);
			}
				
		}
		keys=dataMap.keySet();
		String dates="";
		for(String k:keys)
		{
			//System.out.println("Key:"+k+" List Size:"+dataMap.get(k));
			Row r= dataMap.get(k).get(0);
			dates = getDates(dataMap.get(k));
			newRowList.add(new Row(r.getRoute(),r.getMonth(),dates,r.getDepartureTime(),r.getArrivalTime(),r.getFlightNumber()));
		}
		newRowList=getRows(newRowList);
		return newRowList;
		
	}
	String getDates(List<Row> list)
	{
		String dates="";
		for(Row r:list)
		{
			dates+=r.getDates();
		}
		return dates;
	}
	List<String> getKeys(List<Row> list)
	{
		List<String> keys=new ArrayList<String>();
		List<String> tempKey=new ArrayList<String>();
		String routName="";
		Collections.sort(list,Row.compByRoute);
		for(Row r:list)
		{
			if(!keys.contains(r.getRoute()))
			{
				keys.add(r.getRoute());
			}
		}
		
		for(int i=0;i<keys.size();i++)
		{
			if(!tempKey.contains(keys.get(i)))
				tempKey.add(keys.get(i));
			routName=keys.get(i).split(" - ")[1]+" - "+keys.get(i).split(" - ")[0];
			System.out.println(routName);
			if(keys.contains(routName))
			{
				if(!tempKey.contains(routName))
					tempKey.add(routName);
			}
		}
		keys=tempKey;
		return keys;
	}
	List<Row> getRows(List<Row> rList)
	{
		List<Row> newList=new ArrayList<Row>();
		List<String> routes=getKeys(rList);
		boolean entered=false;
		String dates="";
		
		for(String r:routes)
		{
			entered=false;
			for(Row row:rList)
			{
				if(row.getRoute().equals(r))
				{
					dates=row.getDates().substring(0,row.getDates().lastIndexOf(","));
					
					if(!entered)
					{
						newList.add(new Row(r,row.getMonth().substring(0, 1).toUpperCase()+row.getMonth().substring(1).toLowerCase(),dates,row.getDepartureTime(),row.getArrivalTime(),row.getFlightNumber()));
						entered=true;
					}else{
						newList.add(new Row("",row.getMonth().substring(0, 1).toUpperCase()+row.getMonth().substring(1).toLowerCase(),dates,row.getDepartureTime(),row.getArrivalTime(),row.getFlightNumber()));
						
					}
					
				}
					
			}
		}
		newList=RowSortUtil.sortByMonth(newList);
		return newList;
	}
}
