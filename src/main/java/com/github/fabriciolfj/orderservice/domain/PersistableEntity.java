package com.github.fabriciolfj.orderservice.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

@Getter
@Setter
public class PersistableEntity {

    @Id
    private Long id;

    @CreatedDate
    private Long createdData;

    @LastModifiedDate
    private Long lastModifiedDate;

    @Version
    private int version;
}
