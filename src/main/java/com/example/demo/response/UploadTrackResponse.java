package com.example.demo.response;

public class UploadTrackResponse extends BaseResponse<String> {
	
	public UploadTrackResponse(String data, String errorMessage) {
		setData(data);
		setErrorMessage(errorMessage);
	}
	
	public UploadTrackResponse() {
		
	}

}
