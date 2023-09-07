package com.cerpo.fd.controller;

import com.cerpo.fd.payload.menu.MenuResponse;
import com.cerpo.fd.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/{id}")
    public ResponseEntity<MenuResponse> getMenu(@PathVariable(name = "id") Integer id) {
        return new ResponseEntity<>(menuService.getMenu(id), HttpStatus.OK);
    }
}
