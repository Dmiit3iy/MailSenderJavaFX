package org.dmiit3iy.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.dmiit3iy.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UserRepository {

    ObjectMapper objectMapper = new ObjectMapper();

    {
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    private SendUserIdRepository sendUserIdRepository = new SendUserIdRepository();
    private ArrayList<Integer> integerArrayList = sendUserIdRepository.getNumbersArrayList();

    public ArrayList<User> getUserArrayList() {
        return userArrayList;
    }

    private ArrayList<User> userArrayList = new ArrayList<>();

    public UserRepository(File file) throws IOException {

        userArrayList = objectMapper.readValue(file, new TypeReference<ArrayList<User>>() {});

        for (User user : userArrayList) {
            if (integerArrayList.contains(user.getId())) {
                user.setSend(true);
            }
        }
    }

    public void refresh(){
        ArrayList<Integer> integerArrayList2 = sendUserIdRepository.getNumbersArrayList();
        for (User user : userArrayList) {
            if (integerArrayList2.contains(user.getId())) {
                user.setSend(true);
            }
        }
    }
}
