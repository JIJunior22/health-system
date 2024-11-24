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

    opens group.nine.healthsystem.view to javafx.fxml;
    exports group.nine.healthsystem.view;

    exports group.nine.healthsystem;  // Exporte o pacote contendo a classe MainApplication
    opens group.nine.healthsystem to javafx.fxml;  // Permita o acesso reflexivo do FXMLLoader
}