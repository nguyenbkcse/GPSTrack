package com.example.demo.services;

import java.util.Set;

import com.example.demo.domain.TrackSegment;

public interface TrackSegmentService {
	
	Set<TrackSegment> findByGpsId(String id);

}
