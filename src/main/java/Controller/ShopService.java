package Controller;

import java.util.List;

import Model.ShopDTO;
import Model.ShopevDTO;

public interface ShopService {

	int shopWriteJson(ShopDTO shopDTO);
	int shopUpdateJson(ShopDTO shopDTO);
	ShopDTO shopViewJson(int shop_index);
	int shopListTotalJson();
	int shopListTypeTotalJson(String shop_type);
	List<ShopDTO> shopListdescJson(int startNum, int endNum);
	List<ShopDTO> shopListTypedescJson(int startNum, int endNum, String shop_type);
	List<ShopDTO> shopListascJson(int startNum, int endNum);
	List<ShopDTO> shopListTypeascJson(int startNum, int endNum, String shop_type);
	List<ShopDTO> shopListScoreJson(int startNum, int endNum);
	List<ShopDTO> shopListTypeScoreJson(int startNum, int endNum, String shop_type);
	int shopUpdateScoreJson(double score, int shop_index, double shop_grade);
	int shopDeleteJson(int shop_index);
	
	// 검색
	List<ShopDTO> shopListSearchdescJson(int startNum, int endNum, String search_word);
	List<ShopDTO> shopListSearchascJson(int startNum, int endNum, String search_word);
	int shopListSearchTotalJson(String search_word);
	
	// 점수 주기
	int shopevWriteJson(ShopevDTO shopevDTO);
	int shopevViewJson(int shop_index, int member_num);
	String shopevScoreViewJson(int shop_index, int member_num);
	
	
}
