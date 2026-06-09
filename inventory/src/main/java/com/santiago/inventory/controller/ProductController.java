package com.santiago.inventory.controller;

import com.santiago.inventory.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import com.santiago.inventory.model.Product;


@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public String listProducts(@RequestParam(required = false) String keyword,
                            Model model) {

        if (keyword != null && !keyword.isEmpty()) {

            model.addAttribute("products",
                    productService.searchProducts(keyword));

        } else {

            model.addAttribute("products",
                    productService.getAllProducts());
        }

        model.addAttribute("keyword", keyword);

        return "products";
    }

    @GetMapping("/products/new")
    public String showCreateForm(Model model) {

        model.addAttribute("product", new Product());

        return "create-product";
    }
    @PostMapping("/products")
    public String saveProduct(@Valid @ModelAttribute Product product,BindingResult result) {

        if (result.hasErrors()) {

            if (product.getId() != null) {
                return "edit-product";
            }

            return "create-product";
        }

        productService.saveProduct(product);

        return "redirect:/products";
    }
    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {

        Product product = productService.getProductById(id);

        model.addAttribute("product", product);

        return "edit-product";
    }
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return "redirect:/products";
    }
}