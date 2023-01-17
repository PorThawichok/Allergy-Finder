package com.fluke.allergyfinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fluke.allergyfinder.Product.ProductDetailActivity;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;


public class ProductDetailTest {

    ProductDetailActivity checkTest;


    @BeforeEach
    void setUp() {
        checkTest = new ProductDetailActivity();
    }

    @Test
    public void checkReset() {

        assertEquals(false, checkTest.checkType("user"));
        assertEquals(true, checkTest.checkType("admin"));


    }
}
