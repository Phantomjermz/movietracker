module com.movietracker.movietrackerapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.movietracker.movietrackerapp to javafx.fxml;
    exports com.movietracker.movietrackerapp;
}