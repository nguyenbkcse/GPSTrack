package com.example.demo.test.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;

import com.example.demo.domain.GPX;
import com.example.demo.services.XMLParserService;
import com.example.demo.services.impl.XMLParserServiceImpl;

@RunWith(SpringRunner.class)
public class XMLParserServiceTest {
	
	@Autowired
	XMLParserService xmlParserService;
	
	@TestConfiguration
    public static class XMLParserServiceTestConfiguration {

        @Bean
        DocumentBuilder documentBuilder() throws ParserConfigurationException {
        	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    		return factory.newDocumentBuilder();
        }
        
        @Bean
        XMLParserService xmlParserService() throws ParserConfigurationException {
    		return new XMLParserServiceImpl();
        }
    }
	
	@Test
	public void testConvertXMLContentToGPX() throws SAXException, IOException {
		String fileName = "sample.gpx";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
		MockMultipartFile mockMultipartFile = new MockMultipartFile(fileName, inputStream);
		GPX gpx = xmlParserService.convertXMLContentToGPX(mockMultipartFile);
		Assert.assertEquals("Bardenas Reales: Piskerra y el Paso de los Ciervos", gpx.getGpxName());
		Assert.assertEquals("http://www.oruxmaps.com", gpx.getLinkHref());
		Assert.assertEquals("OruxMaps", gpx.getLinkText());
		Instant instant = Instant.parse("2017-10-22T09:41:33Z");
		Assert.assertEquals(Date.from(instant), gpx.getTime());
		Assert.assertEquals(1, gpx.getTrackSegments().size());
		gpx.getTrackSegments().forEach(segment -> {
			Assert.assertEquals(1245, segment.getTrackPoints().size());
		});
		Assert.assertEquals(15, gpx.getWaypoints().size());
	}

}
