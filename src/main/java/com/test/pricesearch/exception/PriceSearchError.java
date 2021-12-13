package com.test.pricesearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PriceSearchError extends ResponseStatusException {
    public PriceSearchError(HttpStatus status) {
        super(status);
    }

    public PriceSearchError(HttpStatus status, String reason) {
        super(status, reason);
    }

    public PriceSearchError(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    public PriceSearchError(int rawStatusCode, String reason, Throwable cause) {
        super(rawStatusCode, reason, cause);
    }
}
