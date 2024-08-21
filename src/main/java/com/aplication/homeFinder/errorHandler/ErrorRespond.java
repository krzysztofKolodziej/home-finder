package com.aplication.homeFinder.errorHandler;

import org.springframework.http.HttpStatusCode;

public record ErrorRespond(HttpStatusCode status, String message) {
}
