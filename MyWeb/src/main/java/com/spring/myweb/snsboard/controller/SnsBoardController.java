package com.spring.myweb.snsboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.spring.myweb.snsboard.service.SnsBoardService;

import lombok.RequiredArgsConstructor;

@RestController //이동기로 쓰기 위해서 (리스폰스바디를 쓰겠다는 뜻)
@RequestMapping("/snsboard")
@RequiredArgsConstructor
public class SnsBoardController {
	
	private SnsBoardService service;

	@GetMapping("/snsList")
	public ModelAndView snsList() {
		ModelAndView mv = new ModelAndView();
//		mv.addObject("name", "value");
		mv.setViewName("snsboard/snsList");
		return mv;
	}
	
}
