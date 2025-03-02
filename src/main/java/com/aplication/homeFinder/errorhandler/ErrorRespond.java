package com.aplication.homeFinder.errorhandler;

import org.springframework.http.HttpStatusCode;

public record ErrorRespond(HttpStatusCode status, String message) {
}
