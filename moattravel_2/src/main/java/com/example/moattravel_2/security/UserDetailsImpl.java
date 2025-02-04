package com.example.moattravel_2.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.moattravel_2.entity.User;

public class UserDetailsImpl implements UserDetails {
	private final User user;
	private final Collection<GrantedAuthority> authorities;
	
	public UserDetailsImpl(User user, Collection<GrantedAuthority> authorities) {
		this.user = user;
		this.authorities = authorities;
	}
	
	public User getUser() {	//ログイン中のユーザーの情報を使用するときようのメソッド
		return user;
	}
	//ハッシュ化済みのパスワードを返す
	@Override
	public String getPassword() {
		return user.getPassword();
	}
		//ログイン時に利用するメールアドレスを返す
	@Override
	public String getUsername() {
		return user.getEmail();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public boolean isAccountNonExpired() {	//アカウントが期限切れか否か
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {	//ユーザーがロック中か否か
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {	//パスワードが期限切れか否か
		return true;
	}
	
	@Override
	public boolean isEnabled() {	//ユーザーが有効か否か
		return user.getEnabled();
	}
}
