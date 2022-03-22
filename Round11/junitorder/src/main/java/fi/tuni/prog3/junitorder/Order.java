package fi.tuni.prog3.junitorder;

import java.util.List;
import java.util.NoSuchElementException;

public class Order
{
    static class Entry
    {
        Entry(Item item, int count) throws IllegalArgumentException { }

        int getCount()
        {
            return 0;
        }

        Item getItem()
        {
            return new Item("", 0.0);
        }

        String getItemName()
        {
            return "";
        }

        double getUnitPrice()
        {
            return 0.0;
        }

        @Override
        public String toString()
        {
            return "";
        }
    }

    static class Item
    {
        Item(String name, double price) { }

        boolean equals(Item other) { return true; }

        String getName()
        {
            return "";
        }

        double getPrice()
        {
            return 0.0;
        }

        @Override
        public String toString()
        {
            return "";
        }
    }

    Order()
    {

    }

    boolean addItems(Item item, int count) throws IllegalArgumentException, NoSuchElementException
    {
        return true;
    }

    boolean addItems(String name, int count)
    {
        return true;
    }

    List<Entry> getEntries()
    {
        return null;
    }

    int getEntryCount()
    {
        return 0;
    }

    int getItemCount()
    {
        return 0;
    }

    double getTotalPrice()
    {
        return 0.0;
    }

    boolean isEmpty()
    {
        return true;
    }

    boolean removeItems(String name, int count) throws IllegalArgumentException, NoSuchElementException
    {
        return true;
    }
}
