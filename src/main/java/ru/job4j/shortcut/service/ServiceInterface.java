package ru.job4j.shortcut.service;

import ru.job4j.shortcut.model.JSONResponseConvert;
import ru.job4j.shortcut.model.JSONResponseRegistration;
import ru.job4j.shortcut.model.JSONResponseStatistic;

import java.util.List;

public interface ServiceInterface<T, E> {

    JSONResponseRegistration register(T t);

    JSONResponseConvert addNewURL(E e);

    String getAddress(String code);

    List<JSONResponseStatistic> getStatistic();
}
