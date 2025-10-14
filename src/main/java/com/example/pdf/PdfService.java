package com.example.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serviço responsável pela geração de ficheiros PDF.
 * Adaptado para Apache PDFBox 3.x (ex.: 3.0.5)
 */
public class PdfService {

    /**
     * Gera um PDF simples com detalhes de um recibo ou agendamento.
     *
     * @param nomeCliente Nome do cliente
     * @param descricao   Descrição do serviço ou agendamento
     * @param valor       Valor associado
     * @return PDF em formato de bytes (para descarregamento)
     * @throws IOException Caso ocorra erro ao criar o PDF
     */
    public byte[] gerarPdf(String nomeCliente, String descricao, double valor) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Cria instâncias de PDType1Font usando o novo Enum Standard14Fonts.FontName
            PDType1Font headerFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDType1Font regularFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            PDType1Font footFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE);

            // Usar try-with-resources para garantir que o content stream é fechado
            try (PDPageContentStream content = new PDPageContentStream(
                    document,
                    page,
                    PDPageContentStream.AppendMode.OVERWRITE,
                    true,
                    true)) {

                // Cabeçalho
                content.beginText();
                content.setFont(headerFont, 22);
                content.newLineAtOffset(70, 750);
                content.showText("Recibo / Detalhe de Agendamento");
                content.endText();

                // Informações (posicionadas explicitamente)
                float startX = 70f;
                float startY = 700f;
                float lineGap = 20f;

                content.beginText();
                content.setFont(regularFont, 14);
                content.newLineAtOffset(startX, startY);
                content.showText("Nome do cliente: " + safeString(nomeCliente));
                content.endText();

                content.beginText();
                content.setFont(regularFont, 14);
                content.newLineAtOffset(startX, startY - lineGap);
                content.showText("Descrição: " + safeString(descricao));
                content.endText();

                content.beginText();
                content.setFont(regularFont, 14);
                content.newLineAtOffset(startX, startY - 2 * lineGap);
                content.showText(String.format("Valor: %.2f €", valor));
                content.endText();

                content.beginText();
                content.setFont(regularFont, 14);
                content.newLineAtOffset(startX, startY - 3 * lineGap);
                content.showText("Data: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                content.endText();

                // Rodapé
                content.beginText();
                content.setFont(footFont, 10);
                content.newLineAtOffset(70, 100);
                content.showText("Gerado automaticamente pela aplicação ToDoApp");
                content.endText();
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }

    private String safeString(String s) {
        return s == null ? "" : s;
    }
}
