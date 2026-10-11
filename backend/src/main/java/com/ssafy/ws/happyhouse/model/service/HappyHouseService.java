package com.ssafy.ws.happyhouse.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.ws.happyhouse.model.dto.AptInfoListDto;
import com.ssafy.ws.happyhouse.model.dto.HouseDealDto;
import com.ssafy.ws.happyhouse.model.dto.HouseSearchListDto;

public interface HappyHouseService {

	HouseSearchListDto listArticle(Map<String, String> map);

	List getAreaKeyword(String param);

	AptInfoListDto getAptInfoList(Map<String, Object> map);

}
