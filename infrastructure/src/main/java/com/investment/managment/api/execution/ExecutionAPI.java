package com.investment.managment.api.execution;

import com.investment.managment.api.execution.models.CreateExecutionRequest;
import com.investment.managment.api.execution.models.CreateExecutionResponse;
import com.investment.managment.api.execution.models.UpdateExecutionRequest;
import com.investment.managment.api.execution.models.UpdateExecutionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/executions")
public interface ExecutionAPI {

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    UpdateExecutionResponse update(@PathVariable("id") String id, @RequestBody UpdateExecutionRequest executionRequest);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateExecutionResponse create(@RequestBody CreateExecutionRequest executionRequest);


}
