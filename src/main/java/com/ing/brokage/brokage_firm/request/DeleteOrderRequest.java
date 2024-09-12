package com.ing.brokage.brokage_firm.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ing.brokage.brokage_firm.model.OrderId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeleteOrderRequest {
    private String id;

    private String customerId;

    private String assetName;

    @JsonIgnore
    public OrderId toOrderId(){
        return new OrderId(this.getId(), this.getCustomerId(), this.getAssetName());
    }
}
