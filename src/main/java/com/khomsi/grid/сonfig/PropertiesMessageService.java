package com.khomsi.grid.сonfig;

public interface PropertiesMessageService {
    String getProperty(String source);

    String getProperty(String name, Object... params);
}