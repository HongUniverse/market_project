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

import com.green.sunny.dto.ProductImageVO;
import com.green.sunny.dto.ProductVO;
import com.green.sunny.product.ProductService;



/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String home(Model model) {	
		
//		//신상품 조회 서비스 호출 
//		List<ProductVO> newProdList =  productService.getNewProductList();
//		model.addAttribute("newProductList", newProdList);
//		
//		
//		//베스트 상품 조회 서비스
//		List<ProductVO> bestProdList = productService.getBestProductList();
//		model.addAttribute("bestProductList", bestProdList);	
		
		List<ProductImageVO> allPicture = productService.getAllPicture();
		model.addAttribute("allPicture", allPicture);
		
		System.out.println(allPicture);
		return "index";		
	}
	
	@RequestMapping(value="/admin_index")
	public String admin_home() {
		return "admin/login";
	}
	
}
