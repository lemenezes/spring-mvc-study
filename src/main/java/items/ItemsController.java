package items;

import java.util.List;


public class ItemsController {
    private final ItemsRepository itemRepository;

    public ItemsController(ItemsRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> allItems() {
        return itemRepository.all();
    }
}
