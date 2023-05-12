package ru.clevertec.ecl.web.controller;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.service.annotation.Log;
import ru.clevertec.ecl.service.service.UserService;
import ru.clevertec.ecl.web.dto.UserDto;
import ru.clevertec.ecl.web.mapper.UserMapper;

import java.util.List;

@Log
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        return mapper.mapToDto(userService.createUser(mapper.mapToEntity(userDto)));
    }

    @GetMapping("/{userId}")
    public UserDto findUserById(@PathVariable Long userId) {
        return mapper.mapToDto(userService.findUserById(userId));
    }

    @GetMapping()
    public List<UserDto> findAllComments(Pageable pageable) {
        return mapper.mapToDto(userService.findAllUser(pageable));
    }

    @PutMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId,
                              @RequestBody UserDto userDto) {
        userDto.setId(userId);
        return mapper.mapToDto(userService.updateUser(mapper.mapToEntity(userDto)));
    }

    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(name = "userId") Long id) {
        userService.deleteUser(id);
    }
}
