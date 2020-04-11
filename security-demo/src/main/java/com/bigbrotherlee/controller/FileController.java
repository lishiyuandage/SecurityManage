package com.bigbrotherlee.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bigbrotherlee.dto.FileInfo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/file")
public class FileController {
	public static final String path="E:/eclipse-workspace/SecurityDemo/src/main/java/com/bigbrotherlee";
	
	@ApiOperation("swagger的注解，方法的api描述，文件上传服务")
	@PostMapping("/upload")
	public FileInfo upload(MultipartFile file) throws Exception {
		File localfile=new File(path,System.currentTimeMillis()+".txt");
		file.transferTo(localfile);
		FileInfo info=new FileInfo();
		info.setPath(path);
		return info;
	}
	
	@GetMapping("/{id}")
	public void download(@PathVariable String id,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try( 
				InputStream in=new FileInputStream(new File(path,id+".txt"));
				OutputStream out=response.getOutputStream();//在try括号里的会自动关闭流
				){
				response.setContentType("application/x-download");
				response.setHeader("Content-Despositon", "attachment;filename=test.txt");
				IOUtils.copy(in, out);
				out.flush();
		}
		
	}

}
