package vn.iotstar.service;

import java.util.List;

import vn.iotstar.dao.IProductDao;
import vn.iotstar.dao.ProductDao;
import vn.iotstar.entity.Product;

public class ProductServiceImpl implements IProductService {

    public IProductDao productDao = new ProductDao();

    @Override
    public void insert(Product product) {
        productDao.insert(product);
    }

    @Override
    public void update(Product product) {
        productDao.update(product);
    }

    @Override
    public void delete(int id) throws Exception {
        productDao.delete(id);
    }

    @Override
    public Product findById(int id) {
        return productDao.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public List<Product> findAll(int page, int pagesize) {
        return productDao.findAll(page, pagesize);
    }

    @Override
    public List<Product> findLatest(int limit) {
        return productDao.findLatest(limit);
    }

    @Override
    public List<Product> searchByName(String keyword) {
        return productDao.searchByName(keyword);
    }

    @Override
    public int count() {
        return productDao.count();
    }

    @Override
    public int totalPages(int pagesize) {
        int total = count();
        return (int) Math.ceil((double) total / pagesize);
    }
}
