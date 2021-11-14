package com.dmtSystem.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Entity
public class OrderFlow implements Serializable {

    private static final String INICIADA = "REGISTADA";
    private static final String CORTE = "Corte";
    private static final String EMPROVA1 = "Em Prova 1";
    private static final String EMPROVA2 = "Em Prova 2";
    private static final String PRONTA = "Pronta";
    private static final String ACABAMENTO = "Acabamento";
    private static final String EM_EXECUCAO_1 = "Em Execução 1";
    private static final String EM_EXECUCAO_2 = "Em Execução 2";

    private static final String EMPTY = "";
    private static final String EMPTY_INPUT = "Todos os campos têm de ser preenchidos :)";
    private static final String ENCCODE_SIZE =
            "Cotação tem de ter exatamente 10 dígitos\n.Cotação tem de começar com" + " o dígito 4";

    private static final long serialVersionUID = 1L;

    @Id
    @NotEmpty(message = EMPTY_INPUT)
    @Pattern(regexp = "^(4)([0-9]{9})", message = ENCCODE_SIZE)
    private String encCode;

    @ManyToMany
    @OrderBy("description ASC")
    private List<Product> prods = new LinkedList<Product>();

    @ManyToOne
    private Month month;

    @ManyToOne
    private Client client;

    @NotNull(message = EMPTY_INPUT)
    private LocalDateTime dateLimite;
    @NotNull(message = EMPTY_INPUT)
    private LocalDateTime dateStart;
    @NotNull(message = EMPTY_INPUT)
    private String state;
    @NotEmpty(message = EMPTY_INPUT)
    private String description;
    @NotNull(message = EMPTY_INPUT)
    private String costureira;

    private int counter;

    public OrderFlow() {

        setDateStart();
        this.state = INICIADA;
        this.costureira = EMPTY;
        this.counter = 0;

    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public String getCostureira() {
        return costureira;
    }

    public void setCostureira(String costureira) {
        this.costureira = costureira;
    }

    public List<Product> getProd() {
        return prods;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart() {

        LocalDateTime date = LocalDateTime.now();

        this.dateStart = date;
    }

    public LocalDateTime getDateLimite() {
        return dateLimite;
    }

    public int getYear() {
        return dateLimite.getYear();
    }

    public void setDateLimite(String dateLimite) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(dateLimite, formatter);
        LocalDateTime ldt = LocalDateTime.of(dateTime, LocalDateTime.now().toLocalTime());

        this.dateLimite = ldt;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEncCode() {
        return encCode;
    }

    public void setEncCode(String encCode) {
        this.encCode = encCode;
    }

    public String getColor() {
        String color = "";
        switch (state) {
            case INICIADA:
                color = "white";
                break;
            case CORTE:
                color = "#81DAF5";
                break;
            case EM_EXECUCAO_1:
                color = "#0059b3";
                break;
            case EM_EXECUCAO_2:
                color = "#A0D0A6";
                break;
            case EMPROVA1:
                color = "#F7FE2E";
                break;
            case EMPROVA2:
                color = "#FFBF00";
                break;
            case ACABAMENTO:
                color = "#996633";
                break;
            case PRONTA:
                color = "#40FF00";
                break;

        }

        return color;
    }

    public int getCounter() {
        return counter;
    }

    public void addCounter() {
        this.counter++;
    }

    public void remCounter() {

        this.counter--;
        if (counter < 0)
            counter = 0;
    }

}
