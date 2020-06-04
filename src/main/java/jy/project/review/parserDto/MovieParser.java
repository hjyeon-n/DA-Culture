package jy.project.review.parserDto;

public class MovieParser {
	private String title;
	private String image;
	private String director;
	private String actor;
	private String pubDate;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public MovieParser(String title, String image, String director, String actor, String pubDate) {
		super();
		this.title = title;
		this.image = image;
		this.director = director;
		this.actor = actor;
		this.pubDate = pubDate;
	}
	public MovieParser() {
		super();
	}
	@Override
	public String toString() {
		return "moviePaser [title=" + title + ", image=" + image + ", director=" + director + ", actor=" + actor
				+ ", pubDate=" + pubDate + "]";
	}
}
