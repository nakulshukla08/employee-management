package com.tech.employee_management.profile.outbound.sync;

import com.tech.employee_management.api.payroll.CreatePayrollRequest;
import com.tech.employee_management.api.payroll.GetPayrollRequest;
import com.tech.employee_management.api.payroll.GetPayrollResponse;
import com.tech.employee_management.api.payroll.PayrollApi;
import com.tech.employee_management.domain.payroll.Payroll;
import com.tech.employee_management.profile.outbound.model.PayrollApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class PayrolllApiHttpImpl implements PayrollApi {
    @Autowired
    public PayrolllApiHttpImpl(WebClient.Builder webClientBuilder, String payrollServiceURl) {
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
        //Noop
    }
    private GetPayrollResponse reverseMap(PayrollApiResponse input) {
        GetPayrollResponse response = new GetPayrollResponse();
        response.setEmployeeId(input.getEmployeeId());
        response.setPayroll(new Payroll(input.getSalary()));
        return response;
    }
}
