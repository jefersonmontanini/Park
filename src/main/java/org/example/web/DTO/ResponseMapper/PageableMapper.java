package org.example.web.DTO.ResponseMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.example.web.DTO.PageableDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {

    public static PageableDTO toDTO(Page page) {
        return new ModelMapper().map(page, PageableDTO.class);
    }
}
