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
import com.green.sunny.dto.ProductVO;
import com.green.sunny.jjim.JjimService;
import com.green.sunny.order.OrderService;
import com.green.sunny.utils.Criteria;
import com.green.sunny.utils.PageMaker;

@Controller
public class MyPageController {
	
	@Autowired
	private JjimService jjimService;
	@Autowired
	private OrderService orderService;
	
	/*
	 * 찜목록 페이지설정하는 부분 추후 다시 구현예정(새로운 페이지 열어서 찜목록이동/닫기 구현)
	@RequestMapping(value="/jjim_check_form", method=RequestMethod.GET)
	public String jjimCheckView(HttpSession session) {
		
			return "mypage/jjim_check";
	}
	*/
	
	/* 
	 * 나중에 다시 실현~~~~!!! 위에꺼랑 연관된 부분
	@RequestMapping(value="/jjim_insert", method=RequestMethod.GET)
	public String JjimInsert(HttpSession session, JjimVO vo) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "member/login";
		} else {
			
			vo.setId(loginUser.getId());
			
			jjimService.insertJjim(vo);
			
			return "category/cancel";
		}
		
	}
	*/
	
	// 찜목록 추가
	@RequestMapping(value="/jjim_insert_list", method=RequestMethod.GET)
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
	
	// 물어보기
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
				
				//return "mypage/jjimList";
				return "redirect:product_detail?pseq="+pseq;
			}
			
		}
	
		// 찜리스트(페이징 포함)
	@RequestMapping(value="/jjim_list", method=RequestMethod.GET)
	public String jjimList(String id, Criteria criteria, HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		
//		if(loginUser == null) {
//			return "member/login";
//		} else {

			id = loginUser.getId();
			
			//List<JjimVO> jjimList = jjimService.jjimList(loginUser.getId());
			List<JjimVO> jjimList = jjimService.getJjimListPaging(criteria, id);			

			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(criteria);   // 현재 페이지와 페이지당 항목 수 설정
			
			// 전체 게시글의 수 조회
			int totalCount = jjimService.countJjimList(loginUser.getId());
			pageMaker.setTotalCount(totalCount);
//			System.out.println("페이지정보-> "+pageMaker);
			
			model.addAttribute("jjimListSize", jjimList.size());
			model.addAttribute("jjimList", jjimList);
			model.addAttribute("pageMaker", pageMaker);
			
			//return "mypage/jjimList";   //이미지표시형식 
			return "mypage/jjimList_boardType";   // 게시판리스트형식
//		}
		
	}
	
	
	// 찜삭제
	@RequestMapping(value="/jjim_delete", method=RequestMethod.GET)
	public String jjimDelete(HttpSession session, @RequestParam(value="jjseq") int jjseq) {
		
		jjimService.deleteJjim(jjseq);
		
		return "redirect:jjim_list";
	}
	
	// 내상품리스트(페이징 포함)
	@RequestMapping(value="/my_prod_list", method=RequestMethod.GET)
	public String myProductList(String id, Criteria criteria, HttpSession session, Model model) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
//		ProductVO vo = new ProductVO();
		
		if(loginUser == null) {
			return "member/login";
			
		} else {
			
//			vo.setId(loginUser.getId());
//			List<ProductVO> prodList = orderService.myProductList(vo);
			
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

}
