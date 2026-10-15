package com.nttdata.mscustomer.service;

import com.nttdata.mscustomer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import com.nttdata.mscustomer.model.BusinessDetails;
import com.nttdata.mscustomer.model.Customer;
import com.nttdata.mscustomer.model.CustomerProfile;
import com.nttdata.mscustomer.model.CustomerStatus;
import com.nttdata.mscustomer.model.CustomerType;
import com.nttdata.mscustomer.model.DocumentType;
import com.nttdata.mscustomer.model.PersonalDetails;

import com.nttdata.mscustomer.generated.dto.CreateBusinessCustomerRequest;
import com.nttdata.mscustomer.generated.dto.CreatePersonalCustomerRequest;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private Customer toDomain(
            CreatePersonalCustomerRequest request
    ) {
        PersonalDetails personalDetails = new PersonalDetails(
                request.getPersonalDetails().getFirstName(),
                request.getPersonalDetails().getLastName()
        );

        return new Customer(
                null,
                CustomerType.PERSONAL,
                CustomerProfile.valueOf(request.getProfile().getValue()),
                DocumentType.valueOf(request.getDocumentType().getValue()),
                request.getDocumentNumber(),
                request.getEmail(),
                request.getPhone(),
                CustomerStatus.ACTIVE,
                personalDetails,
                null,
                null,
                null
        );
    }
}
