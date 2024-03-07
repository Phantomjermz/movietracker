module com.example.movietracker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.movietracker to javafx.fxml;
    exports com.example.movietracker;
}