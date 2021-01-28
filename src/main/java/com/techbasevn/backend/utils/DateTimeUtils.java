package com.techbasevn.backend.utils;

import com.techbasevn.backend.enumeration.ErrorCode;
import com.techbasevn.backend.exception.RestApiException;
import lombok.SneakyThrows;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateTimeUtils {
    private static final Logger log = LogManager.getLogger(DateTimeUtils.class);
    public static final String DD_MM_YYYY = "dd-MM-yyyy";

    @SneakyThrows
    public static Date convertStringToDate(String input, String format) {
        if (Utils.StringIsEmpty(input)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            return sdf.parse(input);
        } catch (ParseException e) {
            throw new RestApiException(ErrorCode.TIME_FORMAT_INVALID);
        }
    }

    public static String convertDateToString(Date input, String format) {
        if (Utils.ObjectIsNull(input)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        return sdf.format(input);

    }

    public static String convertZoneDateTimeToString(ZonedDateTime input, String format) {
        if (Utils.ObjectIsNull(input)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        Date result = Date.from(input.toInstant());
        return sdf.format(result);

    }
}
