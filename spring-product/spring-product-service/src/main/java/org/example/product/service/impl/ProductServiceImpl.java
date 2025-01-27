package org.example.product.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Product;
import org.example.product.mapper.ProductMapper;
import org.example.product.service.IProductService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
}
