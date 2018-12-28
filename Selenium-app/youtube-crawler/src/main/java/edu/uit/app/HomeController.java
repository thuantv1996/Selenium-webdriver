package edu.uit.app;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uit.com.FacebookConstant;
import edu.uit.crawler.FacebookCrawler;
import edu.uit.crawler.YoutubeCrawler;
import edu.uit.dao.FacebookDataRepository;
import edu.uit.dao.YoutubeDataRepository;
import edu.uit.models.FacebookData;
import edu.uit.models.YoutubeData;

@RestController
@RequestMapping(value = "/crawl")
public class HomeController {
	@Autowired
	private YoutubeDataRepository repository;
	@Autowired
	private FacebookDataRepository facebookRepository;
	
	@RequestMapping(value = "/youtube/{id}", method = RequestMethod.POST)
	public YoutubeData getTitle(@PathVariable("id") String id) throws InterruptedException {
		YoutubeCrawler crawler = new YoutubeCrawler("https://www.youtube.com/watch?v="+id);
		crawler.openBrowser();
		crawler.loadPage();
		crawler.crawlData();
		crawler.closeBrowser();
		YoutubeData youtubeData = crawler.getYoutubeData();
		youtubeData.set_id(ObjectId.get());
		return repository.save(youtubeData);
	}
	
	@RequestMapping(value = "/facebook/{id}/{account}/{password}", method = RequestMethod.POST)
	public List<FacebookData> getFacebook(@PathVariable("id") String id,@PathVariable("account") String acccount, 
										  @PathVariable("password") String password) throws InterruptedException {
		FacebookCrawler crawler = new FacebookCrawler("https://www.facebook.com/groups/"+id);
		crawler.loginFacebook(acccount, password);
		crawler.loadPage();
		crawler.crawlData();
		crawler.closeBrowser();
		List<FacebookData> fbDatas = crawler.getFacebookDatas();
		facebookRepository.save(fbDatas);
		return fbDatas;
	}
}
