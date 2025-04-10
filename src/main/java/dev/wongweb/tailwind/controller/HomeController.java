package dev.wongweb.tailwind.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;



@Controller
public class HomeController {

    // @GetMapping("/")
    // public String home() {
    // return "index";
    // }
    // @RequestMapping(value = {"/", "/efids", "/efids/login"})
    @GetMapping(value = { "/" })
    public String index(HttpSession session) {
        // เช็คว่า login แล้วหรือยัง (session ยังไม่มี username)
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }

        // ถ้า login แล้วให้ไปหน้า main หรือ index ก็ได้
        return "main/main"; // ชื่อ template ตามที่คุณวางไว้ในโฟลเดอร์ templates/main/main.html
    }
}
