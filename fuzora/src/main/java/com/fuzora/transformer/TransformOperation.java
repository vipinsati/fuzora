package com.fuzora.transformer;

import java.util.List;

import lombok.Data;

@Data
public class TransformOperation {
    private TransformOperationType type;
    private List<String> args; // literal values or JSON paths like $.user.name
}
