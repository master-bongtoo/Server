package Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Model.BoardDTO;
import Model.ReplyDTO;


@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boarddao;

	@Override
	public int boardWriteJson(BoardDTO boardDTO) {
		return boarddao.boardWriteJson(boardDTO);
	}

	@Override
	public BoardDTO boardViewJson(int board_num, int member_num) {
		return boarddao.boardViewJson(board_num, member_num);
	}

	@Override
	public int boardUpdateJson(BoardDTO boardDTO) {
		return boarddao.boardUpdateJson(boardDTO);
	}

	@Override
	public int boardUpdateHitJson(int board_num) {
		return boarddao.boardUpdateHitJson(board_num);
	}

	@Override
	public int boardDeleteJson(int board_num, int member_num) {
		return boarddao.boardDeleteJson(board_num, member_num);
	}

	@Override
	public int boardListCategoryTotalJson(String board_category) {
		return boarddao.boardListCategoryTotalJson(board_category);
	}

	@Override
	public int boardListTotalJson() {
		return boarddao.boardListTotalJson();
	}

	@Override
	public List<BoardDTO> boardListdescJson(int startNum, int endNum) {
		return boarddao.boardListdescJson(startNum, endNum);
	}
	
	@Override
	public List<BoardDTO> boardListascJson(int startNum, int endNum) {
		return boarddao.boardListascJson(startNum, endNum);
	}

	@Override
	public List<BoardDTO> boardListCategorydescJson(int startNum, int endNum, String board_category) {
		return boarddao.boardListCategorydescJson(startNum, endNum, board_category);
	}
	
	@Override
	public List<BoardDTO> boardListCategoryascJson(int startNum, int endNum, String board_category) {
		return boarddao.boardListCategoryascJson(startNum, endNum, board_category);
	}

	@Override
	public int boardReplyWriteJson(ReplyDTO replyDTO) {
		return boarddao.boardReplyWriteJson(replyDTO);
	}

	@Override
	public int boardReplyUpdateJson(ReplyDTO replyDTO) {
		return boarddao.boardReplyUpdateJson(replyDTO);
	}

	@Override
	public List<ReplyDTO> boardReplyListJson(int board_num, int startNum, int endNum) {
		return boarddao.boardReplyListJson(board_num, startNum, endNum);
	}

	@Override
	public int boardReplyDeleteJson(ReplyDTO replyDTO) {
		return boarddao.boardReplyDeleteJson(replyDTO);
	}

	@Override
	public int boardReplyListTotalJson(int board_num) {
		return boarddao.boardReplyListTotalJson(board_num);
	}

	@Override
	public List<BoardDTO> myboardListdescJson(int member_num, int startNum, int endNum) {
		return boarddao.myboardListdescJson(member_num, startNum, endNum);
	}
	
	@Override
	public List<BoardDTO> myboardListascJson(int member_num, int startNum, int endNum) {
		return boarddao.myboardListascJson(member_num, startNum, endNum);
	}

	@Override
	public int myboardListTotalJson(int member_num) {
		return boarddao.myboardListTotalJson(member_num);
	}

	@Override
	public int boardSearchListTotalJson(String search_word) {
		return boarddao.boardSearchListTotalJson(search_word);
	}

	@Override
	public List<BoardDTO> boardSearchListdescJson(int startNum, int endNum, String search_word) {
		return boarddao.boardSearchListdescJson(startNum, endNum, search_word);
	}

	@Override
	public List<BoardDTO> boardSearchListascJson(int startNum, int endNum, String search_word) {
		return boarddao.boardSearchListascJson(startNum, endNum, search_word);
	}
	
	
	@Override
	public int btLikeCheckJson(ReplyDTO replyDTO) {
		return boarddao.btLikeCheckJson(replyDTO);
	}

	@Override
	public int btLikeWriteJson(ReplyDTO replyDTO) {
		return boarddao.btLikeWriteJson(replyDTO);
	}

	@Override
	public int btLikeDeleteJson(ReplyDTO replyDTO) {
		return boarddao.btLikeDeleteJson(replyDTO);
	}

	@Override
	public int boardLikeUpdateJson(int board_num, int like_num) {
		return boarddao.boardLikeUpdateJson(board_num, like_num);
	}

	@Override
	public int boardReplyLikeUpdateJson(int reply_num, int like_num) {
		return boarddao.boardReplyLikeUpdateJson(reply_num, like_num);
	}

	@Override
	public List<BoardDTO> boardLikeListJson(int startNum, int endNum, String board_set) {
		return boarddao.boardLikeListJson(startNum, endNum, board_set);
	}

	@Override
	public int replyLikeViewJson(ReplyDTO replyDTO) {
		return boarddao.replyLikeViewJson(replyDTO);
	}

	@Override
	public int boardLikeViewJson(ReplyDTO replyDTO) {
		return boarddao.boardLikeViewJson(replyDTO);
	}

}
