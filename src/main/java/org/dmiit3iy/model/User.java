package org.dmiit3iy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="dd.MM.yyyy HH:mm:ss" )
    private LocalDateTime regDate;
    private String email;
    private int age;
    private String country;
    @JsonIgnore
    private boolean isSend;

}
