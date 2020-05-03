package Controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import Model.AlimiDTO;
import Model.AlimiTagDTO;

@Controller
public class AlimiController {

	@Autowired
	AlimiService alimiService;

	// alimi 작성
	@RequestMapping(value = "/alimi/alimiWriteJson.a")
	public ModelAndView alimiWriteJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int alimi_type = Integer.parseInt(request.getParameter("alimi_type"));
		String alimi_tag = request.getParameter("alimi_tag");

		// 부가 정보받아오기 (부가 정보)
		// 부가정보 객체 선언 및 초기화
		String alimi_place = "";
		String alimi_content = "";
		String alimi_date = "";
		String alimi_who = "";

		if (request.getParameter("alimi_place") != null) {
			alimi_place = request.getParameter("alimi_place");
		}
		if (request.getParameter("alimi_content") != null) {
			alimi_content = request.getParameter("alimi_content");
		}
		if (request.getParameter("alimi_date") != null) {
			alimi_date = request.getParameter("alimi_date");
		}
		if (request.getParameter("alimi_who") != null) {
			alimi_who = request.getParameter("alimi_who");
		}
		// DTO저장(not null & primary key)
		AlimiDTO alimiDTO = new AlimiDTO();

		alimiDTO.setAlimi_tag(alimi_tag);
		alimiDTO.setMember_num(member_num);
		alimiDTO.setAlimi_type(alimi_type);
		// DTO저장(부가 정보)
		alimiDTO.setAlimi_place(alimi_place);
		alimiDTO.setAlimi_content(alimi_content);
		alimiDTO.setAlimi_date(alimi_date);
		alimiDTO.setAlimi_who(alimi_who);
		int total = alimiService.alimiWriteJson(alimiDTO);
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

	// alimi 수정
	@RequestMapping(value = "/alimi/alimiUpdateJson.a")
	public ModelAndView alimiUpdateJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int alimi_index = Integer.parseInt(request.getParameter("alimi_index"));

		AlimiDTO alimiDTO = alimiService.alimiViewJson(alimi_index);
		// 기존 정보받아오기 (부가 정보)
		// 기존 정보 객체 선언 및 초기화
		int alimi_type = alimiDTO.getAlimi_type();
		String alimi_tag = alimiDTO.getAlimi_tag();
		String alimi_place = alimiDTO.getAlimi_place();
		String alimi_content = alimiDTO.getAlimi_content();
		String alimi_date = alimiDTO.getAlimi_date();
		String alimi_who = alimiDTO.getAlimi_who();
		if (request.getParameter("alimi_type") != null) {
			alimi_type = Integer.parseInt(request.getParameter("alimi_type"));
		}
		if (request.getParameter("alimi_tag") != null) {
			alimi_tag = request.getParameter("alimi_tag");
		}
		if (request.getParameter("alimi_place") != null) {
			alimi_place = request.getParameter("alimi_place");
		}
		if (request.getParameter("alimi_content") != null) {
			alimi_content = request.getParameter("alimi_content");
		}
		if (request.getParameter("alimi_date") != null) {
			alimi_date = request.getParameter("alimi_date");
		}
		if (request.getParameter("alimi_who") != null) {
			alimi_who = request.getParameter("alimi_who");
		}
		// DTO저장(not null & primary key)
		alimiDTO.setAlimi_tag(alimi_tag);
		alimiDTO.setMember_num(member_num);
		alimiDTO.setAlimi_type(alimi_type);
		// DTO저장(부가 정보)
		alimiDTO.setAlimi_place(alimi_place);
		alimiDTO.setAlimi_content(alimi_content);
		alimiDTO.setAlimi_date(alimi_date);
		alimiDTO.setAlimi_who(alimi_who);
		int total = alimiService.alimiUpdateJson(alimiDTO);
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

	// alimi 리스트 (안드로이드 전용)
	@RequestMapping(value = "/alimi/alimiListJson.a")
	public ModelAndView alimiListJson(HttpServletRequest request) {
		String alimi_tag = "";
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		int endNum = pg * 10;
		int startNum = endNum - 9;
		// 1페이지당 목록 10개
		if(request.getParameter("alimi_tag") != null) {
			alimi_tag = request.getParameter("alimi_tag");
		} else {
			alimi_tag = "";
		}
		List<AlimiDTO> list = alimiService.alimiListJson(alimi_tag, startNum, endNum);
		
		// JSON으로 결과값 반환
		String rt = null;
		int total = 0;
		int totalAll = 0;
		total = list.size();
		totalAll = alimiService.alimiListTotalJson(alimi_tag);
		if (total > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		if (total > 0) {
			for (int i = 0; i < total; i++) {
				AlimiDTO alimiDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("alimi_index", alimiDTO.getAlimi_index());
				temp.put("alimi_place", alimiDTO.getAlimi_place());
				temp.put("alimi_content", alimiDTO.getAlimi_content());
				temp.put("alimi_date", alimiDTO.getAlimi_date());
				temp.put("alimi_tag", alimiDTO.getAlimi_tag());
				temp.put("member_num", alimiDTO.getMember_num());
				temp.put("alimi_who", alimiDTO.getAlimi_who());
				temp.put("alimi_type", alimiDTO.getAlimi_type());

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

//	
//	// alimi 리스트 (안드로이드 전용)
//	@RequestMapping(value = "/alimi/alimiListJson.a")
//	public ModelAndView alimiListJson(HttpServletRequest request) {
//		
//		int member_num = Integer.parseInt(request.getParameter("member_num"));
//		int pg = 1;
//		if (request.getParameter("pg") != null) {
//			pg = Integer.parseInt(request.getParameter("pg"));
//		}
//		int endNum = pg * 10;
//		int startNum = endNum - 9;
//		// 1페이지당 목록 10개
//		List<AlimiDTO> list = alimiService.alimiListJson(member_num, startNum, endNum);
//
//		// JSON으로 결과값 반환
//		String rt = null;
//		int total = 0;
//		int totalAll = 0;
//		total = list.size();
//		totalAll = alimiService.alimiListTotalJson(member_num);
//		if (total > 0) {
//			rt = "OK";
//		} else {
//			rt = "FAIL";
//		}
//		JSONObject json = new JSONObject();
//		JSONArray item = new JSONArray();
//		if (total > 0) {
//			for (int i = 0; i < total; i++) {
//				AlimiDTO alimiDTO = list.get(i);
//				JSONObject temp = new JSONObject();
//				temp.put("alimi_index", alimiDTO.getAlimi_index());
//				temp.put("alimi_place", alimiDTO.getAlimi_place());
//				temp.put("alimi_content", alimiDTO.getAlimi_content());
//				temp.put("alimi_date", alimiDTO.getAlimi_date());
//				temp.put("alimi_tag", alimiDTO.getAlimi_tag());
//				temp.put("member_num", alimiDTO.getMember_num());
//				temp.put("alimi_who", alimiDTO.getAlimi_who());
//				temp.put("alimi_type", alimiDTO.getAlimi_type());
//
//				// JSONArray에 저장
//				item.put(i, temp);
//			}
//		}
//
//		json.put("rt", rt);
//		json.put("total", total);
//		json.put("totalAll", totalAll);
//		json.put("item", item);
//
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject("json", json);
//		modelAndView.setViewName("../app.jsp");
//		return modelAndView;
//	}

	// alimi 상세보기(안드로이드 전용)
	@RequestMapping(value = "/alimi/alimiViewJson.a")
	public ModelAndView alimiViewJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int alimi_index = Integer.parseInt(request.getParameter("alimi_index"));
		int total = 0;
		AlimiDTO alimiDTO = alimiService.alimiViewJson(alimi_index);
		if (alimiDTO != null) {
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
			temp.put("alimi_index", alimiDTO.getAlimi_index());
			temp.put("alimi_place", alimiDTO.getAlimi_place());
			temp.put("alimi_content", alimiDTO.getAlimi_content());
			temp.put("alimi_date", alimiDTO.getAlimi_date());
			temp.put("alimi_tag", alimiDTO.getAlimi_tag());
			temp.put("member_num", alimiDTO.getMember_num());
			temp.put("alimi_who", alimiDTO.getAlimi_who());
			temp.put("alimi_type", alimiDTO.getAlimi_type());

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

	// alimi 삭제하기(안드로이드 전용)
	@RequestMapping(value = "/alimi/alimiDeleteJson.a")
	public ModelAndView alimiDeleteJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int alimi_index = Integer.parseInt(request.getParameter("alimi_index"));
		int total = 0;
		AlimiDTO alimiDTO = new AlimiDTO();
		alimiDTO.setAlimi_index(alimi_index);
		alimiDTO.setMember_num(member_num);
		total = alimiService.alimiDeleteJson(alimiDTO);
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

	// alimitag 작성
	@RequestMapping(value = "/alimi/alimiTagWriteJson.a")
	public ModelAndView alimiTagWriteJson(HttpServletRequest request) {
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
		String alimi_tag1 = "";
		String alimi_tag2 = "";
		String alimi_tag3 = "";
		String alimi_tag4 = "";
		String alimi_tag5 = "";

		if (request.getParameter("alimi_tag1") != null) {
			alimi_tag1 = request.getParameter("alimi_tag1");
		}
		if (request.getParameter("alimi_tag2") != null) {
			alimi_tag2 = request.getParameter("alimi_tag2");
		}
		if (request.getParameter("alimi_tag3") != null) {
			alimi_tag3 = request.getParameter("alimi_tag3");
		}
		if (request.getParameter("alimi_tag4") != null) {
			alimi_tag4 = request.getParameter("alimi_tag4");
		}
		if (request.getParameter("alimi_tag5") != null) {
			alimi_tag5 = request.getParameter("alimi_tag5");
		}

		// DTO저장(not null & primary key)
		AlimiTagDTO alimiTagDTO = new AlimiTagDTO();

		alimiTagDTO.setMember_num(member_num);
		// DTO저장(부가 정보)
		alimiTagDTO.setAlimi_tag1(alimi_tag1);
		alimiTagDTO.setAlimi_tag2(alimi_tag2);
		alimiTagDTO.setAlimi_tag3(alimi_tag3);
		alimiTagDTO.setAlimi_tag4(alimi_tag4);
		alimiTagDTO.setAlimi_tag5(alimi_tag5);

		int total = alimiService.alimiTagWriteJson(alimiTagDTO);
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

	// alimitag 수정
	@RequestMapping(value = "/alimi/alimiTagUpdateJson.a")
	public ModelAndView alimiTagUpdateJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));

		AlimiTagDTO alimiTagDTO = alimiService.alimiTagViewJson(member_num);
		// 기존 정보받아오기 (부가 정보)
		// 기존 정보 객체 선언 및 초기화
		int alimi_type = alimiTagDTO.getMember_num();
		String alimi_tag1 = alimiTagDTO.getAlimi_tag1();
		String alimi_tag2 = alimiTagDTO.getAlimi_tag2();
		String alimi_tag3 = alimiTagDTO.getAlimi_tag3();
		String alimi_tag4 = alimiTagDTO.getAlimi_tag4();
		String alimi_tag5 = alimiTagDTO.getAlimi_tag5();

		if (request.getParameter("alimi_tag1") != null) {
			alimi_tag1 = request.getParameter("alimi_tag1");
		}
		if (request.getParameter("alimi_tag1") != null) {
			alimi_tag1 = request.getParameter("alimi_tag1");
		}
		if (request.getParameter("alimi_tag3") != null) {
			alimi_tag3 = request.getParameter("alimi_tag3");
		}
		if (request.getParameter("alimi_tag4") != null) {
			alimi_tag4 = request.getParameter("alimi_tag4");
		}
		if (request.getParameter("alimi_tag5") != null) {
			alimi_tag5 = request.getParameter("alimi_tag5");
		}
		// DTO저장(not null & primary key)
		alimiTagDTO.setMember_num(member_num);
		// 부가정보저장
		alimiTagDTO.setAlimi_tag1(alimi_tag1);
		alimiTagDTO.setAlimi_tag2(alimi_tag2);
		alimiTagDTO.setAlimi_tag3(alimi_tag3);
		alimiTagDTO.setAlimi_tag4(alimi_tag4);
		alimiTagDTO.setAlimi_tag5(alimi_tag5);
		int total = alimiService.alimiTagUpdateJson(alimiTagDTO);
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

	// alimitag 상세보기(안드로이드 전용)
	@RequestMapping(value = "/alimi/alimiTagViewJson.a")
	public ModelAndView alimiTagViewJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int total = 0;
		int count = 0;
		AlimiTagDTO alimiTagDTO = alimiService.alimiTagViewJson(member_num);
		if (alimiTagDTO != null) {
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
			temp.put("member_num", alimiTagDTO.getMember_num());
			temp.put("alimi_tag1", alimiTagDTO.getAlimi_tag1());
			temp.put("alimi_tag2", alimiTagDTO.getAlimi_tag2());
			temp.put("alimi_tag3", alimiTagDTO.getAlimi_tag3());
			temp.put("alimi_tag4", alimiTagDTO.getAlimi_tag4());
			temp.put("alimi_tag5", alimiTagDTO.getAlimi_tag5());
			if(alimiTagDTO.getAlimi_tag1() != null) {
				count ++;
			}
			if(alimiTagDTO.getAlimi_tag2() != null) {
				count ++;
			}
			if(alimiTagDTO.getAlimi_tag3() != null) {
				count ++;
			}
			if(alimiTagDTO.getAlimi_tag4() != null) {
				count ++;
			}
			if(alimiTagDTO.getAlimi_tag5() != null) {
				count ++;
			}
			// JSONArray에 저장
			item.put(temp);
		}
		
		json.put("rt", rt);
		json.put("total", total);
		json.put("item", item);
		json.put("count", count);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("../app.jsp");
		return modelAndView;
	}

	// alimitag 삭제하기(안드로이드 전용)
	@RequestMapping(value = "/alimi/alimiTagDeleteJson.a")
	public ModelAndView alimiTagDeleteJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int total = 0;
		AlimiDTO alimiDTO = new AlimiDTO();
		alimiDTO.setMember_num(member_num);
		total = alimiService.alimiTagDeleteJson(member_num);
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
