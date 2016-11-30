package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.Comment;

public class CommentDAO extends AbstractDAO {

	public CommentDAO() {
		super();
	}
	
	public List<Comment> findCommentByNewId(String new_id) {
		String query = "SELECT * FROM comments AS c INNER JOIN news AS n ON c.new_id = n.id WHERE c.new_id = ?";
		List<Comment> list_comments = new ArrayList<Comment>();
		ResultSet rs = retrieveDataUsingFields(query, new_id);
		try {
			while (rs.next()) {
				Comment comment = new Comment();
				
				comment.setId(rs.getInt(1));
				comment.setNew_id(rs.getInt(2));
				comment.setUser_name(rs.getString(3));
				comment.setComment(rs.getString(4));
				comment.setTime_comment(rs.getTimestamp(5));
				
				list_comments.add(comment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list_comments;
	}
	
	public void addComment(int new_id, String user_name, String comment, Timestamp time_comment) {
		String query = "INSERT INTO comments(`new_id`, `user_name`, `comment`, `time_comment`) "
				+ "VALUES(?, ?, ?, ?)";
		insertDataUsingFields(query, String.valueOf(new_id),user_name, comment, time_comment.toString());
		System.out.println("entered DAO");
	}

	/*public static void main(String[] args) {
		CommentDAO cd = new CommentDAO();
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		cd.addComment(3, "@trann", "MU will come back22222", ts);
		System.out.println(".............");
	}*/
	
}
