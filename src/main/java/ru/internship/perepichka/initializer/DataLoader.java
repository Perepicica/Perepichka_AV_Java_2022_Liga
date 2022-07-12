package ru.internship.perepichka.initializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public interface DataLoader {
    default void load(String fileName) throws IOException {
        File file = new File(fileName);
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            saveData(bufferedReader);
        }
    }

    void saveData(BufferedReader bufferedReader) throws IOException;
}
