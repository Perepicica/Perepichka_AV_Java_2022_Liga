package ru.internship.perepichka.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.internship.perepichka.dao.EmployeeRepository;
import ru.internship.perepichka.exception.DataLoadingException;
import ru.internship.perepichka.util.DataParser;

import java.io.BufferedReader;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class EmployeeLoader implements DataLoader{
    private final EmployeeRepository employeeRepository;

    @Override
    public void saveData(BufferedReader br) throws IOException {
        if (employeeRepository.count() != 0) return;
        String line;
        while ((line = br.readLine()) != null) {
            employeeRepository.save(DataParser.parseEmployeeLine(new DataLoadingException(""), line));
        }
    }
}
