package com.example.demo.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.WayPoint;
import com.example.demo.repository.WayPointRepository;
import com.example.demo.services.WayPointService;

@Service
public class WayPointServiceImpl implements WayPointService {
	
	@Autowired
	private WayPointRepository wayPointRepository;

	@Override
	public Set<WayPoint> findByGpsId(String id) {
		return wayPointRepository.findByGpsId(id);
	}
	
}
