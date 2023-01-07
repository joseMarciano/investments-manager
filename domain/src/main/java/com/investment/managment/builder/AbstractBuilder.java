package com.investment.managment.builder;

public abstract class AbstractBuilder<TARGET_CLASS> {

    protected TARGET_CLASS target;

    public AbstractBuilder(TARGET_CLASS target) {
        this.target = target;
    }

    public TARGET_CLASS build() {
        this.validateEntity();
        return target;
    }

    private void validateEntity() {
        this.validate();
        this.afterValidate();
    }

    protected void validate() {
    }

    protected void afterValidate() {
    }

}
