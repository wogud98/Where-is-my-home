package com.ssafy.ws.member.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ws.member.model.dto.MemberDto;
import com.ssafy.ws.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/{userId}")
	public String idCheck(@PathVariable("userId") String userId){
		log.debug("MemberController idCheck user_id : {}", userId);
		int cnt = memberService.idCheck(userId);
		return cnt + "";		
	}

	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody MemberDto memberDto){
		log.debug("MemberController join memberDto info : {}", memberDto);
		memberService.joinMember(memberDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(memberDto);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> map){
		log.debug("MemberController login memberDto info : {}", map);
		Map<String, Object> loginInfo = memberService.loginMember(map);
		
		//로그인 실패 시 401응답
		if(loginInfo==null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		return ResponseEntity.ok(loginInfo);
	}
	
	@PutMapping("/edit")
	public ResponseEntity<?> edit(@RequestBody MemberDto memberDto) {
		log.debug("MemberController edit memberDto info : {}", memberDto);
		memberService.updateMember(memberDto);
		return ResponseEntity.ok(memberDto);
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<?> delete(@PathVariable("userId") String userId) {
		log.debug("MemberController delete userId info : {}", userId);
		memberService.deleteMember(userId);
		return ResponseEntity.ok(userId);
	}
}
