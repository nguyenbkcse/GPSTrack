package com.example.demo.response;

public class UploadTrackResponse extends BaseResponse<String> {
	
	public UploadTrackResponse(String data, String message) {
		setData(data);
		setMessage(message);
	}

}
