package com.example.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.domain.LoginUser;
import com.example.form.InsertItemForm;
import com.example.service.AdminService;
import com.example.service.InsertItemService;
import com.example.service.ShowItemListService;

/**
 * 管理者ページのコントローラー
 * 
 * @author isodakeisuke
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private InsertItemService insertItemService;
	
	@Autowired
	private ShowItemListService showItemListService;
	
	@ModelAttribute
	private InsertItemForm setUpForm() {
		return new InsertItemForm();
	}

	/**
	 * 管理者画面を表示します.
	 * 
	 * @param loginuser ユーザー情報
	 * @return 管理者画面
	 */
	@RequestMapping("")
	public String index(@AuthenticationPrincipal LoginUser loginuser, Model model) {
		if (!isAdmin(loginuser)) {
			return "forward:/";
		}

		Map<String, Integer> purchasedMap = adminService.showPurchasedCount();
		model.addAttribute("purchasedMap", purchasedMap);
		return "admin";
	}
	
	/**
	 * 商品登録画面を表示します.
	 * 
	 * @param loginuser ユーザー情報（このページに入れるかの認証に使用）
	 * @param form 商品情報
	 * @return 管理者なら商品登録画面　その他なら商品一覧画面
	 */
	@RequestMapping("/toInsertItem")
	public String toInsertItem(@AuthenticationPrincipal LoginUser loginuser, Model model) {
		if (!isAdmin(loginuser)) {
			return "forward:/";
		}
		return "insert_item";
	}
	
	/**
	 * 商品を追加します.
	 * 
	 * @param loginuser ユーザー情報
	 * @param form 商品情報
	 * @param result 入力値チェック
	 * @return 登録できれば管理者画面　管理者でなければ商品一覧画面 入力に不具合があれば商品入力画面
	 */
	@RequestMapping("/insertItem")
	public String insertItem(@AuthenticationPrincipal LoginUser loginuser, @Validated InsertItemForm form, BindingResult result, Model model) {
		if (!isAdmin(loginuser)) {
			return "forward:/";
		}
		
		// 名前がかぶっている商品がもともと存在するとき
		List<Item> itemList = showItemListService.search(form.getname(), null);
		if(itemList.size() > 0) {
//			FieldError fieldError = new FieldError(result.getObjectName(), "name", "その名前の商品はすでに存在します");
//			result.addError(fieldError);
			result.rejectValue("name", null, "その名前の商品はすでに存在します");
		}
		
		// 入力された値段を文字列に変更
		Integer priceM = 0;
		Integer priceL = 0;
		try {
			priceM = Integer.parseInt(form.getpriceM());
		} catch (Exception e) {
			e.printStackTrace();
			result.rejectValue("priceM", null, "値段を入力してください");
		}
		try {
			priceL = Integer.parseInt(form.getpriceL());
		} catch (Exception e) {
			e.printStackTrace();
			result.rejectValue("priceL", null, "値段を入力してください");
		}
		
		// 画像が選択されたか判定
		if(form.getItemImage().getOriginalFilename().equals("")) {
			result.rejectValue("itemImage", null, "商品画像を選択してください");
		}
		
		if(result.hasErrors()) {
			return toInsertItem(loginuser, model);
		}
		
		Item item = new Item();
		BeanUtils.copyProperties(form, item);
		item.setPriceM(priceM);
		item.setPriceL(priceL);
		
		insertItemService.insert(item, form.getItemImage());
		
		return "redirect:/admin/toInsertItemFinished";
	}
	
	/**
	 * 商品追加の完了画面
	 * 
	 * @param loginuser ユーザー情報
	 * @param model モデル
	 * @return 商品追加完了画面
	 */
	@RequestMapping("/toInsertItemFinished")
	private String toInsertItemFinished(@AuthenticationPrincipal LoginUser loginuser) {
		if (!isAdmin(loginuser)) {
			return "forward:/";
		}
		
		return "insert_item_finished";
	}

	/**
	 * 管理者かチェックします.
	 * 
	 * @param loginuser ユーザー情報
	 * @return 管理者の場合true、そうでない場合false
	 */
	private boolean isAdmin(LoginUser loginuser) {

		Collection<GrantedAuthority> authorityList = loginuser.getAuthorities();
		for (GrantedAuthority authority : authorityList) {

			if (authority.getAuthority().equals("ROLE_ADMIN")) {
				return true;
			}
		}
		return false;
	}
}
