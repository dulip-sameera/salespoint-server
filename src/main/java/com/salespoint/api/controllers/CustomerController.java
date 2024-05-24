package com.salespoint.api.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salespoint.api.dtos.CustomerDto;
import com.salespoint.api.entities.CustomerEntity;
import com.salespoint.api.exceptions.customs.customer.CustomerNameInvalidException;
import com.salespoint.api.exceptions.customs.customer.CustomerPhoneNumberInvalidException;
import com.salespoint.api.services.CustomerService;
import com.salespoint.api.utils.enums.CustomerStatusEnum;
import com.salespoint.api.utils.response.CustomerResponse;

@CrossOrigin("*")
@RestController
@RequestMapping("/customers")
public class CustomerController {
    private int ACCEPTABLE_MINIMUM_NAME_LENGTH = 3;
    private String SRI_LANKAN_MOBILE_PHONE_NUMBER_REGEX_PATTERN = "^[0]{1}[7]{1}[01245678]{1}[0-9]{7}$";

    @Autowired
    private CustomerService customerService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        Iterable<CustomerEntity> allCustomers = customerService.getAllCustomers();
        List<CustomerResponse> customerResponses = new ArrayList<>();
        allCustomers.forEach((customer) -> {

            CustomerResponse response = CustomerResponse
                    .builder()
                    .id(customer.getId())
                    .fullName(customer.getFullName())
                    .phone(customer.getPhone())
                    .status(customer.getCustomerStatus().getName().name())
                    .build();

            customerResponses.add(response);

        });

        return ResponseEntity.ok(customerResponses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        CustomerEntity customer = customerService.getCustomerById(id);
        CustomerResponse customerResponse = CustomerResponse
                .builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .phone(customer.getPhone())
                .status(customer.getCustomerStatus().getName().name())
                .build();

        return ResponseEntity.ok(customerResponse);

    }

    @GetMapping("/phone/{phone}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<CustomerResponse> getCustomerByPhone(@PathVariable String phone) {
        CustomerEntity customer = customerService.getCustomerByPhone(phone);

        CustomerResponse customerResponse = CustomerResponse
                .builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .phone(customer.getPhone())
                .status(customer.getCustomerStatus().getName().name())
                .build();

        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping("/statuses")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<List<String>> getAllStatuses() {
        List<String> statuses = new ArrayList<>();

        for (CustomerStatusEnum statusEnum : CustomerStatusEnum.values()) {
            statuses.add(statusEnum.name());
        }

        return ResponseEntity.ok(statuses);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerDto customerDto) {
        // If exists, remove leading and trailing white spaces
        customerDto.setFullName(customerDto.getFullName().trim());
        customerDto.setPhone(customerDto.getPhone().trim());

        // validations
        if (!Pattern.matches(SRI_LANKAN_MOBILE_PHONE_NUMBER_REGEX_PATTERN, customerDto.getPhone())) {
            throw new CustomerPhoneNumberInvalidException();
        }

        if (customerDto.getFullName().length() < ACCEPTABLE_MINIMUM_NAME_LENGTH) {
            throw new CustomerNameInvalidException("Full Name is less than " + ACCEPTABLE_MINIMUM_NAME_LENGTH);
        }

        CustomerEntity createdCustomer = customerService.createCustomer(customerDto);

        CustomerResponse response = CustomerResponse
                .builder()
                .fullName(createdCustomer.getFullName())
                .phone(createdCustomer.getPhone())
                .status(createdCustomer.getCustomerStatus().getName().name())
                .id(createdCustomer.getId())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK','CASHIER')")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id,
            @RequestBody CustomerDto customerDto) {
        // If exists, remove leading and trailing white spaces
        customerDto.setFullName(customerDto.getFullName().trim());
        customerDto.setPhone(customerDto.getPhone().trim());

        // validations
        if (!Pattern.matches(SRI_LANKAN_MOBILE_PHONE_NUMBER_REGEX_PATTERN, customerDto.getPhone())) {
            throw new CustomerPhoneNumberInvalidException();
        }

        if (customerDto.getFullName().length() < ACCEPTABLE_MINIMUM_NAME_LENGTH) {
            throw new CustomerNameInvalidException("Full Name is less than " + ACCEPTABLE_MINIMUM_NAME_LENGTH);
        }

        CustomerEntity updatedCustomer = customerService.updateCustomer(id, customerDto);

        CustomerResponse customerResponse = CustomerResponse
                .builder()
                .id(updatedCustomer.getId())
                .phone(updatedCustomer.getPhone())
                .fullName(updatedCustomer.getFullName())
                .status(updatedCustomer.getCustomerStatus().getName().toString())
                .build();

        return ResponseEntity.ok(customerResponse);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CLERK',)")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Customer Deleted");
    }

}
