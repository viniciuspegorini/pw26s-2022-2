package br.edu.utfpr.pb.pw26s.server.controller;

import br.edu.utfpr.pb.pw26s.server.model.Product;
import br.edu.utfpr.pb.pw26s.server.service.CrudService;
import br.edu.utfpr.pb.pw26s.server.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController extends CrudController<Product, Long> {

    @Autowired
    private ProductService productService;

    @Override
    protected CrudService<Product, Long> getService() {
        return this.productService;
    }

    // http://localhost:8080/products/description?desc=TV&price=900
    @GetMapping("description")
    public List<Product> findByDescription(@RequestParam("desc") String desc) {
        return this.productService.findByDescriptionContaining(desc);
    }

    // http://localhost:8080/products/description/TV
    /*
    @GetMapping("description/{desc}")
    public List<Product> findByDescription(@PathVariable("desc") String desc) {
        return this.productService.findByDescriptionContaining(desc);
    }*/

    @PostMapping("upload-a")
    public Product save(@RequestPart("product") @Valid Product entity,
                        @RequestPart("image") MultipartFile image) {
        getService().save(entity);
        productService.saveImage( image, entity );
        return entity;
    }

    @PostMapping("upload-b")
    public Product saveImageFile(@RequestPart("product") @Valid Product entity,
                        @RequestPart("image") MultipartFile image) {
        getService().save(entity);
        productService.saveImageFile( image, entity );
        return entity;
    }

    @GetMapping("image/{id}")
    public String getImage(@PathVariable Long id) {
        return productService.getProductImage(id);
    }

    @GetMapping("download/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long id,
                                                  HttpServletResponse response) throws FileNotFoundException {
        Product product = getService().findOne(id);
        File file = new File("/opt/uploads/images-product/" + product.getImageName());
        InputStreamResource resource = new InputStreamResource(
                new FileInputStream(file)
        );
        return ResponseEntity.ok()
                .headers(httpHeaders -> httpHeaders.add(
                        "Content-Disposition",
                        "attachment; filename=\"" + product.getImageName() + "\""
                )).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
