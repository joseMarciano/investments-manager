package com.investment.managment.api.execution;

import com.investment.managment.execution.models.*;
import com.investment.managment.page.Pagination;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/executions")
public interface ExecutionAPI {

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    UpdateExecutionResponse update(@PathVariable("id") String id, @RequestBody UpdateExecutionRequest executionRequest);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateExecutionResponse create(@RequestBody CreateExecutionRequest executionRequest);

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    FindByIdExecutionResponse findById(@PathVariable("id") String id);

    @GetMapping("summary/{walletId}")
    @ResponseStatus(HttpStatus.OK)
    List<SummaryExecutionByStockResponse> getSummaryByStock(@PathVariable("walletId") String walletId);

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("id") String id);

    @PutMapping("sell/{originId}")
    @ResponseStatus(HttpStatus.CREATED)
    SellExecutionResponse sell(@PathVariable("originId") String originId, @RequestBody SellExecutionRequest executionRequest);


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Pagination<PageExecutionResponse> findAll(
            @RequestParam(required = false) String walletId,
            @RequestParam(required = false) String stockId,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "sort", defaultValue = "createdAt") String sort,
            @RequestParam(value = "direction", defaultValue = "desc") String direction);


}
