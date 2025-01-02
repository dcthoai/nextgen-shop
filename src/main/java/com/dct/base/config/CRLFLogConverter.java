package com.dct.base.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.CompositeConverter;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiElement;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Used in the logging system (Logback) to ensure that log messages do not contain newline characters
 * (CR - Carriage Return and LF - Line Feed)
 * or other unsafe characters that may cause security problems
 * or affect the log format
 * @author thoaidc
 */
public class CRLFLogConverter extends CompositeConverter<ILoggingEvent> {

    public static final Marker CRLF_SAFE_MARKER = MarkerFactory.getMarker("CRLF_SAFE");
    private static final String[] SAFE_LOGGERS = { "org.hibernate" };
    private static final Map<String, AnsiElement> ELEMENTS;

    static {
        // A map that maps color options (like "red", "green") to ANSI objects to apply color to the log string
        Map<String, AnsiElement> ansiElements = new HashMap<>();
        ansiElements.put("faint", AnsiStyle.FAINT);
        ansiElements.put("red", AnsiColor.RED);
        ansiElements.put("green", AnsiColor.GREEN);
        ansiElements.put("yellow", AnsiColor.YELLOW);
        ansiElements.put("blue", AnsiColor.BLUE);
        ansiElements.put("magenta", AnsiColor.MAGENTA);
        ansiElements.put("cyan", AnsiColor.CYAN);
        ELEMENTS = ansiElements;
    }

    @Override
    protected String transform(ILoggingEvent event, String in) {
        AnsiElement element = ELEMENTS.get(getFirstOption());
        List<Marker> markers = event.getMarkerList();

        // If the logger is safe (in SAFE_LOGGERS list) or has the marker CRLF_SAFE_MARKER, keep original log string
        if ((Objects.nonNull(markers) && markers.get(0).contains(CRLF_SAFE_MARKER)) || isLoggerSafe(event)) {
            return in;
        }

        // The characters \n, \r, and \t are replaced with the _ character or an ANSI string if a color is configured
        String replacement = element == null ? "_" : AnsiOutput.toString(element, "_");
        return in.replaceAll("[\n\r\t]", replacement);
    }

    protected boolean isLoggerSafe(ILoggingEvent event) {
        for (String safeLogger : SAFE_LOGGERS) {
            if (event.getLoggerName().startsWith(safeLogger)) {
                return true;
            }
        }

        return false;
    }
}
