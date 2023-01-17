package com.fluke.allergyfinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;




/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginTest {

    LoginActivity checkTest ;


    @BeforeEach
    void setUp() {
        checkTest = new LoginActivity();
    }

    @Test
    public void checkEmail() {

        assertEquals(   false, checkTest.checkEmail("abc@gmail.com"));
        assertEquals(true, checkTest.checkEmail(""));
    }
    @Test
    public void checkPassword() {
        assertEquals(false , checkTest.checkPassword("111111"));
        assertEquals(true, checkTest.checkPassword(""));
    }

}