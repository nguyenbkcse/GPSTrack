package com.example.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class WayPoint {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonIgnore
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="gpx_id", nullable=false)
	@JsonIgnore
    private GPX gpx;
	
	@Column
	private double lat;
	
	@Column
	private double lon;
	
	@Column
	private String name;
	
	@Column
	private String sym;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSym() {
		return sym;
	}

	public void setSym(String sym) {
		this.sym = sym;
	}
	
}
