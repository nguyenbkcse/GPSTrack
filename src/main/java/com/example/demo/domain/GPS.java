package com.example.demo.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class GPS {
	
	@Id
    @GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "gps_id")
	private String id;
	
	@Column(name = "gps_name")
	private String gpsName;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	@Column
	private String author;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "gps")
	private List<WayPoint> waypoints;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "gps")
	private List<TrackSegment> trackSegments;

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

	public List<WayPoint> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(List<WayPoint> waypoints) {
		this.waypoints = waypoints;
	}

	public List<TrackSegment> getTrackSegments() {
		return trackSegments;
	}

	public void setTrackSegments(List<TrackSegment> trackSegments) {
		this.trackSegments = trackSegments;
	}
	
}
