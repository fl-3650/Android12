package com.example.android12;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "data.json";
    private TextView userTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userTextView = findViewById(R.id.userTextView);

        List<User> users = new ArrayList<>();
        users.add(new User(1, "John"));
        users.add(new User(2, "Jane"));

        saveDataToFile(this, users);

        List<User> loadedUsers = loadDataFromFile(this);

        displayUsers(loadedUsers);
    }

    private void saveDataToFile(Context context, List<User> users) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(users);

        try (FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
             FileWriter writer = new FileWriter(fos.getFD())) {
            writer.write(jsonString);
        } catch (IOException e) {
            e.getCause();
        }
    }

    private List<User> loadDataFromFile(Context context) {
        Gson gson = new Gson();
        List<User> users = new ArrayList<>();

        try (FileReader reader = new FileReader(context.getFileStreamPath(FILE_NAME))) {
            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();
            users = gson.fromJson(reader, userListType);
        } catch (IOException e) {
            e.getCause();
        }

        return users;
    }

    private void displayUsers(List<User> users) {
        StringBuilder userDisplayText = new StringBuilder();
        for (User user : users) {
            userDisplayText.append("ID: ").append(user.id).append(", Name: ").append(user.name).append("\n");
        }
        userTextView.setText(userDisplayText.toString());
    }

    static class User {
        int id;
        String name;

        User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
