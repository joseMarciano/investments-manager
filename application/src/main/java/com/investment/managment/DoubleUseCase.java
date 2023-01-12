package com.investment.managment;

public abstract class DoubleUseCase<FIRST_INPUT, SECOND_INPUT, OUTPUT> {

    public abstract OUTPUT execute(FIRST_INPUT fistInput, SECOND_INPUT secondInput);

}

