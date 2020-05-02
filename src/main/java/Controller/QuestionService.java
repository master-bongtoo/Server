package Controller;

import java.util.List;

import Model.QuestionDTO;

public interface QuestionService {
	int questionWriteJson(QuestionDTO questionDTO);
	int questionUpdateJson(QuestionDTO questionDTO);
	List<QuestionDTO> questionListJson(int question_type, int startNum, int endNum);
	QuestionDTO questionViewJson(int question_index);
	int questionDeleteJson(int question_index);
	int questionListTotalJson(int question_type);
}
