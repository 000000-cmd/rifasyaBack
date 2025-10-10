package org.rifasya.main.repositories;

import org.rifasya.main.dto.response.LoginResponseDTO;
import org.rifasya.main.entities.ThirdParty;
import org.rifasya.main.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Override
    <S extends User> S save(S entity);

    @Override
    Optional<User> findById(UUID uuid);

    @Override
    boolean existsById(UUID uuid);

    @Override
    long count();

    @Override
    void deleteById(UUID uuid);

    @Override
    void delete(User entity);

    @Override
    void deleteAllById(Iterable<? extends UUID> uuids);

    @Override
    void deleteAll(Iterable<? extends User> entities);

    @Override
    void deleteAll();

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String mail);

    default LoginResponseDTO toLoginResponseDTO(User user, ThirdParty thirdParty) {
        if (user == null) return null;

        LoginResponseDTO dto = new LoginResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());

        if (thirdParty != null) {
            dto.setName(thirdParty.getFirstName() + " " + thirdParty.getFirstLastName());
        } else {
            dto.setName(user.getUsername());
        }

        if (user.getRoles() != null) {
            dto.setRoles(user.getRoles().stream()
                    .map(userRole -> userRole.getRole().getCode())
                    .collect(Collectors.toList()));
        }

        return dto;
    }

}
