package com.ing.brokerage.brokerage_firm.service;

import com.ing.brokerage.brokerage_firm.model.Customer;
import com.ing.brokerage.brokerage_firm.repository.CustomerRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignupCustomer(){
        String username = "testuser";
        String password = "123456";

        when(customerRepository.save(any(Customer.class))).thenAnswer(i -> i.getArgument(0));
        Customer result = customerService.signupCustomer(username, password);

        assertNotNull(result);
        assertEquals(username, result.getUserName());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }
}
