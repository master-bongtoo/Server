package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Model.QuestionDTO;

@Repository
public class QuestionDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	// question 등록
	public int questionWriteJson(QuestionDTO questionDTO) {
		return sqlSession.insert("mybatis.question.questionWriteJson", questionDTO);
	}

	// question 수정
	public int questionUpdateJson(QuestionDTO questionDTO) {
		return sqlSession.update("mybatis.question.questionUpdateJson", questionDTO);
	}

	// question 리스트
	public List<QuestionDTO> questionListJson(int question_type, int startNum, int endNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("event_type", question_type);
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		return sqlSession.selectList("mybatis.question.questionListJson", map);
	}

	// question 상세보기
	public QuestionDTO questionViewJson(int question_index) {
		return sqlSession.selectOne("mybatis.question.questionViewJson", question_index);
	}

	// question 삭제
	public int questionDeleteJson(int question_index) {
		return sqlSession.delete("mybatis.question.questionDeleteJson", question_index);
	}
	
	// question 리스트 토탈
	public int questionListTotalJson(int question_type) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		return sqlSession.selectOne("mybatis.question.questiontListTotalJson", question_type);
	}
}
