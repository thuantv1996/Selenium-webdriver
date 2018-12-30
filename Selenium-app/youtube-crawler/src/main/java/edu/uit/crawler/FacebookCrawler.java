package edu.uit.crawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import edu.uit.com.FacebookConstant;
import edu.uit.models.FacebookComment;
import edu.uit.models.FacebookData;

public class FacebookCrawler {
	private String url;
	private WebDriver driver;
	private List<FacebookData> facebookDatas;
	private List<WebElement> posts;
	
	/**
	 * getter url 
	 * @return
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * getter facebook data
	 * @return
	 */
	public List<FacebookData> getFacebookDatas() {
		return facebookDatas;
	}
	
	/**
	 * method initialize
	 * @param url path to redirect
	 */
	public FacebookCrawler(String url) {
		/*
		 * code disable notifications on chrome
		 */
		// Map<String, Object> prefs = new HashMap<String, Object>();
		// prefs.put(FacebookConstant.DIS_NOTIFICATIONS, 2);
		// ChromeOptions options = new ChromeOptions();
		// options.setExperimentalOption("prefs", prefs);
		// // path to chrome.exe
		// System.setProperty("webdriver.chrome.driver", 
		// 				   FacebookConstant.PATH_CHROME_EXE );
		// // set url 
		// this.url = url;
		// // open browser
		// driver = new ChromeDriver(options);
		// // maximize windows
		// driver.manage().window().maximize();
		// // initialize  model
		// facebookDatas = new ArrayList<FacebookData>();	


		this.url = url;
		
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put(FacebookConstant.DIS_NOTIFICATIONS, 2);
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		String seleniumWebdriver = "";

		try{
		 	seleniumWebdriver = System.getenv("SELENIUM_WEB_DRIVER");
		}catch(NullPointerException e){
			seleniumWebdriver  = FacebookConstant.PATH_CHROME_EXE;
		}
		
		System.setProperty("webdriver.chrome.driver", seleniumWebdriver);

		try{
			seleniumWebdriver = System.getenv("RUNNING_CLOUD");
			options.addArguments("--headless");
			options.addArguments("--no-sandbox");	
	   }catch(NullPointerException e){
		   
	   }
      		// open browser
			driver = new ChromeDriver(options);
			// maximize windows
			driver.manage().window().maximize();
			// initialize  model
			facebookDatas = new ArrayList<FacebookData>();	
	
	}
	
	/**
	 * method close browser
	 */
	public void closeBrowser() {
		this.driver.quit();
	}

	/**
	 * method crawl data to facebook
	 * 
	 */
	public void crawlData() {
		for (WebElement post : posts) {
			// The variable stores user name
			String userName = "";
			String  message = "";
			// The variable stores the information of each post
			FacebookData fbData = new FacebookData();
			
			/****************************************************************************************
			 * get user name 
			 *****************************************************************************************/
			try {
				// find Element contain user name
				userName = post.findElement(By.tagName(FacebookConstant.TAG_H5))
							    .findElement(By.tagName(FacebookConstant.TAG_A)).getText();
			} catch (Exception e) {
				// print exception 
				System.out.println(e);
			}

			
			/****************************************************************************************
			 *  get message
			  *****************************************************************************************/
			try {
				try {
					// click show more
					post.findElement(By.cssSelector(FacebookConstant.CSS_SHOW_MORE)).click();
				}catch (Exception e) {
					// print exception 
					System.out.println(e);
				}
				// find Element contain message
				message = post.findElement(By.cssSelector(FacebookConstant.CSS_MESSAGE))
						      .findElement(By.tagName(FacebookConstant.TAG_P)).getText();
				
			} catch (Exception e) {
				try {
					// find Element contain message
					message = post.findElement(By.cssSelector(FacebookConstant.CSS_MESSAGE))
								  .findElement(By.cssSelector(FacebookConstant.CSS_MESSAGE_2)).getText();
				}catch (Exception ex) {
					// print exception 
					System.out.println(ex);
				}
				// print exception 
				System.out.println(e);
			}
			
			/****************************************************************************************
			 *  get comment
			  *****************************************************************************************/
			// open full comment
			// repeat until no items show comments
			while(true) {
				try {
					// find items show full comment
					List<WebElement> showMoreCmt = post.findElements(By.cssSelector(FacebookConstant.CSS_CMT_MORE));
					// if not found 
					if(showMoreCmt.size()==0) {
						// break
						break;
					}
					// Repeat through all items
					for (WebElement webElement : showMoreCmt) {
						// click show comment
						webElement.click();
					}
					// wait for load
					Thread.sleep(FacebookConstant.TIME_OUT);
				}catch (Exception e) {
					// print exception 
					System.out.println(e);
					break;
				}
			}
			// get area list comment 
			List<WebElement> divComments = new ArrayList<>();
			// List comments
			List<FacebookComment> comments = new ArrayList<>();
			try {
				// get list area comment
				divComments = post.findElements(By.cssSelector(FacebookConstant.CSS_AREA_CMT));
				// repeat all area in list
				for (WebElement divComment : divComments) {
					FacebookComment comment = new FacebookComment();
					String user = "";
					try {
						// get User name
						user = divComment.findElement(By.cssSelector(FacebookConstant.CSS_USR_CMT)).getText();
					}catch (Exception e) {
						// print exception 
						System.out.println(e);
					}
					String content = "";
					try {
						// get content
						content = divComment.findElement(By.cssSelector(FacebookConstant.CSS_CONTENT_CMT)).getText();
					}catch (Exception e) {
						// print exception 
						System.out.println(e);
					}
					// put into comment
					comment.setUser(user);
					comment.setContent(content);
					comments.add(comment);
				}
			}catch (Exception e) {
				// print exception 
				System.out.println(e);
			}
			
			// stores into fbData
			fbData.setUser(userName);
			fbData.setStatus(message);
			fbData.setComments(comments);
			facebookDatas.add(fbData);
		}
	}
	
	/**
	 * method execute to load page
	 * @throws InterruptedException
	 */
	public void loadPage() throws InterruptedException {
		// variable Javascript to execute scroll windows
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		// get page height
		int pageHeight  = ((Number) js.executeScript("return document.documentElement.scrollHeight")).intValue();
		// current scroll 
		int curHeight = 0;
		// get list post
		posts =  driver.findElements(By.className(FacebookConstant.CLS_POST));
		while(curHeight < pageHeight && posts.size() < FacebookConstant.LIMIT_POST) {
			curHeight+= 700;
			// scroll windows
			js.executeScript("window.scrollBy(0,700)","");
			// wait for load
			Thread.sleep(FacebookConstant.TIME_OUT);
			// get list post
			posts =  driver.findElements(By.className(FacebookConstant.CLS_POST));	
			// get page height
			pageHeight  = ((Number) js.executeScript("return document.documentElement.scrollHeight")).intValue();
		}
	}
	
	/**
	 * method navigate to facebook and login 
	 * @param id account facebook
	 * @param password pasword facebook
	 * @throws InterruptedException
	 */
	public void loginFacebook(String id, String password) throws InterruptedException {
		// navigate to www.facebook.com
		driver.get(FacebookConstant.FB_URL);
		// wait for load
		Thread.sleep(FacebookConstant.TIME_OUT);
		// get textbox input account
		driver.findElement(By.xpath(FacebookConstant.XP_EMAIL)).sendKeys(id);
		// get textbox input password
		driver.findElement(By.xpath(FacebookConstant.XP_PASS)).sendKeys(password);
		// get button login
		driver.findElement(By.xpath(FacebookConstant.XP_LOGIN)).click();
		// wait for load
		Thread.sleep(FacebookConstant.TIME_OUT);
		// navigate to url
		driver.get(url);
		// wait for load
		Thread.sleep(FacebookConstant.TIME_OUT);
	}

}
