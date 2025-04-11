package br.com.fiap.fin_money_api.specification;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.fin_money_api.controller.TransactionController.TransactionFilters;
import br.com.fiap.fin_money_api.model.Transaction;
import jakarta.persistence.criteria.Predicate;

public class TransactionSpecification {

    public static Specification<Transaction> withFilters(TransactionFilters filters){
        return (root, query, cb) -> {  //lambda expresion 
            var predicates = new ArrayList<>();

            if (filters.description() != null){
                predicates.add(
                    cb.like(
                        cb.lower(root.get("description")), "%" + filters.description().toLowerCase() + "%"
                    )
                );
            }

            if(filters.startDate() != null && filters.endDate() != null){
                predicates.add(
                    cb.between(root.get("date"), filters.startDate(), filters.endDate())
                );
            }

            if(filters.startDate() != null && filters.endDate() == null){
                predicates.add(
                    cb.equal(root.get("date"), filters.startDate())
                );
            }

            var arrayPredicates = predicates.toArray(new Predicate[0]);
            return cb.and(arrayPredicates);

        };
    }
    
}
