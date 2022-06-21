package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;

@Repository
public class ItemRepository {
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setImagePath(rs.getString("image_path"));
		item.setDeleted(rs.getBoolean("deleted"));

		return item;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

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
	
	/**
	 * 商品名から曖昧検索.
	 * 
	 * @param name	商品名
	 * @return		検索結果
	 */
	public List<Item> findByName(String name){
		String sql = " SELECT id, name, description, price_m, price_l, image_path, deleted FROM items WHERE name LIKE :name ORDER BY price_m; ";
		name = "%" + name +"%";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		List<Item> itemList = template.query(sql, param, ITEM_ROW_MAPPER);
		return itemList;
	}

	/**
	 * 
	 * idをもとに商品を検索します.
	 * 
	 * @param id 商品ID
	 * @return 商品
	 */
	public Item findById(Integer id) {
		String sql = "select id, name, description, price_m, price_l, image_path, deleted From items Where id=:id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item item = template.queryForObject(sql, param, ITEM_ROW_MAPPER);

		return item;

	}

}
