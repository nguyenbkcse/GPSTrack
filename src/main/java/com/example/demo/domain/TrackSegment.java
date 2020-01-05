package com.example.demo.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class TrackSegment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="gps_id", nullable=false)
    private GPS gps;
	
	@Column(name="gps_id", insertable = false, updatable = false)
	private String gpsId;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "trackSegment")
	private List<TrackPoint> trackPoints;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public GPS getGps() {
		return gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}

	public String getGpsId() {
		return gpsId;
	}

	public void setGpsId(String gpsId) {
		this.gpsId = gpsId;
	}

	public List<TrackPoint> getTrackPoints() {
		return trackPoints;
	}

	public void setTrackPoints(List<TrackPoint> trackPoints) {
		this.trackPoints = trackPoints;
	}
	
}
