package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import Model.MemberDTO;
import common_content.CC;

@Controller
public class MemberController {
	//String url = "http://192.168.219.100:8080/bongtoo_server/";
	String url = new CC().connectIP();

	@Autowired
	private MemberService memberService;

	// 아이디 체크(안드로이드 전용)
	@RequestMapping(value = "/member/memberIDCheckJson.a")
	public ModelAndView memberIDCheck(HttpServletRequest request) {
		String member_id = request.getParameter("member_id");
		int total = 0;
		total = memberService.memberIDCheck(member_id);
		String rt = null;
		if (total == 0) {
			rt = "IDOK";
		} else {
			rt = "IDFAIL";
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

	// 회원가입(안드로이드 전용)
	@RequestMapping(value = "/member/memberWriteJson.a")
	public ModelAndView memberWriteJson(MultipartFile member_origin_img, HttpServletRequest request) {
		// 데이터

		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		String member_id = request.getParameter("member_id");
		String member_pw = request.getParameter("member_pw");
		String name = request.getParameter("name");
		String nickname = request.getParameter("nickname");
		String email = request.getParameter("email");
		String member_phone = request.getParameter("member_phone");

		// 부가 정보받아오기 (부가 정보)
		// 부가정보 객체 선언 및 초기화
		String origin_fileName = "";
		String uuid_fileName = "";

		// 사진정보 체크
		if (member_origin_img != null) {
			// 사진정보 받아오기
			UUID uuid = UUID.randomUUID();
			String filePath = request.getServletContext().getRealPath("/File/memberImage");
			origin_fileName = member_origin_img.getOriginalFilename();
			uuid_fileName = uuid.toString() + origin_fileName;
			File file = new File(filePath, uuid_fileName);

			// 파일 복사
			try {
				// getInputStream() : 업로드한 파일 데이터를 읽어오는 InputStream을 구한다.
				FileCopyUtils.copy(member_origin_img.getInputStream(), new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// DTO저장(not null & primary key)
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMember_id(member_id);
		memberDTO.setMember_phone(member_phone);
		memberDTO.setMember_pw(member_pw);
		memberDTO.setName(name);
		memberDTO.setNickname(nickname);
		// DTO저장(부가 정보)
		memberDTO.setMember_origin_img(origin_fileName);
		memberDTO.setMember_uuid_img(uuid_fileName);
		memberDTO.setEmail(email);

		int total = memberService.memberWriteJson(memberDTO);
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

	// 로그인(안드로이드 전용)
	@RequestMapping(value = "/member/memberLoginJson.a")
	public ModelAndView memberLoginJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String member_id = request.getParameter("member_id");
		String member_pw = request.getParameter("member_pw");
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMember_id(member_id);
		memberDTO.setMember_pw(member_pw);
		MemberDTO dto = memberService.memberLoginJson(memberDTO);

		int total = 0;
		if (dto != null) {
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
			temp.put("member_num", dto.getMember_num());
			temp.put("nickname", dto.getNickname());
			temp.put("grade", dto.getGrade());
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

	// 상세보기(안드로이드 전용)
	@RequestMapping(value = "/member/memberViewJson.a")
	public ModelAndView memberViewJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int member_num = 0;
		int total = 0;
		String member_num_str = request.getParameter("member_num");
		if (member_num_str != null) {
			member_num = Integer.parseInt(member_num_str);
		}
		MemberDTO memberDTO = memberService.memberViewJson(member_num);
		if (memberDTO != null) {
			total = 1;
		}
		String filePath = url + "File/memberImage/";
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
			temp.put("member_num", memberDTO.getMember_num());
			temp.put("member_id", memberDTO.getMember_id());
			temp.put("member_phone", memberDTO.getMember_phone());
			if (memberDTO.getMember_origin_img() != null) {
				temp.put("member_img_path", filePath + memberDTO.getMember_uuid_img());
				temp.put("member_origin_img", memberDTO.getMember_origin_img());
			} else {
				temp.put("member_img_path", "");
				temp.put("member_origin_img", "");
			}

			temp.put("grade", memberDTO.getGrade());
			temp.put("name", memberDTO.getName());
			temp.put("nickname", memberDTO.getNickname());
			temp.put("email", memberDTO.getEmail());
			temp.put("money_give", memberDTO.getMoney_give());
			temp.put("money_take", memberDTO.getMoney_take());
			temp.put("first_logtime", memberDTO.getFirst_logtime());
			temp.put("edit_logtime", memberDTO.getEdit_logtime());

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
	@RequestMapping(value = "/member/memberUpdateJson.a")
	public ModelAndView memberUpdateJson(MultipartFile member_origin_img, HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));

		// 기존 정보 셋팅
		MemberDTO memberDTO = memberService.memberViewJson(member_num);
		String nickname = memberDTO.getNickname();
		String email = memberDTO.getEmail();
		String origin_fileName = memberDTO.getMember_origin_img();
		String uuid_fileName = memberDTO.getMember_uuid_img();
		String member_pw = null;
		// 변경값 존재시
		if (request.getParameter("nickname") != null) {
			nickname = request.getParameter("nickname");
		}
		if (request.getParameter("email") != null) {
			email = request.getParameter("email");
		}
		if (request.getParameter("member_pw") != null) {
			member_pw = request.getParameter("member_pw");
		}

		// 사진정보 체크
		if (member_origin_img != null) {
			// 사진정보 받아오기
			UUID uuid = UUID.randomUUID();
			String filePath = request.getServletContext().getRealPath("/File/memberImage");
			origin_fileName = member_origin_img.getOriginalFilename();
			uuid_fileName = uuid.toString() + origin_fileName;
			File file = new File(filePath, uuid_fileName);

			// 파일 복사
			try {
				// getInputStream() : 업로드한 파일 데이터를 읽어오는 InputStream을 구한다.
				FileCopyUtils.copy(member_origin_img.getInputStream(), new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// DTO저장(not null & primary key)
		memberDTO.setMember_num(member_num);
		memberDTO.setNickname(nickname);
		// DTO저장(부가 정보)
		memberDTO.setMember_origin_img(origin_fileName);
		memberDTO.setMember_uuid_img(uuid_fileName);
		memberDTO.setEmail(email);
		int total = 0;
		if (member_pw != null) {
			memberDTO.setMember_pw(member_pw);
			total = memberService.memberUpdatePWJson(memberDTO);
		} else {
			total = memberService.memberUpdateJson(memberDTO);
		}
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

	// 금액수정(안드로이드 전용)
	@RequestMapping(value = "/member/memberCreditJson.a")
	public ModelAndView memberCreditJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		String money_give_str = request.getParameter("money_give");
		String money_take_str = request.getParameter("money_take");
		int money_give = 0;
		int money_take = 0;

		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMember_num(member_num);
		if (money_give_str != null) {
			money_give = Integer.parseInt(money_give_str);
		}
		if (money_take_str != null) {
			money_take = Integer.parseInt(money_take_str);
		}

		memberDTO.setMoney_give(money_give);
		memberDTO.setMoney_take(money_take);
		int total = memberService.memberCreditJson(memberDTO);
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
	@RequestMapping(value = "/member/memberDeleteJson.a")
	public ModelAndView memberDeleteJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));

		int total = memberService.memberDeleteJson(member_num);
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

	// 관리자 로그인(웹브라우저 전용)
	@RequestMapping(value = "/member/adminLoginCheck.web")
	public ModelAndView adminLoginCheck(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		String input_id = request.getParameter("input_id");
		String input_pw = request.getParameter("input_pw");

		ModelAndView modelAndView = new ModelAndView();
		if (input_id.equals("hong") && input_pw.equals("1234")) {
			HttpSession session = request.getSession();
			session.setAttribute("input_id", "hong");
			modelAndView.addObject("event_type", 1);
			// modelAndView.setViewName("redirect:../board/boardList.do");
//                    modelAndView.setViewName("redirect:../event/eventListJson.a");
			modelAndView.setViewName("redirect:/clientWeb/WebPage/AdminNotice1_NoticeList.jsp");
		} else {
			modelAndView.setViewName("redirect:/clientWeb/WebPage/AdminLoginResult.jsp");
		}
		return modelAndView;
	}

//	// 회원가입(안드로이드 전용) 쭌행님꺼는 못쓰꼤음
//	@RequestMapping(value = "/member/memberWriteJson.a")
//	public ModelAndView memberWriteJson(MultipartFile member_origin_img, HttpServletRequest request) {
//		// 위에 페이지에서 contentType="text/html으로 사용해도 상관없으나 일반적으로 contentType="application/json으로 바꿔서 사용해준다
//		// 한글 인코딩 설정
//		try {
//			request.setCharacterEncoding("UTF-8");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//		// 실제 폴더 위치
//		String dir = request.getServletContext().getRealPath("/memberImage");
//		System.out.println(dir);
//		MultipartRequest multi = null;
//		try {
//			multi = new MultipartRequest(request, dir, 5*1024*1024, "UTF-8", new DefaultFileRenamePolicy());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("multi = " + multi);
//		//데이터
//		String user_name = multi.getParameter("user_name");
//		String user_pwd = multi.getParameter("user_pwd");
//		String email = multi.getParameter("email");
//		String subject = multi.getParameter("subject");
//		String content = multi.getParameter("content");
//
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("../member/memberWriteJson.jsp");
//		return modelAndView;
//	}
}