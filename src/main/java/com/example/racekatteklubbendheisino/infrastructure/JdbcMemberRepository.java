package com.example.racekatteklubbendheisino.infrastructure;

import com.example.racekatteklubbendheisino.domain.Member;
import org.springframework.dao.EmptyResultDataAccessException;
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
    public void save(Member member) {
        String sql = "INSERT INTO members (name, email, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, member.getName(), member.getEmail(), member.getPassword());
    }

    @Override
    public void delete(Member member) {
        String sql = "DELETE FROM members WHERE email = ?";
        jdbcTemplate.update(sql, member.getEmail());
    }

    @Override
    public void update(Member member) {
        String sql = "UPDATE members SET name = ?, password = ? WHERE email = ?";
        jdbcTemplate.update(sql, member.getName(), member.getPassword(), member.getEmail());
    }

    @Override
    public Member findByID(String email) {
        String sql = "SELECT * FROM members WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{email}, memberRowMapper());
    }

    @Override
    public List<Member> findAll() {
        String sql = "SELECT * FROM members";
        return jdbcTemplate.query(sql, memberRowMapper());
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> new Member(
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password")
        );
    }
}