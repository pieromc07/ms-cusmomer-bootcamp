
package com.nttdata.mscustomer.document;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.nttdata.mscustomer.model.BusinessDetails;
import com.nttdata.mscustomer.model.CustomerProfile;
import com.nttdata.mscustomer.model.CustomerStatus;
import com.nttdata.mscustomer.model.CustomerType;
import com.nttdata.mscustomer.model.DocumentType;
import com.nttdata.mscustomer.model.PersonalDetails;

import lombok.Data;

@Data
@Document(collection = "customers")
public class CustomerDocument {

    @Id
    private String id;

    private CustomerType customerType;
    private CustomerProfile profile;
    private DocumentType documentType;
    private String documentNumber;
    private String email;
    private String phone;
    private CustomerStatus status;
    private PersonalDetails personalDetails;
    private BusinessDetails businessDetails;
    private Instant createdAt;
    private Instant updatedAt;
}
