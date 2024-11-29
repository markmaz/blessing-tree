package com.blessingtree.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RosterDTO {
    private List<String> units = new ArrayList<>();
    private List<ParentDTO> parents = new ArrayList<>();

    public List<String> getUnits() {
        return units;
    }

    public void setUnits(List<String> units) {
        this.units = units;
    }

    public List<ParentDTO> getParents() {
        return parents;
    }

    public void setParents(List<ParentDTO> parents) {
        this.parents = parents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RosterDTO rosterDTO)) return false;
        return Objects.equals(units, rosterDTO.units) && Objects.equals(parents, rosterDTO.parents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(units, parents);
    }

    @Override
    public String toString() {
        return "RosterDTO{" +
                "units=" + units +
                ", parents=" + parents +
                '}';
    }
}
