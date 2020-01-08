package com.example.demo.test.repository;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.constants.Constant;
import com.example.demo.domain.GPX;
import com.example.demo.repository.GPXRepository;
import com.example.demo.utils.GPXUtils;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GPXRepositoryTest {
	
	@Autowired
	GPXRepository gpxRepository;
	
	GPX gpx1 = GPXUtils.buildGPX(Constant.gpx1Name, Constant.gpx1Description, Constant.gpx1Author, Constant.gpx1LinkText, Constant.gpx1LinkHref, Constant.gpx1Time);
	GPX gpx2 = GPXUtils.buildGPX(Constant.gpx2Name, Constant.gpx2Description, Constant.gpx2Author, Constant.gpx2LinkText, Constant.gpx2LinkHref, Constant.gpx2Time);
	GPX gpx3 = GPXUtils.buildGPX(Constant.gpx3Name, Constant.gpx3Description, Constant.gpx3Author, Constant.gpx3LinkText, Constant.gpx3LinkHref, Constant.gpx3Time);
	
	public void createGpxs() {
		gpxRepository.save(gpx1);
		gpxRepository.save(gpx2);
		gpxRepository.save(gpx3);
	}
	
	@Test
	public void testFindAllWithTimeOrderDesc() {
		createGpxs();
		PageRequest pageRequest = GPXUtils.buildPageRequest(0, 3);
		List<Object[]> records = gpxRepository.findAllWithBasicInfo(pageRequest);
		Assert.assertEquals(gpx2.getGpxName(), (String)records.get(0)[Constant.GPX_NAME_INDEX]);
		Assert.assertEquals(gpx3.getGpxName(), (String)records.get(1)[Constant.GPX_NAME_INDEX]);
		Assert.assertEquals(gpx1.getGpxName(), (String)records.get(2)[Constant.GPX_NAME_INDEX]);
		assertEquals(records.get(0), gpx2);
		assertEquals(records.get(1), gpx3);
		assertEquals(records.get(2), gpx1);
	}
	
	@Test
	public void testFindFirst2Records() {
		createGpxs();
		PageRequest pageRequest = GPXUtils.buildPageRequest(0, 2);
		List<Object[]> records = gpxRepository.findAllWithBasicInfo(pageRequest);
		Assert.assertEquals(gpx2.getGpxName(), (String)records.get(0)[Constant.GPX_NAME_INDEX]);
		Assert.assertEquals(gpx3.getGpxName(), (String)records.get(1)[Constant.GPX_NAME_INDEX]);
		assertEquals(records.get(0), gpx2);
		assertEquals(records.get(1), gpx3);
	}
	
	@Test
	public void testFindLastRecord() {
		createGpxs();
		PageRequest pageRequest = GPXUtils.buildPageRequest(1, 2);
		List<Object[]> records = gpxRepository.findAllWithBasicInfo(pageRequest);
		Assert.assertEquals(gpx1.getGpxName(), (String)records.get(0)[Constant.GPX_NAME_INDEX]);
		assertEquals(records.get(0), gpx1);
	}
	
	@Test
	public void testFindEmptyResult() {
		PageRequest pageRequest = GPXUtils.buildPageRequest(0, 3);
		List<Object[]> records = gpxRepository.findAllWithBasicInfo(pageRequest);
		Assert.assertTrue(records.isEmpty());
	}
	
	private void assertEquals(Object[] record, GPX gpx) {
		Assert.assertEquals(gpx.getGpxName(), (String)record[Constant.GPX_NAME_INDEX]);
		Assert.assertEquals(gpx.getId(), (String)record[Constant.ID_INDEX]);
		Assert.assertEquals(gpx.getDescription(), (String)record[Constant.DESCRIPTION_INDEX]);
		Assert.assertEquals(gpx.getAuthor(), (String)record[Constant.AUTHOR_INDEX]);
		Assert.assertEquals(gpx.getLinkHref(), (String)record[Constant.LINK_HREF_INDEX]);
		Assert.assertEquals(gpx.getLinkText(), (String)record[Constant.LINK_TEXT_INDEX]);
		Assert.assertEquals(gpx.getTime(), (Date)record[Constant.TIME_INDEX]);
		Assert.assertNull(gpx.getTrackSegments());
		Assert.assertNull(gpx.getWaypoints());
	}
	
}
