package academy.softserve.eschool.controller;

import academy.softserve.eschool.dto.AddedUsersDTO;
import academy.softserve.eschool.repository.UserRepository;
import academy.softserve.eschool.service.PasswordDecodeService;
import academy.softserve.eschool.wrapper.GeneralResponseWrapper;
import academy.softserve.eschool.wrapper.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Api(description = "Operations with users entity")
@RequiredArgsConstructor
public class UserController {
    @NonNull
    private UserRepository userRepository;
    @NonNull
    private PasswordDecodeService passwordDecodeService;

    @GetMapping("")
    @ApiOperation(value = "Admin gets the list of all users (with passwords)", extensions = {@Extension(name = "roles", properties = {
            @ExtensionProperty(name = "admin", value = "the admin is allowed to get the list of all users")})})
    @PreAuthorize("hasRole('ADMIN')")
    public GeneralResponseWrapper<List<AddedUsersDTO>> getAllUsers(){
        return new GeneralResponseWrapper<>(Status.of(HttpStatus.OK), passwordDecodeService.decodemultiple(userRepository.getRegisteredUsers()));
    }
}