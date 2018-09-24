package com.asan.service;

import com.asan.model.ApplicationUser;
import com.asan.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService {
  @Autowired
  private ApplicationUserRepository applicationUserRepository;

  public ApplicationUser save(ApplicationUser applicationUser){
      return applicationUserRepository.save(applicationUser);
  }

   public ApplicationUser findByUsername(String username){
      return applicationUserRepository.findByUsername(username);
    };
}
