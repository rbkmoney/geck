package com.rbkmoney.geck.common.reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by vpankrashkin on 22.03.17.
 * Based on http://stackoverflow.com/a/15519745/3007501
 */


public class ClassFinder {
    private static final Logger log = LoggerFactory.getLogger(ClassFinder.class);

    private static final char PKG_SEPARATOR = '.';

    private static final char DIR_SEPARATOR = '/';

    private static final String CLASS_FILE_SUFFIX = ".class";

    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

    public static <T> List<Class<T>> find(Collection<String> scannedPackages, String classNameSuffix, Class<T> classType) {
        List<Class<T>> classes = new ArrayList<>();
        for (String scannedPackage : scannedPackages) {
            String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
            final String fullClassNameSuffix = classNameSuffix + CLASS_FILE_SUFFIX;
            URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
            if (scannedUrl == null) {
                log.warn(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
                continue;
            }
            System.out.println("Scanned URL "+scannedUrl);
            File scannedDir = new File(scannedUrl.getFile());
            File[] filesList = scannedDir.listFiles();
            if (filesList != null) {
                for (File file : filesList) {
                    classes.addAll(find(file, scannedPackage, fullClassNameSuffix, classType));
                }
            }
        }
        return classes;
    }

    private static <T> List<Class<T>> find(File file, String scannedPackage, String fullClassFileSuffix, Class<T> classType) {

        List<Class<T>> classes = new ArrayList<>();
        String resource = scannedPackage + PKG_SEPARATOR + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(find(child, resource, fullClassFileSuffix, classType));
            }
        } else if (resource.endsWith(fullClassFileSuffix)) {
            int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                Class cl = Class.forName(className);
                if (classType.isAssignableFrom(cl)) {
                    classes.add(cl);
                }
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }

}
