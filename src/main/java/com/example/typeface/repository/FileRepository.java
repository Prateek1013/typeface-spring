package com.example.typeface.repository;

import com.example.typeface.entity.File;
import com.example.typeface.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {
    List<File> findByUser(User user);
}
