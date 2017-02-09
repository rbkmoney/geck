package com.rbkmoney.kebab.kit.tbase.context;

import org.apache.thrift.TBase;

/**
 * Created by tolkonepiu on 09/02/2017.
 */
public class TBaseElementContext implements ElementContext {

    private TBase tBase;

    public TBaseElementContext(TBase tBase) {
        this.tBase = tBase;
    }

    public TBase getValue() {
        return tBase;
    }

    @Override
    public String toString() {
        return "TBaseElementContext{" +
                "tBase=" + tBase +
                '}';
    }
}
