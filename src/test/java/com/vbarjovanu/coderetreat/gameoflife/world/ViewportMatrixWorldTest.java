package com.vbarjovanu.coderetreat.gameoflife.world;

import org.junit.jupiter.api.BeforeEach;

class ViewportMatrixWorldTest extends WorldTest {
    @BeforeEach
    void setUp() {
        this.world = new ViewportMatrixWorld();
    }
}