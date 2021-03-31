package kz.kazgisa.controller;

import kz.kazgisa.data.entity.SubserviceRole;
import kz.kazgisa.data.entity.user.Role;
import kz.kazgisa.service.RoleService;
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
@RequestMapping("roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<?> getRoles(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) Long subserviceId,
                                      Pageable pageable) {
        return ResponseEntity.ok(roleService.getRoles(name,subserviceId,pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getRolesById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping("{id}/subservices")
    public ResponseEntity<?> getRolesSById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleSubservicesById(id));
    }

    @PostMapping("{id}/subservices")
    public ResponseEntity<?> saveRolesSById(@PathVariable Long id, @RequestBody SubserviceRole subserviceRole)
    {
        return ResponseEntity.ok(roleService.saveRoleSubservicesById(id,subserviceRole));
    }

    @DeleteMapping("{id}/subservices/{subRoleId}")
    public ResponseEntity<?> deleteRolesSById(@PathVariable Long id, @PathVariable Long subRoleId)
    {
        return ResponseEntity.ok(roleService.deleteRoleSubservicesById(subRoleId));
    }

    @PostMapping
    public ResponseEntity<?> saveRoles(@RequestBody Role role) {
        return ResponseEntity.ok(roleService.saveRole(role));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateRoles(@PathVariable Long id, @RequestBody Role role) {
        role.setId(id);
        return ResponseEntity.ok(roleService.saveRole(role));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> updateRoles(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.deleteRole(id));
    }





}
