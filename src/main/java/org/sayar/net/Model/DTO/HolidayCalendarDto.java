package org.sayar.net.Model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayCalendarDto {

    private String id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private List<Date> holidays;
}
