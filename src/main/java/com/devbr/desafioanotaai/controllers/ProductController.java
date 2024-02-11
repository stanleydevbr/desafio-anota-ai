package com.devbr.desafioanotaai.controllers;

import com.devbr.desafioanotaai.domain.product.Product;
import com.devbr.desafioanotaai.domain.product.ProductDTO;
import com.devbr.desafioanotaai.services.ProductService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private ProductService service;
    public ProductController(ProductService service){
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<Product> Insert(@RequestBody ProductDTO productData){

        Product newProduct = this.service.insert(productData);
        return ResponseEntity.ok().body(newProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> GetAll(){
        var products = this.service.getAll();
        return ResponseEntity.ok().body(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> Update(@PathVariable("id") String id, @RequestBody ProductDTO productData){

        Product newProduct = this.service.update(id, productData);
        return ResponseEntity.ok().body(newProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> Delete(@PathVariable("id") String id){
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
