package com.telusko.Ecommerce.Page.Controller;

import com.telusko.Ecommerce.Page.Model.Product;
import com.telusko.Ecommerce.Page.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{pid}")
    public ResponseEntity<Product> getProduct(@PathVariable int pid){
        Product product = productService.getProduct(pid);
        if(product!=null) return new ResponseEntity<>(product,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try{
            Product prod = productService.addProduct(product,imageFile);
            return new ResponseEntity<>(prod,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product prod = productService.getProduct(productId);
        byte[] imageFile = prod.getImageData();

        return ResponseEntity.ok().body(imageFile);
//                .contentType(MediaType.valueOf(prod.getImageType()))

    }

    @PutMapping("/product/{pid}")
    public ResponseEntity<String> updateProduct(@PathVariable int pid,@RequestPart Product product,@RequestPart MultipartFile imageFile){
        Product prod = null;
        try {
            prod = productService.updateProduct(pid,product,imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
        }
        if(prod!=null) return new ResponseEntity<>("Updated",HttpStatus.OK);
        return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product prod = productService.getProduct(id);
        if(prod!=null){
            productService.deleteProduct(id);
            return new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to delete!",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        List<Product> products = productService.searchProducts(keyword);
        System.out.println("Searching for "+keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
