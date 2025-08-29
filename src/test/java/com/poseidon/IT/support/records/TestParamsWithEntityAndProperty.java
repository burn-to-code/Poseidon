package com.poseidon.IT.support.records;

import java.util.function.Supplier;

public record TestParamsWithEntityAndProperty(
        String url,
        String view,
        String modelAttributeName,
        String property,
        Object expectedProperty,
        Supplier<Object> entitySupplier
) {}
