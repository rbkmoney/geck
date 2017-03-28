package com.rbkmoney.geck.common.reflection;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by tolkonepiu on 27/03/2017.
 */
public class ClassFinderTest {

    @Test
    public void findResourcesTest() throws IOException {
        Set<String> resources = ClassFinder.findResources(".*_jolt\\.json");
        for (String resource : resources) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(resource)))) {
                assertEquals("{ \"tipa\": \"json\" }", bufferedReader.readLine());
            }
        }
    }

}
