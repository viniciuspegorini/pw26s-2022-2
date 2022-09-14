package br.edu.utfpr.pb.pw26s.server.controller;

import br.edu.utfpr.pb.pw26s.server.minio.payload.FileResponse;
import br.edu.utfpr.pb.pw26s.server.minio.service.MinioService;
import br.edu.utfpr.pb.pw26s.server.minio.util.FileTypeUtils;
import br.edu.utfpr.pb.pw26s.server.model.Product;
import br.edu.utfpr.pb.pw26s.server.service.CrudService;
import br.edu.utfpr.pb.pw26s.server.service.ProductService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController extends CrudController<Product, Long> {

    private final ProductService productService;
    private final MinioService minioService;

    public ProductController(ProductService productService, MinioService minioService) {
        this.productService = productService;
        this.minioService = minioService;
    }

    @Override
    protected CrudService<Product, Long> getService() {
        return this.productService;
    }

    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public Product saveFilename(@RequestPart("product") @Valid Product entity,
                                @RequestPart("image") @Valid MultipartFile file) {
        String fileType = FileTypeUtils.getFileType(file);
        if (fileType != null) {
            FileResponse fileResponse = minioService.putObject(file, "commons", fileType);
            entity.setImageName(fileResponse.getFilename());
            entity.setContentType(fileResponse.getContentType());
        }
        return productService.save(entity);
    }

    @GetMapping(value = "download/{id}")
    public void downloadFile(@PathVariable("id") Long id, HttpServletResponse response) {
        InputStream in = null;
        try {
            Product product = productService.findOne(id);
            in = minioService.downloadObject("commons", product.getImageName());
            response.setHeader("Content-Disposition", "attachment;filename="
                    + URLEncoder.encode(product.getImageName(), "UTF-8"));
            response.setCharacterEncoding("UTF-8");
            // Remove bytes from InputStream Copied to the OutputStream .
            IOUtils.copy(in, response.getOutputStream());
        } catch (UnsupportedEncodingException e) {

        } catch (IOException e) {

        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
