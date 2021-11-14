package com.dmtSystem.controllers;


import com.dmtSystem.models.*;
import com.dmtSystem.repository.CostureiraRepository;
import com.dmtSystem.repository.OrderRepository;
import com.dmtSystem.repository.LogRepository;
import com.dmtSystem.repository.UserWorkerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/Alfaiataria")
@Controller
public class AlfaiatariaController {

    private final OrderRepository er;
    private final LogRepository lr;
    private final UserWorkerRepository ur;
    private final CostureiraRepository cr;

    public AlfaiatariaController(OrderRepository er, LogRepository lr, UserWorkerRepository ur,
                                 CostureiraRepository cr) {
        this.er = er;
        this.lr = lr;
        this.ur = ur;
        this.cr = cr;
    }

    @RequestMapping(value = {"/Encomendas", ""})
    public ModelAndView listEncomendasState(@RequestParam(required = false, defaultValue = "cotacao") String order) {

        ModelAndView mv = new ModelAndView("encomendasState");

        Sort sort = Utils.getSort(order);
        Iterable<OrderFlow> encomendas = er.findAll(sort);

        mv.addObject("encomendas", encomendas);

        return mv;
    }

    @RequestMapping(value = "/Encomendas/{Enccode}", method = RequestMethod.GET)
    public ModelAndView detailEncomendaState(@PathVariable("Enccode") String Enccode) {

        OrderFlow encomenda = er.findByEncCode(Enccode);

        Iterable<Costureira> costureiras = cr.findAll(Sort.by("name"));

        ModelAndView mv = new ModelAndView("order/infoEncomendaState");
        mv.addObject("encomenda", encomenda);

        Iterable<Product> produtos = encomenda.getProd();

        mv.addObject("produtos", produtos);
        mv.addObject("costureiras", costureiras);

        return mv;

    }

    @RequestMapping(value = "/Encomendas/{Enccode}", method = RequestMethod.POST)
    public String detailEncomendaStateForm(@PathVariable("Enccode") String Enccode, String info, String form) {

        OrderFlow encomenda = er.findByEncCode(Enccode);
        Log l = new Log();
        Userworker u = ur.findByNim(SecurityContextHolder.getContext().getAuthentication().getName());
        if (form.equals("1")) {
            encomenda.setState(info);

            l.setDescription("Alteração de Estado para: " + info);
            l.setEncCode(Enccode);
            l.setName(u.getName());

        } else {
            encomenda.setCostureira(info);

            l.setDescription("Alteração de Costureira para: " + info);
            l.setEncCode(Enccode);
            l.setName(u.getName());
        }
        lr.save(l);
        er.save(encomenda);

        return "redirect:./" + encomenda.getEncCode();
    }

}
