package kz.kazgisa.data.dto;

import kz.kazgisa.data.enums.SearchOperation;

public class SearchCriteriaDto {
        private String key;
    private Object value;
    private Object value2;
        private SearchOperation operation;

    public SearchCriteriaDto() {
    }

    public SearchCriteriaDto(String key, Object value, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue2() {
        return value2;
    }

    public void setValue2(Object value2) {
        this.value2 = value2;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(SearchOperation operation) {
        this.operation = operation;
    }
}
