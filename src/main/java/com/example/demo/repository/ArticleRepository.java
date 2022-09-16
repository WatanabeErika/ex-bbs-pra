package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Article;

@Repository
public class ArticleRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Article> ART_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;
	};
	
	/**
	 * @return 記事一覧表示
	 */
	public List<Article> findAll(){
		String sql ="SELECT id, name, content FROM articles ORDER BY id DESC";
		
		return template.query(sql, ART_ROW_MAPPER);
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
