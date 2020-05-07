package ru.job4j.shortcut.web;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.shortcut.model.*;
import ru.job4j.shortcut.service.ShortcutsService;
import ru.job4j.shortcut.service.SiteDetailsService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(URLShortcutController.class)
public class URLShortcutControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShortcutsService service;

    @MockBean
    private SiteDetailsService siteDetailsService;

    @Test
    public void whenRequestInBodyContainsSiteThatNotInSiteStoreThanResponseStatusIsOk() throws Exception {
        String expected = "{\"registration\":true,\"login\":\"fghjk\",\"password\":\"fghkj\"}";

        given(this.service.register(any(Site.class))).willReturn(new JSONResponseRegistration(true, "fghjk", "fghkj"));

        JSONRequest request = new JSONRequest();
        request.setSite("job4j");

        Gson gson = new Gson();
        String json = gson.toJson(request);

        this.mockMvc.perform(post("/shortcuts/registration")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    public void whenRequestInBodyContainsSiteThatHasInSiteStoreThanResponseStatusIsBadRequest() throws Exception {
        String expected = "{\"registration\":false,\"login\":\"\",\"password\":\"\"}";

        given(this.service.register(any(Site.class))).willReturn(new JSONResponseRegistration(false, "", ""));

        JSONRequest request = new JSONRequest();
        request.setSite("job4j");

        Gson gson = new Gson();
        String json = gson.toJson(request);

        this.mockMvc.perform(post("/shortcuts/registration")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expected));
    }

    @Test
    @WithMockUser(username = "user")
    public void whenConvertURLThanReturnStatusISOk() throws Exception {
        String expected = "{\"code\":\"yc4iKxVjZWetek2FJ\"}";
        given(this.service.addNewURL(any(URL.class))).willReturn(new JSONResponseConvert("yc4iKxVjZWetek2FJ"));

        URL url = new URL();
        url.setAddress("https://job4j.ru/TrackStudio/task/8993?thisframe=true");

        Gson gson = new Gson();
        String json = gson.toJson(url);

        this.mockMvc.perform(post("/shortcuts/convert")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    public void whenRedirectURLThanReturnStatusIsFoundAndHTTPInHeader() throws Exception {
        String expected = "https://job4j.ru/TrackStudio/task/8993?thisframe=true";

        given(this.service.getAddress(any(String.class))).willReturn("https://job4j.ru/TrackStudio/task/8993?thisframe=true");

        this.mockMvc.perform(get("/shortcuts/redirect/yc4iKxVjZWetek2FJ"))
                .andExpect(status().isFound())
                .andExpect(header().string("HTTP", expected));
    }

    @Test
    @WithMockUser(username = "user")
    public void when111() throws Exception {
        List<JSONResponseStatistic> list = new ArrayList<>();
        list.add(new JSONResponseStatistic("job4j", 5));

        Gson gson = new Gson();
        String expected = gson.toJson(list);

        given(this.service.getStatistic()).willReturn(list);

        this.mockMvc.perform(get("/shortcuts/statistic"))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }
}