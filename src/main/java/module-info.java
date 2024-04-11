module com.movietracker.movietrackerapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.movietracker.movietrackerapp to javafx.fxml;
    exports com.movietracker.movietrackerapp;
}