package com.example.demo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class TrackPoint {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne
    @JoinColumn(name="track_segment_id", nullable=false)
    private TrackSegment trackSegment;
	
	@Column
	private double lat;
	
	@Column
	private double lon;
	
	@Column
	private double ele;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TrackSegment getTrackSegment() {
		return trackSegment;
	}

	public void setTrackSegment(TrackSegment trackSegment) {
		this.trackSegment = trackSegment;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getEle() {
		return ele;
	}

	public void setEle(double ele) {
		this.ele = ele;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
}
