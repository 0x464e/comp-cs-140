package fi.tuni.prog3.junitorder;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest
{
    @Test
    void addItems()
    {
        var order = new Order();

        var item = new Order.Item("item", 5);
        assertTrue(order.addItems(item, 5));
        assertTrue(order.getEntries().stream().anyMatch(x -> x.getItem().equals(item)));
        assertEquals(5, order.getEntries().stream().filter(x -> x.getItem().equals(item)).findFirst().get().getCount());

        assertTrue(order.addItems(item, 5));
        assertEquals(10, order.getEntries().stream().filter(x -> x.getItem().equals(item)).findFirst().get().getCount());

        assertThrows(IllegalArgumentException.class, () -> order.addItems(item, -5));
        assertEquals(10, order.getEntries().stream().filter(x -> x.getItem().equals(item)).findFirst().get().getCount());

        var item2 = new Order.Item("item", 6);
        assertThrows(IllegalStateException.class, () -> order.addItems(item2, 5));
        assertEquals(10, order.getEntries().stream().filter(x -> x.getItem().equals(item)).findFirst().get().getCount());
    }

    @Test
    void testAddItems()
    {
        var order = new Order();

        var item = new Order.Item("item", 5);
        order.addItems(item, 1);

        assertTrue(order.addItems("item", 5));
        assertTrue(order.getEntries().stream().anyMatch(x -> x.getItemName().equals("item")));
        assertEquals(6, order.getEntries().stream().filter(x -> x.getItemName().equals("item")).findFirst().get().getCount());

        assertTrue(order.addItems("item", 5));
        assertEquals(11, order.getEntries().stream().filter(x -> x.getItemName().equals("item")).findFirst().get().getCount());

        assertThrows(IllegalArgumentException.class, () -> order.addItems("item", -5));
        assertEquals(11, order.getEntries().stream().filter(x -> x.getItemName().equals("item")).findFirst().get().getCount());

        assertThrows(NoSuchElementException.class, () -> order.addItems("item2", 5));
        assertEquals(11, order.getEntries().stream().filter(x -> x.getItemName().equals("item")).findFirst().get().getCount());
    }

    @Test
    void getEntries()
    {
        var order = new Order();

        var item1 = new Order.Item("item1", 5);
        var item2 = new Order.Item("item2", 5);

        order.addItems(item1, 1);
        order.addItems(item2, 1);
        assertTrue(() -> order.getEntries().get(0).getItemName().equals("item1") &&
                order.getEntries().get(1).getItemName().equals("item2"));
    }

    @Test
    void getEntryCount()
    {
        var order = new Order();

        var item1 = new Order.Item("item1", 5);
        var item2 = new Order.Item("item2", 5);

        order.addItems(item1, 1);
        order.addItems(item2, 1);

        assertEquals(2, order.getEntryCount());
    }

    @Test
    void getItemCount()
    {
        var order = new Order();

        var item1 = new Order.Item("item1", 5);
        var item2 = new Order.Item("item2", 5);

        order.addItems(item1, 5);
        order.addItems(item2, 9);

        assertEquals(5+9, order.getItemCount());
    }

    @Test
    void getTotalPrice()
    {
        var order = new Order();

        var item1 = new Order.Item("item1", 5);
        var item2 = new Order.Item("item2", 9);

        order.addItems(item1, 2);
        order.addItems(item2, 1);

        assertEquals(2*5 + 1*9, order.getTotalPrice());
    }

    @Test
    void isEmpty()
    {
        var order = new Order();

        assertTrue(order.isEmpty());

        var item = new Order.Item("item", 5);
        order.addItems(item, 1);

        assertFalse(order.isEmpty());
    }

    @Test
    void removeItems()
    {
        var order = new Order();

        var item = new Order.Item("item", 5);
        order.addItems(item, 10);

        assertTrue(order.removeItems("item", 2));
        assertEquals(8, order.getEntries().stream().filter(x -> x.getItem().equals(item)).findFirst().get().getCount());

        assertTrue(order.removeItems("item", 8));
        assertFalse(order.getEntries().stream().anyMatch(x -> x.getItemName().equals("item")));

        order.addItems(item, 10);
        assertThrows(IllegalArgumentException.class, () -> order.removeItems("item", 11));
        assertEquals(10, order.getEntries().stream().filter(x -> x.getItem().equals(item)).findFirst().get().getCount());

        assertThrows(NoSuchElementException.class, () -> order.removeItems("koirankarva", 1));
    }
}