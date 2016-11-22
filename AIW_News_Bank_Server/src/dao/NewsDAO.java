package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.News;

public class NewsDAO extends AbstractDAO {

	public NewsDAO() {
		super();
	}
	
	public List<News> findAll() {
		String query = "SELECT * FROM news";
		List<News> list_news = new ArrayList<News>();
		ResultSet rs = retrieveData(query);
		try {
			while(rs.next()) {
				News n = new News();
				
				n.setId(rs.getInt(1));
				n.setTitle(rs.getString(2));
				n.setIntro(rs.getString(3));
				n.setContent(rs.getString(4));
				n.setDate_created(rs.getTimestamp(5));
				n.setAuthor(rs.getString(6));
				n.setCatid_id(rs.getInt(7));
				n.setTags(rs.getString(8));
				
				list_news.add(n);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list_news;
	}
	
	public List<News> findById(String id) {
		String query = "SELECT * FROM news WHERE id = ?";
		List<News> list_news = new ArrayList<News>();
		ResultSet rs = retrieveDataUsingFields(query, id);
		try {
			while (rs.next()) {
				News n = new News();
				
				n.setId(rs.getInt(1));
				n.setTitle(rs.getString(2));
				n.setIntro(rs.getString(3));
				n.setContent(rs.getString(4));
				n.setDate_created(rs.getTimestamp(5));
				n.setAuthor(rs.getString(6));
				n.setCatid_id(rs.getInt(7));
				n.setTags(rs.getString(8));
				
				list_news.add(n);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list_news;
	}
	
	public List<News> findByCatid(String catid_name) {
		String query = "SELECT * FROM news AS n INNER JOIN categories AS c ON n.category_id = c.id WHERE c.catid_search_name = ?";
		List<News> list_news = new ArrayList<News>();
		ResultSet rs = retrieveDataUsingFields(query, catid_name);
		try {
			while(rs.next()) {
				News n = new News();
				
				n.setId(rs.getInt(1));
				n.setTitle(rs.getString(2));
				n.setIntro(rs.getString(3));
				n.setContent(rs.getString(4));
				n.setDate_created(rs.getTimestamp(5));
				n.setAuthor(rs.getString(6));
				n.setCatid_id(rs.getInt(7));
				n.setTags(rs.getString(8));
				
				list_news.add(n);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list_news;
	}
	
	public List<News> findByTag(String tag_name) {
		String query = "SELECT * FROM news AS n WHERE n.tags LIKE ?";
		List<News> list_news = new ArrayList<News>();
		ResultSet rs = retrieveDataUsingFieldsLIKE(query, tag_name);
		try {
			while(rs.next()) {
				News n = new News();
				
				n.setId(rs.getInt(1));
				n.setTitle(rs.getString(2));
				n.setIntro(rs.getString(3));
				n.setContent(rs.getString(4));
				n.setDate_created(rs.getTimestamp(5));
				n.setAuthor(rs.getString(6));
				n.setCatid_id(rs.getInt(7));
				n.setTags(rs.getString(8));
				
				list_news.add(n);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list_news;
	}
	
	public List<News> getRandomRelatedNewsByCatid(String catid_id) {
		String query = "SELECT * FROM news AS n WHERE n.category_id = ? ORDER BY RAND() LIMIT 3";
		List<News> list_related_news = new ArrayList<News>();
		ResultSet rs = retrieveDataUsingFields(query, catid_id);
		try {
			while(rs.next()) {
				News n = new News();
				
				n.setId(rs.getInt(1));
				n.setTitle(rs.getString(2));
				n.setIntro(rs.getString(3));
				n.setContent(rs.getString(4));
				n.setDate_created(rs.getTimestamp(5));
				n.setAuthor(rs.getString(6));
				n.setCatid_id(rs.getInt(7));
				n.setTags(rs.getString(8));
				
				list_related_news.add(n);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list_related_news;
	}
	
	public static void main(String[] args) {
		NewsDAO nd = new NewsDAO();
		nd.findAll();
	}

}
