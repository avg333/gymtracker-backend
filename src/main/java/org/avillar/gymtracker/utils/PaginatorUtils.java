package org.avillar.gymtracker.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginatorUtils {

    private PaginatorUtils() {
    }

    public static Pageable getPageable(Integer page, Integer size, final String sortParam, Boolean descSort) {
        if (page == null || page < 0)
            page = 0;
        if (size == null || size <= 1)
            size = 1;

        if (sortParam == null || sortParam.length() == 0)
            return PageRequest.of(page, size);

        if (descSort == null || !descSort)
            descSort = false;

        return descSort ?
                PageRequest.of(page, size, Sort.by(sortParam).descending()) :
                PageRequest.of(page, size, Sort.by(sortParam).ascending());
    }
}
