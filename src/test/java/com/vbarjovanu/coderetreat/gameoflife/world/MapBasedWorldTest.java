package com.vbarjovanu.coderetreat.gameoflife.world;

import org.junit.jupiter.api.BeforeEach;

class MapBasedWorldTest extends WorldTest {
    @BeforeEach
    void setUp() {
        this.world = new MapBasedWorld();
    }
}