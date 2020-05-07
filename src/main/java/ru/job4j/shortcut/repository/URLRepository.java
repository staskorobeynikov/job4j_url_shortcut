package ru.job4j.shortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.shortcut.model.URL;

public interface URLRepository extends CrudRepository<URL, Integer> {

    URL findByCode(String code);
}
