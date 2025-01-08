module org.intissar.jasper2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;


    opens org.intissar.jasper2 to javafx.fxml;
    exports org.intissar.jasper2;
}
