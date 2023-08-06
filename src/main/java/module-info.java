module org.dmiit3iy {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires javax.mail.api;

    opens org.dmiit3iy to javafx.fxml;
    exports org.dmiit3iy.repository to com.fasterxml.jackson.databind;
    exports org.dmiit3iy;
    exports org.dmiit3iy.controllers;
    exports org.dmiit3iy.model;
}
