package guru.springframework.orderservice.services;

import org.springframework.stereotype.Service;

import guru.springframework.orderservice.domain.Product;
import guru.springframework.orderservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Override
	public Product saveProduct(Product product) {
		return productRepository.saveAndFlush(product);
	}

	@Override
	public Product updateQuantityOnHand(Long id, Integer quantityOnHand) {
		Product product = productRepository.findById(id).orElseThrow();
		product.setQuantityOnHand(quantityOnHand);
		return productRepository.saveAndFlush(product);
	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
}
