package dev.jihun;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class FooConfigurationPropertiesTest {

    @Autowired
    FooConfigurationProperties fooConfigurationProperties;

    @Test
    public void 생성자_바인딩_테스트() {
        assertThat(fooConfigurationProperties.getName(), is("foo"));
        assertThat(fooConfigurationProperties.getAge(), is(20));
    }

}