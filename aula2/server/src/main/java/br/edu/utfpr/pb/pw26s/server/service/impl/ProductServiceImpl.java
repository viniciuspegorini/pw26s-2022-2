package br.edu.utfpr.pb.pw26s.server.service.impl;

import br.edu.utfpr.pb.pw26s.server.model.Product;
import br.edu.utfpr.pb.pw26s.server.repository.ProductRepository;
import br.edu.utfpr.pb.pw26s.server.service.ProductService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ProductServiceImpl extends CrudServiceImpl<Product, Long>
        implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    protected JpaRepository<Product, Long> getRepository() {
        return this.productRepository;
    }

    @Override
    public List<Product> findByDescriptionContaining(String description) {
        return productRepository.findByDescriptionContaining(description);
    }

    @Override //Salvando o arquivo em disco
    public void saveImage(MultipartFile file, Product product) {
        File dir = new File("/opt/uploads" + File.separator + "images-product");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String suffix = file.getOriginalFilename().substring(
                file.getOriginalFilename().lastIndexOf("."));
        try {
            FileOutputStream fileOut = new FileOutputStream(
                    new File(dir + File.separator + product.getId() + suffix));
            BufferedOutputStream stream = new BufferedOutputStream(fileOut);
            stream.write(file.getBytes());
            stream.close();
            fileOut.close();
            product.setImageName(product.getId() + suffix);
            this.productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveImageFile(MultipartFile file, Product product) {
        try {
            String suffix = file.getOriginalFilename().substring(
                    file.getOriginalFilename().lastIndexOf("."));

            product.setImageFile(file.getBytes());
            product.setImageName(product.getId() + suffix);

            this.save(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getProductImage(Long id) {
        Product product = productRepository.getOne(id);
        try {
            String filename = "/opt/uploads" + File.separator + "images-product" + File.separator + product.getImageName();
            return encodeFileToBase64(filename);
        } catch (Exception e) {
            return null;
        }
    }

    private String encodeFileToBase64(String filename) throws IOException {
        File file = new File(filename);
        FileInputStream stream = new FileInputStream(file);
        byte[] encoded = Base64.encodeBase64(IOUtils.toByteArray(stream));
        stream.close();
        return new String(encoded, StandardCharsets.US_ASCII);
    }


}
