package de.civento.eahtools.routingrepository.ui.responsibility;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AutoCompleteResult {
    private String id;
    private String text;
}
