package com.gfg.oms;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigTest {
    @Test
    public void getters() {
        String url = "some url";
        String password = "some password";
        Config config = new Config(url, password);
        assertEquals(url, config.getUrl());
        assertEquals(password, config.getPassword());
    }
}