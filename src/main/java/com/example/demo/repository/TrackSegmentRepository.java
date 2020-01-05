package com.example.demo.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.TrackSegment;

public interface TrackSegmentRepository extends JpaRepository<TrackSegment, Long> {
	
	Set<TrackSegment> findByGpsId(String id);

}
