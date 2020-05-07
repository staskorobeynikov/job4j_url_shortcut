package ru.job4j.shortcut.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.*;
import ru.job4j.shortcut.repository.SiteRepository;
import ru.job4j.shortcut.repository.URLRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShortcutsService implements ServiceInterface<Site, URL> {

    private final SiteRepository siteStore;

    private final URLRepository urlStore;

    private final BCryptPasswordEncoder encoder;

    private static final String DEFAULT_ADDRESS = "https://mail.ru/";

    private static final Logger LOG = LoggerFactory.getLogger(ShortcutsService.class);

    @Autowired
    public ShortcutsService(SiteRepository siteStore, URLRepository urlStore, BCryptPasswordEncoder encoder) {
        this.siteStore = siteStore;
        this.urlStore = urlStore;
        this.encoder = encoder;
    }

    @Override
    public JSONResponseRegistration register(Site site) {
        Site check = siteStore.findByName(site.getName());
        if (check != null) {
            LOG.info("Site with this name: {} is already in the system.", site.getName());
            return new JSONResponseRegistration(false, "", "");
        }
        String login = RandomStringUtils.randomAlphanumeric(12);
        String password = RandomStringUtils.randomAlphanumeric(12);
        String encodePassword = encoder.encode(password);

        site.setLogin(login);
        site.setPassword(encodePassword);
        siteStore.save(site);

        LOG.info("In system add new site with name: {}.", site.getName());

        return new JSONResponseRegistration(true, login, password);
    }


    @Override
    @Transactional
    public JSONResponseConvert addNewURL(URL url) {
        String address = url.getAddress();

        String code = RandomStringUtils.randomAlphanumeric(17);
        LOG.info("For address: {} generated string: {}", address, code);

        URL addUrl = new URL();
        addUrl.setAddress(address);
        addUrl.setCode(code);
        addUrl.setCount(0);
        addUrl.setSite(this.getSite());

        urlStore.save(addUrl);

        LOG.info("For site: {} add new URL with address: {} and code: {}", this.getSite().getName(), address, code);

        return new JSONResponseConvert(code);
    }

    @Override
    @Transactional
    public String getAddress(String code) {
        String result = DEFAULT_ADDRESS;
        URL url = urlStore.findByCode(code);
        if (url != null) {
            result = url.getAddress();
            int updateCount = url.getCount() + 1;
            url.setCount(updateCount);
            urlStore.save(url);
        }
        LOG.info("You are redirect by link: {}", result);

        return result;
    }

    @Override
    @Transactional
    public List<JSONResponseStatistic> getStatistic() {
        List<JSONResponseStatistic> result = new ArrayList<>();
        List<URL> urls = (List<URL>) urlStore.findAll();
        for (URL url : urls) {
            result.add(new JSONResponseStatistic(url.getAddress(), url.getCount()));
        }
        return result;
    }

    private Site getSite() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String login = auth.getName();

        return siteStore.findByLogin(login);
    }
}
