package at.dissys.Gui;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDate;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import org.json.JSONArray;
import org.json.JSONObject;

public class GuiController {
    @FXML
    private Label communityPoolLabel;
    @FXML
    private Label gridPortionLabel;
    @FXML
    private Button refreshButton;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Button showDataButton;
    @FXML
    private Label communityProduced;
    @FXML
    private Label communityUsed;
    @FXML
    private Label gridUsed;
    
    private final HttpClient client = HttpClient.newHttpClient();

    public GuiController() {
    }

    @FXML
    public void handleRefresh() {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/energy/current"))
            .build();
        
        client.sendAsync(request, BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(response -> {
                try {
                    JSONObject json = new JSONObject(response);
                    Platform.runLater(() -> {
                        communityPoolLabel.setText("Community Pool: " + json.getString("communityPoolUsed"));
                        gridPortionLabel.setText("Grid Portion: " + json.getString("gridPortion"));
                    });
                } catch (Exception e) {
                    System.err.println("Error parsing current energy data: " + e.getMessage());
                    Platform.runLater(() -> {
                        communityPoolLabel.setText("Community Pool: Error loading data");
                        gridPortionLabel.setText("Grid Portion: Error loading data");
                    });
                }
            });
    }
    
    @FXML
    public void handleShowData() {
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();
        
        if (start != null && end != null) {
            String url = "http://localhost:8080/energy/historical?start=" + start + "T00:00&end=" + end + "T23:59";
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
                
            client.sendAsync(request, BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    try {
                        JSONArray data = new JSONArray(response);
                        if (!data.isEmpty()) {
                            JSONObject item = data.getJSONObject(0);
                            Platform.runLater(() -> {
                                communityProduced.setText("Community produced: " + item.getDouble("communityProduced") + " kWh");
                                communityUsed.setText("Community used: " + item.getDouble("communityUsed") + " kWh");
                                gridUsed.setText("Grid used: " + item.getDouble("gridUsed") + " kWh");
                            });
                        } else {
                            Platform.runLater(() -> {
                                communityProduced.setText("Community produced: No data available");
                                communityUsed.setText("Community used: No data available");
                                gridUsed.setText("Grid used: No data available");
                            });
                        }
                    } catch (Exception e) {
                        System.err.println("Error parsing historical energy data: " + e.getMessage());
                        Platform.runLater(() -> {
                            communityProduced.setText("Community produced: Error loading data");
                            communityUsed.setText("Community used: Error loading data");
                            gridUsed.setText("Grid used: Error loading data");
                        });
                    }
                });
        } else {
            Platform.runLater(() -> {
                communityProduced.setText("Community produced: Please select dates");
                communityUsed.setText("Community used: Please select dates");
                gridUsed.setText("Grid used: Please select dates");
            });
        }
    }
}
