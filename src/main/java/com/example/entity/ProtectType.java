package com.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by yfmacmini001 on 2017/2/6.
 */
@Document(collection = "protectType")
public class ProtectType implements Serializable{

    @Id
    private String id;
    private String name;
    private BigDecimal price;
    private String suggestCateName;
    private String description;
    private String compensateStandard;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSuggestCateName() {
        return suggestCateName;
    }

    public void setSuggestCateName(String suggestCateName) {
        this.suggestCateName = suggestCateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompensateStandard() {
        return compensateStandard;
    }

    public void setCompensateStandard(String compensateStandard) {
        this.compensateStandard = compensateStandard;
    }
}
