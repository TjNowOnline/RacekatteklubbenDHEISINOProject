package com.example.racekatteklubbendheisino.infrastructure;

import com.example.racekatteklubbendheisino.domain.Show;

import java.util.List;

public class JdbcShowRepository implements ShowRepository {
    @Override
    public List<Show> getAll() {
        return List.of();
    }

    @Override
    public Show findById(Long id) {
        return null;
    }

    @Override
    public void saveShow(Show show) {

    }

    @Override
    public void deleteShow(Long id) {

    }

    @Override
    public void updateShow(Show show) {

    }
}
