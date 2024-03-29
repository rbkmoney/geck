package com.rbkmoney.geck.migrator.kit.object;

import com.rbkmoney.geck.migrator.MigrationException;
import com.rbkmoney.geck.migrator.MigrationManager;
import com.rbkmoney.geck.migrator.MigrationManagerBuilder;
import com.rbkmoney.geck.migrator.ThriftDef;
import com.rbkmoney.geck.migrator.kit.MigrationPointProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestObjectMigration {

    @Test
    public void testMigration1SpecSrc() throws MigrationException {
    MigrationManager migrationManager = new MigrationManagerBuilder()
            .setMigrationPointProviders(Arrays.asList(ObjectMigrationPointProvider.newInstance()))
            .setMigrators(Arrays.asList(new ObjectMigrator()))
            .build();
        Map map = new HashMap();
        map.put("v0_field", "v0");
        Map result = (Map) migrationManager.migrate(map, new ThriftDef(0), ObjectMigrator.SERIALIZER_DEF);
        Map expected = new HashMap();
        expected.put("v0_field", "v0");
        expected.put("v1_field", "v1");
        expected.put("v5_field", "v5");
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testMigration2SpecSrc() throws MigrationException {
        MigrationManagerBuilder managerBuilder = new MigrationManagerBuilder();
        managerBuilder.setMigrationPointProviders(Arrays.asList(ObjectMigrationPointProvider.newInstance(), ObjectMigrationPointProvider.newInstance(Arrays.asList("com.rbkmoney.geck.migrator.kit"))))
        .setMigrators(Arrays.asList(new ObjectMigrator()));
        MigrationManager migrationManager = managerBuilder.build();
        Map map = new HashMap();
        map.put("v0_field", "v0");
        Map result = (Map) migrationManager.migrate(map, new ThriftDef(0), ObjectMigrator.SERIALIZER_DEF);
        Map expected = new HashMap();
        expected.put("v0_field", "v0");
        expected.put("v1_field", "v1");
        expected.put("v4_field", "v4");
        expected.put("v5_field", "v5");
        Assert.assertEquals(expected, result);
    }
}
