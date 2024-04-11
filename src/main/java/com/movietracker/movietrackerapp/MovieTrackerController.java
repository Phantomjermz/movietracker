package com.movietracker.movietrackerapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableView;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MovieTrackerController {



    @FXML
    private TableView<Movie> tableView;

    @FXML
    private PieChart pieChart;

    @FXML
    public void initialize() {
        loadDataFromDatabase();
    }

    private void loadDataFromDatabase() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/movie_tracker";
        String user = "root";
        String password = "4341";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
             Statement statement = connection.createStatement()) {

            // Query the database to retrieve movie data
            ResultSet resultSet = statement.executeQuery("SELECT * FROM movies");

            ObservableList<Movie> movieList = FXCollections.observableArrayList();

            // Populate movieList with data from the ResultSet
            while (resultSet.next()) {
                String title = resultSet.getString("Title");
                int year = resultSet.getInt("Year");
                int worldwideGross = resultSet.getInt("Worldwide_Gross");
                int budget = resultSet.getInt("Budget");

                movieList.add(new Movie(title, year, worldwideGross, budget));
            }

            // Populate TableView with movie data
            tableView.setItems(movieList);

            // Populate PieChart with movie data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Movie movie : movieList) {
                pieChartData.add(new PieChart.Data(movie.getTitle(), movie.getWorldwideGross()));
            }
            pieChart.setData(pieChartData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
