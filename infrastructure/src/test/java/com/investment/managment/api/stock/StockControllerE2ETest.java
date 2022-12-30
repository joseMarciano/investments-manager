package com.investment.managment.api.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investment.managment.DataBaseExtension;
import com.investment.managment.E2ETest;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockBuilder;
import com.investment.managment.stock.persistence.StockJpaEntity;
import com.investment.managment.stock.persistence.StockRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.valueOf;

@E2ETest
public class StockControllerE2ETest extends DataBaseExtension {

    private static final String DEFAULT_PATH = "/stocks";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ObjectMapper mapper;


    @ParameterizedTest
    @CsvSource({
            "0:1:symbol:asc, 3, A Day trade",
            "0:1:symbol:desc, 3, Zero lost",
    })
    public void givenAValidQuery_whenCallsFindAll_shouldReturnPageCorrectly(final String params, final int expectedTotal, final String expectedName) throws Exception {
        final var queryParams = params.split(":");
        final var expectedOffset = queryParams[0];
        final var expectedLimit = queryParams[1];
        final var expectedSort = queryParams[2];
        final var expectedDirection = queryParams[3];
        final var expectedFilter = "";

        givenStocks(
                StockBuilder.create().symbol("Zero lost").build(),
                StockBuilder.create().symbol("A Day trade").build(),
                StockBuilder.create().symbol("É muito bom").build()
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].symbol", Matchers.is(expectedName)));
    }


    @ParameterizedTest
    @CsvSource({
            "0:1:symbol:asc, 4, A Day trade",
            "1:1:symbol:asc, 4, É muito bom",
            "2:1:symbol:asc, 4, God Day trade",
            "3:1:symbol:asc, 4, Zero lost"
    })
    public void givenAValidQuery_whenCallsFindAll_shouldPageCorrectly(final String params, final int expectedTotal, final String expectedName) throws Exception {
        final var queryParams = params.split(":");
        final var expectedOffset = queryParams[0];
        final var expectedLimit = queryParams[1];
        final var expectedSort = queryParams[2];
        final var expectedDirection = queryParams[3];
        final var expectedFilter = "";

        givenStocks(
                StockBuilder.create().symbol("Zero lost").build(),
                StockBuilder.create().symbol("A Day trade").build(),
                StockBuilder.create().symbol("God Day trade").build(),
                StockBuilder.create().symbol("É muito bom").build()
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].symbol", Matchers.is(expectedName)));
    }

    @ParameterizedTest
    @CsvSource({
            "liker, Liker many things, 1",
            "ld, Should I st, 1",
            "@, Caracteres: +=12-!@, 1",
    })
    public void givenAValidQuery_whenCallsFindAll_shouldReturnPageFilteredCorrectly(final String expectedFilter, final String expectedName, final int expectedTotal) throws Exception {
        final var expectedOffset = "0";
        final var expectedLimit = "20";
        final var expectedSort = "symbol";
        final var expectedDirection = "desc";

        givenStocks(
                StockBuilder.create().symbol("Liker many things").build(),
                StockBuilder.create().symbol("Should I st").build(),
                StockBuilder.create().symbol("Caracteres: +=12-!@").build()
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.items[0].symbol", Matchers.is(expectedName)));
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
        final var expectedSort = "symbol";
        final var expectedDirection = "desc";
        final var expectedTotal = 0;

        givenStocks(
                StockBuilder.create().symbol("Liker many things").build(),
                StockBuilder.create().symbol("Should I stay or sho").build(),
                StockBuilder.create().symbol("Caracter: +=12-!@").build()
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

    private List<String> givenStocks(final Stock... stocks) {
        final var stocksId = new ArrayList<String>();
        for (Stock s : stocks) {
            stocksId.add(givenStock(s));
        }
        return stocksId;
    }

    private String givenStock(final Stock aStock) {
        stockRepository.saveAndFlush(StockJpaEntity.from(aStock));

        return stockRepository.saveAndFlush(StockJpaEntity.from(aStock)).getId();
    }

}
