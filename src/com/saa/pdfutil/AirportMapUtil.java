package com.saa.pdfutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import net.sf.jasperreports.engine.util.ClassLoaderResource;

public class AirportMapUtil {
	public static void main(String[] args)
	{
		AirportMapUtil u= new AirportMapUtil();
		System.out.println("Region:"+u.getRegionFromAirport("BKC").toUpperCase());
		System.out.println("Country:"+u.getCountryFromAirport("BKC").toUpperCase());
	}
	public String getRegionFromAirport(String airport)
	{
		String region="Not found";
		try {
			//FileReader fr = new FileReader(new File("F:\\SAAFiles\\AirportCodeandRegion.csv"));
			//FileReader fr = new FileReader(new File("F:\\SAAFiles\\Airport code and region.csv"));
			//URL url=this.getClass().getResource("/com/saa/pdfutil/Airportcodeandregion.csv");
			//FileReader fr = new FileReader(new File("com/saa/pdfutil/Airportcodeandregion.csv"));
			//InputStreamReader inr=new InputStreamReader(getClass().getResourceAsStream("AirportCodeandRegion.csv"));
			InputStreamReader inr=new InputStreamReader(AirportMapUtil.class.getResourceAsStream("AirportCodeandRegion.csv"));

			
			//FileReader fr = new FileReader(new File("JasperPdfGenerator/src/airportmapping/Airport code and region.csv"));
			BufferedReader br = new BufferedReader(inr);
			String line = "";
			
			
			while((line=br.readLine())!=null)
			{
				if(line.contains(airport))
				{
					//region=line.split(",")[1];
					region=line.split(",")[3];
					return region;
				}
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return region;
	}
	public String getCountryFromAirport(String airport)
	{
		String country = "Not found";
		
		try {
			//FileReader fr = new FileReader(new File("F:\\SAAFiles\\AirportCodeandRegion.csv"));
			//FileReader fr = new FileReader(new File("F:\\SAAFiles\\Airport code and region.csv"));
			//URL url=AirportMapUtil.class.getResource("AirportCodeandRegion.csv");
			InputStreamReader inr=new InputStreamReader(AirportMapUtil.class.getResourceAsStream("AirportCodeandRegion.csv"));
			//FileReader fr = new FileReader(new File(url.getPath()));
			BufferedReader br = new BufferedReader(inr);
			String line ="";
			while((line = br.readLine())!= null)
			{
				if(line.contains(airport))
				{
					//country = line.split(",")[2];
					country = line.split(",")[2];
					if(country.contains("-"))
					{
							
						country=country.replace("-", ",");
					}
					return country;
				}
			}
		} catch (FileNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return country;
	}
	
	public String getAirportNameFromAirport(String airport)
	{
		String airportName = "Not found";
		
		try {
			//FileReader fr = new FileReader(new File("F:\\SAAFiles\\AirportCodeandRegion.csv"));
			//FileReader fr = new FileReader(new File("F:\\SAAFiles\\Airport code and region.csv"));
			//URL url=AirportMapUtil.class.getResource("AirportCodeandRegion.csv");
			InputStreamReader inr=new InputStreamReader(AirportMapUtil.class.getResourceAsStream("AirportCodeandRegion.csv"));
			//FileReader fr = new FileReader(new File(url.getPath()));
			BufferedReader br = new BufferedReader(inr);
			String line ="";
			while((line = br.readLine())!= null)
			{
				if(line.contains(airport))
				{
					//country = line.split(",")[2];
					airportName = line.split(",")[1];
					return airportName;
				}
			}
		} catch (FileNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return airportName;
	}

}
