package com.example.moattravel_2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel_2.entity.House;

public interface HouseRepository extends JpaRepository<House, Integer> {
	public Page<House> findByNameLike(String keyword, Pageable pageable);
/* findAll():テーブル内の全てのエンティティを取得
 * getReferenceById(id):指定したidのエンティティ取得
 * save():指定したエンティティの保持または更新する
 * delete:指定したエンティティを削除
 * delateById(id):指定したidのエンティティを削除
 * 継承する時はJpaRepository<エンティティのクラス型, 主キーのデータ型>
 */
}
