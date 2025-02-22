package org.sayar.net.Model;


import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.sayar.net.Model.DTO.FailureModeDto;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Document("FAILURE_MODE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FailureMode {

    @Id
    private String id;
    private String name;
    private boolean deleted;


    public FailureMode(String name) {
        this.name = name;
    }

    public FailureMode(String id, String name) {
        this.id = id;
        this.name = name;
    }

    //    public static List<FailureModeDto> map(List<FailureMode> failureModes) {
//        return failureModes
//                .stream()
//                .map(failureMode -> FailureModeDto.builder()
//                        .name(Optional.ofNullable(failureMode.getName()).orElse(null)).build())
//                .collect(Collectors.toList());
//    }
    public static List<FailureModeDto> map(List<FailureMode> failureModes) {
        return failureModes
                .stream()
                .map(failureMode -> FailureModeDto.builder()
                        .id(Optional.ofNullable(failureMode.getId()).orElse(null))
                        .name(Optional.ofNullable(failureMode.getName()).orElse(null))
                        .build())
                .collect(Collectors.toList());
    }
}
