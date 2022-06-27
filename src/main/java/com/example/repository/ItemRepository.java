package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;

/**
 * 商品情報を操作するリポジトリ.
 * 
 * @author kohei.yamamura
 *
 */
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
	 * @param sort 並び順の設定
	 * @return 全ての商品情報
	 */
	public List<Item> findAll(String sort) {
		
		String sql = " SELECT id, name, description, price_m, price_l, image_path, deleted FROM items ORDER BY price_m";
		if(sort != null && sort.equals("DESC")) {
			sql = sql + " DESC";
		}
		List<Item> itemList = template.query(sql, ITEM_ROW_MAPPER);
		return itemList;
	}
	
	/**
	 * 商品名から曖昧検索.
	 * 
	 * @param name	商品名
	 * @param sort	並び順の設定
	 * @return		検索結果
	 */
	public List<Item> findByName(String name, String sort){
		String sql = " SELECT id, name, description, price_m, price_l, image_path, deleted FROM items WHERE name ILIKE :name ORDER BY price_m";
		name = "%" + name +"%";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		if(sort != null && sort.equals("DESC")) {
			sql = sql + " DESC";
		}
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
	
	/*
	 * 商品テーブルの中で一番大きいID + 1(プライマリーキー=主キー)を取得する.
	 * 
	 * @return テーブル内で一番値が大きいID + 1.データがない場合は1
	 */
	private Integer getPrimaryId() {
		try {
			Integer maxId = template.queryForObject("SELECT MAX(id) FROM items;", new MapSqlParameterSource(),
					Integer.class);
			return maxId + 1;
		} catch (DataAccessException e) {
			// データが存在しない場合
			return 1;
		}
	}
	
	/**
	 * 商品を追加します.
	 * 
	 * @param item 商品情報
	 */
	public void insert(Item item) {
		item.setId(getPrimaryId());
		
		String sql = "insert into items (id, name, description, price_m, price_l, image_path) values (:id, :name, :description, :priceM, :priceL, :imagePath)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql, param);
	}

	/**
	 * 商品の値段を変更します
	 * 
	 * @param item
	 */
	public void updatePrice(Item item) {
		String sql = "update items set price_m = :priceM, price_l = :priceL where id = :id";
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql, param);
	}
}
