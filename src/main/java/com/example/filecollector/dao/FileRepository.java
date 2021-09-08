package com.example.filecollector.dao;

import com.example.filecollector.po.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileRepository extends JpaRepository<File, Long> {
    //调用者写法: repositroy.findByQuery("自动机", new PageRequest(1, 10)); page, size, sort
    @Query("select f from File f where f.name like %?1% and f.uploadUser.id = ?2")//记得加%占位符
    Page<File> findByQueryAndUploadUser_Id(String query, Long userId, Pageable pageable);

    Page<File> findAllByUploadUserId(Long userId, Pageable pageable);

    Page<File> findAllByFileTags_idAndUploadUser_id(Long fileTagId, Long userId, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from t_file where name = ?1 and upload_user_id = ?2 limit 1")
    File findByNameAndUploadUser_Id(String fileName, Long userId);

    File findByName(String name);
}
