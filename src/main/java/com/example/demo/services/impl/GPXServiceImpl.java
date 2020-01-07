package com.example.demo.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.domain.GPX;
import com.example.demo.repository.GPXRepository;
import com.example.demo.response.GPXOverviewResponse;
import com.example.demo.response.GPXOverviewResponse.GPXOverviewResponseBuilder;
import com.example.demo.services.GPXService;

@Service
public class GPXServiceImpl implements GPXService {
	
	private static final int ID_INDEX = 0;
	private static final int GPX_NAME_INDEX = 1;
	private static final int DESCRIPTION_INDEX = 2;
	private static final int AUTHOR_INDEX = 3;
	private static final int LINK_HREF_INDEX = 4;
	private static final int LINK_TEXT_INDEX = 5;
	private static final int TIME_INDEX = 6;
	
	@Autowired
	private GPXRepository gpxRepository;

	@Override
	public GPX add(GPX gpx) {
		return gpxRepository.saveAndFlush(gpx);
	}

	@Override
	public List<GPXOverviewResponse> findBasicInfoWithPaging(Pageable pageable) {
		List<Object[]> records = gpxRepository.findAllWithBasicInfo(pageable);
		return records.stream().map(this::buildGPXOverviewResponse).collect(Collectors.toList());
	}
	
	private GPXOverviewResponse buildGPXOverviewResponse(Object[] objects) {
		GPXOverviewResponseBuilder builder = new GPXOverviewResponseBuilder();
		builder.setId((String)objects[ID_INDEX])
				.setGpxName((String)objects[GPX_NAME_INDEX])
				.setDescription((String)objects[DESCRIPTION_INDEX])
				.setAuthor((String)objects[AUTHOR_INDEX])
				.setLinkHref((String)objects[LINK_HREF_INDEX])
				.setLinkText((String)objects[LINK_TEXT_INDEX])
				.setTime((Date)objects[TIME_INDEX]);
		return builder.build();
	}

	@Override
	public GPX findById(String id) {
		return gpxRepository.findOne(id);
	}

}
