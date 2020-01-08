package com.example.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.demo.domain.GPX;

public class GPXUtils {
	
	private GPXUtils() {
		
	}
	
	public static GPX buildGPX(String name, String description, String author, String linkText, String linkHref, String time) {
		GPX gpx = new GPX();
		gpx.setGpxName(name);
		gpx.setDescription(description);
		gpx.setAuthor(author);
		gpx.setLinkText(linkText);
		gpx.setLinkHref(linkHref);
		Date date = null;
	    try {
			date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    gpx.setTime(date);
		return gpx;
	}
	
	public static PageRequest buildPageRequest(int offset, int limit) {
		Sort sort = new Sort(Sort.Direction.DESC, "time");
		return new PageRequest(offset, limit, sort);
	}
}
