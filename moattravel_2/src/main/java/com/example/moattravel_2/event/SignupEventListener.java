package com.example.moattravel_2.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.moattravel_2.entity.User;
import com.example.moattravel_2.service.VerificationTokenService;

@Component
public class SignupEventListener {
	private final VerificationTokenService verificationTokenService;	//ユーザーの確認トークンを作成・管理
	private final JavaMailSender javaMailSender;
	
	public SignupEventListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender) {
		this.verificationTokenService = verificationTokenService;
		this.javaMailSender = mailSender;
	}
	
	@EventListener
	private void onSignupEvent(SignupEvent signupEvent) {
		User user = signupEvent.getUser();//サインアップしたユーザーの情報を取得
		String token = UUID.randomUUID().toString();//市井の確認トークンを生成
		verificationTokenService.create(user,  token);
		String recipientAddress = user.getEmail();
		String subject = "メール認証";	//件名
		String confirmationUrl = signupEvent.getRequestUrl() + "/verify?token=" + token;
		String message = "以下のリンクをクリックして会員登録を完了してください。";
	
		SimpleMailMessage mailMessage = new SimpleMailMessage();//インスタンス生成（メール内容を設定するためのオブジェクト）
		mailMessage.setTo(recipientAddress);
		mailMessage.setSubject(subject);
		mailMessage.setText(message + "\n" + confirmationUrl);	//"\n"は開業して、本文とURLを見やすくしているだけ
		javaMailSender.send(mailMessage);//ここで実際にメールを送信する
	}
	
}
