package com.github.hwhaocool.mm.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.hwhaocool.mm.service.Profile2SlowService;
import com.github.hwhaocool.mm.web.MongoMonitorWebApplication;

@ActiveProfiles("local")
@RunWith( SpringRunner.class)
@SpringBootTest(classes = MongoMonitorWebApplication.class)
public class RecordSystemProfileTaskTest {
    
    @Autowired
    private Profile2SlowService profile2SlowService;

    @Test
    public void test() {
        profile2SlowService.recordAndSave2Other();
        
    }
}
