package org.rafael.security;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.rafael.security.controller.SecurityController;
import org.rafael.security.service.SimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(SecurityController.class)
public class SecuredControllerWebMvcIntegrationTest {

    @MockBean
    SimpleService simpleService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenRequestOnPrivateService_shouldFailWith401() throws Exception {
        mvc.perform(post("/api/private")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"Hallo\"}")
                      )
                    .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(value = "spring", roles = "USER")
    public void givenRequestOnPrivateService_shouldSucceed() throws Exception {
        mvc.perform(post("/api/private")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"Hallo\"}"))
                    .andExpect(status().isOk());
    }

    @WithMockUser(value = "spring", roles = "USER")
    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mvc.perform(post("/api/private")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"message\":\"Hallo\"}")
            ).andExpect(status().isOk());
    }

    @Test
    public void givenRequestOnFreeController_shouldSucceedWith200() throws Exception {
        when(simpleService.getMessage()).thenReturn("Hello World!");
        mvc.perform(get("/api/public")
                // .contentType(MediaType.APPLICATION_JSON)
                )
            .andExpect(status().isOk());
    }

}
