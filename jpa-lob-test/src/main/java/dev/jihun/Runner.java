package dev.jihun;

import dev.jihun.domain.foo.dao.FooRepository;
import dev.jihun.domain.foo.domain.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationRunner {

    @Autowired
    FooRepository fooRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Foo foo = new Foo();
        //language=JSON
        String a = "{ \"a\" :  \"b\", \"c\" :  \"d\", \"e\" :  \"f\"}";
        foo.setLob(a);

        fooRepository.saveAndFlush(foo);
        fooRepository.findAll();
        System.out.println();
    }
}
