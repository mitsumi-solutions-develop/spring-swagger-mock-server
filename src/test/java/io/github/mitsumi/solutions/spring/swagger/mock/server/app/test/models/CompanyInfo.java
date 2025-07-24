package io.github.mitsumi.solutions.spring.swagger.mock.server.app.test.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Generated;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Objects;

/**
 * CompanyInfo
 */

@JsonTypeName("company_info")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.14.0")
public class CompanyInfo implements Serializable {

  private static final long serialVersionUID = 1L;

  private Integer id;

  private String name;

  private @Nullable String address;

  public CompanyInfo() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CompanyInfo(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public CompanyInfo id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public CompanyInfo name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   */
  @JsonProperty("name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CompanyInfo address(@Nullable String address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
   */
  
  @JsonProperty("address")
  public @Nullable String getAddress() {
    return address;
  }

  public void setAddress(@Nullable String address) {
    this.address = address;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CompanyInfo companyInfo = (CompanyInfo) o;
    return Objects.equals(this.id, companyInfo.id) &&
        Objects.equals(this.name, companyInfo.name) &&
        Objects.equals(this.address, companyInfo.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, address);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CompanyInfo {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
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

