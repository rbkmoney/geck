package com.rbkmoney.geck.migrator.kit.jolt;

import com.bazaarvoice.jolt.JsonUtils;
import com.rbkmoney.geck.migrator.*;
import com.rbkmoney.geck.migrator.kit.BaseMigrationSpec;
import com.rbkmoney.geck.migrator.kit.SimpleMigrationPointProvider;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by tolkonepiu on 23/03/2017.
 */
public class JoltMigrationPointProvider extends SimpleMigrationPointProvider {

    public JoltMigrationPointProvider(Collection<JoltSpec> specs) {
        super(createMigrationPoints(specs));
    }

    public static JoltMigrationPointProvider fromClasspath(String... files) throws MigrationException {
        List<JoltSpec> joltSpecs = new ArrayList<>();
        try {
            for (String file : files) {
                Map joltSpecMap = JsonUtils.classpathToMap(file);
                joltSpecs.add(createJoltSpec(joltSpecMap));
            }
            return new JoltMigrationPointProvider(joltSpecs);
        } catch (Exception e) {
            throw new MigrationException("Cannot create JoltSpec list", e);
        }
    }

    public static JoltMigrationPointProvider fromFileSystem(Path dir) throws MigrationException {
        try {
            List<JoltSpec> joltSpecs = Files.walk(dir)
                    .filter(path -> !Files.isDirectory(path))
                    .map(path -> JsonUtils.filepathToMap(path.toAbsolutePath().toString()))
                    .map(map -> createJoltSpec(map))
                    .collect(Collectors.toList());

            return new JoltMigrationPointProvider(joltSpecs);
        } catch (Exception e) {
            throw new MigrationException("Cannot create JoltSpec list", e);
        }
    }

    private static JoltSpec createJoltSpec(Map joltSpecMap) {
        if (joltSpecMap.containsKey("thriftSpec")
                && joltSpecMap.containsKey("joltSpec")) {
            ThriftSpec thriftSpec = createThriftSpec((Map) joltSpecMap.get("thriftSpec"));
            return new JoltSpec(thriftSpec, joltSpecMap.get("joltSpec"));
        }
        throw new IllegalArgumentException("Incorrect jolt spec format");
    }

    private static ThriftSpec createThriftSpec(Map thriftSpecMap) {
        if (thriftSpecMap.containsKey("inDef")
                && thriftSpecMap.containsKey("outDef")) {
            return new ThriftSpec(
                    createThriftDef((Map) thriftSpecMap.get("inDef")),
                    createThriftDef((Map) thriftSpecMap.get("outDef"))
            );
        }
        throw new IllegalArgumentException("Incorrect thrift spec format");
    }

    private static ThriftDef createThriftDef(Map thriftDefMap) {
        if (thriftDefMap.containsKey("version")
                && thriftDefMap.containsKey("type")) {
            return new ThriftDef(
                    (int) thriftDefMap.get("version"),
                    (String) thriftDefMap.get("type")
            );
        }
        throw new IllegalArgumentException("Incorrect thrift def format");
    }

    private static List<MigrationPoint> createMigrationPoints(Collection<JoltSpec> specs) {
        AtomicInteger counter = new AtomicInteger(0);
        return specs.stream()
                .map(spec -> new MigrationPoint(
                        counter.incrementAndGet(),
                        spec.getThriftSpec(),
                        JoltMigrator.SERIALIZER_DEF,
                        new BaseMigrationSpec(spec.getJoltSpec(), MigrationType.JOLT.getKey())
                )).collect(Collectors.toList());
    }

}
