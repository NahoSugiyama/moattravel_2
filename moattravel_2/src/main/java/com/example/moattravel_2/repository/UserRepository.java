package com.example.moattravel_2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel_2.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByEmail(String email);
	//メールアドレスでユーザーを探すメソッド
	public Page<User> findByNameLikeOrFuriganaLike(String namekeyword, String furiganaKeyword, Pageable pageble);
	//名前またはフリガナが指定したキーワードを含んだものを検索し、ページリングしょりを適用する
}
