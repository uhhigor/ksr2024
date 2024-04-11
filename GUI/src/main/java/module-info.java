module org.example.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires project1;


    opens org.example.gui to javafx.fxml;
    exports org.example.gui;
}