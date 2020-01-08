package com.example.demo.response;

import java.util.List;

public class LatestTracksResponse extends BaseResponse<List<GPXOverviewResponse>> {
	
	public LatestTracksResponse(List<GPXOverviewResponse> data) {
		super(data);
	}
	
	public LatestTracksResponse(String errorMessage) {
		super(errorMessage);
	}
	
	public LatestTracksResponse() {
		
	}

}
