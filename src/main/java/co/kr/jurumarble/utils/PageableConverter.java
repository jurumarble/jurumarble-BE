package co.kr.jurumarble.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageableConverter {

    public <T> SliceImpl<T> convertListToSlice(List<T> list, int pageNum, int pageSize) {
        boolean hasNext = false;

        if (list.size() > pageNum) {
            hasNext = true;
            list = list.subList(0, pageSize);
        }

        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        return new SliceImpl<T>(list, pageRequest, hasNext);
    }
}
