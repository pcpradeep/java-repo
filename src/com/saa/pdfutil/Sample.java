package com.saa.pdfutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;








import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.text.DateFormatter;

import com.saa.pdf.FlightDetail;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.ClassLoaderResource;


public class Sample {
	List<FlightDetail> flightDetails = new ArrayList<FlightDetail>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//				List<String> cityList=new ArrayList<String>();
//				List<String> dCityList=new ArrayList<String>();
//				
//				cityList.add("Delhi-Mumbai");
//				cityList.add("Chennai-Delhi");
//				cityList.add("Kolkata-Delhi");
//				cityList.add("Mumbai-Chennai");
//				cityList.add("Mumbai-Delhi");
//				cityList.add("Delhi-Chennai");
//				cityList.add("Chennai-Mumbai");
//				Collections.sort(cityList);
//				System.out.println("After city sort");
//				cityList.forEach((c)->System.out.println(c));
//				
//				String cname="";
//				dCityList.add(cname);
//				
//				for(int i=0;i<cityList.size();i++)
//				{
//					if(!dCityList.contains(cityList.get(i)))
//						dCityList.add(cityList.get(i));
//					cname=cityList.get(i).split("-")[1]+"-"+cityList.get(i).split("-")[0];
//					if(cityList.contains(cname))
//					{
//						if(!dCityList.contains(cname))
//							dCityList.add(cname);
//					}
//					
//				}
//				System.out.println("After route sort");
//				dCityList.forEach((c)->System.out.println(c));
//				
		String name="My name is Pradeep";
		if(name.contains("-"))
		{
			System.out.println("Inside replace");	
			name=name.replace("-", ",");
		}
		System.out.println(name);
		Set<String> hs = new HashSet<String>();
		hs=null;
		System.out.println(hs==null);
				
	}
	
	
	void read(LocalDate start,LocalDate end,String filePath)
	{
		try {
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);
			String line="";
			DateTimeFormatter frt=DateTimeFormatter.ofPattern("ddMMMyy");
			Map<String,String> data = new HashMap<String, String>();
			
			while((line=br.readLine())!=null)
			{
				if(!line.equals(""))
				data = getFlightInfoFromCSV(line);
				if(getDateFromString(data.get("startDate")).isBefore(start))
				{
					if(getDateFromString(data.get("endDate")).isEqual(start))
					{
						//Start date =userdate 
						//End date=csvdate
						flightDetails.add(new FlightDetail(data.get("carCode"), data.get("flightNo"), start.format(frt), data.get("endDate"),data.get("arrStn"), data.get("deptStn"),data.get("arrTimeUTC") ,data.get("depTimeUTC") , "",data.get("dop") , "", ""));
						System.out.println(line);
					}
					if(getDateFromString(data.get("endDate")).isAfter(start)& getDateFromString(data.get("endDate")).isBefore(end))
					{
						//Start date =userdate 
						//End date=csvdate
						flightDetails.add(new FlightDetail(data.get("carCode"), data.get("flightNo"), start.format(frt), data.get("endDate"),data.get("arrStn"), data.get("deptStn"),data.get("arrTimeUTC") ,data.get("depTimeUTC") , "",data.get("dop") , "", ""));
						System.out.println(line);
					}
					if(getDateFromString(data.get("endDate")).isEqual(end))
					{
						//Start date =userdate 
						//End date=csvdate
						flightDetails.add(new FlightDetail(data.get("carCode"), data.get("flightNo"), start.format(frt), data.get("endDate"),data.get("arrStn"), data.get("deptStn"),data.get("arrTimeUTC") ,data.get("depTimeUTC") , "",data.get("dop") , "", ""));
						System.out.println(line);
						
					}
					if(getDateFromString(data.get("endDate")).isAfter(end))
					{
						//Start date =userdate 
						//End date=userdate
						flightDetails.add(new FlightDetail(data.get("carCode"), data.get("flightNo"), start.format(frt), end.format(frt),data.get("arrStn"), data.get("deptStn"),data.get("arrTimeUTC") ,data.get("depTimeUTC") , "",data.get("dop") , "", ""));
						System.out.println(line);
						
					}
					
				}
				if(getDateFromString(data.get("startDate")).isEqual(start))
				{
					if(getDateFromString(data.get("endDate")).isEqual(start))
					{
						//Start date =csvdate 
						//End date=csvdate
						flightDetails.add(new FlightDetail(data.get("carCode"), data.get("flightNo"), data.get("startDate"), data.get("endDate"),data.get("arrStn"), data.get("deptStn"),data.get("arrTimeUTC") ,data.get("depTimeUTC") , "",data.get("dop") , "", ""));
						System.out.println(line);
					}
					if(getDateFromString(data.get("endDate")).isAfter(start)& getDateFromString(data.get("endDate")).isBefore(end))
					{
						//Start date =csvdate 
						//End date=csvdate
						flightDetails.add(new FlightDetail(data.get("carCode"), data.get("flightNo"), data.get("startDate"), data.get("endDate"),data.get("arrStn"), data.get("deptStn"),data.get("arrTimeUTC") ,data.get("depTimeUTC") , "",data.get("dop") , "", ""));
						System.out.println(line);
					}
					if(getDateFromString(data.get("endDate")).isEqual(end))
					{
						//Start date =csvdate 
						//End date=csvdate
						flightDetails.add(new FlightDetail(data.get("carCode"), data.get("flightNo"), data.get("startDate"), data.get("endDate"),data.get("arrStn"), data.get("deptStn"),data.get("arrTimeUTC") ,data.get("depTimeUTC") , "",data.get("dop") , "", ""));
						System.out.println(line);
						
					}
					if(getDateFromString(data.get("endDate")).isAfter(end))
					{
						//Start date =csvdate 
						//End date=userdate
						flightDetails.add(new FlightDetail(data.get("carCode"), data.get("flightNo"), data.get("startDate"), end.format(frt),data.get("arrStn"), data.get("deptStn"),data.get("arrTimeUTC") ,data.get("depTimeUTC") , "",data.get("dop") , "", ""));
						System.out.println(line);
						
					}
					
				}
				if(getDateFromString(data.get("startDate")).isAfter(start)&getDateFromString(data.get("startDate")).isBefore(end))
				{
					
						if(getDateFromString(data.get("endDate")).isBefore(end))
						{
							//Start date =csvdate 
							//End date=csvdate
							flightDetails.add(new FlightDetail(data.get("carCode"), data.get("flightNo"), data.get("startDate"), data.get("endDate"),data.get("arrStn"), data.get("deptStn"),data.get("arrTimeUTC") ,data.get("depTimeUTC") , "",data.get("dop") , "", ""));
							System.out.println(line);
						}
						if(getDateFromString(data.get("endDate")).isEqual(end))
						{
							//Start date =csvdate 
							//End date=csvdate
							flightDetails.add(new FlightDetail(data.get("carCode"), data.get("flightNo"), data.get("startDate"), data.get("endDate"),data.get("arrStn"), data.get("deptStn"),data.get("arrTimeUTC") ,data.get("depTimeUTC") , "",data.get("dop") , "", ""));
							System.out.println(line);
						}
						if(getDateFromString(data.get("endDate")).isAfter(end))
						{
							//Start date =csvdate 
							//End date=userdate
							flightDetails.add(new FlightDetail(data.get("carCode"), data.get("flightNo"), data.get("startDate"), end.format(frt),data.get("arrStn"), data.get("deptStn"),data.get("arrTimeUTC") ,data.get("depTimeUTC") , "",data.get("dop") , "", ""));
							System.out.println(line);
						}
						
					
				}
				
				if(getDateFromString(data.get("startDate")).isEqual(end))
				{
					if(getDateFromString(data.get("endDate")).isEqual(end))
					{
						//Start date =csvdate 
						//End date=csvdate
						flightDetails.add(new FlightDetail(data.get("carCode"), data.get("flightNo"), data.get("startDate"), data.get("endDate"),data.get("arrStn"), data.get("deptStn"),data.get("arrTimeUTC") ,data.get("depTimeUTC") , "",data.get("dop") , "", ""));
						System.out.println(line);
					}
					if(getDateFromString(data.get("endDate")).isAfter(end))
					{
						//Start date =csvdate 
						//End date=userdate
						flightDetails.add(new FlightDetail(data.get("carCode"), data.get("flightNo"), data.get("startDate"), end.format(frt),data.get("arrStn"), data.get("deptStn"),data.get("arrTimeUTC") ,data.get("depTimeUTC") , "",data.get("dop") , "", ""));
						System.out.println(line);
					}
				}
					
				
				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	LocalDate getDateFromString(String dt)
	{
		DateTimeFormatter frmt = DateTimeFormatter.ofPattern("ddMMMyy");
		return LocalDate.parse(dt,frmt);
	}
	public Map<String,String> getFlightInfoFromCSV(String csvData)
	{
		Map<String,String> result = new HashMap<String, String>();
		
		result.put("carCode", csvData.split(",")[0]);
		result.put("flightNo", csvData.split(",")[1]);
		result.put("startDate", csvData.split(",")[2]);
		result.put("endDate", csvData.split(",")[3]);
		result.put("arrStn", csvData.split(",")[4]);
		result.put("arrTimeUTC", csvData.split(",")[5]);
		result.put("deptStn", csvData.split(",")[6]);
		result.put("depTimeUTC", csvData.split(",")[7]);
		result.put("dop", csvData.split(",")[9]);
		
		
		return result;
		
	}
}
