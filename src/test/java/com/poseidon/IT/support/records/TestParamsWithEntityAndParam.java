package com.poseidon.IT.support.records;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public record TestParamsWithEntityAndParam(
        String url,
        String view,
        Supplier<Object> entitySupplier,
        Function<Object, Map<String, String>> modelAttributeSupplier
) {
}
