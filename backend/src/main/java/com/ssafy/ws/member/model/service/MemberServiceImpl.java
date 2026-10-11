package com.ssafy.ws.member.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ssafy.ws.member.controller.MemberController;
import com.ssafy.ws.member.model.dto.MemberDto;
import com.ssafy.ws.member.model.mapper.MemberMapper;
import com.ssafy.ws.member.util.JWTUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j // log Annotation
public class MemberServiceImpl implements MemberService {
		
	private final MemberMapper memberMapper;
	private final JWTUtil jwtUtil;

	@Override
	public int idCheck(String userId) {
		log.debug("memberServiceImpl idCheck member_id : {}", userId);
		return memberMapper.idCheck(userId);
	}

	@Override
	public void joinMember(MemberDto memberDto) {
		log.debug("membererSviceImpl joinMember memberDto : {}", memberDto);
		memberMapper.joinMember(memberDto);		
	}

	@Override
	public Map<String, Object> loginMember(Map<String, String> map) {
		log.debug("memberServiceImpl loginMember map : {}", map);
		MemberDto loginMember = memberMapper.loginMember(map);
		
		// 해당 유저 정보 없음
		if(loginMember==null) return null;
		// 토큰 생성
		String token = jwtUtil.generateToken(loginMember);
		// 응답 데이터에 토큰 정보 구성
		Map<String, Object> tokenResponse = new HashMap<>();
		tokenResponse.put("token", token);
		
		return tokenResponse;
	}

	@Override
	public List<MemberDto> listMember(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberDto getMember(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateMember(MemberDto memberDto) {
		memberMapper.updateMember(memberDto);
		
	}

	@Override
	public void deleteMember(String userId) {
		memberMapper.deleteMember(userId);		
	}	
	
}
