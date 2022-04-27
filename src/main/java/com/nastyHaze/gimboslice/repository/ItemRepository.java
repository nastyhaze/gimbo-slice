package com.nastyHaze.gimboslice.repository;

import com.nastyHaze.gimboslice.entity.data.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    public List<Item> findAllByTagsContaining(String tag);

    public boolean existsByName(String name);
}
