module org.example._socialnetwork_ {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens org.example._socialnetwork_ to javafx.fxml;
    exports org.example._socialnetwork_;
}