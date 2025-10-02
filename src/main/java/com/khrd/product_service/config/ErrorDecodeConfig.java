package com.khrd.product_service.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khrd.product_service.exception.BadRequestException;
import com.khrd.product_service.exception.ExceptionDetail;
import com.khrd.product_service.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.http.HttpStatus;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

//@Configuration
public class ErrorDecodeConfig implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionDetail detail = null;
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // Use the injected objectMapper
            detail = objectMapper.readValue(bodyIs, ExceptionDetail.class);
        } catch (IOException e) {
            // Log the error or wrap it in a more specific exception
            return new RuntimeException("Failed to parse error response body", e);
        }

        switch (response.status()) {
            case HttpStatus.SC_BAD_REQUEST:
                return new BadRequestException(
                        detail != null && detail.getDetail() != null ? detail.getDetail() : "Bad Request");
            case HttpStatus.SC_NOT_FOUND:
                return new NotFoundException(
                        detail != null && detail.getDetail() != null ? detail.getDetail() : "Not Found");
            default:
                return errorDecoder.decode(methodKey, response);
        }
    }
}
