package com.saa.pdfutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileUtil {
	
	public void convertToZip(String zipPath)
	{
		System.out.println(zipPath);
		//File file=null;
		FileOutputStream fo=null;
		ZipOutputStream zo=null;
		FileInputStream fs=null;
		ZipEntry entry=null;
		File f= new File(zipPath);
		try {
			fo = new FileOutputStream(zipPath+"/scheduledflights.zip");
			zo = new ZipOutputStream(fo);
			for(File file:f.listFiles())
			{
				//file=new File(filePaths.get(i));
				if(file.getName().endsWith(".pdf")||file.getName().endsWith(".docx"))
				{
					entry = new ZipEntry(file.getName());
					zo.putNextEntry(entry);
					fs = new FileInputStream(file);
					byte[] buffer=new byte[1024];
					int len;
					while((len=fs.read(buffer))>0)
					{
						zo.write(buffer,0,len);
					}
					zo.flush();
					zo.closeEntry();
					fs.close();
					file.delete();
				}
				
				
			}
			
			
			zo.close();
			
			
			fo.close();
			//deleteFlies(zipPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			
		}
		
	}
	File[] deleteFlies(String dirPath)
	{
		System.out.println("Inside delete files");
		File file=new File(dirPath);
		//file.delete();
		
		for(File f:file.listFiles())
		{
			
			if(f.getName().endsWith(".pdf")||f.getName().endsWith(".docx"))
				f.delete();
			
			
		}
		return file.listFiles();
	}

}
