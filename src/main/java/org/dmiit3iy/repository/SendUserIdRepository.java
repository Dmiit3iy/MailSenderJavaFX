package org.dmiit3iy.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.dmiit3iy.util.Constants;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SendUserIdRepository {
    ObjectMapper objectMapper = new ObjectMapper();

    {
        this.objectMapper.registerModule(new JavaTimeModule());
    }


    private ArrayList<Integer> numbersArrayList = new ArrayList<>();

    public void setNumbersArrayList(Integer id) throws IOException {
        this.numbersArrayList.add(id);
        File file = new File(Constants.FILE_NAME);

        String str = objectMapper.writeValueAsString(numbersArrayList);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(str);
        fileWriter.close();
    }


    public SendUserIdRepository() {
        try {
            File file = new File(Constants.FILE_NAME);
            numbersArrayList = objectMapper.readValue(file, new TypeReference<ArrayList<Integer>>() {
            });
        } catch (IOException ignored) {
        }
    }

    public ArrayList<Integer> getNumbersArrayList() {
        return numbersArrayList;
    }
}
