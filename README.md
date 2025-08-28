# fuzora
iPaaS library for Developers

# — Fuzora Getting Data From Source

## Overview

This section lists all the supported protocols used in Fuzora to inbound the data. Below are the beans available as of now to fetch data from datasource.

## Supported Protocols

| Protocol      | Bean Name         |
|---------------|-------------------|
| AMQP          | rabbitInput       |
| HTTP Polling  | httpPollingInput  |
| HTTP Webhook  | httpWebhook       |
| SFTP          | sftpInput         |

### Note*
    For configuring each protocol see their separate files.

# — Fuzora Filter Schema Specification

## Overview

This section lists all supported JSON filter configurations used in Fuzora. These configurations are parsed at application startup and executed at runtime on input data.

## Supported Operations

| Operation    | Description                        | Type           |
|--------------|------------------------------------|----------------|
| `equals`     | Checks for exact match             | string, number |
| `contains`   | Checks if string contains value    | string         |
| `startsWith` | Checks if string starts with value | string         |
| `endsWith`   | Checks if string ends with value   | string         |
| `lt`         | Less than                          | number         |
| `lte`        | Less than or equal to              | number         |
| `gt`         | Greater than                       | number         |
| `gte`        | Greater than or equal to           | number         |

## Filter Structure Types

### 1. Basic Filter

```json
{
  "field": "age",
  "operation": "gt",
  "value": 25
}
```

### 2. OR Filter

```json
{
  "OR": [
    { "field": "status", "operation": "equals", "value": "active" },
    { "field": "score", "operation": "gte", "value": 90 }
  ]
}
```

### 3. AND Filter

```json
{
  "AND": [
    { "field": "age", "operation": "gte", "value": 18 },
    { "field": "country", "operation": "equals", "value": "India" }
  ]
}
```

### 4. NOT Filter

```json
{
  "NOT": {
    "field": "status",
    "operation": "equals",
    "value": "inactive"
  }
}
```

## Nested Combinations

### 5. Nested AND inside OR

```json
{
  "OR": [
    {
      "AND": [
        { "field": "age", "operation": "gte", "value": 30 },
        { "field": "city", "operation": "equals", "value": "Mumbai" }
      ]
    },
    { "field": "status", "operation": "equals", "value": "active" }
  ]
}
```

### 6. NOT inside AND

```json
{
  "AND": [
    { "field": "type", "operation": "equals", "value": "premium" },
    {
      "NOT": {
        "field": "blocked", "operation": "equals", "value": true
      }
    }
  ]
}
```

### 7. Deeply Nested

```json
{
  "OR": [
    {
      "AND": [
        { "field": "country", "operation": "equals", "value": "India" },
        {
          "OR": [
            { "field": "status", "operation": "equals", "value": "active" },
            {
              "NOT": {
                "field": "age", "operation": "lt", "value": 18
              }
            }
          ]
        }
      ]
    },
    { "field": "score", "operation": "gt", "value": 95 }
  ]
}
```

## Validation Rules

- Only one of `AND`, `OR`, or `NOT` allowed at the same level.
- Each condition must include `field`, `operation`, and `value`.
- Type compatibility is required:
  - Use `contains`, `startsWith`, `endsWith` only with strings
  - Use `lt`, `gt`, `lte`, `gte` only with numeric values

## Examples of Valid Filters

```json
{ "field": "name", "operation": "startsWith", "value": "A" }
```

```json
{
  "AND": [
    { "field": "age", "operation": "gte", "value": 25 },
    {
      "OR": [
        { "field": "city", "operation": "equals", "value": "Delhi" },
        { "field": "city", "operation": "equals", "value": "Bangalore" }
      ]
    }
  ]
}
```

## Edge Case: Invalid Filters

- Missing required field:
```json
{ "operation": "equals", "value": "x" }
```

- Invalid operation for type:
```json
{ "field": "age", "operation": "contains", "value": "2" }
```

- Multiple logical operators at same level:
```json
{ "AND": [...], "OR": [...] }
```

## Tip: Schema Validation

Use the official [JSON Schema validator](https://www.jsonschemavalidator.net/) with Fuzora's filter schema to catch invalid configurations before loading them at runtime.



# JSON Transformer Engine - Supported & Planned Declarative Mappings

This document outlines all supported and planned JSON declarative mappings for the Transformer Engine.

---

## Supported Mappings

### 1. Basic Key Mapping

Maps a value from one key path to another.

```json
{
  "target": "userId",
  "source": "user.id"
}
```

---

### 2. Nested Mapping

Maps nested source keys to nested target keys.

```json
{
  "target": "user.location.city",
  "source": "user.address.city"
}
```

---

### 3. Array Mapping (Flat Value)

Maps a property inside each array element to a new array.

```json
{
  "target": "orderIds",
  "source": "user.orders",
  "isArray": true,
  "sourceField": "id"
}
```

---

### 4. Transform: UPPERCASE

Converts a string to uppercase.

```json
{
  "target": "nameUpper",
  "transform": {
    "type": "UPPERCASE",
    "args": ["$.user.name"]
  }
}
```

---

### 5. Transform: LOWERCASE

Converts a string to lowercase.

```json
{
  "target": "emailLower",
  "transform": {
    "type": "LOWERCASE",
    "args": ["$.user.email"]
  }
}
```

---

### 6. Transform: CONCAT

Concatenates multiple values or literals.

```json
{
  "target": "fullName",
  "transform": {
    "type": "CONCAT",
    "args": ["$.user.firstName", " ", "$.user.lastName"]
  }
}
```

---

### 7. Arithmetic Transforms

#### ADD

```json
{
  "target": "totalCost",
  "transform": {
    "type": "ADD",
    "args": ["$.price", "$.tax"]
  }
}
```

#### SUBTRACT

```json
{
  "target": "discountedPrice",
  "transform": {
    "type": "SUBTRACT",
    "args": ["$.price", "$.discount"]
  }
}
```

#### MULTIPLY

```json
{
  "target": "totalPrice",
  "transform": {
    "type": "MULTIPLY",
    "args": ["$.price", "$.quantity"]
  }
}
```

#### DIVIDE

```json
{
  "target": "unitPrice",
  "transform": {
    "type": "DIVIDE",
    "args": ["$.totalPrice", "$.quantity"]
  }
}
```

---

### 8. Default Value Fallback

Provides a default value if the source path is missing.

```json
{
  "target": "country",
  "source": "user.country",
  "defaultValue": "USA"
}
```

---

### 9. Constant Value Assignment

Assigns a static value directly.

```json
{
  "target": "status",
  "transform": {
    "type": "CONCAT",
    "args": ["ACTIVE"]
  }
}
```

---

## Planned / Future Features

### 🔄 10. Nested Transforms

Support for nested transformations using recursive evaluation.

```json
{
  "target": "formattedPrice",
  "transform": {
    "type": "CONCAT",
    "args": ["$", {
      "type": "ADD",
      "args": ["$.price", "$.tax"]
    }]
  }
}
```

 Note: This requires recursive transform evaluation. Currently optional.

---

### 11. Type Conversion

Planned support to allow dynamic type casting (e.g., string → int, int → string).

```json
{
  "target": "priceStr",
  "transform": {
    "type": "TO_STRING",
    "args": ["$.price"]
  }
}
```

---

### 12. Conditional Logic

Conditional value selection based on JSONPath predicates.

```json
{
  "target": "status",
  "condition": "$.user.active == true",
  "then": "ACTIVE",
  "else": "INACTIVE"
}
```

---

## Notes

- JSONPath expressions (e.g., `$.user.name`) refer to values in the input JSON.
- Transforms can combine literals and JSONPath values.
- Extendable via plugin/strategy pattern for custom operations.

# — Fuzora Pusing Data to destination

## Overview

This section lists all the supported protocols used in Fuzora to outbound the data. Below are the beans available as of now to push data to endpoint.

## Supported Protocols

| Protocol      | Bean Name         |
|---------------|-------------------|
| AMQP          | rabbitOutput      |
| HTTP          | httpOutput        |
| SFTP          | sftpOutput        |

### Note*
    For configuring each protocol see their separate files.