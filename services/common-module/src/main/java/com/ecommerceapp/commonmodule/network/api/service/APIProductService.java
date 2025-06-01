package com.ecommerceapp.commonmodule.network.api.service;

import com.ecommerceapp.commonmodule.constant.APIBaseUrlConstant;
import com.ecommerceapp.commonmodule.dto.ProductDTO;
import com.ecommerceapp.commonmodule.network.api.APIService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class APIProductService {
    private final APIService apiService;
    private String BASE_URL = APIBaseUrlConstant.PRODUCT_SERVICE_URL;

    public List<ProductDTO> getByListId(List<Long> ids) {
        return apiService.get(
                BASE_URL + "/getByListId?productIds=" + ids.toString()
                        .replace("[", "")
                        .replace("]", "")
                        .replace(" ", ""),
                new ParameterizedTypeReference<>(){}
        );
    }
}
