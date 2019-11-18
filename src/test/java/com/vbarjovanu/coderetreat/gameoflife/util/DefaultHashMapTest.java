package com.vbarjovanu.coderetreat.gameoflife.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultHashMapTest {
    private DefaultHashMap<Integer, String> map;

    @BeforeEach
    void setUp() {
        this.map = new DefaultHashMap<>("Default");
    }

    @Test
    void shouldReturnDefaultValue() {
        assertEquals("Default", this.map.get(123));
    }

    @Test
    void addedValueForKeyShouldBeRetrievedByKey() {
        this.map.put(123, "test");
        assertEquals("test", this.map.get(123));
    }
}
