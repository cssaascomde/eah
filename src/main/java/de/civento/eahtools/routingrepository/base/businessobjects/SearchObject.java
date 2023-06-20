package de.civento.eahtools.routingrepository.base.businessobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class SearchObject implements Serializable{
    private int pageNumber = 0;
    private int pageSize = 10;
    private String sortBy;
    private boolean ascending = true;
    private boolean withoutPaging = false;
}