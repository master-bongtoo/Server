package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Model.AlimiDTO;
import Model.AlimiTagDTO;

@Repository
public class AlimiDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	// alimi 등록
	public int alimiWriteJson(AlimiDTO alimiDTO) {
		return sqlSession.insert("mybatis.alimi.alimiWriteJson", alimiDTO);
	}

	// alimi 수정
	public int alimiUpdateJson(AlimiDTO alimiDTO) {
		return sqlSession.update("mybatis.alimi.alimiUpdateJson", alimiDTO);
	}
	
//	// alimi 리스트 총 갯수(기존 태그 전체 검색 갯수)
//	public int alimiListTotalJson(int member_num) {
//		return sqlSession.selectOne("mybatis.alimi.alimiListTotalJson", member_num);
//	}
//
//	// alimi 리스트(기존 태그 전체 검색 목록)
//	public List<AlimiDTO> alimiListJson(int member_num, int startNum, int endNum) {
//		Map<String, Integer> map = new HashMap<String, Integer>();
//		map.put("member_num", member_num);
//		map.put("startNum", startNum);
//		map.put("endNum", endNum);
//		return sqlSession.selectList("mybatis.alimi.alimiListJson", map);
//	}
	
	// alimi 리스트 총 갯수
	public int alimiListTotalJson(String alimi_tag) {
		return sqlSession.selectOne("mybatis.alimi.alimiListTotalJson", alimi_tag);
	}

	// alimi 리스트
	public List<AlimiDTO> alimiListJson(String alimi_tag, int startNum, int endNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("alimi_tag", alimi_tag);
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		return sqlSession.selectList("mybatis.alimi.alimiListJson", map);
	}

	// alimi 상세보기
	public AlimiDTO alimiViewJson(int member_num) {
		return sqlSession.selectOne("mybatis.alimi.alimiViewJson", member_num);
	}

	// alimi 삭제
	public int alimiDeleteJson(AlimiDTO alimiDTO) {
		return sqlSession.delete("mybatis.alimi.alimiDeleteJson", alimiDTO);
	}

	
	// alimitag 등록
	public int alimiTagWriteJson(AlimiTagDTO alimiTagDTO) {
		return sqlSession.insert("mybatis.alimi.alimiTagWriteJson", alimiTagDTO);
	}

	// alimitag 수정
	public int alimiTagUpdateJson(AlimiTagDTO alimiTagDTO) {
		return sqlSession.update("mybatis.alimi.alimiTagUpdateJson", alimiTagDTO);
	}

	// alimitag 상세보기
	public AlimiTagDTO alimiTagViewJson(int member_num) {
		return sqlSession.selectOne("mybatis.alimi.alimiTagViewJson", member_num);
	}

	// alimitag 삭제
	public int alimiTagDeleteJson(int member_num) {
		return sqlSession.delete("mybatis.alimi.alimiTagDeleteJson", member_num);
	}

}
