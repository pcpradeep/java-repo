package com.saa.pdfutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.util.ClassLoaderResource;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleDocxExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class JasperReportUtil {
	
	public void generatePfd(List<Table> tables,String reg,String reportHeader,String outputPath)
	{
		//String filePath="E:\\JasperReportJrxml\\Practicereport.jrxml";
		//String filePath1 = "E:\\JasperReportJrxml\\Practicereport_subreport1.jrxml";
		//String filePath11 = "E:\\JasperReportJrxml\\Practicereport_subreport1_subreport1.jrxml";
		File file =new File(outputPath);
		Collections.sort(tables);
		
		
		UserFactory uf = new UserFactory();
		uf.setTabList(tables);
		
		//userfactory list
		List<UserFactory> uFactoryList = new  ArrayList<UserFactory>();
		
		uFactoryList.add(uf);
		String hour=String.valueOf(LocalDateTime.now().getHour());
		hour=hour.length()<2?"0"+hour:hour;
		String minutes=String.valueOf(LocalDateTime.now().getMinute());
		minutes=minutes.length()<2?"0"+minutes:minutes;
		String month=LocalDateTime.now().getMonth().name().substring(0,1).toUpperCase()+LocalDateTime.now().getMonth().name().substring(1).toLowerCase();
		String subReportHeader="Updated "+LocalDateTime.now().getDayOfMonth()+" "+month+" "+LocalDateTime.now().getYear()+", "+hour+minutes+"hrs (GMT+8)";
		
		
		
		
		JRBeanCollectionDataSource beanCollection = new JRBeanCollectionDataSource(uFactoryList);
		try
		{
			JRDocxExporter exporter = new JRDocxExporter();
			SimpleDocxExporterConfiguration config =new SimpleDocxExporterConfiguration();
			System.out.println("Before compiling.....");
			//Compile and create the report
			//JasperReport masterReport=JasperCompileManager.compileReport(filePath);
			JasperReport masterReport=JasperCompileManager.compileReport(this.getClass().getResourceAsStream("/pdf-templates/Practicereport.jrxml"));
			JasperReport subReport = JasperCompileManager.compileReport(this.getClass().getResourceAsStream("/pdf-templates/Practicereport_subreport1.jrxml"));
			JasperReport subSubReport = JasperCompileManager.compileReport(this.getClass().getResourceAsStream("/pdf-templates/Practicereport_subreport1_subreport1.jrxml"));
			
			System.out.println("After compiling.....");
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("subreportParameter", subReport);
			parameters.put("region", reg.toUpperCase());
			parameters.put("reportHeader", reportHeader);
			parameters.put("subReportHeader", subReportHeader);
			
			
			//Fill the report and create print page
			
			JasperPrint jasperPrint=JasperFillManager.fillReport(masterReport,parameters,beanCollection);
			if(!file.exists())
				file.mkdir();
			
			//exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			//exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputPath+"/"+reg+".docx"));
			
			//exporter.setConfiguration(config);
			//exporter.exportReport();
			//Export the print object
			JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath+"/"+reg+".pdf");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
}
