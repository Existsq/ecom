package ru.exist.ecom.core.application.customer.mapper;

import ru.exist.ecom.core.application.customer.CustomerDTO;
import ru.exist.ecom.core.domain.customer.Customer;

public class CustomerMapper {
  public static CustomerDTO toDto(Customer customer) {
    return new CustomerDTO(
        customer.getId(), customer.getName(), customer.getEmail(), customer.getPhoneNumber());
  }

  public static Customer toEntity(CustomerDTO dto) {
    Customer customer = new Customer();
    customer.setId(dto.id());
    customer.setName(dto.name());
    customer.setEmail(dto.email());
    customer.setPhoneNumber(dto.phoneNumber());

    return customer;
  }
}
