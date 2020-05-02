package Model;

public class ReplyDTO {
	private int member_num;
	private int grade;
	private int board_num;
	private int reply_num;
	private String reply_description;
	private String nickname;
	private int reply_like;
	private String reply_first_date;
	private String reply_edit_date;
	
	public String getReply_first_date() {
		return reply_first_date;
	}
	public void setReply_first_date(String reply_first_date) {
		this.reply_first_date = reply_first_date;
	}
	public String getReply_edit_date() {
		return reply_edit_date;
	}
	public void setReply_edit_date(String reply_edit_date) {
		this.reply_edit_date = reply_edit_date;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getMember_num() {
		return member_num;
	}
	public void setMember_num(int member_num) {
		this.member_num = member_num;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public int getReply_num() {
		return reply_num;
	}
	public void setReply_num(int reply_num) {
		this.reply_num = reply_num;
	}
	public String getReply_description() {
		return reply_description;
	}
	public void setReply_description(String reply_description) {
		this.reply_description = reply_description;
	}
	public int getReply_like() {
		return reply_like;
	}
	public void setReply_like(int reply_like) {
		this.reply_like = reply_like;
	}
	
	
}
