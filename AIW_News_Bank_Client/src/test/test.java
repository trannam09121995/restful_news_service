package test;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

public class test {

	private static final String BASE_URI = "http://localhost:8081/AIW_News_Bank_Server/services/news/";
	private static Client client;

	public test() {
		client = ClientBuilder.newClient();
	}

	/**
	 * Lấy ra tất cả các tin
	 * 
	 * @return list_news_objects
	 */
	public List<News> findAll() {
		List<News> list_news_objects = new ArrayList<News>();

		String list_all_news_string = client.target(BASE_URI + "findAll").request().get(String.class);

		// read xml string response
		Document document = parseXMLFromString(list_all_news_string);
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

			list_news_objects.add(n);

		}

		return list_news_objects;
	}

	/**
	 * tìm tin theo id
	 * 
	 * @param new_id
	 * @return
	 */
	public News findNewById(String new_id) {
		// get data from webservice
		String list_news_xml = client.target(BASE_URI + "findNewById=" + new_id).request().get(String.class);

		// read xml string response
		Document document = parseXMLFromString(list_news_xml);
		Element root = document.getDocumentElement();
		NodeList nl = root.getElementsByTagName("news");

		Element element = (Element) nl.item(0);
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

		return n;
	}

	/**
	 * tìm tin theo loại (tìm theo cột 'catid_search_name' trong database)
	 * 
	 * @param catid_name
	 * @return
	 */
	public List<News> findNewByCatid(String catid_name) {
		List<News> list_news_objects = new ArrayList<News>();

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

			list_news_objects.add(n);

		}
		return list_news_objects;
	}

	
	/**
	 * tìm tin theo tag
	 * @param tag_name
	 * @return
	 */
	public List<News> findNewByTag(String tag_name) {
		List<News> list_news_objects = new ArrayList<News>();

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

			list_news_objects.add(n);
		}
		return list_news_objects;
	}
	
	
	/**
	 * lấy ra random 3 bài viết liên quan cùng thể loại 
	 * @param curent_catid_id
	 * @return
	 */
	public List<News> getRandomRelatedNews(String curent_catid_id) {
		List<News> list_news_objects = new ArrayList<News>();

		// get data from webservice
		String list_news_xml = client.target(BASE_URI + "getRandomRelatedNewsByCatid=" + curent_catid_id).request().get(String.class);

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

			list_news_objects.add(n);
		}
		
		return list_news_objects;
	}
	
	/**
	 * lấy comments của tin đang xem
	 * @param new_id
	 * @return
	 */
	public List<Comment> getCommentByNewId(String new_id) {
		List<Comment> list_comment_onjects = new ArrayList<Comment>();

		String list_comments_xml = client.target(BASE_URI + "getCommentByNewId=" + new_id).request().get(String.class);

		// read xml string response
		Document document = parseXMLFromString(list_comments_xml);
		Element root = document.getDocumentElement();
		NodeList nl = root.getElementsByTagName("comment");

		for (int i = 0; i < nl.getLength(); i++) {
			Element element = (Element) nl.item(i);
			Comment c = new Comment();

			if (element.getTagName().equals("id")) {
				c.setId(Integer.parseInt(element.getTextContent()));
			} else if (element.getTagName().equals("new-id")) {
				c.setNew_id(Integer.parseInt(element.getTextContent()));
			} else if (element.getTagName().equals("user-name")) {
				c.setUser_name(element.getTextContent());
			} else if (element.getTagName().equals("comments")) {
				c.setComment(element.getTextContent());
			} else if (element.getTagName().equals("time-comment")) {
				c.setTime_comment(Timestamp.valueOf(element.getTextContent()));
			}

			list_comment_onjects.add(c);

		}
		return list_comment_onjects;
	}

	/**
	 * thêm comment
	 * 
	 * @param current_new_id
	 * @param user_name
	 * @param comment_contents
	 */
	public void addComment(String current_new_id, String user_name, String comment_contents) {
		String comment_xml = "<comment>" + "<id></id>" + "<new-id>" + current_new_id + "</new-id>" + "<user-name>"
				+ user_name + "</user-name>" + "<comments>" + comment_contents + "</comments>"
				+ "<time-comment></time-comment>" + "</comment>";

		Response response = client.target(BASE_URI + "addComment").request().post(Entity.xml(comment_xml));
		System.out.println("Response details: " + response.toString());
		System.out.println("Comment thành công...");

	}

	/**
	 * 
	 * convert chuỗi String response dạng xml sang xml document
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
	 * Lấy dữ liệu của thẻ xml
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

	public void close() {
		client.close();
	}

	public static void main(String[] args) throws Exception {
		test t = new test();
		t.addComment("3", "Tran Nam", "Glory MU");
		t.clone();
	}

}
