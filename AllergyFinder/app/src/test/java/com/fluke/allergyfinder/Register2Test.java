package com.fluke.allergyfinder;


import org.junit.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;


public class Register2Test {

    RegisterActivity2 checkTest;

    @BeforeEach
    void setUp() {
        checkTest = new RegisterActivity2();
    }
    @Test
    public void calculateMbrFemale() {

        assertEquals(2800.6,checkTest.calculateMbrFemale(1.9,"60","180","18"));
       assertEquals(2542.65, checkTest.calculateMbrFemale(1.725,"60","180","18"));
        assertEquals(2284.7000000000003 , checkTest.calculateMbrFemale(1.55,"60","180","18"));
        assertEquals(2026.75, checkTest.calculateMbrFemale(1.375,"60","180","18"));
        assertEquals(1768.8, checkTest.calculateMbrFemale(1.2,"60","180","18"));
    }
    @Test
    public void calculateMbrMale() {

        assertEquals(3116,checkTest.calculateMbrMale(1.9,"60","180","18"));
        assertEquals(2829, checkTest.calculateMbrMale(1.725,"60","180","18"));
        assertEquals(2542 , checkTest.calculateMbrMale(1.55,"60","180","18"));
        assertEquals(2255, checkTest.calculateMbrMale(1.375,"60","180","18"));
        assertEquals(1968, checkTest.calculateMbrMale(1.2,"60","180","18"));
    }

}
