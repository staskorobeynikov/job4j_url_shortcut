package ru.job4j.shortcut.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.shortcut.model.Site;
import ru.job4j.shortcut.repository.SiteRepository;

import java.util.ArrayList;

@Service
public class SiteDetailsService implements UserDetailsService {

    private final SiteRepository repository;

    @Autowired
    public SiteDetailsService(SiteRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Site site = repository.findByLogin(s);
        if (site == null) {
            throw new UsernameNotFoundException(String.format("Site with login: %s not found", s));
        }
        return toSite(site);
    }

    private UserDetails toSite(Site site) {
        return User
                .withUsername(site.getLogin())
                .password(site.getPassword())
                .authorities(new ArrayList<>())
                .build();
    }
}
