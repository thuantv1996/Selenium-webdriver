package edu.uit.crawler;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import edu.uit.com.YoutubeConstant;
import edu.uit.models.YoutubeComment;
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
		System.setProperty("webdriver.chrome.driver", YoutubeConstant.PATH_CHROME_EXE);
		this.url = url;
		youtubeData = new YoutubeData();
	}
	
	/**
	 * method open chrome and navigate to url
	 * @throws InterruptedException
	 */
	public void openBrowser() throws InterruptedException {
		// open browser
		driver = new ChromeDriver();
		// navigate to url
		driver.get(url);
		// // wait for load
		Thread.sleep(YoutubeConstant.TIME_OUT);
	}
	
	/**
	 * method get title video
	 * @return
	 */
	private String getTitle() {
		String title = "";
		try {
			// get element contain title video
			WebElement element = driver.findElement(By.xpath(YoutubeConstant.XP_TITLE));
			// get title
			title = element.getText();
		}catch(Exception e) {
			// print exception
			System.out.println(e);
		}
		// put into model
		youtubeData.setTitle(title);
		return title;
	}
	
	/**
	 * method get description of video
	 * @return 
	 */
	private String getDescription() {
		String description = "";
		try {
			// get element contain description video
			WebElement element = driver.findElement(By.xpath(YoutubeConstant.XP_DESCRIPTION));
			// get description
			description = element.getText();
		}catch(Exception e) {
			// print exception
			System.out.println(e);
		}
		// put into model
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
			// get element contain number view video
			WebElement element = driver.findElement(By.xpath(YoutubeConstant.XP_NUM_VIEW));
			// get number view
			numberView = Integer.parseInt(element.getText().split(" ")[0].replaceAll(",", ""));
		}catch(Exception e) {
			// print exception
			System.out.println(e);
		}
		// put into model
		youtubeData.setNumberView(numberView);
		return numberView;
	}
	
	/**
	 * method get number like of video
	 * @return
	 */
	private long getLike() {
		long like = 0;
		try {
			// get element contain item like video
			WebElement element = driver.findElements(By.xpath(YoutubeConstant.XP_LIKE)).get(2);
			// get like
			like = Integer.parseInt(element.getAttribute("aria-label").split(" ")[0].replaceAll(",", ""));
		} catch (Exception e) {
			// print exception
			System.out.println(e);
		}
		// put into model
		youtubeData.setNumberLike(like);
		return like;
	}
	
	/**
	 * method get number dislike of video
	 * @return
	 */
	private long getDislike() {
		long dislike = 0;
		try {
			// get element contain item dislike video
			WebElement element = driver.findElements(By.xpath(YoutubeConstant.XP_DISLIKE)).get(3);
			// get dislike
			dislike = Integer.parseInt(element.getAttribute("aria-label").split(" ")[0].replaceAll(",", ""));
		} catch (Exception e) {
			// print exception
			System.out.println(e);
		}
		// put into model
		youtubeData.setNumberDislike(dislike);
		return dislike;
	}
	
	/**
	 * method get channel 
	 * @return
	 */
	private String getChannel() {
		String channel = "";
		try {
			// get element contain channel video
			WebElement element = driver.findElement(By.xpath(YoutubeConstant.XP_CHANNEL));
			// get channel
			channel = element.getText();
		} catch (Exception e) {
			// print exception
			System.out.println(e);
		}
		// put into model
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
			// get element contain number comment video
			WebElement element = driver.findElement(By.xpath(YoutubeConstant.XP_NUM_CMT));
			// get number comment
			numberComment = Integer.parseInt(element.getText().split(" ")[0].replaceAll(",", ""));
		}catch (Exception e) {
			// print exception
			System.out.println(e);
		}
		// put into model
		youtubeData.setNumberComment(numberComment);
		return numberComment;
	}
	
	/**
	 * method get list comment of video
	 * @return
	 * @throws InterruptedException
	 */
	public List<YoutubeComment> getComment() throws InterruptedException {
		// list comment (result)
		List<YoutubeComment> listComments = new ArrayList<YoutubeComment>();
		// list element comment
		List<WebElement> comments = driver.findElements(By.xpath(YoutubeConstant.XP_CMT));
		// repeat all element
		for (WebElement cmt : comments) {
			YoutubeComment comment = new YoutubeComment() ;
			// get and put user name into model
			comment.setUserName(cmt.findElement(By.id(YoutubeConstant.ID_USER_CMT)).findElement(By.tagName("span")).getText());
			// get and put content into model
			comment.setContent(cmt.findElement(By.id(YoutubeConstant.ID_CONTENT_CMT)).getText());
			String like ="";
			try {
				// get and put like into model
				like = (cmt.findElement(By.id(YoutubeConstant.ID_LIKE_CMT)).getAttribute("aria-label").toString());
				like = like.split(" ")[0].replaceAll(".", "").replaceAll("K", "000").replaceAll("M", "000000");
			}catch(Exception e){
				comment.setNumberLike(0);
			}
			 
			try {
				comment.setNumberLike(Integer.parseInt(like));
			}catch(Exception e){
				comment.setNumberLike(0);
			}
			// andd model to list
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
		// create object javascript
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		// get page height
		int pageHeight  = ((Number) js.executeScript("return document.documentElement.scrollHeight")).intValue();
		int curHeight = 0;
		// code scroll to end page
		while(curHeight < pageHeight) {
			// scroll height 700px
			curHeight+= 700;
			js.executeScript("window.scrollBy(0,700)","");
			// wait for load
			Thread.sleep(YoutubeConstant.TIME_OUT);
			// get page height
			pageHeight  = ((Number) js.executeScript("return document.documentElement.scrollHeight")).intValue();
		}
		try {
			// click button show more description
				WebElement buttonShowMore = driver.findElement(By.xpath(YoutubeConstant.XP_DES_MORE));
				js.executeScript("arguments[0].scrollIntoView()", buttonShowMore); 
				js.executeScript("window.scrollBy(0,-100)","");
				buttonShowMore.click();
				// wait for load
				Thread.sleep(YoutubeConstant.TIME_OUT);
		}catch(Exception e) {
			// print exception
			System.out.println(e);
		}
		
	}
	
	/**
	 * method crawl data to youtube
	 * @throws InterruptedException
	 */
	public void crawlData() throws InterruptedException {
		getTitle();
		getDescription();
		getChannel();
		getNumberView();
		getLike();
		getDislike();
		getNumberComment();
		getComment();
	}
	
	/**
	 * method close browser
	 */
	public void closeBrowser() {
		this.driver.quit();
	}
}
