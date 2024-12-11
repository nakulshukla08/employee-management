package com.tech.employee_management.profile.outbound.sync;

import com.tech.employee_management.api.payroll.CreatePayrollRequest;
import com.tech.employee_management.api.payroll.GetPayrollRequest;
import com.tech.employee_management.api.payroll.GetPayrollResponse;
import com.tech.employee_management.api.payroll.PayrollApi;
import com.tech.employee_management.domain.payroll.Payroll;
import com.tech.employee_management.profile.outbound.model.CreatePayroll;
import com.tech.employee_management.profile.outbound.model.PayrollApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class PayrollApiHttpImpl implements PayrollApi {
    @Autowired
    public PayrollApiHttpImpl(WebClient.Builder webClientBuilder, String payrollServiceURl) {
        this.webclient = webClientBuilder.baseUrl(payrollServiceURl).build();
    }
    private final WebClient webclient;
    @Override
    public GetPayrollResponse getPayroll(GetPayrollRequest request) {

        Mono<PayrollApiResponse> response = webclient.get()
                .uri("/"+ request.getEmployeeId())
                .retrieve()
                .bodyToMono(PayrollApiResponse.class);

        return reverseMap(response.block());
    }
    @Override
    public void createPayroll(CreatePayrollRequest request) {

        CreatePayroll payroll = CreatePayroll.builder()
                .employeeId(request.getEmployeeId())
                .grossSalary(request.getGrossSalary())
                .build();
        ClientResponse response = webclient.post()
                .uri("/"+ request.getEmployeeId())
                .bodyValue(payroll)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().is2xxSuccessful()) {
                        return Mono.just(clientResponse);
                    } else {
                        return Mono.error(new RuntimeException("Failed to create payroll: " + clientResponse.statusCode()));
                    }
                })
                .block(); // Block for synchronous example

        // Log or process the response
        if (response != null && response.statusCode().is2xxSuccessful()) {
            log.info("Payroll created successfully.");
        } else {
            log.info("Payroll creation failed.");
        }
    }
    private GetPayrollResponse reverseMap(PayrollApiResponse input) {
        GetPayrollResponse response = new GetPayrollResponse();
        if(input != null)
        {
            response.setEmployeeId(input.getEmployeeId());
            response.setPayroll(new Payroll(input.getSalary()));
        }
        return response;
    }
}
