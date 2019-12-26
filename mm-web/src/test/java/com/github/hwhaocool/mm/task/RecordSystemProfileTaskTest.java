package com.github.hwhaocool.mm.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.hwhaocool.mm.web.MongoMonitorWebApplication;

@RunWith( SpringRunner.class)
@SpringBootTest(classes = MongoMonitorWebApplication.class)
public class RecordSystemProfileTaskTest {
    
    @Autowired
    private RecordSystemProfileTask task;

    @Test
    public void test() {
        task.recordTask();
        
    }
}
