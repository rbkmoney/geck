package com.rbkmoney.geck.filter.parser;

import com.rbkmoney.geck.filter.Parser;

import java.util.List;

import static com.rbkmoney.geck.common.util.StringUtil.split;

/**
 * Created by tolkonepiu on 16/03/2017.
 */
public class PathParser implements Parser {

    private final List<String> items;
    private final String delimiter;

    public static final String DEFAULT_DELIMITER = ".";

    public PathParser(String fieldPath) {
        this(fieldPath, DEFAULT_DELIMITER);
    }

    public PathParser(String fieldPath, String delimiter) {
        this(split(fieldPath, delimiter), delimiter);
    }

    public PathParser(List<String> items, String delimiter) {
        this.items = items;
        this.delimiter = delimiter;
    }

    @Override
    public String getItem(int item) {
        return items.get(item);
    }

    @Override
    public String getItemPath(int item) {
        return String.join(delimiter, items.subList(0, item + 1));
    }

    @Override
    public Parser getSubParser(int from) {
        return new PathParser(items.subList(from, size()), delimiter);
    }

    @Override
    public int size() {
        return items.size();
    }

}
