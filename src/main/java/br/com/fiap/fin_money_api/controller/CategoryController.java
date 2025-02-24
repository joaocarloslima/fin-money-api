package br.com.fiap.fin_money_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.fin_money_api.model.Category;

@RestController
public class CategoryController {
    
    @RequestMapping(produces = "application/json", path = "/categories", method = {RequestMethod.GET})
    public Category index(){
        return new Category(1L, "Educação", "book");
    }

}
