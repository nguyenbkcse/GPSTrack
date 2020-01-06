package com.example.demo.response;

import com.example.demo.domain.GPS;

public class TrackDetailResponse extends BaseResponse<GPS> {
	
	public TrackDetailResponse(GPS data) {
		super(data);
	}
	
	public TrackDetailResponse(String message) {
		super(message);
	}

}
