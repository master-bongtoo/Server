package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import Model.BoardDTO;
import Model.OccasionCashDTO;
import Model.OccasionCashGroupDTO;
import common_content.CC;

@Controller
public class OccasionController {
	//String url = "http://192.168.219.100:8080/bongtoo_server/";
	String url = new CC().connectIP();
	
	@Autowired
	private OccasionService occasionService;

	// 글쓰기(안드로이드 전용)
	@RequestMapping(value = "/occasion/occasionCashWriteJson.a")
	public ModelAndView occasionCashWriteJson(MultipartFile occ_cash_origin_img, HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		String occ_cash_name = request.getParameter("occ_cash_name");
		String occ_cash_group = request.getParameter("occ_cash_group");
		String occ_cash_place = request.getParameter("occ_cash_place");
		int occ_cash_money = Integer.parseInt(request.getParameter("occ_cash_money"));
		String occ_cash_invite_way = request.getParameter("occ_cash_invite_way");
		int occ_cash_attendance = Integer.parseInt(request.getParameter("occ_cash_attendance"));
		int occ_cash_group_index = Integer.parseInt(request.getParameter("occ_cash_group_index"));
		String occ_cash_date = request.getParameter("occ_cash_date");

		// 부가 정보받아오기 (부가 정보)
		// 부가정보 객체 선언 및 초기화
		String occ_cash_memo = "";
		String origin_fileName = "";
		String uuid_fileName = "";
		// 사진정보 체크
		if (occ_cash_origin_img != null) {
			// 사진정보 받아오기
			UUID uuid = UUID.randomUUID();
			String filePath = request.getServletContext().getRealPath("/File/occasionImage");
			origin_fileName = occ_cash_origin_img.getOriginalFilename();
			uuid_fileName = uuid.toString() + origin_fileName;
			File file = new File(filePath, uuid_fileName);

			// 파일 복사
			try {
				// getInputStream() : 업로드한 파일 데이터를 읽어오는 InputStream을 구한다.
				FileCopyUtils.copy(occ_cash_origin_img.getInputStream(), new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 메모 체크
		if (request.getParameter("occ_cash_memo") != null) {
			occ_cash_memo = request.getParameter("occ_cash_memo");
		}

		// DTO저장(not null & primary key)
		OccasionCashDTO occasionCashDTO = new OccasionCashDTO();
		occasionCashDTO.setMember_num(member_num);
		occasionCashDTO.setOcc_cash_name(occ_cash_name);
		occasionCashDTO.setOcc_cash_group(occ_cash_group);
		occasionCashDTO.setOcc_cash_place(occ_cash_place);
		occasionCashDTO.setOcc_cash_money(occ_cash_money);
		occasionCashDTO.setOcc_cash_place(occ_cash_place);
		occasionCashDTO.setOcc_cash_invite_way(occ_cash_invite_way);
		occasionCashDTO.setOcc_cash_attendance(occ_cash_attendance);
		occasionCashDTO.setOcc_cash_date(occ_cash_date);
		occasionCashDTO.setOcc_cash_group_index(occ_cash_group_index);
		// DTO저장(부가 정보)
		occasionCashDTO.setOcc_cash_origin_img(origin_fileName);
		occasionCashDTO.setOcc_cash_uuid_img(uuid_fileName);
		occasionCashDTO.setOcc_cash_memo(occ_cash_memo);
		int total = occasionService.occasionCashWriteJson(occasionCashDTO);
		String rt = null;
		if (total > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		json.put("rt", rt);
		json.put("total", total);
		json.put("item", item);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("../app.jsp");
		return modelAndView;
	}

	// 상세보기(안드로이드 전용)
	@RequestMapping(value = "/occasion/occasionCashViewJson.a")
	public ModelAndView occasionCashViewJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int occ_cash_num = Integer.parseInt(request.getParameter("occ_cash_num"));
		int total = 0;
		OccasionCashDTO occasionCashDTO = occasionService.occasionCashViewJson(occ_cash_num);
		if (occasionCashDTO != null) {
			total = 1;
		}
		String filePath = null;
		String rt = null;
		if (total > 0) {
			rt = "OK";

		} else {
			rt = "FAIL";
		}
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		if (total > 0) {
			JSONObject temp = new JSONObject();
			temp.put("member_num", occasionCashDTO.getMember_num());
			temp.put("occ_cash_name", occasionCashDTO.getOcc_cash_name());
			temp.put("occ_cash_group", occasionCashDTO.getOcc_cash_group());
			temp.put("occ_cash_place", occasionCashDTO.getOcc_cash_place());
			temp.put("occ_cash_money", occasionCashDTO.getOcc_cash_money());
			temp.put("occ_cash_invite_way", occasionCashDTO.getOcc_cash_invite_way());
			temp.put("occ_cash_attendance", occasionCashDTO.getOcc_cash_attendance());
			temp.put("occ_cash_date", occasionCashDTO.getOcc_cash_date());
			temp.put("occ_cash_num", occasionCashDTO.getOcc_cash_num());
			temp.put("occ_cash_group_index", occasionCashDTO.getOcc_cash_group_index());
			if (occasionCashDTO.getOcc_cash_origin_img() != null) {
				filePath = url+"File/occasionImage/";
				temp.put("occ_cash_img_path", filePath + occasionCashDTO.getOcc_cash_uuid_img());
				temp.put("occ_cash_origin_img ", occasionCashDTO.getOcc_cash_origin_img());
			} else {
				temp.put("occ_cash_img_path", "");
				temp.put("occ_cash_origin_img", "");
			}
			if (occasionCashDTO.getOcc_cash_memo() != null) {
				temp.put("occ_cash_memo", occasionCashDTO.getOcc_cash_memo());
			} else {
				temp.put("occ_cash_memo", "");
			}

			// JSONArray에 저장
			item.put(temp);
		}
		json.put("rt", rt);
		json.put("total", total);
		json.put("item", item);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("../app.jsp");
		return modelAndView;
	}

	// 정보수정(안드로이드 전용)
	@RequestMapping(value = "/occasion/occasionCashUpdateJson.a")
	public ModelAndView occasionCashUpdateJson(MultipartFile occ_cash_origin_img, HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int occ_cash_num = Integer.parseInt(request.getParameter("occ_cash_num"));
		OccasionCashDTO occasionCashDTO = new OccasionCashDTO();
		occasionCashDTO = occasionService.occasionCashViewJson(occ_cash_num);
		
		String occ_cash_name = occasionCashDTO.getOcc_cash_name();
		String occ_cash_group = occasionCashDTO.getOcc_cash_group();
		String occ_cash_place = occasionCashDTO.getOcc_cash_place();
		Integer occ_cash_money = occasionCashDTO.getOcc_cash_money();
		String occ_cash_invite_way = occasionCashDTO.getOcc_cash_invite_way();
		Integer occ_cash_attendance = occasionCashDTO.getOcc_cash_attendance();
		String occ_cash_date = occasionCashDTO.getOcc_cash_date();
		
		if(request.getParameter("occ_cash_name") != null) {
			occ_cash_name = request.getParameter("occ_cash_name");
		} else if(occ_cash_name != null){
			
		} else {
			occ_cash_name = "";
		}
		
		if(request.getParameter("occ_cash_group") != null) {
			occ_cash_group = request.getParameter("occ_cash_group");
		} else if(occ_cash_group != null){
			
		} else {
			occ_cash_group = "";
		}
		
		if(request.getParameter("occ_cash_place") != null) {
			occ_cash_place = request.getParameter("occ_cash_place");
		} else if(occ_cash_place != null){
			
		} else {
			occ_cash_place = "";
		}
		
		if(request.getParameter("occ_cash_money") != null) {
			occ_cash_money = Integer.parseInt(request.getParameter("occ_cash_money"));
		} else if(occ_cash_money != null){
			
		} else {
			occ_cash_money = null;
		}
		
		if(request.getParameter("occ_cash_attendance") != null) {
			occ_cash_attendance = Integer.parseInt(request.getParameter("occ_cash_attendance"));
		} else if(occ_cash_attendance != null){
			
		} else {
			occ_cash_attendance = null;
		}
		
		if(request.getParameter("occ_cash_date") != null) {
			occ_cash_date = request.getParameter("occ_cash_date");
		} else if(occ_cash_date != null){
			
		} else {
			occ_cash_date = "";
		}
				
		// 부가 정보받아오기 (부가 정보)
		// 부가정보 객체 선언 및 초기화
		String occ_cash_memo = "";
		String origin_fileName = "";
		String uuid_fileName = "";
		// 사진정보 체크
		if (occ_cash_origin_img != null) {
			// 사진정보 받아오기
			UUID uuid = UUID.randomUUID();
			String filePath = request.getServletContext().getRealPath("/File/occasionImage");
			origin_fileName = occ_cash_origin_img.getOriginalFilename();
			uuid_fileName = uuid.toString() + origin_fileName;
			File file = new File(filePath, uuid_fileName);

			// 파일 복사
			try {
				// getInputStream() : 업로드한 파일 데이터를 읽어오는 InputStream을 구한다.
				FileCopyUtils.copy(occ_cash_origin_img.getInputStream(), new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 메모 체크
		if (request.getParameter("occ_cash_memo") != null) {
			occ_cash_memo = request.getParameter("occ_cash_memo");
		}

		// DTO저장(not null & primary key)
		occasionCashDTO.setOcc_cash_num(occ_cash_num);
		occasionCashDTO.setMember_num(member_num);
		occasionCashDTO.setOcc_cash_name(occ_cash_name);
		occasionCashDTO.setOcc_cash_group(occ_cash_group);
		occasionCashDTO.setOcc_cash_place(occ_cash_place);
		occasionCashDTO.setOcc_cash_money(occ_cash_money);
		occasionCashDTO.setOcc_cash_place(occ_cash_place);
		occasionCashDTO.setOcc_cash_invite_way(occ_cash_invite_way);
		occasionCashDTO.setOcc_cash_attendance(occ_cash_attendance);
		occasionCashDTO.setOcc_cash_date(occ_cash_date);
		// DTO저장(부가 정보)
		occasionCashDTO.setOcc_cash_origin_img(origin_fileName);
		occasionCashDTO.setOcc_cash_uuid_img(uuid_fileName);
		occasionCashDTO.setOcc_cash_memo(occ_cash_memo);
		int total = occasionService.occasionCashUpdateJson(occasionCashDTO);
		String rt = null;
		if (total > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		json.put("rt", rt);
		json.put("total", total);
		json.put("item", item);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("../app.jsp");
		return modelAndView;
	}

	// 삭제하기(안드로이드 전용)
	@RequestMapping(value = "/occasion/occasionCashDeleteJson.a")
	public ModelAndView occasionCashDeleteJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int occ_cash_num = Integer.parseInt(request.getParameter("occ_cash_num"));
		int total = 0;
		total = occasionService.occasionCashDeleteJson(occ_cash_num);
		String rt = null;
		if (total > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		json.put("rt", rt);
		json.put("total", total);
		json.put("item", item);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("../app.jsp");
		return modelAndView;
	}

	// 리스트 (안드로이드 전용)
	@RequestMapping(value = "/occasion/occasionCashListJson.a")
	public ModelAndView boardListJson(HttpServletRequest request) {
		// 1. 데이터
		int pg = 1;
		int money = 0;
		int total_money = 0;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		if (request.getParameter("money") != null) {
			money = Integer.parseInt(request.getParameter("money"));
		}
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		// 2. DB
		// 1페이지당 목록 5개
		int endNum = pg * 10;
		int startNum = endNum - 9;
		List<OccasionCashDTO> list;
		if(money == 0) {
			list = occasionService.occasionCashListJson(startNum, endNum, member_num);
		} else {
			list = occasionService.occasionCashMoneyListJson(startNum, endNum, member_num);
		}
		if(list.size()>0) {
			total_money = occasionService.occasionCashTotalMoneyJson(member_num);
		}
		

		// JSON으로 결과값 반환
		String filePath = url+"File/occasionImage/";
		String rt = null;
		int total = 0;
		int totalAll = 0;
		total = list.size();
		totalAll = occasionService.occasionCashListTotalJson(member_num);
		if (total > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		if (total > 0) {
			for (int i = 0; i < total; i++) {
				OccasionCashDTO occasionCashDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("member_num", occasionCashDTO.getMember_num());
				temp.put("occ_cash_name", occasionCashDTO.getOcc_cash_name());
				temp.put("occ_cash_group", occasionCashDTO.getOcc_cash_group());
				temp.put("occ_cash_place", occasionCashDTO.getOcc_cash_place());
				temp.put("occ_cash_money", occasionCashDTO.getOcc_cash_money());
				temp.put("occ_cash_invite_way", occasionCashDTO.getOcc_cash_invite_way());
				temp.put("occ_cash_attendance", occasionCashDTO.getOcc_cash_attendance());
				temp.put("occ_cash_date", occasionCashDTO.getOcc_cash_date());
				temp.put("occ_cash_num", occasionCashDTO.getOcc_cash_num());
				temp.put("occ_cash_group_index", occasionCashDTO.getOcc_cash_group_index());
				if (occasionCashDTO.getOcc_cash_origin_img() != null) {
					filePath = url+"File/occasionImage/";
					temp.put("occ_cash_img_path", filePath + occasionCashDTO.getOcc_cash_uuid_img());
					temp.put("occ_cash_origin_img ", occasionCashDTO.getOcc_cash_origin_img());
				} else {
					temp.put("occ_cash_img_path", "");
					temp.put("occ_cash_origin_img", "");
				}
				if (occasionCashDTO.getOcc_cash_memo() != null) {
					temp.put("occ_cash_memo", occasionCashDTO.getOcc_cash_memo());
				} else {
					temp.put("occ_cash_memo", "");
				}

				// JSONArray에 저장
				item.put(i, temp);
			}
		}

		json.put("rt", rt);
		json.put("total", total);
		json.put("totalAll", totalAll);
		json.put("total_money", total_money);
		json.put("item", item);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("../app.jsp");
		return modelAndView;
	}
	
	
	// 그룹 글쓰기(안드로이드 전용)
	@RequestMapping(value = "/occasion/occasionCashGroupWriteJson.a")
	public ModelAndView occasionCashGroupWriteJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));

		// 부가 정보받아오기 (부가 정보)
		// 부가정보 객체 선언 및 초기화
		String occ_cash_group1 = "";
		String occ_cash_group2 = "";
		String occ_cash_group3 = "";
		String occ_cash_group4 = "";
		String occ_cash_group5 = "";
		String occ_cash_group6 = "";
		String occ_cash_group7 = "";
		String occ_cash_group8 = "";
		String occ_cash_group9 = "";
		String occ_cash_group10 = "";
		if(request.getParameter("occ_cash_group1") != null) {
			occ_cash_group1 = request.getParameter("occ_cash_group1");
		}
		if(request.getParameter("occ_cash_group2") != null) {
			occ_cash_group2 = request.getParameter("occ_cash_group2");
		}
		if(request.getParameter("occ_cash_group3") != null) {
			occ_cash_group3 = request.getParameter("occ_cash_group3");
		}
		if(request.getParameter("occ_cash_group4") != null) {
			occ_cash_group4 = request.getParameter("occ_cash_group4");
		}
		if(request.getParameter("occ_cash_group5") != null) {
			occ_cash_group5 = request.getParameter("occ_cash_group5");
		}
		if(request.getParameter("occ_cash_group6") != null) {
			occ_cash_group6 = request.getParameter("occ_cash_group6");
		}
		if(request.getParameter("occ_cash_group7") != null) {
			occ_cash_group7 = request.getParameter("occ_cash_group7");
		}
		if(request.getParameter("occ_cash_group8") != null) {
			occ_cash_group8 = request.getParameter("occ_cash_group8");
		}
		if(request.getParameter("occ_cash_group9") != null) {
			occ_cash_group9 = request.getParameter("occ_cash_group9");
		}
		if(request.getParameter("occ_cash_group10") != null) {
			occ_cash_group10 = request.getParameter("occ_cash_group10");
		}

		// DTO저장(not null & primary key)
		OccasionCashGroupDTO occasionCashGroupDTO = new OccasionCashGroupDTO();
		occasionCashGroupDTO.setMember_num(member_num);
		// DTO저장(부가 정보)
		occasionCashGroupDTO.setOcc_cash_group1(occ_cash_group1);
		occasionCashGroupDTO.setOcc_cash_group2(occ_cash_group2);
		occasionCashGroupDTO.setOcc_cash_group3(occ_cash_group3);
		occasionCashGroupDTO.setOcc_cash_group4(occ_cash_group4);
		occasionCashGroupDTO.setOcc_cash_group5(occ_cash_group5);
		occasionCashGroupDTO.setOcc_cash_group6(occ_cash_group6);
		occasionCashGroupDTO.setOcc_cash_group7(occ_cash_group7);
		occasionCashGroupDTO.setOcc_cash_group8(occ_cash_group8);
		occasionCashGroupDTO.setOcc_cash_group9(occ_cash_group9);
		occasionCashGroupDTO.setOcc_cash_group10(occ_cash_group10);
		int total = occasionService.occasionCashGroupWriteJson(occasionCashGroupDTO);
		String rt = null;
		if (total > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		json.put("rt", rt);
		json.put("total", total);
		json.put("item", item);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("../app.jsp");
		return modelAndView;
	}
	
	// 그룹 정보수정(안드로이드 전용)
	@RequestMapping(value = "/occasion/occasionCashGroupUpdateJson.a")
	public ModelAndView occasionCashGroupUpdateJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		OccasionCashGroupDTO occasionCashGroupDTO = occasionService.occasionCashGroupViewJson(member_num);
		
		// 부가 정보받아오기 (부가 정보)
		// 부가정보 객체 선언 및 초기화
		String occ_cash_group1 = occasionCashGroupDTO.getOcc_cash_group1();
		String occ_cash_group2 = occasionCashGroupDTO.getOcc_cash_group2();
		String occ_cash_group3 = occasionCashGroupDTO.getOcc_cash_group3();
		String occ_cash_group4 = occasionCashGroupDTO.getOcc_cash_group4();
		String occ_cash_group5 = occasionCashGroupDTO.getOcc_cash_group5();
		String occ_cash_group6 = occasionCashGroupDTO.getOcc_cash_group6();
		String occ_cash_group7 = occasionCashGroupDTO.getOcc_cash_group7();
		String occ_cash_group8 = occasionCashGroupDTO.getOcc_cash_group8();
		String occ_cash_group9 = occasionCashGroupDTO.getOcc_cash_group9();
		String occ_cash_group10 = occasionCashGroupDTO.getOcc_cash_group10();
		
		if(request.getParameter("occ_cash_group1") != null) {
			occ_cash_group1 = request.getParameter("occ_cash_group1");
		} else if(occ_cash_group1 != null){
			
		} else {
			occ_cash_group1 = "";
		}
		if(request.getParameter("occ_cash_group2") != null) {
			occ_cash_group2 = request.getParameter("occ_cash_group2");
		} else if(occ_cash_group2 != null){
			
		} else {
			occ_cash_group2 = "";
		}
		if(request.getParameter("occ_cash_group3") != null) {
			occ_cash_group3 = request.getParameter("occ_cash_group3");
		} else if(occ_cash_group3 != null){
			
		} else {
			occ_cash_group3 = "";
		}
		if(request.getParameter("occ_cash_group4") != null) {
			occ_cash_group4 = request.getParameter("occ_cash_group4");
		} else if(occ_cash_group4 != null){
			
		} else {
			occ_cash_group4 = "";
		}
		if(request.getParameter("occ_cash_group5") != null) {
			occ_cash_group5 = request.getParameter("occ_cash_group5");
		} else if(occ_cash_group5 != null){
			
		} else {
			occ_cash_group5 = "";
		}
		if(request.getParameter("occ_cash_group6") != null) {
			occ_cash_group6 = request.getParameter("occ_cash_group6");
		} else if(occ_cash_group6 != null){
			
		} else {
			occ_cash_group6 = "";
		}
		if(request.getParameter("occ_cash_group7") != null) {
			occ_cash_group7 = request.getParameter("occ_cash_group7");
		} else if(occ_cash_group7 != null){
			
		} else {
			occ_cash_group7 = "";
		}
		if(request.getParameter("occ_cash_group8") != null) {
			occ_cash_group8 = request.getParameter("occ_cash_group8");
		} else if(occ_cash_group8 != null){
			
		} else {
			occ_cash_group8 = "";
		}
		if(request.getParameter("occ_cash_group9") != null) {
			occ_cash_group9 = request.getParameter("occ_cash_group9");
		} else if(occ_cash_group9 != null){
			
		} else {
			occ_cash_group9 = "";
		}
		if(request.getParameter("occ_cash_group10") != null) {
			occ_cash_group10 = request.getParameter("occ_cash_group10");
		} else if(occ_cash_group10 != null){
			
		} else {
			occ_cash_group10 = "";
		}

		// DTO저장(부가 정보)
		occasionCashGroupDTO.setOcc_cash_group1(occ_cash_group1);
		occasionCashGroupDTO.setOcc_cash_group2(occ_cash_group2);
		occasionCashGroupDTO.setOcc_cash_group3(occ_cash_group3);
		occasionCashGroupDTO.setOcc_cash_group4(occ_cash_group4);
		occasionCashGroupDTO.setOcc_cash_group5(occ_cash_group5);
		occasionCashGroupDTO.setOcc_cash_group6(occ_cash_group6);
		occasionCashGroupDTO.setOcc_cash_group7(occ_cash_group7);
		occasionCashGroupDTO.setOcc_cash_group8(occ_cash_group8);
		occasionCashGroupDTO.setOcc_cash_group9(occ_cash_group9);
		occasionCashGroupDTO.setOcc_cash_group10(occ_cash_group10);

		int total = occasionService.occasionCashGroupUpdateJson(occasionCashGroupDTO);
		String rt = null;
		if (total > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		json.put("rt", rt);
		json.put("total", total);
		json.put("item", item);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("../app.jsp");
		return modelAndView;
	}
	
	// 그룹 상세보기(안드로이드 전용)
	@RequestMapping(value = "/occasion/occasionCashGroupViewJson.a")
	public ModelAndView occasionCashGroupViewJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int total = 0;
		OccasionCashGroupDTO occasionCashGroupDTO = occasionService.occasionCashGroupViewJson(member_num);
		if (occasionCashGroupDTO != null) {
			total = 1;
		}
		String rt = null;
		if (total > 0) {
			rt = "OK";

		} else {
			rt = "FAIL";
		}
		
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		if (total > 0) {
			JSONObject temp = new JSONObject();
			temp.put("member_num", occasionCashGroupDTO.getMember_num());
			temp.put("occ_cash_group1", occasionCashGroupDTO.getOcc_cash_group1());
			temp.put("occ_cash_group2", occasionCashGroupDTO.getOcc_cash_group2());
			temp.put("occ_cash_group3", occasionCashGroupDTO.getOcc_cash_group3());
			temp.put("occ_cash_group4", occasionCashGroupDTO.getOcc_cash_group4());
			temp.put("occ_cash_group5", occasionCashGroupDTO.getOcc_cash_group5());
			temp.put("occ_cash_group6", occasionCashGroupDTO.getOcc_cash_group6());
			temp.put("occ_cash_group7", occasionCashGroupDTO.getOcc_cash_group7());
			temp.put("occ_cash_group8", occasionCashGroupDTO.getOcc_cash_group8());
			temp.put("occ_cash_group9", occasionCashGroupDTO.getOcc_cash_group9());
			temp.put("occ_cash_group10", occasionCashGroupDTO.getOcc_cash_group10());

			// JSONArray에 저장
			item.put(temp);
		}
		json.put("rt", rt);
		json.put("total", total);
		json.put("item", item);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("../app.jsp");
		return modelAndView;
	}
	
	// 그룹 삭제하기(안드로이드 전용)
	@RequestMapping(value = "/occasion/occasionCashGroupDeleteJson.a")
	public ModelAndView occasionCashGroupDeleteJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int total = 0;
		total = occasionService.occasionCashGroupDeleteJson(member_num);
		String rt = null;
		if (total > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		json.put("rt", rt);
		json.put("total", total);
		json.put("item", item);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("../app.jsp");
		return modelAndView;
	}

}
