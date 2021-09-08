package com.example.filecollector.service;

import com.example.filecollector.dao.FileTagRepository;
import com.example.filecollector.po.FileTag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileTagService {
    @Autowired
    private FileTagRepository fileTagRepository;

    public FileTag saveTag(FileTag fileTag) throws Exception {
        FileTag found = fileTagRepository.findByName(fileTag.getName());
        //没有同名
        if (found == null)
            return fileTagRepository.save(fileTag);
        else {
            throw new Exception("存在同名标签");
        }
    }

    public Page<FileTag> getAllTags(Long userId) {
        return fileTagRepository.findAllByCreator_Id(userId, PageRequest.of(0, 10));
    }

    public FileTag getFileTag(Long id) {
        Optional<FileTag> optionalFileTag = fileTagRepository.findById(id);
        return optionalFileTag.orElse(null);
    }

    public FileTag getFileTagByName(String name) {
        return fileTagRepository.findByName(name);
    }

    //更新标签
    public FileTag updateTag(FileTag fileTag) {
        Optional<FileTag> optionalFileTag = fileTagRepository.findById(fileTag.getId());
        FileTag targetTag = optionalFileTag.get();//如果存在，返回optional中的值，否则抛出异常
        BeanUtils.copyProperties(fileTag, targetTag);
        return fileTagRepository.save(targetTag);
    }

    public void deleteTag(long id) {
        fileTagRepository.deleteById(id);
    }
}
