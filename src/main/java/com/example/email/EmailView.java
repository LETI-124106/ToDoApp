package com.example.email;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("email")
public class EmailView extends VerticalLayout {

    // Declara os campos como variáveis da classe
    private EmailField toField = new EmailField("Para");
    private TextField subjectField = new TextField("Assunto");
    private TextArea messageArea = new TextArea("Mensagem");
    private Button sendButton = new Button("Enviar Email");

    public EmailView() {
        toField.setPlaceholder("exemplo@gmail.com");
        subjectField.setPlaceholder("Assunto do email...");
        messageArea.setPlaceholder("Escreve a tua mensagem aqui...");
        messageArea.setHeight("150px");

        sendButton.addClickListener(e -> {
            // Pega os valores DENTRO do listener
            String to = toField.getValue();
            String subject = subjectField.getValue();
            String message = messageArea.getValue();

            // Validação básica
            if (to.isEmpty() || subject.isEmpty() || message.isEmpty()) {
                Notification.show("Preenche todos os campos!");
                return;
            }

            try {
                // ⚠️ CONFIGURA AQUI AS TUAS CREDENCIAIS
                String email = "teu.email@gmail.com";
                String password = "tua-app-password";

                // ✅ CORREÇÃO: Usa EmailService em vez de EmailView
                EmailService emailService = new EmailService(email, password);
                boolean success = emailService.enviarEmail(to, subject, message);

                if (success) {
                    Notification.show("✅ Email enviado com sucesso para: " + to);
                    // Limpar campos após envio
                    subjectField.clear();
                    messageArea.clear();
                } else {
                    Notification.show("❌ Falha ao enviar email. Verifica as credenciais.");
                }

            } catch (Exception ex) {
                Notification.show("Erro: " + ex.getMessage());
            }
        });

        // Adicionar tudo à interface
        add(toField, subjectField, messageArea, sendButton);
    }
}