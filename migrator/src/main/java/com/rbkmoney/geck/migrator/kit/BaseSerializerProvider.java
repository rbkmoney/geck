package com.rbkmoney.geck.migrator.kit;

import com.rbkmoney.geck.migrator.SerializerDef;
import com.rbkmoney.geck.migrator.ThriftDef;
import com.rbkmoney.geck.migrator.kit.jolt.JoltMigrator;
import com.rbkmoney.geck.migrator.kit.object.ObjectMigrator;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.StructProcessor;
import com.rbkmoney.geck.serializer.kit.object.ObjectHandler;
import com.rbkmoney.geck.serializer.kit.object.ObjectProcessor;

import java.util.ArrayList;
import java.util.List;

public class BaseSerializerProvider implements SerializerProvider {
    private final List<SerializerProvider> providers;

    public BaseSerializerProvider(List<SerializerProvider> providers) {
        this();
        providers.addAll(0, providers);
    }

    public BaseSerializerProvider() {
        providers = new ArrayList<>();
        providers.add(new SerializerProvider() {

            @Override
            public <S> StructProcessor<S> getStructProcessor(SerializerDef<S> sDef, ThriftDef srcTypeDef) {
                return (StructProcessor<S>) new ObjectProcessor();
            }

            @Override
            public <R> StructHandler<R> getStructHandler(SerializerDef<R> sDef, ThriftDef resTypeDef) {
                return (StructHandler<R>) new ObjectHandler();
            }

            @Override
            public boolean accept(SerializerDef sDef) {
                return sDef.equals(JoltMigrator.SERIALIZER_DEF)
                        || sDef.equals(ObjectMigrator.SERIALIZER_DEF);
            }
        });
    }

    @Override
    public <S> StructProcessor<S> getStructProcessor(SerializerDef<S> sDef, ThriftDef srcTypeDef) {
        SerializerProvider provider = getMatching(sDef);
        if (provider != null) {
            return provider.getStructProcessor(sDef, srcTypeDef);
        }
        return null;
    }

    @Override
    public <R> StructHandler<R> getStructHandler(SerializerDef<R> sDef, ThriftDef resTypeDef) {
        SerializerProvider provider = getMatching(sDef);
        if (provider != null) {
            return provider.getStructHandler(sDef, resTypeDef);
        }
        return null;
    }

    @Override
    public boolean accept(SerializerDef sDef) {
        return getMatching(sDef) != null;
    }

    protected SerializerProvider getMatching(SerializerDef sDef) {
        for (int i = 0; i < providers.size(); ++i) {
            if (providers.get(i).accept(sDef)) {
                return providers.get(i);
            }
        }
        return null;
    }
}
