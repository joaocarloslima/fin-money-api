package br.com.fiap.fin_money_api.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fiap.fin_money_api.model.Category;
import br.com.fiap.fin_money_api.model.Transaction;
import br.com.fiap.fin_money_api.model.TransactionType;
import br.com.fiap.fin_money_api.repository.CategoryRepository;
import br.com.fiap.fin_money_api.repository.TransactionRepository;
import jakarta.annotation.PostConstruct;

@Component
public class DatabaseSeeder {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @PostConstruct
    public void init() {
        var categories = List.of(
                Category.builder().name("Educação").icon("Book").build(),
                Category.builder().name("Lazer").icon("Dices").build(),
                Category.builder().name("Transporte").icon("Bus").build(),
                Category.builder().name("Moradia").icon("House").build(),
                Category.builder().name("Saúde").icon("Heart").build());

        categoryRepository.saveAll(categories);

        var descriptions = List.of("Uber para faculdade", "Remédio", "Café especial", "Livro didático", "Cinema",
                "Bilhete Único", "Restaurante", "Faculdade", "Plano de Saúde", "Aluguel", "Conta de Água",
                "Conta de Luz", "Streaming");
        var transactions = new ArrayList<Transaction>();

        for (int i = 0; i < 50; i++) {
            transactions.add(Transaction.builder()
                    .description(descriptions.get(new Random().nextInt(descriptions.size())))
                    .amount(BigDecimal.valueOf(10 + new Random().nextDouble() * 500))
                    .date(LocalDate.now().minusDays(new Random().nextInt(30)))
                    .type(TransactionType.EXPENSE)
                    .category(categories.get(new Random().nextInt(categories.size())))
                    .build());
        }

        transactionRepository.saveAll(transactions);

    }

}
