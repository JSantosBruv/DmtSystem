package com.dmtSystem.controllers;

import com.dmtSystem.models.Role;
import com.dmtSystem.models.Userworker;
import com.dmtSystem.repository.RoleRepository;
import com.dmtSystem.repository.UserWorkerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/login")
@Controller
public class LoginController {


    private final UserWorkerRepository ur;
    private final RoleRepository rr;
	private boolean consistent;

    public LoginController(UserWorkerRepository ur, RoleRepository rr) {

        this.ur = ur;
        this.rr = rr;
    }


    @RequestMapping
    public String cheese() {

        if (!consistent) {
            checkDummy();
            consistent = true;
        }

        return "login.html";
    }

    /*Just for dev and testing purposes, would not be here in production*/
    private void checkDummy() {

        if (ur.count() == 0) {
            Role r = new Role();
            r.setRoleFunction("ROLE_ADMIN");
            Role r1 = new Role();
            r1.setRoleFunction("ROLE_ALFAIATARIA");
            Role r2 = new Role();
            r2.setRoleFunction("ROLE_PONTOVENDA");
            rr.save(r);
            rr.save(r1);
            rr.save(r2);

            Userworker u = new Userworker();
            u.setName("Admin");
            u.setNim("admin");
            u.setPass(new BCryptPasswordEncoder(10).encode("superStrongPassword"));
            u.getRoles().add(r);

            ur.save(u);
        }
    }
}