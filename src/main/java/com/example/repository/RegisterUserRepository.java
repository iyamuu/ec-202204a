package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

/**
 * ユーザー登録処理をするリポジトリ.
 * 
 * @author takato.tomizawa
 *
 */
@Repository
public class RegisterUserRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * ユーザー登録処理.
	 * 
	 * @param user ユーザー情報
	 */
	public void save(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "INSERT INTO users(name, email, password, zipcode, address, telephone) VALUES (:name, :email, :password, :zipcode, :address, :telephone);";
		template.update(sql, param);
	}
}
