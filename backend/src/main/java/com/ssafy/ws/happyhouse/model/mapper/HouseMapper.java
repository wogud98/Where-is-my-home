package com.ssafy.ws.happyhouse.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.ws.happyhouse.model.dto.HouseInfoDto;

@Mapper
public interface HouseMapper {

	List<Map<String, Object>> listArticle(Map<String, Object> param);

	int getTotalArticleCount(Map<String, Object> param);

	List getAreaKeyword(Map<String, Object> param);
	
	List<Map<String, Object>> getAptInfoList(Map<String, Object> param);
	
	int getTotalAptInfoListCount(Map<String, Object> param);
}
