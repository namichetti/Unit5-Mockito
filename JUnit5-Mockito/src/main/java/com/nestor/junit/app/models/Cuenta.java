package com.nestor.junit.app.models;

import exception.DineroInsuficienteException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Cuenta {

    private String persona;
    private BigDecimal saldo;
    private Banco banco;

    public void verificarSaldo(BigDecimal saldo){
        int valor = getSaldo().compareTo(saldo);
        if(valor==-1){
            throw new DineroInsuficienteException("No alcanza la guita");
        }
    }
}
