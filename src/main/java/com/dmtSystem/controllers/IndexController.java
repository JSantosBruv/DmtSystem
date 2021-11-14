package com.dmtSystem.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dmtSystem.models.OrderFlow;
import com.dmtSystem.models.Log;
import com.dmtSystem.models.Month;
import com.dmtSystem.models.Product;

import com.dmtSystem.repository.OrderRepository;
import com.dmtSystem.repository.LogRepository;
import com.dmtSystem.repository.MonthRepository;
import com.dmtSystem.repository.ProductRepository;

@Controller
public class IndexController {

	private final OrderRepository er;
	private final MonthRepository mr;
	private final LogRepository lr;
	private final ProductRepository pr;
	private boolean consistent;

	public IndexController(OrderRepository er, MonthRepository mr, LogRepository lr, ProductRepository pr) {
		this.er = er;
		this.mr = mr;
		this.lr = lr;
		this.pr = pr;
	}

	@RequestMapping({ "/", "/index" })
	public String index() {

		build();

		return "index";
	}

	@RequestMapping("/noPermission")
	public String permissionError() {

		return "accessDenied";
	}

	@RequestMapping(value = "/CalendarioDeEncomendas")
	public ModelAndView orderCalendar() {

		ModelAndView mv = new ModelAndView("order/encomendasCalendario");

		Iterable<OrderFlow> janeiro = mr.findById(1).getOrders();
		Iterable<OrderFlow> fevereiro = mr.findById(2).getOrders();
		Iterable<OrderFlow> marco = mr.findById(3).getOrders();
		Iterable<OrderFlow> abril = mr.findById(4).getOrders();
		Iterable<OrderFlow> maio = mr.findById(5).getOrders();
		Iterable<OrderFlow> junho = mr.findById(6).getOrders();
		Iterable<OrderFlow> julho = mr.findById(7).getOrders();
		Iterable<OrderFlow> agosto = mr.findById(8).getOrders();
		Iterable<OrderFlow> setembro = mr.findById(9).getOrders();
		Iterable<OrderFlow> outubro = mr.findById(10).getOrders();
		Iterable<OrderFlow> novembro = mr.findById(11).getOrders();
		Iterable<OrderFlow> dezembro = mr.findById(12).getOrders();

		mv.addObject("janeiro", janeiro);
		mv.addObject("fevereiro", fevereiro);
		mv.addObject("marco", marco);
		mv.addObject("abril", abril);
		mv.addObject("maio", maio);
		mv.addObject("junho", junho);
		mv.addObject("julho", julho);
		mv.addObject("agosto", agosto);
		mv.addObject("setembro", setembro);
		mv.addObject("outubro", outubro);
		mv.addObject("novembro", novembro);
		mv.addObject("dezembro", dezembro);
		return mv;

	}

	@RequestMapping(value = "/CalendarioDeEncomendas/{Enccode}", method = RequestMethod.GET)
	public ModelAndView postCalendar(@PathVariable("Enccode") String Enccode) {

		OrderFlow encomenda = er.findByEncCode(Enccode);
		List<Log> logs = lr.findAllByEncCode(Enccode, Sort.by("date"));

		ModelAndView mv = new ModelAndView("order/encomendaEncCode");

		mv.addObject("encomenda", encomenda);

		mv.addObject("logs", logs);

		return mv;

	}

	public void checkProds() throws IOException {

		if (pr.count() == 0) {

			Scanner in = new Scanner(new ClassPathResource("static/products.txt").getInputStream());

			while (in.hasNextLine()) {

				Product ce = new Product();
				String u = in.next();
				String d = in.nextLine();

				ce.setNna(u);
				ce.setDescription(d);

				pr.save(ce);

			}
			in.close();
			System.out.println(pr.count() + " Products created");
		}

	}

	public void checkMonths() {

		if (mr.count() == 0) {

			Month j = new Month();
			j.setId(1);
			j.setMonth("Janeiro");
			mr.save(j);
			Month f = new Month();
			f.setId(2);
			f.setMonth("Fevereiro");
			mr.save(f);
			Month ma = new Month();
			ma.setId(3);
			ma.setMonth("Março");
			mr.save(ma);
			Month ab = new Month();
			ab.setId(4);
			ab.setMonth("Abril");
			mr.save(ab);
			Month m = new Month();
			m.setId(5);
			m.setMonth("Maio");
			mr.save(m);
			Month jn = new Month();
			jn.setId(6);
			jn.setMonth("Junho");
			mr.save(jn);
			Month jl = new Month();
			jl.setId(7);
			jl.setMonth("Julho");
			mr.save(jl);
			Month a = new Month();
			a.setId(8);
			a.setMonth("Agosto");
			mr.save(a);
			Month s = new Month();
			s.setId(9);
			s.setMonth("Setembro");
			mr.save(s);
			Month o = new Month();
			o.setId(10);
			o.setMonth("Outubro");
			mr.save(o);
			Month n = new Month();
			n.setId(11);
			n.setMonth("Novembro");
			mr.save(n);
			Month d = new Month();
			d.setId(12);
			d.setMonth("Dezembro");
			mr.save(d);

			System.out.println(mr.count() + " Months created");
		}
	}

	private void build() {
		if (!consistent) {
			try {
				checkProds();
			} catch (IOException e) {

				e.printStackTrace();
			}
			checkMonths();
			consistent = true;
		}

	}

}
