package com.hblog.pdfbox.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class PdfBoxService {

    private static final Logger logger = LoggerFactory.getLogger(PdfBoxService.class);

    @Value("${file.tempPath}")
    private String fileTempPath;
    
    @Value("${file.pdfMergePath}")
    private String filePdfMergePath;
    /**
     * 
     * @param multipartHttpServletRequest
     * @return 
     * @throws Exception
     */
    public Map<String,String> pdfMerge(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        logger.info("pdfMerge Start!");
        List<MultipartFile> files = multipartHttpServletRequest.getFiles("files");
        Map<String,String> result = new HashMap<String,String>();

        UUID uuid = UUID.randomUUID();

        String filePath = fileTempPath+new SimpleDateFormat("yyyyMMddHmsS").format(new Date())+"\\";
        //상대경로
        //String savePath = multipartHttpServletRequest.getSession().getServletContext().getRealPath(filePdfMergePath);
        //절대경로
        String savePath = filePdfMergePath;
        String pdfName = uuid+"_Merge.pdf";

        logger.info("savePath:"+savePath);

        PDDocument pdfDoc = null;
        PDFMergerUtility PDFmerger = new PDFMergerUtility();
        PDFmerger.setDestinationFileName(filePdfMergePath + pdfName);

        File folder = new File(filePath);
        File file;
        if (folder.exists() == false) {
            folder.mkdirs();
        }

        for (MultipartFile i : files) {

            try {
                file = new File(filePath + i.getOriginalFilename());
                i.transferTo(file);
                pdfDoc = PDDocument.load(file);
                PDFmerger.addSource(file);
                pdfDoc.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Exception(e);
            }

        }

        try {
            PDFmerger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());

            for(File deleteFile:folder.listFiles()) {
                deleteFile.delete();
            }

            folder.delete();

            result.put("result", "success");
            result.put("pdfName", pdfName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return result;
    }
}
