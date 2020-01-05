package com.example.demo.services;

import java.util.Set;

import com.example.demo.domain.WayPoint;

public interface WayPointService {
	
	Set<WayPoint> findByGpsId(String id);

}
