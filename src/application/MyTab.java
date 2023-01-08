
package application;

import javafx.scene.control.Tab;
import javafx.scene.web.WebView;

/**
 * @author Zhaoyuan(Sarah) Zhang
 *
 */
public class MyTab extends Tab{

	private WebView webView;
	private String tabName;
	

	public MyTab() {
		
		this.webView = new WebView();
		this.webView.getEngine().load("http://www.acc.com");
	}

	public WebView getWebView() {
		return webView;
	}

	public void setWebView(WebView webView) {
		this.webView = webView;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}


	
	
}
