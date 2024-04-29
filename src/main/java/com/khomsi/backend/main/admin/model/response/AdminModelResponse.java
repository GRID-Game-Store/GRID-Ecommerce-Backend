package com.khomsi.backend.main.admin.model.response;

import lombok.Builder;

import java.util.List;

@Builder
public record AdminModelResponse(List<?> entities, long totalItems, int totalPages, int currentPage) {
}