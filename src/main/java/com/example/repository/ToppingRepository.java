package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Topping;

/**
 * トッピング情報を操作するリポジトリ.
 * 
 * @author kohei.yamamura
 *
 */
@Repository
public class ToppingRepository {

	private static final RowMapper<Topping> TOPPING_ROW_MAPPER = (rs, i) -> {
		Topping topping = new Topping();
		topping.setId(rs.getInt("id"));
		topping.setName(rs.getString("name"));
		topping.setPriceM(rs.getInt("price_m"));
		topping.setPriceL(rs.getInt("price_l"));

		return topping;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 
	 * toppingsテーブルを全件検索します.
	 * 
	 * @return トッピング一覧
	 */
	public List<Topping> findAll() {
		String sql = " SELECT id, name, price_m, price_l FROM toppings ORDER BY id; ";
		List<Topping> tppingList = template.query(sql, TOPPING_ROW_MAPPER);
		return tppingList;

	}

}
