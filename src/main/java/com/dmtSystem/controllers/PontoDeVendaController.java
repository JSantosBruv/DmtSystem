package com.dmtSystem.controllers;

import com.dmtSystem.models.*;
import com.dmtSystem.repository.*;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequestMapping("/PontoDeVenda")
@Controller
public class PontoDeVendaController {


    private final OrderRepository er;
    private final ProductRepository pr;
    private final ClientRepository cr;
    private final MonthRepository mr;
    private final LogRepository lr;
    private final UserWorkerRepository ur;

    public PontoDeVendaController(OrderRepository er, ProductRepository pr, ClientRepository cr, MonthRepository mr,
                                  LogRepository lr, UserWorkerRepository ur) {
        this.er = er;
        this.pr = pr;
        this.cr = cr;
        this.mr = mr;
        this.lr = lr;
        this.ur = ur;
    }

    @RequestMapping(value = "/del")
    public String delProd(String Enccode, String desc) {

        OrderFlow encomenda = er.findByEncCode(Enccode);

        Log l = new Log();

        Product prod = pr.findByDescription(desc);
        encomenda.getProd().remove(prod);
        encomenda.getMonth().remProd();

        if (prod.getDescription().contains("DOLMAN"))
            encomenda.remCounter();

        l.setDescription("Remoção do artigo " + desc);
        l.setEncCode(Enccode);
        Userworker u = ur.findByNim(SecurityContextHolder.getContext().getAuthentication().getName());
        l.setName(u.getName());

        lr.save(l);
        er.save(encomenda);

        return "redirect:./Encomendas/" + encomenda.getEncCode();

    }

    @RequestMapping(value = "/Edit/{Enccode}", method = RequestMethod.GET)
    public ModelAndView editEncomenda(@PathVariable("Enccode") String Enccode) {

        OrderFlow encomenda = er.findByEncCode(Enccode);
        ModelAndView mv = new ModelAndView("order/editOrder");
        mv.addObject("encomenda", encomenda);

        return mv;

    }

    @RequestMapping(value = "/Edit/{Enccode}", method = RequestMethod.POST)
    public String editEncomendaForm(@PathVariable("Enccode") String Enccode, @Valid OrderFlow enc, BindingResult result,
                                    @Valid Client client, BindingResult result1, RedirectAttributes rt) {

        if (result.hasErrors() || result1.hasErrors()) {

            StringBuilder errors = new StringBuilder("Erros :");

            String res = checkErrors(result, errors, rt);

            if (!res.isEmpty())
                return res;

            res = checkErrors(result1, errors, rt);

            if (!res.isEmpty())
                return res;

            rt.addFlashAttribute("mensagemErro", errors.toString());
            return "redirect:./{Enccode}";

        }

        OrderFlow encomenda = er.findByEncCode(Enccode);
        cr.save(client);

        encomenda.setClient(client);
        encomenda.setDescription(enc.getDescription());
        encomenda.setDateLimite(enc.getDateLimite().toString().substring(0, 10));
        encomenda.setEncCode(enc.getEncCode());
        client.addEnc(encomenda);

        Month mNew = mr.findById(enc.getDateLimite().getMonthValue());
        Month mOld = mr.findById(encomenda.getMonth().getId());

        mOld.remOrder(encomenda);

        int specialProds = encomenda.getCounter();

        for (int i = 0; i < specialProds; i++) {
            mOld.remProd();
            mNew.addProd();
        }

        mr.save(mOld);

        encomenda.setMonth(mNew);
        er.save(encomenda);

        mNew.addOrder(encomenda);

        mr.save(mNew);

        rt.addFlashAttribute("mensagemValid", "Encomenda editada com sucesso");
        return "redirect:./" + enc.getEncCode();

    }

    @RequestMapping(value = "/Encomendas/{Enccode}", method = RequestMethod.POST)
    public String detailEncomendaProdForm(@PathVariable("Enccode") String Enccode, String description) {

        OrderFlow encomenda = er.findByEncCode(Enccode);
        Log l = new Log();
        Product prod = pr.findByDescription(description);

        encomenda.getProd().add(prod);

        if (prod.getDescription().contains("DOLMAN")) {
            encomenda.getMonth().addProd();
            encomenda.addCounter();
        }

        l.setDescription("Adicionado Produto " + description);
        l.setEncCode(Enccode);
        Userworker u = ur.findByNim(SecurityContextHolder.getContext().getAuthentication().getName());
        l.setName(u.getName());

        lr.save(l);
        er.save(encomenda);

        return "redirect:./" + encomenda.getEncCode();

    }

    @RequestMapping(value = "/Encomendas/{Enccode}", method = RequestMethod.GET)
    public ModelAndView detailEncomendaProd(@PathVariable("Enccode") String Enccode) {

        OrderFlow encomenda = er.findByEncCode(Enccode);
        ModelAndView mv = new ModelAndView("order/infoEncomendaProd");
        mv.addObject("encomenda", encomenda);

        Iterable<Product> produtos = encomenda.getProd();

        Iterable<Product> produtosOrdered = pr.findAll(Sort.by("description"));

        mv.addObject("produtos", produtos);

        mv.addObject("produtosOrdered", produtosOrdered);

        return mv;

    }

    @RequestMapping(value = {"/Encomendas", ""})
    public ModelAndView listEncomendasProd(Model model, @ModelAttribute("user") String user,
                                           @RequestParam(required = false, defaultValue = "cotacao") String order) {

        ModelAndView mv = new ModelAndView("encomendasProd");

        Sort sort = Utils.getSort(order);
        Iterable<OrderFlow> encomendas = er.findAll(sort);

        mv.addObject("encomendas", encomendas);
        model.addAttribute(user);

        return mv;
    }

    @RequestMapping(value = "/RegistarEncomenda", method = RequestMethod.GET)
    public String form() {
        return "order/formOrder";
    }

    @RequestMapping(value = "/RegistarEncomenda", method = RequestMethod.POST)
    public String form(@Valid OrderFlow encomenda, BindingResult result, @Valid Client client, BindingResult result1,
                       RedirectAttributes rt) {

        if (result.hasErrors() || result1.hasErrors()) {

            StringBuilder errors = new StringBuilder("Erros :");

            String res = checkErrors(result, errors, rt);

            if (!res.isEmpty())
                return res;

            res = checkErrors(result1, errors, rt);

            if (!res.isEmpty())
                return res;

            rt.addFlashAttribute("mensagemErro", errors.toString());
            return "redirect:./RegistarEncomenda";

        }
        if (er.existsById(encomenda.getEncCode())) {
            rt.addFlashAttribute("mensagemErro", "Erros: \n.Cotação já existente no sistema");
            return "redirect:./RegistarEncomenda";
        }

        if (!cr.existsById(client.getNim()))
            cr.save(client);

        encomenda.setClient(client);
        er.save(encomenda);
        client.addEnc(encomenda);
        cr.save(client);

        Month m = mr.findById(encomenda.getDateLimite().getMonthValue());
        encomenda.setMonth(m);
        er.save(encomenda);
        m.addOrder(encomenda);
        mr.save(m);

        return "redirect:./Encomendas/" + encomenda.getEncCode();
    }

    private String checkErrors(BindingResult result, StringBuilder errors, RedirectAttributes rt) {

        for (ObjectError ob : result.getAllErrors()) {
            if (!ob.getCode().equals("methodInvocation"))
                errors.append(String.join(System.lineSeparator() + ".", errors, ob.getDefaultMessage()));
            else {
                errors.append("Don't even try :)");
                rt.addFlashAttribute("mensagemErro", errors.toString());
                return "redirect:./{Enccode}";
            }
        }

        return "";
    }
}