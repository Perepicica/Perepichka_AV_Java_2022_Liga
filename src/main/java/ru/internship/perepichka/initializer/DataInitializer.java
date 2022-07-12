package ru.internship.perepichka.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.internship.perepichka.exception.DataLoadingException;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    @Value("${file.employees}")
    private String employeeDataFile;
    @Value("${file.tasks}")
    private String taskDataFile;

    private final LoaderFactory loaderFactory;

    public static final Function<String, DataLoadingException> exceptionType = DataLoadingException::new;

    @PostConstruct
    public void loadData() throws IOException {
        DataLoader employeeFactory = loaderFactory.getFactory(FileType.EMPLOYEES);
        employeeFactory.load(employeeDataFile);
        DataLoader taskFactory = loaderFactory.getFactory(FileType.TASKS);
        taskFactory.load(taskDataFile);
    }

}
