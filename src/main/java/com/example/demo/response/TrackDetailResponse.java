package com.example.demo.response;

import com.example.demo.domain.GPX;

public class TrackDetailResponse extends BaseResponse<GPX> {
	
	public TrackDetailResponse(GPX data) {
		super(data);
	}
	
	public TrackDetailResponse(String errorMessage) {
		super(errorMessage);
	}

}
