package com.nttdata.mscustomer.repository;

import com.nttdata.mscustomer.document.CustomerDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<CustomerDocument, String> {
}
