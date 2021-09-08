package com.example.filecollector.web;

import com.example.filecollector.dao.FileRepository;
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
import org.springframework.data.domain.PageRequest;
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
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;


    @GetMapping(value = "/info/{userId}")
    @ResponseBody
    public Result info(@PathVariable Long userId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("files", fileRepository.
                findAllByUploadUser_IdOrderByFileTags_IdAsc(userId, PageRequest.of(0, 10)));
        return new Result(hashMap, "用户所有文件");
    }

    /**
     * 查询关键词相关文件
     * @param query 用户想要查询的文件关键词
     * @return 查询结果
     */
    @GetMapping(value = "/find/{userId}")
    @ResponseBody
    public Result find(@RequestParam("query") String query, HttpServletRequest request,
                       @PathVariable Long userId) throws JsonProcessingException {
        Page<com.example.filecollector.po.File> filePage = fileService.findFile(query, userId);

        HashMap<String, Object> hashMap = new HashMap<>(2);

        hashMap.put("page", filePage);
        return new Result(hashMap, "文件查询成功");
    }

    @GetMapping("/show")
    @ResponseBody
    public Result show(HttpServletRequest request) throws JsonProcessingException {
        //获取User
        Object userObject = request.getSession().getAttribute("user");
        ObjectMapper objectMapper = new ObjectMapper();
        String userString = objectMapper.writeValueAsString(userObject);
        User user = objectMapper.readValue(userString, User.class);

        //这里可能有NullpointerException
        HashMap<String, Object> hashMap = new HashMap<>(1);
        Page<com.example.filecollector.po.File> filePage = fileRepository.findAllByUploadUserId(user.getId(), PageRequest.of(0, 10));
        hashMap.put("userFiles", filePage);
        return new Result(hashMap, "用户个人文件");
    }


    @GetMapping("/showByQuery/{userId}")
    @ResponseBody
    public Result showByQuery(@RequestParam String query) {
        return null;
    }

    @GetMapping("/showByTag/{userId}")
    @ResponseBody
    public Result showByTag(@RequestParam("tagName") String tagName, @PathVariable Long userId) {
        Page<com.example.filecollector.po.File> files = fileService.getAllByTag(tagName, userId);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("files", files);
        return new Result(hashMap, "标签下文件");
    }

    /**
     *
     * @param uploadFile 上传文件
     * @param fileTagName 前端传入对应标签名字,用户输入，没有就创建，有就直接用
     */
    @PostMapping(value = "/upload/{userId}")
    @ResponseBody
    public Result upload(@RequestParam("uploadFile") MultipartFile uploadFile,
                         @RequestParam("fileTagName") String fileTagName,
                         @PathVariable("userId") Long userId,
                         HttpServletRequest request) throws IOException {
        //打印上传文件信息
        logFileInfo(uploadFile, uploadFilePath);

        //多媒体文件转为po file
        com.example.filecollector.po.File newFile = FileUtils.convertFile(uploadFile);

        //判断文件所在目录是否存在，不存在就创建对应的目录
        File dest = FileUtils.buildDest(uploadFilePath + uploadFile.getOriginalFilename());
        uploadFile.transferTo(dest);



        //调用service 服务，储存到数据库，进行上传相关逻辑的处理
        fileService.saveFile(newFile, fileTagName, dest.getAbsolutePath(), userId);

        Map<String, String> hashMap = new HashMap<>(16);
        hashMap.put("contentType", uploadFile.getContentType());
        hashMap.put("fileName", uploadFile.getOriginalFilename());
        //这么写有缺陷，会丢失精度，应该四舍五入使用Decimal类（小问题，待更改-------------
        hashMap.put("fileSize", String.valueOf(uploadFile.getSize() / 1024) + "KB");//单位是B,大写B代表byte，小写b代表bit

        Result res = new Result(hashMap, "上传成功");
        return res;
    }


    @PostMapping(value = "/uploadAll")
    @ResponseBody
    public Result uploadAll(@RequestParam("uploadFiles") MultipartFile[] uploadFiles, @RequestParam("fileTagName") String fileTagName, HttpServletRequest request) throws IOException {

        //获取userId
        Object userObject = request.getSession().getAttribute("user");
        ObjectMapper objectMapper = new ObjectMapper();
        String userString = objectMapper.writeValueAsString(userObject);
        User user = objectMapper.readValue(userString, User.class);
        Map<String, String> hashMap = new HashMap<>(16);
        //多媒体文件转为po file
        for (int i = 0; i < uploadFiles.length; i++) {
            com.example.filecollector.po.File newFile = FileUtils.convertFile(uploadFiles[i]);
            //判断文件所在目录是否存在，不存在就创建对应的目录
            File dest = FileUtils.buildDest(uploadFilePath + uploadFiles[i].getOriginalFilename());
            uploadFiles[i].transferTo(dest);


            //调用service 服务，储存到数据库，进行上传相关逻辑的处理
            fileService.saveFile(newFile, fileTagName, dest.getAbsolutePath(), user.getId());

            //存入hashmap
            hashMap.put("contentType" + i, uploadFiles[i].getContentType());
            hashMap.put("fileName" + i, uploadFiles[i].getOriginalFilename());
            hashMap.put("fileSize" + i, String.valueOf(uploadFiles[i].getSize() / 1024) + "KB");//单位是B,大写B代表byte，小写b代表bit
        }

        Result res = new Result(hashMap, "上传成功");
        return res;
    }

    /**
     *
     * @param fileName 文件id
     * @param response 响应，下载真正的文件
     * @param request  请求，获取UserId
     */
    @GetMapping("/download/{userId}")
    @ResponseBody
    public Result fileDownload(@RequestParam("fileName") String fileName, @PathVariable("userId") Long userId, HttpServletResponse response, HttpServletRequest request) throws Exception {
        logger.info(fileName);
        com.example.filecollector.po.File found = fileRepository.findByNameAndUploadUser_Id(fileName, userId);

        logger.info(found.toString());
        logger.info(found.getPath());
        File file = new File(found.getPath());//如何解决中文问题
        if (!file.exists()) {
            throw new Exception("下载文件本地不存在");
        }

        //处理数据库中的部分逻辑
        fileService.downloadFile(fileName, userId);

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

    @GetMapping("/updateFile/{userId}")
    @ResponseBody
    public Result updateFile(@RequestParam("oldName") String oldName, @RequestParam("newName") String newName,@PathVariable("userId") Long userId) {
        com.example.filecollector.po.File found = fileRepository.findByNameAndUploadUser_Id(oldName, userId);
        if (found == null)
            return new Result(null, "修改失败");
        found.setName(newName);
        fileRepository.save(found);
        return new Result(null, "修改成功");
    }

    @PostMapping("/delete/{userId}")
    @ResponseBody
    public Result deleteFile(@RequestParam String fileName, @PathVariable Long userId) {
        com.example.filecollector.po.File file = fileRepository.findByNameAndUploadUser_Id(fileName, userId);
        if (file == null)
            return new Result(null, "文件不存在");
        fileRepository.delete(file);
        return new Result(null, "删除成功");
    }

    private void logFileInfo(MultipartFile file, String uploadFilePath) {
        logger.info("文件名：" + file.getName());
        logger.info("原文件名：" + file.getOriginalFilename());
        logger.info("文件类型" + file.getContentType());
        logger.info("文件地址: " + (uploadFilePath + file.getOriginalFilename()));
    }

    public static String toUtf8(String str) throws UnsupportedEncodingException {
        return new String(str.getBytes("UTF-8"),"UTF-8");
    }

}
