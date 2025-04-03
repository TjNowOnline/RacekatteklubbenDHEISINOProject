package com.example.racekatteklubbendheisino.infrastructure;

import com.example.racekatteklubbendheisino.domain.Show;

import java.util.List;

public interface ShowRepository {
    List<Show> getAll();
    Show findById(Long id);
    void saveShow(Show show);
    void deleteShow(Long id);
    void updateShow(Show show);
}
