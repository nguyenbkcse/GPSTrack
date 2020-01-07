package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.GPX;
import com.example.demo.response.GPXOverviewResponse;
import com.example.demo.response.LatestTracksResponse;
import com.example.demo.response.TrackCountResponse;
import com.example.demo.response.TrackDetailResponse;
import com.example.demo.response.UploadTrackResponse;
import com.example.demo.services.GPXService;
import com.example.demo.services.XMLParserService;

@RestController
@RequestMapping
public class GPXController {
	
	@Autowired
	GPXService gpxService;
	
	@Autowired
	XMLParserService xmlParserService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GPXController.class);
	
	@PostMapping("/upload")
	public ResponseEntity<UploadTrackResponse> uploadGPXFile(@RequestParam MultipartFile gpxFile) {
		GPX gpx;
		try {
			gpx = xmlParserService.convertXMLContentToGPX(gpxFile);
		} catch (Exception e) {
			LOGGER.error("Error parsing gpx file", e);
			return new ResponseEntity<>(new UploadTrackResponse(null, "Cannot read file content"), HttpStatus.BAD_REQUEST);
		}
		GPX insertedGPX = gpxService.add(gpx);
		if (insertedGPX == null) {
			return new ResponseEntity<>(new UploadTrackResponse(null, "Cannot upload file into system"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new UploadTrackResponse(insertedGPX.getId(), null), HttpStatus.OK);
	}
	
	@GetMapping("/latest")
	public ResponseEntity<LatestTracksResponse> getLatestTracks(@RequestParam(defaultValue = "0") int offset, @RequestParam (defaultValue = "20") int limit) {
		Sort sort = new Sort(Sort.Direction.DESC, "time");
		PageRequest pageRequest = new PageRequest(offset, limit, sort);
		List<GPXOverviewResponse> result = gpxService.findBasicInfoWithPaging(pageRequest);
		return new ResponseEntity<>(new LatestTracksResponse(result), HttpStatus.OK);
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<TrackDetailResponse> getTrackDetail(@PathVariable(name = "id") String id) {
		try {
			GPX gpx = gpxService.findById(id);
			return new ResponseEntity<>(new TrackDetailResponse(gpx), HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Error getting track detail id {}", id, e);
			return new ResponseEntity<>(new TrackDetailResponse("Error while getting track detail"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/count")
	public ResponseEntity<TrackCountResponse> getCount() {
		return new ResponseEntity<>(new TrackCountResponse(gpxService.count()), HttpStatus.OK);
	}

}
