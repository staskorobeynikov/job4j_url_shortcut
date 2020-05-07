package ru.job4j.shortcut.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "urls")
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String address;

    private String code;

    private int count;

    @ManyToOne
    private Site site;

    public URL() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        URL url = (URL) o;
        return id == url.id
                && count == url.count
                && Objects.equals(address, url.address)
                && Objects.equals(code, url.code)
                && Objects.equals(site, url.site);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, code, count, site);
    }
}
