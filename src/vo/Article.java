package vo;

import java.util.Date;

public class Article {

	private Integer number;
	private Writer writer;
	private String title;
	private Date regdate;
	private Date modifiedDate;
	private int readCount;
	
	
	
	
	public Article(Integer number, Writer writer, String title, Date regdate, Date modifiedDate, int readCount) {
		super();
		this.number = number;
		this.writer = writer;
		this.title = title;
		this.regdate = regdate;
		this.modifiedDate = modifiedDate;
		this.readCount = readCount;
	}
	public Integer getNumber() {
		return number;
	}
	public Writer getWriter() {
		return writer;
	}
	public String getTitle() {
		return title;
	}
	public Date getRegdate() {
		return regdate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public int getReadCount() {
		return readCount;
	}
	
	
}
