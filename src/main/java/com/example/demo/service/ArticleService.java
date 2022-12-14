package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Article;
import com.example.demo.repository.ArticleRepository;

@Service
@Transactional
public class ArticleService {
	@Autowired
	private ArticleRepository repository;
	
	/**
	 * @return 記事一覧表示
	 */
//	public List<Article> findAll(){
//		return repository.findAll();
//	}
	
	/**
	 * @return 記事とコメント結合一覧表示
	 */
	public List<Article> findCommentArticleList(){
		return repository.findCommentArticleList();
	}
	
	/**
	 * 記事投稿
	 */
	public void articleInsert(Article article) {
		repository.articleInsert(article);
	}
	
	/**
	 * @param id
	 * 記事削除
	 */
	public void articleDelete(Integer id) {
		repository.articleDelete(id);
	}
	
}
