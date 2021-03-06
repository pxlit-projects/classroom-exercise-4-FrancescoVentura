package be.pxl.spring.rest.fallout.quote;

import be.pxl.spring.rest.fallout.Application;
import be.pxl.spring.rest.fallout.entity.Quote;
import be.pxl.spring.rest.fallout.repository.QuoteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static be.pxl.spring.rest.fallout.quote.QuoteTestBuilder.aQuote;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class QuoteRepositoryIntegrationTest {

    @Autowired
    private QuoteRepository quoteRepository;

    @Before
    public void setUp() throws Exception {
        quoteRepository.deleteAll();
    }

    @Test
    public void findByAuthor_OnlyReturnsQuotesByGivenAuthor() throws Exception {
        Quote piperQuote1 = aQuote().withAuthor("Piper").withQuotation("quotation").build();
        Quote piperQuote2 = aQuote().withAuthor("Piper").withQuotation("quotation3").build();
        Quote otherQuote = aQuote().withAuthor("Jamie").withQuotation("quotation2").build();
        quoteRepository.save(Arrays.asList(piperQuote1, piperQuote2, otherQuote));

        List<Quote> quotes = quoteRepository.findByAuthor("Piper");

        assertThat(quotes).extracting(Quote::getQuotation).containsOnly("quotation", "quotation3");
    }

    @Test
    public void findByQuotation_OnlyReturnsQuotesByMatchingQuotes() throws Exception {
        // copy pasta
        Quote piperQuote1 = aQuote().withAuthor("Piper").withQuotation("quotation").build();
        Quote piperQuote2 = aQuote().withAuthor("Piper").withQuotation("quotation3").build();
        Quote otherQuote = aQuote().withAuthor("Jamie").withQuotation("quotation2").build();
        quoteRepository.save(Arrays.asList(piperQuote1, piperQuote2, otherQuote));

        List<Quote> quotes = quoteRepository.findByQuotation("quotation");

        assertThat(quotes.stream().anyMatch(s -> s.equals(piperQuote1))).isTrue();
    }
}