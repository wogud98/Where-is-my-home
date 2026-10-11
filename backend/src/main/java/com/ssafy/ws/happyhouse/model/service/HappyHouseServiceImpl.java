package com.ssafy.ws.happyhouse.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.ws.happyhouse.model.dto.AptInfoListDto;
import com.ssafy.ws.happyhouse.model.dto.HouseInfoDto;
import com.ssafy.ws.happyhouse.model.dto.HouseSearchListDto;
import com.ssafy.ws.happyhouse.model.mapper.HouseMapper;

@Service
public class HappyHouseServiceImpl implements HappyHouseService {

	private HouseMapper houseMapper;
	
    @Autowired
    public HappyHouseServiceImpl(HouseMapper houseMapper) {
        this.houseMapper = houseMapper;
    }
    
	@Override
	public HouseSearchListDto listArticle(Map<String, String> map) {
		Map<String, Object> param = new HashMap<String, Object>();
		// 검색 관련 처리
		// 검색에 필요한 키워드 param에 저장
		// key에 해당하는 value가 null이라면, "" 저장
		param.put("lat"		, map.get("lat")	== null? "":map.get("lat"));	//위도
		param.put("lng"		, map.get("lng")	== null? "":map.get("lng"));	//경도
	
		
		param.put("dis"		, Integer.parseInt(map.get("dis")) >= 9 ? "8" : map.get("dis"));	//레벨 별 조회 범위(거리)
//		System.out.println("Is dis:"+ (Integer.parseInt(map.get("dis")) >= 9 ? "8" : map.get("dis")));
		param.put("word"	, map.get("word")	== null? "":map.get("word"));	//검색어
		
		//페이지 정보 처리
		int currentPage = Integer.parseInt(map.get("pgno")	== null ? "1"	: map.get("pgno"));	//현재 페이지
		int sizePerPage = Integer.parseInt(map.get("spp")	== null ? "20"	: map.get("spp"));	//한페이지 당 보여줄 게시글 수
		int start = currentPage * sizePerPage - sizePerPage;		
		param.put("start", start);
		param.put("listsize", sizePerPage);
		
		//Mapper 호출  
		System.out.println(param);
		List<Map<String, Object>> list = houseMapper.listArticle(param); 
		System.out.println("조회 결과 : "+list);
		HouseSearchListDto houseSearchListDto = new HouseSearchListDto();
		int totalArticleCount = houseMapper.getTotalArticleCount(param);	// DB조회 정보의 수
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;		// 전체 페이지 수 
		
		houseSearchListDto.setArticles(list);
		houseSearchListDto.setCurrentPage(currentPage);
		houseSearchListDto.setTotalPageCount(totalPageCount);		
		
		return houseSearchListDto;
	}

	@Override
	public List getAreaKeyword(String word) {
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("word", word);
		List list = houseMapper.getAreaKeyword(param);
		System.out.println("키워드 : "+list);
		return list;
	}

	@Override
	public AptInfoListDto getAptInfoList(Map<String, Object> map) {
		Map<String, Object> param = new HashMap<String, Object>();
		
		long aptCode = Long.parseLong((String) map.get("aptCode"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : (String) map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : (String) map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("aptCode", aptCode);
		param.put("start", start);
		param.put("listsize", sizePerPage);
		
		AptInfoListDto aptInfoListDto = new AptInfoListDto();
		
		int totalArticleCount = houseMapper.getTotalAptInfoListCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;
		
		aptInfoListDto.setAptInfos(houseMapper.getAptInfoList(param));
		aptInfoListDto.setCurrentPage(currentPage);
		aptInfoListDto.setTotalPageCount(totalPageCount);
		
		return aptInfoListDto;
	}
}
