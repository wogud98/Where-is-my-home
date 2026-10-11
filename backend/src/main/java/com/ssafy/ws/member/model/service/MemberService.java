package com.ssafy.ws.member.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.ws.member.model.dto.MemberDto;

public interface MemberService {
	
	int idCheck(String userId);
	void joinMember(MemberDto memberDto);
	Map<String, Object> loginMember(Map<String, String> map);
	
	/* Admin */
	List<MemberDto> listMember(Map<String, Object> map);
	MemberDto getMember(String userId);
	void updateMember(MemberDto memberDto);
	void deleteMember(String userId);
}
