package be.pxl.spring.rest.fallout.repository;



import be.pxl.spring.rest.fallout.entity.Quote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuoteRepository extends CrudRepository<Quote,UUID> {
    List<Quote> findByAuthor(String author);

    List<Quote> findByQuotation(String Quote);
}
