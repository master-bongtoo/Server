package Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Model.OccasionCashDTO;
import Model.OccasionCashGroupDTO;

@Service
public class OccasionServiceImpl implements OccasionService{

	@Autowired
	OccasionCashDAO occasionCashDAO;

	@Override
	public int occasionCashWriteJson(OccasionCashDTO occasionCashDTO) {
		return occasionCashDAO.occasionCashWriteJson(occasionCashDTO);
	}

	@Override
	public int occasionCashUpdateJson(OccasionCashDTO occasionCashDTO) {
		return occasionCashDAO.occasionCashUpdateJson(occasionCashDTO);
	}

	@Override
	public List<OccasionCashDTO> occasionCashListJson(int startNum, int endNum, int member_num) {
		return occasionCashDAO.occasionCashListJson(startNum, endNum, member_num);
	}
	
	@Override
	public List<OccasionCashDTO> occasionCashMoneyListJson(int startNum, int endNum, int member_num) {
		return occasionCashDAO.occasionCashMoneyListJson(startNum, endNum, member_num);
	}

	@Override
	public OccasionCashDTO occasionCashViewJson(int occ_cash_num) {
		return occasionCashDAO.occasionCashViewJson(occ_cash_num);
	}

	@Override
	public int occasionCashDeleteJson(int occ_cash_num) {
		return occasionCashDAO.occasionCashDeleteJson(occ_cash_num);
	}

	@Override
	public int occasionCashListTotalJson(int member_num) {
		return occasionCashDAO.occasionCashListTotalJson(member_num);
	}

	@Override
	public int occasionCashGroupWriteJson(OccasionCashGroupDTO occasionCashGroupDTO) {
		return occasionCashDAO.occasionCashGroupWriteJson(occasionCashGroupDTO);
	}

	@Override
	public int occasionCashGroupUpdateJson(OccasionCashGroupDTO occasionCashGroupDTO) {
		return occasionCashDAO.occasionCashGroupUpdateJson(occasionCashGroupDTO);
	}

	@Override
	public OccasionCashGroupDTO occasionCashGroupViewJson(int member_num) {
		return occasionCashDAO.occasionCashGroupViewJson(member_num);
	}

	@Override
	public int occasionCashGroupDeleteJson(int member_num) {
		return occasionCashDAO.occasionCashGroupDeleteJson(member_num);
	}

	@Override
	public int occasionCashTotalMoneyJson(int member_num) {
		return occasionCashDAO.occasionCashTotalMoneyJson(member_num);
	}
	
	
	
}
