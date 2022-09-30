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
public class ArticleRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
//	private static final RowMapper<Article> ART_ROW_MAPPER = (rs, i) -> {
//		Article article = new Article();
//		article.setId(rs.getInt("id"));
//		article.setName(rs.getString("name"));
//		article.setContent(rs.getString("content"));
//		return article;
//	};
	
	private static final ResultSetExtractor<List<Article>> ART_COM_RESULTSET = (rs) -> {
		List<Article> articleList = new ArrayList<>();
		
		List<Comment> commentList = null;
		
		int beforeIdNum = 0;
		
		while(rs.next()) {
			int nowIdNum = rs.getInt("a_id");
			
			if(nowIdNum != beforeIdNum) {
				Article article = new Article();
				article.setId(nowIdNum);
				article.setName(rs.getString("a_name"));
				article.setContent(rs.getString("a_content"));
				commentList = new ArrayList<Comment>();
				article.setCommentList(commentList);
				articleList.add(article);
			}
			
			if(rs.getInt("c_id") != 0) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("c_id"));
				comment.setName(rs.getString("c_name"));
				comment.setContent(rs.getString("c_content"));
				comment.setArticleId(rs.getInt("c_article_id"));
				commentList.add(comment);
			}
			
			beforeIdNum = nowIdNum;
		}
		return articleList;
	};
	
	/**
	 * @return 記事一覧表示
	 */
//	public List<Article> findAll(){
//		String sql ="SELECT id, name, content FROM articles ORDER BY id DESC";
//		
//		return template.query(sql, ART_ROW_MAPPER);
//	}
	
	/**
	 * @return 記事とコメント結合一覧表示
	 */
	public List<Article> findCommentArticleList(){
		String sql = "SELECT a.id as a_id, a.name as a_name, "
				+ "a.content as a_content, c.id as c_id, c.name as c_name, "
				+ "c.content as c_content, c.article_id as c_article_id "
				+ "FROM articles as a "
				+ "LEFT OUTER JOIN comments as c "
				+ "ON a.id = c.article_id "
				+ "ORDER BY a.id DESC";
		
		return template.query(sql, ART_COM_RESULTSET);
	}
	
	/**
	 * 記事投稿
	 */
	public void articleInsert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		
		String sql = "INSERT INTO articles (name, content) "
				+ "VALUES (:name, :content)";
		
		template.update(sql, param);
	}
	
	/**
	 * @param id
	 * 記事削除
	 */
	public void articleDelete(Integer id) {
		String sql ="DELETE FROM articles WHERE id = :id";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		
		template.update(sql, param);
	}
}
