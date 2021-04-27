package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.geekbrains.entities.Product;
import ru.geekbrains.service.ProductService;

import java.util.List;

@Controller
public class RestController {

    private final ProductService productService;

    @Autowired
    public RestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/add")
    public String index (Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "shop";
    }

    @PostMapping("/add")
    public String addProduct(Product product){
        productService.saveOrUpdate(product);
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String showProducts(Model model){
        List<Product> allProd = productService.getProducts();
        model.addAttribute("products", allProd);
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteProducts(@PathVariable(value = "id") Long id){
        productService.deleteProd(id);
        return "redirect:/products";
    }

    @GetMapping("/update/{id}")
    public String updateProducts(Model model, @PathVariable(value = "id") Long id){
        model.addAttribute("product", productService.getProdById(id));
        return "shop";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id){
        productService.saveOrUpdate(productService.getProdById(id));
        return "redirect:/products";
    }

    @GetMapping("products/{id}")
    public String showProducts(Model model, @PathVariable(value = "id") Long id){
        model.addAttribute("product", productService.getProdById(id));
        return "/product";
    }

    @GetMapping("/min")
    public String sortByMinPrice(Model model){
        List<Product> allProd = productService.filteringByMinPrice();
        model.addAttribute("products", allProd);
        return "index";
    }

    @GetMapping("/max")
    public String sortByMaxPrice(Model model){
        List<Product> allProd = productService.filteringByMaxPrice();
        model.addAttribute("products", allProd);
        return "index";
    }

    @GetMapping("/middle")
    public String sortByMiddlePrice(Model model){
        List<Product> allProd = productService.filteringByMiddlePrice();
        model.addAttribute("products", allProd);
        return "index";
    }

}
