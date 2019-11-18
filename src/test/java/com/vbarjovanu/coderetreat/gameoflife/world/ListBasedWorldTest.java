package com.vbarjovanu.coderetreat.gameoflife.world;

import org.junit.jupiter.api.BeforeEach;

class ListBasedWorldTest extends WorldTest {
    @BeforeEach
    void setUp() {
        this.world = new ListBasedWorld();
    }
}