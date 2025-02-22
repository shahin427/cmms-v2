package org.sayar.net.Controller;

import org.sayar.net.Model.Lubricant;
import org.sayar.net.Service.LubricantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/lubricant")
public class LubricantController {

    @Autowired
    private LubricantService lubricantService;

    @PostMapping("save")
    public ResponseEntity<?> save(@RequestBody Lubricant lubricant) {
        return ResponseEntity.ok().body(lubricantService.save(lubricant));
    }

    @PostMapping("get-page")
    public ResponseEntity<?> getAll(@RequestBody Lubricant lubricant, Pageable pageable, Integer totalElements) {
        return ResponseEntity.ok().body(lubricantService.getAll(lubricant, pageable, totalElements));
    }

    @GetMapping("get-one")
    public ResponseEntity<?> getOne(@PathParam("id") String id) {
        return ResponseEntity.ok().body(lubricantService.getOne(id));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody Lubricant lubricant) {
        return ResponseEntity.ok().body(lubricantService.update(lubricant));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@PathParam("id") String id) {
        return ResponseEntity.ok().body(lubricantService.delete(id));
    }

    @GetMapping("get-all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(lubricantService.getAllWithNoPage());
    }

    @GetMapping("check-used")
    public ResponseEntity<?> checkIfLubricantUsedInAsset(@PathParam("id") String id) {
        return ResponseEntity.ok().body(lubricantService.checkIfLubricantUsedInAsset(id));
    }
}
