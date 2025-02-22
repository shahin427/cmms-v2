package org.sayar.net.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Lubricant {
    @Id
    private String id;
    private String title;
    private String type;
}
