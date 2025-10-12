package com.example.examplefeature;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import org.javamoney.moneta.Money;

public class CurrencyTest {
    public static void main(String[] args) {
        // Valor original em euros
        MonetaryAmount valor = Money.of(100, "EUR");

        // Converter para dólares
        CurrencyConversion conversao = MonetaryConversions.getConversion("USD");
        MonetaryAmount convertido = valor.with(conversao);

        // Mostrar resultado
        System.out.println("Valor original: " + valor);
        System.out.println("Convertido: " + convertido);
    }
}
