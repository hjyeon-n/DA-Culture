package jy.project.review.parserDto;

public class BookParser {
	private String author;
	private String image;
	private String title;
	private String publisher;
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public BookParser(String title, String image, String author, String publisher) {
		super();
		this.author = author;
		this.image = image;
		this.title = title;
		this.publisher = publisher;
	}
	public BookParser() {
		super();
	}
	@Override
	public String toString() {
		return "BookParser [author=" + author + ", image=" + image + ", title=" + title + ", publisher=" + publisher
				+ "]";
	}
}
