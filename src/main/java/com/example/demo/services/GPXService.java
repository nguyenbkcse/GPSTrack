package com.example.demo.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.demo.domain.GPX;
import com.example.demo.response.GPXOverviewResponse;

public interface GPXService {
	
	public GPX add(GPX gpx);
	
	public List<GPXOverviewResponse> findBasicInfoWithPaging(Pageable pageable);
	
	public GPX findById(String id);

}
