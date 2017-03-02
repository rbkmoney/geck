package com.rbkmoney.geck.migrator;

/**
 * Created by vpankrashkin on 02.03.17.
 */
public interface MigrationStore {
    MigrationPoint getMigration(ThriftDef in, ThriftDef out);
}
