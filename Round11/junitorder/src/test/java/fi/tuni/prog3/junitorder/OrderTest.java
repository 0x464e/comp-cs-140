package fi.tuni.prog3.junitorder;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest
{
    @Test
    void testEntry()
    {
        assertThrows(IllegalArgumentException.class, () -> new Order.Entry(null, -5));

        var entry = new Order.Entry(new Order.Item("item", 5), 5);
        assertEquals(5, entry.getCount());

        var item = new Order.Item("item", 5);
        assertEquals(5, new Order.Entry(item, 5).getCount());

        assertEquals("item", entry.getItemName());

        assertEquals(5, entry.getUnitPrice());

        assertEquals("5 units of item", entry.toString());
    }

    @Test
    void testItem()
    {
        assertThrows(IllegalArgumentException.class, () -> new Order.Item(null, -5));

        var item = new Order.Item("item", 5);
        assertEquals("item", item.getName());

        assertEquals(5, item.getPrice());

        assertEquals("Item(item, 5.00)", item.toString());
    }


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
        var item3 = new Order.Item("item3", 5);
        var item4 = new Order.Item("item4", 5);

        order.addItems(item1, 1);
        order.addItems(item2, 1);
        order.addItems(item3, 1);
        order.addItems(item4, 1);
        assertTrue(() -> order.getEntries().get(0).getItemName().equals("item1") &&
                order.getEntries().get(1).getItemName().equals("item2") &&
                order.getEntries().get(2).getItemName().equals("item3") &&
                order.getEntries().get(3).getItemName().equals("item4"));
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

        assertTrue(order.getEntryCount() == 0 && order.isEmpty());

        var item = new Order.Item("item", 5);
        order.addItems(item, 1);

        assertTrue(order.getEntryCount() != 0 && !order.isEmpty());
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