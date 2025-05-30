package com.example.racekatteklubbendheisino.infrastructure;

import com.example.racekatteklubbendheisino.domain.Member;
import com.example.racekatteklubbendheisino.domain.Pet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMemberRepository implements CRUDRepository<Member, String> {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    // Gemmer et nyt medlem i databasen
    public void save(Member member) {
        String sql = "INSERT INTO members (name, email, password, role, last_login) VALUES (?, ?, ?, ?, NOW())";

        // Tjekker og formaterer rollen, hvis den ikke er korrekt
        String role = member.getRole();
        if (role == null || role.isEmpty()) {
            role = "ROLE_MEMBER";
        } else if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role.toUpperCase();
        }

        jdbcTemplate.update(sql, member.getName(), member.getEmail(), member.getPassword(), role);
    }

    @Override
    // Sletter et medlem fra databasen baseret på email
    public void delete(Member member) {
        String sql = "DELETE FROM members WHERE email = ?";
        jdbcTemplate.update(sql, member.getEmail());
    }

    @Override
    // Opdaterer et medlems oplysninger i databasen
    public void update(Member member) {
        String sql = "UPDATE members SET name = ?, password = ?, role = ? WHERE email = ?";
        String role = member.getRole() != null && !member.getRole().startsWith("ROLE_")
                ? "ROLE_" + member.getRole().toUpperCase()
                : member.getRole();
        jdbcTemplate.update(sql, member.getName(), member.getPassword(), role, member.getEmail());
    }

    @Override
    // Finder et medlem baseret på email
    public Member findByID(String email) {
        String sql = "SELECT * FROM members WHERE email = ?";
        try {
            List<Member> members = jdbcTemplate.query(sql, memberRowMapper(), email);
            if (members.isEmpty()) {
                return null;
            }
            return members.get(0);
        } catch (Exception e) {
            System.err.println("Error loading member with email: " + email + ". Exception: " + e.getMessage());
            return null;
        }
    }

    @Override
    // Henter alle medlemmer fra databasen
    public List<Member> findAll() {
        String sql = "SELECT * FROM members";
        return jdbcTemplate.query(sql, memberRowMapper());
    }

    // Finder et medlem baseret på ID
    public Optional<Member> findById(Long memberId) {
        String sql = "SELECT * FROM members WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, memberRowMapper(), memberId));
        } catch (Exception e) {
            System.err.println("Error loading member with ID: " + memberId + ". Exception: " + e.getMessage());
            return Optional.empty();
        }
    }

    // Finder kæledyr, der tilhører et medlem baseret på ID
    public List<Pet> findByMemberId(Long id) {
        String sql = "SELECT * FROM pets WHERE member_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Pet pet = new Pet();
            pet.setId(rs.getLong("id"));
            pet.setName(rs.getString("name"));
            pet.setBreed(rs.getString("breed"));
            pet.setMemberId(rs.getLong("member_id"));
            return pet;
        }, id);
    }

    // Sletter medlemmer, der ikke har logget ind i over et år
    public void deleteOldMembers() {
        String sql = "DELETE FROM members WHERE last_login < DATE_SUB(NOW(), INTERVAL 1 YEAR)";
        jdbcTemplate.update(sql);
    }

    // Opdaterer sidste login-tidspunkt for et medlem
    public void updateLastLogin(String email) {
        String sql = "UPDATE members SET last_login = NOW() WHERE email = ?";
        jdbcTemplate.update(sql, email);
    }

    // Finder medlemmer baseret på navn eller email
    public List<Member> findByNameOrEmail(String query) {
        String sql = "SELECT * FROM members WHERE name LIKE ? OR email LIKE ?";
        String searchTerm = "%" + query + "%";
        return jdbcTemplate.query(sql, memberRowMapper(), searchTerm, searchTerm);
    }

    // Mapper database-rækker til Member-objekter
    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            member.setEmail(rs.getString("email"));
            member.setPassword(rs.getString("password"));
            member.setRole(rs.getString("role"));
            return member;
        };
    }
}