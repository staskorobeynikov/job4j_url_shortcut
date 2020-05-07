package ru.job4j.shortcut.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.repository.SiteRepository;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SiteDetailsServiceTest {

    @MockBean
    private SiteRepository siteStore;

    @Autowired
    private SiteDetailsService service;

    private Site site = new Site();

    @Before
    public void setUp() {
        site.setId(1);
        site.setName("job4j");
        site.setLogin("jWSYIDmO5bLY");
        site.setPassword("9JzhxHHHx0yW");
    }

    @Test
    public void whenLoadSiteByLoginThanDetailsIsNotNull() {
        given(this.siteStore.findByLogin(any(String.class))).willReturn(site);

        UserDetails details = service.loadUserByUsername("jWSYIDmO5bLY");

        assertNotNull(details);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void whenLoadSiteByLoginThanUsernameNotFoundException() {
        given(this.siteStore.findByLogin(any(String.class))).willReturn(null);

        service.loadUserByUsername("jWSYIDmO5bLY");
    }
}