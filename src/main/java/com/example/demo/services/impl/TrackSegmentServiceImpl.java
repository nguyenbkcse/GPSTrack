package com.example.demo.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.TrackSegment;
import com.example.demo.repository.TrackSegmentRepository;
import com.example.demo.services.TrackSegmentService;

@Service
public class TrackSegmentServiceImpl implements TrackSegmentService {
	
	@Autowired
	private TrackSegmentRepository trackSegmentRepository;

	@Override
	public Set<TrackSegment> findByGpsId(String id) {
		return trackSegmentRepository.findByGpsId(id);
	}

}
