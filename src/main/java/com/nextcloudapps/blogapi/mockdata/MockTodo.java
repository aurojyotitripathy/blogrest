package com.nextcloudapps.blogapi.mockdata;

import java.io.Serializable;

public class MockTodo implements Serializable {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
