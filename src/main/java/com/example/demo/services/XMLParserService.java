package com.example.demo.services;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.domain.GPX;

public interface XMLParserService {
	
	GPX convertXMLContentToGPX(MultipartFile gpxFile);

}
