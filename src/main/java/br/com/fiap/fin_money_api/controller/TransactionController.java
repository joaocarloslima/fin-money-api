package br.com.fiap.fin_money_api.controller;



import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.fin_money_api.model.Transaction;
import br.com.fiap.fin_money_api.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("transactions")
@Slf4j
public class TransactionController {

    public record TransactionFilter(String description, LocalDate startDate, LocalDate endDate) {
    }

    @Autowired
    private TransactionRepository repository;

    @GetMapping
    public List<Transaction> index(){
           
        return repository.findAll();
    }

}
