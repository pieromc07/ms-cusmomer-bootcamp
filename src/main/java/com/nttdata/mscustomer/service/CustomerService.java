package com.nttdata.mscustomer.service;

import com.nttdata.mscustomer.exception.DuplicateCustomerException;
import com.nttdata.mscustomer.mapper.CustomerMapper;
import com.nttdata.mscustomer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import com.nttdata.mscustomer.model.Customer;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(
            CustomerRepository customerRepository,
            CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public Mono<Customer> createCustomer(Customer customer) {
        return customerRepository.existsByDocumentTypeAndDocumentNumber(
                customer.getDocumentType(),
                customer.getDocumentNumber()
        ).flatMap(exists -> {
            if (exists) {
                return Mono.error(new DuplicateCustomerException(
                        "Customer with document type "
                                + customer.getDocumentType()
                                + " and document number "
                                + customer.getDocumentNumber()
                                + " already exists"
                ));
            }

            return customerRepository.save(customerMapper.toDocument(customer))
                    .map(customerMapper::toDomain);
        });
    }
}
