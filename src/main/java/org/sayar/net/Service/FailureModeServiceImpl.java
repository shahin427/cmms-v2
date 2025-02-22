package org.sayar.net.Service;


import com.mongodb.client.result.UpdateResult;
import org.sayar.net.Dao.FailureModeDao;
import org.sayar.net.Model.DTO.FailureModeDto;
import org.sayar.net.Model.FailureMode;
import org.sayar.net.Tools.Print;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FailureModeServiceImpl implements FailureModeService {


    private FailureModeDao failureModeDao;

    public FailureModeServiceImpl(FailureModeDao failureModeDao) {
        this.failureModeDao = failureModeDao;
    }

    @Override
    public FailureModeDto save(FailureModeDto failureModeDto) {
        FailureMode failureMode = new FailureMode(failureModeDto.getName());
        FailureMode savedFailureMode = failureModeDao.save(failureMode);
        return FailureModeDto.builder()
                .id(savedFailureMode.getId())
                .name(savedFailureMode.getName())
                .build();
    }

    @Override
    public Page<FailureModeDto> getAllPage(String term, Pageable pageable) {
        List<FailureMode> failureModes = failureModeDao.getAllPage(term, pageable);
        Print.print("failureModes",failureModes);
        long count = failureModeDao.count(term);
        return new PageImpl<>(FailureMode.map(failureModes), pageable, count);
    }

    @Override
    public FailureModeDto getOne(String id) {
        FailureMode failureMode = failureModeDao.getOne(id);
        return FailureModeDto.builder()
                .id(failureMode.getId())
                .name(failureMode.getName())
                .build();
    }

    @Override
    public boolean update(FailureModeDto failureModeDto) {
        UpdateResult updateResult = failureModeDao.update(failureModeDto);
        return updateResult.getModifiedCount() > 0;
    }

    @Override
    public boolean delete(String id) {
        UpdateResult updateResult = failureModeDao.remove(id);
        return updateResult.getModifiedCount() > 0;
    }

    @Override
    public List<FailureModeDto> getAll() {
        List<FailureMode> failureModes = failureModeDao.getAll();
        return failureModes.stream()
                .map(failureMode ->
                        FailureModeDto.builder()
                                .id(failureMode.getId())
                                .name(failureMode.getName())
                                .build())
                .collect(Collectors.toList());
    }
}
