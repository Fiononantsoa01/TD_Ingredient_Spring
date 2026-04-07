package hei.ingredient.repository;

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
public class ProductCategoryRepository {
    private final DataSource dataSource;

    public ProductCategoryRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public List<ProductCategory> findAllCategory() {
        List<ProductCategory> categories= new ArrayList<>();
        String sql= "select id, name from product_category";
        try(Connection conn= dataSource.getConnection()) {
            PreparedStatement ps= conn.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while(rs.next()) {
                ProductCategory product_category= new ProductCategory();
                product_category.setId(rs.getInt("id"));
                product_category.setName(rs.getString("name"));
                categories.add(product_category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }
    public  ProductCategory findCategoryByProductId(Integer ProductId) {
        String sql= "select id, name from product_category where id = ?";
        try(Connection conn=dataSource.getConnection()) {
            PreparedStatement ps= conn.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            if(rs.next()) {
                ProductCategory product_category= new ProductCategory();
                product_category.setId(rs.getInt("id"));
                product_category.setName(rs.getString("name"));
           }
            throw null;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
