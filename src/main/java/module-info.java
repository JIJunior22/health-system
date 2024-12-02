module group.nine.healthsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires static lombok;

    // Abrir pacotes para frameworks que utilizam reflexão
    opens group.nine.healthsystem to javafx.fxml;
    opens group.nine.healthsystem.domain to org.hibernate.orm.core, net.bytebuddy;

    // Exportar pacotes necessários
    exports group.nine.healthsystem;
    exports group.nine.healthsystem.domain;


    requires org.hibernate.orm.core;
    requires java.sql;
    requires bcrypt;
    requires org.apache.httpcomponents.httpclient;
    requires java.net.http;
    requires org.apache.httpcomponents.httpcore;


    exports group.nine.healthsystem.dao;

    // Permite que o pacote seja acessado pelo módulo javafx.fxml
    opens group.nine.healthsystem.view to javafx.fxml;
}




