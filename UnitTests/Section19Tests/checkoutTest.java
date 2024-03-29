package Section19Tests;

import Section19.Model.Cart;
import Section19.Model.Item;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class checkoutTest {
    double [] prices = {1.1, 2.2, 3.3};
    Cart c;
    Item i1;
    Item i2;

    @Before
    public void setup(){
        c = new Cart();

        i1 = new Item("Pepsi", 1.99);
        i2 = new Item("Crush", 1.99);

       c.addItem(i1);
       c.addItem(i2);
    }

    @Test
    public void itemAddedTest(){
        assertTrue(c.contains(i1));
        assertTrue(c.contains(i2));
    }

    @Test
    public void duplicateItemAddedTest(){
       assertFalse(c.add(i1));

    }

    @Test
    public void itemRemovedTest(){

        c.removeItem(i1.getName());
        assertFalse(c.contains(i1));

    }

    @Test (expected = IllegalStateException.class)
    public void removeItemFromEmptyCartTest(){
        c.clear();
        c.remove(i1);

    }

    @Test
    public void subtotalIsValidTest(){
        double subtotal = i1.getPrice() + i2.getPrice();
        assertEquals(subtotal, c.getSubtotal(),0);
    }

    @Test
    public void taxisValidTest(){

        assertEquals(0.86, c.getTax(6.6), 0);
    }

    @Test
    public void totalIsValidTest(){
        double subtotal = i1.getPrice() + i2.getPrice();
        double taxAmount = c.getTax(subtotal);
        double total = subtotal + taxAmount;
        assertEquals(total, c.getTotal(c.getSubtotal(), c.getTax(c.getSubtotal())),0);
    }
    @Test (expected = IllegalStateException.class)
    public void checkoutEmptyCartTest(){
        c.clear();
        c.checkOut();

    }
}
