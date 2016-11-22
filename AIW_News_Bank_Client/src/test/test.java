package test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

public class test {
	
	private static final String BASE_URI = "http://localhost:8081/AIW_News_Bank_Server/services/news/";

	public test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		Client client = ClientBuilder.newClient();
		try {
			
			String comment_xml = "<comment><id></id><new-id>3</new-id><user-name>Tran Nam</user-name><comments>I love Manchester United</comments>"
					+ "<time-comment></time-comment></comment>";
			Response response = client.target(BASE_URI + "addComment").request().post(Entity.xml(comment_xml));
			System.out.println(response.toString());
			response.close();
			
		} finally {
			client.close();
		}
	}

}
