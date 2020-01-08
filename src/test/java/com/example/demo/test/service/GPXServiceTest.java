package com.example.demo.test.service;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.constants.Constant;
import com.example.demo.domain.GPX;
import com.example.demo.repository.GPXRepository;
import com.example.demo.response.GPXOverviewResponse;
import com.example.demo.services.GPXService;
import com.example.demo.services.impl.GPXServiceImpl;
import com.example.demo.utils.GPXUtils;

@RunWith(SpringRunner.class)
public class GPXServiceTest {
	
	@MockBean
	GPXRepository gpxRepository;
	
	@Autowired
	GPXService gpxService;
	
	GPX gpx1 = GPXUtils.buildGPX(Constant.gpx1Name, Constant.gpx1Description, Constant.gpx1Author, Constant.gpx1LinkText, Constant.gpx1LinkHref, Constant.gpx1Time);
	GPX gpx2 = GPXUtils.buildGPX(Constant.gpx2Name, Constant.gpx2Description, Constant.gpx2Author, Constant.gpx2LinkText, Constant.gpx2LinkHref, Constant.gpx2Time);
	GPX gpx3 = GPXUtils.buildGPX(Constant.gpx3Name, Constant.gpx3Description, Constant.gpx3Author, Constant.gpx3LinkText, Constant.gpx3LinkHref, Constant.gpx3Time);
	
	List<GPX> gpxs = Arrays.asList(gpx2, gpx3, gpx1); //in order of field time desc
	
	@TestConfiguration
    public static class GPXServiceTestConfiguration {

        @Bean
        GPXService gpxService(){
            return new GPXServiceImpl();
        }
    }
	
	@Before
    public void setUp() {
		String gpx1Id = "1";
		String gpx2Id = "2";
		String gpx3Id = "3";
        Mockito.when(gpxRepository.saveAndFlush(any(GPX.class)))
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
        
        Mockito.when(gpxRepository.count()).thenReturn((long)gpxs.size());
        
        Mockito.when(gpxRepository.findOne(gpx1Id)).thenReturn(gpx1);
        Mockito.when(gpxRepository.findOne(gpx2Id)).thenReturn(gpx2);
        Mockito.when(gpxRepository.findOne(gpx3Id)).thenReturn(gpx3);
        
        Mockito.when(gpxRepository.findAllWithBasicInfo(any(Pageable.class))).thenAnswer(new Answer<List<Object[]>>() {

			@Override
			public List<Object[]> answer(InvocationOnMock invocation) throws Throwable {
				List<Object[]> records = new ArrayList<>();
				Pageable pageable = (Pageable)invocation.getArguments()[0];
				int pageSize = pageable.getPageSize();
				int pageNumber = pageable.getPageNumber();
				int totalElements = gpxs.size();
				int startIndex = pageNumber * pageSize;
				int endIndex = Math.min(startIndex + pageSize - 1, totalElements - 1);
				if (startIndex <= totalElements - 1 && endIndex <= totalElements - 1) {
					for (int i = startIndex; i <= endIndex; i++) {
						GPX currentGPX = gpxs.get(i);
						Object[] objects = new Object[7];
						objects[Constant.ID_INDEX] = currentGPX.getId();
						objects[Constant.GPX_NAME_INDEX] = currentGPX.getGpxName();
						objects[Constant.DESCRIPTION_INDEX] = currentGPX.getDescription();
						objects[Constant.AUTHOR_INDEX] = currentGPX.getAuthor();
						objects[Constant.LINK_HREF_INDEX] = currentGPX.getLinkHref();
						objects[Constant.LINK_TEXT_INDEX] = currentGPX.getLinkText();
						objects[Constant.TIME_INDEX] = currentGPX.getTime();
						records.add(objects);
					}
				}
				return records;
			}
		});
    }
	
	@Test
	public void testAddGpx() {
		Assert.assertNull(gpx1.getId());
		gpxService.add(gpx1);
		Assert.assertNotNull(gpx1.getId());
	}
	
	@Test
	public void testCountGpx() {
		Assert.assertTrue(gpxService.count() == gpxs.size());
	}
	
	@Test
	public void testFindById() {
		gpxService.add(gpx1);
		Assert.assertEquals(gpxService.findById(gpx1.getId()), gpx1);
	}
	
	@Test
	public void testFindAllBasicInfoWithPaging() {
		PageRequest pageRequest = GPXUtils.buildPageRequest(0, gpxs.size());
		List<GPXOverviewResponse> result = gpxService.findBasicInfoWithPaging(pageRequest);
		assertEqual(gpx2, result.get(0));
		assertEqual(gpx3, result.get(1));
		assertEqual(gpx1, result.get(2));
	}
	
	@Test
	public void testFindFirst2RecordBasicInfoWithPaging() {
		PageRequest pageRequest = GPXUtils.buildPageRequest(0, 2);
		List<GPXOverviewResponse> result = gpxService.findBasicInfoWithPaging(pageRequest);
		assertEqual(gpx2, result.get(0));
		assertEqual(gpx3, result.get(1));
	}
	
	@Test
	public void testFindLastRecordBasicInfoWithPaging() {
		PageRequest pageRequest = GPXUtils.buildPageRequest(1, 2);
		List<GPXOverviewResponse> result = gpxService.findBasicInfoWithPaging(pageRequest);
		assertEqual(gpx1, result.get(0));
	}
	
	private void assertEqual(GPX gpx, GPXOverviewResponse gpxOverviewResponse) {
		Assert.assertEquals(gpx.getId(), gpxOverviewResponse.getId());
		Assert.assertEquals(gpx.getGpxName(), gpxOverviewResponse.getGpxName());
		Assert.assertEquals(gpx.getDescription(), gpxOverviewResponse.getDescription());
		Assert.assertEquals(gpx.getAuthor(), gpxOverviewResponse.getAuthor());
		Assert.assertEquals(gpx.getLinkHref(), gpxOverviewResponse.getLinkHref());
		Assert.assertEquals(gpx.getLinkText(), gpxOverviewResponse.getLinkText());
		Assert.assertEquals(gpx.getTime(), gpxOverviewResponse.getTime());
	}
}
