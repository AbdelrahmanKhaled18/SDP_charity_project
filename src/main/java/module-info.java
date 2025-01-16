module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires mysql.connector.java;
    requires jakarta.mail;
    requires java.desktop;
    //requires jakarta.mail;

    opens com.example.demo2 to javafx.fxml;
    opens model to javafx.base;

    exports com.example.demo2;
}