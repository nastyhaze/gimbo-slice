package com.nastyHaze.gimboslice.service.data.item;

import com.nastyHaze.gimboslice.constant.ItemTag;
import com.nastyHaze.gimboslice.entity.data.Item;
import com.nastyHaze.gimboslice.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemListingService {

    @Autowired
    private ItemRepository itemRepository;


    public List<Item> retrieveItemsByTag(ItemTag tag) {
        return itemRepository.findAllByTagsContaining(tag.name());
    }
}
