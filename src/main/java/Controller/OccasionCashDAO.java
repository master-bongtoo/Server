package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Model.OccasionCashDTO;
import Model.OccasionCashGroupDTO;


@Repository
public class OccasionCashDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	// occ 등록
	public int occasionCashWriteJson(OccasionCashDTO occasionCashDTO) {
		return sqlSession.insert("mybatis.occasionCash.occasionCashWriteJson", occasionCashDTO);
	}
	
	// occ 수정
	public int occasionCashUpdateJson(OccasionCashDTO occasionCashDTO) {
		return sqlSession.update("mybatis.occasionCash.occasionCashUpdateJson", occasionCashDTO);
	}
	
	// occ 리스트
	public List<OccasionCashDTO> occasionCashListJson(int startNum, int endNum, int member_num) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		map.put("member_num", member_num);
		return sqlSession.selectList("mybatis.occasionCash.occasionCashListJson", map);
	}
	
	// occ 금액 리스트
	public List<OccasionCashDTO> occasionCashMoneyListJson(int startNum, int endNum, int member_num) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		map.put("member_num", member_num);
		return sqlSession.selectList("mybatis.occasionCash.occasionCashMoneyListJson", map);
	}
	
	// occ 금액 총합
	public int occasionCashTotalMoneyJson(int member_num) {
		return sqlSession.selectOne("mybatis.occasionCash.occasionCashTotalMoneyJson", member_num);
	}
	
	// occ 상세보기
	public OccasionCashDTO occasionCashViewJson(int occ_cash_num) {
		return sqlSession.selectOne("mybatis.occasionCash.occasionCashViewJson", occ_cash_num);
	}
	
	// occ 총 목록
	public int occasionCashListTotalJson(int member_num) {
		return sqlSession.selectOne("mybatis.occasionCash.occasionCashListTotalJson", member_num);
	}
	
	// occ 삭제
	public int occasionCashDeleteJson(int occ_cash_num) {
		return sqlSession.delete("mybatis.occasionCash.occasionCashDeleteJson", occ_cash_num);
	}
	
	// occ그룹 등록
	public int occasionCashGroupWriteJson(OccasionCashGroupDTO occasionCashGroupDTO) {
		return sqlSession.insert("mybatis.occasionCash.occasionCashGroupWriteJson", occasionCashGroupDTO);
	}
	
	// occ그룹 수정
	public int occasionCashGroupUpdateJson(OccasionCashGroupDTO occasionCashGroupDTO) {
		return sqlSession.update("mybatis.occasionCash.occasionCashGroupUpdateJson", occasionCashGroupDTO);
	}
	
	// occ그룹 상세보기
	public OccasionCashGroupDTO occasionCashGroupViewJson(int member_num) {
		return sqlSession.selectOne("mybatis.occasionCash.occasionCashGroupViewJson", member_num);
	}
	
	// occ그룹 삭제
	public int occasionCashGroupDeleteJson(int member_num) {
		return sqlSession.delete("mybatis.occasionCash.occasionCashGroupDeleteJson", member_num);
	}
}
