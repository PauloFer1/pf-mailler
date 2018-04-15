package com.pfernand.pfmailler.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jdbi.v3.core.Jdbi;

@Slf4j
public class SqlUtils {

    public static int[] runSqlScript(Jdbi jdbi, String resource) throws IOException {
        String resourceContent = readResourceToString(resource);
        log.debug("Running sql script: {}", resourceContent);
        return jdbi.withHandle(h -> h.createScript(resourceContent).execute());
    }

    public static String readResourceToString(String resource) throws IOException {
        return IOUtils.toString(SqlUtils.class.getResourceAsStream(resource),
            Charset.forName("UTF-8"));
    }
}
