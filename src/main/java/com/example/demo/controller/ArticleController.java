package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Article;
import com.example.demo.service.ArticleService;

@Controller
@RequestMapping("/ex-bbs")
public class ArticleController {
	@Autowired
	private ArticleService service;
	
	/**
	 * @return 記事一覧表示
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Article> articleList = service.findAll();
		
		model.addAttribute("articleList", articleList);
		
		return "bbs";
	}
}
