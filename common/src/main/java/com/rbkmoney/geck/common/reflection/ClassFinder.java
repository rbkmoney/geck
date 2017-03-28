package com.rbkmoney.geck.common.reflection;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by vpankrashkin on 22.03.17.
 * Based on http://stackoverflow.com/a/15519745/3007501
 */


public class ClassFinder {

    public static <T> Collection<Class<? extends T>> find(Collection<String> scannedPackages, String classNameSuffix, Class<T> classType) {
        Set<Class<? extends T>> classes = new HashSet<>();
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

    public static Set<String> findResources(String pattern) {
        return new Reflections(new ResourcesScanner()).getResources(Pattern.compile(pattern));
    }

}
