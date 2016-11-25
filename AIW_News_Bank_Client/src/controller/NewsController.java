package controller;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.view.facelets.Tag;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import entity.Comment;
import entity.News;
import utils.BaseController;

@ManagedBean
@SessionScoped
public class NewsController extends BaseController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4272073285836203919L;
	// client vars
	public Client client;
	public static final String BASE_URI = "http://localhost:8081/AIW_News_Bank_Server/services/news/";

	// display vars
	public List<News> list_news = new ArrayList<News>();
	public List<News> list_related_news = new ArrayList<News>();
	public News current_new = new News();
	public List<Comment> list_comments = new ArrayList<Comment>();
	List<String> list_tags = new ArrayList<String>();

	// add comment vars
	public int comment_id;
	public int current_new_id;
	public String user_name;
	public String comments;
	public String time_comment;

	public NewsController() {
		client = ClientBuilder.newClient();
		findAll();
	}

	public List<News> findAll() {

		list_news.clear();

		// get data from webservice
		String list_news_xml = client.target(BASE_URI + "findAll").request().get(String.class);

		// read xml string response
		Document document = parseXMLFromString(list_news_xml);
		Element root = document.getDocumentElement();
		NodeList nl = root.getElementsByTagName("news");

		for (int i = 0; i < nl.getLength(); i++) {
			Element element = (Element) nl.item(i);
			News n = new News();

			// id
			NodeList id = element.getElementsByTagName("id");
			Element line = (Element) id.item(0);
			n.setId(Integer.parseInt(getCharacterDataFromElement(line)));

			// title
			NodeList title = element.getElementsByTagName("title");
			line = (Element) title.item(0);
			n.setTitle(getCharacterDataFromElement(line));

			// intro
			NodeList intro = element.getElementsByTagName("intro");
			line = (Element) intro.item(0);
			n.setIntro(getCharacterDataFromElement(line));

			// content
			NodeList content = element.getElementsByTagName("content");
			line = (Element) content.item(0);
			n.setContent(getCharacterDataFromElement(line));

			// date-created
			NodeList date_created = element.getElementsByTagName("date-created");
			line = (Element) date_created.item(0);
			n.setDate_created(Timestamp.valueOf(getCharacterDataFromElement(line)));

			// author
			NodeList author = element.getElementsByTagName("author");
			line = (Element) author.item(0);
			n.setAuthor(getCharacterDataFromElement(line));

			// catid-id
			NodeList catid_id = element.getElementsByTagName("category-id");
			line = (Element) catid_id.item(0);
			n.setCatid_id(Integer.parseInt(getCharacterDataFromElement(line)));

			// tags
			NodeList tags = element.getElementsByTagName("tags");
			line = (Element) tags.item(0);
			n.setTags(getCharacterDataFromElement(line));

			list_news.add(n);

		}
		return list_news;

	}

	public void findNewById(String new_id) {
		// get data from webservice
		String list_news_xml = client.target(BASE_URI + "findNewById=" + new_id).request().get(String.class);

		// read xml string response
		Document document = parseXMLFromString(list_news_xml);
		Element root = document.getDocumentElement();
		NodeList nl = root.getElementsByTagName("news");

		Element element = (Element) nl.item(0);
		// News n = new News();

		// id
		NodeList id = element.getElementsByTagName("id");
		Element line = (Element) id.item(0);
		current_new.setId(Integer.parseInt(getCharacterDataFromElement(line)));

		// title
		NodeList title = element.getElementsByTagName("title");
		line = (Element) title.item(0);
		current_new.setTitle(getCharacterDataFromElement(line));

		// intro
		NodeList intro = element.getElementsByTagName("intro");
		line = (Element) intro.item(0);
		current_new.setIntro(getCharacterDataFromElement(line));

		// content
		NodeList content = element.getElementsByTagName("content");
		line = (Element) content.item(0);
		current_new.setContent(getCharacterDataFromElement(line));

		// date-created
		NodeList date_created = element.getElementsByTagName("date-created");
		line = (Element) date_created.item(0);
		current_new.setDate_created(Timestamp.valueOf(getCharacterDataFromElement(line)));

		// author
		NodeList author = element.getElementsByTagName("author");
		line = (Element) author.item(0);
		current_new.setAuthor(getCharacterDataFromElement(line));

		// catid-id
		NodeList catid_id = element.getElementsByTagName("category-id");
		line = (Element) catid_id.item(0);
		current_new.setCatid_id(Integer.parseInt(getCharacterDataFromElement(line)));

		// tags
		NodeList tags = element.getElementsByTagName("tags");
		line = (Element) tags.item(0);
		current_new.setTags(getCharacterDataFromElement(line));

		// get related news
		getRandomRelatedNewsByCatid(String.valueOf(current_new.getCatid_id()));

		// get tags
		if (list_tags.size() > 0) {
			list_tags.clear();
		}
		list_tags = getTags(current_new.getTags());

		// get comments
		getCommentByNewId(String.valueOf(current_new.getId()));

		// redirect page
		try {
			redirect(getContextPath() + "/current.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return current_new;
	}

	public List<News> findNewByCatid(String catid_name) {
		list_news.clear();
		// get data from webservice
		String list_news_xml = client.target(BASE_URI + "findNewByCatid=" + catid_name).request().get(String.class);

		// read xml string response
		Document document = parseXMLFromString(list_news_xml);
		Element root = document.getDocumentElement();
		NodeList nl = root.getElementsByTagName("news");

		for (int i = 0; i < nl.getLength(); i++) {
			Element element = (Element) nl.item(i);
			News n = new News();

			// id
			NodeList id = element.getElementsByTagName("id");
			Element line = (Element) id.item(0);
			n.setId(Integer.parseInt(getCharacterDataFromElement(line)));

			// title
			NodeList title = element.getElementsByTagName("title");
			line = (Element) title.item(0);
			n.setTitle(getCharacterDataFromElement(line));

			// intro
			NodeList intro = element.getElementsByTagName("intro");
			line = (Element) intro.item(0);
			n.setIntro(getCharacterDataFromElement(line));

			// content
			NodeList content = element.getElementsByTagName("content");
			line = (Element) content.item(0);
			n.setContent(getCharacterDataFromElement(line));

			// date-created
			NodeList date_created = element.getElementsByTagName("date-created");
			line = (Element) date_created.item(0);
			n.setDate_created(Timestamp.valueOf(getCharacterDataFromElement(line)));

			// author
			NodeList author = element.getElementsByTagName("author");
			line = (Element) author.item(0);
			n.setAuthor(getCharacterDataFromElement(line));

			// catid-id
			NodeList catid_id = element.getElementsByTagName("category-id");
			line = (Element) catid_id.item(0);
			n.setCatid_id(Integer.parseInt(getCharacterDataFromElement(line)));

			// tags
			NodeList tags = element.getElementsByTagName("tags");
			line = (Element) tags.item(0);
			n.setTags(getCharacterDataFromElement(line));

			list_news.add(n);

		}
		return list_news;
	}

	public void findNewByTag(String tag_name) {
		list_news.clear();
		// get data from webservice
		String list_news_xml = client.target(BASE_URI + "findNewByTag=" + tag_name).request().get(String.class);

		// read xml string response
		Document document = parseXMLFromString(list_news_xml);
		Element root = document.getDocumentElement();
		NodeList nl = root.getElementsByTagName("news");

		for (int i = 0; i < nl.getLength(); i++) {
			Element element = (Element) nl.item(i);
			News n = new News();

			// id
			NodeList id = element.getElementsByTagName("id");
			Element line = (Element) id.item(0);
			n.setId(Integer.parseInt(getCharacterDataFromElement(line)));

			// title
			NodeList title = element.getElementsByTagName("title");
			line = (Element) title.item(0);
			n.setTitle(getCharacterDataFromElement(line));

			// intro
			NodeList intro = element.getElementsByTagName("intro");
			line = (Element) intro.item(0);
			n.setIntro(getCharacterDataFromElement(line));

			// content
			NodeList content = element.getElementsByTagName("content");
			line = (Element) content.item(0);
			n.setContent(getCharacterDataFromElement(line));

			// date-created
			NodeList date_created = element.getElementsByTagName("date-created");
			line = (Element) date_created.item(0);
			n.setDate_created(Timestamp.valueOf(getCharacterDataFromElement(line)));

			// author
			NodeList author = element.getElementsByTagName("author");
			line = (Element) author.item(0);
			n.setAuthor(getCharacterDataFromElement(line));

			// catid-id
			NodeList catid_id = element.getElementsByTagName("category-id");
			line = (Element) catid_id.item(0);
			n.setCatid_id(Integer.parseInt(getCharacterDataFromElement(line)));

			// tags
			NodeList tags = element.getElementsByTagName("tags");
			line = (Element) tags.item(0);
			n.setTags(getCharacterDataFromElement(line));

			list_news.add(n);
		}
		try {
			redirect(getContextPath() + "/NewsByTag.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getRandomRelatedNewsByCatid(String current_catid_of_new) {
		list_related_news.clear();
		// get data from webservice
		String list_news_xml = client.target(BASE_URI + "getRandomRelatedNewsByCatid=" + current_catid_of_new).request()
				.get(String.class);

		// read xml string response
		Document document = parseXMLFromString(list_news_xml);
		Element root = document.getDocumentElement();
		NodeList nl = root.getElementsByTagName("news");

		for (int i = 0; i < nl.getLength(); i++) {
			Element element = (Element) nl.item(i);
			News n = new News();

			// id
			NodeList id = element.getElementsByTagName("id");
			Element line = (Element) id.item(0);
			n.setId(Integer.parseInt(getCharacterDataFromElement(line)));

			// title
			NodeList title = element.getElementsByTagName("title");
			line = (Element) title.item(0);
			n.setTitle(getCharacterDataFromElement(line));

			// intro
			NodeList intro = element.getElementsByTagName("intro");
			line = (Element) intro.item(0);
			n.setIntro(getCharacterDataFromElement(line));

			// content
			NodeList content = element.getElementsByTagName("content");
			line = (Element) content.item(0);
			n.setContent(getCharacterDataFromElement(line));

			// date-created
			NodeList date_created = element.getElementsByTagName("date-created");
			line = (Element) date_created.item(0);
			n.setDate_created(Timestamp.valueOf(getCharacterDataFromElement(line)));

			// author
			NodeList author = element.getElementsByTagName("author");
			line = (Element) author.item(0);
			n.setAuthor(getCharacterDataFromElement(line));

			// catid-id
			NodeList catid_id = element.getElementsByTagName("category-id");
			line = (Element) catid_id.item(0);
			n.setCatid_id(Integer.parseInt(getCharacterDataFromElement(line)));

			// tags
			NodeList tags = element.getElementsByTagName("tags");
			line = (Element) tags.item(0);
			n.setTags(getCharacterDataFromElement(line));

			list_related_news.add(n);

		}
		// return list_related_news;
	}

	public List<Comment> getCommentByNewId(String new_id) {
		list_comments.clear();

		String list_comments_xml = client.target(BASE_URI + "getCommentByNewId=" + new_id).request().get(String.class);

		// read xml string response
		Document document = parseXMLFromString(list_comments_xml);
		Element root = document.getDocumentElement();
		NodeList nl = root.getElementsByTagName("comment");

		for (int i = 0; i < nl.getLength(); i++) {
			Element element = (Element) nl.item(i);
			Comment c = new Comment();

			// id
			NodeList id = element.getElementsByTagName("id");
			Element line = (Element) id.item(0);
			c.setId(Integer.parseInt(getCharacterDataFromElement(line)));

			// new-id
			NodeList current_new_id = element.getElementsByTagName("new-id");
			line = (Element) current_new_id.item(0);
			c.setNew_id(Integer.parseInt(getCharacterDataFromElement(line)));

			// user name
			NodeList user_name = element.getElementsByTagName("user-name");
			line = (Element) user_name.item(0);
			c.setUser_name(getCharacterDataFromElement(line));

			// comment contents
			NodeList comments = element.getElementsByTagName("comments");
			line = (Element) comments.item(0);
			c.setComment(getCharacterDataFromElement(line));

			// time comment
			NodeList time_comment = element.getElementsByTagName("time-comment");
			line = (Element) time_comment.item(0);
			c.setTime_comment(Timestamp.valueOf(getCharacterDataFromElement(line)));

			list_comments.add(c);

		}
		return list_comments;
	}

	public void addComment(String current_new_id, String user_name, String comment_contents) {
		String comment_xml = "<comment>" + "<id></id>" + "<new-id>" + current_new_id + "</new-id>" + "<user-name>@"
				+ user_name + "</user-name>" + "<comments>" + comment_contents + "</comments>"
				+ "<time-comment></time-comment>" + "</comment>";

		Response response = client.target(BASE_URI + "addComment").request().post(Entity.xml(comment_xml));
		System.out.println("Response details: " + response.toString());

		// reload comment
		getCommentByNewId(String.valueOf(current_new.getId()));
	}

	/**
	 * convert reponse xml string into readable xml document
	 * 
	 * @param xml
	 * @return xml document
	 */
	public static Document parseXMLFromString(String xml) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document doc = null;
		try {
			builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xml));
			doc = builder.parse(is);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return doc;
	}

	/**
	 * 
	 * @param e
	 * @return value of input element (tag name)
	 */
	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}

	/**
	 * Lấy ra list các tags
	 * 
	 * @param input
	 * @return
	 */
	public List<String> getTags(String input) {
		String[] array_tags = new String[5];
		array_tags = input.split(",");

		return new ArrayList<String>(Arrays.asList(array_tags));
	}

	public List<News> getList_news() {
		return list_news;
	}

	public void setList_news(List<News> list_news) {
		this.list_news = list_news;
	}

	public List<News> getList_related_news() {
		return list_related_news;
	}

	public void setList_related_news(List<News> list_related_news) {
		this.list_related_news = list_related_news;
	}

	public News getCurrent_new() {
		return current_new;
	}

	public void setCurrent_new(News current_new) {
		this.current_new = current_new;
	}

	public List<Comment> getList_comments() {
		return list_comments;
	}

	public void setList_comments(List<Comment> list_comments) {
		this.list_comments = list_comments;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getCurrent_new_id() {
		return current_new_id;
	}

	public void setCurrent_new_id(int current_new_id) {
		this.current_new_id = current_new_id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTime_comment() {
		return time_comment;
	}

	public void setTime_comment(String time_comment) {
		this.time_comment = time_comment;
	}

	public List<String> getList_tags() {
		return list_tags;
	}

	public void setList_tags(List<String> list_tags) {
		this.list_tags = list_tags;
	}

	/*
	 * public static void main(String[] args) { NewsController nc = new
	 * NewsController(); List<String> list_tags_separated = new
	 * ArrayList<String>(); findNewById("1"); System.out.println(n.getTags());
	 * String[] arr_tags = n.getTags().split(","); for (int i = 0; i <
	 * arr_tags.length; i++) { System.out.println(arr_tags[i]); }
	 * 
	 * }
	 */
}
