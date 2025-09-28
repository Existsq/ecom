package ru.exist.ecom.core.application.product.mapper;

import ru.exist.ecom.core.application.product.ProductDTO;
import ru.exist.ecom.core.domain.product.Product;

public class ProductMapper {
  public static ProductDTO toDto(Product product) {
    return new ProductDTO(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getStock());
  }

  public static Product toEntity(ProductDTO dto) {
    Product product = new Product();
    product.setId(dto.id());
    product.setName(dto.name());
    product.setDescription(dto.description());
    product.setPrice(dto.price());
    product.setStock(dto.stock());

    return product;
  }
}
