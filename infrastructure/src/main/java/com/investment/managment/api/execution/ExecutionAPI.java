package com.investment.managment.api.execution;

import com.investment.managment.api.ResponseExceptionHandler;
import com.investment.managment.execution.models.*;
import com.investment.managment.page.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/executions")
public interface ExecutionAPI {

    @PutMapping(value = "{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update an Execution")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Execution updated",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "422", description = "Business rules exception",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseExceptionHandler.ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Execution not found", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "404", description = "Stock not found", content = @Content(schema = @Schema))
    })
    UpdateExecutionResponse update(@PathVariable("id") String id, @RequestBody UpdateExecutionRequest executionRequest);

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create an Execution")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Execution created"),
            @ApiResponse(responseCode = "422", description = "Business rules exception",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseExceptionHandler.ApiError.class))),
    })
    CreateExecutionResponse create(@RequestBody CreateExecutionRequest executionRequest);

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Get an Execution")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Execution found"),
            @ApiResponse(responseCode = "404", description = "Execution not found", content = @Content(schema = @Schema))
    })
    FindByIdExecutionResponse findById(@PathVariable("id") String id);

    @GetMapping(value = "summary/{walletId}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a summary of executions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    List<SummaryExecutionByStockResponse> getSummaryByStock(@PathVariable("walletId") String walletId);

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an Execution")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Execution deleted"),
            @ApiResponse(responseCode = "422", description = "Business rules exception",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseExceptionHandler.ApiError.class))),
    })
    void deleteById(@PathVariable("id") String id);

    @PutMapping(value = "sell/{originId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Sell an Execution")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Execution sold"),
            @ApiResponse(responseCode = "422", description = "Business rules exception",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseExceptionHandler.ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Execution origin not found", content = @Content(schema = @Schema))
    })
    SellExecutionResponse sell(@PathVariable("originId") String originId, @RequestBody SellExecutionRequest executionRequest);


    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Pagination of Executions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
    })
    Pagination<PageExecutionResponse> findAll(
            @RequestParam(required = false) String walletId,
            @RequestParam(required = false) String stockId,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "sort", defaultValue = "status") String sort,
            @RequestParam(value = "direction", defaultValue = "asc") String direction);


}
