package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Category;
import entity.News;

public class CategoryDAO extends AbstractDAO {

	public CategoryDAO() {
		super();
	}
	
	public List<Category> findAll() {
		String query = "SELECT * FROM categories";
		List<Category> list_categories = new ArrayList<Category>();
		ResultSet rs = retrieveData(query);
		try {
			while(rs.next()) {
				Category catid = new Category();
				
				catid.setId(rs.getInt(1));
				catid.setName(rs.getString(2));
				catid.setSerch_name(rs.getString(3));
				
				list_categories.add(catid);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list_categories;
	}

}
