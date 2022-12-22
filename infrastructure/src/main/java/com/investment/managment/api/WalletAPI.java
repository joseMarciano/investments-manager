package com.investment.managment.api;

import com.investment.managment.wallet.models.CreateWalletRequest;
import com.investment.managment.wallet.models.CreateWalletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/wallets")
public interface WalletAPI {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateWalletResponse create(@RequestBody CreateWalletRequest walletRequest);


}
