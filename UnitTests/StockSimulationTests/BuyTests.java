package StockSimulationTests;


import StockSimulation.Model.Account.Account;
import StockSimulation.Model.Account.Personal;
import StockSimulation.Model.Account.TFSA;
import StockSimulation.Model.Stock;
import StockSimulation.Model.Trade;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

public class BuyTests {
    List<Account> accounts;
    Account personal;
    Account tfsa;
    List<Stock> stocks;
    static final double INITIAL_DEPOSIT = 1000;
    Stock FB;
    Stock AAPL;
    Stock GOOG;
    Stock TSLA;

    Trade sell;

    Trade buy;

    @Before
    public void setup(){

        accounts = new ArrayList<>();
        personal = new Personal(INITIAL_DEPOSIT);
        tfsa = new TFSA(INITIAL_DEPOSIT);
        accounts.add(personal);
        accounts.add(tfsa);

        FB = new Stock(Stock.StockName.FB);
        AAPL = new Stock(Stock.StockName.AAPL);
        GOOG = new Stock(Stock.StockName.GOOG);
        TSLA = new Stock(Stock.StockName.TSLA);

        stocks = new ArrayList<>();
        stocks.add(FB);
        stocks.add(AAPL);
        stocks.add(GOOG);
        stocks.add(TSLA);

        //Initialize each stock price to 10
        for(Stock s : stocks){
            s.setPrice(10);
            //Set each account inventory to 10 of each stock
            for(Account a : accounts){
                a.addStock(s,10);
            }
        }

        sell = new Trade(FB, "sell", 5);
        buy = new Trade (AAPL, "buy", 5);

    }

    @Test
    public void Test_PersonalAccountPurchaseShare_SharesIncrease(){

        assertEquals(10,personal.getInventory(buy.getStock()) );
        assertTrue(personal.ExecuteTrade(buy));
        assertEquals(15, personal.getInventory(buy.getStock()));
    }

    @Test
    public void Test_TFSAAccountPurchaseShare_SharesIncrease(){
        assertEquals(10,tfsa.getInventory(buy.getStock()) );
        assertTrue(tfsa.ExecuteTrade(buy));
        assertEquals(15, tfsa.getInventory(buy.getStock()));
    }
    @Test
    public void PurchaseWithInsufficientFunds_NoSharesAdded(){
        buy.setNumberOfShares(101);

        assertEquals(10,tfsa.getInventory(buy.getStock()) );
        assertEquals(10,personal.getInventory(buy.getStock()) );

        assertFalse(personal.ExecuteTrade(buy));
        assertFalse(tfsa.ExecuteTrade(buy));

        assertEquals(10,tfsa.getInventory(buy.getStock()) );
        assertEquals(10,personal.getInventory(buy.getStock()) );

    }
    @Test
    public void PersonalAccountSharePurchase_FundsDecrease(){
        assertEquals(1000.00,personal.getFunds() );
        assertTrue(personal.ExecuteTrade(buy));
        assertEquals(950.00, personal.getFunds());

    }
    @Test
    public void TFSAAccountSharePurchase_FundsDecreaseTradeFeeApplied(){

    }
}