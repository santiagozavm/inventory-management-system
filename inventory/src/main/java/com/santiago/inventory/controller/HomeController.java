package com.santiago.inventory.controller;

import com.santiago.inventory.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("totalProducts",
                productService.getTotalProducts());

        model.addAttribute("inventoryValue",
                productService.getTotalInventoryValue());

        model.addAttribute("lowStockProducts",
                productService.getLowStockProducts());

        return "index";
    }
}