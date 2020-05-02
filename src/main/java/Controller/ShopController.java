package Controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import Model.ShopDTO;
import Model.ShopevDTO;
import common_content.CC;

@Controller
public class ShopController {
	//String url = "http://192.168.219.100:8080/bongtoo_server/";
	String url = new CC().connectIP();

	@Autowired
	ShopService service;

	// 샵 작성(안드로이드 전용)
	@RequestMapping(value = "/shop/boardWriteJson.a")
	public ModelAndView boardWriteJson(MultipartFile shop_origin_img, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("../app.jsp");
		return modelAndView;
	}

	// 샵 전체 리스트 (안드로이드 전용)
	@RequestMapping(value = "/shop/shopListJson.a")
	public ModelAndView shopListJson(HttpServletRequest request) {
		// 1. 데이터
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2. DB
		// 1페이지당 목록 5개
		int endNum = pg * 10;
		int startNum = endNum - 9;
		List<ShopDTO> list = service.shopListdescJson(startNum, endNum);

		// JSON으로 결과값 반환
		String filePath = url + "File/shopImage/";
		String rt = null;
		int total = 0;
		int totalAll = 0;
		double shop_total_score = 0;
		int shop_total_count = 0;
		String shop_grade = "";
		total = list.size();
		if (total > 0) {
			rt = "OK";
			totalAll = service.shopListTotalJson();
		} else {
			rt = "FAIL";
		}
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		if (total > 0) {
			for (int i = 0; i < total; i++) {
				ShopDTO shopDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("shop_index", shopDTO.getShop_index());
				temp.put("shop_name", shopDTO.getShop_name());
				temp.put("shop_type", shopDTO.getShop_type());
				temp.put("shop_addr", shopDTO.getShop_addr());

				shop_total_score = shopDTO.getShop_total_score();
				shop_total_count = shopDTO.getShop_total_count();
				shop_grade = String.valueOf(Math.round((shop_total_score / shop_total_count) * 10) / 10.0);
				temp.put("shop_grade", shop_grade);

				temp.put("shop_detail", shopDTO.getShop_detail());
				if (shopDTO.getShop_phone() != null) {
					temp.put("shop_phone", shopDTO.getShop_phone());
				} else {
					temp.put("shop_phone", "");
				}
				if (shopDTO.getShop_url() != null) {
					temp.put("shop_url", shopDTO.getShop_url());
				} else {
					temp.put("shop_url", "");
				}
				if (shopDTO.getShop_email() != null) {
					temp.put("shop_email", shopDTO.getShop_email());
				} else {
					temp.put("shop_email", "");
				}

				if (shopDTO.getShop_origin_img() != null) {
					temp.put("shop_img_path", filePath + shopDTO.getShop_uuid_img());
					temp.put("shop_origin_img", shopDTO.getShop_origin_img());
				} else {
					temp.put("shop_img_path", "");
					temp.put("shop_origin_img", "");
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
	
	// 샵 전체 검색 리스트 (안드로이드 전용)
	@RequestMapping(value = "/shop/shopListSearchJson.a")
	public ModelAndView shopListSearchJson(HttpServletRequest request) {
		// 1. 데이터
		String search_word = "%"+request.getParameter("search_word")+"%";
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2. DB
		// 1페이지당 목록 5개
		int endNum = pg * 10;
		int startNum = endNum - 9;
		List<ShopDTO> list = service.shopListSearchdescJson(startNum, endNum, search_word);
		
//		if(request.getParameter("up_down") != null) {
//			list = service.shopListSearchdescJson(startNum, endNum, search_word);
//		} else {
//			list = service.shopListSearchascJson(startNum, endNum, search_word);
//		}
		
		// JSON으로 결과값 반환
		String filePath = url + "File/shopImage/";
		String rt = null;
		int total = 0;
		int totalAll = 0;
		double shop_total_score = 0;
		int shop_total_count = 0;
		String shop_grade = "";
		total = list.size();
		if (total > 0) {
			rt = "OK";
			totalAll = service.shopListSearchTotalJson(search_word);
		} else {
			rt = "FAIL";
		}
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		if (total > 0) {
			for (int i = 0; i < total; i++) {
				ShopDTO shopDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("shop_index", shopDTO.getShop_index());
				temp.put("shop_name", shopDTO.getShop_name());
				temp.put("shop_type", shopDTO.getShop_type());

				
				
				shop_total_score = shopDTO.getShop_total_score();
				shop_total_count = shopDTO.getShop_total_count();
				shop_grade = String.valueOf(Math.round((shop_total_score / shop_total_count) * 10) / 10.0);
				temp.put("shop_grade", shop_grade);

				temp.put("shop_detail", shopDTO.getShop_detail());
				if (shopDTO.getShop_phone() != null) {
					temp.put("shop_phone", shopDTO.getShop_phone());
				} else {
					temp.put("shop_phone", "");
				}
				if (shopDTO.getShop_url() != null) {
					temp.put("shop_url", shopDTO.getShop_url());
				} else {
					temp.put("shop_url", "");
				}
				if (shopDTO.getShop_email() != null) {
					temp.put("shop_email", shopDTO.getShop_email());
				} else {
					temp.put("shop_email", "");
				}
				if (shopDTO.getShop_addr() != null) {
					temp.put("shop_addr", shopDTO.getShop_addr());
				} else {
					temp.put("shop_addr", "");
				}

				if (shopDTO.getShop_origin_img() != null) {
					temp.put("shop_img_path", filePath + shopDTO.getShop_uuid_img());
					temp.put("shop_origin_img", shopDTO.getShop_origin_img());
				} else {
					temp.put("shop_img_path", "");
					temp.put("shop_origin_img", "");
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

	// 샵 타입 전체 리스트 (안드로이드 전용)
	@RequestMapping(value = "/shop/shopListTypeJson.a")
	public ModelAndView shopListTypeJson(HttpServletRequest request) {
		// 1. 데이터
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		String shop_type = request.getParameter("shop_type");
		// 2. DB
		// 1페이지당 목록 5개
		int endNum = pg * 10;
		int startNum = endNum - 9;
		List<ShopDTO> list = service.shopListTypedescJson(startNum, endNum, shop_type);

		// JSON으로 결과값 반환
		String filePath = url + "File/shopImage/";
		String rt = null;
		int total = 0;
		int totalAll = 0;
		double shop_total_score = 0;
		int shop_total_count = 0;
		String shop_grade = "";
		total = list.size();
		if (total > 0) {
			rt = "OK";
			totalAll = service.shopListTypeTotalJson(shop_type);
		} else {
			rt = "FAIL";
		}
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		if (total > 0) {
			for (int i = 0; i < total; i++) {
				ShopDTO shopDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("shop_index", shopDTO.getShop_index());
				temp.put("shop_name", shopDTO.getShop_name());
				temp.put("shop_type", shopDTO.getShop_type());

				shop_total_score = shopDTO.getShop_total_score();
				shop_total_count = shopDTO.getShop_total_count();
				shop_grade = String.valueOf(Math.round((shop_total_score / shop_total_count) * 10) / 10.0);
				temp.put("shop_grade", shop_grade);

				temp.put("shop_detail", shopDTO.getShop_detail());
				if (shopDTO.getShop_phone() != null) {
					temp.put("shop_phone", shopDTO.getShop_phone());
				} else {
					temp.put("shop_phone", "");
				}
				if (shopDTO.getShop_url() != null) {
					temp.put("shop_url", shopDTO.getShop_url());
				} else {
					temp.put("shop_url", "");
				}
				if (shopDTO.getShop_email() != null) {
					temp.put("shop_email", shopDTO.getShop_email());
				} else {
					temp.put("shop_email", "");
				}
				if (shopDTO.getShop_addr() != null) {
					temp.put("shop_addr", shopDTO.getShop_addr());
				} else {
					temp.put("shop_addr", "");
				}

				if (shopDTO.getShop_origin_img() != null) {
					temp.put("shop_img_path", filePath + shopDTO.getShop_uuid_img());
					temp.put("shop_origin_img", shopDTO.getShop_origin_img());
				} else {
					temp.put("shop_img_path", "");
					temp.put("shop_origin_img", "");
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

	// 샵 스코어 전체 리스트 (안드로이드 전용)
	@RequestMapping(value = "/shop/shopListScoreJson.a")
	public ModelAndView shopListScoreJson(HttpServletRequest request) {
		// 1. 데이터
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2. DB
		// 1페이지당 목록 5개
		int endNum = pg * 10;
		int startNum = endNum - 9;
		List<ShopDTO> list = service.shopListScoreJson(startNum, endNum);

		// JSON으로 결과값 반환
		String filePath = url + "File/shopImage/";
		String rt = null;
		int total = 0;
		int totalAll = 0;
		double shop_total_score = 0;
		int shop_total_count = 0;
		String shop_grade = "";
		total = list.size();
		if (total > 0) {
			rt = "OK";
			totalAll = service.shopListTotalJson();
		} else {
			rt = "FAIL";
		}
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		if (total > 0) {
			for (int i = 0; i < total; i++) {
				ShopDTO shopDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("shop_index", shopDTO.getShop_index());
				temp.put("shop_name", shopDTO.getShop_name());
				temp.put("shop_type", shopDTO.getShop_type());

				shop_total_score = shopDTO.getShop_total_score();
				shop_total_count = shopDTO.getShop_total_count();
				shop_grade = String.valueOf(Math.round((shop_total_score / shop_total_count) * 10) / 10.0);
				temp.put("shop_grade", shop_grade);

				temp.put("shop_detail", shopDTO.getShop_detail());
				if (shopDTO.getShop_phone() != null) {
					temp.put("shop_phone", shopDTO.getShop_phone());
				} else {
					temp.put("shop_phone", "");
				}
				if (shopDTO.getShop_url() != null) {
					temp.put("shop_url", shopDTO.getShop_url());
				} else {
					temp.put("shop_url", "");
				}
				if (shopDTO.getShop_email() != null) {
					temp.put("shop_email", shopDTO.getShop_email());
				} else {
					temp.put("shop_email", "");
				}
				if (shopDTO.getShop_addr() != null) {
					temp.put("shop_addr", shopDTO.getShop_addr());
				} else {
					temp.put("shop_addr", "");
				}

				if (shopDTO.getShop_origin_img() != null) {
					temp.put("shop_img_path", filePath + shopDTO.getShop_uuid_img());
					temp.put("shop_origin_img", shopDTO.getShop_origin_img());
				} else {
					temp.put("shop_img_path", "");
					temp.put("shop_origin_img", "");
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

	// 샵 타입 전체 리스트 (안드로이드 전용)
	@RequestMapping(value = "/shop/shopListTypeScoreJson.a")
	public ModelAndView shopListTypeScoreJson(HttpServletRequest request) {
		// 1. 데이터
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		String shop_type = request.getParameter("shop_type");
		//System.out.println("shop_type="+shop_type);
		// 2. DB
		// 1페이지당 목록 5개
		int endNum = pg * 10;
		int startNum = endNum - 9;
		List<ShopDTO> list = service.shopListTypeScoreJson(startNum, endNum, shop_type);

		// JSON으로 결과값 반환
		String filePath = url + "File/shopImage/";
		String rt = null;
		int total = 0;
		int totalAll = 0;
		double shop_total_score = 0;
		int shop_total_count = 0;
		String shop_grade = "";
		total = list.size();
		if (total > 0) {
			rt = "OK";
			totalAll = service.shopListTypeTotalJson(shop_type);
		} else {
			rt = "FAIL";
		}
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		if (total > 0) {
			for (int i = 0; i < total; i++) {
				ShopDTO shopDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("shop_index", shopDTO.getShop_index());
				temp.put("shop_name", shopDTO.getShop_name());
				temp.put("shop_type", shopDTO.getShop_type());

				shop_total_score = shopDTO.getShop_total_score();
				shop_total_count = shopDTO.getShop_total_count();
				shop_grade = String.valueOf(Math.round((shop_total_score / shop_total_count) * 10) / 10.0);
				temp.put("shop_grade", shop_grade);

				temp.put("shop_detail", shopDTO.getShop_detail());
				if (shopDTO.getShop_phone() != null) {
					temp.put("shop_phone", shopDTO.getShop_phone());
				} else {
					temp.put("shop_phone", "");
				}
				if (shopDTO.getShop_url() != null) {
					temp.put("shop_url", shopDTO.getShop_url());
				} else {
					temp.put("shop_url", "");
				}
				if (shopDTO.getShop_email() != null) {
					temp.put("shop_email", shopDTO.getShop_email());
				} else {
					temp.put("shop_email", "");
				}
				if (shopDTO.getShop_addr() != null) {
					temp.put("shop_addr", shopDTO.getShop_addr());
				} else {
					temp.put("shop_addr", "");
				}

				if (shopDTO.getShop_origin_img() != null) {
					temp.put("shop_img_path", filePath + shopDTO.getShop_uuid_img());
					temp.put("shop_origin_img", shopDTO.getShop_origin_img());
				} else {
					temp.put("shop_img_path", "");
					temp.put("shop_origin_img", "");
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
	
	// 샵 상세보기(안드로이드 전용)
	@RequestMapping(value = "/shop/shopViewJson.a")
	public ModelAndView shopViewJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int shop_index = Integer.parseInt(request.getParameter("shop_index"));
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int total = 0;
		String shop_member_score = service.shopevScoreViewJson(shop_index, member_num);
		if(shop_member_score == null) {
			shop_member_score = "-1";
		}
		double shop_total_score = 0;
		int shop_total_count = 0;
		double shop_grade = 0;
		ShopDTO shopDTO = service.shopViewJson(shop_index);
		if (shopDTO != null) {
			total = 1;
			shop_total_score = shopDTO.getShop_total_score();
			shop_total_count = shopDTO.getShop_total_count();
			shop_grade = Math.round((shop_total_score / shop_total_count) * 10) / 10.0;
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
			temp.put("shop_index", shopDTO.getShop_index());
			temp.put("shop_name", shopDTO.getShop_name());
			temp.put("shop_grade", shop_grade);
			temp.put("shop_detail", shopDTO.getShop_detail());
			temp.put("shop_type", shopDTO.getShop_type());
			temp.put("shop_member_score", shop_member_score);
			if (shopDTO.getShop_phone() != null) {
				temp.put("shop_phone", shopDTO.getShop_phone());
			} else {
				temp.put("shop_phone", "");
			}
			if (shopDTO.getShop_url() != null) {
				temp.put("shop_url", shopDTO.getShop_url());
			} else {
				temp.put("shop_url", "");
			}
			if (shopDTO.getShop_email() != null) {
				temp.put("shop_email", shopDTO.getShop_email());
			} else {
				temp.put("shop_phone", "");
			}
			if (shopDTO.getShop_addr() != null) {
				temp.put("shop_addr", shopDTO.getShop_addr());
			} else {
				temp.put("shop_addr", "");
			}
			if (shopDTO.getShop_origin_img() != null) {
				filePath = url + "File/shopImage/";
				temp.put("shop_img_path", filePath + shopDTO.getShop_uuid_img());
				temp.put("shop_origin_img ", shopDTO.getShop_origin_img());
			} else {
				temp.put("shop_img_path", "");
				temp.put("shop_origin_img", "");
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

	// 샵 점수 주기(안드로이드 전용)
	@RequestMapping(value = "/shop/shopUpdateScoreJson.a")
	public ModelAndView shopUpdateScoreJson(HttpServletRequest request) {
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int shop_index = Integer.parseInt(request.getParameter("shop_index"));
		double score = Double.parseDouble(request.getParameter("score"));
//		System.out.println("member_num="+member_num);
//		System.out.println("shop_index="+shop_index);
		int total = 0;
		double su = 0;
		su = service.shopevViewJson(shop_index, member_num);
//		System.out.println("su="+su);
		ShopevDTO shopevDTO = new ShopevDTO();
		shopevDTO.setMember_num(member_num);
		shopevDTO.setShop_index(shop_index);
		shopevDTO.setShop_score(score);
		String rt = null;
		if (su > 0) {
			rt = "LIKE_FAIL";
		} else {
			rt = "LIKE_OK";
			total = service.shopevWriteJson(shopevDTO);
			ShopDTO shopDTO = service.shopViewJson(shop_index);
			double shop_total_score = shopDTO.getShop_total_score()+score;
			int shop_total_count = shopDTO.getShop_total_count()+1;
			double shop_grade = Math.round((shop_total_score / shop_total_count) * 10) / 10.0;
			service.shopUpdateScoreJson(score, shop_index, shop_grade);
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

	// 정보수정(안드로이드 전용)
	@RequestMapping(value = "/shop/shopUpdateJson.a")
	public ModelAndView shopUpdateJson(MultipartFile shop_origin_img, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("../app.jsp");
		return modelAndView;
	}

	// 샵 삭제하기(안드로이드 전용)
	@RequestMapping(value = "/shop/shopDeleteJson.a")
	public ModelAndView shopDeleteJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int shop_index = Integer.parseInt(request.getParameter("shop_index"));
		int total = 0;
		total = service.shopDeleteJson(shop_index);
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
