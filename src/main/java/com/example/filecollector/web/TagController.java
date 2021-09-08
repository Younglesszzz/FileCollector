package com.example.filecollector.web;

import com.example.filecollector.dao.FileRepository;
import com.example.filecollector.po.File;
import com.example.filecollector.po.FileTag;
import com.example.filecollector.service.FileTagService;
import com.example.filecollector.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@RestController
public class TagController {
    private final static Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private FileTagService fileTagService;

    @Autowired
    private FileRepository fileRepository;

    @PostMapping("/createTag")
    public Result createTag(@RequestBody FileTag fileTag) throws Exception {
        fileTagService.saveTag(fileTag);
        return new Result(null, "创建完成");
    }

    @PostMapping("/updateTag")
    public Result updateTag(@RequestBody FileTag fileTag) {
        fileTagService.updateTag(fileTag);
        return new Result(null, "更新完毕");
    }

    @GetMapping("/getAllTags/{userId}")
    public Result getAllTags(@PathVariable Long userId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        Page<FileTag> fileTags = fileTagService.getAllTags(userId);
        hashMap.put("tags", fileTags);
        return new Result(hashMap, "所有标签和文件");
    }



}
