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

import Model.QuestionDTO;
import common_content.CC;

@Controller
public class QuestionController {
	//String url = "http://192.168.219.100:8080/bongtoo_server/";
	String url = new CC().connectIP();
	
	@Autowired
	QuestionService questionService;

	// question 작성(안드로이드 전용)
	@RequestMapping(value = "/question/questionWriteJson.a")
	public ModelAndView questionWriteJson(MultipartFile question_origin_img, HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int question_type = Integer.parseInt(request.getParameter("question_type"));
		String question_subject = request.getParameter("question_subject");
		String question_content = request.getParameter("question_content");

		// 부가 정보받아오기 (부가 정보)
		// 부가정보 객체 선언 및 초기화
		String question_phone = "";
		String question_email = "";
		String origin_fileName = "";
		String uuid_fileName = "";
		// 사진정보 체크
		if (question_origin_img != null) {
			// 사진정보 받아오기
			UUID uuid = UUID.randomUUID();
			String filePath = request.getServletContext().getRealPath("/File/eventImage");
			origin_fileName = question_origin_img.getOriginalFilename();
			uuid_fileName = uuid.toString() + origin_fileName;
			File file = new File(filePath, uuid_fileName);

			// 파일 복사
			try {
				// getInputStream() : 업로드한 파일 데이터를 읽어오는 InputStream을 구한다.
				FileCopyUtils.copy(question_origin_img.getInputStream(), new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(request.getParameter("question_phone") != null) {
			question_phone = request.getParameter("question_phone");
		}
		if(request.getParameter("question_email") != null) {
			question_email = request.getParameter("question_email");
		}

		// DTO저장(not null & primary key)
		QuestionDTO questionDTO = new QuestionDTO();
		questionDTO.setMember_num(member_num);
		questionDTO.setQuestion_subject(question_subject);
		questionDTO.setQuestion_content(question_content);
		questionDTO.setQuestion_email(question_email);
		questionDTO.setQuestion_type(question_type);
		// 디폴트값 0(답변대기)
		questionDTO.setQuestion_check(0);
		// DTO저장(부가 정보)
		questionDTO.setQuestion_phone(question_phone);
		questionDTO.setQuestion_origin_img(origin_fileName);
		questionDTO.setQuestion_uuid_img(uuid_fileName);
		int total = questionService.questionWriteJson(questionDTO);
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

	// question 상세보기(안드로이드 전용)
	@RequestMapping(value = "/question/questionViewJson.a")
	public ModelAndView questionViewJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int question_index = Integer.parseInt(request.getParameter("question_index"));
		int total = 0;
		QuestionDTO questionDTO = questionService.questionViewJson(question_index);
		if (questionDTO != null) {
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
			temp.put("member_num", questionDTO.getMember_num());
			temp.put("question_index", questionDTO.getQuestion_index());
			temp.put("question_type", questionDTO.getQuestion_type());
			temp.put("question_subject", questionDTO.getQuestion_subject());
			temp.put("question_content", questionDTO.getQuestion_content());
			temp.put("question_email", questionDTO.getQuestion_email());
			temp.put("question_check", questionDTO.getQuestion_check());
			if (questionDTO.getQuestion_origin_img() != null) {
				filePath = url+"File/questionImage/";
				temp.put("question_img_path", filePath + questionDTO.getQuestion_uuid_img());
				temp.put("question_origin_img ", questionDTO.getQuestion_origin_img());
			} else {
				temp.put("question_img_path", "");
				temp.put("question_origin_img", "");
			}
			if(questionDTO.getQuestion_phone() != null) {
				temp.put("question_phone", questionDTO.getQuestion_phone());
			} else {
				temp.put("question_phone", "");
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

	// question 수정(안드로이드 전용)
	@RequestMapping(value = "/question/questionUpdateJson.a")
	public ModelAndView eventUpdateJson(MultipartFile question_origin_img, HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/* 필수 정보받아오기 (not null & primary key) */
		// 수정될 인덱스 값(after)
		int question_index = Integer.parseInt(request.getParameter("question_index"));
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		// 수정할 인덱스 값(before)

		QuestionDTO questionDTO = questionService.questionViewJson(question_index);
		int question_type = questionDTO.getQuestion_type();
		String question_subject = questionDTO.getQuestion_subject();
		String question_content = questionDTO.getQuestion_content();
		String question_email;
		String question_phone;
		if(request.getParameter("question_type")!=null) {
			question_type = Integer.parseInt(request.getParameter("question_type"));
		}
		if(request.getParameter("question_subject")!=null) {
			question_subject = request.getParameter("question_subject");
		}
		if(request.getParameter("question_content")!=null) {
			question_content = request.getParameter("question_content");
		}
		
		if(request.getParameter("question_email")!=null) {
			question_email = request.getParameter("question_email");
		} else if (questionDTO.getQuestion_email() != null) {
			question_email = questionDTO.getQuestion_email();
		} else {
			question_email = "";
		}
		if(request.getParameter("question_phone")!=null) {
			question_phone = request.getParameter("question_phone");
		} else if (questionDTO.getQuestion_phone() != null) {
			question_phone = questionDTO.getQuestion_phone();
		} else {
			question_phone = "";
		}

		// 부가 정보받아오기 (부가 정보)
		// 부가정보 객체 선언 및 초기화
		String origin_fileName = "";
		String uuid_fileName = "";
		// 사진정보 체크
		if (question_origin_img != null) {
			// 사진정보 받아오기
			UUID uuid = UUID.randomUUID();
			String filePath = request.getServletContext().getRealPath("/File/eventImage");
			origin_fileName = question_origin_img.getOriginalFilename();
			uuid_fileName = uuid.toString() + origin_fileName;
			File file = new File(filePath, uuid_fileName);

			// 파일 복사
			try {
				// getInputStream() : 업로드한 파일 데이터를 읽어오는 InputStream을 구한다.
				FileCopyUtils.copy(question_origin_img.getInputStream(), new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// DTO저장(not null & primary key)
		questionDTO.setQuestion_index(question_index);
		questionDTO.setMember_num(member_num);
		questionDTO.setQuestion_type(question_type);
		questionDTO.setQuestion_subject(question_subject);
		questionDTO.setQuestion_content(question_content);
		questionDTO.setQuestion_email(question_email);
		// DTO저장(부가 정보)
		questionDTO.setQuestion_phone(question_phone);
		questionDTO.setQuestion_origin_img(origin_fileName);
		questionDTO.setQuestion_uuid_img(uuid_fileName);
		int total = questionService.questionUpdateJson(questionDTO);
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

	// question 삭제하기(안드로이드 전용)
	@RequestMapping(value = "/question/questionDeleteJson.a")
	public ModelAndView questionDeleteJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int question_index = Integer.parseInt(request.getParameter("question_index"));
		int total = 0;
		total = questionService.questionDeleteJson(question_index);
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

	// question 리스트 (안드로이드 전용)
	@RequestMapping(value = "/question/questionListJson.a")
	public ModelAndView questionListJson(HttpServletRequest request) {
		// 1. 데이터 받아오기
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		int endNum = pg * 10;
		int startNum = endNum - 9;
		int question_type = Integer.parseInt(request.getParameter("question_type"));
		
		// 2. DB
		List<QuestionDTO> list;
		list = questionService.questionListJson(question_type, startNum, endNum);
		int totalAll = questionService.questionListTotalJson(question_type);
		// JSON으로 결과값 반환
		String filePath = url+"File/questionImage/";
		String rt = null;
		int total = 0;
		// int totalAll = 0;
		total = list.size();
		if (total > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		if (total > 0) {
			for (int i = 0; i < total; i++) {
				QuestionDTO questionDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("member_num", questionDTO.getMember_num());
				temp.put("question_index", questionDTO.getQuestion_index());
				temp.put("question_type", questionDTO.getQuestion_type());
				temp.put("question_subject", questionDTO.getQuestion_subject());
				temp.put("question_content", questionDTO.getQuestion_content());
				temp.put("question_email", questionDTO.getQuestion_email());
				temp.put("question_check", questionDTO.getQuestion_check());
				if (questionDTO.getQuestion_origin_img() != null) {
					filePath = url+"File/questionImage/";
					temp.put("question_img_path", filePath + questionDTO.getQuestion_uuid_img());
					temp.put("question_origin_img ", questionDTO.getQuestion_origin_img());
				} else {
					temp.put("question_img_path", "");
					temp.put("question_origin_img", "");
				}
				if(questionDTO.getQuestion_phone() != null) {
					temp.put("question_phone", questionDTO.getQuestion_phone());
				} else {
					temp.put("question_phone", "");
				}

				// JSONArray에 저장
				item.put(temp);
			}
		}

		json.put("rt", rt);
		json.put("total", total);
		json.put("totalAll", totalAll);
		json.put("item", item);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("../app.jsp");
		return modelAndView;
	}
}
