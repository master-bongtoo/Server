package Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Model.EventDTO;

@Service
public class EventServiceImpl implements EventService{

	@Autowired
	EventDAO eventDAO;
	
	@Override
	public int eventWriteJson(EventDTO eventDTO) {
		return eventDAO.eventWriteJson(eventDTO);
	}

	@Override
	public int eventUpdateJson(EventDTO eventDTO) {
		return eventDAO.eventUpdateJson(eventDTO);
	}

	@Override
	public List<EventDTO> eventListJson(int event_type, int startNum, int endNum) {
		return eventDAO.eventListJson(event_type, startNum, endNum);
	}

	@Override
	public EventDTO eventViewJson(int event_index) {
		return eventDAO.eventViewJson(event_index);
	}

	@Override
	public int eventDeleteJson(int event_index) {
		return eventDAO.eventDeleteJson(event_index);
	}

	@Override
	public List<EventDTO> eventFAQListJson(int event_type, String event_subject2, int startNum, int endNum) {
		return eventDAO.eventFAQListJson(event_type, event_subject2, startNum, endNum);
	}

	@Override
	public List<EventDTO> eventSearchListJson(String search_word, int startNum, int endNum) {
		return eventDAO.eventSearchListJson(search_word, startNum, endNum);
	}

	@Override
	public int eventListTotalJson() {
		return eventDAO.eventListTotalJson();
	}

	@Override
	public int eventFAQListTotalJson(int event_type, String event_subject2) {
		return eventDAO.eventFAQListTotalJson(event_type, event_subject2);
	}

	@Override
	public int eventSearchListTotalJson(String search_word) {
		return eventDAO.eventSearchListTotalJson(search_word);
	}

}
