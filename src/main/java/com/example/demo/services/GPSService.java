package com.example.demo.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.demo.domain.GPS;
import com.example.demo.response.GPSResponse;

public interface GPSService {
	
	public GPS add(GPS gps);
	
	public List<GPSResponse> findBasicInfoWithPaging(Pageable pageable);
	
	public GPS findById(String id);

}
