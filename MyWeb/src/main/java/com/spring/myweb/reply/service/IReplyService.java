package com.spring.myweb.reply.service;

import java.util.List;

import com.spring.myweb.reply.dto.ReplyListResponseDTO;
import com.spring.myweb.reply.dto.ReplyRequestDTO;
import com.spring.myweb.reply.dto.ReplyUpdateRequestDTO;
import com.spring.myweb.reply.entity.Reply;

public interface IReplyService {
	
	//댓글 등록
	void replyRegist(ReplyRequestDTO dto);
	
	//목록 요청
	List<ReplyListResponseDTO> getList(int bno, int pageNum);
	
	//댓글 개수 (페이징, PageCreator는 사용하지 않습니다.)
	int getTotal(int bno);
	
	//비밀번호 확인
	String pwCheck(int rno);
	
	//댓글 수정
	String update(ReplyUpdateRequestDTO dto);
	
	//댓글 삭제
	String delete(int rno, String replyPw);

}
