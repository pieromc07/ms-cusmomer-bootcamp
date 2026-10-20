package com.nttdata.mscustomer.mapper;

import com.nttdata.mscustomer.document.CustomerDocument;
import com.nttdata.mscustomer.generated.dto.BusinessDetails;
import com.nttdata.mscustomer.generated.dto.CreateCustomerRequest;
import com.nttdata.mscustomer.generated.dto.Customer;
import com.nttdata.mscustomer.generated.dto.CustomerStatus;
import com.nttdata.mscustomer.generated.dto.PersonalDetails;
import com.nttdata.mscustomer.model.CustomerProfile;
import com.nttdata.mscustomer.model.CustomerType;
import com.nttdata.mscustomer.model.DocumentType;
import java.time.ZoneOffset;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public com.nttdata.mscustomer.model.Customer toDomain(
            CreateCustomerRequest request) {
        if (request.getCustomerType() == null
                || request.getDocumentType() == null
                || request.getProfile() == null) {
            throw new IllegalArgumentException(
                    "Customer type, document type and profile are required");
        }

        CustomerType customerType = CustomerType.valueOf(
                request.getCustomerType().getValue());
        if (customerType == CustomerType.PERSONAL) {
            if (request.getPersonalDetails() == null) {
                throw new IllegalArgumentException(
                        "Personal details are required for a personal customer");
            }
        } else if (request.getBusinessDetails() == null) {
            throw new IllegalArgumentException(
                    "Business details are required for a business customer");
        }

        return new com.nttdata.mscustomer.model.Customer(
                customerType,
                CustomerProfile.valueOf(request.getProfile().getValue()),
                DocumentType.valueOf(request.getDocumentType().getValue()),
                request.getDocumentNumber(),
                request.getEmail(),
                request.getPhone(),
                customerType == CustomerType.PERSONAL
                        ? new com.nttdata.mscustomer.model.PersonalDetails(
                                request.getPersonalDetails().getFirstName(),
                                request.getPersonalDetails().getLastName())
                        : null,
                customerType == CustomerType.BUSINESS
                        ? new com.nttdata.mscustomer.model.BusinessDetails(
                                request.getBusinessDetails().getLegalName(),
                                request.getBusinessDetails().getTradeName())
                        : null);
    }

    public CustomerDocument toDocument(
            com.nttdata.mscustomer.model.Customer customer) {
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

    public com.nttdata.mscustomer.model.Customer toDomain(
            CustomerDocument document) {
        return new com.nttdata.mscustomer.model.Customer(
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
                document.getUpdatedAt());
    }

    public Customer toResponse(
            com.nttdata.mscustomer.model.Customer customer) {
        Customer response = new Customer();
        response.setId(customer.getId());
        response.setCustomerType(Customer.CustomerTypeEnum
                .valueOf(customer.getCustomerType().name()));
        response.setDocumentType(Customer.DocumentTypeEnum
                .valueOf(customer.getDocumentType().name()));
        response.setDocumentNumber(customer.getDocumentNumber());
        response.setEmail(customer.getEmail());
        response.setPhone(customer.getPhone());
        response.setProfile(Customer.ProfileEnum
                .valueOf(customer.getProfile().name()));
        response.setStatus(CustomerStatus.valueOf(customer.getStatus().name()));
        response.setCreatedAt(customer.getCreatedAt().atOffset(ZoneOffset.UTC));
        response.setUpdatedAt(customer.getUpdatedAt().atOffset(ZoneOffset.UTC));

        if (customer.getCustomerType() == CustomerType.PERSONAL) {
            response.setPersonalDetails(new PersonalDetails(
                    customer.getPersonalDetails().firstName(),
                    customer.getPersonalDetails().lastName()));
        } else {
            BusinessDetails businessDetails = new BusinessDetails(
                    customer.getBusinessDetails().legalName());
            businessDetails.setTradeName(customer.getBusinessDetails().tradeName());
            response.setBusinessDetails(businessDetails);
        }
        return response;
    }
}
