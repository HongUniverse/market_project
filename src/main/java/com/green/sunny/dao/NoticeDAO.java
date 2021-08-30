package com.green.sunny.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.sunny.dto.GongziVO;
import com.green.sunny.dto.OneoneVO;
import com.green.sunny.dto.QuestionVO;
import com.green.sunny.utils.Criteria;

@Repository
public class NoticeDAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// �������� ����Ʈ
	public List<GongziVO> noticeList(GongziVO vo) {
		
		return mybatis.selectList("NoticeDAO.noticeList", vo);
	}
	
	// ����¡���� �������� ����Ʈ
	public List<GongziVO> noticeListPaging(Criteria criteria, GongziVO vo) {
		
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("criteria", criteria);
		map.put("vo", vo);
		
		return mybatis.selectList("NoticeDAO.noticeListPaging", map);
	}
	
	// �������� �Խù� ����
	public int countNotice() {
		
		return mybatis.selectOne("NoticeDAO.countNotice");
	}
	
	// �������� ��ȸ��
	public void plusCntNotice(GongziVO vo) {
		
		mybatis.update("NoticeDAO.plusCntNotice", vo);
	}
	
	public int noticeCnt(int gseq) {
		
		return mybatis.selectOne("NoticeDAO.noticeCnt", gseq);
	}
	
	// �������� ������
	public GongziVO noticeDetail(GongziVO vo) {
		
		return mybatis.selectOne("NoticeDAO.noticeDetail", vo);
	}
	
	// ���ֹ������� ����Ʈ
	public List<QuestionVO> questionList(QuestionVO vo) {
		
		return mybatis.selectList("NoticeDAO.questionList", vo);
	}
	
	// 1:1 ���� ���
	public void inserOneone(OneoneVO vo) {
		
		mybatis.insert("NoticeDAO.insertOneone", vo);
	}
	
	// 1:1 ��������Ѱ� �󼼺���
	public OneoneVO oneoneDetail(OneoneVO vo) {
		
		return mybatis.selectOne("NoticeDAO.oneoneDetail", vo);
	}
	
	//1:1 ���� ����Ʈ
	public List<OneoneVO> oneoneList(OneoneVO vo) {
		
		return mybatis.selectList("NoticeDAO.oneoneList", vo);
	}
	
	
}
