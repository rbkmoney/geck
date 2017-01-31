package com.rbkmoney.kebab.thrift;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by tolkonepiu on 25/01/2017.
 */
public class ThriftSet extends ThriftElement implements Iterable<ThriftElement> {

    private final Set<ThriftElement> elements = new HashSet();

    public void add(ThriftElement element) {
        if (element == null) {
            element = ThriftNull.INSTANCE;
        }
        elements.add(element);
    }

    public void addAll(ThriftSet set) {
        elements.addAll(set.elements);
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

        ThriftSet thriftSet = (ThriftSet) o;

        return elements.equals(thriftSet.elements);
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }
}
