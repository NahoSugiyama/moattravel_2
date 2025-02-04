package com.example.moattravel_2.contrller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String index() {
		return "index";  //メソッドの最後で呼び出すビューの名前をreturnで返す
	} //.htmlは省略可
}