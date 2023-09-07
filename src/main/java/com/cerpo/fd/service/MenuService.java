package com.cerpo.fd.service;

import com.cerpo.fd.model.retailer.menu.MenuRepository;
import com.cerpo.fd.payload.menu.MenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuResponse getMenu(Integer menuId) {
        var menu = menuRepository.findMenuByMenuId(menuId).orElseThrow(RuntimeException::new); //ResourceNotFoundException
        return new MenuResponse(menu);
    }
}
