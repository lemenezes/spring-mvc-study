package items;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


public class ItemsControllerUnitTest {

    @Mock
    ItemsRepository itemRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void returnsAListOfItems() throws Exception {
        ItemsController itemsController = new ItemsController(itemRepository);
        Item itemA = new Item();
        Item itemB = new Item();
        List<Item> itemsInRepository = Arrays.asList(itemA, itemB);
        when(itemRepository.all()).thenReturn(itemsInRepository);

        List<Item> itemsReturnedFromController = itemsController.allItems();

        assertEquals(itemsInRepository, itemsReturnedFromController);
    }
}