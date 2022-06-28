package com.example.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * 商品追加の業務処理を行います.
 * 
 * @author isodakeisuke
 *
 */
@Service
public class InsertItemService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品の追加を行います.
	 * 
	 * @param item 商品情報
	 * @throws IOException
	 */
	public void insert(Item item, MultipartFile itemImage) {
		// 画像の保存先のパスを作成
		// ファイルの名前：LocalDateTime＋選択されたファイルの名前（ファイルの名前がかぶらないようLocalDateTimeをつけています）
		File imageDir = new File("src\\main\\resources\\static\\img_aloha_resize");
		imageDir = imageDir.getAbsoluteFile();
		String imageName = LocalDateTime.now().getNano() + itemImage.getOriginalFilename();
		Path imagePath = Paths.get(imageDir.toString() + "\\" + imageName);

		// アップロードファイルをバイト値に変換
		byte[] bytes;
		try {
			bytes = itemImage.getBytes();
			// バイト値を書き込む為のファイルを作成して指定したパスに格納
			OutputStream stream = Files.newOutputStream(imagePath);
			// ファイルに書き込み
			stream.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		item.setImagePath(imageName);
		itemRepository.insert(item);
	}
}
