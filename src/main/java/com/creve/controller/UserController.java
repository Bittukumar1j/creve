package com.creve.controller;
import com.creve.model.User;
import com.creve.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/sync")
    public User syncUser(@RequestBody User user) {
        return userService.saveOrUpdateUser(user.getName(), user.getEmail());
    }
    // Google Login ke baad user ko DB mein check/save karne ka endpoint
   /* @PostMapping("/sync")
    public User syncUser(@RequestParam String name, @RequestParam String email) {
        return userService.saveOrUpdateUser(name, email);
    }
*/
    // Current user ki details get karne ke liye
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        // Yahan tum simple repository call ya service call use kar sakte ho
        return null; // Implement logic here based on your requirement
    }
}