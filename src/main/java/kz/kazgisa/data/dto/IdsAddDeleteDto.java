package kz.kazgisa.data.dto;

import java.util.List;

public class IdsAddDeleteDto {
    private List<Long> add;
    private List<Long> delete;

    public List<Long> getAdd() {
        return add;
    }

    public void setAdd(List<Long> add) {
        this.add = add;
    }

    public List<Long> getDelete() {
        return delete;
    }

    public void setDelete(List<Long> delete) {
        this.delete = delete;
    }
}
