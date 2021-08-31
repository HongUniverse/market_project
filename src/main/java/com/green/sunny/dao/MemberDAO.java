package com.green.sunny.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.sunny.dto.AddressDoroVO;
import com.green.sunny.dto.AddressJibunVO;
import com.green.sunny.dto.MemberVO;

@Repository
public class MemberDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// ���̵� ��������
	public MemberVO getIdMember(MemberVO vo) {
		
		return mybatis.selectOne("MemberDAO.getIdMember", vo);
	}
	// ȸ�� ������
	// ���ǥ�ÿ����ε� ���
	public MemberVO getMember(MemberVO vo) {
		
		return mybatis.selectOne("MemberDAO.getMember", vo);
	}
	
	// ȸ�� ID ���� Ȯ��
	public int confirmID(String id) {
		
		String pwd = mybatis.selectOne("MemberDAO.confirmID", id);
		
		if (pwd != null) {  // ȸ���� ������ ���
			return 1;
		} else {   // ȸ���� ���� ���
			return -1;
		}
	}
	
	/*
	 * ȸ�� �α���
	 */
	public int loginID(MemberVO vo) {
		int result = -1;
		String pwd = null;
		
		pwd = mybatis.selectOne("MemberDAO.confirmID", vo.getId());
//		System.out.println(vo);   // Ȯ�ο�
//		System.out.println(pwd);
		// DB���� ��ȸ�� password�� ����ڰ� �Է��� password��
		if(pwd == null) {  // ����ڰ� �������� �ʴ� ���(���̵� Ʋ�����)
			result = -1;
		} else if (pwd.equals(vo.getPwd())) { // id�� pwd�� ��ġ
			result = 1;
		} else {  // ��й�ȣ�� ��ġ���� �ʴ� ���
			result = 0;
		}
		
		return result;
		
	}
	
	// ȸ���߰�
	public void insertMember(MemberVO vo) {
		
		mybatis.insert("MemberDAO.insertMember", vo);
	}
	
	// �ּ� ã��
	// ������ ã��
	public List<AddressJibunVO> selectAddressByDong(String dong) {
		
		return 	mybatis.selectList("MemberDAO.selectAddressByDong", dong);
	}
	
	// ���θ����� ã��
	public List<AddressDoroVO> selectAddressByDoro(String doro) {
		
		return mybatis.selectList("MemberDAO.selectAddressByDoro", doro);
	}
	
	// ���̵� �ߺ�üũ
	public int userIdCheck(String id) {
		
		return mybatis.selectOne("MemberDAO.checkOverId", id);
	}
	
	// �г��� �ߺ�üũ
	public int userNicknameCheck(String nick_name) {
		
		return mybatis.selectOne("MemberDAO.checkOverNickname", nick_name);
	}
	
	// ȸ������ ����
	public void updateMember(MemberVO vo) {
		
		mybatis.update("MemberDAO.updateMember", vo);
	}
	
	// ���̵� ã��
	public MemberVO getMemberByNameAndEmail(String name, String email) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("email", email);
		
		return mybatis.selectOne("MemberDAO.getMemberByNameAndEmail", map);
	}
	
	public MemberVO findPassword(String id, String name, String email) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("name", name);
		map.put("email", email);
		
		return mybatis.selectOne("MemberDAO.findPassword", map);
	}
	
	public void changePassword(MemberVO vo) {
		
		mybatis.update("MemberDAO.changePassword", vo);
	}
	
	// ���
	public List<MemberVO> gradeSelect(MemberVO vo){
		
		return mybatis.selectOne("MemberDAO.gradeSelect", vo); 
	}
	
}
