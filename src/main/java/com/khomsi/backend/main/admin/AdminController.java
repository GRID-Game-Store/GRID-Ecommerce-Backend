package com.khomsi.backend.main.admin;

import com.khomsi.backend.main.admin.model.dto.GameInsertDTO;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.impl.AdminServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.khomsi.backend.сonfig.ApplicationConfig.BEARER_KEY_SECURITY_SCHEME;
@RestController
@Tag(name = "Admin", description = "CRUD operation for Admin Controller")
@RequestMapping("/api/v1/admin")
@Validated
@RequiredArgsConstructor
public class AdminController {
    private final AdminServiceImpl adminService;
    @PostMapping("/add")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Add game to db")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse addGameToDb(@RequestBody GameInsertDTO gameDTO) {
        return adminService.addGameToDb(gameDTO);
    }
}
