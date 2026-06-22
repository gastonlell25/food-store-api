package com.prog3.food_store_api.components;

import com.prog3.food_store_api.models.*;
import com.prog3.food_store_api.models.enums.OrderStatus;
import com.prog3.food_store_api.models.enums.PaymentMethod;
import com.prog3.food_store_api.models.enums.Role;
import com.prog3.food_store_api.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoderComponent passwordEncoder;

    @Value("${seed.admin.email:admin@foodstore.com}")
    private String adminEmail;

    @Value("${seed.admin.password:admin1234}")
    private String adminPassword;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) return;

        User admin = userRepository.save(User.builder()
                .name("Admin").lastName("Store")
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .role(Role.ADMIN).build());

        User client = userRepository.save(User.builder()
                .name("Juan").lastName("García")
                .email("juan@mail.com").phone("1155667788")
                .password(passwordEncoder.encode("cliente1234"))
                .role(Role.CLIENT).build());

        User client2 = userRepository.save(User.builder()
                .name("María").lastName("López")
                .email("maria@mail.com").phone("1144556677")
                .password(passwordEncoder.encode("cliente1234"))
                .role(Role.CLIENT).build());

        Category pizzas = categoryRepository.save(Category.builder()
                .name("Pizzas").description("Pizzas artesanales con masa fresca").build());

        Category hamburguesas = categoryRepository.save(Category.builder()
                .name("Hamburguesas").description("Hamburguesas gourmet con ingredientes frescos").build());

        Category bebidas = categoryRepository.save(Category.builder()
                .name("Bebidas").description("Refrescos, jugos naturales y aguas").build());

        Category postres = categoryRepository.save(Category.builder()
                .name("Postres").description("Dulces y postres caseros").build());

        Category ensaladas = categoryRepository.save(Category.builder()
                .name("Ensaladas").description("Ensaladas frescas y nutritivas").build());

        Product p1 = productRepository.save(Product.builder()
                .name("Pizza Mozzarella").description("Clásica pizza con salsa de tomate y mozzarella derretida")
                .price(new BigDecimal("1200.00")).stock(20).available(true)
                .image("https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=400")
                .category(pizzas).build());

        Product p2 = productRepository.save(Product.builder()
                .name("Pizza Napolitana").description("Pizza con rodajas de tomate fresco, albahaca y ajo")
                .price(new BigDecimal("1350.00")).stock(15).available(true)
                .image("https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400")
                .category(pizzas).build());

        Product p3 = productRepository.save(Product.builder()
                .name("Pizza Cuatro Quesos").description("Mozzarella, provolone, parmesano y roquefort")
                .price(new BigDecimal("1500.00")).stock(10).available(true)
                .image("https://images.unsplash.com/photo-1513104890138-7c749659a591?w=400")
                .category(pizzas).build());

        Product p4 = productRepository.save(Product.builder()
                .name("Hamburguesa Clásica").description("Medallón de carne vacuna, lechuga, tomate y cheddar")
                .price(new BigDecimal("950.00")).stock(25).available(true)
                .image("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400")
                .category(hamburguesas).build());

        Product p5 = productRepository.save(Product.builder()
                .name("Hamburguesa BBQ").description("Doble medallón con salsa BBQ, panceta crocante y cebolla caramelizada")
                .price(new BigDecimal("1250.00")).stock(18).available(true)
                .image("https://images.unsplash.com/photo-1553979459-d2229ba7433b?w=400")
                .category(hamburguesas).build());

        Product p6 = productRepository.save(Product.builder()
                .name("Hamburguesa Veggie").description("Medallón de legumbres, aguacate, tomate y rúcula")
                .price(new BigDecimal("1100.00")).stock(12).available(true)
                .image("https://images.unsplash.com/photo-1520072959219-c595dc870360?w=400")
                .category(hamburguesas).build());

        Product p7 = productRepository.save(Product.builder()
                .name("Coca-Cola 500ml").description("Refresco de cola clásico bien frío")
                .price(new BigDecimal("400.00")).stock(50).available(true)
                .image("https://images.unsplash.com/photo-1581636625402-29b2a704ef13?w=400")
                .category(bebidas).build());

        Product p8 = productRepository.save(Product.builder()
                .name("Jugo de Naranja Natural").description("Jugo exprimido al momento, 400ml")
                .price(new BigDecimal("550.00")).stock(30).available(true)
                .image("https://images.unsplash.com/photo-1534353436294-0dbd4bdac845?w=400")
                .category(bebidas).build());

        Product p9 = productRepository.save(Product.builder()
                .name("Agua Mineral 500ml").description("Agua mineral sin gas")
                .price(new BigDecimal("250.00")).stock(60).available(true)
                .image("https://images.unsplash.com/photo-1564419320461-6870880221ad?w=400")
                .category(bebidas).build());

        Product p10 = productRepository.save(Product.builder()
                .name("Brownie con Helado").description("Brownie tibio de chocolate con una bola de helado de vainilla")
                .price(new BigDecimal("750.00")).stock(15).available(true)
                .image("https://images.unsplash.com/photo-1564355808539-22fda35bed7e?w=400")
                .category(postres).build());

        Product p11 = productRepository.save(Product.builder()
                .name("Cheesecake de Frutilla").description("Cheesecake cremoso con coulis de frutillas frescas")
                .price(new BigDecimal("800.00")).stock(10).available(true)
                .image("https://images.unsplash.com/photo-1578775887804-699de7086ff9?w=400")
                .category(postres).build());

        Product p12 = productRepository.save(Product.builder()
                .name("Ensalada César").description("Lechuga romana, pollo grillado, crutones, parmesano y aderezo César")
                .price(new BigDecimal("900.00")).stock(0).available(false)
                .image("https://images.unsplash.com/photo-1512852939750-1305098529bf?w=400")
                .category(ensaladas).build());

        Product p13 = productRepository.save(Product.builder()
                .name("Ensalada Caprese").description("Tomate, mozzarella fresca, albahaca y aceite de oliva")
                .price(new BigDecimal("850.00")).stock(8).available(true)
                .image("https://images.unsplash.com/photo-1608897013039-887f21d8c804?w=400")
                .category(ensaladas).build());

        Order order1 = Order.builder()
                .date(LocalDate.now().minusDays(3))
                .status(OrderStatus.DELIVERED)
                .paymentMethod(PaymentMethod.CASH)
                .user(client).build();
        order1.addOrderDetail(OrderDetail.builder().product(p1).quantity(2).build());
        order1.addOrderDetail(OrderDetail.builder().product(p7).quantity(2).build());
        orderRepository.save(order1);

        Order order2 = Order.builder()
                .date(LocalDate.now().minusDays(1))
                .status(OrderStatus.IN_PREPARATION)
                .paymentMethod(PaymentMethod.DEBIT_CARD)
                .user(client).build();
        order2.addOrderDetail(OrderDetail.builder().product(p4).quantity(1).build());
        order2.addOrderDetail(OrderDetail.builder().product(p10).quantity(1).build());
        order2.addOrderDetail(OrderDetail.builder().product(p9).quantity(2).build());
        orderRepository.save(order2);

        Order order3 = Order.builder()
                .date(LocalDate.now())
                .status(OrderStatus.PENDING)
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .user(client2).build();
        order3.addOrderDetail(OrderDetail.builder().product(p3).quantity(1).build());
        order3.addOrderDetail(OrderDetail.builder().product(p11).quantity(2).build());
        orderRepository.save(order3);

        Order order4 = Order.builder()
                .date(LocalDate.now().minusDays(5))
                .status(OrderStatus.CANCELLED)
                .paymentMethod(PaymentMethod.BANK_TRANSFER)
                .user(client2).build();
        order4.addOrderDetail(OrderDetail.builder().product(p5).quantity(2).build());
        order4.addOrderDetail(OrderDetail.builder().product(p8).quantity(2).build());
        orderRepository.save(order4);
    }
}
