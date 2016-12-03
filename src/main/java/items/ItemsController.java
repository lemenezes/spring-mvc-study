package items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemsController {

    @Autowired
    private ItemsRepository itemRepository;

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public ResponseEntity allItems() {
        return new ResponseEntity(itemRepository.all(), HttpStatus.OK);
    }
}