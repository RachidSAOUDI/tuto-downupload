package ma.saoudi.tutodownupload.service;

import ma.saoudi.tutodownupload.exception.FileNotFoundException;
import ma.saoudi.tutodownupload.exception.FileStorageException;
import ma.saoudi.tutodownupload.properties.FileUploadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements IFileSystemStorage {
    private final Path dirLocation;

    @Autowired
    public FileSystemStorageService(FileUploadProperties fileUploadProperties) {
        this.dirLocation = Paths.get(fileUploadProperties.getLocation())
                .toAbsolutePath().normalize();
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(this.dirLocation);
        } catch (Exception e) {
            throw new FileStorageException("could not create upload dir!");
        }
    }

    @Override
    public String saveFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            Path dfile = this.dirLocation.resolve(fileName);
            Files.copy(file.getInputStream(),dfile, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new FileStorageException("Could not upload file");
        }
        return null;
    }

    @Override
    public Resource loadFile(String fileName) {
        try {
            Path file = this.dirLocation.resolve(fileName).normalize();
            Resource resource=new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()){
                return resource;
            } else {
                throw new FileNotFoundException("Could not find file");
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not download file");
        }
    }
}
