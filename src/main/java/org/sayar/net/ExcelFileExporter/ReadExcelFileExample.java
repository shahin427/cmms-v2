package org.sayar.net.ExcelFileExporter;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sayar.net.Model.newModel.Helper.ResponseContent;
import org.sayar.net.Model.newModel.Part.Part;
import org.sayar.net.Tools.Print;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("file")
@RestController
public class ReadExcelFileExample {
    @Autowired
    private MongoTemplate mongoOperations;

    @PostMapping(value = "uploadExcelFile")
    public ResponseEntity<?> uploadExcelFileToInsetDoctor(@RequestParam("file") MultipartFile file) throws IOException {
        ResponseContent content = new ResponseContent();
//        String path = "/home/masoud/Downloads/test";
        String path = "/home/shahin/Downloads/partExcel";
        new File(path).mkdirs();
        File fileResult = new File(path, file.getOriginalFilename());
        Print.print("fileResult",fileResult);
        File file2 = new File(fileResult.getAbsolutePath());
        Print.print("file2",file2);
        FileUtils.writeByteArrayToFile(file2, file.getBytes());
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(fileResult.getAbsolutePath()));
        XSSFSheet sheet = workbook.getSheetAt(0);

        int x = 0;
        while (sheet.getRow(x) != null && (sheet.getRow(x).getCell(0) != null || sheet.getRow(x).getCell(1) != null)) {
            Part part = new Part();
            try {
                if (sheet.getRow(x).getCell(2) != null) {

                    part.setName(sheet.getRow(x).getCell(2).toString());
                }
                if (sheet.getRow(x).getCell(1) != null) {
                    part.setPartCode(sheet.getRow(x).getCell(1).toString());
                }
                part.setRegistrationDate(new Date());
            } catch (Exception e) {
                e.printStackTrace();
                ++x;
                continue;
            }
            try {
                if (part.getName() != null && part.getPartCode() != null)
                    mongoOperations.save(part);
            } catch (Exception e) {
                continue;
            }
            ++x;
        }
        return ResponseEntity.ok().body(content);
    }
}