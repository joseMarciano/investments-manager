package com.investment.managment.api;

import com.investment.managment.wallet.models.CreateWalletRequest;
import com.investment.managment.wallet.models.CreateWalletResponse;
import com.investment.managment.wallet.models.UpdateWalletRequest;
import com.investment.managment.wallet.models.UpdateWalletResponse;
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


}
