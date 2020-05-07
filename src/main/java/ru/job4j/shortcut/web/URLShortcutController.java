package ru.job4j.shortcut.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.shortcut.model.*;
import ru.job4j.shortcut.service.ServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/shortcuts")
public class URLShortcutController {

    private final ServiceInterface<Site, URL> service;

    @Autowired
    public URLShortcutController(ServiceInterface<Site, URL> service) {
        this.service = service;
    }

    @PostMapping("/registration")
    public ResponseEntity<JSONResponseRegistration> registration(@RequestBody JSONRequest request) {
        Site site = new Site();
        site.setName(request.getSite());
        JSONResponseRegistration result = service.register(site);

        return new ResponseEntity<>(
                result,
                result.isRegistration() ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        );
    }

    @PostMapping("/convert")
    public ResponseEntity<JSONResponseConvert> convert(@RequestBody URL url) {
        JSONResponseConvert result = service.addNewURL(url);

        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }

    @GetMapping("/redirect/{code}")
    public ResponseEntity<String> redirect(@PathVariable String code) {
        String address = service.getAddress(code);
        HttpHeaders headers = new HttpHeaders();
        headers.add("HTTP", address);

        return new ResponseEntity<>(
                headers,
                HttpStatus.FOUND
        );
    }

    @GetMapping("/statistic")
    public ResponseEntity<List<JSONResponseStatistic>> getStatistic() {
        List<JSONResponseStatistic> result = service.getStatistic();

        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }
}
