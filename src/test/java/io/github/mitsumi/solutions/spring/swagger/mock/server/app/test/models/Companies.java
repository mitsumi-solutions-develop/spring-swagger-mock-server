package io.github.mitsumi.solutions.spring.swagger.mock.server.app.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Generated;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Companies
 */

@JsonTypeName("companies")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.14.0")
public class Companies implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer count;

  private List<CompanyInfo> results = new ArrayList<>();

  public Companies() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Companies(Integer count) {
    this.count = count;
  }

  public Companies count(Integer count) {
    this.count = count;
    return this;
  }

  /**
   * Get count
   * @return count
   */
  @JsonProperty("count")
  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public Companies results(List<CompanyInfo> results) {
    this.results = results;
    return this;
  }

  public Companies addResultsItem(CompanyInfo resultsItem) {
    if (this.results == null) {
      this.results = new ArrayList<>();
    }
    this.results.add(resultsItem);
    return this;
  }

  /**
   * Get results
   * @return results
   */
  @JsonProperty("results")
  public List<CompanyInfo> getResults() {
    return results;
  }

  public void setResults(List<CompanyInfo> results) {
    this.results = results;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Companies companies = (Companies) o;
    return Objects.equals(this.count, companies.count) &&
        Objects.equals(this.results, companies.results);
  }

  @Override
  public int hashCode() {
    return Objects.hash(count, results);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Companies {\n");
    sb.append("    count: ").append(toIndentedString(count)).append("\n");
    sb.append("    results: ").append(toIndentedString(results)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

