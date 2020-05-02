package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Model.ShopDTO;
import Model.ShopevDTO;

@Repository
public class ShopDAO {

	@Autowired
	SqlSessionTemplate sqlSession;

	// 샵 작성
	public int shopWriteJson(ShopDTO shopDTO) {
		return sqlSession.insert("mybatis.shop.shopWriteJson", shopDTO);
	}

	// 샵 수정
	public int shopUpdateJson(ShopDTO shopDTO) {
		return sqlSession.update("mybatis.shop.shopUpdateJson", shopDTO);
	}
	
	// 샵 상세보기
	public ShopDTO shopViewJson(int shop_index) {
		return sqlSession.selectOne("mybatis.shop.shopViewJson", shop_index);
	}
	
	// 샵 전체 총 갯수
	public int shopListTotalJson() {
		return sqlSession.selectOne("mybatis.shop.shopListTotalJson");
	}
	// 샵 타입 총 갯수
	public int shopListTypeTotalJson(String shop_type) {
		return sqlSession.selectOne("mybatis.shop.shopListTypeTotalJson", shop_type);
	}
	
	// 샵 전체 리스트 desc(내림차순)
	public List<ShopDTO> shopListdescJson(int startNum, int endNum) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		return sqlSession.selectList("mybatis.shop.shopListdescJson", map);
	}
	
	// 샵 전체 리스트 asc(오름차순)
	public List<ShopDTO> shopListascJson(int startNum, int endNum) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		return sqlSession.selectList("mybatis.shop.shopListascJson", map);
	}
	
	// 샵 타입 리스트 desc(내림차순)
	public List<ShopDTO> shopListTypedescJson(int startNum, int endNum, String shop_type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		map.put("shop_type", shop_type);
		return sqlSession.selectList("mybatis.shop.shopListTypedescJson", map);
	}
	
	// 샵 타입 리스트 asc(내림차순)
	public List<ShopDTO> shopListTypeascJson(int startNum, int endNum, String shop_type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		map.put("shop_type", shop_type);
		return sqlSession.selectList("mybatis.shop.shopListTypeascJson", map);
	}
	
	// 샵 전체 검색 리스트 desc(내림차순)
	public List<ShopDTO> shopListSearchdescJson(int startNum, int endNum, String search_word) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		map.put("search_word", search_word);
		return sqlSession.selectList("mybatis.shop.shopListSearchdescJson", map);
	}
	
	// 샵 전체 검색 리스트 asc(오름차순)
	public List<ShopDTO> shopListSearchascJson(int startNum, int endNum, String search_word) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		map.put("search_word", search_word);
		return sqlSession.selectList("mybatis.shop.shopListSearchascJson", map);
	}
	
	// 샵 전체 검색 갯수
	public int shopListSearchTotalJson(String search_word) {
		return sqlSession.selectOne("mybatis.shop.shopListSearchTotalJson", search_word);
	}
	
	// 샵 전체 스코어 순 리스트
	public List<ShopDTO> shopListScoreJson(int startNum, int endNum) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		return sqlSession.selectList("mybatis.shop.shopListScoreJson", map);
	}
	
	// 샵 타입 스코어 순 리스트
	public List<ShopDTO> shopListTypeScoreJson(int startNum, int endNum, String shop_type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		map.put("shop_type", shop_type);
		return sqlSession.selectList("mybatis.shop.shopListTypeScoreJson", map);
	}
	
	// 샵 점수 추가
	public int shopUpdateScoreJson(double score, int shop_index, double shop_grade) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("score", score);
		map.put("shop_index", shop_index);
		map.put("shop_grade", shop_grade);
		return sqlSession.update("mybatis.shop.shopUpdateScoreJson", map);
	}
	// 샵 삭제
	public int shopDeleteJson(int shop_index) {
		return sqlSession.delete("mybatis.shop.shopDeleteJson", shop_index);
	}

	
	// 샵 추천 데이터 추가
	public int shopevWriteJson(ShopevDTO shopevDTO) {
		return sqlSession.insert("mybatis.shop.shopevWriteJson", shopevDTO);
	}
	
	// 샵 추천 데이터 존재여부 확인
	public int shopevViewJson(int shop_index, int member_num) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("member_num", member_num);
		map.put("shop_index", shop_index);
		return sqlSession.selectOne("mybatis.shop.shopevViewJson", map);
	}
	
	// 샵 맴버추천여부 확인
	public String shopevScoreViewJson(int shop_index, int member_num) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("member_num", member_num);
		map.put("shop_index", shop_index);
		return sqlSession.selectOne("mybatis.shop.shopevScoreViewJson", map);
	}
}
