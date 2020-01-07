package com.example.demo.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.example.demo.domain.GPX;

public interface XMLParserService {
	
	GPX convertXMLContentToGPX(MultipartFile gpxFile) throws SAXException, IOException;

}
