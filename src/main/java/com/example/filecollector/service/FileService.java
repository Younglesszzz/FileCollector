package com.example.filecollector.service;

import com.example.filecollector.dao.FileRepository;
import com.example.filecollector.dao.FileTagRepository;
import com.example.filecollector.po.File;
import com.example.filecollector.po.FileTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileTagRepository fileTagRepository;



    @Transactional
    public File saveFile(File file, String tagName, String path) {
        //标签是否存在？
        FileTag found = fileTagRepository.findByName(tagName);

        //如果用户输入的tagName不存在，那么创建一个新的tag
        if (found == null) {
            found = new FileTag(tagName);
            fileTagRepository.save(found);
        }
        file.getFileTags().add(found);
        //录入file的path
        file.setPath(path);

        return fileRepository.save(file);
    }


    /**
     *
     * @param id 文件id
     */
    @Transactional
    public void downloadFile(Long id) throws Exception {
        Optional<File> file = fileRepository.findById(id);
        if (!file.isPresent()) {
            throw new Exception("下载的文件资源不存在");
        }
        //文件累计下载次数+1
        file.get().setDownloadCount(file.get().getDownloadCount() + 1);

    }


    public Page<File> findFile(String query) {
        return fileRepository.findByQuery(query, PageRequest.of(0, 10));
    }

    public File findById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }
}
