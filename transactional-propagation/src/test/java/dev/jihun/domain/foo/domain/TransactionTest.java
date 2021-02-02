package dev.jihun.domain.foo.domain;

import dev.jihun.domain.foo.service.FooParentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.config.location=classpath:application-test.yml"})
class TransactionTest {

    @Autowired
    FooParentService fooParentService;

    @Test
    public void parentRequiredAndChildRequired(){
        fooParentService.parentTransactionRequired();
    }

}
