package com.example.demo.response;

import java.util.List;

public class LatestTracksResponse extends BaseResponse<List<GPSResponse>> {
	
	public LatestTracksResponse(List<GPSResponse> data) {
		super(data);
	}
	
	public LatestTracksResponse(String message) {
		super(message);
	}

}
