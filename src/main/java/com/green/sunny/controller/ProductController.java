package com.green.sunny.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.green.sunny.dto.MemberVO;
import com.green.sunny.dto.ProductImageVO;
import com.green.sunny.dto.ProductVO;
import com.green.sunny.member.MemberService;
import com.green.sunny.product.ProductService;
import com.green.sunny.utils.Criteria;
import com.green.sunny.utils.PageMaker;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/category", method=RequestMethod.GET)
	public String productKindAction(ProductVO vo, Model model, @RequestParam(value="key", defaultValue="") String key,
			Criteria criteria, String kind) {
		//List<ProductVO> listProduct = productService.getProductListByKind(vo.getKind());
		//model.addAttribute("productKindList", listProduct);
		
		System.out.println("vo=" +vo);
		//����¡ ó�� 
		List<ProductVO> prodList = productService.getListWithPaging(criteria, key, kind);
		
//		if(vo.getKind().equals("1")) {
//			vo.setKind_nm("�м��Ƿ�/��ȭ");
//		}
		ProductVO productTry = productService.tryMethod(vo);
		
		//String pageKind = kind;
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(criteria); 	//���� �������� �������� �׸� �� ���� 
		
		//��ü �Խñ��� �� ��ȸ
		int totalCount = productService.countProductList(key, kind);
		pageMaker.setTotalCount(totalCount);
//		System.out.println("����¡ ����=" + pageMaker);
		
		model.addAttribute("productListSize", prodList.size());
		model.addAttribute("productList", prodList);
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("kind", kind);
		model.addAttribute("productTry",productTry);
		model.addAttribute("criteria", criteria);
		return "category/product_list";
	}
	
	@RequestMapping(value="/product_detail", method=RequestMethod.GET)
	public String productDetailAction(ProductVO vo, Model model, HttpSession session, ProductImageVO pvo) {
		
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		int count = productService.selectCount(vo.getPseq());
//		System.out.println(("count=" +count));
		
		vo.setCnt(count);
		productService.plusCount(vo);
		ProductVO product = productService.getProduct(vo);	
		
		//�ٸ����� �ҷ�����
		List<ProductImageVO> productImageList = productService.getOtherPicture(pvo);
		
		model.addAttribute("productVO", product);
		model.addAttribute("productImageList", productImageList);
		
	
		
		return "category/product_detail";
	}
	
	
	@RequestMapping(value="/admin_product_write_form")
	public String adminProductWriteView(Model model, HttpSession session) {
//	String url = "member/login";
//		
//		//���ǿ� ����� ����� ������ �о�´�.
//		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");		//setattribute�Ѱ� �����ü� �ִ�.
//		
//		if (loginUser == null) {	//���� �α����� �ȵ� ������
//			return url;
//		}else {
//			vo.setId(loginUser.getId());
//			
//			//��ٱ��� ���������� ���� ȣ�� 
//			cartService.insertCart(vo);
//			
//			return "redirect:cart_list";	//��ٱ��� ����� ��ȸ�Ͽ� ��ٱ��� ��� ȭ�� ǥ�� 
//		}
		
		String url = "member/login";
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return url;
		}else {
		
		String kindList[] = {"�м��Ƿ�/��ȭ", "��Ƽ", "���/���Ƶ�", "��ǰ", "�ֹ�/��Ȱ��ǰ", "���׸���", "����������", "������/����", "�ڵ�����ǰ", "����/����/DVD", "�ϱ�/����/���", "�ݷ�����", "�ｺ/�ǰ���ǰ", "���ᳪ��"};
		String kindList2[] = {"���ŷ�", "�����ŷ�", "��ΰ���"};
		model.addAttribute("kindList", kindList);
		model.addAttribute("kindList2", kindList2);
		
		return "category/product_write";
		}
	}
	/*
	 * ��ǰ���
	 */
	@RequestMapping(value="/admin_product_write", method=RequestMethod.POST)
	public String adminProductWrite(MultipartHttpServletRequest uploadFile ,ProductVO vo, ProductImageVO pvo, HttpSession session) {
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		if (loginUser == null) return "index";
		else {
			    vo.setId(loginUser.getId());
			    
				productService.insertProduct(vo);
				int pseq = productService.selectMaxPseq();
				
			    List<MultipartFile> fileList = uploadFile.getFiles("file");
		        String src = uploadFile.getParameter("src");
		        String path = session.getServletContext().getRealPath("WEB-INF/resources/product_images/");
		        
		        for (MultipartFile mf : fileList) {
		            String originFileName = mf.getOriginalFilename(); // ���� ���� ��
		            long fileSize = mf.getSize(); // ���� ������
		            
		            pvo.setProduct_image(originFileName);
		            pvo.setPseq(pseq);
		            
		            String safeFile = path + System.currentTimeMillis() + originFileName;
		            productService.insertImage(pvo);
		            try {
		                mf.transferTo(new File(safeFile));
		            } catch (IllegalStateException e) {
		                e.printStackTrace();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
		        }
		        return "redirect:category?kind="+vo.getKind();
		}
	}
	
	@RequestMapping(value="/test")
	public String test() {
		
		return "category/cancel";
	}
	
	@RequestMapping(value="/product_delete")
	public String productDelete(ProductVO vo) {
		
		productService.deleteComment(vo.getPseq());
		productService.deletePicture(vo.getPseq());
		productService.deleteProduct(vo.getPseq());
		
		return "redirect:category?kind="+vo.getKind();  
	}
	
	@RequestMapping(value="/update_form")
	public String productUpdate(Model model, HttpSession session, ProductVO vo) {
		
		String url = "member/login";
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return url;
		}else {
		
		ProductVO product = productService.getProduct(vo);	
		
		model.addAttribute("productVO", product);
			
		String kindList[] = {"�м��Ƿ�/��ȭ", "��Ƽ", "���/���Ƶ�", "��ǰ", "�ֹ�/��Ȱ��ǰ", "���׸���", "����������", "������/����", "�ڵ�����ǰ", "����/����/DVD", "�ϱ�/����/���", "�ݷ�����", "�ｺ/�ǰ���ǰ", "���ᳪ��"};
		String kindList2[] = {"���ŷ�", "�����ŷ�", "��ΰ���"};
		model.addAttribute("kindList", kindList);
		model.addAttribute("kindList2", kindList2);
		
		return "category/product_update";
		}
		
		
	}
	
	@RequestMapping(value="/product_update")
	public String continueProductUpdate(MultipartHttpServletRequest uploadFile,ProductVO vo, HttpSession session, ProductImageVO pvo) {
		
	MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "index";
		}
		else {
			        vo.setId(loginUser.getId());
				    
					productService.insertProduct(vo);
					int pseq = productService.selectMaxPseq();
					
				    List<MultipartFile> fileList = uploadFile.getFiles("file");
			        String src = uploadFile.getParameter("src");
			        String path = session.getServletContext().getRealPath("WEB-INF/resources/product_images/");
			        
			        productService.deletePicture(pseq);
			        
			        for (MultipartFile mf : fileList) {
			            String originFileName = mf.getOriginalFilename(); // ���� ���� ��
			            long fileSize = mf.getSize(); // ���� ������
			            
			            pvo.setProduct_image(originFileName);
			            pvo.setPseq(pseq);
			            
			            String safeFile = path + System.currentTimeMillis() + originFileName;
			            productService.insertImage(pvo);
			            try {
			                mf.transferTo(new File(safeFile));
			            } catch (IllegalStateException e) {
			                e.printStackTrace();
			            } catch (IOException e) {
			                e.printStackTrace();
			            }
			        }
			        return "redirect:category?kind="+vo.getKind();
		}
	}
}