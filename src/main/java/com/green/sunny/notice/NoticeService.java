package com.green.sunny.notice;

import java.util.List;

import com.green.sunny.dto.GongziVO;
import com.green.sunny.dto.OneoneVO;
import com.green.sunny.dto.QuestionVO;
import com.green.sunny.utils.Criteria;

public interface NoticeService {

	// �������� ����Ʈ
	List<GongziVO> noticeList(GongziVO vo);
	
	// �������� ������
	public GongziVO noticeDetail(GongziVO vo);
	
	// ����¡���� �������� ����Ʈ
	public List<GongziVO> noticeListPaging(Criteria criteria, GongziVO vo);

	// �������� ��ȸ��
	public void plusCntNotice(GongziVO vo);
	public int noticeCnt(int gseq);
	
	// �������� �Խù� ����
	public int countNotice();
		
	// ���ֹ������� ����Ʈ
	List<QuestionVO> questionList(QuestionVO vo);

	// 1:1 ���� ���
	void inserOneone(OneoneVO vo);

	// 1:1 ��������Ѱ� �󼼺���
	OneoneVO oneoneDetail(OneoneVO vo);

	//1:1 ���� ����Ʈ
	List<OneoneVO> oneoneList(OneoneVO vo);

}