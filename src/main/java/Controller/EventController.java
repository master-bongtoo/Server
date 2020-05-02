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

import Model.EventDTO;
import common_content.CC;

@Controller
public class EventController {
	//String url = "http://192.168.219.100:8080/bongtoo_server/";
	String url = new CC().connectIP();

	@Autowired
	private EventService eventService;

	// 이벤트 작성(안드로이드 전용)
	@RequestMapping(value = "/event/eventWriteJson.a")
	public ModelAndView eventWriteJson(MultipartFile event_origin_img, HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int isnew = Integer.parseInt(request.getParameter("isnew"));
		String event_subject1 = request.getParameter("event_subject1");		
		String event_content = request.getParameter("event_content");
		String event_type = request.getParameter("event_type");

		// 부가 정보받아오기 (부가 정보)
		// 부가정보 객체 선언 및 초기화
		String event_subject2 = "";
		String event_date = "";
		String origin_fileName = "";
		String uuid_fileName = "";
		if(request.getParameter("event_subject2") != null) {
			event_subject2 = request.getParameter("event_subject2");
		}
		if(request.getParameter("event_date") != null) {
			event_date = request.getParameter("event_date");
		} else {
			event_date = "sysdate";
		}
		// 사진정보 체크
		if (event_origin_img != null) {
			// 사진정보 받아오기
			UUID uuid = UUID.randomUUID();
			String filePath = request.getServletContext().getRealPath("/File/eventImage");
			origin_fileName = event_origin_img.getOriginalFilename();
			uuid_fileName = uuid.toString() + origin_fileName;
			File file = new File(filePath, uuid_fileName);

			// 파일 복사
			try {
				// getInputStream() : 업로드한 파일 데이터를 읽어오는 InputStream을 구한다.
				FileCopyUtils.copy(event_origin_img.getInputStream(), new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// DTO저장(not null & primary key)
		EventDTO eventDTO = new EventDTO();
		eventDTO.setIsnew(isnew);
		eventDTO.setEvent_subject1(event_subject1);
		eventDTO.setEvent_subject2(event_subject2);
		eventDTO.setEvent_content(event_content);
		eventDTO.setEvent_date(event_date);
		eventDTO.setEvent_type(event_type);
		// DTO저장(부가 정보)
		eventDTO.setEvent_origin_img(origin_fileName);
		eventDTO.setEvent_uuid_img(uuid_fileName);
		int total = eventService.eventWriteJson(eventDTO);
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

		//웹 브라우저 체크 --0402 녕 시작
		int code = 0;
		if(request.getParameter("code")!=null) {
			code= Integer.parseInt(request.getParameter("code"));
		}
		//System.out.println("code(eventWriteJson) : "+code);

		if(code==0) {			//디폴트값이면 안드로이드 전송
			modelAndView.setViewName("../app.jsp");
		} else if(code==1) {	//1이면 웹 전송
			modelAndView.setViewName("redirect:/clientWeb/WebPage/AdminNotice1_NoticeList.jsp?event_type=1&pg=1");
		}
		//웹 브라우저 체크 --0402 녕 끝
		return modelAndView;
	}

	// 이벤트 상세보기(안드로이드 전용)
	@RequestMapping(value = "/event/eventViewJson.a")
	public ModelAndView eventViewJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int event_index = Integer.parseInt(request.getParameter("event_index"));
		int total = 0;
		EventDTO eventDTO = eventService.eventViewJson(event_index);
		if (eventDTO != null) {
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
			temp.put("event_index", eventDTO.getEvent_index());
			temp.put("isnew", eventDTO.getIsnew());
			temp.put("event_subject1", eventDTO.getEvent_subject1());
			temp.put("event_subject2", eventDTO.getEvent_subject2());
			temp.put("event_content", eventDTO.getEvent_content());
			temp.put("event_date", eventDTO.getEvent_date());
			if (eventDTO.getEvent_origin_img() != null) {
				filePath = url+"File/eventImage/";
				temp.put("event_img_path", filePath + eventDTO.getEvent_uuid_img());
				temp.put("event_origin_img ", eventDTO.getEvent_origin_img());
			} else {
				temp.put("event_img_path", "");
				temp.put("event_origin_img", "");
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
	@RequestMapping(value = "/event/eventUpdateJson.a")
	public ModelAndView eventUpdateJson(MultipartFile event_origin_img, HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/* 필수 정보받아오기 (not null & primary key) */
		// 완전 필수 정보이면서 수정할 인덱스 값!(before)
		int event_index = Integer.parseInt(request.getParameter("event_index"));
		// 필수 정보받아오기 (not null & primary key)
		int isnew = Integer.parseInt(request.getParameter("isnew"));
		String event_subject1 = request.getParameter("event_subject1");		
		String event_content = request.getParameter("event_content");
		String event_type = request.getParameter("event_type");

		// 부가 정보받아오기 (부가 정보)
		// 부가정보 객체 선언 및 초기화
		String event_subject2 = "";
		String event_date = "";
		String origin_fileName = "";
		String uuid_fileName = "";
		if(request.getParameter("event_subject2") != null) {
			event_subject2 = request.getParameter("event_subject2");
		}
		if(request.getParameter("event_date") != null) {
			event_date = request.getParameter("event_date");
		} else { 
			event_date = "sysdate";
		}
		// 사진정보 체크
		if (event_origin_img != null) {
			// 사진정보 받아오기
			UUID uuid = UUID.randomUUID();
			String filePath = request.getServletContext().getRealPath("/File/eventImage");
			origin_fileName = event_origin_img.getOriginalFilename();
			uuid_fileName = uuid.toString() + origin_fileName;
			File file = new File(filePath, uuid_fileName);

			// 파일 복사
			try {
				// getInputStream() : 업로드한 파일 데이터를 읽어오는 InputStream을 구한다.
				FileCopyUtils.copy(event_origin_img.getInputStream(), new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// DTO저장(not null & primary key)
		EventDTO eventDTO = new EventDTO();
		eventDTO.setEvent_index(event_index);
		eventDTO.setIsnew(isnew);
		eventDTO.setEvent_subject1(event_subject1);
		eventDTO.setEvent_subject2(event_subject2);
		eventDTO.setEvent_content(event_content);
		eventDTO.setEvent_date(event_date);
		eventDTO.setEvent_type(event_type);
		// DTO저장(부가 정보)
		eventDTO.setEvent_origin_img(origin_fileName);
		eventDTO.setEvent_uuid_img(uuid_fileName);
		int total = eventService.eventUpdateJson(eventDTO);
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
		//웹 브라우저 체크 --0402 녕 시작
		int code = 0;
		if(request.getParameter("code")!=null) {
			code= Integer.parseInt(request.getParameter("code"));
		}
		//System.out.println("code(eventUpdateJson) : "+code);
		if(code==0) {			//디폴트값이면 안드로이드 전송
			modelAndView.setViewName("../app.jsp");
		} else if(code==1) {	//1이면 웹 전송
			modelAndView.setViewName("redirect:/clientWeb/WebPage/AdminModifyResult.jsp");
		}
		//웹 브라우저 체크 --0402 녕 끝
		return modelAndView;
	}

	// 삭제하기(안드로이드 전용)
	@RequestMapping(value = "/event/eventDeleteJson.a")
	public ModelAndView eventDeleteJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int event_index = Integer.parseInt(request.getParameter("event_index"));
		int total = 0;
		total = eventService.eventDeleteJson(event_index);
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

		//웹 처리 추가 -- 녕0402
		int code = 0;
		code = Integer.parseInt(request.getParameter("code"));
		//System.out.println("code(eventDeleteJson) : "+code);
		if(code==0) {
			modelAndView.setViewName("../app.jsp");
		} else if(code==1) {
			modelAndView.setViewName("redirect:/clientWeb/WebPage/AdminDeleteResult.jsp");
		}

		return modelAndView;
	}

	// 리스트 (안드로이드 전용)
	@RequestMapping(value = "/event/eventListJson.a")
	public ModelAndView eventListJson(HttpServletRequest request) {
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		int endNum = pg * 10;
		int startNum = endNum - 9;
		int event_type = Integer.parseInt(request.getParameter("event_type"));
		String event_subject2;
		List<EventDTO> list;
		int totalAll = 0;
		// 1페이지당 목록
		if(request.getParameter("event_subject2") != null) {
			event_subject2 = request.getParameter("event_subject2");
			list = eventService.eventFAQListJson(event_type, event_subject2, startNum, endNum);
			totalAll = eventService.eventFAQListTotalJson(event_type, event_subject2);
		} else {
			list = eventService.eventListJson(event_type, startNum, endNum);
			totalAll = eventService.eventListTotalJson();
		}

		// JSON으로 결과값 반환
		String filePath = url+"File/eventImage/";
		String rt = null;
		int total = 0;
		//int totalAll = 0;
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
				EventDTO eventDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("event_index", eventDTO.getEvent_index());
				temp.put("isnew", eventDTO.getIsnew());
				temp.put("event_subject1", eventDTO.getEvent_subject1());
				temp.put("event_type", eventDTO.getEvent_type());
				if (eventDTO.getEvent_subject2() != null) {
					temp.put("event_subject2", eventDTO.getEvent_subject2());
				} else {
					temp.put("event_subject2", "");
				}
				if (eventDTO.getEvent_date() != null) {
					temp.put("event_date", eventDTO.getEvent_date());
				} else {
					temp.put("event_date", "sysdate");
				}
				temp.put("event_content", eventDTO.getEvent_content());

				if (eventDTO.getEvent_origin_img() != null) {
					filePath = url+"File/eventImage/";
					temp.put("event_img_path", filePath + eventDTO.getEvent_uuid_img());
					temp.put("event_origin_img", eventDTO.getEvent_origin_img());
				} else {
					temp.put("event_img_path", "");
					temp.put("event_origin_img", "");
				}

				// JSONArray에 저장
				item.put(i, temp);
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

	// FAQ 검색 리스트 (안드로이드 전용)
	@RequestMapping(value = "/event/eventSearchListJson.a")
	public ModelAndView eventSearchListJson(HttpServletRequest request) {
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		int endNum = pg * 10;
		int startNum = endNum - 9;


		String search_word = "%"+request.getParameter("search_word")+"%";

		List<EventDTO> list = eventService.eventSearchListJson(search_word, startNum, endNum);
		int totalAll = eventService.eventSearchListTotalJson(search_word);


		// JSON으로 결과값 반환
		String filePath = url+"File/eventImage/";
		String rt = null;
		int total = 0;
		//int totalAll = 0;
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
				EventDTO eventDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("event_index", eventDTO.getEvent_index());
				temp.put("isnew", eventDTO.getIsnew());
				temp.put("event_subject1", eventDTO.getEvent_subject1());
				temp.put("event_type", eventDTO.getEvent_type());
				if (eventDTO.getEvent_subject2() != null) {
					temp.put("event_subject2", eventDTO.getEvent_subject2());
				} else {
					temp.put("event_subject2", "");
				}
				if (eventDTO.getEvent_date() != null) {
					temp.put("event_date", eventDTO.getEvent_date());
				} else {
					temp.put("event_date", "sysdate");
				}
				temp.put("event_content", eventDTO.getEvent_content());

				if (eventDTO.getEvent_origin_img() != null) {
					filePath = url+"File/eventImage/";
					temp.put("event_img_path", filePath + eventDTO.getEvent_uuid_img());
					temp.put("event_origin_img", eventDTO.getEvent_origin_img());
				} else {
					temp.put("event_img_path", "");
					temp.put("event_origin_img", "");
				}

				// JSONArray에 저장
				item.put(i, temp);
			}
		}

		json.put("rt", rt);
		json.put("total", total);
		json.put("item", item);
		json.put("totalAll", totalAll);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("../app.jsp");
		return modelAndView;
	}
}
