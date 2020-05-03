package Controller;

import java.util.List;

import Model.EventDTO;

public interface EventService {
	int eventWriteJson(EventDTO eventDTO);
	int eventUpdateJson(EventDTO eventDTO);
	List<EventDTO> eventListJson(int event_type, int startNum, int endNum);
	int eventListTotalJson();
	EventDTO eventViewJson(int event_index);
	int eventDeleteJson(int event_index);
	List<EventDTO> eventFAQListJson(int event_type, String event_subject2, int startNum, int endNum);
	int eventFAQListTotalJson(int event_type, String event_subject2);
	List<EventDTO> eventSearchListJson(String search_word, int startNum, int endNum);
	int eventSearchListTotalJson(String search_word);
}
