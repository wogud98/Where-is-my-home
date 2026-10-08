package com.ssafy.ws.happyhouse.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ws.happyhouse.model.dto.AptInfoListDto;
import com.ssafy.ws.happyhouse.model.dto.HouseSearchListDto;
import com.ssafy.ws.happyhouse.model.service.HappyHouseService;

import lombok.extern.slf4j.Slf4j;

//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
//@CrossOrigin(origins = "http://localhost:5173", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE }, maxAge = 6000)
@RestController
@RequestMapping("/house")
@Slf4j
public class HappyHouseController {
	
	private HappyHouseService happyHouseService;

	public HappyHouseController(HappyHouseService happyHouseService) {
		this.happyHouseService = happyHouseService;
	}
	
	
	@GetMapping
	public ResponseEntity<?> listArticle(
			@RequestParam Map<String, String> map){
		System.out.println("조회요청 수신");
		//Logger.info("listArticle map - {}", map);
		try {
			HouseSearchListDto houseSearchListDto = happyHouseService.listArticle(map);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(houseSearchListDto);

		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/area")
	public ResponseEntity<?> getAreaKeyword(@RequestParam("word") String word){
		System.out.println("검색어 추천 키워드 요청 수신: "+ word);
		try {
			List list = happyHouseService.getAreaKeyword(word);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(list);
			
		} catch ( Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/detail")
	public ResponseEntity<?> getAptInfoList(@RequestParam Map<String, Object> map) {
		AptInfoListDto aptInfoListDto = happyHouseService.getAptInfoList(map); 
		System.out.println("HappyHouseController getAptInfoList의 aptInfoListDto : "+aptInfoListDto);
		HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return ResponseEntity.ok().headers(header).body(aptInfoListDto);
	}
	

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
