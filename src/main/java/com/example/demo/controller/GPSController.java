package com.example.demo.controller;

import java.util.List;

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

import com.example.demo.domain.GPS;
import com.example.demo.response.GPSResponse;
import com.example.demo.response.LatestTracksResponse;
import com.example.demo.response.TrackDetailResponse;
import com.example.demo.response.UploadTrackResponse;
import com.example.demo.services.GPSService;
import com.example.demo.services.XMLParserService;

@RestController
@RequestMapping
public class GPSController {
	
	@Autowired
	GPSService gpsService;
	
	@Autowired
	XMLParserService xmlParserService;
	
	@PostMapping("/upload")
	public ResponseEntity<UploadTrackResponse> uploadGPSFile(@RequestParam MultipartFile gpsFile) {
		GPS gps = xmlParserService.convertXMLContentToGPS(gpsFile);
		if (gps == null) {
			return new ResponseEntity<>(new UploadTrackResponse(null, "Cannot read file content"), HttpStatus.BAD_REQUEST);
		}
		GPS insertedGPS = gpsService.add(gps);
		if (insertedGPS == null) {
			return new ResponseEntity<>(new UploadTrackResponse(null, "Cannot upload file into system"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new UploadTrackResponse(insertedGPS.getId(), null), HttpStatus.OK);
	}
	
	@GetMapping("/latest")
	public ResponseEntity<LatestTracksResponse> getLatestTracks(@RequestParam(defaultValue = "0") int offset, @RequestParam (defaultValue = "20") int limit) {
		Sort sort = new Sort(Sort.Direction.DESC, "time");
		PageRequest pageRequest = new PageRequest(offset, limit, sort);
		List<GPSResponse> result = gpsService.findBasicInfoWithPaging(pageRequest);
		return new ResponseEntity<>(new LatestTracksResponse(result), HttpStatus.OK);
	}
	
	@GetMapping("/detail/{id}")
	public ResponseEntity<TrackDetailResponse> getTrackDetail(@PathVariable(name = "id") String id) {
		GPS gps = gpsService.findById(id);
		return new ResponseEntity<>(new TrackDetailResponse(gps), HttpStatus.OK);
	}

}
