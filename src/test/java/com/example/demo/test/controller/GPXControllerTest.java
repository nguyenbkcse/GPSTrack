package com.example.demo.test.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.constants.Constant;
import com.example.demo.domain.GPX;
import com.example.demo.response.GPXOverviewResponse;
import com.example.demo.response.GPXOverviewResponse.GPXOverviewResponseBuilder;
import com.example.demo.response.LatestTracksResponse;
import com.example.demo.response.TrackCountResponse;
import com.example.demo.response.TrackDetailResponse;
import com.example.demo.response.UploadTrackResponse;
import com.example.demo.services.GPXService;
import com.example.demo.services.XMLParserService;
import com.example.demo.utils.GPXUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest
public class GPXControllerTest {
	
	@MockBean
	GPXService gpxService;
	
	@MockBean
	XMLParserService xmlParserService;
	
	@Autowired
    private MockMvc mvc;

	ObjectMapper objectMapper = new ObjectMapper();
	
	GPX gpx1 = GPXUtils.buildGPX(Constant.gpx1Name, Constant.gpx1Description, Constant.gpx1Author, Constant.gpx1LinkText, Constant.gpx1LinkHref, Constant.gpx1Time);
	GPX gpx2 = GPXUtils.buildGPX(Constant.gpx2Name, Constant.gpx2Description, Constant.gpx2Author, Constant.gpx2LinkText, Constant.gpx2LinkHref, Constant.gpx2Time);
	GPX gpx3 = GPXUtils.buildGPX(Constant.gpx3Name, Constant.gpx3Description, Constant.gpx3Author, Constant.gpx3LinkText, Constant.gpx3LinkHref, Constant.gpx3Time);
	String gpx1Id = "1";
	String gpx2Id = "2";
	String gpx3Id = "3";
	
	@Before
	public void init() {
		Mockito.when(gpxService.add(any(GPX.class)))
        .thenAnswer(new Answer<GPX>() {

			@Override
			public GPX answer(InvocationOnMock invocation) throws Throwable {
				GPX gpx = (GPX) invocation.getArguments()[0];
				if (gpx.getGpxName().equals(gpx1.getGpxName())) {
					gpx.setId(gpx1Id);
				} else if (gpx.getGpxName().equals(gpx2.getGpxName())) {
					gpx.setId(gpx2Id);
				} else if (gpx.getGpxName().equals(gpx3.getGpxName())) {
					gpx.setId(gpx3Id);
				}
				return gpx;
			}
		});
		
		Mockito.when(gpxService.findById(any(String.class)))
        .thenAnswer(new Answer<GPX>() {

			@Override
			public GPX answer(InvocationOnMock invocation) throws Throwable {
				String id = (String) invocation.getArguments()[0];
				if (id.equals(gpx1Id)) {
					return gpx1;
				} else if (id.equals(gpx2Id)) {
					return gpx2;
				} else if (id.equals(gpx3Id)) {
					return gpx3;
				}
				return null;
			}
		});
	}
	
	@Test
	public void testUploadInvalidFile() throws Exception {
		MockMultipartFile mockFile = mockFile("sample.gpx");
		when(xmlParserService.convertXMLContentToGPX(mockFile)).thenThrow(new IOException("Cannot read file"));
		MvcResult result = mvc.perform(fileUpload("/upload").file(mockFile))
			.andReturn();
		Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
		UploadTrackResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), UploadTrackResponse.class);
		Assert.assertEquals("Cannot read file content", response.getErrorMessage());
	}
	
	@Test
	public void testUploadValidFile() throws Exception {
		MockMultipartFile mockFile = mockFile("sample.gpx");
		when(xmlParserService.convertXMLContentToGPX(mockFile)).thenReturn(gpx1);
		MvcResult result = mvc.perform(fileUpload("/upload").file(mockFile))
			.andReturn();
		Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		UploadTrackResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), UploadTrackResponse.class);
		Assert.assertEquals(gpx1.getId(), response.getData());
	}
	
	@Test
	public void testGetCount() throws Exception {
		upload3Gpx();
		MvcResult result = mvc.perform(get("/count")).andReturn();
		Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		TrackCountResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), TrackCountResponse.class);
		Assert.assertTrue(response.getData() == 3);
	}
	
	@Test
	public void testGetEmptyLatestTracks() throws Exception {
		when(gpxService.findBasicInfoWithPaging(any(Pageable.class))).thenReturn(Collections.emptyList());
		MvcResult result = mvc.perform(get("/latest").param("offset", "0").param("limit", "10")).andReturn();
		Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		LatestTracksResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), LatestTracksResponse.class);
		Assert.assertTrue(response.getData().isEmpty());
	}
	
	@Test
	public void testGetFullLatestTracks() throws Exception {
		List<GPXOverviewResponse> gpxs = new ArrayList<>();
		gpxs.add(buildResponseFrom(gpx1));
		gpxs.add(buildResponseFrom(gpx2));
		gpxs.add(buildResponseFrom(gpx3));
		when(gpxService.findBasicInfoWithPaging(any(Pageable.class))).thenReturn(gpxs);
		MvcResult result = mvc.perform(get("/latest").param("offset", "0").param("limit", "10")).andReturn();
		Assert.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		LatestTracksResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), LatestTracksResponse.class);
		assertEquals(gpx1, response.getData().get(0));
		assertEquals(gpx2, response.getData().get(1));
		assertEquals(gpx3, response.getData().get(2));
	}
	
	@Test
	public void testGetTrackDetail() throws Exception {
		MockMultipartFile mockFile = mockFile("sample.gpx");
		when(xmlParserService.convertXMLContentToGPX(mockFile)).thenReturn(gpx1);
		MvcResult result = mvc.perform(get("/detail/{id}", gpx1Id)).andReturn();
		TrackDetailResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), TrackDetailResponse.class);
		assertEquals(gpx1, response.getData());
	}
	
	private GPXOverviewResponse buildResponseFrom(GPX gpx) {
		GPXOverviewResponseBuilder builder = new GPXOverviewResponseBuilder();
		builder.setId(gpx.getId());
		builder.setAuthor(gpx.getAuthor());
		builder.setDescription(gpx.getDescription());
		builder.setGpxName(gpx.getGpxName());
		builder.setLinkHref(gpx.getLinkHref());
		builder.setLinkText(gpx.getLinkText());
		builder.setTime(gpx.getTime());
		return builder.build();
	}
	
	private void assertEquals(GPX gpx, GPXOverviewResponse gpxOverviewResponse) {
		Assert.assertEquals(gpx.getId(), gpxOverviewResponse.getId());
		Assert.assertEquals(gpx.getGpxName(), gpxOverviewResponse.getGpxName());
		Assert.assertEquals(gpx.getDescription(), gpxOverviewResponse.getDescription());
		Assert.assertEquals(gpx.getAuthor(), gpxOverviewResponse.getAuthor());
		Assert.assertEquals(gpx.getLinkHref(), gpxOverviewResponse.getLinkHref());
		Assert.assertEquals(gpx.getLinkText(), gpxOverviewResponse.getLinkText());
		Assert.assertEquals(gpx.getTime(), gpxOverviewResponse.getTime());
	}
	
	private void assertEquals(GPX first, GPX second) {
		Assert.assertTrue(first.getAuthor().equals(second.getAuthor()));
		Assert.assertTrue(first.getDescription().equals(second.getDescription()));
		Assert.assertTrue(first.getGpxName().equals(second.getGpxName()));
		Assert.assertTrue(first.getLinkHref().equals(second.getLinkHref()));
		Assert.assertTrue(first.getLinkText().equals(second.getLinkText()));
		Assert.assertTrue(first.getTime().equals(second.getTime()));
		Assert.assertNull(first.getTrackSegments());
		Assert.assertNull(second.getTrackSegments());
		Assert.assertNull(first.getWaypoints());
		Assert.assertNull(second.getWaypoints());
	}
	
	private void upload3Gpx() throws Exception {
		MockMultipartFile mockFile1 = mockFile("sample.gpx");
		when(xmlParserService.convertXMLContentToGPX(mockFile1)).thenReturn(gpx1);
		mvc.perform(fileUpload("/upload").file(mockFile1));
		MockMultipartFile mockFile2 = mockFile("sample.gpx");
		when(xmlParserService.convertXMLContentToGPX(mockFile2)).thenReturn(gpx2);
		mvc.perform(fileUpload("/upload").file(mockFile2));
		MockMultipartFile mockFile3 = mockFile("sample.gpx");
		when(xmlParserService.convertXMLContentToGPX(mockFile3)).thenReturn(gpx3);
		mvc.perform(fileUpload("/upload").file(mockFile3));
		when(gpxService.count()).thenReturn(3L);
	}
	
	private MockMultipartFile mockFile(String fileName) throws IOException {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
		return new MockMultipartFile("gpxFile", inputStream);
	}

}
