package com.rbkmoney.kebab.thrift;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tolkonepiu on 25/01/2017.
 */
public class ThriftMap extends ThriftElement implements Iterable<ThriftMapEntry> {

    private List<ThriftMapEntry> mapEntries = new ArrayList<>();

    public void addEntry(ThriftMapEntry entry) {
        mapEntries.add(entry);
    }

    public int size() {
        return mapEntries.size();
    }

    @Override
    public Iterator<ThriftMapEntry> iterator() {
        return mapEntries.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThriftMap thriftMap = (ThriftMap) o;

        return mapEntries.equals(thriftMap.mapEntries);
    }

    @Override
    public int hashCode() {
        return mapEntries.hashCode();
    }
}
