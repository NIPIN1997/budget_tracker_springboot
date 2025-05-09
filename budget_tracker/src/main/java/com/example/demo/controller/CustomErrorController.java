package com.example.demo.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		String errorMessage = "An unknown error occurred.";
		if (status != null) {
			int statusCode = Integer.parseInt(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				errorMessage = "Page not found (404).";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				errorMessage = "Internal server error (500).";
			} else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
				errorMessage = "Bad request (400).";
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				errorMessage = "Access forbidden (403).";
			} else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
				errorMessage = "Method not allowed (405).";
			} else {
				errorMessage = "Error occurred with status code: " + statusCode;
			}
		}
		model.addAttribute("errorMessage", errorMessage);
		return "error";
	}

}
