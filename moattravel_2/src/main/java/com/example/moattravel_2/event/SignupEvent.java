package com.example.moattravel_2.event;

import org.springframework.context.ApplicationEvent;

import com.example.moattravel_2.entity.User;

import lombok.Getter;

@Getter
public class SignupEvent extends ApplicationEvent {
	private User user;//ユーザー情報をURLを保持
	private String requestUrl;
	
	public SignupEvent(Object source, User user, String requestUrl) {
		super(source);//イベントを発行したオブジェクトを親クラスに伝える
		this.user = user;
		this.requestUrl = requestUrl;
	}
	
}
