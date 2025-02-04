package com.example.moattravel_2.service;  //アプリ固有の処理やルールを実装する部分

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.moattravel_2.entity.House;
import com.example.moattravel_2.form.HouseEditForm;
import com.example.moattravel_2.form.HouseRegisterForm;
import com.example.moattravel_2.repository.HouseRepository;

@Service
public class HouseService {
	private final HouseRepository houseRepository;
	
	public HouseService (HouseRepository houseRepository) {
		this.houseRepository = houseRepository;
	}
	
	@Transactional	//一連の処理を一括で実行する・処理中に例外がおこったら児童でロールバックする
	public void create(HouseRegisterForm houseRegisterForm) {
		House house = new House();
		MultipartFile imageFile = houseRegisterForm.getImageFile();
		
		if(!imageFile.isEmpty()) {
			String imageName = imageFile.getOriginalFilename(); //基のファイル名を取得
			String hashedImageName = generateNewFileName(imageName);	//新たにハッシュ化（元のデータを固定長の値に変換）されたファイル名を作成
			Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);	//画像を保存するディレクトリのパスを作成
			copyImageFile(imageFile, filePath);	//imageFileをfilePathにコピーし、画像を保存
			house.setImageName(hashedImageName);	//houseオブジェクトに新しい画像のファイル名を設定
		}
		
		house.setName(houseRegisterForm.getName());	//	フォームで入力したデータを取得し、houseのnameに代入
		house.setDescription(houseRegisterForm.getDescription());
		house.setPrice(houseRegisterForm.getPrice());
		house.setCapacity(houseRegisterForm.getCapacity());
		house.setPostalCode(houseRegisterForm.getPostalCode());
		house.setAddress(houseRegisterForm.getAddress());
		house.setPhoneNumber(houseRegisterForm.getPhoneNumber());
		
		houseRepository.save(house);	//Spring Data JPAで定義されているメソッド
	}

	@Transactional
	public void update(HouseEditForm houseEditForm) {
		House house = houseRepository.getReferenceById(houseEditForm.getId());
		MultipartFile imageFile = houseEditForm.getImageFile();
		
		if(!imageFile.isEmpty()) {
			String imageName = imageFile.getOriginalFilename();
			String hashedImageName = generateNewFileName(imageName);
			Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
			copyImageFile(imageFile, filePath);
			house.setImageName(hashedImageName);
		}
		
		house.setName(houseEditForm.getName());	//	フォームで入力したデータを取得し、houseのnameに代入
		house.setDescription(houseEditForm.getDescription());
		house.setPrice(houseEditForm.getPrice());
		house.setCapacity(houseEditForm.getCapacity());
		house.setPostalCode(houseEditForm.getPostalCode());
		house.setAddress(houseEditForm.getAddress());
		house.setPhoneNumber(houseEditForm.getPhoneNumber());
		
		houseRepository.save(house);
	}
	
	public String generateNewFileName(String fileName) {  //imageFileNameが渡されている
		String[] fileNames = fileName.split("\\.");	//.でファイル名を分割（例example.text→"example"と"text"の二つの要素に分けて配列に収納
		for (int i = 0; i < fileNames.length - 1; i++) {	//例でいくとtext以外の部分数だけ繰り返す
			//↓UUID.randomUUID().で一意な識別しを生成し、文字列に変換し、fileNames配列の各部分に置き換え
			fileNames[i] = UUID.randomUUID().toString();  	
			
		}
		String hashedFileName = String.join(".", fileNames);	//再び配列の中身を"."で結合
		return hashedFileName;
	}
	
	//画像ファイルを指定したファイルにコピーするメソッド
	public void copyImageFile(MultipartFile imageFile, Path filePath) {	//引数をcreatメソッドから受け取る
		try {
			Files.copy(imageFile.getInputStream(), filePath);
		} catch (IOException e) {	//エラーが発生したときに行なうもの
			e.printStackTrace();	//エラー詳細をコンソールに表示する
		}
	}
	
}
