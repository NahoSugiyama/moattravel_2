package com.example.moattravel_2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel_2.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByEmail(String email);
	//メールアドレスでユーザーを探すメソッド
}
