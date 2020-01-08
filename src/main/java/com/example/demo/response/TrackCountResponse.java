package com.example.demo.response;

public class TrackCountResponse extends BaseResponse<Long> {

	public TrackCountResponse(Long data) {
		super(data);
	}
	
	public TrackCountResponse(String errorMessage) {
		super(errorMessage);
	}
	
	public TrackCountResponse() {
		
	}
}
