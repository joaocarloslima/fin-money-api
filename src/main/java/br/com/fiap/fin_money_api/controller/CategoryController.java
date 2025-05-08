package br.com.fiap.fin_money_api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import br.com.fiap.fin_money_api.model.User;
import br.com.fiap.fin_money_api.repository.CategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("categories")
@Slf4j
public class CategoryController {
    
    @Autowired
    private CategoryRepository repository;

    @GetMapping
    @Operation(summary = "Listar categorias", description = "Retorna um array com todas as categorias")
    @Cacheable("categories")
    public List<Category> index(@AuthenticationPrincipal User user) {
        return repository.findByUser(user);
    }

    @PostMapping
    @CacheEvict(value = "categories", allEntries = true)
    @Operation(responses = @ApiResponse(responseCode = "400", description = "Validação falhou"))
    @ResponseStatus(code = HttpStatus.CREATED)
    public Category create(@RequestBody @Valid Category category, @AuthenticationPrincipal User user) {
        log.info("Cadastrando categoria " + category.getName());
        category.setUser(user);
        return repository.save(category);
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> get(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.info("Buscando categoria " + id);
        return ResponseEntity.ok(getCategory(id, user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Category> delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        log.info("Deletando categoria " + id);
        repository.delete(getCategory(id, user));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody @Valid Category category, @AuthenticationPrincipal User user) {
        log.info("Atualizando categoria " + id + " com " + category);

        var oldCategory = getCategory(id, user);
        //category.setId(id);
        //category.setUser(user);
        BeanUtils.copyProperties(category, oldCategory, "id", "user");
        repository.save(oldCategory);
        return ResponseEntity.ok(oldCategory);
    }

    private Category getCategory(Long id, User user) {
        var category = repository.findById(id)
                .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada")
                );
        if(!category.getUser().equals(user)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return category;
    }

}
