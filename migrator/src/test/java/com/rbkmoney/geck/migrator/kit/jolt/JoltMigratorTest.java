package com.rbkmoney.geck.migrator.kit.jolt;

import com.bazaarvoice.jolt.JsonUtils;
import com.rbkmoney.geck.migrator.MigrationException;
import com.rbkmoney.geck.migrator.MigrationManager;
import com.rbkmoney.geck.migrator.MigrationManagerBuilder;
import com.rbkmoney.geck.migrator.ThriftDef;
import com.rbkmoney.geck.migrator.kit.object.ObjectMigrator;
import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by tolkonepiu on 22/03/2017.
 */
public class JoltMigratorTest {

    @Test
    public void migration1Test() throws MigrationException {
        MigrationManager migrationManager = new MigrationManagerBuilder().build();
        Map inputMap = JsonUtils.classpathToMap("/jolt/json_input.json");
        Map migrationMap = (Map) migrationManager.migrate(inputMap, new ThriftDef(1), JoltMigrator.SERIALIZER_DEF);
        assertEquals(JsonUtils.classpathToMap("/jolt/json_output.json"), migrationMap);
    }
}
