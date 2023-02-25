package id.maseka.playground.service.device.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import id.maseka.playground.service.common.BaseControllerTest;
import id.maseka.playground.service.device.domain.Device;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.ZonedDateTime;
import java.util.*;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DeviceWebControllerTest extends BaseControllerTest {

    @Test
    void given_data_when_createDevice_then_returnId() throws Exception {
        // given all data is sent
        var request = new HashMap<String, Object>();
        request.put("deviceType", 1);
        request.put("identifier", UUID.randomUUID().toString());
        request.put("externalUserId", UUID.randomUUID().toString());

        // when
        mockMvc.perform(post("/v1/device").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                //then
                .andExpectAll(status().isCreated())
                .andDo(
                        document("device-create",
                            requestFields(attributes(key("title").value("Create a device request")),
                                    fieldWithPath("deviceType").description("device's platform").attributes(getConstraintAtributesOfProperty(Device.class, "deviceType")),
                                    fieldWithPath("identifier").description("type of device ").attributes(getConstraintAtributesOfProperty(Device.class, "identifier")),
                                    fieldWithPath("externalUserId").description("type of device ").attributes(getConstraintAtributesOfProperty(Device.class, "externalUserId")).optional()
                            )
                        ));

        // given external user id is not sent
        request.clear();
        request.put("deviceType", 1);
        request.put("identifier", UUID.randomUUID().toString());

        // when
        mockMvc.perform(post("/v1/device").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                //then
                .andExpectAll(status().isCreated())
                .andDo(
                        document("device-create",
                                requestFields(attributes(key("title").value("Create a device request")),
                                        fieldWithPath("deviceType").description("device's platform").attributes(getConstraintAtributesOfProperty(Device.class, "deviceType")),
                                        fieldWithPath("identifier").description("type of device ").attributes(getConstraintAtributesOfProperty(Device.class, "identifier")),
                                        fieldWithPath("externalUserId").description("type of device ").attributes(getConstraintAtributesOfProperty(Device.class, "externalUserId")).type(JsonFieldType.STRING).optional()
                                )
                        ));
    }

    @Test
    void given_invalidData_when_createDevice_then_returnError() throws Exception {
        // given null request body
        Map<String, Object> request = null;

        // when
        mockMvc.perform(post("/v1/device").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                //then
                .andExpectAll(
                        status().is4xxClientError(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("$.type").value("about:blank"),
                        jsonPath("$.title").value("Bad Request"),
                        jsonPath("$.detail").value("Failed to read request"),
                        jsonPath("$.instance").value("/v1/device")
                );

        // given the body data is not sent
        request = new HashMap<>();

        // when
        mockMvc.perform(post("/v1/device").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                //then
                .andExpectAll(
                        status().is4xxClientError(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("$.type").value("/errors/validation-error"),
                        jsonPath("$.title").value("validation error"),
                        jsonPath("$.detail").value("fail to validate constraints"),
                        jsonPath("$.instance").value("/v1/device"),
                        jsonPath("$.parameter").isMap(),
                        jsonPath("$.parameter.deviceType").value("device type cannot be null"),
                        jsonPath("$.parameter.identifier").value("identifier cannot be null or blank"),
                        jsonPath("$.parameter.externalUserId").doesNotExist(),
                        jsonPath("$.parameter.lastActive").doesNotExist()
                );

        // given device type is not sent
        request.clear();
        request.put("deviceType", null);
        request.put("identifier", UUID.randomUUID().toString());
        request.put("externalUserId", UUID.randomUUID().toString());

        // when
        mockMvc.perform(post("/v1/device").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                //then
                .andExpectAll(
                        status().is4xxClientError(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("$.type").value("/errors/validation-error"),
                        jsonPath("$.title").value("validation error"),
                        jsonPath("$.detail").value("fail to validate constraints"),
                        jsonPath("$.instance").value("/v1/device"),
                        jsonPath("$.parameter").isMap(),
                        jsonPath("$.parameter.deviceType").value("device type cannot be null")
                );

        // given identifier is blank
        request.clear();
        request.put("deviceType", 1);
        request.put("identifier", "");
        request.put("externalUserId", UUID.randomUUID().toString());

        // when
        mockMvc.perform(post("/v1/device").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                //then
                .andExpectAll(
                        status().is4xxClientError(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("$.type").value("/errors/validation-error"),
                        jsonPath("$.title").value("validation error"),
                        jsonPath("$.detail").value("fail to validate constraints"),
                        jsonPath("$.instance").value("/v1/device"),
                        jsonPath("$.parameter").isMap(),
                        jsonPath("$.parameter.identifier").value("identifier cannot be null or blank")
                );

        // given external user id is blank
        request.clear();
        request.put("deviceType", 1);
        request.put("identifier", UUID.randomUUID().toString());
        request.put("externalUserId", "");

        // when
        mockMvc.perform(post("/v1/device").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                //then
                .andExpectAll(
                        status().is4xxClientError(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("$.type").value("/errors/validation-error"),
                        jsonPath("$.title").value("validation error"),
                        jsonPath("$.detail").value("fail to validate constraints"),
                        jsonPath("$.instance").value("/v1/device"),
                        jsonPath("$.parameter").isMap(),
                        jsonPath("$.parameter.externalUserId").value("external user id cannot be blank")
                );
    }

    @Test
    void given_notFoundId_when_getDeviceById_then_returnError() throws Exception {
        // given
        // when
        mockMvc.perform(get("/v1/device/da49b6d647f").contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_PROBLEM_JSON),
                        jsonPath("$.type").value("/errors/not-found-error"),
                        jsonPath("$.title").value("resource is not found"),
                        jsonPath("$.detail").value("resource da49b6d647f of Device is not found"),
                        jsonPath("$.instance").value("/v1/device/da49b6d647f"),
                        jsonPath("$.entity").isMap(),
                        jsonPath("$.entity.Device").value("da49b6d647f")
                );
    }

    @Test
    void given_createdDeviceWithDeviceTypeAndIdentifier_when_getDeviceById_then_returnDeviceWithDeviceTypeAndIdentifierAndLastActive() throws Exception {
        // given
        var request = new HashMap<String, Object>();
        request.put("deviceType", 1);
        request.put("identifier", "7a7e6e6b7c7");
        var id = mockMvc.perform(post("/v1/device").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        // when
        mockMvc.perform(get("/v1/device/" + id))
            // then
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").exists(),
                        jsonPath("$.deviceType").value("ANDROID"),
                        jsonPath("$.identifier").value("7a7e6e6b7c7"),
                        jsonPath("$.externalUserId").doesNotExist(),
                        jsonPath("$.lastActive").exists())
                .andDo(
                        document("device-get",
                                responseFields(attributes(key("title").value("Get a device response")),
                                        fieldWithPath("id").description("data identifier"),
                                        fieldWithPath("deviceType").description("device's platform"),
                                        fieldWithPath("identifier").description("device's token"),
                                        fieldWithPath("externalUserId").description("device's external user id"),
                                        fieldWithPath("lastActive").description("device's last active with ISO 8601").type(ZonedDateTime.class)
                                )
                        ));
    }

    @Test
    void given_createdDeviceWithDeviceTypeAndIdentifierAndExternalUserId_when_getDeviceById_then_returnDeviceWithDeviceTypeAndIdentifierAndExternalUserIdAndLastActive() throws Exception {
        // given
        var request = new HashMap<String, Object>();
        request.put("deviceType", 1);
        request.put("identifier", "7a7e6e6b7c7");
        request.put("externalUserId", "857474");
        var id = mockMvc.perform(post("/v1/device").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        // when
        mockMvc.perform(get("/v1/device/" + id))
                // then
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").exists(),
                        jsonPath("$.deviceType").value("ANDROID"),
                        jsonPath("$.identifier").value("7a7e6e6b7c7"),
                        jsonPath("$.externalUserId").value("857474"),
                        jsonPath("$.lastActive").exists())
                .andDo(
                        document("device-get",
                                responseFields(attributes(key("title").value("Get a device response")),
                                        fieldWithPath("id").description("data identifier"),
                                        fieldWithPath("deviceType").description("device's platform"),
                                        fieldWithPath("identifier").description("device's token"),
                                        fieldWithPath("externalUserId").description("device's external user id"),
                                        fieldWithPath("lastActive").description("device's last active with ISO 8601").type(ZonedDateTime.class)
                                )
                        ));
    }

    @Override
    protected Collection<Class<?>> defineModelToAssignConstraint() {
        return List.of(Device.class);
    }
}
