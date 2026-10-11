package com.ssafy.ws.board.model.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardListDto {

	private List<BoardDto> articles;
	private int currentPage;
	private int totalPageCount;

}
