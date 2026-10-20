package com.nttdata.mscustomer.controller;

import com.nttdata.mscustomer.generated.api.CustomersApi;
import com.nttdata.mscustomer.generated.dto.CreateCustomerRequest;
import com.nttdata.mscustomer.generated.dto.Customer;
import com.nttdata.mscustomer.mapper.CustomerMapper;
import com.nttdata.mscustomer.service.CustomerService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class CustomerController implements CustomersApi {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public CustomerController(
            CustomerService customerService,
            CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @Override
    public Mono<ResponseEntity<Customer>> createCustomer(
            Mono<CreateCustomerRequest> createCustomerRequest,
            ServerWebExchange exchange) {

        return createCustomerRequest
                .map(customerMapper::toDomain)
                .flatMap(customerService::createCustomer)
                .map(customerMapper::toResponse)
                .map(response -> ResponseEntity
                        .created(URI.create("/api/v1/customers/" + response.getId()))
                        .body(response));
    }

}
