package com.investment.managment.api.wallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investment.managment.DataBaseExtension;
import com.investment.managment.E2ETest;
import com.investment.managment.config.json.Json;
import com.investment.managment.wallet.models.CreateWalletRequest;
import org.hamcrest.Matchers;
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
//
//    @Test
//    public void givenAValidWallet_whenCallsUpdateWithValidParams_shouldUpdateIt() {
//        final var expectedName = "Long term";
//        final var expectedDescription = "This is a long term wallet";
//        final var expectedColor = "FFFFFF";
//
//        final var aWallet = WalletBuilder.create()
//                .name("long")
//                .description("Thus is a long term")
//                .color("FFFDF")
//                .build();
//
//        Assertions.assertEquals(aWallet.getCreatedAt(), aWallet.getUpdatedAt());
//
//        final var actualWallet = aWallet.update(expectedName, expectedDescription, expectedColor);
//
//        Assertions.assertNotNull(actualWallet.getId());
//        Assertions.assertEquals(actualWallet.getName(), expectedName);
//        Assertions.assertEquals(actualWallet.getDescription(), expectedDescription);
//        Assertions.assertEquals(actualWallet.getColor(), expectedColor);
//        Assertions.assertNotNull(actualWallet.getCreatedAt());
//        Assertions.assertTrue(actualWallet.getCreatedAt().isBefore(actualWallet.getUpdatedAt()));
//    }
//
//    @Test
//    public void givenAValidWallet_whenCallsUpdateWithAInvalidNullName_shouldReturnADomainException() {
//        final String expectedName = null;
//        final var expectedDescription = "This is a long term wallet";
//        final var expectedColor = "FFFFFF";
//
//        final var expectedErrorMessage = "'name' should not be null";
//
//        final var aWallet = WalletBuilder.create()
//                .name("long")
//                .description("Thus is a long term")
//                .color("FFFDF")
//                .build();
//
//        Assertions.assertEquals(aWallet.getCreatedAt(), aWallet.getUpdatedAt());
//
//        final var actualException =
//                Assertions.assertThrows(DomainException.class,
//                        () -> aWallet.update(expectedName, expectedDescription, expectedColor));
//
//        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
//    }
//
//    @Test
//    public void givenAValidWallet_whenCallsUpdateWithAInvalidEmptyName_shouldReturnADomainException() {
//        final var expectedName = "  ";
//        final var expectedDescription = "This is a long term wallet";
//        final var expectedColor = "FFFFFF";
//
//        final var expectedErrorMessage = "'name' should not be empty";
//
//        final var aWallet = WalletBuilder.create()
//                .name("long")
//                .description("Thus is a long term")
//                .color("FFFDF")
//                .build();
//
//        Assertions.assertEquals(aWallet.getCreatedAt(), aWallet.getUpdatedAt());
//
//        final var actualException =
//                Assertions.assertThrows(DomainException.class,
//                        () -> aWallet.update(expectedName, expectedDescription, expectedColor));
//
//        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
//    }
//
//    @Test
//    public void givenAValidWallet_whenCallsUpdateWithAInvalidBigName_shouldReturnADomainException() {
//        final var expectedName = """
//                Pensando mais a longo prazo, o acompanhamento das preferências de consumo
//                maximiza as possibilidades por conta das formas de ação. Não obstante, o
//                desafiador cenário globalizado facilita a criação dos relacionamentos
//                verticais entre as hierarquias. Ainda assim, existem dúvidas a respeito
//                de como a execução dos pontos do programa possibilita uma melhor visão
//                global do sistema de formação de quadros que corresponde às necessidades.
//                """;
//        final var expectedDescription = "This is a long term wallet";
//        final var expectedColor = "FFFFFF";
//
//        final var expectedErrorMessage = "'name' should be between 1 and 100 characters";
//
//        final var aWallet = WalletBuilder.create()
//                .name("long")
//                .description("Thus is a long term")
//                .color("FFFDF")
//                .build();
//
//        Assertions.assertEquals(aWallet.getCreatedAt(), aWallet.getUpdatedAt());
//
//        final var actualException =
//                Assertions.assertThrows(DomainException.class,
//                        () -> aWallet.update(expectedName, expectedDescription, expectedColor));
//
//        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
//    }
//
//    @Test
//    public void givenAValidWallet_whenCallsUpdateWithAInvalidBigDescription_shouldReturnADomainException() {
//        final var expectedName = "This is a long term wallet";
//        final var expectedDescription = """
//                Pensando mais a longo prazo, o acompanhamento das preferências de consumo
//                maximiza as possibilidades por conta das formas de ação. Não obstante, o
//                desafiador cenário globalizado facilita a criação dos relacionamentos
//                verticais entre as hierarquias. Ainda assim, existem dúvidas a respeito
//                de como a execução dos pontos do programa possibilita uma melhor visão
//                global do sistema de formação de quadros que corresponde às necessidades.
//                """;
//        final var expectedColor = "FFFFFF";
//
//        final var expectedErrorMessage = "'description' should be between 1 and 255 characters";
//
//        final var aWallet = WalletBuilder.create()
//                .name("long")
//                .description("Thus is a long term")
//                .color("FFFDF")
//                .build();
//
//        Assertions.assertEquals(aWallet.getCreatedAt(), aWallet.getUpdatedAt());
//
//        final var actualException =
//                Assertions.assertThrows(DomainException.class,
//                        () -> aWallet.update(expectedName, expectedDescription, expectedColor));
//
//        Assertions.assertEquals(expectedErrorMessage, actualException.getError().message());
//    }


}
