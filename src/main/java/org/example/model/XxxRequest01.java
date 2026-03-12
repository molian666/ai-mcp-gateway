package org.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

/**
 * @author wyh
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class XxxRequest01 {

    @JsonProperty(required = true, value = "city")
    @JsonPropertyDescription("城市名称,如果是中文汉字请先转换为汉语拼音，例如北京：beijing")
    private String city;

    @JsonProperty(required = true, value = "company")
    @JsonPropertyDescription("公司信息,如果是中文汉字请先转换为汉语拼音，例如京东：jingdong")
    private Company company;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Company{

        @JsonProperty(required = true, value = "name")
        @JsonPropertyDescription("公司名称")
        private String name;

        @JsonProperty(required = true, value = "type")
        @JsonPropertyDescription("公司类型")
        private String type;
    }
}
