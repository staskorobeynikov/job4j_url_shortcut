package ru.job4j.shortcut.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.job4j.shortcut.model.*;
import ru.job4j.shortcut.repository.SiteRepository;
import ru.job4j.shortcut.repository.URLRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortcutsServiceTest {

    @MockBean
    private SiteRepository siteStore;

    @MockBean
    private URLRepository urlStore;

    @Autowired
    private ShortcutsService service;

    @Test
    public void whenSiteAlreadyRegisterThanReturnRegistrationFalse() {
        String name = "job4j";
        Site site = new Site();
        site.setName(name);
        given(this.siteStore.findByName(any(String.class))).willReturn(site);

        JSONResponseRegistration result = this.service.register(site);

        assertThat(result.isRegistration(), is(false));
    }

    @Test
    public void whenSiteNotRegisteredYetThanReturnRegistrationTrue() {
        String name = "job4j";
        Site site = new Site();
        site.setName(name);
        given(this.siteStore.findByName(any(String.class))).willReturn(null);

        JSONResponseRegistration result = this.service.register(site);

        assertThat(result.isRegistration(), is(true));
    }

    @Test
    @WithMockCustomerUser(username = "job4j")
    public void whenAddNewURLInSystemThanResponseNotNull() {
        String name = "job4j";
        Site site = new Site();
        site.setName(name);
        site.setLogin("customUsername");
        site.setPassword("password");

        URL url = new URL();
        url.setAddress("https://job4j.ru/TrackStudio/task/8993?thisframe=true");

        given(siteStore.findByLogin(any(String.class))).willReturn(site);
        given(this.urlStore.save(any(URL.class))).willReturn(url);

        JSONResponseConvert result = this.service.addNewURL(url);

        assertNotNull(result);
    }

    @Test
    public void whenRequestWithCodeThatNotInSystemThenReturnDefaultAddress() {
        String code = "yc4iKxVjZWetek2FJ";

        given(this.urlStore.findByCode(any(String.class))).willReturn(null);

        String result = this.service.getAddress(code);

        assertThat(result, is("https://mail.ru/"));
    }

    @Test
    public void whenRequestWithCodeThatHasInSystemThenReturnURL() {
        String code = "yc4iKxVjZWetek2FJ";
        URL url = new URL();
        url.setAddress("https://job4j.ru/TrackStudio/task/8993?thisframe=true");

        given(this.urlStore.findByCode(any(String.class))).willReturn(url);

        String result = this.service.getAddress(code);

        assertThat(result, is("https://job4j.ru/TrackStudio/task/8993?thisframe=true"));
    }

    @Test
    @WithMockCustomerUser(username = "job4j")
    public void whenGetStatisticThanReturnListJSONResponse() {
        URL url = new URL();
        url.setAddress("https://job4j.ru/TrackStudio/task/8993?thisframe=true");
        url.setCount(10);
        List<URL> list = new ArrayList<>();
        list.add(url);

        Site site = new Site();
        site.setName("job4j");
        site.setLogin("job4j");
        site.setPassword("password");

        given(this.siteStore.findByLogin(any(String.class))).willReturn(site);
        given(this.urlStore.findBySite_Name(any(String.class))).willReturn(list);

        List<JSONResponseStatistic> result = this.service.getStatistic();

        assertThat(result.get(0).getTotal(), is(10));
    }
}