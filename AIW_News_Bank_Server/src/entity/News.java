package entity;

import java.sql.Timestamp;

public class News {

	private int id;
	private String title;
	private String intro;
	private String content;
	private Timestamp date_created;
	private String author;
	private int catid_id;
	private String tags;

	public News() {
		// TODO Auto-generated constructor stub
	}

	public News(int id, String title, String intro, String content, Timestamp date_created, String author,
			int catid_id) {
		super();
		this.id = id;
		this.title = title;
		this.intro = intro;
		this.content = content;
		this.date_created = date_created;
		this.author = author;
		this.catid_id = catid_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getDate_created() {
		return date_created;
	}

	public void setDate_created(Timestamp date_created) {
		this.date_created = date_created;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getCatid_id() {
		return catid_id;
	}

	public void setCatid_id(int catid_id) {
		this.catid_id = catid_id;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
