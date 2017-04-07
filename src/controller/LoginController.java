package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.DAO;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 031 31.03.17.
 */
public class LoginController implements Initializable {
    DAO dao = new DAO();

    @FXML
    public Button LoginButton;
    @FXML
    public TextField LoginEdit;
    @FXML
    public PasswordField PasswordEdit;
    @FXML
    public Label errorLabel;

    @FXML
    private void onLoginButtonClick(ActionEvent event) throws Exception {
       if (dao.setup(LoginEdit.getText(), PasswordEdit.getText())){
            ((Node) (event.getSource())).getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Main");
            stage.setResizable(false);
            stage.show();
        } else {
            errorLabel.setText("Неверный логин или пароль");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
