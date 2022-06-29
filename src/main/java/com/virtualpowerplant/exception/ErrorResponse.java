package com.virtualpowerplant.exception;

import lombok.Data;
import java.text.SimpleDateFormat;
import java.util.Date;


@Data
public class ErrorResponse {
    private final String timeStamp;
    private final String message;
    private final String requestId;

    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    public ErrorResponse(String message, String requestId) {
        this.message = message;
        this.requestId = requestId;
        this.timeStamp =  sdf1.format(new Date().getTime());
    }
}
