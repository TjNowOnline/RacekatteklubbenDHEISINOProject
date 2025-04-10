package com.example.racekatteklubbendheisino.infrastructure;

import com.example.racekatteklubbendheisino.domain.Member;
import com.example.racekatteklubbendheisino.domain.Pet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcPetRepository implements CRUDRepository<Pet, Long> {
    private final JdbcTemplate jdbcTemplate;

    public JdbcPetRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    // Gemmer et nyt kæledyr i databasen
    public void save(Pet pet) {
        String sql = "INSERT INTO pets (name, age, breed, owner_id, photo_url) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, pet.getName(), pet.getAge(), pet.getBreed(), pet.getOwner().getId(), pet.getPhotoUrl());
    }

    @Override
    // Sletter et kæledyr fra databasen baseret på ID
    public void delete(Pet pet) {
        String sql = "DELETE FROM pets WHERE id = ?";
        jdbcTemplate.update(sql, pet.getId());
    }

    @Override
    // Opdaterer et kæledyrs oplysninger i databasen
    public void update(Pet pet) {
        String sql = "UPDATE pets SET name = ?, age = ?, breed = ?, owner_id = ?, photo_url = ? WHERE id = ?";
        jdbcTemplate.update(sql, pet.getName(), pet.getAge(), pet.getBreed(), pet.getOwner().getId(), pet.getPhotoUrl(), pet.getId());
    }

    @Override
    // Finder et kæledyr baseret på ID
    public Pet findByID(Long id) {
        String sql = "SELECT * FROM pets WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, petRowMapper(), id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    // Henter alle kæledyr fra databasen
    public List<Pet> findAll() {
        String sql = "SELECT * FROM pets";
        return jdbcTemplate.query(sql, petRowMapper());
    }

    // Finder kæledyr baseret på ejerens ID
    public List<Pet> findByOwnerId(Long ownerId) {
        String sql = "SELECT * FROM pets WHERE owner_id = ?";
        return jdbcTemplate.query(sql, petRowMapper(), ownerId);
    }

    // Mapper database-rækker til Pet-objekter
    private RowMapper<Pet> petRowMapper() {
        return (rs, rowNum) -> new Pet(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("breed"),
                new Member(rs.getLong("owner_id"), null, null, null),
                rs.getInt("age"),
                rs.getString("photo_url")
        );
    }

    // Sletter kæledyr, der er ældre end 30 år
    public void deleteOldPets() {
        String sql = "DELETE FROM pets WHERE age > 30";
        jdbcTemplate.update(sql);
    }
}