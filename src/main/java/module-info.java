open module group.nine.healthsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires bcrypt;
    requires org.apache.httpcomponents.httpclient;
    requires java.net.http;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.pdfbox;




    exports group.nine.healthsystem.view;

    exports group.nine.healthsystem;  // Exporte o pacote contendo a classe MainApplication
}
