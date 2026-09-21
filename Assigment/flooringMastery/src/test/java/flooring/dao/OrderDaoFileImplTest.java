package flooring.dao;

import com.flooring.dao.OrderDao;
import com.flooring.dao.OrderDaoFileImpl;
import com.flooring.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderDaoFileImplTest {

    private OrderDao dao;

    @BeforeEach
    public void setUp() {
        dao = new OrderDaoFileImpl();
    }
    @Test
    public void testGetOrdersByDate() throws Exception {
        LocalDate date = LocalDate.of(2026, 11, 19);

        List<Order> orders = dao.getOrdersByDate(date);

        assertNotNull(orders);
    }

    @Test
    public void testGetNextOrderNumber() throws Exception {
        LocalDate date = LocalDate.of(2026, 11, 19);

        dao.getOrdersByDate(date);

        int nextNumber = dao.getNextOrderNumber();

        assertEquals(3, nextNumber);
    }
    @Test
    public void testAddOrder() throws Exception {
        Order order = new Order();

        order.setOrderNumber(99);
        order.setCustomerName("Test Customer");
        order.setState("TX");
        order.setOrderDate(LocalDate.of(2026, 12, 20));
        order.setTaxRate(new BigDecimal("4.45"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("350.00"));
        order.setLaborCost(new BigDecimal("415.00"));
        order.setTax(new BigDecimal("34.07"));
        order.setTotal(new BigDecimal("799.07"));

        dao.addOrder(order);
    }

    @Test
    public void testRemoveOrder() throws Exception {
        Order order = new Order();

        order.setOrderNumber(99);
        order.setCustomerName("Test Customer");
        order.setState("TX");
        order.setOrderDate(LocalDate.of(2026, 12, 20));
        order.setTaxRate(new BigDecimal("4.45"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        order.setMaterialCost(new BigDecimal("350.00"));
        order.setLaborCost(new BigDecimal("415.00"));
        order.setTax(new BigDecimal("34.07"));
        order.setTotal(new BigDecimal("799.07"));

        dao.addOrder(order);
        dao.removeOrder(order);
    }
    @Test
    public void testEditOrder() throws Exception {
        Order order = new Order();

        order.setOrderNumber(99);
        order.setCustomerName("Updated Customer");
        order.setState("TX");
        order.setOrderDate(LocalDate.of(2026, 12, 20));
        order.setTaxRate(new BigDecimal("4.45"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));

        order.setMaterialCost(new BigDecimal("350.00"));
        order.setLaborCost(new BigDecimal("415.00"));
        order.setTax(new BigDecimal("34.07"));
        order.setTotal(new BigDecimal("799.07"));

        dao.editOrder(order);
    }
    @Test
    public void testExportAllData() throws Exception {
        dao.exportAllData();
    }
}
