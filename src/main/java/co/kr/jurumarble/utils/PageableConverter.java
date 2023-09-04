package co.kr.jurumarble.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PageableConverter {

    public <T> SliceImpl<T> convertListToSlice(List<T> list, int pageNum, int pageSize) {
        int start = pageNum * pageSize;
        int end = Math.min((pageNum + 1) * pageSize, list.size());

        List<T> subList = new ArrayList<>();

        if (start < list.size()) {
            subList = list.subList(start, end);
        }

        boolean hasNext = end < list.size();

        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);

        return new SliceImpl<>(subList, pageRequest, hasNext);
    }

}
