package com.ssafy.ws.happyhouse.model.dto;

public class HouseDealDto {

	private String no;
	String apartmentName;
	private String dealAmount;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private String area;
	private String floor;
	private String cancelDealType;
	private String date;
	private String addr;
	private long aptCode;

	public HouseDealDto() {}
	
	public HouseDealDto(String no, String apartmentName, String dealAmount, String area, String floor,
			String cancelDealType, String date, long aptCode, String addr) {
		this.no = no;
		this.apartmentName = apartmentName;
		this.dealAmount = dealAmount;
		this.area = area;
		this.floor = floor;
		this.cancelDealType = cancelDealType;
		this.date = date;
		this.aptCode = aptCode;
		this.addr = addr;
	}

	public String getDate() {
		return date;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getApartmentName() {
		return apartmentName;
	}

	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}

	public int getDealYear() {
		return dealYear;
	}

	public void setDealYear(int dealYear) {
		this.dealYear = dealYear;
	}

	public int getDealMonth() {
		return dealMonth;
	}

	public void setDealMonth(int dealMonth) {
		this.dealMonth = dealMonth;
	}

	public int getDealDay() {
		return dealDay;
	}

	public void setDealDay(int dealDay) {
		this.dealDay = dealDay;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getCancelDealType() {
		return cancelDealType;
	}

	public void setCancelDealType(String cancelDealType) {
		this.cancelDealType = cancelDealType;
	}

	public long getAptCode() {
		return aptCode;
	}

	public void setAptCode(long aptCode) {
		this.aptCode = aptCode;
	}

	@Override
	public String toString() {
		return "HouseDealDto [no=" + no + ", dealAmount=" + dealAmount + ", dealYear=" + dealYear + ", dealMonth="
				+ dealMonth + ", dealDay=" + dealDay + ", area=" + area + ", floor=" + floor + ", cancelDealType="
				+ cancelDealType + ", aptCode=" + aptCode + "]";
	}

}
