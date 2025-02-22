package org.sayar.net.Service;

import org.sayar.net.Model.DTO.FailureModeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FailureModeService {
    FailureModeDto save(FailureModeDto failureModeDto);

    Page<FailureModeDto> getAllPage(String term, Pageable pageable);

    FailureModeDto getOne(String id);

    boolean update(FailureModeDto failureModeDto);

    boolean delete(String id);

    List<FailureModeDto> getAll();
}
