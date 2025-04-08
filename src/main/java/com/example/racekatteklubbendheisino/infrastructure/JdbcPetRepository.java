// src/main/java/com/example/racekatteklubbendheisino/infrastructure/JdbcPetRepository.java
package com.example.racekatteklubbendheisino.infrastructure;

import com.example.racekatteklubbendheisino.domain.Member;
import com.example.racekatteklubbendheisino.domain.Pet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcPetRepository implements CRUDRepository<Pet, Long> {
    private final JdbcTemplate jdbcTemplate;

    public JdbcPetRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Pet pet) {
        String sql = "INSERT INTO pets (name, age, breed, owner_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, pet.getName(), pet.getAge(), pet.getBreed(), pet.getOwner().getId());
    }

    @Override
    public void delete(Pet pet) {
        String sql = "DELETE FROM pets WHERE id = ?";
        jdbcTemplate.update(sql, pet.getId());
    }

    @Override
    public void update(Pet pet) {
        String sql = "UPDATE pets SET name = ?, age = ?, breed = ?, owner_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, pet.getName(), pet.getAge(), pet.getBreed(), pet.getOwner().getId(), pet.getId());
    }

    @Override
    public Pet findByID(Long id) {
        String sql = "SELECT * FROM pets WHERE id = ?";
        RowMapper<Pet> rowMapper = (rs, rowNum) -> new Pet(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("breed"),
                new Member(rs.getLong("owner_id"), null, null, null), // Simplified for example
                rs.getInt("age")
        );
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public List<Pet> findAll() {
        String sql = "SELECT * FROM pets";
        RowMapper<Pet> rowMapper = (rs, rowNum) -> new Pet(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("breed"),
                new Member(rs.getLong("owner_id"), null, null, null), // Simplified for example
                rs.getInt("age")
        );
        return jdbcTemplate.query(sql, rowMapper);
    }
    public List<Pet> findByOwnerId(Long ownerId) {
        String sql = "SELECT * FROM pets WHERE owner_id = ?";
        RowMapper<Pet> rowMapper = (rs, rowNum) -> new Pet(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("breed"),
                new Member(rs.getLong("owner_id"), null, null, null),
                rs.getInt("age")
        );
        return jdbcTemplate.query(sql, rowMapper, ownerId);
    }

}