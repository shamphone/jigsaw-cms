package com.fulong.longcon.counter.impl;

import junit.framework.*;
import com.fulong.longcon.counter.ext.*;
import java.net.URL;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import java.util.Properties;
import com.fulong.common.ResourceUtils;
import org.springframework.core.io.FileSystemResource;
import com.fulong.longcon.counter.AccessCounter;

public class TestAccessCounterImpl
    extends TestCase {
    private AccessCounterImpl accessCounterImpl = null;
    protected Properties properties;
    protected XmlBeanFactory beanFactory;
    protected AccessCounterRepositoryExt repository;
    protected void setUp() throws Exception {
        super.setUp();

        URL file = this.getClass().getClassLoader().getResource(
            "test.config.xml");
        FileSystemResource resource = new FileSystemResource(file.getPath());
        this.beanFactory = new XmlBeanFactory(resource);
        repository = (AccessCounterRepositoryExt)this.beanFactory.getBean(
            "acccessCounterRepository");

        this.properties = new Properties();
        properties.load(ResourceUtils.getResourceAsStream("testCase.properties"));

        String name = "AccessCounter";

        accessCounterImpl = new AccessCounterImpl(name, repository);
    }

    protected void tearDown() throws Exception {
        accessCounterImpl = null;
        super.tearDown();
    }

    public void testGetCount() {
        long expectedReturn = 0L;
        long actualReturn = accessCounterImpl.getCount();
        assertEquals("return value", expectedReturn, actualReturn);

        accessCounterImpl.increase();
        actualReturn = accessCounterImpl.getCount();
        assertEquals("return value", expectedReturn + 1, actualReturn);

    }

    public void testIncrease() {
        //   for(int i = 0; i< 10; i++)
        //  {
        Runnable runnable = new Runnable() {
            public void run() {
                int i = 1000;
                while (true) {
                    for (; i > 0; i--) {
                        AccessCounter accessCounter1 = repository.
                            getAccessCounter("thread1");

                        AccessCounter accessCounter2 = repository.
                            getAccessCounter("thread2");
                        accessCounter1.increase();
                        accessCounter2.increase();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        //while(true);
        //  }
    }
}
