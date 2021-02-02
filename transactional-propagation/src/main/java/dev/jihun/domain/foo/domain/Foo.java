package dev.jihun.domain.foo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Foo {

    @Id
    private String id;

    @Builder
    public Foo(String id) {
        this.id = id;
    }
}
