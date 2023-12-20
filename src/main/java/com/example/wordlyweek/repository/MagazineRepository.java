package com.example.wordlyweek.repository;

import com.example.wordlyweek.model.Magazine;
import com.example.wordlyweek.model.Writer;
import java.util.ArrayList;
import java.util.List;

public interface MagazineRepository{
    ArrayList<Magazine> getAllMagazines();

    Magazine getMagazineById(int magazineId);

    Magazine addMagazine(Magazine magazine);

    Magazine updateMagazine(int magazineId,Magazine magazine);

    void deleteMagazine(int magazineId);

    List<Writer> getMagazineWriters(int magazineId);

}
