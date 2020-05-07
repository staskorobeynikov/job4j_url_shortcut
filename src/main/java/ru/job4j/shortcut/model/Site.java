package ru.job4j.shortcut.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sites")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String login;

    private String password;

    @OneToMany(mappedBy = "site")
    private List<URL> urlList;

    public Site() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<URL> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<URL> urlList) {
        this.urlList = urlList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Site site = (Site) o;
        return id == site.id
                && Objects.equals(name, site.name)
                && Objects.equals(login, site.login)
                && Objects.equals(password, site.password)
                && Objects.equals(urlList, site.urlList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, password, urlList);
    }
}
