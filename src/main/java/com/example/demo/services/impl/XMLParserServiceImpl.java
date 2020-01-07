package com.example.demo.services.impl;

import java.time.Instant;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.example.demo.domain.GPX;
import com.example.demo.domain.TrackPoint;
import com.example.demo.domain.TrackSegment;
import com.example.demo.domain.WayPoint;
import com.example.demo.services.XMLParserService;

@Service
public class XMLParserServiceImpl implements XMLParserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(XMLParserServiceImpl.class);
	private static final String META_DATA_TAG = "metadata";
	private static final String NAME_TAG = "name";
	private static final String DESCRIPTION_TAG = "desc";
	private static final String TIME_TAG = "time";
	private static final String AUTHOR_TAG = "author";
	private static final String LINK_TAG = "link";
	private static final String TEXT_TAG = "text";
	private static final String WAYPOINT_TAG = "wpt";
	private static final String SYM_TAG = "sym";
	private static final String TRACK_TAG = "trk";
	private static final String TRACK_SEGMENT_TAG = "trkseg";
	private static final String TRACK_POINT_TAG = "trkpt";
	private static final String ELEVATION_TAG = "ele";
	private static final String LAT_ATTRIBUTE = "lat";
	private static final String LON_ATTRIBUTE = "lon";
	private static final String HREF_ATTRIBUTE = "href";
	
	@Autowired
	DocumentBuilder documentBuilder;
	
	@Override
	public GPX convertXMLContentToGPX(MultipartFile gpxFile) {
		GPX gpx;
		try {
			gpx = new GPX();
			Document doc = documentBuilder.parse(gpxFile.getInputStream());
			doc.getDocumentElement().normalize();
			getInfoFromMetaData(doc, gpx);
			getInfoFromWayPoints(doc, gpx);
			getInfoFromTrackPoints(doc, gpx);
		} catch (Exception e) {
			gpx = null;
			LOGGER.error("Error parsing xml file", e);
		}
        return gpx;
	}
	
	private void getInfoFromMetaData(Document doc, GPX gpx) {
		Element metaDataNode = (Element)doc.getElementsByTagName(META_DATA_TAG).item(0);
		gpx.setGpxName(metaDataNode.getElementsByTagName(NAME_TAG).item(0).getTextContent());
		gpx.setDescription(metaDataNode.getElementsByTagName(DESCRIPTION_TAG).item(0).getTextContent());
		gpx.setAuthor(metaDataNode.getElementsByTagName(AUTHOR_TAG).item(0).getTextContent());
		gpx.setTime(getDateFromString(metaDataNode.getElementsByTagName(TIME_TAG).item(0).getTextContent()));
		Element linkNode = (Element) metaDataNode.getElementsByTagName(LINK_TAG).item(0);
		Element textNode = (Element) linkNode.getElementsByTagName(TEXT_TAG).item(0);
		gpx.setLinkHref(linkNode.getAttribute(HREF_ATTRIBUTE));
		gpx.setLinkText(textNode.getTextContent());
	}
	
	private void getInfoFromWayPoints(Document doc, GPX gpx) {
		NodeList nodeList = doc.getElementsByTagName(WAYPOINT_TAG);
		Set<WayPoint> waypoints = new LinkedHashSet<>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element element = (Element)nodeList.item(i);
			WayPoint waypoint = new WayPoint();
			waypoint.setGpx(gpx);
	        waypoint.setName(element.getElementsByTagName(NAME_TAG).item(0).getTextContent());
	        waypoint.setSym(element.getElementsByTagName(SYM_TAG).item(0).getTextContent());
	        waypoint.setLat(Double.parseDouble(element.getAttribute(LAT_ATTRIBUTE)));
	        waypoint.setLon(Double.parseDouble(element.getAttribute(LON_ATTRIBUTE)));
	        waypoints.add(waypoint);
		}
		gpx.setWaypoints(waypoints);
	}
	
	private void getInfoFromTrackPoints(Document doc, GPX gpx) {
		Element trackNode = (Element)doc.getElementsByTagName(TRACK_TAG).item(0);
		NodeList trackSegmentNodes = trackNode.getElementsByTagName(TRACK_SEGMENT_TAG);
		Set<TrackSegment> trackSegments = new LinkedHashSet<>();
		for (int i = 0; i < trackSegmentNodes.getLength(); i++) {
			Element trackSegmentNode = (Element) trackSegmentNodes.item(i);
			TrackSegment trackSegment = new TrackSegment();
			trackSegment.setGpx(gpx);
			NodeList trackPointNodes = trackSegmentNode.getElementsByTagName(TRACK_POINT_TAG);
			Set<TrackPoint> trackPoints = new LinkedHashSet<>();
			for (int j = 0; j < trackPointNodes.getLength(); j++) {
				Element trackPointNode = (Element)trackPointNodes.item(j);
				TrackPoint trackPoint = new TrackPoint();
				trackPoint.setLat(Double.parseDouble(trackPointNode.getAttribute(LAT_ATTRIBUTE)));
				trackPoint.setLon(Double.parseDouble(trackPointNode.getAttribute(LON_ATTRIBUTE)));
				trackPoint.setEle(Double.parseDouble(trackPointNode.getElementsByTagName(ELEVATION_TAG).item(0).getTextContent()));
				trackPoint.setTime(getDateFromString(trackPointNode.getElementsByTagName(TIME_TAG).item(0).getTextContent()));
		        trackPoint.setTrackSegment(trackSegment);
		        trackPoints.add(trackPoint);
			}
			trackSegment.setTrackPoints(trackPoints);
			trackSegments.add(trackSegment);
		}
		gpx.setTrackSegments(trackSegments);
	}
	
	private Date getDateFromString(String dateString) {
		Instant instant = Instant.parse(dateString);
		return Date.from(instant);
	}

}
