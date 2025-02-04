package com.example.moattravel_2.contrller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;	//フォームの入力データにエラーがないかチェック
import org.springframework.validation.annotation.Validated;	//
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;	//フォームのデータをオブジェクトに格納して受け渡す
import org.springframework.web.bind.annotation.PathVariable;	//URLの｛｝部分を変数として取得する
import org.springframework.web.bind.annotation.PostMapping;	//データの送信・登録
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;	//URLの最後のパラメータを取得できる
import org.springframework.web.servlet.mvc.support.RedirectAttributes;	//リダイレクト時のデータ保持

import com.example.moattravel_2.entity.House;
import com.example.moattravel_2.form.HouseEditForm;
import com.example.moattravel_2.form.HouseRegisterForm;
import com.example.moattravel_2.repository.HouseRepository;
import com.example.moattravel_2.service.HouseService;

@Controller
@RequestMapping("/admin/houses")	//クラス全体で/admin/housesを共通のURLとしている
public class AdminHouseController {
	private final HouseRepository houseRepository;
	private final HouseService houseService;
	
	public AdminHouseController(HouseRepository houseRepository, HouseService houseService) {
		this.houseRepository = houseRepository;
		this.houseService = houseService;
	}
	
	@GetMapping
	public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC)Pageable pageable, @RequestParam(name = "keyword", required = false) String keyword) {
		/*page:ページ番号（デフォルトは0）
		 * size:サイズ（1ページあたりの表示数、デフォルトは10
		 * sort:並び替え対象
		 * direction:並び替える順番
		 */
		Page<House> housePage;
		if(keyword != null && !keyword.isEmpty()) {
			housePage = houseRepository.findByNameLike("%" + keyword + "%", pageable);
		} else {
			housePage = houseRepository.findAll(pageable);
					
		}
		
		model.addAttribute("housePage", housePage);
		model.addAttribute("keyword", keyword);
		
		return "admin/houses/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model) {
		House house = houseRepository.getReferenceById(id);
		
		model.addAttribute("house", house);
		
		return "admin/houses/show";
	}
	
	@GetMapping("/register") ///registerにアクセスするとこのメソッドが実行される
	public String register(Model model) {
		model.addAttribute("houseRegisterForm", new HouseRegisterForm()); //houseRegisterFormという名前でHTMLに渡している
		return "admin/houses/register";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute @Validated HouseRegisterForm houseRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "admin/houses/register";	//もしエラーがあったらregister画面に戻す
		}
		houseService.create(houseRegisterForm);		//エラーがなかったらhouseServiceに値を渡して登録をする
		redirectAttributes.addFlashAttribute("successMessage", "民宿を登録しました");	//登録処理が成功した場合、リダイレクト先にメッセージを渡す
		
		return "redirect:/admin/houses";	//成功したらこのページに飛ぶ
	}
	
	@GetMapping("/{id}/edit")	//編集画面に更新前の情報を載せるメソッド
	public String edit(@PathVariable(name = "id") Integer id, Model model) {
		House house = houseRepository.getReferenceById(id);
		String imageName = house.getImageName();
		HouseEditForm houseEditForm = new HouseEditForm(house.getId(), house.getName(), null , house.getDescription(), house.getPrice(), house.getCapacity(), house.getPostalCode(), house.getAddress(), house.getPhoneNumber());
			//更新前の民宿画像はファイル名で渡して表示するため、コンストラクタの引数はnullでOK
		model.addAttribute("imageName", imageName);
		model.addAttribute("houseEditForm", houseEditForm);
		
		return "admin/houses/edit";
		
	}
	
	@PostMapping("/{id}/update")
	public String update(@ModelAttribute @Validated HouseEditForm houseEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "admin/houses/edit";
		}
		
		houseService.update(houseEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "民宿情報を編集しました。");
		
		return "redirect:/admin/houses";
	}
	
	@PostMapping("/{id}/delete")
	public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {	//redirectAttributesで成功・失敗のメッセージを渡す
		houseRepository.deleteById(id);
		
		redirectAttributes.addFlashAttribute("successMessage", "民宿を削除しました。");
		
		return "redirect:/admin/houses";
	}
}
