package com.example.pdf;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.server.VaadinSession;

import java.io.IOException;
import java.util.Base64;

/**
 * Página Vaadin que gera e descarrega PDF sem StreamResource nem controllers externos.
 * Usa JavaScript para descarregar o ficheiro diretamente no browser.
 */
@PageTitle("Gerar PDF")
@Route("pdf")
public class PdfView extends VerticalLayout {

    private final PdfService pdfService = new PdfService();

    public PdfView() {
        setPadding(true);
        setSpacing(true);
        setAlignItems(Alignment.CENTER);

        add(new H2("Geração de PDF – Recibo ou Agendamento"));

        // Campos do formulário
        TextField nomeField = new TextField("Nome do cliente");
        TextField descricaoField = new TextField("Descrição do serviço");
        NumberField valorField = new NumberField("Valor (€)");
        valorField.setMin(0);

        FormLayout form = new FormLayout(nomeField, descricaoField, valorField);
        form.setMaxWidth("500px");

        Button gerarBtn = new Button("Gerar e descarregar PDF", event -> {
            try {
                String nome = nomeField.getValue();
                String descricao = descricaoField.getValue();
                double valor = valorField.getValue() != null ? valorField.getValue() : 0.0;

                // Gera o PDF
                byte[] pdfBytes = pdfService.gerarPdf(nome, descricao, valor);

                // Codifica em Base64 para envio ao browser
                String base64 = Base64.getEncoder().encodeToString(pdfBytes);

                // Cria o comando JavaScript que cria e clica num link temporário
                String js = """
                    const base64 = '%s';
                    const byteCharacters = atob(base64);
                    const byteNumbers = Array.from(byteCharacters, c => c.charCodeAt(0));
                    const byteArray = new Uint8Array(byteNumbers);
                    const blob = new Blob([byteArray], {type: 'application/pdf'});
                    const url = URL.createObjectURL(blob);
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = 'recibo.pdf';
                    a.click();
                    URL.revokeObjectURL(url);
                    """.formatted(base64);

                // Executa no browser do utilizador
                getUI().ifPresent(ui -> ui.getPage().executeJs(js));

                Notification.show("PDF gerado e descarregado com sucesso!", 3000, Notification.Position.TOP_CENTER);

            } catch (IOException e) {
                Notification.show("Erro ao gerar PDF: " + e.getMessage(),
                        5000, Notification.Position.TOP_CENTER);
                e.printStackTrace();
            }
        });

        add(form, gerarBtn);
    }
}
