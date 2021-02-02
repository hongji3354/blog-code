package dev.jihun;

import dev.jihun.domain.foo.service.FooParentService;
import lombok.RequiredArgsConstructor;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Runner implements ApplicationRunner {

    private final FooParentService fooParentService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        fooParentService.parentTransactionRequired();
    }
}
