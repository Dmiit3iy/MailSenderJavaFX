package org.dmiit3iy.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.dmiit3iy.App;
import org.dmiit3iy.model.User;
import org.dmiit3iy.repository.SendUserIdRepository;
import org.dmiit3iy.util.Constants;
import org.dmiit3iy.util.MailSender;

import java.io.IOException;

public class LetterController {
   private SendUserIdRepository sendUserIdRepository = new SendUserIdRepository();

    private User user;
    @FXML
    public TextField titleTextField;
    @FXML
    public TextArea letterTextArea;
    @FXML
    public void sendButton(ActionEvent actionEvent) {
        if ((!titleTextField.getText().isBlank())&&(!letterTextArea.getText().isBlank())){
            MailSender mailSender = new MailSender(Constants.EMAIL,Constants.EMAIL_PASSWORD,user.getEmail());//Укажите ваш логин и пароль
            try {
                mailSender.send(titleTextField.getText(), letterTextArea.getText());
                App.showMessage("Успех", "Письмо успешно отправлено", Alert.AlertType.INFORMATION);
                sendUserIdRepository.setNumbersArrayList(user.getId());
            }catch (IllegalArgumentException e){
                App.showMessage("Ошибка", "Ошибка отправки сообщения", Alert.AlertType.ERROR);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                titleTextField.clear();
                letterTextArea.clear();
            }

        }else{
            App.showMessage("Ошибка", "Заполните все поля пред отправкой", Alert.AlertType.INFORMATION);
        }
    }

    public void initData(User user) {
        this.user = user;
    }
}
