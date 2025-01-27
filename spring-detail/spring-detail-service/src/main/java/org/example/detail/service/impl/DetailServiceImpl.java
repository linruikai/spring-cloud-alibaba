package org.example.detail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.detail.mapper.DetailMapper;
import org.example.detail.service.IDetailService;
import org.example.entity.Detail;
import org.springframework.stereotype.Service;

@Service
public class DetailServiceImpl extends ServiceImpl<DetailMapper, Detail> implements IDetailService {
}
