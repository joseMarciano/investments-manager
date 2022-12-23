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
import com.investment.managment.wallet.persistence.WalletRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.valueOf;

@E2ETest
public class WalletControllerE2ETest extends DataBaseExtension {

    private static final String DEFAULT_PATH = "/wallets";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WalletRepository walletRepository;

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


    @Test
    public void givenAValidID_whenCallsDeleteWallet_shouldDeleteIt() throws Exception {
        final var expectedName = "This is a long term wallet";
        final var expectedDescription = "THis is long term description";
        final var expectedColor = "FFFFFF";

        Assertions.assertEquals(walletRepository.count(), 0);
        final var expectedId = givenWallet(new CreateWalletRequest(expectedName, expectedDescription, expectedColor));
        Assertions.assertEquals(walletRepository.count(), 1);

        final RequestBuilder request = MockMvcRequestBuilders.delete(DEFAULT_PATH + "/{id}", expectedId);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertEquals(walletRepository.count(), 0);
    }

    @Test
    public void givenAnInvalidID_whenCallsDeleteWallet_shouldBeOk() throws Exception {
        Assertions.assertEquals(walletRepository.count(), 0);
        final var anId = WalletID.unique().getValue();

        final RequestBuilder request = MockMvcRequestBuilders.delete(DEFAULT_PATH + "/{id}", anId);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(MockMvcResultHandlers.print());

        Assertions.assertEquals(walletRepository.count(), 0);
    }

    @Test
    public void givenAValidID_whenCallsUseCase_shouldReturnWallet() throws Exception {
        final var expectedName = "This is a long term wallet";
        final var expectedDescription = "THis is long term description";
        final var expectedColor = "FFFFFF";

        Assertions.assertEquals(walletRepository.count(), 0);
        final var expectedId = givenWallet(new CreateWalletRequest(expectedName, expectedDescription, expectedColor));
        Assertions.assertEquals(walletRepository.count(), 1);


        final RequestBuilder request = MockMvcRequestBuilders.get(DEFAULT_PATH + "/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(expectedId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(expectedName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(expectedDescription)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color", Matchers.is(expectedColor)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt", Matchers.notNullValue()));
    }

    @Test
    public void givenAnInvalidID_whenCallsUseCase_shouldReturnANotFoundException() throws Exception {
        final var expectedId = WalletID.unique();

        final RequestBuilder request = MockMvcRequestBuilders.get(DEFAULT_PATH + "/{id}", expectedId)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @ParameterizedTest
    @CsvSource({
            "0:1:name:asc, 3, A Day trade",
            "0:1:name:desc, 3, Zero lost",
            "0:1:description:asc, 3, Zero lost",
            "0:1:description:desc, 3, É muito bom"
    })
    public void givenAValidQuery_whenCallsFindAll_shouldReturnPageCorrectly(final String params, final int expectedTotal, final String expectedName) throws Exception {
        final var queryParams = params.split(":");
        final var expectedOffset = queryParams[0];
        final var expectedLimit = queryParams[1];
        final var expectedSort = queryParams[2];
        final var expectedDirection = queryParams[3];
        final var expectedFilter = "";

        givenWallets(
                new CreateWalletRequest("Zero lost", "A Day trade is nice", "D0D0D0"),
                new CreateWalletRequest("A Day trade", "The man", "FFFFF"),
                new CreateWalletRequest("É muito bom", "Zero lost is liar", "D0D0D3")
        );
        final RequestBuilder request = MockMvcRequestBuilders.get(DEFAULT_PATH)
                .queryParam("offset", expectedOffset)
                .queryParam("limit", expectedLimit)
                .queryParam("sort", expectedSort)
                .queryParam("direction", expectedDirection)
                .queryParam("filter", expectedFilter)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.offset", Matchers.is(valueOf(expectedOffset))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.limit", Matchers.is(valueOf(expectedLimit))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total", Matchers.is(expectedTotal)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].name", Matchers.is(expectedName)));
    }


    @ParameterizedTest
    @CsvSource({
            "0:1:name:asc, 4, A Day trade",
            "1:1:name:asc, 4, É muito bom",
            "2:1:name:asc, 4, God Day trade",
            "3:1:name:asc, 4, Zero lost"
    })
    public void givenAValidQuery_whenCallsFindAll_shouldPageCorrectly(final String params, final int expectedTotal, final String expectedName) throws Exception {
        final var queryParams = params.split(":");
        final var expectedOffset = queryParams[0];
        final var expectedLimit = queryParams[1];
        final var expectedSort = queryParams[2];
        final var expectedDirection = queryParams[3];
        final var expectedFilter = "";

        givenWallets(
                new CreateWalletRequest("Zero lost", "A Day trade is nice", "D0D0D0"),
                new CreateWalletRequest("A Day trade", "The man", "FFFFF"),
                new CreateWalletRequest("God Day trade", "The man", "FFFFF"),
                new CreateWalletRequest("É muito bom", "Zero lost is liar", "D0D0D3")
        );

        final RequestBuilder request = MockMvcRequestBuilders.get(DEFAULT_PATH)
                .queryParam("offset", expectedOffset)
                .queryParam("limit", expectedLimit)
                .queryParam("sort", expectedSort)
                .queryParam("direction", expectedDirection)
                .queryParam("filter", expectedFilter)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.offset", Matchers.is(valueOf(expectedOffset))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.limit", Matchers.is(valueOf(expectedLimit))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total", Matchers.is(expectedTotal)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].name", Matchers.is(expectedName)));
    }

    @ParameterizedTest
    @CsvSource({
            "liker, Liker many things, 1",
            "ld, Should I stay or should I go, 1",
            "@, Caracteres especiais: +=12-!@, 1",
    })
    public void givenAValidQuery_whenCallsFindAll_shouldReturnPageFilteredCorrectly(final String expectedFilter, final String expectedName, final int expectedTotal) throws Exception {
        final var expectedOffset = "0";
        final var expectedLimit = "20";
        final var expectedSort = "name";
        final var expectedDirection = "desc";

        givenWallets(
                new CreateWalletRequest("Liker many things", "Options nicer", "D0D0D0"),
                new CreateWalletRequest("Should I stay or should I go", "Curious with ç", "FFFFF"),
                new CreateWalletRequest("Caracteres especiais: +=12-!@", "Zero lost is liar", "D0D0D3")
        );

        final RequestBuilder request = MockMvcRequestBuilders.get(DEFAULT_PATH)
                .queryParam("offset", expectedOffset)
                .queryParam("limit", expectedLimit)
                .queryParam("sort", expectedSort)
                .queryParam("direction", expectedDirection)
                .queryParam("filter", expectedFilter)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.offset", Matchers.is(valueOf(expectedOffset))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.limit", Matchers.is(valueOf(expectedLimit))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total", Matchers.is(expectedTotal)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].name", Matchers.is(expectedName)));
    }

    @ParameterizedTest
    @CsvSource({
            "consumer",
            "(mui bien)",
            "there are",
    })
    public void givenAValidQuery_whenCallsFindAll_shouldReturnEmptyPageCorrectly(final String expectedFilter) throws Exception {
        final var expectedOffset = "0";
        final var expectedLimit = "20";
        final var expectedSort = "name";
        final var expectedDirection = "desc";
        final var expectedTotal = 0;

        givenWallets(
                new CreateWalletRequest("Liker many things", "Options nicer", "D0D0D0"),
                new CreateWalletRequest("Should I stay or should I go", "Curious with ç", "FFFFF"),
                new CreateWalletRequest("Caracteres especiais: +=12-!@", "Zero lost is liar", "D0D0D3")
        );


        final RequestBuilder request = MockMvcRequestBuilders.get(DEFAULT_PATH)
                .queryParam("offset", expectedOffset)
                .queryParam("limit", expectedLimit)
                .queryParam("sort", expectedSort)
                .queryParam("direction", expectedDirection)
                .queryParam("filter", expectedFilter)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.offset", Matchers.is(valueOf(expectedOffset))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.limit", Matchers.is(valueOf(expectedLimit))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.total", Matchers.is(expectedTotal)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.items.length()", Matchers.is(0)));
    }

    private List<String> givenWallets(final CreateWalletRequest... walletsRequests) throws Exception {
        final var walletsId = new ArrayList<String>();
        for (CreateWalletRequest w : walletsRequests) {
            walletsId.add(givenWallet(w));
        }
        return walletsId;
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
