package com.rbkmoney.kebab;

import java.io.IOException;

/**
 * Created by tolkonepiu on 27/01/2017.
 */
public interface Serializer<T> {

    T read(StructReader in) throws IOException;

    void write(StructWriter out, T value) throws IOException;

}
