package br.edu.utfpr.pb.pw26s.server.service;

import br.edu.utfpr.pb.pw26s.server.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService extends CrudService<Product, Long> {

    List<Product> findByDescriptionContaining(String description);

    void saveImage(MultipartFile file, Product product);

    void saveImageFile(MultipartFile file, Product product);

    String getProductImage(Long id);
}
