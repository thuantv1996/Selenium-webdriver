package edu.uit.app;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uit.com.FacebookConstant;
import edu.uit.dao.FacebookDataRepository;
import edu.uit.dao.YoutubeDataRepository;
import edu.uit.models.FacebookData;
import edu.uit.models.YoutubeData;
import edu.uit.youtube_crawler.FacebookCrawler;
import edu.uit.youtube_crawler.YoutubeCrawler;

@RestController
@RequestMapping(value = "/crawl", method = RequestMethod.GET)
public class HomeController {
	@Autowired
	private YoutubeDataRepository repository;
	@Autowired
	private FacebookDataRepository facebookRepository;
	
	@RequestMapping(value = "/youtube/{id}", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/facebook", method = RequestMethod.GET)
	public List<FacebookData> getFacebook() throws InterruptedException {
		FacebookCrawler crawler = new FacebookCrawler("https://www.facebook.com/groups/126322494891797");
		crawler.loginFacebook(FacebookConstant.USER, FacebookConstant.PASSWORD);
		crawler.loadPage();
		crawler.crawlData();
		crawler.closeBrowser();
		List<FacebookData> fbDatas = crawler.getFacebookDatas();
		// return facebookRepository.save(fbDatas);
		return fbDatas;
	}
}
