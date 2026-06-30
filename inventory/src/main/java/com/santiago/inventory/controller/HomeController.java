package com.santiago.inventory.controller;

import com.santiago.inventory.service.ProductService;
import com.santiago.inventory.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProductService productService;
    private final UserService userService;

    public HomeController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("totalProducts",
                productService.getTotalProducts());

        model.addAttribute("inventoryValue",
                productService.getTotalInventoryValue());

        model.addAttribute("lowStockProducts",
                productService.getLowStockProducts());
        
        model.addAttribute("totalUsers",
                userService.countUsers());

        return "index";
    }
}