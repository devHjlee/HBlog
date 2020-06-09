package com.hblog.pdfbox.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hblog.pdfbox.service.PdfBoxService;
//강제 충돌
@Controller
public class PdfBoxController {
    private static final Logger logger = LoggerFactory.getLogger(PdfBoxController.class);

    @Autowired
    private PdfBoxService pdfService;

    @RequestMapping(value = "/pdfbox/merge", method = RequestMethod.GET)
    public String toastUiGrid(HttpServletRequest request) {
        logger.info("PdfBoxController Get");
        return "/dev/pdfbox/pdfBoxMerge";
    }

    @RequestMapping(value = "/pdfbox/pdfUpload", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public ResponseEntity<Map<String,String>> pdfUpload(MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {

        Map<String,String> result = new HashMap<String,String>();

        try {
            result = pdfService.pdfMerge(multipartHttpServletRequest);
        } catch (Exception e) {
            result.put("result", "fail");
            result.put("error", e.toString());
            return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/pdfbox/pdfDownload/{fileName:.+}")
    public ResponseEntity<Resource> pdfDownload(@PathVariable String fileName,HttpServletRequest request) throws Exception {

        // Load file as Resource
        Resource resource = pdfService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
