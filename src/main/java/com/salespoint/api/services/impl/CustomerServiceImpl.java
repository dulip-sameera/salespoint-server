package com.salespoint.api.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salespoint.api.dtos.CustomerDto;
import com.salespoint.api.entities.CustomerEntity;
import com.salespoint.api.entities.CustomerStatusEntity;
import com.salespoint.api.exceptions.customs.customer.CustomerAlreadyExistsException;
import com.salespoint.api.exceptions.customs.customer.CustomerNotFoundException;
import com.salespoint.api.exceptions.customs.customer.CustomerStatusNotFoundException;
import com.salespoint.api.repositories.CustomerRepository;
import com.salespoint.api.repositories.CustomerStatusRepository;
import com.salespoint.api.services.CustomerService;
import com.salespoint.api.utils.enums.CustomerStatusEnum;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerStatusRepository customerStatusRepository;

    @Override
    public Iterable<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public CustomerEntity getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException());
    }

    @Override
    public CustomerEntity getCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone)
                .orElseThrow(() -> new CustomerNotFoundException());
    }

    @Override
    public CustomerEntity createCustomer(CustomerDto customerDto) {
        CustomerStatusEntity status = customerStatusRepository.findByName(CustomerStatusEnum.ACTIVE)
                .orElseThrow(() -> new CustomerStatusNotFoundException());

        Optional<CustomerEntity> optionalCustomer = customerRepository.findByPhone(customerDto.getPhone());

        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException();
        }

        CustomerEntity createdCustomer = CustomerEntity
                .builder()
                .fullName(customerDto.getFullName())
                .phone(customerDto.getPhone())
                .customerStatus(status)
                .build();

        return customerRepository.save(createdCustomer);
    }

    @Override
    public CustomerEntity updateCustomer(Long id, CustomerDto customerDto) {
        CustomerEntity existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException());

        CustomerStatusEnum statusEnum = customerDto.getStatus().equals(CustomerStatusEnum.ACTIVE.toString())
                ? CustomerStatusEnum.ACTIVE
                : CustomerStatusEnum.DELETE;

        CustomerStatusEntity status = customerStatusRepository.findByName(statusEnum)
                .orElseThrow(() -> new CustomerStatusNotFoundException());

        existingCustomer.setFullName(customerDto.getFullName());
        existingCustomer.setPhone(customerDto.getPhone());
        existingCustomer.setCustomerStatus(status);

        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        CustomerEntity existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException());

        CustomerStatusEntity status = customerStatusRepository.findByName(CustomerStatusEnum.DELETE)
                .orElseThrow(() -> new CustomerStatusNotFoundException());

        existingCustomer.setCustomerStatus(status);
        customerRepository.save(existingCustomer);
    }

}
