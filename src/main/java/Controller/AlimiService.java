package Controller;

import java.util.List;

import Model.AlimiDTO;
import Model.AlimiTagDTO;

public interface AlimiService {
	int alimiWriteJson(AlimiDTO alimiDTO);
	int alimiUpdateJson(AlimiDTO alimiDTO);
//	int alimiListTotalJson(int member_num);
//	List<AlimiDTO> alimiListJson(int member_num, int startNum, int endNum);
	int alimiListTotalJson(String alimi_tag);
	List<AlimiDTO> alimiListJson(String alimi_tag, int startNum, int endNum);
	AlimiDTO alimiViewJson(int alimi_index);
	int alimiDeleteJson(AlimiDTO alimiDTO);
	
	int alimiTagWriteJson(AlimiTagDTO alimiTagDTO);
	int alimiTagUpdateJson(AlimiTagDTO alimiTagDTO);
	AlimiTagDTO alimiTagViewJson(int member_num);
	int alimiTagDeleteJson(int member_num);
}
