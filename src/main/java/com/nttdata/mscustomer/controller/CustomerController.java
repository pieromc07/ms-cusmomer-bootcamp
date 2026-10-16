package com.nttdata.mscustomer.controller;


import com.nttdata.mscustomer.generated.api.CustomersApi;
import com.nttdata.mscustomer.generated.dto.CreateCustomerRequest;
import com.nttdata.mscustomer.generated.dto.CreatePersonalCustomerRequest;
import com.nttdata.mscustomer.generated.dto.Customer;
import com.nttdata.mscustomer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class CustomerController implements CustomersApi {


    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


   /*  @Override
    public Mono<ResponseEntity<Customer>>
    createCustomer(
            Mono<CreateCustomerRequest> createCustomerRequest,
            ServerWebExchange exchange) {

       return createCustomerRequest
                .flatMap(request -> {
                    // Próximo paso: convertir el DTO al modelo de dominio.
                    if(request instanceof CreatePersonalCustomerRequest personalRequest) {
                        return customerService.createCustomer(personalRequest)
                                .map(customer -> ResponseEntity.ok().body(customer));
                    }
                    return Mono.empty();
                });
    }*/

}
