package com.example.demo.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.WayPoint;

public interface WayPointRepository extends JpaRepository<WayPoint, Long> {
	
	Set<WayPoint> findByGpsId(String id);

}
