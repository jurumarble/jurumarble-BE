package co.kr.jurumarble.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageableConverter {

    public <T> SliceImpl<T> convertListToSlice(List<T> list, int pageable, int pageable1, Pageable pageable2) {
        boolean hasNext = false;

        if (list.size() > pageable) {
            hasNext = true;
            list = list.subList(0, pageable1);
        }

        return new SliceImpl<T>(list, pageable2, hasNext);
    }
}
