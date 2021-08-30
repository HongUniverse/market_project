package com.green.sunny.dao;

import java.util.*;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.green.sunny.dto.ProductImageVO;
import com.green.sunny.dto.ProductVO;
import com.green.sunny.utils.Criteria;

@Repository
public class ProductDAO {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	public List<ProductVO> getProductListByKind(String kind) {		
		
		return mybatis.selectList("ProductDAO.getProductListByKind", kind);
	}
	
	public void insertProduct(ProductVO vo) {
		
		
		mybatis.insert("ProductDAO.insertProduct", vo);
	}
	
	public void insertImage(ProductImageVO pvo) {
		
		mybatis.insert("ProductDAO.insertImage", pvo);
	}
	
	public ProductVO getProduct(ProductVO vo) {		//������ ��ǰ��ȣ (pk)�̹Ƿ� ���ڵ尡 �ϳ� �����ϱ� List�� ������ �ʿ� ����.
		
		
	return mybatis.selectOne("ProductDAO.getProduct", vo);	//���� ���ڵ� �ϳ��̹Ƿ� selectOne�޼ҵ带 ���ش�.
	} 
	
	
	
	public ProductVO tryMethod(ProductVO vo) {
		
		return mybatis.selectOne("ProductDAO.try", vo);
	}
	
	public void deleteProduct(int pseq) {
		
		mybatis.delete("ProductDAO.deleteProduct", pseq);
	}
	
	public void deleteComment(int pseq) {
		
		mybatis.delete("ProductDAO.deleteComment", pseq);
	}
	
	public void deletePicture(int pseq) {
		
		mybatis.delete("ProductDAO.deletePicture", pseq);
	}
	
	public void updateProduct(ProductVO vo) {
		
		mybatis.update("ProductDAO.updateProduct", vo);
	}
	
	public List<ProductVO> getListWithPaging(Criteria criteria, String key, String kind) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("criteria", criteria);
		map.put("key", key);
		map.put("kind", kind);
		
		return mybatis.selectList("ProductDAO.listWithPaging", map);
	}
	
	public List<ProductImageVO> getOtherPicture(ProductImageVO pvo) {
		
		return mybatis.selectList("ProductDAO.getOtherPicture", pvo);
	}
	
	public int countProductList(String name, String kind) {
		HashMap<String, Object> map = new HashMap<>();
		
		map.put("name", name);
		map.put("kind", kind);
		return mybatis.selectOne("ProductDAO.countProductList", map);
	}
	
	public void plusCount(ProductVO vo) {
		
		mybatis.update("ProductDAO.plusCount", vo);
	}
	
	public int selectCount(int pseq) {
		return mybatis.selectOne("ProductDAO.selectCount", pseq);
	}
	
	public int selectMaxPseq() {
		return mybatis.selectOne("ProductDAO.selectMaxPseq");
	}
	
	public String getOnePicture(int pseq) {
		
		return mybatis.selectOne("ProductDAO.getOnePicture", pseq);
	}
	

}