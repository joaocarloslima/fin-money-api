package br.com.fiap.fin_money_api.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.fiap.fin_money_api.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction>{
    
    //SELECT * FROM TRANSACTION WHERE DESCRIPTION = :DESCRIPTION
    //@Query("SELECT t FROM TRANSACTION as t WHERE t.description = :description") //JPQL
    //Page<Transaction> findByDescriptionContainingIgnoringCase(String description, Pageable pageable); // Query Methods



    //Page<Transaction> findByDate(LocalDate date, Pageable pageable);



    Page<Transaction> findByDescriptionContainingIgnoringCaseAndDate(String description, LocalDate date, Pageable pageable);
    
}
