module at.dissys.Gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.json;


    opens at.dissys.Gui to javafx.fxml;
    exports at.dissys.Gui;
}