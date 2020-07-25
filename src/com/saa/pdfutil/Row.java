package com.saa.pdfutil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class Row {
	private String route;
	private String month;
	private String dates;
	private String arrivalTime;
	private String departureTime;
	private String flightNumber;
	public Row(String route, String month, String dates,String departureTime, String arrivalTime, String flightNumber) {
		super();
		this.route = route;
		this.month = month;
		this.dates = dates;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.flightNumber = flightNumber;
	}
	
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDates() {
		return dates;
	}
	public void setDates(String dates) {
		this.dates = dates;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	
	public static Comparator<Row> compByRoute=new Comparator<Row>() {
		
		@Override
		public int compare(Row r1, Row r2) {
			// TODO Auto-generated method stub
			return r1.getRoute().substring(0, 3).compareTo(r2.getRoute().substring(0, 3));
		}
	};
	public static Comparator<Row> sortMonth=new Comparator<Row>() {
			
			@Override
			public int compare(Row d1, Row d2) {
				// TODO Auto-generated method stub
				Months m1=MonthUtil.getMonth(d1.month);
				Months m2=MonthUtil.getMonth(d2.month);
				return m1.compareTo(m2);
			}
		};
		public static Comparator<Row> sortTime=new Comparator<Row>() {
			
			@Override
			public int compare(Row d1, Row d2) {
				// TODO Auto-generated method stub
				DateTimeFormatter frmt=DateTimeFormatter.ofPattern("HH:mm");
				LocalTime time1=LocalTime.parse(d1.getArrivalTime(),frmt);
				LocalTime time2=LocalTime.parse(d2.getArrivalTime(),frmt);
				return time1.compareTo(time2);
			}
		};
	
	@Override
	public String toString() {
		return "Row [route=" + route + ", month=" + month + ", dates=" + dates
				+ ", arrivalTime=" + arrivalTime + ", departureTime="
				+ departureTime + ", flightNumber=" + flightNumber + "]";
	}


}
