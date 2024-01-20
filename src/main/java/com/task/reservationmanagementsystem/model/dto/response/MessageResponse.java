package com.task.reservationmanagementsystem.model.dto.response;

import org.springframework.http.HttpStatusCode;

public record MessageResponse (String message, HttpStatusCode httpStatusCode, int statusCode){
}
