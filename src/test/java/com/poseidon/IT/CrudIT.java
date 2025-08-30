package com.poseidon.IT;

import com.poseidon.IT.support.EntityTestUtils;
import com.poseidon.IT.support.records.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CrudIT extends SetUpIT{

    @Autowired
    private EntityTestUtils entityTestUtils;


    // ------------------ HOME END POINT------------------
    @ParameterizedTest
    @MethodSource("com.poseidon.IT.support.TestDataProviders#entityProviderForGetList")
    void testCrudOperationsGetList(TestParams params) throws Exception {
        // LIST GET
        mockMvc.perform(get(params.url()))
                .andExpect(status().isOk())
                .andExpect(view().name(params.view()))
                .andExpect(model().attribute(params.modelAttribute(), hasSize(2)))
                .andExpect(model().attribute(params.modelAttribute(),
                        hasItem(hasProperty(params.entityProperty(), is(params.expectedValue()))
                        )));
    }

    // ------------------ ADD END POINT------------------
    @ParameterizedTest
    @MethodSource("com.poseidon.IT.support.TestDataProviders#entityProviderForAdd")
    void testCrudOperationsAdd(TestParams params) throws Exception {
        // ADD GET
        mockMvc.perform(get(params.url()))
                .andExpect(status().isOk())
                .andExpect(view().name(params.view()));
    }

    // ------------------ VALIDATE END POINT------------------
    @ParameterizedTest
    @MethodSource("com.poseidon.IT.support.TestDataProviders#entityProviderForAddValidate")
    void testCrudOperationsAddValidate(TestParamsWithEntityAndParam params) throws Exception {

        Object entity = params.entitySupplier().get();
        Map<String,String> modelAttribute = params.modelAttributeSupplier().apply(entity);


        MockHttpServletRequestBuilder requestBuilder = post(params.url());
        modelAttribute.forEach(requestBuilder::param);

        // ADD POST
        mockMvc.perform(requestBuilder.with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(params.view()));

        assertTrue(entityTestUtils.isPresentEntity(entity));
    }

    @ParameterizedTest
    @MethodSource("com.poseidon.IT.support.TestDataProviders#entityProviderForAddValidateButBindingResultHasErrors")
    void testCrudOperationsAddValidate_WhenBidingResultIsNegative(TestParamWithEntityAndParamAndErrors params) throws Exception {

        Object entity = params.entitySupplier().get();
        Map<String,String> modelAttribute = params.modelAttributeSupplier().apply(entity);

        MockHttpServletRequestBuilder requestBuilder = post(params.url());
        modelAttribute.forEach(requestBuilder::param);

        mockMvc.perform(requestBuilder.with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(params.view()))
                .andExpect(model().attributeExists(params.modelName()))
                .andExpect(model().attributeHasFieldErrors(
                        params.modelName(),
                        params.errors().toArray(new String[0])
                ));
    }

    // ------------------ SHOW UPDATE FORM END POINT------------------
    @ParameterizedTest
    @MethodSource("com.poseidon.IT.support.TestDataProviders#entityProviderForShowUpdate")
    void testCrudOperationsUpdateShowForm(TestParamsWithEntityAndProperty params) throws Exception {

        Object entity = params.entitySupplier().get();
        Integer id = entityTestUtils.saveEntityAndReturnId(entity);

        mockMvc.perform(get(params.url() + "/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name(params.view()))
                .andExpect(model().attributeExists(params.modelAttributeName()))
                .andExpect(model().attribute(params.modelAttributeName(), hasProperty(params.property(), equalTo(params.expectedProperty()))));

    }

    // ------------------ UPDATE END POINT------------------
    @ParameterizedTest
    @MethodSource("com.poseidon.IT.support.TestDataProviders#entityProviderForUpdateValidate")
    void testCrudOperationsUpdate(TestParamsWithEntityAndParam params) throws Exception {

        Object entity = params.entitySupplier().get();
        Integer id = entityTestUtils.saveEntityWithDifferenceAndReturnId(entity);

        Map<String,String> modelAttribute = params.modelAttributeSupplier().apply(entity);

        MockHttpServletRequestBuilder requestBuilder = post(params.url() + "/" + id);
        modelAttribute.forEach(requestBuilder::param);

        mockMvc.perform(requestBuilder.with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(params.view()));

        assertTrue(entityTestUtils.isPresentEntity(entity));
    }

    @ParameterizedTest
    @MethodSource("com.poseidon.IT.support.TestDataProviders#entityProviderForUpdateButBindingResultHasErrors")
    void testCrudOperationsAddUpdate_WhenBidingResultIsNegative(TestParamWithEntityAndParamAndErrors params) throws Exception {

        Object entity = params.entitySupplier().get();
        Integer id = entityTestUtils.saveEntityWithDifferenceAndReturnId(entity);

        Map<String,String> modelAttribute = params.modelAttributeSupplier().apply(entity);

        MockHttpServletRequestBuilder requestBuilder = post(params.url() + "/" + id);
        modelAttribute.forEach(requestBuilder::param);

        mockMvc.perform(requestBuilder.with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name(params.view()))
                .andExpect(model().attributeExists(params.modelName()))
                .andExpect(model().attributeHasFieldErrors(
                        params.modelName(),
                        params.errors().toArray(new String[0])
                ));
    }

    // ------------------ DELETE END POINT------------------
    @ParameterizedTest
    @MethodSource("com.poseidon.IT.support.TestDataProviders#entityProviderForDelete")
    void testCrudOperationsDelete(TestParamsWithEntity params) throws Exception {
        Object entity = params.entitySupplier().get();
        Integer id = entityTestUtils.saveEntityAndReturnId(entity);

        mockMvc.perform(get(params.url() + "/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(params.view()));

        assertFalse(entityTestUtils.isPresentEntity(entity));
    }

}
