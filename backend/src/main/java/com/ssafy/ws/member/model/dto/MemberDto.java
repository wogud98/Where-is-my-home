package com.ssafy.ws.member.model.dto;

import com.ssafy.ws.happyhouse.model.dto.DongCodeDto;
import com.ssafy.ws.happyhouse.model.dto.HouseDealDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

	private String userId;
	private String password;
	private String name;
	private String registerDate;
	
	@Override
	public String toString() {
		return "MemberDto [userId=" + userId + ", password=" + password + ", name=" + name + ", registerDate="
				+ registerDate + "]";
	} 

}
