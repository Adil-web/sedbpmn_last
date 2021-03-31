package kz.kazgisa.data.dto;

import java.util.List;
import java.util.Map;

public class LayersPageDto {
    private List<Map<String, Object>> layers;
    private Integer count;

    public List<Map<String, Object>> getLayers() {
        return layers;
    }

    public void setLayers(List<Map<String, Object>> layers) {
        this.layers = layers;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
