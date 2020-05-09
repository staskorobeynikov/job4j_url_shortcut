package ru.job4j.shortcut.model;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SiteTest {

    @Test
    public void whenTestMethodModelURLAndSite() {
        Set<URL> urls = new HashSet<>();
        URL url = new URL();
        url.setId(1);
        url.setCount(10);
        url.setAddress("https://job4j.ru/TrackStudio/task/8993?thisframe=true");
        url.setCode("yc4iKxVjZWetek2FJ");
        urls.add(url);

        URL url1 = new URL();
        url1.setId(1);
        url1.setCount(10);
        url1.setAddress("https://job4j.ru/TrackStudio/task/8993?thisframe=true");
        url1.setCode("yc4iKxVjZWetek2FJ");
        urls.add(url1);

        List<URL> list = new ArrayList<>(urls);

        Map<Site, List<URL>> map = new HashMap<>();
        Site site = new Site();
        site.setId(1);
        site.setName("job4j");
        site.setLogin("customUsername");
        site.setPassword("password");
        site.setUrlList(list);
        map.put(site, list);

        Site site1 = new Site();
        site1.setId(1);
        site1.setName("job4j");
        site1.setLogin("customUsername");
        site1.setPassword("password");
        site1.setUrlList(list);
        map.put(site1, list);

        assertThat(map.size(), is(1));
        assertThat(map.get(site).size(), is(1));
        assertThat(map.keySet().iterator().next().getId(), is(1));
        assertThat(map.keySet().iterator().next().getUrlList().get(0).getCode(), is("yc4iKxVjZWetek2FJ"));
        assertThat(map.keySet().iterator().next().getUrlList().get(0).getId(), is(1));
    }
}