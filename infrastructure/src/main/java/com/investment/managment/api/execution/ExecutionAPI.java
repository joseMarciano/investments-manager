package com.investment.managment.api.execution;

import com.investment.managment.api.execution.models.*;
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


}
