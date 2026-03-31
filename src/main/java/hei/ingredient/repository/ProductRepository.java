package hei.ingredient.repository;

import hei.ingredient.entity.Product;
import hei.ingredient.entity.ProductCategory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private final DataSource dataSource;
    public ProductRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public List<Product> getProductList(int page, int size) {
        List<Product> productList = new ArrayList<>();

        String sql = """
        SELECT 
            p.id,
            p.name,
            p.price,
            p.creation_datetime,
            c.id as category_id,
            c.name as category_name
        FROM product p
        LEFT JOIN product_category c ON p.id = c.product_id
        ORDER BY p.id DESC
        LIMIT ? OFFSET ?
    """;

        int offset = (page - 1) * size;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, size);
            stmt.setInt(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {

                    // 🔹 Category
                    ProductCategory category = null;
                    if (rs.getInt("category_id") != 0) {
                        category = new ProductCategory(
                                rs.getInt("id"),
                                rs.getString("category_name")
                        );
                    }

                    // 🔹 Product
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setCreationDate(
                            rs.getDate("creation_datetime") != null
                                    ? rs.getDate("creation_datetime").toLocalDate()
                                    : null
                    );
                    product.setProductCategory(category);

                    productList.add(product);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error to get Product " + e);
        }

        return productList;
    }
    }

