package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import Model.ReplyDTO;
import common_content.CC;

@Controller
public class BoardController {
	//String url = "http://192.168.219.100:8080/bongtoo_server/";
	String url = new CC().connectIP();

	@Autowired
	private BoardService boardService;

	// 글쓰기(안드로이드 전용)
	@RequestMapping(value = "/board/boardWriteJson.a")
	public ModelAndView boardWriteJson(MultipartFile board_origin_img, MultipartFile board_origin_video,
			HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		String board_title = request.getParameter("board_title");
		String board_description = request.getParameter("board_description");
		String board_category = request.getParameter("board_category");

		// 부가 정보받아오기 (부가 정보)
		// 부가정보 객체 선언 및 초기화
		String origin_fileName = "";
		String uuid_fileName = "";
		String origin_fileName_video = "";
		String uuid_fileName_video = "";
		UUID uuid;
		// 사진정보 체크
		if (board_origin_img != null) {
			// 사진정보 받아오기
			uuid = UUID.randomUUID();
			String filePath = request.getServletContext().getRealPath("File/boardImage");
			origin_fileName = board_origin_img.getOriginalFilename();
			uuid_fileName = uuid.toString() + origin_fileName;
			File file = new File(filePath, uuid_fileName);

			// 파일 복사
			try {
				// getInputStream() : 업로드한 파일 데이터를 읽어오는 InputStream을 구한다.
				FileCopyUtils.copy(board_origin_img.getInputStream(), new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 동영상정보 체크
		if (board_origin_video != null) {
			// 사진정보 받아오기
			uuid = UUID.randomUUID();
			String filePath_video = request.getServletContext().getRealPath("/boardVideo");
			origin_fileName_video = board_origin_video.getOriginalFilename();
			uuid_fileName_video = uuid.toString() + origin_fileName_video;
			File file = new File(filePath_video, uuid_fileName_video);

			// 파일 복사
			try {
				// getInputStream() : 업로드한 파일 데이터를 읽어오는 InputStream을 구한다.
				FileCopyUtils.copy(board_origin_video.getInputStream(), new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// DTO저장(not null & primary key)
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setMember_num(member_num);
		boardDTO.setBoard_title(board_title);
		boardDTO.setBoard_description(board_description);
		boardDTO.setBoard_category(board_category);
		// DTO저장(부가 정보)
		boardDTO.setBoard_origin_img(origin_fileName);
		boardDTO.setBoard_uuid_img(uuid_fileName);
		boardDTO.setBoard_origin_video(origin_fileName_video);
		boardDTO.setBoard_uuid_video(uuid_fileName_video);
		int total = boardService.boardWriteJson(boardDTO);
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

	//// *안씁니다.* 게시글 조회수 업데이트(안드로이드 전용)
	@RequestMapping(value = "/board/boardHitUpdateJson.a")
	public ModelAndView boardHitUpdateJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		int total = boardService.boardUpdateHitJson(board_num);
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
	@RequestMapping(value = "/board/boardViewJson.a")
	public ModelAndView memberViewJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int total = 0;
		BoardDTO boardDTO = boardService.boardViewJson(board_num, member_num);
		if (boardDTO != null) {
			total = 1;
			boardService.boardUpdateHitJson(board_num);
			boardDTO = boardService.boardViewJson(board_num, member_num);
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
			temp.put("member_num", boardDTO.getMember_num());
			temp.put("board_num", boardDTO.getBoard_num());
			if (boardDTO.getBoard_origin_img() != null) {
				filePath = url + "File/boardImage/";
				temp.put("board_img_path", filePath + boardDTO.getBoard_uuid_img());
				temp.put("board_origin_img ", boardDTO.getBoard_origin_img());
			} else {
				temp.put("board_img_path", "");
				temp.put("board_origin_img", "");
			}
			if (boardDTO.getBoard_origin_video() != null) {
				filePath = url + "boardVideo/";
				temp.put("board_video_path", filePath + boardDTO.getBoard_uuid_video());
				temp.put("board_origin_video", boardDTO.getBoard_origin_video());
			} else {
				temp.put("board_video_path", "");
				temp.put("board_origin_video", "");
			}
			temp.put("board_title", boardDTO.getBoard_title());
			temp.put("board_description", boardDTO.getBoard_description());
			temp.put("board_category", boardDTO.getBoard_category());
			temp.put("board_hit", boardDTO.getBoard_hit());
			temp.put("board_like", boardDTO.getBoard_like());
			temp.put("board_first_date", boardDTO.getBoard_first_date());
			temp.put("board_edit_date", boardDTO.getBoard_edit_date());
			temp.put("grade", boardDTO.getGrade());
			temp.put("nickname", boardDTO.getNickname());

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
	@RequestMapping(value = "/board/boardUpdateJson.a")
	public ModelAndView boardUpdateJson(MultipartFile board_origin_img, MultipartFile board_origin_video,
			HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		// 부가 정보받아오기 (부가 정보)
		// 부가정보 객체 선언 및 초기화
		BoardDTO boardDTO = boardService.boardViewJson(board_num, member_num);
		String board_title = "";
		String board_description = "";
		String origin_fileName = "";
		String uuid_fileName = "";
		String origin_fileName_video = "";
		String uuid_fileName_video = "";
		String board_category = "";
		if (request.getParameter("board_title") != null) {
			board_title = request.getParameter("board_title");
		} else {
			board_title = boardDTO.getBoard_title();
		}
		if (request.getParameter("board_description") != null) {
			board_description = request.getParameter("board_description");
		} else {
			board_description = boardDTO.getBoard_description();
		}
		if (request.getParameter("board_category") != null) {
			board_category = request.getParameter("board_category");
		} else {
			board_category = boardDTO.getBoard_category();
		}
		UUID uuid;
		// 사진정보 체크
		if (board_origin_img != null) {
			// 사진정보 받아오기
			uuid = UUID.randomUUID();
			String filePath = request.getServletContext().getRealPath("/File/boardImage");
			origin_fileName = board_origin_img.getOriginalFilename();
			uuid_fileName = uuid.toString() + origin_fileName;
			File file = new File(filePath, uuid_fileName);

			// 파일 복사
			try {
				// getInputStream() : 업로드한 파일 데이터를 읽어오는 InputStream을 구한다.
				FileCopyUtils.copy(board_origin_img.getInputStream(), new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (boardDTO.getBoard_origin_img() != null) {
			origin_fileName = boardDTO.getBoard_origin_img();
			uuid_fileName = boardDTO.getBoard_uuid_img();
		} else {
			origin_fileName = "";
			uuid_fileName = "";
		}
		// 동영상정보 체크
		if (board_origin_video != null) {
			// 사진정보 받아오기
			uuid = UUID.randomUUID();
			String filePath_video = request.getServletContext().getRealPath("/boardVideo");
			origin_fileName_video = board_origin_video.getOriginalFilename();
			uuid_fileName_video = uuid.toString() + origin_fileName_video;
			File file = new File(filePath_video, uuid_fileName_video);

			// 파일 복사
			try {
				// getInputStream() : 업로드한 파일 데이터를 읽어오는 InputStream을 구한다.
				FileCopyUtils.copy(board_origin_video.getInputStream(), new FileOutputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (boardDTO.getBoard_origin_video() != null) {
			origin_fileName_video = boardDTO.getBoard_origin_video();
			uuid_fileName_video = boardDTO.getBoard_uuid_video();
		} else {
			origin_fileName_video = "";
			uuid_fileName_video = "";
		}
		// DTO저장(not null & primary key)
		boardDTO.setMember_num(member_num);
		boardDTO.setBoard_num(board_num);
		boardDTO.setBoard_title(board_title);
		boardDTO.setBoard_description(board_description);
		boardDTO.setBoard_category(board_category);
		// DTO저장(부가 정보)
		boardDTO.setBoard_origin_img(origin_fileName);
		boardDTO.setBoard_uuid_img(uuid_fileName);
		boardDTO.setBoard_origin_video(origin_fileName_video);
		boardDTO.setBoard_uuid_video(uuid_fileName_video);
		int total = boardService.boardUpdateJson(boardDTO);
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
	@RequestMapping(value = "/board/boardDeleteJson.a")
	public ModelAndView boardDeleteJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		int total = 0;
		total = boardService.boardDeleteJson(board_num, member_num);
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

	// 글 전체 리스트 (안드로이드 전용)
	@RequestMapping(value = "/board/boardListJson.a")
	public ModelAndView boardListJson(HttpServletRequest request) {
		// 1. 데이터
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2. DB
		// 1페이지당 목록 5개
		int endNum = pg * 10;
		int startNum = endNum - 9;
		List<BoardDTO> list;
		if (request.getParameter("up_down") != null) {
			list = boardService.boardListascJson(startNum, endNum);
		} else {
			list = boardService.boardListdescJson(startNum, endNum);
		}


		// JSON으로 결과값 반환
		String filePath = url + "File/boardImage/";
		String filePath_video = url + "File/boardVideo/";
		String rt = null;
		int total = 0;
		int totalAll = 0;
		total = list.size();
		if (total > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		totalAll = boardService.boardListTotalJson();
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		
		if (total > 0) {

			// 게시글 작성 날짜와 현재날짜를 비교하여 new여부를 판단하기 위한 데이터 셋팅
	        Calendar calendar = Calendar.getInstance();
	        int yy = calendar.get(Calendar.YEAR);
	        int mm = calendar.get(Calendar.MONTH) + 1;
	        int dd = calendar.get(Calendar.DAY_OF_MONTH);
	        calendar = null;		
			SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
	        Date server_date = null;
			try {
				server_date = dateFormat.parse(yy+"-"+mm+"-"+dd);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
	        Date board_date = null;       
			boolean isnew = false;	// new이면 true
			
			for (int i = 0; i < total; i++) {
				BoardDTO boardDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("member_num", boardDTO.getMember_num());
				temp.put("board_num", boardDTO.getBoard_num());
				temp.put("board_title", boardDTO.getBoard_title());
				temp.put("board_description", boardDTO.getBoard_description());
				temp.put("board_first_date", boardDTO.getBoard_first_date());
				try {
					board_date = dateFormat.parse(boardDTO.getBoard_first_date());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(server_date.compareTo(board_date) == 0) {
					isnew = true;
				} else {
					isnew = false;
				}
				temp.put("isnew", isnew);
				temp.put("board_edit_date", boardDTO.getBoard_edit_date());
				if (boardDTO.getBoard_origin_img() != null) {
					temp.put("board_img_path", filePath + boardDTO.getBoard_uuid_img());
					temp.put("board_origin_img", boardDTO.getBoard_origin_img());
				} else {
					temp.put("board_img_path", "");
					temp.put("board_origin_img", "");
				}
				if (boardDTO.getBoard_origin_video() != null) {
					temp.put("board_video_path", filePath_video + boardDTO.getBoard_uuid_video());
					temp.put("board_origin_video", boardDTO.getBoard_origin_video());
				} else {
					temp.put("board_video_path", "");
					temp.put("board_origin_video", "");
				}
				temp.put("board_category", boardDTO.getBoard_category());
				temp.put("board_hit", boardDTO.getBoard_hit());
				temp.put("board_like", boardDTO.getBoard_like());
				temp.put("grade", boardDTO.getGrade());
				temp.put("nickname", boardDTO.getNickname());
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

	// 검색 리스트 (안드로이드 전용)
	@RequestMapping(value = "/board/boardSearchListJson.a")
	public ModelAndView boardSearchListJson(HttpServletRequest request) {
		// 1. 데이터
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		String search_word = "%" + request.getParameter("search_word") + "%";
		// 2. DB
		// 1페이지당 목록 5개
		int endNum = pg * 10;
		int startNum = endNum - 9;
		List<BoardDTO> list = boardService.boardSearchListdescJson(startNum, endNum, search_word);

		if (request.getParameter("up_down") != null) {
			list = boardService.boardSearchListascJson(startNum, endNum, search_word);
		} else {
			list = boardService.boardSearchListdescJson(startNum, endNum, search_word);
		}

		// JSON으로 결과값 반환
		String filePath = url + "File/boardImage/";
		String filePath_video = url + "File/boardVideo/";
		String rt = null;
		int total = 0;
		int totalAll = 0;
		total = list.size();
		if (total > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		totalAll = boardService.boardSearchListTotalJson(search_word);
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		if (total > 0) {
			
			// 게시글 작성 날짜와 현재날짜를 비교하여 new여부를 판단하기 위한 데이터 셋팅
	        Calendar calendar = Calendar.getInstance();
	        int yy = calendar.get(Calendar.YEAR);
	        int mm = calendar.get(Calendar.MONTH) + 1;
	        int dd = calendar.get(Calendar.DAY_OF_MONTH);
	        calendar = null;		
			SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
	        Date server_date = null;
			try {
				server_date = dateFormat.parse(yy+"-"+mm+"-"+dd);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
	        Date board_date = null;       
			boolean isnew = false;	// new이면 true
			
			for (int i = 0; i < total; i++) {
				BoardDTO boardDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("member_num", boardDTO.getMember_num());
				temp.put("board_num", boardDTO.getBoard_num());
				temp.put("board_title", boardDTO.getBoard_title());
				temp.put("board_description", boardDTO.getBoard_description());
				temp.put("board_first_date", boardDTO.getBoard_first_date());
				try {
					board_date = dateFormat.parse(boardDTO.getBoard_first_date());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(server_date.compareTo(board_date) == 0) {
					isnew = true;
				} else {
					isnew = false;
				}
				temp.put("isnew", isnew);				
				temp.put("board_edit_date", boardDTO.getBoard_edit_date());
				if (boardDTO.getBoard_origin_img() != null) {
					temp.put("board_img_path", filePath + boardDTO.getBoard_uuid_img());
					temp.put("board_origin_img", boardDTO.getBoard_origin_img());
				} else {
					temp.put("board_img_path", "");
					temp.put("board_origin_img", "");
				}
				if (boardDTO.getBoard_origin_video() != null) {
					temp.put("board_video_path", filePath_video + boardDTO.getBoard_uuid_video());
					temp.put("board_origin_video", boardDTO.getBoard_origin_video());
				} else {
					temp.put("board_video_path", "");
					temp.put("board_origin_video", "");
				}
				temp.put("board_category", boardDTO.getBoard_category());
				temp.put("board_hit", boardDTO.getBoard_hit());
				temp.put("board_like", boardDTO.getBoard_like());
				temp.put("grade", boardDTO.getGrade());
				temp.put("nickname", boardDTO.getNickname());
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

	// 카테고리 글 전체 리스트 (안드로이드 전용)
	@RequestMapping(value = "/board/boardListCategoryJson.a")
	public ModelAndView boardListCategoryJson(HttpServletRequest request) {
		// 1. 데이터
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2. DB
		// 1페이지당 목록 10개
		int endNum = pg * 10;
		int startNum = endNum - 9;
		String board_category = request.getParameter("board_category");
		List<BoardDTO> list;
//		if(request.getParameter("up_down") != null) {
//			list = boardService.boardListCategoryascJson(startNum, endNum, board_category);
//		} else {
//			list = boardService.boardListCategorydescJson(startNum, endNum, board_category);
//		}

		int up_down = 0;

		if (request.getParameter("up_down") != null) {
			up_down = Integer.parseInt(request.getParameter("up_down"));
		}
		if (up_down == 0) {
			list = boardService.boardListCategorydescJson(startNum, endNum, board_category);
		} else {
			list = boardService.boardListCategoryascJson(startNum, endNum, board_category);
		}

		// JSON으로 결과값 반환
		String filePath = url + "File/boardImage/";
		String filePath_video = url + "File/boardVideo/";
		String rt = null;
		int total = 0;
		int totalAll = 0;
		total = list.size();
		if (total > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		totalAll = boardService.boardListCategoryTotalJson(board_category);
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		if (total > 0) {
			
			// 게시글 작성 날짜와 현재날짜를 비교하여 new여부를 판단하기 위한 데이터 셋팅
	        Calendar calendar = Calendar.getInstance();
	        int yy = calendar.get(Calendar.YEAR);
	        int mm = calendar.get(Calendar.MONTH) + 1;
	        int dd = calendar.get(Calendar.DAY_OF_MONTH);
	        calendar = null;		
			SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
	        Date server_date = null;
			try {
				server_date = dateFormat.parse(yy+"-"+mm+"-"+dd);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
	        Date board_date = null;       
			boolean isnew = false;	// new이면 true
			
			for (int i = 0; i < total; i++) {
				BoardDTO boardDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("member_num", boardDTO.getMember_num());
				temp.put("board_num", boardDTO.getBoard_num());
				temp.put("board_title", boardDTO.getBoard_title());
				temp.put("board_description", boardDTO.getBoard_description());
				temp.put("board_first_date", boardDTO.getBoard_first_date());
				try {
					board_date = dateFormat.parse(boardDTO.getBoard_first_date());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(server_date.compareTo(board_date) == 0) {
					isnew = true;
				} else {
					isnew = false;
				}
				temp.put("isnew", isnew);		
				temp.put("board_edit_date", boardDTO.getBoard_edit_date());
				if (boardDTO.getBoard_origin_img() != null) {
					temp.put("board_img_path", filePath + boardDTO.getBoard_uuid_img());
					temp.put("board_origin_img", boardDTO.getBoard_origin_img());
				} else {
					temp.put("board_img_path", "");
					temp.put("board_origin_img", "");
				}
				if (boardDTO.getBoard_origin_video() != null) {
					temp.put("board_video_path", filePath_video + boardDTO.getBoard_uuid_video());
					temp.put("board_origin_video", boardDTO.getBoard_origin_video());
				} else {
					temp.put("board_video_path", "");
					temp.put("board_origin_video", "");
				}
				temp.put("board_category", boardDTO.getBoard_category());
				temp.put("board_hit", boardDTO.getBoard_hit());
				temp.put("board_like", boardDTO.getBoard_like());
				temp.put("grade", boardDTO.getGrade());
				temp.put("nickname", boardDTO.getNickname());
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

	// 게시글 댓글 작성 (안드로이드 전용)
	@RequestMapping(value = "/board/boardReplyWriteJson.a")
	public ModelAndView boardReplyWriteJson(HttpServletRequest request) {
		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String reply_description = request.getParameter("reply_description");

		// DTO저장(not null & primary key)
		ReplyDTO replyDTO = new ReplyDTO();
		replyDTO.setMember_num(member_num);
		replyDTO.setBoard_num(board_num);
		replyDTO.setReply_description(reply_description);
		int total = boardService.boardReplyWriteJson(replyDTO);
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

	// 게시글 댓글 수정 (안드로이드 전용)
	@RequestMapping(value = "/board/boardReplyUpdateJson.a")
	public ModelAndView boardReplyUpdateJson(HttpServletRequest request) {
		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String reply_description = request.getParameter("reply_description");

		// DTO저장(not null & primary key)
		ReplyDTO replyDTO = new ReplyDTO();
		replyDTO.setMember_num(member_num);
		replyDTO.setBoard_num(board_num);
		replyDTO.setReply_description(reply_description);
		int total = boardService.boardReplyUpdateJson(replyDTO);
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

	// 게시글 댓글 리스트 (안드로이드 전용)
	@RequestMapping(value = "/board/boardReplyListJson.a")
	public ModelAndView boardReplyListJson(HttpServletRequest request) {
		// 1. 데이터
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2. DB
		// 1페이지당 목록 10개
		int endNum = pg * 10;
		int startNum = endNum - 9;
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		List<ReplyDTO> list = boardService.boardReplyListJson(board_num, startNum, endNum);
		boardService.boardUpdateHitJson(board_num);
		
		
		// JSON으로 결과값 반환
		String rt = null;
		int total = 0;
		int totalAll = 0;
		total = list.size();
		if (total > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		totalAll = boardService.boardReplyListTotalJson(board_num);
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		if (total > 0) {
			for (int i = 0; i < total; i++) {
				ReplyDTO replyDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("member_num", replyDTO.getMember_num());
				temp.put("board_num", replyDTO.getBoard_num());
				temp.put("reply_num", replyDTO.getReply_num());
				temp.put("reply_description", replyDTO.getReply_description());
				temp.put("reply_like", replyDTO.getReply_like());
				temp.put("grade", replyDTO.getGrade());
				temp.put("nickname", replyDTO.getNickname());
				temp.put("reply_first_date", replyDTO.getReply_first_date());
				temp.put("reply_edit_date", replyDTO.getReply_edit_date());
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

	// 댓글 삭제하기(안드로이드 전용)
	@RequestMapping(value = "/board/boardReplyDeleteJson.a")
	public ModelAndView boardReplyDeleteJson(HttpServletRequest request) {
		// 데이터
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 필수 정보받아오기 (not null & primary key)
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int reply_num = Integer.parseInt(request.getParameter("reply_num"));
		ReplyDTO replyDTO = new ReplyDTO();
		replyDTO.setMember_num(member_num);
		replyDTO.setReply_num(reply_num);
		int total = 0;
		total = boardService.boardReplyDeleteJson(replyDTO);
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

	// 내가 쓴 글 전체 리스트 (안드로이드 전용)
	@RequestMapping(value = "/board/myboardListJson.a")
	public ModelAndView myboardListJson(HttpServletRequest request) {
		// 1. 데이터
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2. DB
		// 1페이지당 목록 5개
		int endNum = pg * 10;
		int startNum = endNum - 9;
		String up_down = "";
		List<BoardDTO> list;

		if (request.getParameter("up_down") != null) {
			list = boardService.myboardListascJson(member_num, startNum, endNum);
		} else {
			list = boardService.myboardListdescJson(member_num, startNum, endNum);
		}

		// JSON으로 결과값 반환
		String filePath = url + "File/boardImage/";
		String filePath_video = url + "File/boardVideo/";
		String rt = null;
		int total = 0;
		int totalAll = 0;
		total = list.size();
		if (total > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		totalAll = boardService.myboardListTotalJson(member_num);
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		if (total > 0) {
			for (int i = 0; i < total; i++) {
				BoardDTO boardDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("member_num", boardDTO.getMember_num());
				temp.put("board_num", boardDTO.getBoard_num());
				temp.put("board_title", boardDTO.getBoard_title());
				temp.put("board_description", boardDTO.getBoard_description());
				temp.put("board_first_date", boardDTO.getBoard_first_date());
				temp.put("board_edit_date", boardDTO.getBoard_edit_date());
				if (boardDTO.getBoard_origin_img() != null) {
					temp.put("board_img_path", filePath + boardDTO.getBoard_uuid_img());
					temp.put("board_origin_img", boardDTO.getBoard_origin_img());
				} else {
					temp.put("board_img_path", "");
					temp.put("board_origin_img", "");
				}
				if (boardDTO.getBoard_origin_video() != null) {
					temp.put("board_video_path", filePath_video + boardDTO.getBoard_uuid_video());
					temp.put("board_origin_video", boardDTO.getBoard_origin_video());
				} else {
					temp.put("board_video_path", "");
					temp.put("board_origin_video", "");
				}
				temp.put("board_category", boardDTO.getBoard_category());
				temp.put("board_hit", boardDTO.getBoard_hit());
				temp.put("board_like", boardDTO.getBoard_like());
				temp.put("grade", boardDTO.getGrade());
				temp.put("nickname", boardDTO.getNickname());
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

	// 통합 추천하기(안드로이드 전용)
	@RequestMapping(value = "/board/boardLikeJson.a")
	public ModelAndView boardLikeJson(HttpServletRequest request) {
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		int member_num = Integer.parseInt(request.getParameter("member_num"));
		int reply_num = 0;
		int reply_member_num = 0;
		if (request.getParameter("reply_num") != null) {
			reply_num = Integer.parseInt(request.getParameter("reply_num"));
		}
		if (request.getParameter("reply_member_num") != null) {
			reply_member_num = Integer.parseInt(request.getParameter("reply_member_num"));
		}

		ReplyDTO replyDTO = new ReplyDTO();
		replyDTO.setMember_num(member_num);
		replyDTO.setBoard_num(board_num);
		replyDTO.setReply_num(reply_num);
		int total = 0;
		int like_num = 0;
		int board_like = 0;
		int reply_like = 0;

		total = boardService.btLikeCheckJson(replyDTO);
		if (total == 0) {
			boardService.btLikeWriteJson(replyDTO);
			like_num = +1;
		} else {
			boardService.btLikeDeleteJson(replyDTO);
			like_num = -1;
		}

		if (reply_num == 0) {
			total = boardService.boardLikeUpdateJson(board_num, like_num);
			board_like = boardService.boardLikeViewJson(replyDTO);
		} else {
			total = boardService.boardReplyLikeUpdateJson(reply_num, like_num);
			replyDTO.setMember_num(reply_member_num);
			reply_like = boardService.replyLikeViewJson(replyDTO);
		}

		String rt = null;
		if (like_num > 0) {
			rt = "OK";

		} else if (like_num < 0) {
			rt = "FAIL";
		} else {
			rt = "ERROR";
		}
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		json.put("rt", rt);
		json.put("total", total);
		json.put("item", item);
		json.put("board_like", board_like);
		json.put("reply_like", reply_like);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("json", json);
		modelAndView.setViewName("../app.jsp");
		return modelAndView;
	}

	// 인기 글 전체 리스트 (안드로이드 전용)
	@RequestMapping(value = "/board/boardLikeListJson.a")
	public ModelAndView boardLikeListJson(HttpServletRequest request) {
		// 1. 데이터
		int pg = 1;
		if (request.getParameter("pg") != null) {
			pg = Integer.parseInt(request.getParameter("pg"));
		}
		// 2. DB
		// 1페이지당 목록 5개
		int endNum = pg * 10;
		int startNum = endNum - 9;
		String board_set = "board_like";
		List<BoardDTO> list = boardService.boardLikeListJson(startNum, endNum, board_set);

		// JSON으로 결과값 반환
		String filePath = url + "File/boardImage/";
		String filePath_video = url + "File/boardVideo/";
		String rt = null;
		int total = 0;
		int totalAll = 0;
		total = list.size();
		if (total > 0) {
			rt = "OK";
		} else {
			rt = "FAIL";
		}
		totalAll = boardService.boardListTotalJson();
		JSONObject json = new JSONObject();
		JSONArray item = new JSONArray();
		if (total > 0) {
			
			// 게시글 작성 날짜와 현재날짜를 비교하여 new여부를 판단하기 위한 데이터 셋팅
	        Calendar calendar = Calendar.getInstance();
	        int yy = calendar.get(Calendar.YEAR);
	        int mm = calendar.get(Calendar.MONTH) + 1;
	        int dd = calendar.get(Calendar.DAY_OF_MONTH);
	        calendar = null;		
			SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
	        Date server_date = null;
			try {
				server_date = dateFormat.parse(yy+"-"+mm+"-"+dd);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
	        Date board_date = null;       
			boolean isnew = false;	// new이면 true
			
			for (int i = 0; i < total; i++) {
				BoardDTO boardDTO = list.get(i);
				JSONObject temp = new JSONObject();
				temp.put("member_num", boardDTO.getMember_num());
				temp.put("board_num", boardDTO.getBoard_num());
				temp.put("board_title", boardDTO.getBoard_title());
				temp.put("board_description", boardDTO.getBoard_description());
				temp.put("board_first_date", boardDTO.getBoard_first_date());
				try {
					board_date = dateFormat.parse(boardDTO.getBoard_first_date());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(server_date.compareTo(board_date) == 0) {
					isnew = true;
				} else {
					isnew = false;
				}
				temp.put("isnew", isnew);	
				temp.put("board_edit_date", boardDTO.getBoard_edit_date());
				if (boardDTO.getBoard_origin_img() != null) {
					temp.put("board_img_path", filePath + boardDTO.getBoard_uuid_img());
					temp.put("board_origin_img", boardDTO.getBoard_origin_img());
				} else {
					temp.put("board_img_path", "");
					temp.put("board_origin_img", "");
				}
				if (boardDTO.getBoard_origin_video() != null) {
					temp.put("board_video_path", filePath_video + boardDTO.getBoard_uuid_video());
					temp.put("board_origin_video", boardDTO.getBoard_origin_video());
				} else {
					temp.put("board_video_path", "");
					temp.put("board_origin_video", "");
				}
				temp.put("board_category", boardDTO.getBoard_category());
				temp.put("board_hit", boardDTO.getBoard_hit());
				temp.put("board_like", boardDTO.getBoard_like());
				temp.put("grade", boardDTO.getGrade());
				temp.put("nickname", boardDTO.getNickname());
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

}
