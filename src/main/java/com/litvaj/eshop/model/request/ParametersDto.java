package com.litvaj.eshop.model.request;

import lombok.Data;
import org.springframework.data.domain.Pageable;


@Data
public class ParametersDto {
    private Pageable pageable;
}
