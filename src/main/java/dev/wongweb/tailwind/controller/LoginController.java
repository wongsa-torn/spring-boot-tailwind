package dev.wongweb.tailwind.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String reason, Model model) {
        model.addAttribute("reason", reason);
        return "login/login"; // ระบุ path ไปที่ templates/login/login.html
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
            @RequestParam String pass,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        String sql = "SELECT * FROM PUBLICPAGE_STAFF WHERE username = ? AND password = ?";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, username, pass);

        if (!result.isEmpty()) {
            Map<String, Object> user = result.get(0);

            // บันทึกข้อมูล session
            session.setAttribute("username", user.get("username"));
            session.setAttribute("role", user.get("role"));
            session.setAttribute("airportlist", user.get("hopo"));
            session.setMaxInactiveInterval(-1); // ไม่หมดอายุ

            // ลบ session เก่า และเพิ่ม session ใหม่
            jdbcTemplate.update("DELETE FROM session_members WHERE username = ?", user.get("username"));
            jdbcTemplate.update("INSERT INTO session_members (session_id, username) VALUES (?, ?)",
                    session.getId(), user.get("username"));

            // redirect ตาม role
            return "qa".equals(user.get("role")) ? "main/main" : "main/main";
        } else {
            redirectAttributes.addAttribute("reason", "Invalid username or password");
            return "redirect:/login";
        }
    }

    @GetMapping("/db-test")
    @ResponseBody
    public String testConnection() {
        try {
            // Integer result = jdbcTemplate.queryForObject("SELECT 1 FROM DUAL", Integer.class);
            return "Database connected successfully! ✅";
        } catch (Exception e) {
            return "Database connection failed ❌: " + e.getMessage();
        }
    }

}
