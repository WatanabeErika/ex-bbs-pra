package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Article;
import com.example.demo.domain.Comment;
import com.example.demo.form.ArticleForm;
import com.example.demo.form.CommentForm;
import com.example.demo.service.ArticleService;
import com.example.demo.service.CommentService;

@Controller
@RequestMapping("/ex-bbs")
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CommentService commentService;
	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}
	
	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	
	/**
	 * @return 記事一覧表示
	 */
	@RequestMapping("/articleShowList")
	public String articleShowList(Model model) {
//		List<Article> articleList = articleService.findAll();
//		
//		articleList.forEach(article -> {
//			List<Comment> commentList = commentService.findByArticleId(article.getId());
//			article.setCommentList(commentList);
//		});
		
		List<Article> articleList = articleService.findCommentArticleList();
		
		model.addAttribute("articleList", articleList);
		
		return "bbs";
	}
	
	/**
	 * 記事投稿
	 */
	@RequestMapping("/articleInsert")
	public String articleInsert(ArticleForm form) {
		Article article = new Article();
		
		BeanUtils.copyProperties(form, article);
		
		articleService.articleInsert(article);
		
		return "redirect:/ex-bbs/articleShowList";
	}
	
	/**
	 * @param comment
	 * コメント投稿
	 */
	@RequestMapping("/insertComment")
	public String insertComment(CommentForm form, Integer id) {
		Comment comment = new Comment();
		
		BeanUtils.copyProperties(form, comment);
		
		comment.setArticleId(id);
		commentService.insertComment(comment);
		
		return "redirect:/ex-bbs/articleShowList";
	}
	
	/**
	 * @return　記事＆コメント削除
	 * 
	 */
	@RequestMapping("/delete")
	public String delete(Integer id) {
		
		Comment comment = new Comment();
		
		comment.setArticleId(id);
		
		articleService.articleDelete(id);
		
		commentService.deleteComment(comment.getArticleId());
		
		return "redirect:/ex-bbs/articleShowList";
	}
	
}
