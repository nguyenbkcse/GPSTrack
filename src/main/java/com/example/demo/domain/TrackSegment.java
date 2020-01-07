package com.example.demo.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TrackSegment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonIgnore
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="gpx_id", nullable=false)
	@JsonIgnore
    private GPX gpx;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "trackSegment", fetch = FetchType.EAGER)
	@OrderBy("id")
	private Set<TrackPoint> trackPoints;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public GPX getGpx() {
		return gpx;
	}

	public void setGpx(GPX gpx) {
		this.gpx = gpx;
	}

	public Set<TrackPoint> getTrackPoints() {
		return trackPoints;
	}

	public void setTrackPoints(Set<TrackPoint> trackPoints) {
		this.trackPoints = trackPoints;
	}
	
}
