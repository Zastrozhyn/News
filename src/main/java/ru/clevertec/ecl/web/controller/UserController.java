package ru.clevertec.ecl.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.annotation.Log;
import ru.clevertec.ecl.service.service.UserService;
import ru.clevertec.ecl.web.dto.UserDto;
import ru.clevertec.ecl.web.mapper.UserMapper;

import java.util.List;

/**
 * REst controller for users
 */
@Log
@Tag(name = "User api", description = "User management")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * @param userDto
     * @return UserDto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        return mapper.mapToDto(userService.createUser(mapper.mapToEntity(userDto)));
    }

    /**
     * @param userId
     * @return UserDto
     */
    @GetMapping("/{userId}")
    public UserDto findUserById(@PathVariable Long userId) {
        return mapper.mapToDto(userService.findUserById(userId));
    }

    /**
     * @param pageable
     * @return List of UserDto
     */
    @GetMapping()
    public List<UserDto> findAllComments(Pageable pageable) {
        return mapper.mapToDto(userService.findAllUser(pageable));
    }

    /**
     * @param userId
     * @param userDto
     * @return UserDto
     */
    @PutMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId,
                              @RequestBody UserDto userDto) {
        userDto.setId(userId);
        return mapper.mapToDto(userService.updateUser(mapper.mapToEntity(userDto)));
    }

    /**
     * @param userId
     */
    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(name = "userId") Long userId) {
        userService.deleteUser(userId);
    }
}
