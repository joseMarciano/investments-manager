package com.investment.managment.wallet;

import com.investment.managment.Identifier;
import com.investment.managment.util.IdUtil;

public class WalletID extends Identifier<String> {

    private final String id;

    public static WalletID unique() {
        return new WalletID(IdUtil.unique());
    }

    private WalletID(final String id) {
        this.id = id;
    }

    @Override
    public String getValue() {
        return this.id;
    }
}
