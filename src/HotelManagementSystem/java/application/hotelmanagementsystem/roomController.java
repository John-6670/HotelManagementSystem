package application.hotelmanagementsystem;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class roomController implements Initializable {

    @FXML
    private ListView <String> roomTypeList;
    @FXML
    private Label roomTypeLabel;

    String[] RoomType = {"Single" , "Double" , "SUIT" , "VIP" };
    String CurrentType;

    @Override
    public void initialize(URL url, ResourceBundle arg1) {
        roomTypeList.getItems().addAll(RoomType);
       /* roomTypeList.getSelectionModel().selectionModeProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {

                CurrentType = roomTypeList.getSelectionModel().getSelectedItem();
                roomTypeLabel.setText(CurrentType);
            }
        });*/
    }
}
