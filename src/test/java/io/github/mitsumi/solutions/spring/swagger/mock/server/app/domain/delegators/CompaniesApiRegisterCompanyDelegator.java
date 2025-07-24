package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.delegators;

import io.github.mitsumi.solutions.spring.swagger.mock.server.app.test.models.CompanyInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@TestComponent
public class CompaniesApiRegisterCompanyDelegator implements ResponseEntityDelegator {

    @Override
    public ResponseEntity<Object> delegate(HttpServletRequest request,
                                           Map<Integer, Class<?>> responseBodyTypes,
                                           Map<String, Object> parameters) {
        return ResponseEntity.ok(createCompanyInfo());
    }

    private CompanyInfo createCompanyInfo() {
        return new CompanyInfo().id(20250724).name("Mitsumi").address("Mitsumi solutions building");
    }
}
