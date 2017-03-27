package com.rbkmoney.geck.migrator.kit.jolt;

import com.rbkmoney.geck.migrator.MigrationException;
import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * Created by tolkonepiu on 22/03/2017.
 */
public class JoltMigratorTest {

    @Test
    public void joltMigrationPointProviderTest() throws MigrationException, URISyntaxException {
        JoltMigrationPointProvider.fromClasspath("/jolt/spec_event.json");
        JoltMigrationPointProvider.fromFileSystem(Paths.get(this.getClass().getResource("/jolt/").toURI()));
    }

}
