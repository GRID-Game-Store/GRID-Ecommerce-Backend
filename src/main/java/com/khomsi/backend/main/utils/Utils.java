package com.khomsi.backend.main.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@UtilityClass
public final class Utils {
    public static Sort createSorting(String[] sort, String defaultSortField) {
        try {
            return (sort != null && sort.length == 2) ?
                    Sort.by(sort[1].equalsIgnoreCase("asc") ? ASC : DESC, sort[0]) :
                    Sort.by(DESC, defaultSortField);
        } catch (PropertyReferenceException e) {
            // Log trace
            e.printStackTrace();
            // Return default value
            return Sort.by(DESC, defaultSortField);
        }
    }
    public static String truncateTitle(String title) {
        if (title.length() > 30) {
            return title.substring(0, 27) + "...";
        }
        return title;
    }
}