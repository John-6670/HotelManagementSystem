module com.example.receptionist {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.receptionist to javafx.fxml;
    exports com.example.receptionist;
}