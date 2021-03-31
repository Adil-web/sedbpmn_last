package kz.kazgisa.controller;


import kz.kazgisa.data.entity.dict.Subservice;
import kz.kazgisa.service.SubserviceService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("subservices")
public class SubserviceController {
    private final SubserviceService subserviceService;

    public SubserviceController(SubserviceService subserviceService) {
        this.subserviceService = subserviceService;
    }

    @GetMapping
    public ResponseEntity<?> getsubservices(@RequestParam(required = false) String name, Pageable pageable) {
        return ResponseEntity.ok(subserviceService.getSubservices(name,pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getSubservicesById(@PathVariable Long id) {
        return ResponseEntity.ok(subserviceService.getSubserviceById(id));
    }

    @GetMapping("{id}/members")
    public ResponseEntity<?> getSubserviceMems(@PathVariable Long id,
                                               @RequestParam(required = false) Long regionId) {
        return ResponseEntity.ok(subserviceService.getSubserviceMembersById(id, regionId));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateSubservices(@PathVariable Long id, @RequestBody Subservice subservice) {
        subservice.setId(id);
        return ResponseEntity.ok(subserviceService.saveSubservice(subservice));
    }

    @DeleteMapping("{id}/roles/{roleId}")
    public ResponseEntity<?> deleteRolesSById(@PathVariable Long id, @PathVariable Long roleId)
    {
        return ResponseEntity.ok(subserviceService.deleteSubserviceRolesBySubserviceIdAndRoleId(id,roleId));
    }
}
