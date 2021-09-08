package com.example.filecollector.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public interface PageUtil {
//
//    /**
//     * Create a {@link org.springframework.data.domain.Page} from a {@link java.util.List} of objects
//     *
//     * @param list     List数据
//     * @param pageable 分页参数.
//     * @param <T>     包含数据
//     * @return page
//     */
//    static <T> Page<T> createPageFromList(List<T> list, Pageable pageable) {
//        if (list == null) {
//            throw new IllegalArgumentException("list不能为空");
//        }
//
//        int startOfPage = pageable.getPageNumber() * pageable.getPageSize();
//        if (startOfPage > list.size()) {
//            return new PageImpl<>(new ArrayList<>(), pageable, 0);
//        }
//
//        int endOfPage = Math.min(startOfPage + pageable.getPageSize(), list.size());
//        return new PageImpl<>(list.subList(startOfPage, endOfPage), pageable, list.size());
//    }
}
