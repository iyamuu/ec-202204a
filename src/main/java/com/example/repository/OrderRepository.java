package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;
import com.example.domain.User;

@Repository
public class OrderRepository {
	
	private static final ResultSetExtractor<List<Order>> ORDER_ITEM_TOPPING_EXTRACTER = (rs) -> {
		List<Order> orderList = new ArrayList<>();
		List<OrderItem> orderItemList = null;
		List<OrderTopping> orderToppingList = null;
		List<Topping> toppingList = null;
		
		long beforeOrderId = 0;
		long beforeOrderItemId = 0;
		
		while (rs.next()) {
			int nowOrderId = rs.getInt("o_id");
			
			if (nowOrderId != beforeOrderId) {
				Order order = new Order();
				order.setId(nowOrderId);
				order.setUserId(null);
				order.setStatus(rs.getInt("status"));
				order.setTotalPrice(rs.getInt("total_price"));
				order.setOrderDate(rs.getDate("order_date"));
				order.setDestinationName(rs.getString("destination_name"));
				order.setDestinationEmail(rs.getString("destination_email"));
				order.setDestinationZipCode(rs.getString("destination_zipcode"));
				order.setDestinationAddress(rs.getString("destination_address"));
				order.setDestinationTel(rs.getString("destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("delivery_time"));
				order.setPaymentMethod(rs.getInt("payment_method"));
				
				User user = new User();
				user.setId(rs.getInt("u_id"));
				user.setName(rs.getString("u_name"));
				user.setEmail(rs.getString("email"));
				user.setPassword(rs.getString("password"));
				user.setZipcode(rs.getString("zipcode"));
				user.setAddress(rs.getString("address"));
				user.setTelephone(rs.getString("telephone"));
				order.setUser(user);
				
				orderItemList = new ArrayList<>();
				order.setOrderItemList(orderItemList);
				
				orderList.add(order);
			}
			
			int nowOrderItemId = rs.getInt("oi_id");
			
			if (beforeOrderItemId != nowOrderItemId) {
				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt("oi_id"));
				orderItem.setItemId(rs.getInt("item_id"));
				orderItem.setOrderId(rs.getInt("order_id"));
				orderItem.setQuantity(rs.getInt("quantity"));
				orderItem.setSize(rs.getString("size").charAt(0));
				
				Item item = new Item();
				item.setId(rs.getInt("i_id"));
				item.setName(rs.getString("i_name"));
				item.setDescription(rs.getString("description"));
				item.setPriceM(rs.getInt("i_price_m"));
				item.setPriceL(rs.getInt("i_price_l"));
				item.setImagePath(rs.getString("image_path"));
				item.setDeleted(rs.getBoolean("deleted"));
				
				toppingList = new ArrayList<>();
				item.setToppingList(toppingList);
				
				
				orderItem.setItem(item);
				
				orderToppingList = new ArrayList<>();
				orderItem.setOrderToppingList(orderToppingList);
				
				orderItemList.add(orderItem);
			}
			
			if (rs.getInt("order_item_id") == nowOrderItemId) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setId(rs.getInt("ot_id"));
				orderTopping.setToppingId(rs.getInt("topping_id"));
				orderTopping.setOrderItemId(rs.getInt("order_item_id"));
				orderTopping.setTopping(new Topping());
				
				Topping topping = new Topping();
				topping.setId(rs.getInt("t_id"));
				topping.setName(rs.getString("t_name"));
				topping.setPriceM(rs.getInt("t_price_m"));
				topping.setPriceL(rs.getInt("t_price_l"));
				toppingList.add(topping);
				
				orderToppingList.add(orderTopping);
			}
			
			beforeOrderItemId = nowOrderItemId;
			beforeOrderId = nowOrderId;
			
		}
		
		return orderList;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public List<Order> findById(Integer orderId) {
		String sql = "select "
				+ "	o.id as o_id, user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode, destination_address, destination_tel, delivery_time, payment_method, "
				+ "	u.id as u_id, u.name as u_name, email, password, zipcode, address, telephone, "
				+ "	oi.id as oi_id, item_id, order_id, quantity, size, "
				+ "	i.id as i_id, i.name as i_name, description, i.price_m as i_price_m, i.price_l as i_price_l, image_path, deleted, "
				+ "	ot.id as ot_id, topping_id, order_item_id, "
				+ "	t.id as t_id, t.name as t_name, t.price_m as t_price_m, t.price_l as t_price_l "
				+ "from orders as o "
				+ "	left join order_items as oi on o.id=oi.order_id "
				+ "	left join users as u on u.id=o.user_id "
				+ "	left join items as i on i.id=item_id "
				+ "	left join order_toppings as ot on oi.id=ot.order_item_id "
				+ "	left join toppings as t on t.id= ot.topping_id "
				+ "where o_id=:orderId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId);
		List<Order> orderList = template.query(sql, param, ORDER_ITEM_TOPPING_EXTRACTER);
		return orderList;
	}
	
	
	public void save(Order order) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		System.out.println(param);
		
		
//		String updateSql = "UPDATE orders SET   WHERE id=:id";
//        template.update(updateSql, param);
	}
}
