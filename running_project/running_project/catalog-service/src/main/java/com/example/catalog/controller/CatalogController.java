package com.example.catalog.controller;
import com.example.catalog.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/items")
public class CatalogController {

    private final List<Product> items = new ArrayList<>();

   
    public CatalogController() {
        items.add(new Product("1","Baby T-Shirt",10.5,50,"Clothing"));
        items.add(new Product("2","Baby Pants",15.0,30,"Clothing"));
        items.add(new Product("3","Baby Hat",5.0,100,"Accessories"));
    }

    @GetMapping
    public List<Product> getAll() {
        return items;
    }

    
    @GetMapping("/{id}")
    public Product getById(@PathVariable String id) {
        return items.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
    }

   
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addItem(@RequestBody Product p) {
        if (items.stream().anyMatch(i -> i.getId().equals(p.getId()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Item with given id already exists");
        }
        items.add(p);
    }
}