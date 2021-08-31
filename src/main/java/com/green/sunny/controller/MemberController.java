package com.green.sunny.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.green.sunny.dto.AddressDoroVO;
import com.green.sunny.dto.AddressJibunVO;
import com.green.sunny.dto.MemberVO;
import com.green.sunny.member.MemberService;

@Controller
@SessionAttributes("loginUser")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	/*
	 * �α��� ȭ�� ǥ��
	 */
	@RequestMapping(value="/login_form")
	public String loginView() {
		
		return "member/login";
	}
	
	/*
	 * ����� �α��� ó��
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginAction(MemberVO vo, Model model,
							@RequestParam(value="id") String id, 
							@RequestParam(value="pwd") String pwd) {
		
		MemberVO loginUser = null;
		
		int result = memberService.loginID(vo);
		
		if(result == 1) {  // ����� ���� ����
			loginUser = memberService.getMember(vo);
			
			model.addAttribute("loginUser", loginUser);
			
			return "redirect:/index";
		
		} else {
			
			return "member/login_fail";
		}
		
	}
	
	// �α׾ƿ�
	@RequestMapping("/logout")
	public String logout(SessionStatus status) {
		
		status.setComplete();
		
		return "redirect:/index";
	}
	
	// ������� ȭ�� ���
	@RequestMapping(value="/contract")
	public String contractView() {
		
		return "member/contract";   // ��ư �ȳ��ͼ� ��� ���
//		return "member/join";
	}
	
	// ȸ������ ������ ���
	@RequestMapping(value="/join_form")
	public String joinView() {
		
		return "member/join";
	}
	
	// ���̵� �ߺ�üũ ��Ʈ�ѷ�
	@RequestMapping(value="/idCheck", method=RequestMethod.POST)
	@ResponseBody
	public int idCheck(@RequestParam(value="id") String id) throws Exception {
		
		int result = memberService.userIdCheck(id);
		
		System.out.println(result);
		
		return result;
		
	}
	
	// �г��� �ߺ�üũ ��Ʈ�ѷ�
//	@RequestMapping(value="/nickNameCheck", method=RequestMethod.POST)
//	@ResponseBody
//	public int nickNameCheck(@RequestParam(value="nick_name") String nick_name) throws Exception {
//		
//		int result = memberService.userNicknameCheck(nick_name);
//		
//		System.out.println(result);
//		
//		return result;
//		
//	}
	
	// ȸ������ �Ϸ�
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String joinAction(@RequestParam(value="id") String id,
							 @RequestParam(value="addr1") String addr1,
							 @RequestParam(value="addr2") String addr2,
							 MemberVO vo) {
		
		MemberVO member = memberService.getIdMember(vo);
		
		System.out.println("�����¾��̵�"+id);
		System.out.println("�������̵�"+member.getId());
		
		if(id == member.getId()) {
			return "member/join_fail";
			
		} else {
			
			vo.setAddress(addr1+" "+addr2);
			memberService.insertMember(vo);
			
			return "member/login";
		}
		
		
	}
	
	@RequestMapping(value="/find_zip_num_dong")
	public String findZipNum1() {
		
		return "member/findZipNumDong";
	}
	
	@RequestMapping(value="/find_zip_num_doro")
	public String findZipNum2() {
		
		return "member/findZipNumDoro";
	}
	
	// �ּ�ã��
	@RequestMapping(value="/find_zip_dong", method=RequestMethod.POST)
	public String findZipNumDongAction(AddressJibunVO vo, Model model) {
		
		List<AddressJibunVO> addrList = memberService.selectAddressByDong(vo.getDong());
		
		model.addAttribute("addressList", addrList);
		
		return "member/findZipNumDong";
	}
	
	@RequestMapping(value="/find_zip_doro", method=RequestMethod.POST)
	public String findZipNumDoroAction(AddressDoroVO vo, Model model) {
		
		List<AddressDoroVO> addrList = memberService.selectAddressByDoro(vo.getDoro());
		
		model.addAttribute("addressList", addrList);
		
		return "member/findZipNumDoro";
	}
	
	// ȸ������ ������ �ѱ�� �� ���� ��������
	@RequestMapping(value="/member_info")
	public String memberInfo(HttpSession session, MemberVO vo, Model model) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
			
			String[] kindList = {"�м��Ƿ�/��ȭ", "��Ƽ", "���/���Ƶ�", "��ǰ", "�ֹ�/��Ȱ��ǰ", "���׸���", "����������", 
								"������/����", "�ڵ�����ǰ", "����/����/DVD", "�ϱ�/����/���", "�ݷ�����", "�ｺ/�ǰ���ǰ"};
			
			model.addAttribute("inter_kindList", kindList);
			
			vo.setId(loginUser.getId());
			
			MemberVO member = memberService.getMember(vo);
			model.addAttribute("MemberVO", member);

			return "member/memberInfo";
			
		}
	}
	
	// ȸ������ ���� ������ �̵�
	@RequestMapping(value="/member_update_form", method=RequestMethod.GET)
	public String updateMemberView(HttpSession session, MemberVO vo, Model model) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			return "member/login";
		} else {
		
			String[] kindList = {"�м��Ƿ�/��ȭ", "��Ƽ", "���/���Ƶ�", "��ǰ", "�ֹ�/��Ȱ��ǰ", "���׸���", "����������", 
					"������/����", "�ڵ�����ǰ", "����/����/DVD", "�ϱ�/����/���", "�ݷ�����", "�ｺ/�ǰ���ǰ"};

			model.addAttribute("inter_kindList", kindList);

			vo.setId(loginUser.getId());
						
			MemberVO member = memberService.getMember(vo);
			model.addAttribute("MemberVO", member);
		
			return "member/memberUpdate";
		}
	}
	
	// ȸ������ ����
	@RequestMapping(value="/member_update", method=RequestMethod.POST)
	public String updateMember(@RequestParam(value="addr1") String addr1,
			   				   @RequestParam(value="addr2") String addr2,
			   				   MemberVO vo) {
		
		vo.setAddress(addr1+" "+addr2);
		
		memberService.updateMember(vo);
		
		return "redirect:member_info";
		
	}

	// ���̵� ��й�ȣ ã�� �������� �̵�
	@RequestMapping("/find_id_form")
	public String findIdView() {
		
		return "member/find_id";
	}

	@RequestMapping("/find_pwd_form")
	public String findPwdView() {
		
		return "member/find_pwd";
	}
	
	@RequestMapping("/find_id")
	public String findId(MemberVO vo, Model model) {
		
		String name = vo.getName();
		String email = vo.getEmail();
		
		System.out.println(name + email);
		
		MemberVO member = memberService.getMemberByNameAndEmail(name, email);
		
		if(member != null) {
			model.addAttribute("message", 1);
			model.addAttribute("id", member.getId());
		} else {
			model.addAttribute("message", -1);
		}
		
		return "member/findResult";
		
	}
	
	@RequestMapping("/find_pwd")
	public String findPwd(MemberVO vo, Model model) {
		
		String id = vo.getId();
		String name = vo.getName();
		String email = vo.getEmail();
		
		System.out.println(id + name + email);
		
		MemberVO member = memberService.findPassword(id, name, email);
		
		if (member != null) {
			model.addAttribute("message", 1);
			model.addAttribute("id", member.getId());
		} else {
			model.addAttribute("message", -1);
		}
		
		return "member/findPwdResult";
		
	}
	
	@RequestMapping(value="/change_pwd", method=RequestMethod.GET)
	public String changePwd(MemberVO vo, Model model) {
		
		memberService.changePassword(vo);
		
		return "member/close";
		
	}
	
}
