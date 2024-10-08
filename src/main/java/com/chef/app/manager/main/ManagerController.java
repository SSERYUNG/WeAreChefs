package com.chef.app.manager.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chef.app.food.FoodDTO;
import com.chef.app.food.StoreOrderDTO;
import com.chef.app.manager.OriMemberDTO;
import com.chef.app.manager.StockBuyingDTO;
import com.chef.app.manager.StockMidBuyingDTO;
import com.chef.app.manager.TotalPurchaseDTO;
import com.chef.app.member.MemberDTO;

import oracle.jdbc.driver.parser.util.Array;
import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping("/manager/*")
public class ManagerController {
	@Autowired
	private ManagerService managerService;
	
	// index 첫줄 정보 4가지 --------------------------------------------------------------------------
	@GetMapping("index")
	public String managerIndex(Model model) throws Exception{
		// 첫줄 정보
		List<Long> indexFirstRowInfo = managerService.getIndexFirstRowInfo();
		model.addAttribute("indexFirstRowInfo", indexFirstRowInfo);
		// 매출
		List<TotalPurchaseDTO> monthSales = managerService.getMonthSales();
		model.addAttribute("monthSales", monthSales);
		// 지출
		List<TotalPurchaseDTO> monthExpends = managerService.getMonthExpend();
		model.addAttribute("monthExpends", monthExpends);
		// 차익
		List<TotalPurchaseDTO> monthEarns = new ArrayList<TotalPurchaseDTO>();
		for(int i =0; i < monthExpends.size(); i++) {
			TotalPurchaseDTO result = new TotalPurchaseDTO();
			// 월 추가
			result.setPur_period(monthExpends.get(i).getPur_period());
			// 금액 추가
			Long earn = monthSales.get(i).getPur_price() - monthExpends.get(i).getPur_price();
			result.setPur_price(earn);
			
			monthEarns.add(result);
		}
		model.addAttribute("monthEarns", monthEarns);
		
		// 일별 회원가입
		TotalPurchaseDTO dailyMember = managerService.getDailyMember();
		model.addAttribute("dailyMember", dailyMember);
		
		// 일별 매출
		TotalPurchaseDTO dailySales = managerService.getDailySales();
		model.addAttribute("dailySales", dailySales);
		
		// 회원 정보 리스트
		List<MemberDTO> memberListDesc = managerService.getMemberListDesc();
		model.addAttribute("memberListDesc", memberListDesc);
		
		// 결제 완료 리스트
		List<StoreOrderDTO> orderListDesc = managerService.getOrderListDesc();
		model.addAttribute("orderListDesc", orderListDesc);
		
		return "manager/index";
	}

	// memberInfo 회원 정보 List --------------------------------------------------------------------------
	@GetMapping("memberInfo")
	public String managerInfo(Model model) throws Exception{
		List<MemberDTO> MemberListDesc = managerService.getMemberListDesc();
		model.addAttribute("memberListDesc", MemberListDesc);
		
		return "manager/info";
	}
	
	// memberDetail 회원 정보 --------------------------------------------------------------------------
	@GetMapping("memberDetail")
	public String memberDetail(MemberDTO memberDTO, Model model) throws Exception{
		model.addAttribute("memberDetail", managerService.getMemberDetail(memberDTO));
		
		return "manager/memberDetail";
	}
	
	@GetMapping("memberDetailSample")
	public String memberDetailSample(MemberDTO memberDTO, Model model) throws Exception{
		model.addAttribute("memberDetail", managerService.getMemberDetail(memberDTO));
		
		return "manager/memberDetailSample";
	}
	
	// memberDetail 회원 정보 수정 --------------------------------------------------------------------------
	@GetMapping("memberDetailUpdate")
	public String memberDetailUpdate(MemberDTO memberDTO, Model model) throws Exception{
		model.addAttribute("memberDetail", managerService.getMemberDetail(memberDTO));
		return "manager/memberDetailUpdate";
	}
	
	@PostMapping("memberDetailUpdate")
	public String postMemberDetailUpdate(OriMemberDTO memberDTO, Model model) throws Exception{
		int result = managerService.postMemberDetailUpdate(memberDTO);
		System.out.println("memberDetail Update Pw : " + memberDTO.getMember_pwd());
		System.out.println("");
		if(result > 0) {
			model.addAttribute("memberDetail", managerService.getMemberDetail(memberDTO));
			return "manager/memberDetailSample";
		}else {
			model.addAttribute("msg", "오류 발생");
			model.addAttribute("url", "manager/memberDetail");
			return "commons/message";
		}
	}
	
	// 회원정보 수정 제약조건 --------------------------------------------------------------------------
	// 아이디 검사
	@PostMapping("memberIdConfirm")
	public String memberUpdateConfirm(MemberDTO memberDTO, OriMemberDTO oriMemberDTO, Model model) throws Exception{
		if(memberDTO.getMember_id().equals(oriMemberDTO.getOriMember_id())) {
			model.addAttribute("msg", 0);
			return "commons/result";
		}else {
			Long memberId = managerService.confirmMemberId(memberDTO);
			model.addAttribute("msg", memberId);	
			return "commons/result";
		}
	}
	//닉네임 검사
	@PostMapping("memberNicknameConfirm")
	public String memberNicknameConfirm(MemberDTO memberDTO, OriMemberDTO oriMemberDTO, Model model) throws Exception{
		if(memberDTO.getMember_nickname().equals(oriMemberDTO.getOriMember_nickname())) {
			model.addAttribute("msg", 0);
			return "commons/result";
		}else {
			Long memberNickname = managerService.confirmMemberNickname(memberDTO);
			model.addAttribute("msg", memberNickname);	
			return "commons/result";
		}
	}
	// 메일
	@PostMapping("memberMailConfirm")
	public String memberMailConfirm(MemberDTO memberDTO, OriMemberDTO oriMemberDTO, Model model) throws Exception{
		if(memberDTO.getMember_mail().equals(oriMemberDTO.getOriMember_mail())) {
			model.addAttribute("msg", 0);
			return "commons/result";
		}else {
			Long memberMail = managerService.confirmMemberMail(memberDTO);
			model.addAttribute("msg", memberMail);	
			return "commons/result";
		}
	}
	// 연락처
	@PostMapping("memberPhoneConfirm")
	public String memberPhoneConfirm(MemberDTO memberDTO, OriMemberDTO oriMemberDTO, Model model) throws Exception{
		if(memberDTO.getMember_phone().equals(oriMemberDTO.getOriMember_phone())) {
			model.addAttribute("msg", 0);
			return "commons/result";
		}else {
			Long memberPhone = managerService.confirmMemberPhone(memberDTO);
			model.addAttribute("msg", memberPhone);	
			return "commons/result";
		}
	}
	
	// 주문 & 배송
	// 주문
	// orderList
	@GetMapping("orderList")
	public String orderList(Model model) throws Exception{		
		model.addAttribute("orderList", managerService.getOrderList());
		model.addAttribute("cancelOrderList", managerService.getCancelOrderList());
		return "manager/orderList";
	}
	// orderList cancelOk
	@PostMapping("cancelOk")
	public String cancelOk(StoreOrderDTO storeOrderDTO, Model model) throws Exception{
		int result = managerService.cancelOk(storeOrderDTO);
		if(result>0){
			model.addAttribute("cancelOrderList", managerService.getCancelOrderList());
			return "manager/cancelOrderListSample";
		}else {
			model.addAttribute("result", 1);
			return "commons/result";
		}
	}
	// orderList cancelNo
	@PostMapping("cancelNo")
	public String cancelNo(StoreOrderDTO storeOrderDTO, Model model) throws Exception{
		int result = managerService.cancelNo(storeOrderDTO);
		if(result>0){
			model.addAttribute("cancelOrderList", managerService.getCancelOrderList());
			return "manager/cancelOrderListSample";
		}else {
			model.addAttribute("result", 1);
			return "commons/result";
		}
	}
	// orderDetail
	@GetMapping("orderDetail")
	public String orderDetail(StoreOrderDTO storeOrderDTO, Model model) throws Exception{
		model.addAttribute("orderDetail", managerService.orderDetail(storeOrderDTO));
		
		if(managerService.orderFoodDetail(storeOrderDTO).size() == 0){
			model.addAttribute("foodSize", 0);
		}else {
			List<FoodDTO> result = managerService.orderFoodDetail(storeOrderDTO);
			Long totalPrice = 0L;
			for(FoodDTO a : result) {
				totalPrice = totalPrice + a.getTotal_price();
			}
			System.out.println("토탈 프라이스 : " + totalPrice);
			model.addAttribute("totalPrice", totalPrice);
			model.addAttribute("orderFoodDetail", managerService.orderFoodDetail(storeOrderDTO));			
		}
		return "manager/orderDetail";
	}
	// orderDetail Update Page loading
	@PostMapping("orderDetailUpdate")
	public String orderDetailUpdate(StoreOrderDTO storeOrderDTO, Model model) throws Exception{
		model.addAttribute("orderDetail", managerService.orderDetail(storeOrderDTO));
		if(managerService.orderFoodDetail(storeOrderDTO).size() == 0){
			model.addAttribute("foodSize", 0);
		}else {
			model.addAttribute("orderFoodDetail", managerService.orderFoodDetail(storeOrderDTO));			
		}
		return "manager/orderDetailUpdate";
	}
	// 수정 완료 버튼 클릭 시
	@PostMapping("completeOrderDetailUpdate")
	public String completeOrderDetailUpdate(StoreOrderDTO storeOrderDTO, Model model) throws Exception{
		int result = managerService.completeOrderDetailUpdate(storeOrderDTO);
		if(result > 0) {
			model.addAttribute("orderDetail", managerService.orderDetail(storeOrderDTO));
			if(managerService.orderFoodDetail(storeOrderDTO).size() == 0){
				model.addAttribute("foodSize", 0);
			}else {
				model.addAttribute("orderFoodDetail", managerService.orderFoodDetail(storeOrderDTO));			
			}
			return "manager/orderDetailSample";
		}else {
			model.addAttribute("msg", 0);
			return "commons/result";
		}

	}
	// 수정 취소 버튼 클릭 시 발생
	@GetMapping("cancelOrderDetailUpdate")
	public String cancelOrderDetailUpdate(StoreOrderDTO storeOrderDTO, Model model) throws Exception{
		model.addAttribute("orderDetail", managerService.orderDetail(storeOrderDTO));
		
		if(managerService.orderFoodDetail(storeOrderDTO).size() == 0){
			model.addAttribute("foodSize", 0);
		}else {
			model.addAttribute("orderFoodDetail", managerService.orderFoodDetail(storeOrderDTO));			
		}
		return "manager/orderDetailSample";
	}
	
	// stockList-------------------------------------------------------------------------------------
	@GetMapping("stockList")
	public String stockList(Model model) throws Exception{
		model.addAttribute("stockList", managerService.stockList());
		model.addAttribute("stockLackList", managerService.stockLackList());
		return "manager/stockList";
	}
	
	// 재고 구매
	@PostMapping("buyingStock")
	public String buyingStock(@RequestBody StockMidBuyingDTO [] ar, Model model) throws Exception{
		System.out.println("@@ 길이 : " + ar.length);
		if(ar.length <= 0) {
			model.addAttribute("msg", 0);
			return "commons/result";
		}else {
		int result = managerService.buyingStock(ar);
		System.out.println("최종 결과 : " + result);
		model.addAttribute("msg", result);
		return "commons/result";
		}
	}
	
	// add items to food table. When click addBtn.
	@PostMapping("addItems")
	public void addItems(@RequestBody FoodDTO [] ar, Model model) throws Exception{
		managerService.addItems(ar);
	}
	
	// 재고 구매 기록
	@GetMapping("stockRecordList")
	public String stockRecordList(Model model) throws Exception{
		model.addAttribute("stockRecordList", managerService.getStockRecordList());
		return "manager/stockRecordList";
	}
	
	@GetMapping("stockRecordDetail")
	public String stockReecordDetail(StockBuyingDTO sbDTO, Model model) throws Exception{
		model.addAttribute("stockBuyingDetailBig", managerService.getStockBuyingDetailBig(sbDTO));
		model.addAttribute("stockBuyingDetailSmall", managerService.getStockBuyingDetailSmall(sbDTO));
		return "manager/stockRecordDetail";
	}
	
	
	//#@@@@@@@@@@@@@@@@22 끝
}
