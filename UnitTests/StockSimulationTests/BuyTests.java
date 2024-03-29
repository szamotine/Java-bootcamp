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
    //#region Member Declarations
    List<Account> accounts;
    Account personal;
    Account TFSA;
    List<Stock> stocks;
    static final double INITIAL_DEPOSIT = 1000;
    Stock FB;
    Stock AAPL;
    Stock GOOG;
    Stock TSLA;
    Trade sell;
    Trade buy;

    //#endregion

    @Before
    public void setup(){

        accounts = new ArrayList<>();
        personal = new Personal(INITIAL_DEPOSIT);
        TFSA = new TFSA(INITIAL_DEPOSIT);
        accounts.add(personal);
        accounts.add(TFSA);

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
                a.initializeStock(s,10);
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
        assertEquals(10, TFSA.getInventory(buy.getStock()));
        assertTrue(TFSA.ExecuteTrade(buy));
        assertEquals(15, TFSA.getInventory(buy.getStock()));
    }
    @Test
    public void PurchaseWithInsufficientFunds_NoSharesAdded(){
        buy.setNumberOfShares(101);

        assertEquals(10, TFSA.getInventory(buy.getStock()));
        assertEquals(10,personal.getInventory(buy.getStock()) );

        assertFalse(personal.ExecuteTrade(buy));
        assertFalse(TFSA.ExecuteTrade(buy));

        assertEquals(10, TFSA.getInventory(buy.getStock()));
        assertEquals(10,personal.getInventory(buy.getStock()) );

    }
    @Test
    public void PersonalAccountSharePurchase_FundsDecrease(){
        assertEquals(1000.00,personal.getFunds() );
        assertTrue(personal.ExecuteTrade(buy));
        assertEquals(950.00, personal.getFunds());

    }
    @Test
    public void TFSA_AccountSharePurchase_FundsDecreaseTradeFeeApplied(){

        assertEquals(1000.00,TFSA.getFunds() );
        assertTrue(TFSA.ExecuteTrade(buy));
        assertEquals(949.50, TFSA.getFunds());

    }
}
