package com.fluke.allergyfinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;


public class ForgetPasswordTest {

    ForgetPasswordActivity checkTest;


    @BeforeEach
    void setUp() {
        checkTest = new ForgetPasswordActivity();
    }

    @Test
    public void checkReset() {

        assertEquals(false, checkTest.checkReset("abc@gmail.com"));
        assertEquals(true, checkTest.checkReset(""));


    }
}
