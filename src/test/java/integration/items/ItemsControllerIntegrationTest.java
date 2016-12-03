package integration.items;

import integration.BaseIntegrationTest;
import items.Item;
import items.ItemsController;
import items.ItemsRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ItemsControllerIntegrationTest extends BaseIntegrationTest {

    @Mock
    private ItemsRepository repository;

    @InjectMocks
    private ItemsController controller;

    @Before
    public void setUp() throws Exception {
        setupController(controller);
    }

    @Test
    public void returnsAListOfItems() throws Exception {
        mockRepositoryWithTwoItems();

        controllerInstance()
                .perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].id", hasItem(1)))
                .andExpect(jsonPath("$[*].id", hasItem(2)))
                .andExpect(jsonPath("$[*].name", hasItem("First item")))
                .andExpect(jsonPath("$[*].name", hasItem("Second item")));
    }

    private void mockRepositoryWithTwoItems() {
        List<Item> itemList = Arrays.asList(
                new Item(1, "First item"),
                new Item(2, "Second item")
        );
        when(repository.all()).thenReturn(itemList);
    }
}
