package com.example.filecollector.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_fileTag")
public class FileTag {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标签名称 前端必填
     */
    @NotBlank(message = "请输入标签名称")
    private String name;


    /**
     * 标签下的文件
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "fileTags")
    private List<File> files = new ArrayList<>();//不进行级联操作,mappedBy fileTags，则需要通过File.fileTags添加标签，而不是通过Tag.files添加标签

    @ManyToOne
    private User creator;

    public FileTag(String name) {
        this.name = name;
    }

    public FileTag() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
