package com.fuzora.transformer;

import java.util.List;

import lombok.Data;

@Data
public class TransformerSchema {
    private List<MappingRule> mappings;
}
