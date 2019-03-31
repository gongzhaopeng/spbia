package com.aztu68.spring.greenwich.genesis;

import com.aztu68.spring.greenwich.genesis.model.Reader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext
@AutoConfigureMockMvc
public class GenesisApplicationTests {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void contextLoads() {
//    }

    @Test
    public void homePage_unauthenticatedUser() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header()
                        .string("Location", "http://localhost/login"));
    }

    @Test
//    @WithMockUser(username = "craig",
//            password = "password",
//            roles = "READER")
    @WithUserDetails("craig")
    public void homePage() throws Exception {

        Reader expectedReader = new Reader();
        expectedReader.setUsername("craig");
        expectedReader.setPassword("{noop}password");
        expectedReader.setFullname("Craig Walls");

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("readingList"))
                .andExpect(model()
                        .attribute("reader", samePropertyValuesAs(expectedReader)))
                .andExpect(model()
                        .attribute("books", hasSize(0)));
    }

}

