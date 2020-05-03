package Model;

public class QuestionDTO {
	private int member_num;
	private int question_index;
	private int question_type;
	private int question_check;
	private String question_subject;
	private String question_content;
	private String question_origin_img;
	private String question_uuid_img;
	private String question_phone ;
	private String question_email ;
	
	public int getMember_num() {
		return member_num;
	}
	public void setMember_num(int member_num) {
		this.member_num = member_num;
	}
	public int getQuestion_index() {
		return question_index;
	}
	public void setQuestion_index(int question_index) {
		this.question_index = question_index;
	}
	public int getQuestion_type() {
		return question_type;
	}
	public void setQuestion_type(int question_type) {
		this.question_type = question_type;
	}
	public int getQuestion_check() {
		return question_check;
	}
	public void setQuestion_check(int question_check) {
		this.question_check = question_check;
	}
	public String getQuestion_subject() {
		return question_subject;
	}
	public void setQuestion_subject(String question_subject) {
		this.question_subject = question_subject;
	}
	public String getQuestion_content() {
		return question_content;
	}
	public void setQuestion_content(String question_content) {
		this.question_content = question_content;
	}
	public String getQuestion_origin_img() {
		return question_origin_img;
	}
	public void setQuestion_origin_img(String question_origin_img) {
		this.question_origin_img = question_origin_img;
	}
	public String getQuestion_uuid_img() {
		return question_uuid_img;
	}
	public void setQuestion_uuid_img(String question_uuid_img) {
		this.question_uuid_img = question_uuid_img;
	}
	public String getQuestion_phone() {
		return question_phone;
	}
	public void setQuestion_phone(String question_phone) {
		this.question_phone = question_phone;
	}
	public String getQuestion_email() {
		return question_email;
	}
	public void setQuestion_email(String question_email) {
		this.question_email = question_email;
	}
	
	
}
