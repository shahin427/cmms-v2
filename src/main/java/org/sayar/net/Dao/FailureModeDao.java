package org.sayar.net.Dao;

import com.mongodb.client.result.UpdateResult;
import org.sayar.net.Model.DTO.FailureModeDto;
import org.sayar.net.Model.FailureMode;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FailureModeDao {
    FailureMode save(FailureMode failureMode);

    List<FailureMode> getAllPage(String term, Pageable pageable);

    long count(String term);

    FailureMode getOne(String id);

    UpdateResult update(FailureModeDto failureModeDto);

    UpdateResult remove(String id);

    List<FailureMode> getAll();
}
