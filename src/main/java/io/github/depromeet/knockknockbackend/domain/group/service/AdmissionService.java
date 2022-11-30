package io.github.depromeet.knockknockbackend.domain.group.service;


import io.github.depromeet.knockknockbackend.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final UserRepository userRepository;

}
