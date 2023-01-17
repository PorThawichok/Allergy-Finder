package com.fluke.allergyfinder;

import org.junit.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Register1Test {

    RegisterActivity1 checkTest ;


    @BeforeEach
    void setUp() {
        checkTest = new RegisterActivity1();
    }
    @Test
    public void checkEmail() {

        assertEquals(   false, checkTest.checkEmail("admin@gmail.com"));
        assertEquals(true, checkTest.checkEmail(""));
    }
    @Test
    public void checkPassword() {
        assertEquals(false , checkTest.checkPassword("123123"));
        assertEquals(true, checkTest.checkPassword(""));
    }
    @Test
    public void checkName() {
        assertEquals(false , checkTest.checkName("babyshark"));
        assertEquals(true, checkTest.checkName(""));
    }
    @Test
    public void checkLength() {
        assertEquals(false , checkTest.checkLength("123456"));
        assertEquals(false , checkTest.checkLength("1234567"));
        assertEquals(true, checkTest.checkLength("123"));

    }
    @Test
    public void checkAge() {
        assertEquals(false , checkTest.checkAge("18"));
        assertEquals(true, checkTest.checkAge(""));
    }
    @Test
    public void checkHeight() {
        assertEquals(false , checkTest.checkHeight("180"));
        assertEquals(true, checkTest.checkHeight(""));
    }
    @Test
    public void checkWeight() {
        assertEquals(false , checkTest.checkWeight("55"));
        assertEquals(true, checkTest.checkWeight(""));
    }




}