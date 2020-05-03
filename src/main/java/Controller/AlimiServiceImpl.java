package Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Model.AlimiDTO;
import Model.AlimiTagDTO;

@Service
public class AlimiServiceImpl implements AlimiService{

	@Autowired
	AlimiDAO alimidao;
	
	@Override
	public int alimiWriteJson(AlimiDTO alimiDTO) {
		return alimidao.alimiWriteJson(alimiDTO);
	}

	@Override
	public int alimiUpdateJson(AlimiDTO alimiDTO) {
		return alimidao.alimiUpdateJson(alimiDTO);
	}

//	@Override
//	public int alimiListTotalJson(int member_num) {
//		return alimidao.alimiListTotalJson(member_num);
//	}
//	
//	@Override
//	public List<AlimiDTO> alimiListJson(int member_num, int startNum, int endNum) {
//		return alimidao.alimiListJson(member_num, startNum, endNum);
//	}
	
	@Override
	public int alimiListTotalJson(String alimi_tag) {
		return alimidao.alimiListTotalJson(alimi_tag);
	}

	@Override
	public List<AlimiDTO> alimiListJson(String alimi_tag, int startNum, int endNum) {
		return alimidao.alimiListJson(alimi_tag, startNum, endNum);
	}
	
	
	@Override
	public AlimiDTO alimiViewJson(int alimi_index) {
		return alimidao.alimiViewJson(alimi_index);
	}

	@Override
	public int alimiDeleteJson(AlimiDTO alimiDTO) {
		return alimidao.alimiDeleteJson(alimiDTO);
	}

	@Override
	public int alimiTagWriteJson(AlimiTagDTO alimiTagDTO) {
		return alimidao.alimiTagWriteJson(alimiTagDTO);
	}

	@Override
	public int alimiTagUpdateJson(AlimiTagDTO alimiTagDTO) {
		return alimidao.alimiTagUpdateJson(alimiTagDTO);
	}

	@Override
	public AlimiTagDTO alimiTagViewJson(int member_num) {
		return alimidao.alimiTagViewJson(member_num);
	}

	@Override
	public int alimiTagDeleteJson(int member_num) {
		return alimidao.alimiTagDeleteJson(member_num);
	}




}
