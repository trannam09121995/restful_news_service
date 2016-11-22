package entity;

import java.sql.Timestamp;

public class Comment {

	private int id;
	private int new_id;
	private String user_name;
	private String comment;
	private Timestamp time_comment;

	public Comment() {
		// TODO Auto-generated constructor stub
	}

	public Comment(int id, int new_id, String user_name, String comment, Timestamp time_comment) {
		super();
		this.id = id;
		this.new_id = new_id;
		this.user_name = user_name;
		this.comment = comment;
		this.time_comment = time_comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNew_id() {
		return new_id;
	}

	public void setNew_id(int new_id) {
		this.new_id = new_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getTime_comment() {
		return time_comment;
	}

	public void setTime_comment(Timestamp time_comment) {
		this.time_comment = time_comment;
	}

}
