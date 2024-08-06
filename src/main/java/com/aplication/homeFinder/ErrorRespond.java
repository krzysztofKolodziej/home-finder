package com.aplication.homeFinder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@AllArgsConstructor
public final class ErrorRespond {

    private final HttpStatusCode status;
    private final String message;
}
