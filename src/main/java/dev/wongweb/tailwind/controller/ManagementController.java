package dev.wongweb.tailwind.controller;

import dev.wongweb.tailwind.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import dev.wongweb.tailwind.service.UserService;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/management")
public class ManagementController {

    private final UserService userService;

    public ManagementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getManagementPage(Model model) {
        // ดึงข้อมูลผู้ใช้จาก service
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users); // ส่งข้อมูลไปที่หน้า
        return "management/management"; // แสดงผลใน template ของ management
    }

    // แสดงฟอร์มเพิ่มข้อมูลใน Modal
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User()); // สร้าง user ว่างให้กับฟอร์ม
        return "management/management"; // ใช้ template เดิมเพื่อแสดงหน้า
    }

    // บันทึกข้อมูลเมื่อฟอร์มถูกส่ง
    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/management"; // หลังจากบันทึกแล้ว redirect ไปที่หน้าหลัก
    }
}