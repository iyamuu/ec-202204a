package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.example.domain.Item;

public class ToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Item> TOPPING_ROW_MAPPER = new BeanPropertyRowMapper<>(Item.class);

	/**
	 * 全検索.
	 * 
	 * @return 全てのトッピング情報
	 */
	public List<Item> findAll() {
		String sql = " SELECT id, name, price_m, price_l FROM toppings ORDER BY id; ";
		List<Item> itemList = template.query(sql, TOPPING_ROW_MAPPER);
		return itemList;
	}

}
