package com.green.sunny.member;

import java.util.List;

import com.green.sunny.dto.AddressDoroVO;
import com.green.sunny.dto.AddressJibunVO;
import com.green.sunny.dto.MemberVO;

public interface MemberService {

	// ȸ�� ������
	MemberVO getMember(MemberVO vo);

	// ȸ�� ID ���� Ȯ��
	int confirmID(String id);

	/*
	 * ȸ�� �α���
	 */
	int loginID(MemberVO vo);

	// ȸ���߰�
	void insertMember(MemberVO vo);
	
	// �ּ� ã��
	// ������ ã��
	public List<AddressJibunVO> selectAddressByDong(String dong) ;
	
	// ���θ����� ã��
	public List<AddressDoroVO> selectAddressByDoro(String doro);
	
	// ���̵� �ߺ� üũ
	public int userIdCheck(String id);
	
	// �г��� �ߺ�üũ
	public int userNicknameCheck(String nick_name);

	// ȸ������ ����
	public void updateMember(MemberVO vo);
	
	// ���̵� ã��
	public MemberVO getMemberByNameAndEmail(String name, String email);
	
	public MemberVO findPassword(String id, String name, String email);
	
	public void changePassword(MemberVO vo);
	
}