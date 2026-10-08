package com.ssafy.ws.happyhouse.model.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
public class HouseSearchListDto {
	
	private List<Map<String, Object>> articles;
	private int currentPage;
	private int totalPageCount;
}
