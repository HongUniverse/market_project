package com.green.sunny.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.green.sunny.board.BoardService;
import com.green.sunny.dto.BoardVO;
import com.green.sunny.dto.MemberVO;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/moveBoard", method = RequestMethod.GET)
	public String moveBoard() {
        return "board/board";
	} 
	
	//��ü �� ��ȸ
	@ResponseBody // ajax�̹Ƿ�
	@RequestMapping(value="/getBoard", method = RequestMethod.GET)
	public Map<String, Object> selectBoard(HttpServletResponse response) throws IOException {
		System.out.println("getBoard");
		//���� ����
//		List<Map<String,Object>> resultMap = new ArrayList<Map<String,Object>>();
//		resultMap = boardService.selectBoardList();
		
		//���������̺� api�� ����ϱ� ���� ����
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", boardService.selectBoardList());
		System.out.println("map:"+map);
		return map;
	}
	
	@RequestMapping(value="/getBoardDetail", method = RequestMethod.GET)
	public String moveBoard(@RequestParam(value="bseq") int bseq, Model model, BoardVO vo) {
		//System.out.println("bseq:"+bseq);
		Map<String, Object> map = new HashMap<String, Object>();

	    vo.setCount(boardService.selectCount(bseq));
		boardService.plusCount(vo);
		
		map = boardService.selectBoardDetail(bseq);
		model.addAttribute("map", map);
		
		
        return "board/board_detail";
	} 
	
	@RequestMapping(value="/openBoardList.do", method = RequestMethod.GET)
	public String moveBackBoard() {

		return "board/board";
	} 
	
	@RequestMapping(value="/openBoardDelete.do", method = RequestMethod.GET)
	public String DeleteBoard(int bseq) {
		//System.out.println("bseq=" +bseq);
		
		boardService.deleteBoard(bseq);
		
		return "board/board";
	} 
	
	@RequestMapping(value="/BoardWrite", method = RequestMethod.GET)
	public String BoardWrite() {

		return "board/board_write";
	} 
	
	@RequestMapping(value="/write_save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> BoardWriteForm(@RequestParam Map<String,Object> param, HttpSession session ,@RequestParam(value="files[]") @Nullable List<String> files) {
		MemberVO loginUser = (MemberVO)session.getAttribute("loginUser");
		Map<String,Object> resultMap = new HashMap<String, Object>();
		param.put("id", loginUser.getId());
		
		try {
			//�� ���
			boardService.insertBoardInfo(param);
			//bSeq ���ϱ�
			int bSeq = boardService.seletcBSeq();
			param.put("bSeq", bSeq);
			//���� ���
			for(String fileName : files) {
				param.put("fileName", fileName);
				boardService.insertFile(param);
			}
			resultMap.put("msg", "��� �����Ͽ����ϴ�.");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("msg", "��� �����Ͽ����ϴ�.");
		}
		
		return resultMap;
	} 
	
	@RequestMapping(value="/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
	@ResponseBody
	public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request )  {
		
		JsonObject jsonObject = new JsonObject();
		
		// ���ΰ�η� ����
		String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
		String fileRoot = contextRoot+"WEB-INF/resources/images/";
		String originalFileName = multipartFile.getOriginalFilename();	//�������� ���ϸ�
		//String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//���� Ȯ����
//		String savedFileName = UUID.randomUUID() + extension;	//����� ���� ��
		
		File targetFile = new File(fileRoot + originalFileName);	
		try {
			InputStream fileStream = multipartFile.getInputStream();
			FileUtils.copyInputStreamToFile(fileStream, targetFile);	//���� ����

			jsonObject.addProperty("url", "images/"+originalFileName); // contextroot + resources + ������ ���� ������
			jsonObject.addProperty("responseCode", "success");
				
		} catch (IOException e) {
			FileUtils.deleteQuietly(targetFile);	//����� ���� ����
			jsonObject.addProperty("responseCode", "error");
			e.printStackTrace();
		}
		String a = jsonObject.toString();
		
		return a;
	}
}	
