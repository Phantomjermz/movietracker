package com.movietracker.movietrackerapp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import java.io.InputStream;
import java.sql.*;

public class MovieTrackerApplication extends Application {

    private TableView<Movie> tableView;
    private PieChart pieChart;
    private Scene pieChartScene;
    private Scene tableViewScene;

    @Override
    public void start(Stage primaryStage) {
        // Initialize TableView and PieChart
        tableView = new TableView<>();
        pieChart = new PieChart();

        // Create BorderPane for layout
        BorderPane root = new BorderPane();
        root.setLeft(tableView);
        root.setCenter(pieChart);

        // Create scenes
        pieChartScene = new Scene(root, 800, 600);
        tableViewScene = createTableViewScene(primaryStage);

        // Create switch button
        Button switchButton = new Button("Switch to Table View");
        switchButton.setOnAction(event -> {
            if (primaryStage.getScene() == pieChartScene) {
                primaryStage.setScene(tableViewScene);
            } else {
                primaryStage.setScene(pieChartScene);
            }
        });

        // Load the icon image
        Image iconImage = null;
        InputStream iconStream = getClass().getResourceAsStream("icon.png");
        if (iconStream != null) {
            iconImage = new Image(iconStream);
            primaryStage.getIcons().add(iconImage);
        } else {
            System.err.println("Icon image not found.");
        }

        // Create an ImageView for the icon
        ImageView iconView = new ImageView(iconImage);
        iconView.setFitWidth(32); // Set the width of the icon
        iconView.setFitHeight(32); // Set the height of the icon

        // Create an HBox for the top layout
        HBox topBox = new HBox(switchButton, iconView);
        topBox.setAlignment(Pos.CENTER_RIGHT); // Align to center right
        topBox.setSpacing(10); // Add some spacing between the button and icon

        // Add the top layout to the root
        root.setTop(topBox);

        // Set primary stage
        primaryStage.setScene(pieChartScene);
        primaryStage.setTitle("Movie Database Viewer");
        primaryStage.show();

        // Load data from MySQL database
        loadDataFromDatabase();

        // Load CSS file
        String cssFile = getClass().getResource("styles.css").toExternalForm();

        // CSS to pieChart
        pieChartScene.getStylesheets().add(cssFile);

        // CSS to tableViewScene
        tableViewScene.getStylesheets().add(cssFile);
    }

    private Scene createTableViewScene(Stage primaryStage) {
        // Create TableView scene
        BorderPane tableViewRoot = new BorderPane(tableView);
        Button switchBackButton = new Button("Switch to Pie Chart");
        switchBackButton.setOnAction(event -> {
            primaryStage.setScene(pieChartScene);
        });
        HBox topBox = new HBox(switchBackButton);
        topBox.setAlignment(Pos.CENTER_RIGHT);
        topBox.setSpacing(10);
        tableViewRoot.setTop(topBox);
        return new Scene(tableViewRoot, 800, 600);
    }

    private void loadDataFromDatabase() {
        String jdbcUrl = "jdbc:mysql://localhost:3306/mysql";
        String user = "root";
        String password = "4341";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM movies");

            ObservableList<Movie> movieList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                String title = resultSet.getString("Title");
                int year = resultSet.getInt("Year");
                double worldwideGross = resultSet.getDouble("Worldwide_Gross");
                double budget = resultSet.getDouble("Budget");

                // Populating TableView
                movieList.add(new Movie(title, year, worldwideGross, budget));
            }

            // Adding columns to TableView
            TableColumn<Movie, String> titleColumn = new TableColumn<>("Title");
            titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

            TableColumn<Movie, Integer> yearColumn = new TableColumn<>("Year");
            yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty().asObject());

            TableColumn<Movie, Double> worldwideGrossColumn = new TableColumn<>("Worldwide Gross");
            worldwideGrossColumn.setCellValueFactory(cellData -> cellData.getValue().worldwideGrossProperty().asObject());

            TableColumn<Movie, Double> budgetColumn = new TableColumn<>("Budget");
            budgetColumn.setCellValueFactory(cellData -> cellData.getValue().budgetProperty().asObject());

            tableView.getColumns().addAll(titleColumn, yearColumn, worldwideGrossColumn, budgetColumn);
            tableView.setItems(movieList);

            // Output the size of the movieList for debugging
            System.out.println("Number of movies retrieved: " + movieList.size());

            // Populate PieChart
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
            for (Movie movie : movieList) {
                pieChartData.add(new PieChart.Data(movie.getTitle(), movie.getWorldwideGross()));
            }
            pieChart.setData(pieChartData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
