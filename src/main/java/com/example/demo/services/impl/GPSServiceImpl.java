package com.example.demo.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.domain.GPS;
import com.example.demo.repository.GPSRepository;
import com.example.demo.response.GPSResponse;
import com.example.demo.services.GPSService;

@Service
public class GPSServiceImpl implements GPSService {
	
	@Autowired
	private GPSRepository gpsRepository;

	@Override
	public GPS add(GPS gps) {
		return gpsRepository.saveAndFlush(gps);
	}

	@Override
	public List<GPSResponse> findBasicInfoWithPaging(Pageable pageable) {
		return gpsRepository.findAllWithBasicInfo(pageable);
	}

	@Override
	public GPS findById(String id) {
		return gpsRepository.findOne(id);
	}

}
