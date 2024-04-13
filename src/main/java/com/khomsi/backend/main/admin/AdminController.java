package com.khomsi.backend.main.admin;

import com.khomsi.backend.main.admin.model.dto.GameInsertDTO;
import com.khomsi.backend.main.admin.model.response.AdminResponse;
import com.khomsi.backend.main.admin.service.impl.AdminServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @PostMapping("/game/add")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)}, summary = "Add game to db")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse addGameToDb(@RequestBody GameInsertDTO gameDTO) {
        return adminService.addGameToDb(gameDTO);
    }

    @PostMapping("/game/activate/{game-id}")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)},
            summary = "Change game visibility to activate/deactivate in db")
    @ResponseStatus(HttpStatus.OK)
    public AdminResponse changeGameVisibility(@PathVariable("game-id")
                                              @Min(1) @Max(Long.MAX_VALUE) Long gameId,
                                              @RequestParam(value = "activate") boolean activate) {
        return adminService.toggleGameActiveStatus(gameId, activate);
    }
}
