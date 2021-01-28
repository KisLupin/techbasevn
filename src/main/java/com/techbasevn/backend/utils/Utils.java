package com.techbasevn.backend.utils;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Objects;

public class Utils {

    public static boolean ObjectIsNull(Object object) {
        return Objects.isNull(object);
    }

    public static boolean ObjectNonNull(Object object) {
        return Objects.nonNull(object);
    }

    public static boolean StringNonEmpty(String data) {
        return !StringUtils.isEmpty(data);
    }

    public static boolean StringIsEmpty(String data) {
        return StringUtils.isEmpty(data);
    }

    public static boolean CollectionIsEmpty(Collection data) {
        return CollectionUtils.isEmpty(data);
    }

    public static boolean CollectionNonEmpty(Collection data) {
        return !CollectionUtils.isEmpty(data);
    }
}
