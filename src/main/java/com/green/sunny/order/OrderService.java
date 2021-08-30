package com.green.sunny.order;

import java.util.List;

import com.green.sunny.dto.OrderVO;
import com.green.sunny.dto.ProductVO;
import com.green.sunny.utils.Criteria;

public interface OrderService {
	
	public List<ProductVO> myProductList(ProductVO vo);
	
	public int countMyProduct(String id);
	
	public List<ProductVO> myProductListPaging(Criteria criteria, String id);
	
	// �ֹ� �������� ����Ʈ
	public List<OrderVO> orderList(OrderVO vo);
	
	// �ֹ� Ȯ�� ��ư
	public void orderSet(OrderVO vo);
}