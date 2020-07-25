package com.saa.pdf;

import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;



public class FlightDetail implements Comparable<FlightDetail> {
	private String carCode;
	private String flightNo;
	private String startDate;
	private String endDate;
	private String arrStn;
	private String deptStn;
	private String arrTimeUTC;
	private String depTimeUTC;
	private String acType;
	private String dop;
	private String markFlightNo;
	private String serviceType;
	
	
	
	public FlightDetail(String carCode, String flightNo, String startDate,
			String endDate, String arrStn, String deptStn, String arrTimeUTC,
			String depTimeUTC, String acType, String dop, String markFlightNo,
			String serviceType) {
		super();
		this.carCode = carCode;
		this.flightNo = flightNo;
		this.startDate = startDate;
		this.endDate = endDate;
		this.arrStn = arrStn;
		this.deptStn = deptStn;
		this.arrTimeUTC = arrTimeUTC;
		this.depTimeUTC = depTimeUTC;
		this.acType = acType;
		this.dop = dop;
		this.markFlightNo = markFlightNo;
		this.serviceType = serviceType;
	}
	public String getCarCode() {
		return carCode;
	}
	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getArrStn() {
		return arrStn;
	}
	public void setArrStn(String arrStn) {
		this.arrStn = arrStn;
	}
	public String getDeptStn() {
		return deptStn;
	}
	public void setDeptStn(String deptStn) {
		this.deptStn = deptStn;
	}
	public String getArrTimeUTC() {
		return arrTimeUTC;
	}
	public void setArrTimeUTC(String arrTimeUTC) {
		this.arrTimeUTC = arrTimeUTC;
	}
	public String getDepTimeUTC() {
		return depTimeUTC;
	}
	public void setDepTimeUTC(String depTimeUTC) {
		this.depTimeUTC = depTimeUTC;
	}
	public String getAcType() {
		return acType;
	}
	public void setAcType(String acType) {
		this.acType = acType;
	}
	public String getDop() {
		return this.dop;
	}
	public void setDop(String dop) {
		this.dop = dop;
	}
	public String getMarkFlightNo() {
		return this.markFlightNo;
	}
	public void setMarkFlightNo(String markFlightNo) {
		this.markFlightNo = markFlightNo;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	@Override
	public String toString() {
		return "FlightDetail [carCode=" + carCode + ", flightNo=" + flightNo
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", arrStn=" + arrStn + ", deptStn=" + deptStn
				+ ", arrTimeUTC=" + arrTimeUTC + ", depTimeUTC=" + depTimeUTC
				+ ", acType=" + acType + ", dop=" + dop + ", markFlightNo="
				+ markFlightNo + ", serviceType=" + serviceType + "]";
	}
	@Override
	public int compareTo(FlightDetail f) {
		// TODO Auto-generated method stub
		DateTimeFormatter frt = DateTimeFormatter.ofPattern("ddMMMyy");
		LocalDate sDate1 = LocalDate.parse(startDate, frt);
		LocalDate sDate2 = LocalDate.parse(f.getStartDate(), frt);
		return sDate1.compareTo(sDate2);
	}
	
	
	
	
	

}
