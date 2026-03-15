package com.ohsin.ordermgr.dto;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        LocalDateTime timestamp,
        String code,
        String message,
        String path
) {
}