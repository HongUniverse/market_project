package com.green.sunny.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.green.sunny.dto.JjimVO;
import com.green.sunny.dto.MemberVO;
import com.green.sunny.dto.OrderVO;
import com.green.sunny.dto.ProductVO;
import com.green.sunny.jjim.JjimService;
import com.green.sunny.member.MemberService;
import com.green.sunny.order.OrderService;
import com.green.sunny.utils.Criteria;
import com.green.sunny.utils.PageMaker;

@Controller
public class MyPageController {
	
	@Autowired
	private JjimService jjimService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MemberService memberService;
	
		
	// ?????? ?߰?
	@RequestMapping("/jjim_insert_list")
	public String jjimInsertList(HttpSession session,
								 @RequestParam(value="pseq") int pseq) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		JjimVO vo = new JjimVO();
		
		if (loginUser == null) {
			return "member/login";
		} else {
			
			vo.setId(loginUser.getId());
			
			vo.setPseq(pseq);
			
			jjimService.insertJjim(vo);
			
			return "redirect:/jjim_list";
		}
		
	}
	
	// ?????
	@RequestMapping(value="/jjim_list_cancel", method=RequestMethod.GET)
	public String jjimListCancel(@RequestParam(value="pseq") int pseq, HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		JjimVO vo = new JjimVO();
		
		if (loginUser == null) {
			return "member/login";
		} else {
			
			vo.setId(loginUser.getId());
			vo.setPseq(pseq);
			
			jjimService.insertJjim(vo);
			
			return "redirect:product_detail?pseq="+pseq;
		}
		
	}
	
	// ?򸮽?Ʈ(????¡ ????)
	@RequestMapping("/jjim_list")
	public String jjimList(String id, Criteria criteria, HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");

		id = loginUser.getId();
		
		List<JjimVO> jjimList = jjimService.getJjimListPaging(criteria, id);			

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(criteria);   // ???? ???????? ???????? ?׸? ?? ????
		
		// ??ü ?Խñ??? ?? ??ȸ
		int totalCount = jjimService.countJjimList(loginUser.getId());
		pageMaker.setTotalCount(totalCount);
		
		model.addAttribute("jjimListSize", jjimList.size());
		model.addAttribute("jjimList", jjimList);
		model.addAttribute("pageMaker", pageMaker);
		
		return "mypage/jjimList_boardType";   // ?Խ??Ǹ???Ʈ????
		
	}
	
	
	// ??????
	@RequestMapping("/jjim_delete")
	public String jjimDelete(HttpSession session, 
						     @RequestParam(value="jjseq") int jjseq) {
		
		jjimService.deleteJjim(jjseq);
		
		return "redirect:jjim_list";
	}
	
	// ????ǰ????Ʈ(????¡ ????)
	@RequestMapping("/my_prod_list")
	public String myProductList(String id, Criteria criteria, HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
			
		} else {
			
			id = loginUser.getId();
			
			List<ProductVO>	prodList = orderService.myProductListPaging(criteria, id);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(criteria);
			
			int totalCount = orderService.countMyProduct(id);
			pageMaker.setTotalCount(totalCount);
			
			model.addAttribute("prodListSize", prodList.size());
			model.addAttribute("prodList", prodList);
			model.addAttribute("pageMaker", pageMaker);
			
			return "mypage/myProductList";
		}
		
	}

	@RequestMapping("/grade_detail")
	public String gradeDetail(HttpSession session, MemberVO vo, Model model) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		vo.setId(loginUser.getId());
				
		String[] gradeList = {"Welcome(Bronze)", "Silver", "Gold", "Platinum", "VIP(Diamond)"};
		String gradeDetail = gradeList[0];
		
		MemberVO member = memberService.getMember(vo);
		
		int grade = Integer.parseInt(member.getGrade());
		
		switch(grade) {
			case 1 :
				gradeDetail = gradeList[0];
				break;
			case 2 :
				gradeDetail = gradeList[1];
				break;
			case 3 :
				gradeDetail = gradeList[2];
				break;
			case 4 :
				gradeDetail = gradeList[3];
				break;
			case 5 :
				gradeDetail = gradeList[4];
				break;
		}
		
		model.addAttribute("MemberVO", member);
		model.addAttribute("gradeDetail", gradeDetail);
	
		return "mypage/myGrade";
	}
	
	@RequestMapping("/order_list")
	public String orderList(HttpSession session, Criteria criteria, String id, Model model) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		id = loginUser.getId();
		
		List<OrderVO> orderList = orderService.orderListPaging(criteria, id);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(criteria);
		
		int totalCount = orderService.countMyOrder(id);
		pageMaker.setTotalCount(totalCount);
		
		model.addAttribute("orderList", orderList);
		model.addAttribute("pageMaker", pageMaker);
		
		return "mypage/orderList";
	}
	
	@RequestMapping("/order_set")
	public String orderSet(@RequestParam(value="pid") String pid, 
						   OrderVO vo, MemberVO mVo, HttpSession session) {
		
		// ?????ڰ???
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		vo.setId(loginUser.getId());
		
		orderService.orderSet(vo);
		
		// ?Ǹ??? ???ް???
		mVo.setId(pid);
		MemberVO member = memberService.getMember(mVo);
				
		int count = orderService.orderSetCount(member.getId());  // ???ް??? ?߰?????
				
		if(count == 10) {
			
			memberService.gradeChange(member);
			
		} else if (count == 15) {
			
			memberService.gradeChange(member);
			
		} else if (count == 25) {
			
			memberService.gradeChange(member);
			
		} else if (count == 35) {
			
			memberService.gradeChange(member);
			
		}
		
		return "redirect:order_list";
	}

}
