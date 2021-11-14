package com.dmtSystem.controllers;

import org.springframework.data.domain.Sort;

public class Utils {


    public static Sort getSort(String order) {
        Sort sort = null;

        switch (order) {
            case "nome":
                sort = Sort.by("client.name");
                break;
            case "arma":
                sort = Sort.by("client.weapon");
                break;
            case "posto":
                sort = Sort.by("client.ranked");
                break;
            case "cotacao":
                sort = Sort.by("encCode");
                break;
            case "nim":
                sort = Sort.by("client.nim");
                break;
            case "local":
                sort = Sort.by("description");
                break;
            case "dataregisto":
                sort = Sort.by("dateStart");
                break;
            case "datalimite":
                sort = Sort.by("dateLimite");
                break;
            case "estado":
                sort = Sort.by("state");
                break;

        }
        return sort;
    }
}
