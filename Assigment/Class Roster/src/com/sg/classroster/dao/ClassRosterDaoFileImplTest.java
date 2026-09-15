package com.sg.classroster.dao;

import org.junit.jupiter.api.*;

import java.io.FileWriter;

import static org.junit.jupiter.api.Assertions.fail;


public class ClassRosterDaoFileImplTest {
    ClassRosterDao testDao;
    public ClassRosterDaoFileImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception{
        String testFile = "testroster.txt";
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        testDao = new ClassRosterDaoFileImpl(testFile);
    }

}