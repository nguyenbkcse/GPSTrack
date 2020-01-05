package com.example.demo.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GPSResponse {
	
	private String id;
	private String gpsName;
	private String description;
	private String author;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
	private Date time;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getGpsName() {
		return gpsName;
	}
	
	public void setGpsName(String gpsName) {
		this.gpsName = gpsName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}

	public GPSResponse(String id, String gpsName, String description, String author, Date time) {
		this.id = id;
		this.gpsName = gpsName;
		this.description = description;
		this.author = author;
		this.time = time;
	}

}
