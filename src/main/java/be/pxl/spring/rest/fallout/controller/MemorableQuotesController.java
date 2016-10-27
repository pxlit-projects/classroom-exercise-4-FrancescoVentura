package be.pxl.spring.rest.fallout.controller;

import be.pxl.spring.rest.fallout.entity.Quote;
import be.pxl.spring.rest.fallout.quote.QuoteAssembler;
import be.pxl.spring.rest.fallout.quote.QuoteR;
import be.pxl.spring.rest.fallout.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(MemorableQuotesController.QUOTE_BASE_URL)
@RestController
public class MemorableQuotesController {

    public static final String QUOTE_BASE_URL = "/quote";

    public MemorableQuotesController() {
    }

    @Autowired
    private QuoteRepository quoteRepository;
    //@Autowired
    private QuoteAssembler quoteAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public List<QuoteR> all() {
        List<Quote> quotes = quoteRepository.findAll();
        List<QuoteR> quoteRs = new ArrayList<>();
        for (Quote quote: quotes) {
           quoteRs.add(QuoteR.of(quote.getId().toString(),quote.getAuthor(),quote.getQuotation()));
        }
        return quoteRs;
    }

    @RequestMapping(method = RequestMethod.GET, params = {"author"})
    public List<QuoteR> byAuthor(@RequestParam("author") String author) {
        List<Quote> quotes = quoteRepository.findByAuthor(author);
        List<QuoteR> quoteRs = new ArrayList<>();
        for (Quote quote: quotes) {
            quoteRs.add(QuoteR.of(quote.getId().toString(),quote.getAuthor(),quote.getQuotation()));
        }
        return quoteRs;
    }

    @RequestMapping(method = RequestMethod.POST, produces="application/json",headers="Accept=application/json")
    public ResponseEntity addQuote(@RequestBody QuoteR newQuoteR){
        Quote quote = new Quote(newQuoteR.getAuthor(),newQuoteR.getQuote());
        quoteRepository.save(quote);
        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("location", quote.getId().toString());
        return new ResponseEntity<Quote>(quote, httpHeaders,HttpStatus.CREATED);
    }
}
