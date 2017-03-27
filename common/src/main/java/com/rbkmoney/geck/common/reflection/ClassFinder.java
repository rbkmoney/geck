package com.rbkmoney.geck.common.reflection;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vpankrashkin on 22.03.17.
 * Based on http://stackoverflow.com/a/15519745/3007501
 */


public class ClassFinder {
    private static final Logger log = LoggerFactory.getLogger(ClassFinder.class);

    public static <T> Collection<Class<? extends T>> find(Collection<String> scannedPackages, String classNameSuffix, Class<T> classType) {
        List<Class<? extends T>> classes = new ArrayList<>();
        for (String scannedPackage : scannedPackages) {
            classes.addAll(find(scannedPackage, classNameSuffix, classType));
        }
        return classes;
    }

    public static <T> Collection<Class<? extends T>> find(String scannedPackage, String classSuffix, Class<T> classType) {
        return new Reflections(scannedPackage)
                .getSubTypesOf(classType).stream()
                .filter(t -> t.getSimpleName().endsWith(classSuffix))
                .collect(Collectors.toSet());
    }

}
