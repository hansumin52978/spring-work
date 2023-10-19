package com.spring.myweb.reply.service;

import java.util.List;

import com.spring.myweb.reply.dto.ReplyListResponseDTO;
import com.spring.myweb.reply.dto.ReplyRegistDTO;
import com.spring.myweb.reply.entity.Reply;

public interface IReplyService {
	
	//댓글 등록
	void replyRegist(ReplyRegistDTO dto);
	
	//목록 요청
	List<ReplyListResponseDTO> getList(int bno, int pageNum);
	
	//댓글 개수 (페이징, PageCreator는 사용하지 않습니다.)
	int getTotal(int bno);
	
	//비밀번호 확인
	String pwCheck(int rno);
	
	//댓글 수정
	void update(Reply reply);
	
	//댓글 삭제
	void delete(int rno);

}
