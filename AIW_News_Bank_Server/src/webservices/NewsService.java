package webservices;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import dao.CategoryDAO;
import dao.CommentDAO;
import dao.NewsDAO;
import entity.Comment;
import entity.News;

@Path("/news")
public class NewsService {

	// DAOs
	private CategoryDAO categoryDAO = new CategoryDAO();
	private CommentDAO commentDAO = new CommentDAO();
	private NewsDAO newsDAO = new NewsDAO();

	private List<News> list_news = new ArrayList<News>();
	private List<Comment> list_comments = new ArrayList<Comment>();
	
	
	/**
	 * 
	 * @return xml response of all news
	 */
	@GET
	@Path("findAll")
	@Produces("application/xml")
	public StreamingOutput findAllNews() {
		list_news = newsDAO.findAll();
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
				outputNewsList(outputStream, list_news);
			}
		};
	}
	
	@GET
	@Path("findNewById={new_id}")
	@Produces("application/xml")
	public StreamingOutput findNewById(@PathParam("new_id") String new_id) {
		list_news = newsDAO.findById(new_id);
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
				outputNewsList(outputStream, list_news);
			}
		};
	}
	
	
	/**
	 * 
	 * @param catid_name
	 * @return
	 */
	@GET
	@Path("findNewByCatid={catid_name}")
	@Produces("application/xml")
	public StreamingOutput getNewsByCatid(@PathParam("catid_name") String catid_name) {
		list_news = newsDAO.findByCatid(catid_name);
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
				outputNewsList(outputStream, list_news);
			}
		};
	}
	
	/**
	 * 
	 * @param tag_name
	 * @return
	 */
	@GET
	@Path("findNewByTag={tag_name}")
	@Produces("application/xml")
	public StreamingOutput getNewsByTag(@PathParam("tag_name") String tag_name) {
		list_news = newsDAO.findByTag(tag_name);
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
				outputNewsList(outputStream, list_news);
			}
		};
	}
	
	/**
	 * 
	 * @param new_id
	 * @return
	 */
	@GET
	@Path("getRandomRelatedNewsByCatid={catid_id}")
	@Produces("application/xml")
	public StreamingOutput getRandomRelatedNews(@PathParam("catid_id") String catid_id) {
		list_news = newsDAO.getRandomRelatedNewsByCatid(catid_id);
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
				outputNewsList(outputStream, list_news);
			}
		};
	}
	
	@GET
	@Path("getCommentByNewId={new_id}")
	@Produces("application/xml")
	public StreamingOutput getCommentByNewId(@PathParam("new_id") String new_id) {
		list_comments = commentDAO.findCommentByNewId(new_id);
		return new StreamingOutput() {
			public void write(OutputStream outputStream) throws IOException, WebApplicationException {
				outputCommentList(outputStream, list_comments);
			}
		};
	}
	
	@POST
	@Path("/addComment")
	@Consumes("application/xml")
	public void addComment(InputStream is) {
		Comment comment = readComment(is);
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		System.out.println(comment.getNew_id()+"---------"+comment.getTime_comment());
		commentDAO.addComment(comment.getNew_id(), comment.getUser_name(), comment.getComment(), ts);
		//return Response.entity("Added comment").build();
	}

	/**
	 * 
	 * @param os
	 * @param list
	 * @throws IOException
	 */
	protected void outputNewsList(OutputStream os, List<News> list) throws IOException {
		PrintStream writer = new PrintStream(os);

		writer.println("<list-news>");
		for (News n : list) {
			writer.println("<news>");
			writer.println("<id>" + n.getId() + "</id>");
			writer.println(" <title>" + n.getTitle() + "</title>");
			writer.println(" <intro>" + n.getIntro() + "</intro>");
			writer.println(" <content>" + n.getContent() + "</content>");
			writer.println(" <date-created>" + n.getDate_created() + "</date-created>");
			writer.println("<author>" + n.getAuthor() + "</author>");
			writer.println("<category-id>" + n.getCatid_id() + "</category-id>");
			writer.println("<tags>" +n.getTags()+ "</tags>");
			writer.println("</news>");

		}
		writer.println("</list-news>");
	}

	/**
	 * 
	 * @param os
	 * @param n
	 * @throws IOException
	 */
	protected void outputNews(OutputStream os, News n) throws IOException {
		PrintStream writer = new PrintStream(os);
		writer.println("<list-news>");
		writer.println("<news>");
		writer.println("<id>" + n.getId() + "</id>");
		writer.println(" <title>" + n.getTitle() + "</title>");
		writer.println(" <intro>" + n.getIntro() + "</intro>");
		writer.println(" <content>" + n.getContent() + "</content>");
		writer.println(" <date-created>" + n.getDate_created() + "</date-created>");
		writer.println("<author>" + n.getAuthor() + "</author>");
		writer.println("<category-id>" + n.getCatid_id() + "</category-id>");
		writer.println("<tags>" +n.getTags()+ "</tags>");
		writer.println("</news>");
		writer.println("</list-news>");
	}
	

	/**
	 * 
	 * @param os
	 * @param list_comments
	 * @throws IOException
	 */
	protected void outputCommentList(OutputStream os, List<Comment> list_comments) throws IOException {
		PrintStream writer = new PrintStream(os);
		writer.println("<list-comments>");
		for (Comment c : list_comments) {
			writer.println("<comment>");
			writer.println("<id>" + c.getId() + "</id>");
			writer.println("<new-id>" + c.getNew_id() + "</new-id>");
			writer.println("<user-name>" + c.getUser_name() + "</user-name>");
			writer.println("<comments>" + c.getComment() + "</comments>");
			writer.println("<time-comment>" + c.getTime_comment() + "</time-comment>");
			writer.println("</comment>");
		}
		writer.println("</list-comments>");
	}
	
	/**
	 * 
	 * @param is
	 * @return
	 */
	protected Comment readComment(InputStream is) {
		Comment comment = new Comment();
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();

			NodeList nl = root.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Element element = (Element) nl.item(i);
				
				/* if (element.getTagName().equals("id")) {
					comment.setId(Integer.parseInt(element.getTextContent()));
				} else */ if (element.getTagName().equals("new-id")) {
					comment.setNew_id(Integer.parseInt(element.getTextContent()));
				} else if (element.getTagName().equals("user-name")) {
					comment.setUser_name(element.getTextContent());
				} else if (element.getTagName().equals("comments")) {
					comment.setComment(element.getTextContent());
				}/* else if (element.getTagName().equals("time-comment")) {
					comment.setTime_comment(Timestamp.valueOf(element.getTextContent()));
				}*/
				
			}
		} catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		} 
		return comment;
	}

}
