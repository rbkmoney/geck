package com.rbkmoney.kebab.thrift;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tolkonepiu on 25/01/2017.
 */
public class ThriftList extends ThriftElement implements Iterable<ThriftElement> {

    private final List<ThriftElement> elements = new ArrayList<>();

    public void add(ThriftElement element) {
        if (element == null) {
            element = ThriftNull.INSTANCE;
        }
        elements.add(element);
    }

    public void addAll(ThriftList list) {
        elements.addAll(list.elements);
    }

    public int size() {
        return elements.size();
    }

    @Override
    public Iterator<ThriftElement> iterator() {
        return elements.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThriftList that = (ThriftList) o;

        return elements.equals(that.elements);
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }
}
