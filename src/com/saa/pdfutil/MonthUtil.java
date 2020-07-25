package com.saa.pdfutil;



enum Months{
	JANUARY,FEBRUARY,MARCH,APRIL,MAY,JUNE,JULY,AUGUST,SEPTEMBER,OCTOBER,NOVEMBER,DECEMBER
}
class MonthUtil
{
	static Months getMonth(String mName)
	{
		if(mName.equalsIgnoreCase("JANUARY"))
			return Months.JANUARY;
		else if(mName.equalsIgnoreCase("FEBRUARY"))
			return Months.FEBRUARY;
		else if(mName.equalsIgnoreCase("MARCH"))
			return Months.MARCH;
		else if(mName.equalsIgnoreCase("APRIL"))
			return Months.APRIL;
		else if(mName.equalsIgnoreCase("MAY"))
			return Months.MAY;
		else if(mName.equalsIgnoreCase("JUNE"))
			return Months.JUNE;
		else if(mName.equalsIgnoreCase("JULY"))
			return Months.JULY;
		else if(mName.equalsIgnoreCase("AUGUST"))
			return Months.AUGUST;
		else if(mName.equalsIgnoreCase("SEPTEMBER"))
			return Months.SEPTEMBER;
		else if(mName.equalsIgnoreCase("OCTOBER"))
			return Months.OCTOBER;
		else if(mName.equalsIgnoreCase("NOVEMBER"))
			return Months.NOVEMBER;
		else if(mName.equalsIgnoreCase("DECEMBER"))
			return Months.DECEMBER;
		else
			return null;
		
	}
}


