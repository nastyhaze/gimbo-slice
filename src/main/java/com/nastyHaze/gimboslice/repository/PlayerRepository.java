package com.nastyHaze.gimboslice.repository;

import com.nastyHaze.gimboslice.entity.data.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    public boolean existsByName(String name);
}
