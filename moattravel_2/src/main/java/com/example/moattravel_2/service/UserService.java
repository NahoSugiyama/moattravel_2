package com.example.moattravel_2.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.moattravel_2.entity.Role;
import com.example.moattravel_2.entity.User;
import com.example.moattravel_2.form.SignupForm;
import com.example.moattravel_2.form.UserEditForm;
import com.example.moattravel_2.repository.RoleRepository;
import com.example.moattravel_2.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional
	public User create(SignupForm signupForm) {
		User user = new User();
		Role role = roleRepository.findByName("ROLE_GENERAL");
		
		user.setName(signupForm.getName());
		user.setFurigana(signupForm.getFurigana());
		user.setPostalCode(signupForm.getPostalCode());
		user.setAddress(signupForm.getAddress());
		user.setPhoneNumber(signupForm.getPhoneNumber());
		user.setEmail(signupForm.getEmail());
		user.setPassword(passwordEncoder.encode(signupForm.getPassword()));//フォームで受けとったパスを、ハッシュ化してuserにセット
		user.setRole(role);	//会員登録だから問答無用でROLE_GENERALをあてはめちゃう
		user.setEnabled(false);	
		
		return userRepository.save(user);
	}
	
	@Transactional
	public void update(UserEditForm userEditForm) {
		User user = userRepository.getReferenceById(userEditForm.getId());
		
		user.setName(userEditForm.getName());
		user.setFurigana(userEditForm.getFurigana());
		user.setPostalCode(userEditForm.getPostalCode());
		user.setAddress(userEditForm.getAddress());
		user.setPhoneNumber(userEditForm.getPhoneNumber());
		user.setEmail(userEditForm.getEmail());

		userRepository.save(user);
	}
	
	//メールアドレスが登録済みか否かチェック
	public boolean isEmailRegistered(String email) {
		User user = userRepository.findByEmail(email);//メールアドレスを持つユーザーを探す、無かったらnullが入る
		return user != null;	//見つかったらtrue（登録済み）で返す、見つからなかったらfalse（未登録）で返す
	}
	
	//パスワードが確認用と一致しているかチェック
	public boolean isSamePassword(String password, String passwordConfirmation) {
		return password.equals(passwordConfirmation);
	}
	
	//ユーザーを有効にする
	@Transactional
	public void enableUser(User user) {
		user.setEnabled(true);
		userRepository.save(user);
	}
	
	//メールアドレスが変更されたかどうかチェックする
	public boolean isEmailCahanged(UserEditForm userEditForm) {
		User currentUser = userRepository.getReferenceById(userEditForm.getId());
		return !userEditForm.getEmail().equals(currentUser.getEmail());
				
	}
}
