package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Comment;
import com.example.demo.repository.CommentRepository;



@Service
@Transactional
public class CommentService {
	@Autowired
	private CommentRepository repository;
	
	/**
	 * @param articleId
	 * @return　コメント一覧表示
	 */
	public List<Comment> findByArticleId(Integer articleId){
		return repository.findByArticleId(articleId);
	}
	
	
}
