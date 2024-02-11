package com.devbr.desafioanotaai.services;

import com.devbr.desafioanotaai.domain.category.Category;
import com.devbr.desafioanotaai.domain.category.CategoryDTO;
import com.devbr.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.devbr.desafioanotaai.domain.product.Product;
import com.devbr.desafioanotaai.domain.product.ProductDTO;
import com.devbr.desafioanotaai.domain.product.exceptions.ProductNotFoundException;
import com.devbr.desafioanotaai.repositories.ProductRepository;
import com.devbr.desafioanotaai.services.aws.AwsSnsService;
import com.devbr.desafioanotaai.services.aws.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository repository;
    private CategoryService categoryService;
    private AwsSnsService snsService;
    public ProductService(ProductRepository repository, CategoryService categoryService, AwsSnsService snsService)
    {
        this.repository = repository;
        this.categoryService = categoryService;
        this.snsService = snsService;
    }

    public Product insert(ProductDTO productData){
        Category category = categoryService.getById(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Product newProduct = new Product(productData);

        this.repository.save(newProduct);
        this.snsService.publish(new MessageDTO(newProduct.toString()));

        return newProduct;
    }

    public List<Product> getAll(){
//        List<Product> products = this.repository.findAll();
        return this.repository.findAll();
    }

    public Product update(String id, ProductDTO productData){
        Product product = this.repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        if(productData.categoryId() != null){
            this.categoryService.getById(productData.categoryId())
                    .orElseThrow(CategoryNotFoundException::new);
            product.setCategory(productData.categoryId());
        }

        if(!productData.title().isEmpty()) product.setTitle(productData.title());
        if(!productData.description().isEmpty()) product.setDescription(productData.description());
        if(!(productData.price() == null)) product.setPrice(productData.price());

        this.repository.save(product);
        this.snsService.publish(new MessageDTO(product.toString()));

        return product;
    }

    public void delete(String id){
        Product product = this.repository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        this.repository.delete(product);
    }
}
