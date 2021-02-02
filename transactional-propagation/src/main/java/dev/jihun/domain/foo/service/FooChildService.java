package dev.jihun.domain.foo.service;

import dev.jihun.domain.foo.dao.FooRepository;
import dev.jihun.domain.foo.domain.Foo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@RequiredArgsConstructor
@Service
public class FooChildService {

    @PersistenceContext
    EntityManager entityManager;

    private final FooRepository fooRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void childTransactionRequired(){
        log.info("child current transaction : {}", TransactionSynchronizationManager.getCurrentTransactionName());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void childTransactionSupports(){
        log.info("child current transaction : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        //entityManager.persist(Foo.builder().id("child").build());
        //fooRepository.save(Foo.builder().id("child").build());
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void childTransactionMandatory(){
        log.info("child current transaction : {}", TransactionSynchronizationManager.getCurrentTransactionName());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void childTransactionRequiresNew(){
        log.info("child current transaction : {}", TransactionSynchronizationManager.getCurrentTransactionName());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void childTransactionNotSupported(){
        log.info("child current transaction : {}", TransactionSynchronizationManager.getCurrentTransactionName());
        //entityManager.persist(Foo.builder().id("child").build());
    }

    @Transactional(propagation = Propagation.NEVER)
    public void childTransactionNever(){
        log.info("child current transaction : {}", TransactionSynchronizationManager.getCurrentTransactionName());
    }

    @Transactional(propagation = Propagation.NESTED)
    public void childTransactionNested(){
        log.info("child current transaction : {}", TransactionSynchronizationManager.getCurrentTransactionName());
    }
}
