package com.rbkmoney.geck.migrator.kit.jolt;

import com.rbkmoney.geck.migrator.ThriftSpec;

/**
 * Created by tolkonepiu on 26/03/2017.
 */
public class JoltSpec {

    private final ThriftSpec thriftSpec;
    private final Object joltSpec;

    public JoltSpec(ThriftSpec thriftSpec, Object joltSpec) {
        this.thriftSpec = thriftSpec;
        this.joltSpec = joltSpec;
    }

    public ThriftSpec getThriftSpec() {
        return thriftSpec;
    }

    public Object getJoltSpec() {
        return joltSpec;
    }
}
