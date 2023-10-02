package co.kr.jurumarble.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PageableConverter {

    public <T> SliceImpl<T> convertListToSlice(List<T> list, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        if (list.size() > pageSize) {
            list.remove(list.size() - 1);
            return new SliceImpl<>(list, pageable, true);
        }
        return new SliceImpl<>(list, pageable, false);
    }

}
