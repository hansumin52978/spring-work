package com.spring.myweb.freeboard.service;

import java.util.List;

import com.spring.myweb.freeboard.dto.FreeModifyRequestDTO;
import com.spring.myweb.freeboard.dto.FreeRegistRequestDTO;
import com.spring.myweb.freeboard.dto.page.Page;
import com.spring.myweb.freeboard.dto.response.FreeDetailResponseDTO;
import com.spring.myweb.freeboard.dto.response.FreeListResponseDTO;
import com.spring.myweb.freeboard.entity.FreeBoard;

public interface IFreeBoardService {
	
		//글 등록
		void regist(FreeRegistRequestDTO dto);
			
		//글 목록
		List<FreeListResponseDTO> getList(Page page);
		
		//총 개시물 개수
		int getTotal(Page page);
			
		//상세보기
		FreeDetailResponseDTO getContent(int bno);
			
		//수정
		void update(FreeModifyRequestDTO freeboard);
			
		//삭제
		void delete(int bno);

}
