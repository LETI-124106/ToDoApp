package com.example.examplefeature;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailQuickTest {

    public static void main(String[] args) {
        System.out.println("🚀 INICIANDO TESTE RÁPIDO DA BIBLIOTECA DE EMAIL...\n");

        try {
            // Teste 1: Verificar se as classes básicas existem
            System.out.println("1. Verificando carregamento das classes...");
            Session session = Session.getDefaultInstance(new Properties());
            Message message = new MimeMessage(session);
            System.out.println("   ✅ Classes carregadas com sucesso!");

            // Teste 2: Configurar propriedades básicas
            System.out.println("2. Testando configurações SMTP...");
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            System.out.println("   ✅ Propriedades configuradas!");

            // Teste 3: Criar uma mensagem de teste
            System.out.println("3. Criando mensagem de teste...");
            message.setFrom(new InternetAddress("test@example.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("test@example.com"));
            message.setSubject("Teste da Biblioteca JavaMail");
            message.setText("Se recebeste esta mensagem no console, a biblioteca está a funcionar!");

            System.out.println("   ✅ Mensagem de teste criada!");

            System.out.println("\n🎉 SUCESSO! A biblioteca JavaMail está a funcionar corretamente!");
            System.out.println("📧 Podes prosseguir com a implementação do envio de emails reais.");

        } catch (Exception e) {
            System.err.println("\n❌ FALHA NO TESTE!");
            System.err.println("Erro: " + e.getMessage());
            System.err.println("\n📋 Possíveis soluções:");
            System.err.println("   • Verifica se a dependência está correta no pom.xml");
            System.err.println("   • Executa 'mvn clean compile' no terminal");
            System.err.println("   • Recarrega o projeto Maven no IntelliJ");
            e.printStackTrace();
        }
    }
}
