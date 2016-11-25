/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author trann
 */
public class BaseController implements Serializable {

	private static final long serialVersionUID = 6462772875449630871L;

	private static final char[] SOURCE_CHARACTERS = { 'À', 'Á', 'Â', 'Ã', 'È', 'É', 'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ',
			'Ù', 'Ú', 'Ý', 'à', 'á', 'â', 'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý', 'Ă', 'ă',
			'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ', 'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ',
			'ẫ', 'Ậ', 'ậ', 'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ', 'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế',
			'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ', 'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
			'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ', 'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ',
			'Ừ', 'ừ', 'Ử', 'ử', 'Ữ', 'ữ', 'Ự', 'ự' };

	private static final char[] DESTINATION_CHARACTERS = { 'A', 'A', 'A', 'A', 'E', 'E', 'E', 'I', 'I', 'O', 'O', 'O',
			'O', 'U', 'U', 'Y', 'a', 'a', 'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u', 'y', 'A',
			'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a',
			'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
			'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
			'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U',
			'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u' };

	public String getMessage(String key) {
		String value = key + " not found";

		Map<String, String> mapLabel = (Map<String, String>) getSessionValue("mapLabel");
		if (mapLabel != null && mapLabel.containsKey(key)) {
			value = mapLabel.get(key);
		}
		return value;
	}

	public String getMessage(String key, Object... arguments) {
		String value = key + " not found";

		Map<String, String> mapLabel = (Map<String, String>) getSessionValue("mapLabel");
		if (mapLabel != null && mapLabel.containsKey(key)) {
			value = mapLabel.get(key);
			for (int i = 0; i < arguments.length; i++) {
				value = value.replace("{" + i + "}", arguments[i].toString());
			}
		}

		return value;
	}

	public Locale getCurrentLocale() {
		Locale currentLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		return currentLocale;
	}

	private char removeAccent(char ch) {
		int index = Arrays.binarySearch(SOURCE_CHARACTERS, ch);
		if (index >= 0) {
			ch = DESTINATION_CHARACTERS[index];
		}
		return ch;
	}

	public String removeAccent(String s) {
		StringBuilder sb = new StringBuilder(s);
		for (int i = 0; i < sb.length(); i++) {
			sb.setCharAt(i, removeAccent(sb.charAt(i)));
		}
		return sb.toString();
	}

	public ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	public void addMessage(String idClientMessages, String message) {
		FacesContext.getCurrentInstance().addMessage(idClientMessages,
				new FacesMessage(FacesMessage.FACES_MESSAGES, message));
		getExternalContext().getFlash().setKeepMessages(true);
	}

	public void addMessageERR(String idClientMessages, String message) {
		FacesContext.getCurrentInstance().addMessage(idClientMessages,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message));
		getExternalContext().getFlash().setKeepMessages(true);
	}

	public void addMessageINFO(String idClientMessages, String message) {
		FacesContext.getCurrentInstance().addMessage(idClientMessages,
				new FacesMessage(FacesMessage.SEVERITY_INFO, message, message));
		getExternalContext().getFlash().setKeepMessages(true);
	}

	public void addMessageWARN(String idClientMessages, String message) {
		FacesContext.getCurrentInstance().addMessage(idClientMessages,
				new FacesMessage(FacesMessage.SEVERITY_WARN, message, message));
		getExternalContext().getFlash().setKeepMessages(true);
	}

	public void addMessageFATAL(String idClientMessages, String message) {
		FacesContext.getCurrentInstance().addMessage(idClientMessages,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, message, message));
		getExternalContext().getFlash().setKeepMessages(true);
	}

	// public void executeScript(String script) {
	// RequestContext.getCurrentInstance().execute(script);
	// }

	public String getParameter(String key) {
		String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
		if (value == null) {
			value = "";
		}
		return value;
	}

	public String getParameter(String key, String nvl) {
		String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
		if (value == null) {
			value = nvl;
		}
		return value;
	}

	public void setParameter(String key, Object value) {
		getParameters().put(key, value);
	}

	public Map getParameters() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
	}

	public HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

	public Map getSessions() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}

	public Object getSessionValue(String key) {
		return getSessions().get(key);
	}

	public void setSessionValue(String key, Object value) {
		getSessions().put(key, value);
	}

	public Map getApplications() {
		return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
	}

	public Object getApplicationValue(String key) {
		return getApplications().get(key);
	}

	public void setApplicationValue(String key, Object value) {
		getApplications().put(key, value);
	}

	public String getContextPath() {
		return getExternalContext().getRequestContextPath();
	}

	public String getContentType() {
		return getExternalContext().getRequestContentType();
	}

	public String getIpAddress() {
		return getExternalContext().getRemoteUser();
	}

	public String getRealPath(String path) {
		return getExternalContext().getRealPath(path);
	}

	public InputStream getResourceAsStream(String filePath) {
		return getExternalContext().getResourceAsStream(filePath);
	}

	public String getMimeType(String filePath) {
		return getExternalContext().getMimeType(filePath);
	}

	public String getRequestURL() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return request.getRequestURL().toString();
	}

	public String getRequestURI() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return request.getRequestURI();
	}

	public HttpServletRequest getRequest() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return request;
	}

	public HttpServletResponse getResponse() {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();
		return response;
	}

	public void redirect(String path) throws IOException {
		getExternalContext().redirect(path);
	}

	/*public List<Pagination> paginations(List<Pagination> paginations, int rowPerPage, int currentPage,
			int pageNumberDisplay, int rowTotal) {
		paginations = new ArrayList<Pagination>();
		if (rowTotal > 0) {
			int pageTotal = getPageTotal(rowPerPage, rowTotal);
			int value = 0;
			Pagination page;
			// Trang dau tien
			page = new Pagination();
			// page.setLabel("first");
			page.setLabel("");
			page.setValue(1);
			// page.setStyle("page first");
			page.setStyle("fa fa-fast-backward");
			if (currentPage == 1) {
				page.setRender(false);
				page.setDisabled(true);
			} else {
				page.setRender(true);
				page.setDisabled(false);
			}
			paginations.add(page);
			// ----------------
			// Tro ve tap trang truoc do
			page = new Pagination();
			// page.setLabel("prevs");
			page.setLabel("");
			// page.setStyle("page prevs");
			page.setStyle("fa fa-backward");
			if (currentPage % pageNumberDisplay == 0) {
				value = ((currentPage / pageNumberDisplay - 1) * pageNumberDisplay - pageNumberDisplay) + 1;
			} else {
				value = ((currentPage / pageNumberDisplay) * pageNumberDisplay - pageNumberDisplay) + 1;
			}

			if (value < 0) {
				page.setValue(1);
				page.setRender(false);
				page.setDisabled(true);
			} else {
				page.setValue(value);
				page.setRender(true);
				page.setDisabled(false);
			}
			paginations.add(page);
			// ----------------
			// Tro ve trang truoc do
			page = new Pagination();
			// page.setLabel("prev");
			page.setLabel("");
			// page.setStyle("page prev");
			page.setStyle("fa fa-caret-left");

			if (currentPage == 1) {
				page.setValue(1);
				page.setRender(false);
				page.setDisabled(true);
			} else {
				value = currentPage - 1;
				page.setValue(value);
				page.setRender(true);
				page.setDisabled(false);
			}
			paginations.add(page);
			// ----------------

			// Cac trang giua
			int begin = 0, end = 0;
			if (currentPage % pageNumberDisplay == 0) {
				begin = (currentPage / pageNumberDisplay - 1) * pageNumberDisplay + 1;
			} else {
				begin = (currentPage / pageNumberDisplay) * pageNumberDisplay + 1;
			}

			end = begin + pageNumberDisplay - 1;
			if (end > pageTotal) {
				end = pageTotal;
			}
			for (int i = begin; i <= end; i++) {
				page = new Pagination();
				page.setLabel(i + "");
				page.setValue(i);
				page.setRender(true);
				page.setStyle("page");
				if (i == currentPage) {
					page.setDisabled(false);
					page.setStyle("page active");
				}
				paginations.add(page);
			}
			// ----------------

			// Trang tiep theo
			page = new Pagination();
			// page.setLabel("next");
			page.setLabel("");
			// page.setStyle("page next");
			page.setStyle("fa fa-caret-right");
			if (currentPage == pageTotal) {
				page.setValue(pageTotal);
				page.setRender(false);
				page.setDisabled(true);
			} else {
				value = currentPage + 1;
				page.setValue(value);
				page.setRender(true);
				page.setDisabled(false);
			}
			paginations.add(page);
			// ----------------

			// Tap trang tiep theo
			page = new Pagination();
			// page.setLabel("nexts");
			page.setLabel("");
			// page.setStyle("page nexts");
			page.setStyle("fa fa-forward");
			if (currentPage % pageNumberDisplay == 0) {
				value = ((currentPage / pageNumberDisplay - 1) * pageNumberDisplay + pageNumberDisplay) + 1;
			} else {
				value = ((currentPage / pageNumberDisplay) * pageNumberDisplay + pageNumberDisplay) + 1;
			}
			if (value > pageTotal) {
				page.setValue(pageTotal);
				page.setRender(false);
				page.setDisabled(true);
			} else {
				page.setValue(value);
				page.setRender(true);
				page.setDisabled(false);
			}
			paginations.add(page);
			// ----------------
			// Trang cuoi cung
			page = new Pagination();
			// page.setLabel("last");
			page.setLabel("");
			page.setValue(pageTotal);
			// page.setStyle("page last");
			page.setStyle("fa fa-fast-forward");
			if (currentPage == pageTotal) {
				page.setRender(false);
				page.setDisabled(true);
			} else {
				page.setRender(true);
				page.setDisabled(false);
			}
			paginations.add(page);
			// ----------------
		}

		return paginations;

	}

	public int getPageTotal(int rowPerPage, int rowTotal) {
		int pageTotal = rowTotal / rowPerPage;
		if (rowTotal % rowPerPage > 0) {
			pageTotal++;
		}
		return pageTotal;
	}

	public User getUserSession() {
		return (User) getSessionValue(SessionConstant.USER);
	}

	public User getUserRegisterSession() {
		return (User) getSessionValue(SessionConstant.USER_REGISTER);
	}

	*//**
	 * Send email active 
	 * @param user {@link User}
	 * @throws MessagingException 
	 * @throws AddressException 
	 *//*
	public void sendEmailActive(User user) throws AddressException, MessagingException{
		// Thực hiện gửi email
		String linkActive = getDomainName(getRequestURL()) + getContextPath() + "/active-user?email="
				+ user.getEmail() + "&token=" + user.getToken();
		String body = getMessage("user.register.email_body", linkActive,linkActive);
		String[] toAddress = {user.getEmail()};
		SendMailUtil.sendmailserver(toAddress, getMessage("user.register.email_title"), body);
	}
	
	*//**
	 * Hàm lấy domain
	 * 
	 * @param url
	 * @return
	 * @throws URISyntaxException
	 *//*
	public String getDomainName(String url){
		URI uri;
		String domain = "";
		try {
			uri = new URI(url);
			domain = uri.getPort() > 0 ? uri.getHost() + ":" + uri.getPort() : uri.getHost();
			domain = domain.startsWith("www.") ? domain.substring(4) : domain;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return domain;
	}
	
	*//**
	 *  Change language
	 * @param locale
	 *//*
	public void changeLanguage(String locale) {
		if (locale == "") {
			locale = "ja";
		}
		LabelLocaleDAO labelLocaleDAO = new LabelLocaleDAO();
		Map<String, String> mapLabel = new HashMap<String, String>();
		
		FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) new Locale(locale));
		List<LabelLocale> listLabelLocale = labelLocaleDAO.findByProperty("locale", locale);
		
		if (listLabelLocale != null && listLabelLocale.size() > 0) {
			for (LabelLocale labelLocale : listLabelLocale) {
				mapLabel.put(labelLocale.getLabelKey(), labelLocale.getLabelValue());
			}
			setSessionValue("mapLabel", mapLabel);
		}
		
	}*/
}
