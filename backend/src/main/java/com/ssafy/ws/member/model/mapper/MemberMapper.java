package com.ssafy.ws.member.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.ws.member.model.dto.MemberDto;

@Mapper
public interface MemberMapper {

	int idCheck(String userId) ;
	void joinMember(MemberDto memberDto) ;
	MemberDto loginMember(Map<String, String> map) ;

	/* Admin */
	List<MemberDto> listMember(Map<String, Object> map) ;
	MemberDto getMember(String userId) ;
	void updateMember(MemberDto memberDto) ;
	void deleteMember(String userId) ;
}
