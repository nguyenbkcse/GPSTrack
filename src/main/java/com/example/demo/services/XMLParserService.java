package com.example.demo.services;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.GPS;

public interface XMLParserService {
	
	GPS convertXMLContentToGPS(MultipartFile gpsFile);

}