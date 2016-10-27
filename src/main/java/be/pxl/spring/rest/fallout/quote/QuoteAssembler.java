package be.pxl.spring.rest.fallout.quote;

import be.pxl.spring.rest.fallout.entity.Quote;

// Example of not using @Service or @Component, see be.pxl.spring.rest.fallout.Application for this class' configuration
public class QuoteAssembler {
    public QuoteR toRepresentation(Quote quote) {
        return QuoteR.of(quote.getId().toString(), quote.getAuthor(), quote.getQuotation());
    }
}
