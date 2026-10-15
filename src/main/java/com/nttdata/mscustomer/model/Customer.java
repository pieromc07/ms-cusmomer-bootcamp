package com.nttdata.mscustomer.model;

import lombok.Data;
import java.time.Instant;

@Data
public class Customer {

    private final String id;
    private final CustomerType customerType;
    private CustomerProfile profile;
    private DocumentType documentType;
    private String documentNumber;
    private String email;
    private String phone;
    private CustomerStatus status;
    private final PersonalDetails personalDetails;
    private final BusinessDetails businessDetails;
    private final Instant createdAt;
    private Instant updatedAt;

    public Customer(
            String id,
            CustomerType customerType,
            CustomerProfile profile,
            DocumentType documentType,
            String documentNumber,
            String email,
            String phone,
            CustomerStatus status,
            PersonalDetails personalDetails,
            BusinessDetails businessDetails,
            Instant createdAt,
            Instant updatedAt
    ) {
        if (customerType == null) {
            throw new IllegalArgumentException("Customer type is required");
        }

        if (customerType == CustomerType.PERSONAL
                && (personalDetails == null || businessDetails != null)) {
            throw new IllegalArgumentException(
                    "A PERSONAL customer must have personalDetails and no businessDetails"
            );
        }

        if (customerType == CustomerType.BUSINESS
                && (businessDetails == null || personalDetails != null)) {
            throw new IllegalArgumentException(
                    "A BUSINESS customer must have businessDetails and no personalDetails"
            );
        }

        this.id = id;
        this.customerType = customerType;
        this.profile = profile;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.personalDetails = personalDetails;
        this.businessDetails = businessDetails;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
