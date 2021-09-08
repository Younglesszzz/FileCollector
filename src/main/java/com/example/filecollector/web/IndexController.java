package com.example.filecollector.web;

import com.example.filecollector.dao.FileRepository;
import com.example.filecollector.dao.FileTagRepository;
import com.example.filecollector.service.FileService;
import com.example.filecollector.service.FileTagService;
import com.example.filecollector.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;

@Controller
public class IndexController {
    private final static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileTagRepository fileTagRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }


}
