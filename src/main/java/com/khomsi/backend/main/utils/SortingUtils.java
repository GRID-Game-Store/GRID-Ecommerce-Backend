package com.khomsi.backend.main.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@UtilityClass
public final class SortingUtils {
    public static Sort createSorting(String[] sort, String defaultSortField) {
        return (sort != null && sort.length == 2) ?
                Sort.by(sort[1].equalsIgnoreCase("asc") ? ASC : DESC, sort[0]) :
                Sort.by(DESC, defaultSortField);
    }
}