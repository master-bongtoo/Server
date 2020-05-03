package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Model.EventDTO;

@Repository
public class EventDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	// event 등록
	public int eventWriteJson(EventDTO eventDTO) {
		return sqlSession.insert("mybatis.event.eventWriteJson", eventDTO);
	}
	
	// event 수정
	public int eventUpdateJson(EventDTO eventDTO) {
		return sqlSession.update("mybatis.event.eventUpdateJson", eventDTO);
	}
	
	// event 리스트
	public List<EventDTO> eventListJson(int event_type, int startNum, int endNum) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("event_type", event_type);
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		return sqlSession.selectList("mybatis.event.eventListJson", map);
	}	
	
	// event 리스트 토탈
	public int eventListTotalJson() {
		return sqlSession.selectOne("mybatis.event.eventListTotalJson");
	}
	
	// event FAQ 리스트
	public List<EventDTO> eventFAQListJson(int event_type, String event_subject2, int startNum, int endNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("event_type", event_type);
		map.put("event_subject2", event_subject2);
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		return sqlSession.selectList("mybatis.event.eventFAQListJson", map);
	}
	
	// event FAQ 토탈
	public int eventFAQListTotalJson(int event_type, String event_subject2) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("event_type", event_type);
		map.put("event_subject2", event_subject2);
		return sqlSession.selectOne("mybatis.event.eventFAQListTotalJson", map);
	}
	
	// event 검색 리스트
	public List<EventDTO> eventSearchListJson(String search_word, int startNum, int endNum) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search_word", search_word);
		map.put("startNum", startNum);
		map.put("endNum", endNum);
		return sqlSession.selectList("mybatis.event.eventSearchListJson", map);
	}
	
	// event 검색 토탈
	public int eventSearchListTotalJson(String search_word) {
		return sqlSession.selectOne("mybatis.event.eventSearchListTotalJson", search_word);
	}
	
	// event 상세보기
	public EventDTO eventViewJson(int event_index) {
		return sqlSession.selectOne("mybatis.event.eventViewJson", event_index);
	}
	
	// event 삭제
	public int eventDeleteJson(int event_index) {
		return sqlSession.delete("mybatis.event.eventDeleteJson", event_index);
	}

	
}
