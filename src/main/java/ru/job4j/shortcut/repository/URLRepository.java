package ru.job4j.shortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.shortcut.model.URL;

import java.util.List;

public interface URLRepository extends CrudRepository<URL, Integer> {

    URL findByCode(String code);

    List<URL> findBySite_Name(String name);

    @Modifying
    @Query("update URL as u set u.count = (u.count + 1) where u.code = ?1")
    void changeCount(String code);
}
