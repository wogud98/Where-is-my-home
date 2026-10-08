package com.ssafy.ws.happyhouse.model.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AptInfoListDto {
	private List<Map<String, Object>> aptInfos;
	private int currentPage;
	private int totalPageCount;
}
