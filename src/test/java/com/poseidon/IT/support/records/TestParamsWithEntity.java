package com.poseidon.IT.support.records;

import java.util.function.Supplier;

public record TestParamsWithEntity(
        String url,
        String view,
        Supplier<Object> entitySupplier
) {}
