package com.spring.basic.board.dto;

import com.spring.basic.board.entity.Board;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardModifyRequestDTO {
	
	private int boardNo;
	private String writer;
	private String title;
	private String content;
	
	public BoardModifyRequestDTO(Board board) {
	      this.boardNo = board.getBoardNo();
	      this.writer = board.getWriter();
	      this.title = board.getTitle();
	      this.content = board.getContent();
	   }

}
