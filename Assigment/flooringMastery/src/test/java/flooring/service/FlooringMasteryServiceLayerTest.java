package flooring.service;

import com.flooring.dao.OrderDao;
import com.flooring.dao.OrderDaoFileImpl;
import com.flooring.dao.ProductDao;
import com.flooring.dao.ProductDaoFileImpl;
import com.flooring.dao.TaxDao;
import com.flooring.dao.TaxDaoFileImpl;
import com.flooring.model.Order;
import com.flooring.service.FlooringMasteryDataValidationException;
import com.flooring.service.FlooringMasteryServiceLayer;
import com.flooring.service.FlooringMasteryServiceLayerImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FlooringMasteryServiceLayerTest {

    private FlooringMasteryServiceLayer service;

    @BeforeEach
    public void setUp() {
        OrderDao orderDao = new OrderDaoFileImpl();
        TaxDao taxDao = new TaxDaoFileImpl();
        ProductDao productDao = new ProductDaoFileImpl();

        service = new FlooringMasteryServiceLayerImpl(
                orderDao,
                taxDao,
                productDao);
    }

    @Test
    public void testValidOrder() throws Exception {
        Order order = new Order();

        order.setOrderNumber(1);
        order.setCustomerName("Test Customer");
        order.setState("TX");
        order.setOrderDate(LocalDate.now().plusDays(1));
        order.setTaxRate(new BigDecimal("4.45"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));

        BigDecimal materialCost =
                order.getArea().multiply(order.getCostPerSquareFoot());

        BigDecimal laborCost =
                order.getArea().multiply(order.getLaborCostPerSquareFoot());

        BigDecimal tax =
                materialCost.add(laborCost)
                        .multiply(order.getTaxRate()
                                .divide(new BigDecimal("100")));

        BigDecimal total =
                materialCost.add(laborCost).add(tax);

        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);
        order.setTax(tax);
        order.setTotal(total);

        service.addOrder(order);
    }
    @Test
    public void testBlankCustomerName() {
        Order order = new Order();

        order.setOrderNumber(1);
        order.setCustomerName("");
        order.setState("TX");
        order.setOrderDate(LocalDate.now().plusDays(1));
        order.setTaxRate(new BigDecimal("4.45"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));

        assertThrows(
                FlooringMasteryDataValidationException.class,
                () -> service.addOrder(order)
        );
    }
    @Test
    public void testAreaTooSmall() {
        Order order = new Order();

        order.setOrderNumber(1);
        order.setCustomerName("Test Customer");
        order.setState("TX");
        order.setOrderDate(LocalDate.now().plusDays(1));
        order.setTaxRate(new BigDecimal("4.45"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("99"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));

        assertThrows(
                FlooringMasteryDataValidationException.class,
                () -> service.addOrder(order)
        );
    }
    @Test
    public void testInvalidState() {
        Order order = new Order();

        order.setOrderNumber(1);
        order.setCustomerName("Test Customer");
        order.setState("XX");
        order.setOrderDate(LocalDate.now().plusDays(1));
        order.setTaxRate(new BigDecimal("4.45"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));

        assertThrows(
                FlooringMasteryDataValidationException.class,
                () -> service.addOrder(order)
        );
    }
    @Test
    public void testInvalidProduct() {
        Order order = new Order();

        order.setOrderNumber(1);
        order.setCustomerName("Test Customer");
        order.setState("TX");
        order.setOrderDate(LocalDate.now().plusDays(1));
        order.setTaxRate(new BigDecimal("4.45"));
        order.setProductType("Carpet");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));

        assertThrows(
                FlooringMasteryDataValidationException.class,
                () -> service.addOrder(order)
        );
    }
    @Test
    public void testOrderDateNotInFuture() {
        Order order = new Order();

        order.setOrderNumber(1);
        order.setCustomerName("Test Customer");
        order.setState("TX");
        order.setOrderDate(LocalDate.now());
        order.setTaxRate(new BigDecimal("4.45"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("100"));
        order.setCostPerSquareFoot(new BigDecimal("3.50"));
        order.setLaborCostPerSquareFoot(new BigDecimal("4.15"));

        assertThrows(
                FlooringMasteryDataValidationException.class,
                () -> service.addOrder(order)
        );
    }
}
