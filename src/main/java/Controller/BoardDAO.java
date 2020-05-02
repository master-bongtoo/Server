package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Model.BoardDTO;
import Model.ReplyDTO;

@Repository
public class BoardDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	// 게시판 글쓰기
	public int boardWriteJson(BoardDTO boardDTO) {
		return sqlSession.insert("mybatis.board.boardWriteJson", boardDTO);
	}
	// 글 상세 보기
	public BoardDTO boardViewJson(int board_num, int member_num) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("board_num", board_num);
		map.put("member_num", member_num);
		return sqlSession.selectOne("mybatis.board.boardViewJson", map);
	}
	// 카테고리 글 전체 갯수
	public int boardListCategoryTotalJson(String board_category) {
		return sqlSession.selectOne("mybatis.board.boardListCategoryTotalJson", board_category);
	}
	// 글 전체 갯수
	public int boardListTotalJson() {
		return sqlSession.selectOne("mybatis.board.boardListTotalJson");
	}
	// 글 전체 리스트 desc(내림차순)
	public List<BoardDTO> boardListdescJson(int startNum, int endNum) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		return sqlSession.selectList("mybatis.board.boardListdescJson", map);
	}
	// 글 전체 리스트 asc(오름 차순)
	public List<BoardDTO> boardListascJson(int startNum, int endNum) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		return sqlSession.selectList("mybatis.board.boardListascJson", map);
	}
	// 카테고리 글 전체 리스트 desc(내림차순)
	public List<BoardDTO> boardListCategorydescJson(int startNum, int endNum, String board_category) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		map.put("board_category", board_category);
		return sqlSession.selectList("mybatis.board.boardListCategorydescJson", map);
	}
	// 카테고리 글 전체 리스트 asc(오름 차순)
	public List<BoardDTO> boardListCategoryascJson(int startNum, int endNum, String board_category) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		map.put("board_category", board_category);
		return sqlSession.selectList("mybatis.board.boardListCategoryascJson", map);
	}
	
	// 글 전체 검색 갯수
	public int boardSearchListTotalJson(String search_word) {
		return sqlSession.selectOne("mybatis.board.boardSearchListTotalJson", search_word);
	}
	// 글 전체 검색 리스트 desc(내림차순)
	public List<BoardDTO> boardSearchListdescJson(int startNum, int endNum, String search_word) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		map.put("search_word", search_word);
		return sqlSession.selectList("mybatis.board.boardSearchListdescJson", map);
	}
	// 글 전체 검색 리스트 asc(오름 차순)
	public List<BoardDTO> boardSearchListascJson(int startNum, int endNum, String search_word) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		map.put("search_word", search_word);
		return sqlSession.selectList("mybatis.board.boardSearchListascJson", map);
	}
	
	// 게시글 수정
	public int boardUpdateJson(BoardDTO boardDTO) {
		return sqlSession.update("mybatis.board.boardUpdateJson", boardDTO);
	}
	// 게시글 조회수 증가
	public int boardUpdateHitJson(int board_num) {
		return sqlSession.update("mybatis.board.boardUpdateHitJson", board_num);
	}
	// 게시글 삭제
	public int boardDeleteJson(int board_num, int member_num) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("board_num", board_num);
		map.put("member_num", member_num);
		return sqlSession.delete("mybatis.board.boardDeleteJson", map);
	}
	
	// 댓글 작성
	public int boardReplyWriteJson(ReplyDTO replydto) {
		return sqlSession.insert("mybatis.board.boardReplyWriteJson", replydto);
	}
	// 댓글 수정
	public int boardReplyUpdateJson(ReplyDTO replydto) {
		return sqlSession.update("mybatis.board.boardReplyUpdateJson", replydto);
	}
	// 댓글 총 갯수
	public int boardReplyListTotalJson(int board_num) {
		return sqlSession.selectOne("mybatis.board.boardReplyListTotalJson", board_num);
	}
	// 댓글 리스트
	public List<ReplyDTO> boardReplyListJson(int board_num, int startNum, int endNum) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		map.put("board_num", board_num);
		return sqlSession.selectList("mybatis.board.boardReplyListJson", map);
	}
	// 댓글 삭제
	public int boardReplyDeleteJson(ReplyDTO replydto) {
		return sqlSession.delete("mybatis.board.boardReplyDeleteJson", replydto);
	}
	
	// 내가 쓴 글 내림 차순(최신이 맨위에)
	public List<BoardDTO> myboardListdescJson(int member_num, int startNum, int endNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_num", member_num);
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		return sqlSession.selectList("mybatis.board.myboardListdescJson", map);
	}
	// 내가 쓴 글 오름 차순(최신이 맨 아래)
	public List<BoardDTO> myboardListascJson(int member_num, int startNum, int endNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member_num", member_num);
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		return sqlSession.selectList("mybatis.board.myboardListascJson", map);
	}
	// 내가 쓴 글 전체 갯수
	public int myboardListTotalJson(int member_num) {
		return sqlSession.selectOne("mybatis.board.myboardListTotalJson", member_num);
	}
	
	
	// 추천수 확인
	public int btLikeCheckJson(ReplyDTO replyDTO) {
		return sqlSession.selectOne("mybatis.board.btLikeCheckJson", replyDTO);
	}
	// 추천 테이블 데이터 작성
	public int btLikeWriteJson(ReplyDTO replyDTO) {
		return sqlSession.insert("mybatis.board.btLikeWriteJson", replyDTO);
	}
	// 추천 테이블 데이터 제거
	public int btLikeDeleteJson(ReplyDTO replyDTO) {
		return sqlSession.insert("mybatis.board.btLikeDeleteJson", replyDTO);
	}
	// 게시판 데이터 추천 증감
	public int boardLikeUpdateJson(int board_num, int like_num) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("board_num", board_num);
		map.put("like_num", like_num);
		return sqlSession.update("mybatis.board.boardLikeUpdateJson", map);
	}
	// 댓글 데이터 추천 증감
	public int boardReplyLikeUpdateJson(int reply_num, int like_num) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("reply_num", reply_num);
		map.put("like_num", like_num);
		return sqlSession.update("mybatis.board.boardReplyLikeUpdateJson", map);
	}
	// 게시글 데이터 추천값  받아오기
	public int boardLikeViewJson(ReplyDTO replyDTO) {
		return sqlSession.selectOne("mybatis.board.boardLikeViewJson", replyDTO);
	}
	// 댓글 데이터 추천값  받아오기
	public int replyLikeViewJson(ReplyDTO replyDTO) {
		return sqlSession.selectOne("mybatis.board.replyLikeViewJson", replyDTO);
	}
	
	
	// 인기글 리스트
	public List<BoardDTO> boardLikeListJson(int startNum, int endNum, String board_set) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		map.put("board_set", board_set);
		return sqlSession.selectList("mybatis.board.boardLikeListJson", map);
	}
}