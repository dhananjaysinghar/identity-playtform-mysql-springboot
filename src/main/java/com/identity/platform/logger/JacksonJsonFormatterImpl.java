package com.identity.platform.logger;

import ch.qos.logback.contrib.jackson.JacksonJsonFormatter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class JacksonJsonFormatterImpl extends JacksonJsonFormatter {

    private static final String MESSAGE = "message";
    private static final String START_MESSAGE = "{";
    private static final String END_MESSAGE = "}";
    private static final String SEPARATOR_MESSAGE = "=";
    private static final String COMMA_SEPARATOR_MESSAGE = ",";
    private static final String EMPTY_STRING = "";


    @Override
    @SuppressWarnings("unchecked")
    public String toJsonString(Map map) throws IOException {
        try {
            if (isAJsonMessage(map)) {
                String data = map.get(MESSAGE).toString();
                map.putAll(Arrays.stream(data.replace(START_MESSAGE, EMPTY_STRING)
                                .replace(END_MESSAGE, EMPTY_STRING)
                                .split(COMMA_SEPARATOR_MESSAGE))
                        .map(arrayData -> arrayData.split(SEPARATOR_MESSAGE))
                        .collect(Collectors.toMap(d -> (d[0]).trim(), d -> d[1])));
            }
        } catch (Exception ex) {
            log.warn(ex.getMessage());
        }
        return super.toJsonString(map);
    }

    private boolean isAJsonMessage(Map map) {
        return map != null && map.get(MESSAGE) != null
                && map.get(MESSAGE).toString().startsWith(START_MESSAGE)
                && map.get(MESSAGE).toString().endsWith(END_MESSAGE);
    }
}