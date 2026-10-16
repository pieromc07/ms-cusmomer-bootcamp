package com.nttdata.mscustomer.repository;

import com.nttdata.mscustomer.document.CustomerDocument;
import com.nttdata.mscustomer.model.DocumentType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<CustomerDocument, String> {

    Mono<Boolean> existsByDocumentTypeAndDocumentNumber(
            DocumentType documentType,
            String documentNumber
    );
}
