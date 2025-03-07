package br.com.fiap.fin_money_api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.fin_money_api.model.Category;
import br.com.fiap.fin_money_api.repository.CategoryRepository;

@RestController
@RequestMapping("categories")
public class CategoryController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private CategoryRepository repository;

    @GetMapping
    public List<Category> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Category create(@RequestBody Category category) {
        log.info("Cadastrando categoria " + category.getName());
        repository.save(category);
        return category;
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> get(@PathVariable Long id) {
        log.info("Buscando categoria " + id);
        return ResponseEntity.ok(getCategory(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Category> delete(@PathVariable Long id) {
        log.info("Deletando categoria " + id);
        repository.delete(getCategory(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category) {
        log.info("Atualizando categoria " + id + " com " + category);

        getCategory(id);
        category.setId(id);
        repository.save(category);
        return ResponseEntity.ok(category);
    }

    private Category getCategory(Long id) {
        return repository.findById(id)
                .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada")
                );
    }

}
