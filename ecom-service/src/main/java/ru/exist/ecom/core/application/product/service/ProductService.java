package ru.exist.ecom.core.application.product.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.exist.ecom.core.application.exception.ProductNotFoundException;
import ru.exist.ecom.core.application.product.ProductDTO;
import ru.exist.ecom.core.application.product.mapper.ProductMapper;
import ru.exist.ecom.core.domain.product.Product;
import ru.exist.ecom.core.infrastructure.product.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public List<ProductDTO> getAllProducts() {
    return productRepository.findAll().stream()
        .map(ProductMapper::toDto)
        .collect(Collectors.toList());
  }

  public ProductDTO getProduct(UUID productId) {
    return productRepository
        .findById(productId)
        .map(ProductMapper::toDto)
        .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));
  }

  @Transactional
  public ProductDTO createProduct(ProductDTO dto) {
    Product product = ProductMapper.toEntity(dto);
    Product saved = productRepository.save(product);
    return ProductMapper.toDto(saved);
  }

  @Transactional
  public ProductDTO updateProduct(UUID productId, ProductDTO dto) {
    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));

    product.setName(dto.name());
    product.setDescription(dto.description());
    product.setPrice(dto.price());
    product.setStock(dto.stock());

    Product saved = productRepository.save(product);
    return ProductMapper.toDto(saved);
  }

  @Transactional
  public void deleteProduct(UUID productId) {
    productRepository.deleteById(productId);
  }
}
