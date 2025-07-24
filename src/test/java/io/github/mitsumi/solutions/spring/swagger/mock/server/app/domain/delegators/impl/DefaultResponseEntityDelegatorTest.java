package io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.delegators.impl;

import io.github.mitsumi.solutions.spring.swagger.mock.server.app.TestSpringSwaggerMockServerApplication;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.delegators.CompaniesApiRegisterCompanyDelegator;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.test.models.Companies;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.test.models.Company;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.test.models.CompanyInfo;
import io.github.mitsumi.solutions.spring.swagger.mock.server.app.test.models.ErrorResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = TestSpringSwaggerMockServerApplication.class, properties = {
    "spring.swagger.mock-server.config-file=./src/test/resources/test-data/unit-test/io.github.mitsumi.solutions.spring.swagger.mock.server.app.domain.delegators.impl.DefaultResponseEntityDelegatorTest/mock-server.config.json"
})
@ActiveProfiles("unit-test")
@Import(CompaniesApiRegisterCompanyDelegator.class)
public class DefaultResponseEntityDelegatorTest {

    @Autowired
    private DefaultResponseEntityDelegator delegator;

    private MockHttpServletRequest request;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @ParameterizedTest
    @MethodSource("test_getCompanies_ok_parameter_provider")
    public void test_getCompanies_ok(Map<Integer, Class<?>> responseBodyTypes, Map<String, Object> parameters) {
        request = new MockHttpServletRequest("get", requestUri("/companies"));

        var actual = delegator.delegate(request, responseBodyTypes, parameters);

        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.getBody(), instanceOf(Companies.class));

        var actualBody = (Companies) actual.getBody();

        Assertions.assertNotNull(actualBody);
        assertThat(actualBody.getCount(), is(2));
        assertThat(actualBody.getResults().size(), is(2));

        var actualCompany = actualBody.getResults().getFirst();
        assertThat(actualCompany.getId(), is(1));
        assertThat(actualCompany.getName(), is("test company 1"));
        assertThat(actualCompany.getAddress(), is("test address 1"));
    }

    @ParameterizedTest
    @MethodSource("test_getCompanies_badRequest_parameter_provider")
    public void test_getCompanies_badRequest(Map<Integer, Class<?>> responseBodyTypes, Map<String, Object> parameters) {
        request = new MockHttpServletRequest("get", requestUri("/companies"));

        var actual = delegator.delegate(request, responseBodyTypes, parameters);

        assertThat(actual.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(actual.getBody(), instanceOf(ErrorResponse.class));

        var actualBody = (ErrorResponse) actual.getBody();

        Assertions.assertNotNull(actualBody);
        assertThat(actualBody.getErrorCode(), is("400"));
        assertThat(actualBody.getErrorMessage(), is("bad request"));
    }


    @ParameterizedTest
    @MethodSource("test_registerCompany_ok_parameter_provider")
    public void test_registerCompany_ok(Map<Integer, Class<?>> responseBodyTypes, Map<String, Object> parameters) {
        request = new MockHttpServletRequest("post", requestUri("/companies"));

        var actual = delegator.delegate(request, responseBodyTypes, parameters);

        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.getBody(), instanceOf(CompanyInfo.class));

        var actualBody = (CompanyInfo) actual.getBody();

        Assertions.assertNotNull(actualBody);
        assertThat(actualBody.getId(), is(20250724));
        assertThat(actualBody.getName(), is("Mitsumi"));
        assertThat(actualBody.getAddress(), is("Mitsumi solutions building"));
    }


    @ParameterizedTest
    @MethodSource("test_getCompany_ok_parameter_provider")
    public void test_getCompany_ok(Map<Integer, Class<?>> responseBodyTypes, Map<String, Object> parameters) {
        request = new MockHttpServletRequest("get", requestUri("/companies/1"));

        var actual = delegator.delegate(request, responseBodyTypes, parameters);

        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.getBody(), instanceOf(CompanyInfo.class));

        var actualBody = (CompanyInfo) actual.getBody();

        Assertions.assertNotNull(actualBody);
        assertThat(actualBody.getId(), is(1));
        assertThat(actualBody.getName(), is("test company 1"));
        assertThat(actualBody.getAddress(), is("test company address 1"));

    }

    @ParameterizedTest
    @MethodSource("test_getCompany_defaultOk_parameter_provider")
    public void test_getCompany_defaultOk(Map<Integer, Class<?>> responseBodyTypes, Map<String, Object> parameters) {
        request = new MockHttpServletRequest("get", requestUri("/companies/3"));

        var actual = delegator.delegate(request, responseBodyTypes, parameters);

        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.getBody(), instanceOf(CompanyInfo.class));

        var actualBody = (CompanyInfo) actual.getBody();

        Assertions.assertNotNull(actualBody);
        assertThat(actualBody.getId(), is(1));
        assertThat(actualBody.getName(), is("default company"));
        assertThat(actualBody.getAddress(), is("default company address"));
    }

    @ParameterizedTest
    @MethodSource("test_deleteCompany_ok_parameter_provider")
    public void test_deleteCompany_ok(Map<Integer, Class<?>> responseBodyTypes, Map<String, Object> parameters) {
        request = new MockHttpServletRequest("delete", requestUri("/companies/1"));

        var actual = delegator.delegate(request, responseBodyTypes, parameters);

        assertThat(actual.getStatusCode(), is(HttpStatus.OK));
        assertThat(actual.getBody(), nullValue());
    }

    @ParameterizedTest
    @MethodSource("test_deleteCompany_badRequest_parameter_provider")
    public void test_deleteCompany_badRequest(Map<Integer, Class<?>> responseBodyTypes, Map<String, Object> parameters) {
        request = new MockHttpServletRequest("delete", requestUri("/companies/2"));

        var actual = delegator.delegate(request, responseBodyTypes, parameters);

        assertThat(actual.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(actual.getBody(), instanceOf(ErrorResponse.class));

        var actualBody = (ErrorResponse) actual.getBody();

        Assertions.assertNotNull(actualBody);
        assertThat(actualBody.getErrorCode(), is("400"));
        assertThat(actualBody.getErrorMessage(), is("bad request"));
    }

    private String requestUri(String path) {
        return contextPath + path;
    }

    static Stream<Arguments> test_getCompanies_ok_parameter_provider() {
        return Stream.of(
            Arguments.arguments(
                Map.of(200, Companies.class, 400, ErrorResponse.class),
                Map.of("apiKey", "cd9b5011-42d9-44b4-b760-b0794ae2e728", "name", "test company")
            )
        );
    }

    static Stream<Arguments> test_getCompanies_badRequest_parameter_provider() {
        return Stream.of(
            Arguments.arguments(
                Map.of(200, Companies.class, 400, ErrorResponse.class),
                Map.of("apiKey", "85d73fbd-a4b5-4456-be53-43c4dd20813c", "name", "test company")
            )
        );
    }

    static Stream<Arguments> test_registerCompany_ok_parameter_provider() {
        var company = new Company().name("test company").address("test address");
        return Stream.of(
            Arguments.arguments(
                Map.of(200, Companies.class, 400, ErrorResponse.class),
                Map.of("apiKey", "cd9b5011-42d9-44b4-b760-b0794ae2e728", "company", company)
            )
        );
    }

    static Stream<Arguments> test_getCompany_ok_parameter_provider() {
        return Stream.of(
            Arguments.arguments(
                Map.of(200, CompanyInfo.class, 400, ErrorResponse.class),
                Map.of("apiKey", "cd9b5011-42d9-44b4-b760-b0794ae2e728", "id", 1)
            )
        );
    }

    static Stream<Arguments> test_getCompany_defaultOk_parameter_provider() {
        return Stream.of(
            Arguments.arguments(
                Map.of(200, CompanyInfo.class, 400, ErrorResponse.class),
                Map.of("apiKey", "cd9b5011-42d9-44b4-b760-b0794ae2e728", "id", 3)
            )
        );
    }

    static Stream<Arguments> test_deleteCompany_ok_parameter_provider() {
        return Stream.of(
            Arguments.arguments(
                Map.of(400, ErrorResponse.class),
                Map.of("apiKey", "cd9b5011-42d9-44b4-b760-b0794ae2e728", "id", 1)
            )
        );
    }

    static Stream<Arguments> test_deleteCompany_badRequest_parameter_provider() {
        return Stream.of(
            Arguments.arguments(
                Map.of(400, ErrorResponse.class),
                Map.of("apiKey", "cd9b5011-42d9-44b4-b760-b0794ae2e728", "id", 2)
            )
        );
    }
}
