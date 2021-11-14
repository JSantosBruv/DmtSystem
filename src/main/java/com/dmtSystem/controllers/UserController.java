package com.dmtSystem.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dmtSystem.models.Userworker;
import com.dmtSystem.repository.UserWorkerRepository;

@RequestMapping("/User")
@Controller
public class UserController {


	private final UserWorkerRepository ur;

	public UserController(UserWorkerRepository ur) {
		this.ur = ur;
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	@PreAuthorize("hasRole('READ_PRIVILEGE')")
	public String changeUserPassword(Principal principal, @RequestParam("password") String password,
			@RequestParam("passwordSec") String passwordSec, @RequestParam("oldpassword") String oldPassword,
			RedirectAttributes rt) {

		Userworker user = ur.findByNim(principal.getName());

		if (!new BCryptPasswordEncoder().matches(oldPassword, user.getPass())) {
			rt.addFlashAttribute("mensagemErro", "Erro: Password antiga não está correta.");
			return "redirect:./updatePassword";
		}
		if (!password.equals(passwordSec)) {
			rt.addFlashAttribute("mensagemErro", "Erro: Os campos de introdução da nova Password não correspondem.");
			return "redirect:./updatePassword";
		}

		user.setPass(new BCryptPasswordEncoder(15).encode(password));
		ur.save(user);

		rt.addFlashAttribute("mensagemValid", "Password alterada com sucesso");

		return "redirect:./updatePassword";

	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
	@PreAuthorize("hasRole('READ_PRIVILEGE')")
	public ModelAndView changeUserPassword(Principal principal) {

		ModelAndView mvc = new ModelAndView("changePassword");
		Userworker user = ur.findByNim(principal.getName());

		mvc.addObject("name", user.getName());
		mvc.addObject("role", user.getRole());

		return mvc;

	}
}
