package com.chef.app.member;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.List;

import javax.crypto.Cipher;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chef.app.food.StoreMidOrderDTO;
import com.chef.app.food.StoreOrderDTO;
import com.chef.app.recipe.RecipeDTO;
import com.chef.app.recipe.RecipeReplyDTO;
import com.chef.app.recipe.RecipeReviewDTO;
import com.chef.app.util.Email;
import com.chef.app.util.Pager;

import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping("/member/*")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private Email email;
	
	
	@GetMapping("prfileSnsDelete")
	public String prfileSnsDelete(MemberDTO memberDTO, Model model) throws Exception {
		System.out.println("===== prfileSnsDelete =====");
		System.out.println(memberDTO.getMember_id());
		
		String url = "";
		int result = memberService.prfileSnsDelete(memberDTO);
		
		if(result > 0) {
			model.addAttribute("msg", result);
			url = "commons/result";
		}
		return url;
	}
	
	@PostMapping("prfileSnsAdd")
	public String prfileSnsAdd(MemberDTO memberDTO, Model model) throws Exception {
		System.out.println("prfileSnsAdd 확인");
		System.out.println(memberDTO.getMember_id());
		System.out.println(memberDTO.getProfile_sns_url());
		String url = "";
		int result = memberService.prfileSnsAdd(memberDTO);
		
		if(result > 0) {
			model.addAttribute("msg", result);
			url = "commons/result";
		}
		return url;
	}
	
	@GetMapping("profileDelete")
	public String profileDelete(MemberDTO memberDTO, Model model) throws Exception {
		int result = memberService.profileDelete(memberDTO);
		
		String url = "";
		if(result > 0) {
			model.addAttribute("msg", result);
			url = "commons/result";
		}
		return url;
	}
	
	@PostMapping("profileChange")
	public String profileChange(MemberDTO memberDTO, MultipartFile multipartFile, HttpSession session, Model model) throws Exception {
		MemberDTO memberdto = (MemberDTO)session.getAttribute("member");
		memberDTO.setMember_id(memberdto.getMember_id());
		
		int result = 0;
		String url = "";
		
		if(multipartFile.getSize() == 0) {
			System.out.println("============= 파일 null 값 =============");
			memberService.profileDelete(memberDTO);
			return url = "redirect:/member/mypage";
		}
		
		
		result = memberService.profileChange(memberDTO, multipartFile, session);			
		
		if(result > 0) {
			url = "redirect:/member/mypage";
			
		}
		return url;

	}								
	
	@GetMapping("duplication")
	public String duplication(MemberDTO memberDTO, Model model) throws Exception {
		System.out.println("== duplication ==");
		System.out.println(memberDTO.getMember_nickname());
		int check = memberService.duplication(memberDTO);
		
		model.addAttribute("msg", check);
		
		return "commons/result";
	}
	
	@GetMapping("introducesDelete")
	public String introducesDelete(MemberDTO memberDTO, HttpSession session) throws Exception {
		System.out.println("멤버 id 값:"+ memberDTO.getMember_id() );
		MemberDTO memberdto = (MemberDTO)session.getAttribute("member");
		memberdto.setMember_id(memberDTO.getMember_id());
		int result = memberService.introducesDelete(memberdto);
		System.out.println("결과 :" + result);
		
		String url = "";
		if(result > 0) {
			url = "member/profileAboutMe";
		}
		return url;
	}
	
//	@GetMapping("getList")
//	public String getList(Pager pager, Model model, HttpSession session) throws Exception {
//		System.out.println("== @@@@@@@@@@@@@@@@@@@@@@@@@@@@ ==");
//		System.out.println(pager.getOrder());
//		System.out.println(pager.getPage());
//		
//		MemberDTO memberdto = (MemberDTO)session.getAttribute("member");
//		memberdto = memberService.mypage(memberdto);
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("memberdto", memberdto);
//		map.put("pager", pager);
//
//		//레시피 작성
//		List<RecipeDTO> recipedto = memberService.recipeList(map);
//		model.addAttribute("recipeList", recipedto);
//		
//		// 상대방 레시피에 작성한 리뷰
//		List<RecipeReviewDTO> recipeReview = memberService.recipeReviewList(map);
//		model.addAttribute("reviewList", recipeReview);
//
//		return "member/recipeList";
//	}
	
	@GetMapping("mypage")
	public void mypage(HttpSession session, Model model, Pager pager,String tab) throws Exception {
		
		if(tab==null) {
			tab="1";
		}
		
		MemberDTO memberdto = (MemberDTO)session.getAttribute("member");
//		현재 로그인한 멤버의 모든 정보 가져오기
		memberdto = memberService.mypage(memberdto);
		model.addAttribute("member", memberdto);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberdto", memberdto);
		map.put("pager", pager);
				
		// 작성한 레시피 리스트
		
		if(tab.equals("1")) {
		Map<String, Object> recipeMap = memberService.recipeList(map);
		model.addAttribute("recipeMap", recipeMap);
		model.addAttribute("tab", tab);
		}
		
		if(tab.equals("2")) {
		// 상대방 레시피에 작성한 리뷰
		Map<String, Object> recipeReviewMap = memberService.recipeReviewList(map);
		model.addAttribute("recipeReviewMap", recipeReviewMap);
		model.addAttribute("tab", tab);
		
		List<RecipeReviewDTO> re = (List<RecipeReviewDTO>)recipeReviewMap.get("recipereViewAr");
		
		for(RecipeReviewDTO r : re ) {
			System.out.println("컨텐츠 반복");
			System.out.println(r.getBoard_content());
		}
		
		
		}
		
		if(tab.equals("3")) {
		// 상대방 레시피에 작성한 댓글
		Map<String, Object> recipeReplyMap = memberService.recipeReplyList(map);
		model.addAttribute("recipeReplyMap", recipeReplyMap);
		model.addAttribute("tab", tab);
		}
		
		// 최근 작성한 레시피 상위3개
		List<RecipeDTO> recentyList = memberService.recipeRecentList();
		model.addAttribute("recentyList", recentyList);
		
	}
	
	@PostMapping("mypage")
	public String mypageUpdate(MemberDTO memberDTO, HttpSession session, Model model) throws Exception {
		
//		MemberDTO memberdto = (MemberDTO)session.getAttribute("member");
//		memberdto.setProfile_about_me(profile_about_me);
//		memberdto.setMember_id(member_id);
		System.out.println("myapge 자기소개");
		MemberDTO memberdto = (MemberDTO)session.getAttribute("member");
		memberdto.setMember_id(memberDTO.getMember_id());
		memberdto.setProfile_about_me(memberDTO.getProfile_about_me());;
		
		int result = memberService.mypageUpdate(memberdto);
		
		String url = "";
		if(result > 0) {
			
			url = "member/profileAboutMe";
		}
		return url;
	}
	
	
	
	
	@GetMapping("sendEmail")
	public String email(MemberDTO memberDTO, Model model, String member_mail) throws Exception {
		System.out.println("== Email ==");
		System.out.println(member_mail);
		
		if(member_mail != null || member_mail != "") {
			String mailresult = email.mailTemplete(member_mail);
			System.out.println("반환값:" +mailresult);
			model.addAttribute("msg", mailresult);		
		}
		
		return "commons/result";
	}
	
	@GetMapping("email")
	public void email() throws Exception {
		
	}
	
	@GetMapping("getcode")
	public void getcode() throws Exception {
		System.out.println("== get code ==");
	}
	
	@PostMapping("kakaologin")
	public String kakaologin(String token, MemberDTO memberDTO, Model model, HttpSession session) throws Exception {
		System.out.println("== Kakao Controller ==");
		int num = 0;
		String result = "";
		
		if(token != null) {
			System.out.println("1번 값:" + token);
			System.out.println("2번 값:" + memberDTO.getMember_id());
			System.out.println("3번 값:" + memberDTO.getMember_nickname());
			System.out.println("4번 값:" + memberDTO.getKakao_profile_img());
			System.out.println("5번 값:" + memberDTO.getMember_type());
			num = memberService.kakaologin(memberDTO);
		}
		
		if(num > 0) {
			MemberDTO memberdto = memberService.kakaologin2(memberDTO);
			System.out.println(memberdto);
			session.setAttribute("member", memberdto);
			model.addAttribute("msg", num);
			result = "commons/result";
		}
		return result;
	}
	
	@GetMapping("kakaologin")
	public void kakao() throws Exception {
		System.out.println("== Kakao Controller ==");

//		memberService.kakao();
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) throws Exception {
		session.invalidate();
		
		return "redirect:/";
				
	}
	
	@GetMapping("login")
	public void login() throws Exception {

	}
	
	@PostMapping("login")
	public String login(MemberDTO memberDTO, Model model, HttpSession session) throws Exception {
		System.out.println("== login Controller ==");
		//session에 memberDTO 정보를 집어 넣어야함
		
		MemberDTO result = memberService.login(memberDTO);
		int num = 1;
		
		if(result != null ) {
			session.setAttribute("member", result);
			System.out.println(session);
			model.addAttribute("msg", num);
			return "commons/result";
		} else {
			session.invalidate();
			model.addAttribute("msg", 0);
			return "commons/result";
		}	
	}
	
	@GetMapping("join")
	public void join() throws Exception {
		System.out.println("== Get Join Controller ==");

	}
	
	@PostMapping("join")
	public String join(MemberDTO memberDTO, Model model) throws Exception {
		System.out.println("== Post Join Controller ==");

		int result = memberService.join(memberDTO);
		System.out.println("Join 반환값 :" + result);
		
		if(result > 0) {
			model.addAttribute("msg", result);
		}
		return "commons/result";
	}
	
	@GetMapping("buyList")
	public void buyList(HttpSession session,Model model,Pager pager,String startDate,String endDate) throws Exception {
		
		Calendar ca = Calendar.getInstance();
		Date today = ca.getTime();
		
        ca.add(Calendar.MONTH, -3);
        Date threeMonthsAgo = ca.getTime();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String nowStr = format.format(today);
		String threeAgo  = format.format(threeMonthsAgo);
		
		if(startDate==null||startDate.equals("")) {
			startDate=threeAgo;
		}
		if(endDate==null||endDate.equals("")) {
			endDate=nowStr;
		}
		
		Map<String, Object> goService = new HashMap<String, Object>();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		goService.put("memberDTO", memberDTO);
		goService.put("pager", pager);
		goService.put("startDate",startDate);
		goService.put("endDate", endDate);
		
		Map<String, Object> map = memberService.buyList(goService);
				
		model.addAttribute("list", map.get("list"));
		model.addAttribute("pager", map.get("pager"));
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
	}
	
	@GetMapping("cancleRequest")
	public String cancleRequest(StoreOrderDTO storeOrderDTO,Model model) throws Exception {
		
		int result = memberService.cancleRequest(storeOrderDTO);
		
		if(result>0) {
			model.addAttribute("msg", "결제취소 요청이 완료됐습니다");
			model.addAttribute("url", "/member/buyList");
		}else {
			model.addAttribute("msg", "결제취소 요청에 실패했습니다");
			model.addAttribute("url", "/member/buyList");	
		}
		
		return "commons/message";
	}
	
}
