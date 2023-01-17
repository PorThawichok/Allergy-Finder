package com.fluke.allergyfinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class EditUserTest {
    EditUserActivity  checkTest;

    @BeforeEach
    void setUp() {
        checkTest = new EditUserActivity();
    }

    @Test
    public void checkEmail() {

        assertEquals(   false, checkTest.checkEmail("abc@gmail.com"));
        assertEquals(true, checkTest.checkEmail(""));
    }
    @Test
    public void checkPassword() {
        assertEquals(false , checkTest.checkPassword("000000"));
        assertEquals(true, checkTest.checkPassword(""));
    }
    @Test
    public void checkName() {
        assertEquals(false , checkTest.checkName("pannatat"));
        assertEquals(true, checkTest.checkName(""));
    }
    @Test
    public void checkAge() {
        assertEquals(false , checkTest.checkAge("26"));
        assertEquals(true, checkTest.checkAge(""));
    }
    @Test
    public void checkAgeNumber() {
        assertEquals(false , checkTest.checkAgeNumber("26"));
        assertEquals(true, checkTest.checkAgeNumber("100"));
        assertEquals(true, checkTest.checkAgeNumber("105"));
    }
    @Test
    public void checkHeight() {
        assertEquals(false , checkTest.checkHeight("170"));
        assertEquals(true, checkTest.checkHeight(""));
    }
    @Test
    public void checkHeightNumber() {
        assertEquals(false , checkTest.checkHeightNumber("170"));
        assertEquals(true, checkTest.checkHeightNumber("200"));
        assertEquals(true, checkTest.checkHeightNumber("210"));
    }
    @Test
    public void checkWeight() {
        assertEquals(false , checkTest.checkWeight("55"));
        assertEquals(true, checkTest.checkWeight(""));
    }
    @Test
    public void checkWeightNumber() {
        assertEquals(false , checkTest.checkWeightNumber("55"));
        assertEquals(true, checkTest.checkWeightNumber("110"));
        assertEquals(true, checkTest.checkWeightNumber("130"));
    }
}
