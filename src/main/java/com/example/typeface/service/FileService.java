package com.example.typeface.service;

import com.example.typeface.entity.File;
import com.example.typeface.entity.User;
import com.example.typeface.repository.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@org.springframework.transaction.annotation.Transactional
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File store(MultipartFile file, User user) throws IOException {
        String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
        File fileEntity=new File();
        fileEntity.setName(fileName);
        fileEntity.setType(file.getContentType());
        fileEntity.setSize(file.getSize());
        fileEntity.setData(file.getBytes());
        fileEntity.setUser(user);
        return fileRepository.save(fileEntity);
    }

    public File getFile(UUID id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found with id " + id));
    }

    public List<File> getAllFiles(User user) {
        return fileRepository.findByUser(user);
    }

    public void deleteFile(UUID id, User user) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
        
        if (!file.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this file");
        }
        
        fileRepository.delete(file);
    }
}
