package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;

/**
 * 商品を操作するリポジトリ.
 * 
 * @author kohei.yamamura
 *
 */
@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Item> ITEM_ROW_MAPPER = new BeanPropertyRowMapper<>(Item.class);

	
	/**
	 * 全検索.
	 * 
	 * @return 全ての商品情報
	 */
	public List<Item> findAll() {
		String sql = " SELECT id, name, description, price_m, price_l, image_path, deleted FROM items ORDER BY price_m; ";
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	public List<Item> findByName(String name){
		String sql = " SELECT id, name, description, price_m, price_l, image_path, deleted FROM items WHERE name LIKE :name ORDER BY price_m; ";
		name = "" + name +"";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}

}
