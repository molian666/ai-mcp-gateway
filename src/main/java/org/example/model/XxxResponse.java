package org.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

/**
 * @author wyh
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class XxxResponse {
    @JsonProperty(required = true, value = "salary")
    @JsonPropertyDescription("工资")
    private String salary;

    @JsonProperty(required = true, value = "dayManHour")
    @JsonPropertyDescription("工时")
    private String dayManHour;
}
