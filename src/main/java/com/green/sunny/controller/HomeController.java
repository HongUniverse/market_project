package com.green.sunny.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.green.sunny.dto.ProductVO;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
//	@Autowired
//	private ProductService productService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String home(Model model) {	
		
//		//�Ż�ǰ ��ȸ ���� ȣ�� 
//		List<ProductVO> newProdList =  productService.getNewProductList();
//		model.addAttribute("newProductList", newProdList);
//		
//		
//		//����Ʈ ��ǰ ��ȸ ����
//		List<ProductVO> bestProdList = productService.getBestProductList();
//		model.addAttribute("bestProductList", bestProdList);		
		return "index";		
	}
	
}
