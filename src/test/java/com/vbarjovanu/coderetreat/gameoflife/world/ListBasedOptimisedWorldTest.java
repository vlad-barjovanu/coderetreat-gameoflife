package com.vbarjovanu.coderetreat.gameoflife.world;

import org.junit.jupiter.api.BeforeEach;

class ListBasedOptimisedWorldTest extends WorldTest {
    @BeforeEach
    void setUp() {
        this.world = new ListBasedOptimisedWorld();
    }
}