package edu.uit.app;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.uit.dao.YoutubeDataRepository;
import edu.uit.models.YoutubeData;
import edu.uit.youtube_crawler.YoutubeCrawler;

@RestController
@RequestMapping(value = "/crawl", method = RequestMethod.GET)
public class HomeController {
	@Autowired
	private YoutubeDataRepository repository;
	
	@RequestMapping(value = "/youtube/{id}", method = RequestMethod.GET)
	public YoutubeData getTitle(@PathVariable("id") String id) throws InterruptedException {
		YoutubeCrawler crawler = new YoutubeCrawler("https://www.youtube.com/watch?v="+id);
		crawler.run();
		crawler.LoadPage();
		crawler.getTitle();
		crawler.getChannel();
		crawler.getView();
		crawler.getLike();
		crawler.getDislike();
		crawler.getNumberComment();
		crawler.getComment();
		crawler.CloseBrowser();
		YoutubeData youtubeData = crawler.getYoutubeData();
		youtubeData.set_id(ObjectId.get());
		return repository.save(youtubeData);
	}
}
