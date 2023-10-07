package co.kr.jurumarble.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PageableConverter {
    private static final int INDEX_OF_START_SUBLIST = 0;

    public <T> SliceImpl<T> convertListToSlice(List<T> list, int pageNum, int pageSize) {
        if (list.isEmpty()) {
            return new SliceImpl<>(list);
        }

        if (list.size() < pageSize) {
            pageSize = list.size();
        }

        Pageable pageable = PageRequest.of(pageNum, pageSize);

        int start = INDEX_OF_START_SUBLIST;
        int end = Math.min((start + pageable.getPageSize()), list.size());

        List<T> subList = new ArrayList<>(list.subList(start, end));

        boolean hasNext = end < list.size();

        return new SliceImpl<>(subList, pageable, hasNext);
    }

}
