package Controller;

import java.util.List;

import Model.OccasionCashDTO;
import Model.OccasionCashGroupDTO;

public interface OccasionService {
	
	int occasionCashWriteJson(OccasionCashDTO occasionCashDTO);
	int occasionCashUpdateJson(OccasionCashDTO occasionCashDTO);
	List<OccasionCashDTO> occasionCashListJson(int startNum, int endNum, int member_num);
	List<OccasionCashDTO> occasionCashMoneyListJson(int startNum, int endNum, int member_num);
	int occasionCashTotalMoneyJson(int member_num);
	OccasionCashDTO occasionCashViewJson(int occ_cash_num);
	int occasionCashDeleteJson(int occ_cash_num);
	int occasionCashListTotalJson(int member_num);
	
	int occasionCashGroupWriteJson(OccasionCashGroupDTO occasionCashGroupDTO);
	int occasionCashGroupUpdateJson(OccasionCashGroupDTO occasionCashGroupDTO);
	OccasionCashGroupDTO occasionCashGroupViewJson(int member_num);
	int occasionCashGroupDeleteJson(int member_num);

}
