package com.project.Ecommerce.DaoImpl;

import com.project.Ecommerce.Dao.UploadDao;
import com.project.Ecommerce.Entities.Customer;
import com.project.Ecommerce.Entities.ProductVariation;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class UploadDaoImpl implements UploadDao
{
        String currentPath = System.getProperty("user.dir");

        public Optional<String> getExtensionByStringHandling(String filename) {
            return Optional.ofNullable(filename)
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(filename.lastIndexOf(".") + 1));
        }

        @Override
        public ResponseEntity<Object> uploadSingleImageForProductVariation(MultipartFile file, ProductVariation productVariation) throws IOException {

            File convertFile = new File(currentPath +"/src/main/resources/productVariation/images" + file.getOriginalFilename());
            convertFile.createNewFile();
            String fileBasePath = currentPath +"/src/main/resources/productVariation/";
            Path path = Paths.get(fileBasePath + convertFile.getName());
            FileOutputStream fileOutputStream = new FileOutputStream(convertFile);
            System.out.println(convertFile.getAbsolutePath());
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
            Optional<String> ext = getExtensionByStringHandling(convertFile.getName());
            int count = 0;
            File dir = new File(fileBasePath);

            if (ext.isPresent()) {
                if (dir.isDirectory()) {
                    File[] files = dir.listFiles();
                    for (File file1 : files) {
                        String value = productVariation.getId().toString();
                        if (file1.getName().startsWith(value)) {
                            count++;
                            System.out.println(count);
                        }
                    }
                    String productVariationIdInString = productVariation.getId().toString();
                    productVariationIdInString = productVariationIdInString +"_" +count;
                    Files.move(path, path.resolveSibling(productVariationIdInString + "." + ext.get()));

                }
            } else {
                throw new RuntimeException();
            }
            return new ResponseEntity<>("Product Variation image successfully added", HttpStatus.OK);
        }

    @Override
          public ResponseEntity<Object> uploadSingleImage(MultipartFile file, Customer customer) throws IOException {
            File convertFile = new File(currentPath +"/src/main/resources/static/users/images" + file.getOriginalFilename());
            convertFile.createNewFile();
            String fileBasePath = currentPath +"/src/main/resources/static/users/";
            Path path = Paths.get(fileBasePath + convertFile.getName());
            FileOutputStream fileOutputStream = new FileOutputStream(convertFile);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
            Optional<String> ext = getExtensionByStringHandling(convertFile.getName());
            File dir = new File(fileBasePath);
            if (ext.isPresent())
            {
                if (dir.isDirectory())
                {
                    File[] files = dir.listFiles();
                    for (File file1 : files) {
                        String value = customer.getId().toString();
                        if (file1.getName().startsWith(value)) {
                            Files.delete(Paths.get(file1.getPath()));
                        }
                    }
                    Files.move(path, path.resolveSibling(customer.getId() + "." + ext.get()));
                }


            } else {
                throw new RuntimeException();
            }
            return new ResponseEntity<>("User profile image successfully  added", HttpStatus.OK);
        }


    @Override
    public String downloadImage(String filename, HttpServletRequest request) throws IOException {
        String fileBasePath = currentPath +"/src/main/resources/static/users/";
        File dir = new File(fileBasePath);
        Resource resource = null;
        String contentType = null;
        if (dir.isDirectory()) {
            File arr[] = dir.listFiles();
            for (File file : arr) {
                if (file.getName().startsWith(filename)) {
                    Path path = Paths.get(fileBasePath + file.getName());
                    try
                    {
                        resource = new UrlResource(path.toUri());
                    } catch (MalformedURLException e)
                    {
                        e.printStackTrace();
                    }
                    contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
                    System.out.println(contentType);
                }
            }
        }
        System.out.println(contentType);
        return resource.getFilename();
                /*ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource)*/
    }

    @Override
        public ResponseEntity downloadImageForVariation(String fileName, HttpServletRequest request) throws IOException {
            String fileBasePath = currentPath +"/src/main/resources/productVariation/";
            File dir = new File(fileBasePath);
            Resource resource = null;
            String contentType = null;
            if (dir.isDirectory()) {
                File arr[] = dir.listFiles();
                for (File file : arr) {
                    if (file.getName().startsWith(fileName)) {
                        Path path = Paths.get(fileBasePath + file.getName());
                        try
                        {
                            resource = new UrlResource(path.toUri());
                        } catch (MalformedURLException e)
                        {
                            e.printStackTrace();
                        }
                        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
                        System.out.println(contentType);
                    }
                }
            }
            System.out.println(contentType);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }

}