package com.nttdata.mscustomer.service;

import com.nttdata.mscustomer.document.CustomerDocument;
import com.nttdata.mscustomer.exception.DuplicateCustomerException;
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
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
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

            return customerRepository.save(toDocument(customer))
                    .map(this::toDomain);
        });
    }

    private Customer toDomain(
            CreatePersonalCustomerRequest request
    ) {
        PersonalDetails personalDetails = new PersonalDetails(
                request.getPersonalDetails().getFirstName(),
                request.getPersonalDetails().getLastName()
        );

        return new Customer(
                CustomerType.PERSONAL,
                CustomerProfile.valueOf(request.getProfile().getValue()),
                DocumentType.valueOf(request.getDocumentType().getValue()),
                request.getDocumentNumber(),
                request.getEmail(),
                request.getPhone(),
                personalDetails,
                null
        );
    }

    private Customer toDomain(
            CreateBusinessCustomerRequest request
    ){
        BusinessDetails businessDetails = new BusinessDetails(
                request.getBusinessDetails().getLegalName(),
                request.getBusinessDetails().getTradeName()
        );

        return new Customer(
                CustomerType.BUSINESS,
                CustomerProfile.valueOf(request.getProfile().getValue()),
                DocumentType.valueOf(request.getDocumentType().getValue()),
                request.getDocumentNumber(),
                request.getEmail(),
                request.getPhone(),
                null,
                businessDetails
        );
    }

    private CustomerDocument toDocument(
            Customer customer
    ){
        CustomerDocument document = new CustomerDocument();
        document.setId(customer.getId());
        document.setCustomerType(customer.getCustomerType());
        document.setProfile(customer.getProfile());
        document.setDocumentType(customer.getDocumentType());
        document.setDocumentNumber(customer.getDocumentNumber());
        document.setEmail(customer.getEmail());
        document.setPhone(customer.getPhone());
        document.setStatus(customer.getStatus());
        document.setPersonalDetails(customer.getPersonalDetails());
        document.setBusinessDetails(customer.getBusinessDetails());
        document.setCreatedAt(customer.getCreatedAt());
        document.setUpdatedAt(customer.getUpdatedAt());
        return document;
    }

    private Customer toDomain(
            CustomerDocument document
    ){
        return new Customer(
                document.getId(),
                document.getCustomerType(),
                document.getProfile(),
                document.getDocumentType(),
                document.getDocumentNumber(),
                document.getEmail(),
                document.getPhone(),
                document.getStatus(),
                document.getPersonalDetails(),
                document.getBusinessDetails(),
                document.getCreatedAt(),
                document.getUpdatedAt()
        );
    }
}
