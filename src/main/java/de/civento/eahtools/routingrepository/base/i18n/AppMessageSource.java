package de.civento.eahtools.routingrepository.base.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AppMessageSource {
    private final MessageSource messageSource;

    public AppMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String code, String... vars) {
        Object[] args = new Object[vars.length];
        System.arraycopy(vars, 0, args, 0, vars.length);
        return this.messageSource.getMessage(code,
                args,
                LocaleContextHolder.getLocale());
    }
}
