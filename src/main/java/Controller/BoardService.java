package Controller;

import java.util.List;

import Model.BoardDTO;
import Model.ReplyDTO;

public interface BoardService {
	int boardWriteJson(BoardDTO boardDTO);
	BoardDTO boardViewJson(int board_num, int member_num);
	int boardListCategoryTotalJson(String board_category);
	int boardListTotalJson();
	int boardSearchListTotalJson(String search_word);
	List<BoardDTO> boardListdescJson(int startNum, int endNum);
	List<BoardDTO> boardListascJson(int startNum, int endNum);
	List<BoardDTO> boardListCategorydescJson(int startNum, int endNum, String board_category);
	List<BoardDTO> boardListCategoryascJson(int startNum, int endNum, String board_category);
	List<BoardDTO> boardSearchListdescJson(int startNum, int endNum, String search_word);
	List<BoardDTO> boardSearchListascJson(int startNum, int endNum, String search_word);
	
	int boardUpdateJson(BoardDTO boardDTO);
	int boardUpdateHitJson(int board_num);
	int boardDeleteJson(int board_num, int member_num);
	
	int boardReplyWriteJson(ReplyDTO replydto);
	int boardReplyUpdateJson(ReplyDTO replydto);
	int boardReplyListTotalJson(int board_num);
	List<ReplyDTO> boardReplyListJson(int board_num, int startNum, int endNum);
	int boardReplyDeleteJson(ReplyDTO replyDTO);
	
	List<BoardDTO> myboardListdescJson(int member_num, int startNum, int endNum);
	List<BoardDTO> myboardListascJson(int member_num, int startNum, int endNum);
	int myboardListTotalJson(int member_num);
	
	int btLikeCheckJson(ReplyDTO replyDTO);
	int btLikeWriteJson(ReplyDTO replyDTO);
	int btLikeDeleteJson(ReplyDTO replyDTO);
	int boardLikeUpdateJson(int board_num, int like_num);
	int boardReplyLikeUpdateJson(int reply_num, int like_num);
	int boardLikeViewJson(ReplyDTO replyDTO);
	int replyLikeViewJson(ReplyDTO replyDTO);
	
	List<BoardDTO> boardLikeListJson(int startNum, int endNum, String board_set);
}
