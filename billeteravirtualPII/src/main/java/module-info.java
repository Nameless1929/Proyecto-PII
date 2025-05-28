module com.example.billeteravirtual {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    // Nuevas dependencias para PDFBox y logging
    requires org.apache.pdfbox;
    requires org.apache.fontbox;
    requires org.slf4j;


    // Abre paquetes para JavaFX
    opens com.example.billeteravirtual to javafx.graphics;
    opens com.example.billeteravirtual.controller to javafx.fxml;
    opens com.example.billeteravirtual.model to javafx.base;
    opens com.example.billeteravirtual.service to org.apache.pdfbox; // Necesario para PDFBox

    // Exporta paquetes
    exports com.example.billeteravirtual;
    exports com.example.billeteravirtual.controller;
    exports com.example.billeteravirtual.model;
    exports com.example.billeteravirtual.service;
}