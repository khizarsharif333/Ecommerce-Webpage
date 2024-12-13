package com.telusko.Ecommerce.Page.Service;

import com.telusko.Ecommerce.Page.Model.Product;
import com.telusko.Ecommerce.Page.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public Product updateProduct(int pid,Product product,MultipartFile imageFile) throws IOException {
        Product prod = productRepo.findById(pid).orElse(null);
        if(prod==null){
            return null;
        }
        product.setImageType(imageFile.getContentType());
        product.setImageName(imageFile.getName());
        product.setImageData(imageFile.getBytes());
        return productRepo.save(product);
    }


    public List<Product> getProducts() {
        return productRepo.findAll();
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageType(imageFile.getContentType());
        product.setImageName(imageFile.getName());
        product.setImageData(imageFile.getBytes());
        return productRepo.save(product);
    }

    public Product getProduct(int pid) {
        return productRepo.findById(pid).orElse(null);
    }

    public void deleteProduct(int pid) {
        productRepo.deleteById(pid);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepo.searchProducts(keyword);
    }
}
