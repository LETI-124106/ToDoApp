package com.example.qrcode;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.google.zxing.WriterException;
import java.io.IOException;

@Route("qrcode")
public class QRCodeView extends VerticalLayout {

    public QRCodeView() {
        try {
            String qrCodeBase64 = QRCodeGenerator.generateBase64QRCode("https://localhost:8080/user/123");
            Image qrImage = new Image(qrCodeBase64, "QR Code");
            qrImage.setWidth("200px");
            qrImage.setHeight("200px");
            add(qrImage);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
}
