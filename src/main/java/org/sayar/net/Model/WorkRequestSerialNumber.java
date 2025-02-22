package org.sayar.net.Model;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

@Data
public class WorkRequestSerialNumber {
    @Id
    private String id;
    //    private int number = 0;
    private int number;
    private String type = "workRequestType";
}
