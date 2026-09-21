package com.flooring.view;

public interface UserIO {
    void print(String msg);

    String readString(String prompt);

    int readInt(String prompt);
}
