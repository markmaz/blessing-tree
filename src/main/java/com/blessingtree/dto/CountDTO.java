package com.blessingtree.dto;

import java.util.Objects;

public class CountDTO {
    private Long count;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountDTO countDTO)) return false;
        return Objects.equals(count, countDTO.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }

    @Override
    public String toString() {
        return "CountDTO{" +
                "count=" + count +
                '}';
    }
}
