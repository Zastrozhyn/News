package ru.clevertec.ecl.web.controller;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.service.service.UserService;
import ru.clevertec.ecl.web.dto.UserDto;
import ru.clevertec.ecl.web.mapper.UserMapper;

import java.util.List;

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

    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable Long id) {
        return mapper.mapToDto(userService.findUserById(id));
    }

    @GetMapping()
    public List<UserDto> findAllComments(Pageable pageable) {
        return mapper.mapToDto(userService.findAllUser(pageable));
    }

    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable(name = "userId") Long id) {
        userService.deleteUser(id);
    }
}
