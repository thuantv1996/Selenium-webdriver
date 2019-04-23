package edu.uit.com;

public class YoutubeConstant {
	public static final String PATH_CHROME_EXE = "E:\\git\\Selenium-webdriver\\chromedriver_win32\\chromedriver.exe";
	public static final long TIME_OUT = 1800;
	public static final String DIS_NOTIFICATIONS = "profile.default_content_setting_values.notifications";

	/**
	 * xPath
	 */
	public static final String XP_TITLE = "//*[@id=\"container\"]/h1/yt-formatted-string";
	public static final String XP_DESCRIPTION = "//*[@id=\"description\"]/yt-formatted-string";
	public static final String XP_NUM_VIEW = "//*[@id=\"count\"]/yt-view-count-renderer/span[1]";
	public static final String XP_LIKE = "//*[@id=\"text\"]";
	public static final String XP_DISLIKE = "//*[@id=\"text\"]";
	public static final String XP_CHANNEL = "//*[@id=\"owner-name\"]/a";
	public static final String XP_NUM_CMT = "//*[@id=\"count\"]/yt-formatted-string";
	public static final String XP_CMT = "//*[@id=\"comment\"]";
	public static final String XP_DES_MORE = "//*[@id=\"more\"]";
	/**
	 * id
	 */
	public static final String ID_USER_CMT = "author-text";
	public static final String ID_CONTENT_CMT = "content-text";
	public static final String ID_LIKE_CMT = "vote-count-middle";
}
