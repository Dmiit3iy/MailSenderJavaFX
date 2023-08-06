package org.dmiit3iy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.dmiit3iy.model.User;
import org.dmiit3iy.repository.UserRepository;

import java.io.*;
import java.util.ArrayList;

public class MainController {
    private File selectedFile;
    private UserRepository userRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    {
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    private ArrayList<User> users = new ArrayList<>();
    @FXML
    public MenuBar menuBar;
    @FXML
    public TableView<User> tableUsers;

    @FXML
    public void openFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Окно выбора файла JSON");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.json", "*.txt"));
        selectedFile = fileChooser.showOpenDialog(null);
        loadTable();
    }

    public void loadTable() {
        if (selectedFile == null) {
            return;
        }
        try {
            userRepository = new UserRepository(selectedFile);
            users = userRepository.getUserArrayList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (users != null) {

            ObservableList<User> data = FXCollections.observableArrayList(users);
            TableColumn<User, String> idCol = new TableColumn<User, String>("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<User, String>("id"));
            TableColumn<User, String> nameCol = new TableColumn<User, String>("Name");
            nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
            TableColumn<User, String> regDateCol = new TableColumn<User, String>("Registration");
            regDateCol.setCellValueFactory(new PropertyValueFactory<User, String>("regDate"));
            TableColumn<User, String> emailCol = new TableColumn<User, String>("E-mail");
            emailCol.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
            TableColumn<User, String> ageCol = new TableColumn<User, String>("Age");
            ageCol.setCellValueFactory(new PropertyValueFactory<User, String>("age"));
            TableColumn<User, String> countryCol = new TableColumn<User, String>("Country");
            countryCol.setCellValueFactory(new PropertyValueFactory<User, String>("country"));
            TableColumn<User, Boolean> isSendCol = new TableColumn<User, Boolean>("Is send?");
            isSendCol.setCellValueFactory(new PropertyValueFactory<User, Boolean>("isSend"));
            TableColumn<User, String> actionCol = new TableColumn<>("Send mail");
            tableUsers.setItems(data);
            tableUsers.getColumns().setAll(isSendCol, idCol, nameCol, regDateCol, emailCol, ageCol, countryCol, actionCol);

            Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> cellFactory =
                    new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
                        @Override
                        public TableCell call(final TableColumn<User, Boolean> param) {
                            final TableCell<User, Boolean> cell = new TableCell<User, Boolean>() {
                                CheckBox btn = new CheckBox("");

                                {
                                    btn.setDisable(true);
                                    btn.setStyle("-fx-opacity: 1;");
                                }

                                @Override
                                public void updateItem(Boolean item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty) {
                                        setGraphic(null);
                                        setText(null);
                                    } else {
                                        btn.setSelected(tableUsers.getItems().get(getIndex()).isSend());
                                        setGraphic(btn);
                                        setText(null);
                                    }
                                }
                            };
                            return cell;
                        }
                    };
            isSendCol.setCellFactory(cellFactory);
            isSendCol.setEditable(true);


            Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory2 =
                    new Callback<TableColumn<User, String>, TableCell<User, String>>() {
                        @Override
                        public TableCell call(final TableColumn<User, String> param) {
                            final TableCell<User, String> cell = new TableCell<User, String>() {
                                final Button btn = new Button("send");

                                {
                                    btn.getStyleClass().add("success");
                                }

                                @Override
                                public void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty) {
                                        setGraphic(null);
                                        setText(null);
                                    } else {
                                        btn.setOnAction(event -> {
                                            User user = getTableView().getItems().get(getIndex());
                                            try {
                                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/dmiit3iy/letter.fxml"));
                                                Stage stage = new Stage(StageStyle.DECORATED);
                                                stage.setScene(new Scene(loader.load()));
                                                stage.setOnCloseRequest(e -> loadTable());
                                                stage.show();
                                                LetterController letterController = loader.getController();
                                                letterController.initData(user);

                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        });
                                        setGraphic(btn);
                                        setText(null);
                                    }
                                }
                            };
                            return cell;
                        }
                    };
            actionCol.setCellFactory(cellFactory2);
        }
    }
}
