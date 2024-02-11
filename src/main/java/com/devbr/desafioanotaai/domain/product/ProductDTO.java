package com.devbr.desafioanotaai.domain.product;

import com.devbr.desafioanotaai.domain.category.Category;

public record ProductDTO(String title, String description, String ownerId, Integer price, String categoryId) {

}
