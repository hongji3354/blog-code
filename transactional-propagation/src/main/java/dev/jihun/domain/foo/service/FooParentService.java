package dev.jihun.domain.foo.service;

import dev.jihun.domain.foo.dao.FooRepository;
import dev.jihun.domain.foo.domain.Foo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@RequiredArgsConstructor
@Service
public class FooParentService {

    private final FooRepository fooRepository;
    private final FooChildService fooChildService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void parentTransactionRequired(){
        log.info("parent current transaction : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        fooChildService.childTransactionRequired();
    }

    public void parentNonTransaction(){
        log.info("parent current transaction : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        fooChildService.childTransactionNotSupported();
    }
}
