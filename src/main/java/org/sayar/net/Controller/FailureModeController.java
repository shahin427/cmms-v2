package org.sayar.net.Controller;

import org.sayar.net.Model.DTO.FailureModeDto;
import org.sayar.net.Service.FailureModeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/failure-mode")
public class FailureModeController {

    private final FailureModeService failureModeService;

    public FailureModeController(FailureModeService failureModeService) {
        this.failureModeService = failureModeService;
    }


    @PostMapping("/save")
    public FailureModeDto save(@RequestBody FailureModeDto failureModeDto) {
        return failureModeService.save(failureModeDto);
    }

    @PostMapping("/get-all-page")
    public Page<FailureModeDto> getAllPage(@RequestParam(value = "term", required = false) String term, Pageable pageable) {
        return failureModeService.getAllPage(term, pageable);
    }


    @GetMapping("/get-one")
    public FailureModeDto getOne(@PathParam("id") String id) {
        return failureModeService.getOne(id);
    }

    @PutMapping("/update")
    public boolean update(@RequestBody FailureModeDto failureModeDto) {
        return failureModeService.update(failureModeDto);
    }

    @ DeleteMapping("/delete")
    public boolean delete(@PathParam("id") String id) {
        return failureModeService.delete(id);
    }

    @GetMapping("get-all")
    public List<FailureModeDto> getAll() {
        return failureModeService.getAll();
    }

}
