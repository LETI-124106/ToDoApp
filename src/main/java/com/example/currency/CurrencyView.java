package com.example.currency;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.Route;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;

@Route("currency")
public class CurrencyView extends VerticalLayout {

    public CurrencyView() {
        // Campo para inserir o valor
        NumberField amountField = new NumberField("Valor");
        amountField.setValue(1.0);

        // Dropdown de moeda de origem
        ComboBox<String> fromCurrency = new ComboBox<>("De");
        fromCurrency.setItems("EUR", "USD", "GBP");
        fromCurrency.setValue("EUR");

        // Dropdown de moeda de destino
        ComboBox<String> toCurrency = new ComboBox<>("Para");
        toCurrency.setItems("EUR", "USD", "GBP");
        toCurrency.setValue("USD");

        // Botão para converter
        Button convertButton = new Button("Converter");
        convertButton.addClickListener(e -> {
            double amount = amountField.getValue();
            String from = fromCurrency.getValue();
            String to = toCurrency.getValue();

            try {
                CurrencyUnit fromUnit = Monetary.getCurrency(from);
                MonetaryAmount originalAmount = Money.of(amount, fromUnit);

                CurrencyConversion conversion = MonetaryConversions.getConversion(to);
                MonetaryAmount converted = originalAmount.with(conversion);

                Notification.show(originalAmount + " = " + converted);
            } catch (Exception ex) {
                Notification.show("Erro na conversão: " + ex.getMessage());
            }
        });

        // Adicionar tudo à interface
        add(amountField, fromCurrency, toCurrency, convertButton);
    }
}

