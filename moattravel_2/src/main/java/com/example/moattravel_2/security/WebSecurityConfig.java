package com.example.moattravel_2.security;

import org.springframework.context.annotation.Bean;		//Springが管理するオブジェクトを定義して、アプリケーション全体で使うことができる。
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;	//メソッド単位でのセキュリティ設定を有効にする
import org.springframework.security.config.annotation.web.builders.HttpSecurity;	//HTTPリクエストに対するセキュリティ設定を行なう
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;	//パスワードをハッシュ化
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((requests) -> requests
					.requestMatchers("/css/**", "/images/**", "/js/**", "/storage/**", "/", "/signup/**", "/houses", "/houses/{id}", "/stripe/webhook").permitAll()
					.requestMatchers("/admin/**").hasRole("ADMIN")	//ADMINを持つ＝管理者のみがアクセス可能なURL
					.anyRequest().authenticated()	//authenticated()は認証されていないユーザーはアクセス出来ないことを意味する
			)
			.formLogin((form) -> form
                .loginPage("/login")              // ログインページのURL
                .loginProcessingUrl("/login")     // ログインフォームの送信先URL
                .defaultSuccessUrl("/?loggedIn")  // ログイン成功時のリダイレクト先URL
                .failureUrl("/login?error")       // ログイン失敗時のリダイレクト先URL
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutSuccessUrl("/?loggedOut")  // ログアウト時のリダイレクト先URL
                .permitAll()
          
        	)
        	.csrf((csrf) -> csrf.ignoringRequestMatchers("/stripe/webhook"));
			
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {	//パスワードを暗号化したり、照合できる
		return new BCryptPasswordEncoder();
	}
	
}
