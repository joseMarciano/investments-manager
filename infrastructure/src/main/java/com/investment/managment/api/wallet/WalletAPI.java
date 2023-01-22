package com.investment.managment.api.wallet;

import com.investment.managment.api.ResponseExceptionHandler;
import com.investment.managment.page.Pagination;
import com.investment.managment.wallet.models.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/wallets")
public interface WalletAPI {
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a Wallet")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Wallet created"),
            @ApiResponse(responseCode = "422", description = "Business rules exception",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseExceptionHandler.ApiError.class))),
    })
    CreateWalletResponse create(@RequestBody CreateWalletRequest walletRequest);


    @PutMapping(value = "{id}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a Wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet updated"),
            @ApiResponse(responseCode = "422", description = "Business rules exception",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseExceptionHandler.ApiError.class))),
            @ApiResponse(responseCode = "404", description = "Wallet not found", content = @Content(schema = @Schema))
    })
    UpdateWalletResponse update(@PathVariable("id") String id, @RequestBody UpdateWalletRequest walletRequest);

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a Wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Wallet deleted",
                    content = {@Content(schema = @Schema)}),
    })
    void deleteById(@PathVariable("id") String id);

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a Wallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet found"),
            @ApiResponse(responseCode = "404", description = "Wallet not found", content = @Content(schema = @Schema))
    })
    FindByIdWalletResponse findById(@PathVariable("id") String id);

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Pagination of Wallets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
    })
    Pagination<PageWalletResponse> page(@RequestParam(value = "limit", defaultValue = "20") int limit,
                                        @RequestParam(value = "offset", defaultValue = "0") int offset,
                                        @RequestParam(value = "filter", required = false) String filter,
                                        @RequestParam(value = "sort", defaultValue = "name") String sort,
                                        @RequestParam(value = "direction", defaultValue = "asc") String direction);


}
