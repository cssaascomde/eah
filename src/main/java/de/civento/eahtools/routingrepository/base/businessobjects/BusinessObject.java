package de.civento.eahtools.routingrepository.base.businessobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BusinessObject implements Serializable{
    private String id;
    private LocalDateTime sysModifiedAt;
    private String sysModifiedBy;
    private int sysVersion;
}