package dev.jihun.domain.foo.dao;

import dev.jihun.domain.foo.domain.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FooRepository extends JpaRepository<Foo, Long> {
}
