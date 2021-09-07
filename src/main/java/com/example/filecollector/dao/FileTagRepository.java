package com.example.filecollector.dao;

import com.example.filecollector.po.File;
import com.example.filecollector.po.FileTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface FileTagRepository extends JpaRepository<FileTag, Long> {
    /**
     * 标签名查找
     * @param name
     * @return
     */
    FileTag findByName(String name);



    /**
     * 查找标签 按pageable内定义的排序顺序和数量返回
     * @param pageable
     * @return
     */
    @Query("select ft from FileTag ft")
    List<FileTag> findTop(Pageable pageable);
}
