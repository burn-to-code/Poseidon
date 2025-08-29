package com.poseidon.IT.support.records;

public record TestParams(
        String url,
        String view,
        String modelAttribute,
        String entityProperty,
        Object expectedValue
) {}
