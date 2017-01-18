package integration.items;

import integration.BaseIntegrationTest;
import items.Item;
import model.item;
import items.ItemsController;
import items.ItemsRepository;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
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

    private void mockRepositoryWithTwoItems_1() {
        List<Item> itemList = Arrays.asList(
                new Item(1, "First item"),
                new Item(2, "Second item")
        );
        when(repository.all()).thenReturn(itemList);
    }

    @Test
    public void returnsAListOfItemsWithMock() throws Exception {
        this.mockRepositoryWithTwoItems();
        this.controllerInstance()
                .perform(MockMvcRequestBuilders.get("/items", new Object[0])).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", IsCollectionContaining.hasItem(Integer.valueOf(1))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id", IsCollectionContaining.hasItem(Integer.valueOf(2))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", IsCollectionContaining.hasItem("First item")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name", IsCollectionContaining.hasItem("Second item")));
    }

    @Test
    public void returnListFirstItemPrice() throws Exception {
        this.mockRepositoryWithTwoItems();
        this.controllerInstance().perform(MockMvcRequestBuilders.get("/items", new Object[0])).andExpect(MockMvcResultMatchers.jsonPath("$[0].price", CoreMatchers.is(Double.valueOf(100.0D))));
    }

    @Test
    public void returnListSecondItemPrice() throws Exception {
        this.mockRepositoryWithTwoItems();
        this.controllerInstance().perform(MockMvcRequestBuilders.get("/items", new Object[0])).andExpect(MockMvcResultMatchers.jsonPath("$[1].price", CoreMatchers.is(Double.valueOf(250.0D))));
    }

    private void mockRepositoryWithTwoItems() {
        List itemList = Arrays.asList(new item[]{new item(1, "First item", 100.0D), new item(2, "Second item", 250.0D)});
        Mockito.when(this.repository.all()).thenReturn(itemList);
    }

    @Test
    public void CalcDiscountPriceFirstItem() throws Exception {
        this.mockRepositoryWithTwoItems();
        this.controllerInstance().perform(MockMvcRequestBuilders.get("/items", new Object[0])).andExpect(MockMvcResultMatchers.jsonPath("$[0].priceWithDiscount", CoreMatchers.is(Double.valueOf(85.0D))));
    }


    @Test
    public void CalcDiscountPriceSecondItem() throws Exception {
        mockRepositoryWithTwoItems();
        controllerInstance()
                .perform(get("/items"))
                .andExpect(jsonPath("$[1].priceWithDiscount", is(212.50)));

    }



}
