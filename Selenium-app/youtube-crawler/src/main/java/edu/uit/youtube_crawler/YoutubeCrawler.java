package edu.uit.youtube_crawler;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import edu.uit.models.Comment;
import edu.uit.models.YoutubeData;

public class YoutubeCrawler {

	private String url;
	private WebDriver driver;
	private YoutubeData youtubeData;
	
	public YoutubeData getYoutubeData() {
		return youtubeData;
	}

	public void setYoutubeData(YoutubeData youtubeData) {
		this.youtubeData = youtubeData;
	}

	// initialize
	public YoutubeCrawler(String url) {
		System.setProperty("webdriver.chrome.driver", "E:\\git\\Selenium-webdriver\\chromedriver_win32\\chromedriver.exe");
		this.url = url;
		driver = new ChromeDriver();
		youtubeData = new YoutubeData();
	}
	
	// run 
	public void run() throws InterruptedException {
		driver.get(url);
		Thread.sleep(5000);
	}
	// get title
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
	// get View
	public long getView() {
		long view = 0;
		try {
			WebElement element = driver.findElement(By.xpath("//*[@id=\"count\"]/yt-view-count-renderer/span[1]"));
			view = Integer.parseInt(element.getText().split(" ")[0].replaceAll(",", ""));
		}catch(Exception e) {
			System.out.println("Selenium - Canot find element - getView");
		}
		youtubeData.setNumberView(view);
		return view;
	}
	
	// get Like
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
	// get Dislike //*[@id="text"]
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
	// get channel
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
	// get number comment
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
	
	
	// get list comment
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
	
	public void LoadPage() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		int pageHeight  = ((Number) js.executeScript("return document.documentElement.scrollHeight")).intValue();
		int curHeight = 0;
		while(curHeight < pageHeight) {
			curHeight+= 700;
			js.executeScript("window.scrollBy(0,700)","");
			Thread.sleep(3000);
			pageHeight  = ((Number) js.executeScript("return document.documentElement.scrollHeight")).intValue();
		}
	}
	
	public void CloseBrowser() {
		this.driver.quit();
	}
}
