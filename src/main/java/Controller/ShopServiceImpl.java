package Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Model.ShopDTO;
import Model.ShopevDTO;

@Service
public class ShopServiceImpl implements ShopService{

	@Autowired
	ShopDAO shopDAO;
	
	@Override
	public int shopWriteJson(ShopDTO shopDTO) {
		return shopDAO.shopWriteJson(shopDTO);
	}

	@Override
	public int shopUpdateJson(ShopDTO shopDTO) {
		return shopDAO.shopUpdateJson(shopDTO);
	}

	@Override
	public ShopDTO shopViewJson(int shop_index) {
		return shopDAO.shopViewJson(shop_index);
	}

	@Override
	public int shopListTotalJson() {
		return shopDAO.shopListTotalJson();
	}

	@Override
	public int shopListTypeTotalJson(String shop_type) {
		return shopDAO.shopListTypeTotalJson(shop_type);
	}

	@Override
	public List<ShopDTO> shopListdescJson(int startNum, int endNum) {
		return shopDAO.shopListdescJson(startNum, endNum);
	}

	@Override
	public List<ShopDTO> shopListTypedescJson(int startNum, int endNum, String shop_type) {
		return shopDAO.shopListTypedescJson(startNum, endNum, shop_type);
	}

	@Override
	public List<ShopDTO> shopListScoreJson(int startNum, int endNum) {
		return shopDAO.shopListScoreJson(startNum, endNum);
	}

	@Override
	public List<ShopDTO> shopListTypeScoreJson(int startNum, int endNum, String shop_type) {
		return shopDAO.shopListTypeScoreJson(startNum, endNum, shop_type);
	}

	@Override
	public int shopUpdateScoreJson(double score, int shop_index, double shop_grade) {
		return shopDAO.shopUpdateScoreJson(score, shop_index, shop_grade);
	}

	@Override
	public int shopDeleteJson(int shop_index) {
		return shopDAO.shopDeleteJson(shop_index);
	}

	@Override
	public int shopevWriteJson(ShopevDTO shopevDTO) {
		return shopDAO.shopevWriteJson(shopevDTO);
	}

	@Override
	public int shopevViewJson(int shop_index, int member_num) {
		return shopDAO.shopevViewJson(shop_index, member_num);
	}

	@Override
	public List<ShopDTO> shopListascJson(int startNum, int endNum) {
		return shopDAO.shopListascJson(startNum, endNum);
	}

	@Override
	public List<ShopDTO> shopListTypeascJson(int startNum, int endNum, String shop_type) {
		return shopDAO.shopListTypeascJson(startNum, endNum, shop_type);
	}

	@Override
	public List<ShopDTO> shopListSearchdescJson(int startNum, int endNum, String search_word) {
		return shopDAO.shopListSearchdescJson(startNum, endNum, search_word);
	}

	@Override
	public List<ShopDTO> shopListSearchascJson(int startNum, int endNum, String search_word) {
		return shopDAO.shopListSearchascJson(startNum, endNum, search_word);
	}

	@Override
	public String shopevScoreViewJson(int shop_index, int member_num) {
		return shopDAO.shopevScoreViewJson(shop_index, member_num);
	}

	@Override
	public int shopListSearchTotalJson(String search_word) {
		return shopDAO.shopListSearchTotalJson(search_word);
	}

}
