package com.asan.service;

import com.asan.model.ApplicationUserRole;
import com.asan.repository.ApplicationRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationRoleService {
  @Autowired
 private ApplicationRoleRepository applicationRoleRepository;
   public ApplicationUserRole findByRole(String role){
       return applicationRoleRepository.findByRole(role);
   }
}
