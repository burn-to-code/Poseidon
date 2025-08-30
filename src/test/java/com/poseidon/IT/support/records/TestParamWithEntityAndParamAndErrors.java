package com.poseidon.IT.support.records;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public record TestParamWithEntityAndParamAndErrors(
        String url,
        String view,
        Supplier<Object> entitySupplier,
        Function<Object, Map<String, String>> modelAttributeSupplier,
        String modelName,
        List<Object> errors
) {
}
