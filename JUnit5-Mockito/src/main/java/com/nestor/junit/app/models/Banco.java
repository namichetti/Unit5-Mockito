package com.nestor.junit.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Banco {

    private String name;
    private List<Cuenta> cuentas = new ArrayList<>();

    public void addCuenta(Cuenta cuenta){
        this.cuentas.add(cuenta);
    }
}
