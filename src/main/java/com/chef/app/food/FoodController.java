package com.chef.app.food;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chef.app.member.MemberDTO;
import com.chef.app.util.Pager;

import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping("/food/*")
public class FoodController {
	
	@Autowired
	private FoodService foodService;
	
	@GetMapping("add")
	public void add() throws Exception {
		
	}
	
	@PostMapping("add")
	public String add(FoodDTO foodDTO,MultipartFile attach,HttpSession session,Model model) throws Exception {
		
		int result = foodService.add(foodDTO, attach, session);
		
		if(result>0) {
			model.addAttribute("result", "상품 등록이 완료됐습니다");
			model.addAttribute("url", "/food/list");
			return "food/message";
		}
		
		model.addAttribute("result", "상품 등록에 실패했습니다");
		model.addAttribute("url", "/food/list");
		return "food/message";
		
	}
	
	@GetMapping("searchFood")
	public String searchFood(FoodDTO foodDTO, Model model) throws Exception {	
		
		List<FoodDTO> ar = foodService.searchFood(foodDTO);
		model.addAttribute("list", ar);
		return "food/foodSearchList";
		
	}
	

	@PostMapping("uploadContentImage")
	@ResponseBody
	public Map<String, String> uploadContentImage(MultipartFile upload,Model model,HttpSession session) throws Exception{

		String imgName = foodService.uploadContentImage(upload, session);
		String path ="/resources/upload/foodcontents/"+imgName;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("url", path);

		return map;

	}
	
	@GetMapping("list")
	public void getList(Pager pager,Model model,HttpSession session) throws Exception{
		
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
				
		List<FoodDTO> ar =foodService.getList(pager);
		List<Map<String, Object>> categoryCount =foodService.categoryCount();
		model.addAttribute("pager", pager);
		model.addAttribute("list", ar);
		model.addAttribute("count", categoryCount);
		model.addAttribute("admin", memberDTO);
		
	}
	
	@PostMapping("list")
	public String getListSearch(Pager pager,Model model) throws Exception{
		
		List<FoodDTO> ar =foodService.getList(pager);
		List<Map<String, Object>> categoryCount =foodService.categoryCount();
		model.addAttribute("pager", pager);
		model.addAttribute("list", ar);
		model.addAttribute("count", categoryCount);

		return "food/nameSearchList";
		
	}
	
	@GetMapping("detail")
	public void getDetail(FoodDTO foodDTO,Model model,HttpSession session,Pager pager) throws Exception {
		
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		Double avg = foodService.startAvg(foodDTO);
		
		Map<String, Object> map = foodService.getDetail(foodDTO,pager);

		map.put("admin", memberDTO);
		map.put("avg", avg);
		
		model.addAttribute("map", map);

	}
	
	@GetMapping("update")
	public void updateDetail(FoodDTO foodDTO,Model model,Pager pager) throws Exception{
		
		Map<String, Object> map = foodService.getDetail(foodDTO,pager);
		
		model.addAttribute("map", map);
		
	}
	
	@PostMapping("update")
	public String updateDetail(MultipartFile attach,HttpSession session,FoodDTO foodDTO,Model model) throws Exception {
		
		int result = foodService.updateDetail(attach, session, foodDTO);
		
		if (result>0) {
			model.addAttribute("result", "게시글 수정이 완료 됐습니다");
			model.addAttribute("url", "/food/list");
			return "food/message";
			
		}
			model.addAttribute("result", "게시글 수정에 실패 했습니다");
			model.addAttribute("url", "/food/list");
			return "food/message";
		
	}
	
	@GetMapping("delete")
	public String detailDelete(FoodDTO foodDTO,Model model) throws Exception {
		
		int result = foodService.detailDelete(foodDTO);
		
		if (result>0) {
			model.addAttribute("result", "게시글 삭제가 완료 됐습니다");
			model.addAttribute("url", "/food/list");
			return "food/message";
			
		}
			model.addAttribute("result", "게시글 삭제에 실패 했습니다");
			model.addAttribute("url", "/food/list");
			return "food/message";
		
	}
	
	@GetMapping("cartAdd")
	public String cartAdd (Long food_num,Long cart_count,Model model,HttpSession session) throws Exception {
		
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		String memberId = memberDTO.getMember_id();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_id", memberId);
		map.put("food_num", food_num);
		map.put("cart_count", cart_count);
		
		int result = foodService.cartAdd(map);
		
		if (result>0) {
			model.addAttribute("result", "장바구니 추가 완료, 장바구니로 이동하시겠습니까?");
			model.addAttribute("urlTrue", "/food/cart");
			model.addAttribute("urlFalse", "/food/detail?food_num="+food_num);
			return "food/confirm";
			
		}
			model.addAttribute("result", "장바구니 추가에 실패했습니다");
			model.addAttribute("url", "/food/list");
			return "food/message";
		
	}
	
	@GetMapping("cart")
	public void cartList(Model model,HttpSession session) throws Exception{
		
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		String member_id = memberDTO.getMember_id();
		
		StoreCartDTO storeCartDTO = new StoreCartDTO();
		storeCartDTO.setMember_id(member_id);
		List<StoreCartDTO> ar = foodService.cartList(storeCartDTO);
		
		model.addAttribute("list", ar);
		
	}
	
	@PostMapping("cartDelete")
	public String deleteCart(StoreCartDTO storeCartDTO,Model model,HttpSession session) throws Exception {
		
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		String member_id = memberDTO.getMember_id();
		
		storeCartDTO.setMember_id(member_id);
		
		int result = foodService.deleteCart(storeCartDTO);
		
		return "redirect:/food/cart";

	}
	
	
	@PostMapping("pay")
	public String payMain(@RequestBody List<StoreCartDTO> ar,Model model,HttpSession session) throws Exception{
		
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		String member_id = memberDTO.getMember_id();
		
		for(StoreCartDTO a:ar) {
			a.setMember_id(member_id);
		}
		
		List<StoreCartDTO> cartAr = foodService.payCartList(ar);
		
	    session.setAttribute("cartList", cartAr);
	    session.setAttribute("orderMember", memberDTO);
		
	    model.addAttribute("msg", "/food/pay");
		
		return "commons/result";
		
	}
	
	@GetMapping("pay")
	public String payMain(HttpSession session,Model model) throws Exception{
		
		List<StoreCartDTO> cartAr =(List<StoreCartDTO>)session.getAttribute("cartList");
		
		if(cartAr == null) {
			model.addAttribute("msg", "잘못된 접근입니다.");
			model.addAttribute("url", "/");
			
			return "commons/message";
		}
		
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		model.addAttribute("orderMember", memberDTO);
		model.addAttribute("list", cartAr);
		
		return "food/pay";
		
	}
	
	@PostMapping("payment/complete")
	public String payComplete(@RequestBody StoreOrderDTO storeOrderDTO,Model model,HttpSession session) throws Exception {
		
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		String member_id = memberDTO.getMember_id();

		storeOrderDTO.setMember_id(member_id);
			
		foodService.orderInsert(storeOrderDTO);
		model.addAttribute("msg", "/food/payComplete?order_num="+storeOrderDTO.getOrder_num());
		return "commons/result";
		
	}
	
	@GetMapping("payComplete")
	public String payComplete2(StoreOrderDTO storeOrderDTO,Model model) throws Exception{
		
		if(storeOrderDTO.getOrder_num()==null) {
			
			model.addAttribute("msg", "잘못된 접근입니다.");
			model.addAttribute("url", "/");
			
			return "commons/message";
			
		}
		
		model.addAttribute("num", storeOrderDTO);
		
		return "food/payComplete";
		
	}
	
	@GetMapping("cartCountChange")
	public String cartCountChange(StoreCartDTO storeCartDTO,HttpSession session,Model model) throws Exception {
		
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		String member_id = memberDTO.getMember_id();
		
		storeCartDTO.setMember_id(member_id);
		
		int result = foodService.cartCountChange(storeCartDTO);
		model.addAttribute("msg", result);
		
		return "commons/result";	
	}
	
	@PostMapping("replyAdd")
	public String replyAdd (StoreReplyDTO storeReplyDTO,Model model,HttpSession session) throws Exception {
		
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		if(memberDTO==null) {
			model.addAttribute("msg","로그인을 해주세요");
			model.addAttribute("url", "/member/login");
			
			return "commons/message";
		}
		
		int result  = foodService.replyAdd(storeReplyDTO);
		
		model.addAttribute("msg","소중한 후기 감사합니다!");
		model.addAttribute("url", "/food/detail?food_num="+storeReplyDTO.getFood_num());
		
		return "commons/message";
		
	}
	
	@PostMapping("replyUpdate")
	public String replyUpdate (StoreReplyDTO storeReplyDTO,Model model) throws Exception {
		
		model.addAttribute("dto", storeReplyDTO);
		
		return "food/replyUpdate";
		
	}
	
	@PostMapping("replyUpdateInsert")
	public String replyUpdateInsert (StoreReplyDTO storeReplyDTO,Model model) throws Exception {
		
		int result = foodService.replyUpdateInsert(storeReplyDTO);
		
			model.addAttribute("msg", result);
			return "commons/result";
			
	}
	
	@PostMapping("adminReplyAdd")
	public String adminReplyAdd (StoreReplyDTO storeReplyDTO, Model model) throws Exception{
		
		model.addAttribute("dto", storeReplyDTO);
				
		return "food/adminReply";
		
	}
	
	@PostMapping("adminReplySubmit")
	public String adminReplySubmit(StoreReplyDTO storeReplyDTO,HttpSession session,Model model) throws Exception{
		
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		String member_id = memberDTO.getMember_id();
		
		storeReplyDTO.setMember_id(member_id);
		
		int result = foodService.adminReplySubmit(storeReplyDTO);
		
		model.addAttribute("msg", result);
		return "commons/result";
		
	}
	
	@PostMapping("replyDelete")
	public String replyDelete(StoreReplyDTO storeReplyDTO,HttpSession session,Model model) throws Exception {
		
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		String member_id = memberDTO.getMember_id();
		
		storeReplyDTO.setMember_id(member_id);
		
		int result = foodService.replyDelete(storeReplyDTO);
		
		model.addAttribute("msg", result);
		return "commons/result";
		
	}
	
}
