package co.kr.jurumarble.comment.controller.converter;

import co.kr.jurumarble.comment.enums.CommentType;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, CommentType> {

    @Override
    public CommentType convert(String source) {
        return CommentType.valueOf(source.toUpperCase());
    }
}
