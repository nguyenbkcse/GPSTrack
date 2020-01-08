package com.example.demo.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GPXOverviewResponse {
	
	private String id;
	private String gpxName;
	private String description;
	private String author;
	private String linkHref;
	private String linkText;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
	private Date time;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getGpxName() {
		return gpxName;
	}
	
	public void setGpxName(String gpxName) {
		this.gpxName = gpxName;
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
	
	public String getLinkHref() {
		return linkHref;
	}

	public void setLinkHref(String linkHref) {
		this.linkHref = linkHref;
	}

	public String getLinkText() {
		return linkText;
	}

	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}

	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}
	
	public GPXOverviewResponse() {
		
	}

	private GPXOverviewResponse(GPXOverviewResponseBuilder builder) {
		this.id = builder.id;
		this.gpxName = builder.gpxName;
		this.description = builder.description;
		this.author = builder.author;
		this.linkHref = builder.linkHref;
		this.linkText = builder.linkText;
		this.time = builder.time;
	}
	
	public static class GPXOverviewResponseBuilder {
		private String id;
		private String gpxName;
		private String description;
		private String author;
		private String linkHref;
		private String linkText;
		private Date time;
		
		public GPXOverviewResponseBuilder setId(String id) {
			this.id = id;
			return this;
		}
		
		public GPXOverviewResponseBuilder setGpxName(String gpxName) {
			this.gpxName = gpxName;
			return this;
		}
		
		public GPXOverviewResponseBuilder setDescription(String description) {
			this.description = description;
			return this;
		}
		
		public GPXOverviewResponseBuilder setAuthor(String author) {
			this.author = author;
			return this;
		}
		
		public GPXOverviewResponseBuilder setLinkHref(String linkHref) {
			this.linkHref = linkHref;
			return this;
		}
		
		public GPXOverviewResponseBuilder setLinkText(String linkText) {
			this.linkText = linkText;
			return this;
		}
		
		public GPXOverviewResponseBuilder setTime(Date time) {
			this.time = time;
			return this;
		}
		
		public GPXOverviewResponse build() {
			return new GPXOverviewResponse(this);
		}
	}

}
