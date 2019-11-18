package com.vbarjovanu.coderetreat.gameoflife.world;

import org.junit.jupiter.api.BeforeEach;

class MatrixBasedWorldTest extends WorldTest {
    @BeforeEach
    void setUp() {
        this.world = new MatrixBasedWorld();
    }
}