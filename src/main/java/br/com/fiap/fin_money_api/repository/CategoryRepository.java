package br.com.fiap.fin_money_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.fin_money_api.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
