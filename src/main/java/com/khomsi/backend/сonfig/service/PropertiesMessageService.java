package com.khomsi.backend.сonfig.service;

public interface PropertiesMessageService {
    String getProperty(String source);

    String getProperty(String name, Object... params);
}