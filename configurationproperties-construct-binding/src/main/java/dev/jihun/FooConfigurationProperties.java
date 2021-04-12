package dev.jihun;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("foo")
@ConstructorBinding
public class FooConfigurationProperties {

    private String name;
    private int age;

    public FooConfigurationProperties(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
