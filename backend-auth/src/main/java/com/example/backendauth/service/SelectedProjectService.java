package com.example.backendauth.service;

import com.example.backendauth.model.Project;
import com.example.backendauth.model.User;
import com.example.backendauth.model.SelectedProject;
import com.example.backendauth.repository.SelectedProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SelectedProjectService {
    @Autowired
    private SelectedProjectRepository selectedProjectRepository;

    public void selectProject(User user, Project project) {
        if (!selectedProjectRepository.existsByUserAndProject(user, project)) {
            SelectedProject sel = new SelectedProject();
            sel.setUser(user);
            sel.setProject(project);
            selectedProjectRepository.save(sel);
        }
    }

    public List<SelectedProject> getByUser(User user) {
        return selectedProjectRepository.findByUser(user);
    }
}
