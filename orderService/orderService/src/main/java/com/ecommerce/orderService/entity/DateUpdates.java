package com.ecommerce.orderService.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@MappedSuperclass
public class DateUpdates {
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @PrePersist
    protected void onCreate(){
        this.creationTime = new Date();
        this.updateTime = new Date();
    }

    @PreUpdate
    protected  void onUpdate(){
        this.updateTime = new Date();
    }
}
