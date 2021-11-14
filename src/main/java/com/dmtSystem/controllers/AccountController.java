package com.dmtSystem.controllers;

import com.dmtSystem.exports.ExcelExportView;
import com.dmtSystem.models.*;
import com.dmtSystem.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequestMapping("/Administrador")
@Controller
public class AccountController {

    private final UserWorkerRepository ur;
    private final ProductRepository pr;
    private final OrderRepository er;
    private final MonthRepository mr;
    private final ClientRepository cr;
    private final CostureiraRepository ctr;


    public AccountController(UserWorkerRepository ur, ProductRepository pr, OrderRepository er, MonthRepository mr,
                             ClientRepository cr, CostureiraRepository ctr) {
        this.ur = ur;
        this.pr = pr;
        this.er = er;
        this.mr = mr;
        this.cr = cr;
        this.ctr = ctr;
    }

    @RequestMapping(value = "")
    public String adminArea() {

        return "adminArea";
    }

    @RequestMapping(value = "RegistarUtilizador", method = RequestMethod.GET)
    public String register() {
        return "registarUtilizador";
    }

    @RequestMapping(value = "/RegistarUtilizador", method = RequestMethod.POST)
    public String registerForm(@Valid Userworker worker, BindingResult result, @Valid Role r, BindingResult result1,
                               RedirectAttributes rt) {

        if (result.hasErrors()) {

            rt.addFlashAttribute("mensagemErro",
                                 "Erro: " + result.getAllErrors().iterator().next().getDefaultMessage());

            return "redirect:./RegistarUtilizador";

        } else if (result1.hasErrors()) {

            rt.addFlashAttribute("mensagemErro",
                                 "Erro: " + result1.getAllErrors().iterator().next().getDefaultMessage());

            return "redirect:./RegistarUtilizador";

        } else if (ur.existsById(worker.getNim())) {
            rt.addFlashAttribute("mensagemErro", "Utilizador já existente");
            return "redirect:./RegistarUtilizador";
        }
        worker.getRoles().add(r);
        worker.setPass(new BCryptPasswordEncoder(15).encode(worker.getPass()));
        ur.save(worker);

        rt.addFlashAttribute("mensagemValid", "Utilizador adicionado com sucesso");

        return "redirect:./RegistarUtilizador";
    }

    @RequestMapping(value = "/AlterarPermissao", method = RequestMethod.GET)
    public ModelAndView alterPermission() {

        ModelAndView mv = new ModelAndView("alterPermissions");

        Iterable<Userworker> usersOrdered = ur.findAll(Sort.by("name"));

        mv.addObject("usersOrdered", usersOrdered);

        return mv;
    }

    @RequestMapping(value = "/AlterarPermissao", method = RequestMethod.POST)
    public String alterPermissionForm(Role r, String name, RedirectAttributes rt) {

        if (!r.isValid()) {

            rt.addFlashAttribute("mensagemErro", "Erro no preenchimento de campos, tente de novo");
            return "redirect:./AlterarPermissao";
        }

        Userworker worker = ur.findByName(name);

        worker.getRoles().remove(0);
        worker.getRoles().add(r);

        ur.save(worker);

        rt.addFlashAttribute("mensagemValid", "Permissão alterada com sucesso");
        return "redirect:./AlterarPermissao";
    }

    @RequestMapping(value = "/ListarUtilizadores", method = RequestMethod.GET)
    public ModelAndView listUsers(@RequestParam(required = false, defaultValue = "nome") String order) {

        ModelAndView mv = new ModelAndView("listUsers");

        Sort sort = null;

        switch (order) {
            case "nome":
                sort = Sort.by("name");
                break;
            case "nim":
                sort = Sort.by("nim");
                break;
            case "permissao":
                sort = Sort.by("roles");
                break;
        }

        Iterable<Userworker> usersOrdered = ur.findAll(sort);

        mv.addObject("usersOrdered", usersOrdered);

        return mv;
    }

    @RequestMapping(value = "/RegistarProduto", method = RequestMethod.GET)
    public String form() {
        return "formProduto";
    }

    @RequestMapping(value = "/RegistarProduto", method = RequestMethod.POST)
    public String form(@Valid Product product, BindingResult result, RedirectAttributes rt) {

        if (result.hasErrors()) {
            rt.addFlashAttribute("mensagemErro", result.getAllErrors().iterator().next().getDefaultMessage());

            return "redirect:./RegistarProduto";
        } else if (pr.existsById(product.getNna())) {
            rt.addFlashAttribute("mensagemErro", "Erro: Artigo já existente");
            return "redirect:./RegistarProduto";
        }
        try {
            pr.save(product);
        } catch (Exception e) {
            rt.addFlashAttribute("mensagemErro", "Erro");
            return "redirect:./RegistarProduto";
        }

        rt.addFlashAttribute("mensagemValid", "Produto adicionado com sucesso");
        return "redirect:./RegistarProduto";
    }

    @RequestMapping(value = "/RemoverProduto", method = RequestMethod.GET)
    public ModelAndView remProduto() {

        ModelAndView mv = new ModelAndView("remProduto");

        Iterable<Product> produtosOrdered = pr.findAll(Sort.by("description"));

        mv.addObject("produtosOrdered", produtosOrdered);

        return mv;
    }

    @RequestMapping(value = "/delProd")
    public String delProd(String description, RedirectAttributes rt) {

        Product product = pr.findByDescription(description);
        try {
            pr.delete(product);
        } catch (Exception e) {
            rt.addFlashAttribute("mensagemErro",
                                 "Erro: Encomendas registadas no sistema não podem conter o produto selecionado");
            return "redirect:./RemoverProduto";

        }
        rt.addFlashAttribute("mensagemValid", "Artigo removido com sucesso");
        return "redirect:./RemoverProduto";

    }

    @RequestMapping(value = "/delUser")
    public String delUser(String nim, RedirectAttributes rt) {

        Userworker worker = ur.findByNim(nim);
        try {
            ur.delete(worker);
        } catch (Exception e) {
            rt.addFlashAttribute("mensagemErro", "Erro: Erro Interno, utilizador não pode ser removido.");
            return "redirect:./ListarUtilizadores";

        }
        rt.addFlashAttribute("mensagemValid", "Utilizador: " + worker.getName() + " removido com sucesso");
        return "redirect:./ListarUtilizadores";

    }

    @RequestMapping(value = "/RemoverEncomenda", method = RequestMethod.GET)
    public ModelAndView remEncomenda(@RequestParam(required = false, defaultValue = "cotacao") String order) {

        ModelAndView mv = new ModelAndView("remEncomenda");

        Sort sort = Utils.getSort(order);

        Iterable<OrderFlow> encomendas = er.findAll(sort);

        mv.addObject("encomendas", encomendas);

        return mv;
    }

    @RequestMapping(value = "/delEnc")
    public String delEnc(String Enccode, RedirectAttributes rt) {

        OrderFlow order = er.findByEncCode(Enccode);

        try {
            Client c = cr.findByNim(order.getClient().getNim());
            c.remEnc(order);
            cr.save(c);

            Month m = mr.findById(order.getMonth().getId());
            m.remOrder(order);

            int specialProds = order.getCounter();

            for (int i = 0; i < specialProds; i++)
                m.remProd();

            mr.save(m);
            er.delete(order);
        } catch (Exception e) {
            rt.addFlashAttribute("mensagemErro", "Erro: Encomenda não pode ser removida, tente mais tarde");
            System.out.println(e.getMessage());
            return "redirect:./RemoverEncomenda";

        }
        rt.addFlashAttribute("mensagemValid", "Encomenda removida com sucesso");
        return "redirect:./RemoverEncomenda";

    }

    @RequestMapping(value = "/RegistoDeActividade", method = RequestMethod.GET)
    public ModelAndView listActivity(@RequestParam(required = false, defaultValue = "cotacao") String order) {

        ModelAndView mv = new ModelAndView("listActivity");

        Sort sort = Utils.getSort(order);

        Iterable<OrderFlow> orders = er.findAll(sort);

        mv.addObject("encomendas", orders);

        return mv;

    }

    @RequestMapping(value = "/exportRegistoAtividade", method = RequestMethod.GET)
    public ModelAndView getExcel() {

        Sort sort = Sort.by("encCode");

        Iterable<OrderFlow> orders = er.findAll(sort);

        return new ModelAndView(new ExcelExportView(), "encomendas", orders);
    }

    @RequestMapping(value = "/RegistarCostureira", method = RequestMethod.GET)
    public ModelAndView addCostureira() {

        return new ModelAndView("registarCostureira");
    }

    @RequestMapping(value = "/RegistarCostureira", method = RequestMethod.POST)
    public String addCostureiraPost(String name, RedirectAttributes rt) {

        Costureira c = new Costureira();
        c.setName(name);

        ctr.save(c);

        rt.addFlashAttribute("mensagemValid", "Costureira adicionada com sucesso");

        return "redirect:./RegistarCostureira";
    }

}
