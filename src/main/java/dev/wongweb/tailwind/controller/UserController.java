package dev.wongweb.tailwind.controller;

import dev.wongweb.tailwind.model.User;
import dev.wongweb.tailwind.service.UserService;
import org.springframework.stereotype.Controller; // <== ต้องใช้ Controller
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(Model model) {
        // ดึงข้อมูลผู้ใช้จาก service
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users); // ใส่ข้อมูลลงใน model
        return "management/management"; // เปลี่ยนไปแสดงในหน้า management
    }

}
