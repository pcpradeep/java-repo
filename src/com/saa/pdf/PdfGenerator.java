package com.saa.pdf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.text.html.parser.Entity;

import com.saa.pdfutil.AirportMapUtil;
import com.saa.pdfutil.JasperReportUtil;
import com.saa.pdfutil.Row;
import com.saa.pdfutil.Table;
import com.saa.pdfutil.ZipFileUtil;

public class PdfGenerator {
	List<FlightDetail> flightDetails = new ArrayList<FlightDetail>();
	
	public void getPdf(String sDate,String eDate,String dataPath,String outputPath)
	{
		LocalDate startDate=getDateFromString(sDate);
		LocalDate endDate=getDateFromString(eDate);
		String startMonthName=startDate.getMonth().name();
		String endMonthName=endDate.getMonth().name();
		System.out.println(startMonthName.substring(0, 1)+startMonthName.substring(1).toLowerCase()+" - "+endMonthName.substring(0, 1)+endMonthName.substring(1).toLowerCase());
		String reportHeader="Flight Schedules for "+startMonthName.substring(0, 1)+startMonthName.substring(1).toLowerCase()+" - "+endMonthName.substring(0, 1)+endMonthName.substring(1).toLowerCase()+" "+startDate.getYear();
		RowHelper u=new RowHelper();
		
		List<String> regions=new ArrayList<String>();
		regions.add("Europe");
		regions.add("The Americas");
		regions.add("North Asia");
		regions.add("South East Asia");
		regions.add("Southwest Pacific");
		regions.add("West Asia & Africa");
		loadCsvToList(startDate,endDate,dataPath);
		for(String reg:regions)
		{
			
			Map<String,List<FlightDetail>> countryFlights=getFlightForCountries(reg);
			String key="";
			System.out.println("Inside Main");
			AirportMapUtil util=new AirportMapUtil();
			DopCalculator dc = new DopCalculator();
			List<Table> tbls= new ArrayList<Table>();
			String flightNo="";
			String stn="";
			String region="";
			String country="";
			String route ="";
			List<String> dop=null;
			List<Row> rows=null;
			Set<String> keys = countryFlights.keySet();
			for(String k:keys)
			{
				//System.out.println("Key:"+k+" List size:"+countryFlights.get(k).size());
				rows=new ArrayList<Row>();
				for(FlightDetail fd:countryFlights.get(k))
				{
					//System.out.println(fd);
					flightNo =fd.getCarCode()+" "+fd.getFlightNo();
					stn = fd.getArrStn().equals("SIN")?fd.getDeptStn():fd.getArrStn();
					region = util.getRegionFromAirport(stn);
					country = util.getCountryFromAirport(stn);
					//route=fd.getDeptStn().substring(0,1).toUpperCase()+fd.getDeptStn().substring(1).toLowerCase()+"-"+fd.getArrStn().substring(0,1).toUpperCase()+fd.getArrStn().substring(1).toLowerCase();
					route = util.getAirportNameFromAirport(fd.getDeptStn())+" - "+util.getAirportNameFromAirport(fd.getArrStn());
					
					dop=dc.getScheduledDates(fd.getDop(), fd.getStartDate(), fd.getEndDate());
					
					
					for(int i=0;i<dop.size();i++)
					{
						
							rows.add(new Row(route,dop.get(i).split("-")[1],dop.get(i).split("-")[0],fd.getDepTimeUTC().substring(0, 2)+":"+fd.getDepTimeUTC().substring(2, 4),fd.getArrTimeUTC().substring(0, 2)+":"+fd.getArrTimeUTC().substring(2, 4),flightNo));
							System.out.println(dop.get(i));
					}
					//tbls.add(new Table(k,rows));
					
					//break;
				}
				
				//tbls.add(new Table(k.substring(0,1).toUpperCase()+k.substring(1).toLowerCase(),u.removeDuplicates(rows)));
				tbls.add(new Table(k,u.removeDuplicates(rows)));
				//break;
			}
			JasperReportUtil report = new JasperReportUtil();
			report.generatePfd(tbls,reg,reportHeader,outputPath);
		}
		ZipFileUtil zip= new ZipFileUtil();
		zip.convertToZip(outputPath);

	}
	public static void main(String[] args)
	{
		PdfGenerator cls = new PdfGenerator();
		cls.getPdf("01Jul20", "30Sep20", "C:/Users/admin/Downloads/sche-dat(10).dat","C:/ScheduledAANew");
		//cls.getPdf(args[0], args[1], args[2],args[3]);
				
	

		
	}
	
	public Map<String,String> getFlightInfoFromCSV(String csvData)
	{
		Map<String,String> result = new HashMap<String, String>();
		
		result.put("carCode", csvData.split(",")[0]);
		result.put("flightNo", csvData.split(",")[1]);
		result.put("startDate", csvData.split(",")[2]);
		result.put("endDate", csvData.split(",")[3]);
		result.put("arrStn", csvData.split(",")[4]);
		result.put("depTimeUTC", csvData.split(",")[5]);
		result.put("deptStn", csvData.split(",")[6]);
		result.put("arrTimeUTC", csvData.split(",")[7]);
		result.put("dop", csvData.split(",")[9]);
		result.put("type", csvData.split(",")[11]);
		
		
		return result;
		
	}
	public void loadCsvToList(LocalDate start,LocalDate end,String filePath)
	{
		//String filePath ="F:\\SAAFiles\\sche-dat (1).dat";
		//String filePath ="F:\\SAAFiles\\sche-dat.dat";
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
				if(!data.get("type").equals("F"))
				{
					if(getDateFromString(data.get("startDate")).isBefore(start))
					{
						if(getDateFromString(data.get("endDate")).isEqual(start))
						{
							//Start date =userdate 
							//End date=csvdate
							flightDetails.add(new FlightDetail(data.get("carCode"), data.get("flightNo"), start.format(frt), data.get("endDate"),data.get("arrStn"), data.get("deptStn"),data.get("arrTimeUTC") ,data.get("depTimeUTC"), "",data.get("dop") , "", ""));
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
							
				
			}
	
			
					Collections.sort(flightDetails);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public List<String> getCountriesFromList(String reg)
	{
		List<String> countries = new ArrayList<String>();
		AirportMapUtil util=new AirportMapUtil();
		String stn="";
		String region="";
		String country="";
		for(FlightDetail fd:flightDetails)
		{
			stn=fd.getArrStn().equals("SIN")?fd.getDeptStn():fd.getArrStn();
			region = util.getRegionFromAirport(stn);
			country = util.getCountryFromAirport(stn);
			if(region.equals(reg))
			{
				if(!countries.contains(country))
					countries.add(country);
			}
		}
		
		return countries;
	}
	public Map<String,List<FlightDetail>> getFlightForCountries(String reg)
	{
		Map<String,List<FlightDetail>> countryFlight=new HashMap<String, List<FlightDetail>>();
		List<FlightDetail> flights=null;
		AirportMapUtil util=new AirportMapUtil();
		String countryName="";
		String stn ="";
		for(String country:getCountriesFromList(reg))
		{

			flights=new ArrayList<FlightDetail>();
			System.out.println("Before Country :"+country+" List Size"+flights.size());
			
			for(FlightDetail fd:flightDetails)
			{
				stn=fd.getArrStn().equals("SIN")?fd.getDeptStn():fd.getArrStn();
				
				countryName = util.getCountryFromAirport(stn);
				if(countryName.equals(country))
				{
					flights.add(fd);
					System.out.println(stn+" "+fd.getDeptStn()+" "+fd.getArrStn());
				}
			}
			countryFlight.put(country, flights);
			System.out.println("After Country :"+country+" List Size"+flights.size());
		}
		return countryFlight;
		
	}
	LocalDate getDateFromString(String dt)
	{
		DateTimeFormatter frmt = DateTimeFormatter.ofPattern("ddMMMyy");
		return LocalDate.parse(dt,frmt);
	}
	
}
