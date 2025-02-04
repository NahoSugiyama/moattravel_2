package com.example.moattravel.entity;



import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity	  //entity(テーブル）として機能する
@Table (name = "houses")   //houseテーブルに対応付け
@Data   //ゲッター、セッターの自動生成
public class House {
	@Id  //以下のフィールドを主キーに指定する
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")  //カラムとは表でいう列のこと。名前をここで付ける。
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "image_name")
	private String imageName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private Integer price;
	
	@Column(name = "capacity")
	private Integer capacity;
	
	@Column(name = "postal_code")
	private String postalCode;
	
	@Column (name = "address")
	private String address;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "created_at", insertable = false, updatable = false)
	private Timestamp createdAt;
	//insettable属性には「そのカラムにあたいを挿入できるかどうか」
	//updatable属性には「そのカラムの値を更新できるかどうか」
	
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;
	
	
}



