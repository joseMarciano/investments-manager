package com.investment.managment.api.wallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investment.managment.DataBaseExtension;
import com.investment.managment.E2ETest;
import com.investment.managment.config.json.Json;
import com.investment.managment.wallet.WalletID;
import com.investment.managment.wallet.models.CreateWalletRequest;
import com.investment.managment.wallet.models.CreateWalletResponse;
import com.investment.managment.wallet.models.UpdateWalletRequest;
import com.investment.managment.wallet.models.UpdateWalletResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@E2ETest
public class WalletControllerE2ETest extends DataBaseExtension {

    private static final String DEFAULT_PATH = "/wallets";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void givenAValidParams_whenCallsNewWallet_shouldInstantiateIt() throws Exception {
        final var expectedName = "Long term";
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";

        final var actualRequestCommand = new CreateWalletRequest(expectedName, expectedDescription, expectedColor);

        final RequestBuilder request = MockMvcRequestBuilders.post(DEFAULT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(expectedName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(expectedDescription)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Matchers.is(expectedColor)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt", Matchers.notNullValue()));
    }

    @Test
    public void givenAInvalidNullName_whenCallsNewWallet_shouldReturn422() throws Exception {
        final String expectedName = null;
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";

        final var expectedErrorMessage = "'name' should not be null";
        final var actualRequestCommand = new CreateWalletRequest(expectedName, expectedDescription, expectedColor);

        final RequestBuilder request = MockMvcRequestBuilders.post(DEFAULT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", Matchers.is(expectedErrorMessage)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenAInvalidEmptyName_whenCallsNewWallet_shouldReturn422() throws Exception {
        final var expectedName = "  ";
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";

        final var expectedErrorMessage = "'name' should not be empty";
        final var actualRequestCommand = new CreateWalletRequest(expectedName, expectedDescription, expectedColor);

        final RequestBuilder request = MockMvcRequestBuilders.post(DEFAULT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", Matchers.is(expectedErrorMessage)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenAInvalidBigName_whenCallsNewWallet_shouldReturn422() throws Exception {
        final var expectedName = """
                Pensando mais a longo prazo, o acompanhamento das preferências de consumo
                maximiza as possibilidades por conta das formas de ação. Não obstante, o
                desafiador cenário globalizado facilita a criação dos relacionamentos
                verticais entre as hierarquias. Ainda assim, existem dúvidas a respeito
                de como a execução dos pontos do programa possibilita uma melhor visão
                global do sistema de formação de quadros que corresponde às necessidades.
                """;
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";

        final var expectedErrorMessage = "'name' should be between 1 and 100 characters";

        final var actualRequestCommand = new CreateWalletRequest(expectedName, expectedDescription, expectedColor);

        final RequestBuilder request = MockMvcRequestBuilders.post(DEFAULT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", Matchers.is(expectedErrorMessage)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenAInvalidBigDescription_whenCallsNewWallet_shouldReturn422() throws Exception {
        final var expectedName = "This is a long term wallet";
        final var expectedDescription = """
                Pensando mais a longo prazo, o acompanhamento das preferências de consumo
                maximiza as possibilidades por conta das formas de ação. Não obstante, o
                desafiador cenário globalizado facilita a criação dos relacionamentos
                verticais entre as hierarquias. Ainda assim, existem dúvidas a respeito
                de como a execução dos pontos do programa possibilita uma melhor visão
                global do sistema de formação de quadros que corresponde às necessidades.
                """;
        final var expectedColor = "FFFFFF";

        final var expectedErrorMessage = "'description' should be between 1 and 255 characters";

        final var actualRequestCommand = new CreateWalletRequest(expectedName, expectedDescription, expectedColor);

        final RequestBuilder request = MockMvcRequestBuilders.post(DEFAULT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", Matchers.is(expectedErrorMessage)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenAValidWallet_whenCallsUpdateWithValidParams_shouldUpdateIt() throws Exception {
        final var expectedName = "Long term";
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";

        final var expectedId = givenWallet(new CreateWalletRequest("long", "long description", "FFFDE"));

        final var actualRequestCommand = new UpdateWalletRequest(expectedId, expectedName, expectedDescription, expectedColor);

        final RequestBuilder request = MockMvcRequestBuilders.put(DEFAULT_PATH + "/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(expectedId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(expectedName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(expectedDescription)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Matchers.is(expectedColor)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt", Matchers.notNullValue()))
                .andExpect(result -> {
                    final UpdateWalletResponse updateWalletResponse = Json.readValue(result.getResponse().getContentAsString(), UpdateWalletResponse.class);
                    Assertions.assertTrue(updateWalletResponse.createdAt().isBefore(updateWalletResponse.updatedAt()));
                });
    }

    @Test
    public void givenAValidWallet_whenCallsUpdateWithAInvalidNullName_shouldReturn422() throws Exception {
        final String expectedName = null;
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";
        final var expectedErrorMessage = "'name' should not be null";

        final var expectedId = givenWallet(new CreateWalletRequest("long", "long description", "FFFDE"));

        final var actualRequestCommand = new UpdateWalletRequest(expectedId, expectedName, expectedDescription, expectedColor);

        final RequestBuilder request = MockMvcRequestBuilders.put(DEFAULT_PATH + "/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", Matchers.is(expectedErrorMessage)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenAValidWallet_whenCallsUpdateWithAInvalidEmptyName_shouldReturn422() throws Exception {
        final var expectedName = "  ";
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";
        final var expectedErrorMessage = "'name' should not be empty";

        final var expectedId = givenWallet(new CreateWalletRequest("long", "long description", "FFFDE"));

        final var actualRequestCommand = new UpdateWalletRequest(expectedId, expectedName, expectedDescription, expectedColor);

        final RequestBuilder request = MockMvcRequestBuilders.put(DEFAULT_PATH + "/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", Matchers.is(expectedErrorMessage)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenAValidWallet_whenCallsUpdateWithAInvalidBigName_shouldReturn422() throws Exception {
        final var expectedName = """
                Pensando mais a longo prazo, o acompanhamento das preferências de consumo
                maximiza as possibilidades por conta das formas de ação. Não obstante, o
                desafiador cenário globalizado facilita a criação dos relacionamentos
                verticais entre as hierarquias. Ainda assim, existem dúvidas a respeito
                de como a execução dos pontos do programa possibilita uma melhor visão
                global do sistema de formação de quadros que corresponde às necessidades.
                """;
        final var expectedDescription = "This is a long term wallet";
        final var expectedColor = "FFFFFF";

        final var expectedErrorMessage = "'name' should be between 1 and 100 characters";

        final var expectedId = givenWallet(new CreateWalletRequest("long", "long description", "FFFDE"));

        final var actualRequestCommand = new UpdateWalletRequest(expectedId, expectedName, expectedDescription, expectedColor);

        final RequestBuilder request = MockMvcRequestBuilders.put(DEFAULT_PATH + "/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", Matchers.is(expectedErrorMessage)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenAValidWallet_whenCallsUpdateWithAInvalidBigDescription_shouldReturn422() throws Exception {
        final var expectedName = "This is a long term wallet";
        final var expectedDescription = """
                Pensando mais a longo prazo, o acompanhamento das preferências de consumo
                maximiza as possibilidades por conta das formas de ação. Não obstante, o
                desafiador cenário globalizado facilita a criação dos relacionamentos
                verticais entre as hierarquias. Ainda assim, existem dúvidas a respeito
                de como a execução dos pontos do programa possibilita uma melhor visão
                global do sistema de formação de quadros que corresponde às necessidades.
                """;
        final var expectedColor = "FFFFFF";

        final var expectedErrorMessage = "'description' should be between 1 and 255 characters";
        final var expectedId = givenWallet(new CreateWalletRequest("long", "long description", "FFFDE"));

        final var actualRequestCommand = new UpdateWalletRequest(expectedId, expectedName, expectedDescription, expectedColor);

        final RequestBuilder request = MockMvcRequestBuilders.put(DEFAULT_PATH + "/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message", Matchers.is(expectedErrorMessage)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenInvalidWallet_whenCallsUpdateWithAInvalidID_shouldReturn404() throws Exception {

        final var anId = WalletID.unique().getValue();
        final var actualRequestCommand = new UpdateWalletRequest(anId, "A NAME", "dESCRITP", "fffrt");

        final RequestBuilder request = MockMvcRequestBuilders.put(DEFAULT_PATH + "/{id}", anId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    private String givenWallet(final CreateWalletRequest walletRequest) throws Exception {
        final var actualRequestCommand = new CreateWalletRequest(walletRequest.name(), walletRequest.description(), walletRequest.color());
        final RequestBuilder request = MockMvcRequestBuilders.post(DEFAULT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(actualRequestCommand));

        return Json.readValue(mvc.perform(request).andReturn().getResponse().getContentAsString(), CreateWalletResponse.class).id();
    }

}
