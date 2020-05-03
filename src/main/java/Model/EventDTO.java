package Model;

public class EventDTO {
	private int event_index;
	private int isnew;
	private String event_subject1;
	private String event_subject2;
	private String event_content;
	private String event_date;
	private String event_origin_img;
	private String event_uuid_img;
	private String event_type;
	
	
	public String getEvent_type() {
		return event_type;
	}
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
	public int getEvent_index() {
		return event_index;
	}
	public void setEvent_index(int event_index) {
		this.event_index = event_index;
	}
	public int getIsnew() {
		return isnew;
	}
	public void setIsnew(int isnew) {
		this.isnew = isnew;
	}
	public String getEvent_subject1() {
		return event_subject1;
	}
	public void setEvent_subject1(String event_subject1) {
		this.event_subject1 = event_subject1;
	}
	public String getEvent_subject2() {
		return event_subject2;
	}
	public void setEvent_subject2(String event_subject2) {
		this.event_subject2 = event_subject2;
	}
	public String getEvent_content() {
		return event_content;
	}
	public void setEvent_content(String event_content) {
		this.event_content = event_content;
	}
	public String getEvent_date() {
		return event_date;
	}
	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}
	public String getEvent_origin_img() {
		return event_origin_img;
	}
	public void setEvent_origin_img(String event_origin_img) {
		this.event_origin_img = event_origin_img;
	}
	public String getEvent_uuid_img() {
		return event_uuid_img;
	}
	public void setEvent_uuid_img(String event_uuid_img) {
		this.event_uuid_img = event_uuid_img;
	}
	
	
	
}
