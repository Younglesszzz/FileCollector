package com.example.filecollector.web;

import com.example.filecollector.po.User;
import com.example.filecollector.service.FileService;
import com.example.filecollector.util.FileUtils;
import com.example.filecollector.vo.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/files")
public class FileController {
    private final static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${file.upload-path}")
    private String uploadFilePath;

    @Value("${file.download-path}")
    private String downloadFilePath;

    @Autowired
    private FileService fileService;

    @GetMapping
    public String index() {
        return "index";
    }

    /**
     * 查询关键词相关文件
     * @param query 用户想要查询的文件关键词
     * @return 查询结果
     */
    @GetMapping(value = "/find")
    @ResponseBody
    public Result find(@RequestParam("query") String query, HttpServletRequest request) throws JsonProcessingException {
        Page<com.example.filecollector.po.File> filePage = fileService.findFile(query);

        HashMap<String, Object> hashMap = new HashMap<>(2);

        hashMap.put("page", filePage);
        return new Result(hashMap, "文件查询成功");
    }

    /**
     *
     * @param uploadFile 上传文件
     * @param fileTagName 前端传入对应标签名字,用户输入，没有就创建，有就直接用
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public Result upload(@RequestParam("uploadFile") MultipartFile uploadFile, @RequestParam("fileTagName") String fileTagName, HttpServletRequest request) throws IOException {
        //打印上传文件信息
        logFileInfo(uploadFile, uploadFilePath);

        //多媒体文件转为po file
        com.example.filecollector.po.File newFile = FileUtils.convertFile(uploadFile);

        //判断文件所在目录是否存在，不存在就创建对应的目录
        File dest = FileUtils.buildDest(uploadFilePath + uploadFile.getOriginalFilename());
        uploadFile.transferTo(dest);

        //获取userId
        Object userObject = request.getSession().getAttribute("user");
        ObjectMapper objectMapper = new ObjectMapper();
        String userString = objectMapper.writeValueAsString(userObject);
        User user = objectMapper.readValue(userString, User.class);


        //调用service 服务，储存到数据库，进行上传相关逻辑的处理
        fileService.saveFile(newFile, fileTagName, dest.getAbsolutePath(), user.getId());

        Map<String, String> hashMap = new HashMap<>(16);
        hashMap.put("contentType", uploadFile.getContentType());
        hashMap.put("fileName", uploadFile.getOriginalFilename());
        //这么写有缺陷，会丢失精度，应该四舍五入使用Decimal类（小问题，待更改-------------
        hashMap.put("fileSize", String.valueOf(uploadFile.getSize() / 1024) + "KB");//单位是B,大写B代表byte，小写b代表bit

        Result res = new Result(hashMap, "上传成功");
        return res;
    }

    /**
     *
     * @param id 文件id
     * @param response 响应，下载真正的文件
     * @param request  请求，获取UserId
     */
    @GetMapping("/{id}/download")
    @ResponseBody
    public Result fileDownload(@PathVariable("id") Long id, HttpServletResponse response, HttpServletRequest request) throws Exception {
        String fileName = fileService.findById(id).getName();
        File file = new File(downloadFilePath + fileName);//如何解决中文问题
        if (!file.exists()) {
            throw new Exception("下载文件本地不存在");
        }

        //处理数据库中的部分逻辑
        fileService.downloadFile(id);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", file.getName());

        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int)file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        byte[] buff = new byte[1024];
        OutputStream os = response.getOutputStream();
        int i = 0;
        while ((i = bis.read(buff)) != -1) {
            os.write(buff, 0, i);
            os.flush();
        }
        return new Result(hashMap, "下载成功");
    }

    private void logFileInfo(MultipartFile file, String uploadFilePath) {
        logger.info("文件名：" + file.getName());
        logger.info("原文件名：" + file.getOriginalFilename());
        logger.info("文件类型" + file.getContentType());
        logger.info("文件地址: " + (uploadFilePath + file.getOriginalFilename()));
    }
}
