package com.javarush.jira.profile.internal.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashSet;
import java.util.Set;

import static com.javarush.jira.login.internal.web.UserTestData.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAuth() throws Exception {
        mockMvc.perform(get(ProfileRestController.REST_URL)).andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void validProfileTo() throws Exception {
        Set<String> mailNotifications = new HashSet<>();
        mailNotifications.add("assigned");
        mailNotifications.add("overdue");
        mailNotifications.add("deadline");

        Set<ContactTo> contacts = new HashSet<>();
        contacts.add(new ContactTo("skype", "userSkype"));
        contacts.add(new ContactTo("mobile", "+01234567890"));
        contacts.add(new ContactTo("website", "user.com"));

        ProfileTo validProfile = new ProfileTo(null, mailNotifications, contacts);

        MockHttpServletRequestBuilder requestBuilder = put(ProfileRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validProfile));
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void unValidProfileTo() throws Exception {
        Set<String> mailNotification = new HashSet<>();
        mailNotification.add("");
        Set<ContactTo> contacts = new HashSet<>();
        contacts.add(new ContactTo("skype", ""));
        ProfileTo unValidProfile = new ProfileTo(null, mailNotification, contacts);

        MockHttpServletRequestBuilder requestBuilders = put(ProfileRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unValidProfile));
        mockMvc.perform(requestBuilders)
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateProfileTo() throws Exception {
        ProfileTo updateProfile = ProfileTestData.getUpdatedTo();

        MockHttpServletRequestBuilder requestBuilder = put(ProfileRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProfile));
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }
    @Test
    @WithUserDetails(value = USER_MAIL)
    void addNewProfileTo() throws Exception {
        ProfileTo newProfile = ProfileTestData.getNewTo();
        MockHttpServletRequestBuilder requestBuilder = put(ProfileRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProfile));
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent());
    }

}
