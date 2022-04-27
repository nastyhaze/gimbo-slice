package com.nastyHaze.gimboslice.service.data.item;

import com.nastyHaze.gimboslice.entity.data.Item;
import com.nastyHaze.gimboslice.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ItemSaveService {

    @Autowired
    private ItemRepository itemRepository;

    public void save(Item item) {
        itemRepository.save(item);
    }
}
