package com.investment.managment.api;

import com.investment.managment.page.Pagination;
import com.investment.managment.wallet.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/wallets")
public interface WalletAPI {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateWalletResponse create(@RequestBody CreateWalletRequest walletRequest);

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    UpdateWalletResponse update(@PathVariable("id") String id, @RequestBody UpdateWalletRequest walletRequest);

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("id") String id);

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    FindByIdWalletResponse findById(@PathVariable("id") String id);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Pagination<PageWalletResponse> page(@RequestParam("limit") int limit,
                                        @RequestParam("offset") int offset,
                                        @RequestParam("filter") String filter,
                                        @RequestParam("sort") String sort,
                                        @RequestParam("direction") String direction);


}
