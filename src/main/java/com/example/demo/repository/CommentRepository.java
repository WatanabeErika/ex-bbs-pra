package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Article;
import com.example.demo.domain.Comment;

@Repository
public class CommentRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Comment> COM_ROWMAPPER = (rs, i) ->{
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};
	
//	private static final ResultSetExtractor<List<Article>> ART_COM_RESULTSET = (rs) -> {
//		List<Article> articleList = new ArrayList<>();
//		
//		List<Comment> commentList = null;
//		
//		int beforeIdNum = 0;
//		
//		while(rs.next()) {
//			int nowIdNum = rs.getInt("a_id");
//			
//			if(nowIdNum != beforeIdNum) {
//				Article article = new Article();
//				article.setId(nowIdNum);
//				article.setName(rs.getString("a_name"));
//				commentList = new ArrayList<Comment>();
//				article.setCommentList(commentList);
//				articleList.add(article);
//			}
//			
//			if(rs.getInt("c_id") != 0) {
//				Comment comment = new Comment();
//				comment.setId(rs.getInt("c_id"));
//				comment.setName(rs.getString("c_name"));
//				commentList.add(comment);
//			}
//			
//			beforeIdNum = nowIdNum;
//		}
//		return articleList;
//	};
	
	/**
	 * @param articleId
	 * @return　コメント一覧表示
	 */
	public List<Comment> findByArticleId(Integer articleId){
		String sql = "SELECT id, name, content, article_id "
				+ "FROM comments "
				+ "WHERE article_id = :articleId";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		
		return template.query(sql, param, COM_ROWMAPPER);
	}
	
	/**
	 * @return 記事とコメント結合一覧表示
	 */
//	public List<Article> findCommentArticleList(){
//		String sql = "SELECT a.id, a.name, a.content, c.id, c.name, c.content, "
//				+ "FROM articles as a "
//				+ "LEFT OUTER JOIN comments as c "
//				+ "ON a.id = c.article_id "
//				+ "WHERE c.article_id = :articleId";
//	}
	
	/**
	 * @param comment
	 * コメント投稿
	 */
	public void insertComment(Comment comment) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		
		String sql = "INSERT INTO comments (name, content, article_id) "
				+ "VALUES (:name, :content, :articleId)";
		
		template.update(sql, param);
	}
	
	/**
	 * @param articleId
	 * コメント削除
	 */
	public void deleteComment(Integer articleId) {
		String sql = "DELETE FROM comments WHERE article_id = :articleId";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		
		template.update(sql, param);
	}
}
