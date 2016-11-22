package entity;

public class Category {

	private int id;
	private String name;
	private String serch_name;

	public Category() {
		// TODO Auto-generated constructor stub
	}

	public Category(int id, String name, String serch_name) {
		super();
		this.id = id;
		this.name = name;
		this.serch_name = serch_name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerch_name() {
		return serch_name;
	}

	public void setSerch_name(String serch_name) {
		this.serch_name = serch_name;
	}
	
	

}
