package edu.uit.youtube_crawler;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import edu.uit.models.Comment;
import edu.uit.models.YoutubeData;

public class YoutubeCrawler {

	private String url;
	private WebDriver driver;
	private YoutubeData youtubeData;
	
	/**
	 * getter of youtubeData
	 * @return
	 */
	public YoutubeData getYoutubeData() {
		return youtubeData;
	}

	/**
	 * method initialize
	 * @param url path to redirect
	 */
	public YoutubeCrawler(String url) {
		this.url = url;
		String seleniumWebdriver = "";
		try{
		 	seleniumWebdriver = System.getenv("SELENIUM_WEB_DRIVER");
		}catch(NullPointerException e){
			seleniumWebdriver  = "E:\\git\\Selenium-webdriver\\chromedriver_win32\\chromedriver.exe";
		}
		
		System.setProperty("webdriver.chrome.driver", seleniumWebdriver);

		ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
		driver = new ChromeDriver(options);
		youtubeData = new YoutubeData();
	}
	
	/**
	 * method open chrome and navigate to url
	 * @throws InterruptedException
	 */
	public void openBrowser() throws InterruptedException {
		driver.get(url);
		Thread.sleep(5000);
	}
	
	/**
	 * method get title video
	 * @return
	 */
	public String getTitle() {
		String title = "";
		try {
			WebElement element = driver.findElement(By.xpath("//*[@id=\"container\"]/h1/yt-formatted-string"));
			title = element.getText();
		}catch(Exception e) {
			System.out.println("Selenium - Canot find element - getTitle");
		}
		youtubeData.setTitle(title);
		return title;
	}
	
	/**
	 * method get description of video
	 * @return 
	 */
	public String getDescription() {
		String description = "";
		try {
			WebElement element = driver.findElement(By.xpath("//*[@id=\"description\"]/yt-formatted-string"));
			description = element.getText();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		youtubeData.setDescription(description);
		return description;
	}
	
	/**
	 * method get number view of video
	 * @return
	 */
	public long getNumberView() {
		long numberView = 0;
		try {
			WebElement element = driver.findElement(By.xpath("//*[@id=\"count\"]/yt-view-count-renderer/span[1]"));
			numberView = Integer.parseInt(element.getText().split(" ")[0].replaceAll(",", ""));
		}catch(Exception e) {
			System.out.println("Selenium - Canot find element - getView");
		}
		youtubeData.setNumberView(numberView);
		return numberView;
	}
	
	/**
	 * method get number like of video
	 * @return
	 */
	public long getLike() {
		long like = 0;
		try {
			WebElement element = driver.findElements(By.xpath("//*[@id=\"text\"]")).get(2);
			like = Integer.parseInt(element.getAttribute("aria-label").split(" ")[0].replaceAll(",", ""));
		}catch(Exception e) {
			System.out.println("Selenium - Canot find element - getLike");
		}
		youtubeData.setNumberLike(like);
		return like;
	}
	
	/**
	 * method get number dislike of video
	 * @return
	 */
	public long getDislike() {
		long dislike = 0;
		try {
			WebElement element = driver.findElements(By.xpath("//*[@id=\"text\"]")).get(3);
			dislike = Integer.parseInt(element.getAttribute("aria-label").split(" ")[0].replaceAll(",", ""));
		}catch(Exception e) {
			System.out.println("Selenium - Canot find element - getDislike");
		}
		youtubeData.setNumberDislike(dislike);
		return dislike;
	}
	
	/**
	 * method get channel 
	 * @return
	 */
	public String getChannel() {
		String channel = "";
		try {
			WebElement element = driver.findElement(By.xpath("//*[@id=\"owner-name\"]/a"));
			channel = element.getText();
		}catch(Exception e) {
			System.out.println("Selenium - Canot find element - getChannel");
		}
		youtubeData.setChannel(channel);
		return channel;
	}
	
	/**
	 * method get number comment of video
	 * @return
	 * @throws InterruptedException
	 */
	public long getNumberComment() throws InterruptedException {
		long numberComment = 0;
		try {
			WebElement element = driver.findElement(By.xpath("//*[@id=\"count\"]/yt-formatted-string"));
			numberComment = Integer.parseInt(element.getText().split(" ")[0].replaceAll(",", ""));
		}catch(Exception e) {
			System.out.println("Selenium - Canot find element - getNumberComment");
		}
		youtubeData.setNumberComment(numberComment);
		return numberComment;
	}
	
	/**
	 * method get list comment of video
	 * @return
	 * @throws InterruptedException
	 */
	public List<Comment> getComment() throws InterruptedException {
		List<Comment> listComments = new ArrayList<Comment>();
		List<WebElement> comments = driver.findElements(By.xpath("//*[@id=\"comment\"]"));
		for (WebElement cmt : comments) {
			Comment comment = new Comment() ;
			comment.setUserName(cmt.findElement(By.id("author-text")).findElement(By.tagName("span")).getText());
			comment.setContent(cmt.findElement(By.id("content-text")).getText());
			String like ="";
			try {
				like = (cmt.findElement(By.id("vote-count-middle")).getAttribute("aria-label").toString());
				like = like.split(" ")[0].replaceAll(".", "").replaceAll("K", "000").replaceAll("M", "000000");
			}catch(Exception e){
				comment.setNumberLike(0);
			}
			 
			try {
				comment.setNumberLike(Integer.parseInt(like));
			}catch(Exception e){
				comment.setNumberLike(0);
			}
			
			listComments.add(comment);
		}
		youtubeData.setComments(listComments);
		return listComments;
	}
	
	/**
	 * method wait page load
	 * @throws InterruptedException
	 */
	public void loadPage() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		int pageHeight  = ((Number) js.executeScript("return document.documentElement.scrollHeight")).intValue();
		int curHeight = 0;
		while(curHeight < pageHeight) {
			curHeight+= 700;
			Thread.sleep(3000);
			js.executeScript("window.scrollBy(0,700)","");
			
			pageHeight  = ((Number) js.executeScript("return document.documentElement.scrollHeight")).intValue();
		}
		try {
				WebElement buttonShowMore = driver.findElement(By.xpath("//*[@id=\"more\"]"));
				js.executeScript("arguments[0].scrollIntoView()", buttonShowMore); 
				js.executeScript("window.scrollBy(0,-100)","");
				buttonShowMore.click();
				Thread.sleep(1000);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * method close browser
	 */
	public void closeBrowser() {
		this.driver.quit();
	}
}
