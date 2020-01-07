package com.example.demo.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class GPX {
	
	@Id
    @GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "gpx_id")
	@JsonIgnore
	private String id;
	
	@Column(name = "gpx_name")
	private String gpxName;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	@Column
	private String author;
	
	@Column
	private String linkHref;
	
	@Column
	private String linkText;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
	private Date time;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "gpx", fetch = FetchType.EAGER)
	@OrderBy("id")
	private Set<WayPoint> waypoints;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "gpx", fetch = FetchType.EAGER)
	@OrderBy("id")
	private Set<TrackSegment> trackSegments;

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

	public Set<WayPoint> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(Set<WayPoint> waypoints) {
		this.waypoints = waypoints;
	}

	public Set<TrackSegment> getTrackSegments() {
		return trackSegments;
	}

	public void setTrackSegments(Set<TrackSegment> trackSegments) {
		this.trackSegments = trackSegments;
	}
	
}
