package com.example.filecollector.web;

import com.example.filecollector.po.FileTag;
import com.example.filecollector.service.FileTagService;
import com.example.filecollector.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TagController {
    private final static Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private FileTagService fileTagService;

}
