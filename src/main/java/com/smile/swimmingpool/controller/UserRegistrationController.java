package com.smile.swimmingpool.controller;



import com.smile.swimmingpool.entity.User;
import com.smile.swimmingpool.service.UserRegistrationDto;
import com.smile.swimmingpool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/admin/user")
public class UserRegistrationController {
    @Autowired
	private UserService userService;



	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}

	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}

	@GetMapping
	public String showRegistrationForm() {
		return "registration";
	}

	@PostMapping("save")
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
		userService.save(registrationDto);
		System.out.println(registrationDto);
		return "redirect:/admin/user/users";
	}

	@GetMapping("/users")
	public String fetchUsers(Model model, HttpSession session) {
		List<User> userList = userService.findAll();
		String userName = String.valueOf(session.getAttribute("userName"));
		model.addAttribute("userName", userName);
		return "users";
	}

	@PostMapping("/editUser")
	public ResponseEntity<?> fetchUser(HttpServletRequest request, Model model) throws SQLException, ClassNotFoundException {
		Long userId = Long.parseLong(request.getParameter("userId"));
		System.out.println(userId);
		Optional<User> user = userService.findById(userId);
		System.out.println(user);
		Optional<User> result;
		if (user == null) {
			result = null;
		} else {
			result = user;
		}
		return ResponseEntity.ok(result);
	}

	@PostMapping("/updateUser")
	public String updateUser(HttpServletRequest request, Model model) throws SQLException, ClassNotFoundException {
		String userId = request.getParameter("userId");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phoneNo = request.getParameter("phoneNo");
		boolean status = Boolean.parseBoolean(request.getParameter("status"));
		User user = new User(userId, firstName, lastName, email, phoneNo, status);
		System.out.println(user);
		String msg = userService.updateById(user);
		System.out.println(msg);
		String result;
		if (msg == null) {
			result = null;
		} else {
			result = msg;
		}
		return "redirect:/admin/user/users";
	}

	@PostMapping("/deleteUser")
	public String deleteUser(HttpServletRequest request) throws NumberFormatException {
		Long userId = Long.parseLong(request.getParameter("userId"));
		String msg = userService.deleteById(userId);
		return "redirect:/admin/user/users";
	}





}


