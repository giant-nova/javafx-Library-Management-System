module org.vervebridge.proj.libmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens org.vervebridge.proj.libmanagementsystem to javafx.fxml;
    exports org.vervebridge.proj.libmanagementsystem;
}