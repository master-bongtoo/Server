package Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Model.QuestionDTO;

@Service
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	QuestionDAO questionDAO;
	
	@Override
	public int questionWriteJson(QuestionDTO questionDTO) {
		return questionDAO.questionWriteJson(questionDTO);
	}

	@Override
	public int questionUpdateJson(QuestionDTO questionDTO) {
		return questionDAO.questionUpdateJson(questionDTO);
	}

	@Override
	public List<QuestionDTO> questionListJson(int question_type, int startNum, int endNum) {
		return questionDAO.questionListJson(question_type, startNum, endNum);
	}

	@Override
	public QuestionDTO questionViewJson(int question_index) {
		return questionDAO.questionViewJson(question_index);
	}

	@Override
	public int questionDeleteJson(int question_index) {
		return questionDAO.questionDeleteJson(question_index);
	}
	
	@Override
	public int questionListTotalJson(int question_type) {
		return questionDAO.questionListTotalJson(question_type);
	}

}
