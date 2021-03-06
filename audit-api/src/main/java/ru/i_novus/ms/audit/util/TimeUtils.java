/*
 *    Copyright 2020 I-Novus
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package ru.i_novus.ms.audit.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;

public final class TimeUtils {

    private static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm:ss";
    private static final DateTimeFormatter DATE_TIME_PATTERN_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
    private static final String DATE_PATTERN_WITH_POINT = "dd.MM.yyyy";
    private static final DateTimeFormatter DATE_PATTERN_WITH_POINT_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN_WITH_POINT);

    private static final Logger logger = LoggerFactory.getLogger(TimeUtils.class);

    private TimeUtils() {
    }

    public static OffsetDateTime parseOffsetDateTime(String str) {
        try {
            return LocalDateTime.parse(str, DATE_TIME_PATTERN_FORMATTER).atOffset(ZoneOffset.UTC);
        } catch (DateTimeException e) {
            logger.debug("Failed to parse Date&Time using format: " + DATE_TIME_PATTERN, e);
        }

        throw new IllegalArgumentException("Failed to parse Date&Time: " + str);
    }

    public static LocalDateTime parseLocalDateTime(String str) {
        return parseOffsetDateTime(str).toLocalDateTime();
    }

    public static String format(LocalDateTime localDateTime) {
        return localDateTime.format(DATE_TIME_PATTERN_FORMATTER);
    }

    public static String format(LocalDate localDate) {
        return localDate.format(DATE_PATTERN_WITH_POINT_FORMATTER);
    }

    public static String format(OffsetDateTime offsetDateTime) {
        return offsetDateTime.format(DATE_TIME_PATTERN_FORMATTER);
    }
}
