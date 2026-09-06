package vn.iotstar.service;

import java.util.List;

import vn.iotstar.dao.CategoryDao;
import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.entity.Category;

public class CategoryServiceImpl implements ICategoryService {

    public ICategoryDao cateDao = new CategoryDao();

    @Override
    public List<Category> findAll() {
        return cateDao.findAll();
    }

    @Override
    public Category findById(int id) {
        return cateDao.findById(id);
    }

    @Override
    public List<Category> searchByName(String keyword) {
        return cateDao.searchByName(keyword);
    }

    @Override
    public void insert(Category category) throws Exception {
        if (category.getCategoryname() == null || category.getCategoryname().trim().isEmpty()) {
            throw new Exception("Ten danh muc khong duoc de trong");
        }
        category.setCategoryname(category.getCategoryname().trim());

        Category cate = this.findByCategoryname(category.getCategoryname());
        if (cate != null) {
            throw new Exception("Ten danh muc da ton tai");
        }
        cateDao.insert(category);
    }

    @Override
    public void update(Category category) throws Exception {
        if (category.getCategoryname() == null || category.getCategoryname().trim().isEmpty()) {
            throw new Exception("Ten danh muc khong duoc de trong");
        }
        category.setCategoryname(category.getCategoryname().trim());

        Category existing = this.findById(category.getCategoryid());
        if (existing == null) {
            throw new Exception("Danh muc khong ton tai");
        }

        Category sameName = this.findByCategoryname(category.getCategoryname());
        if (sameName != null && sameName.getCategoryid() != category.getCategoryid()) {
            throw new Exception("Ten danh muc da ton tai");
        }

        cateDao.update(category);
    }

    @Override
    public void delete(int id) throws Exception {
        cateDao.delete(id);
    }

    @Override
    public int count() {
        return cateDao.count();
    }

    @Override
    public List<Category> findAll(int page, int pagesize) {
        return cateDao.findAll(page, pagesize);
    }

    @Override
    public Category findByCategoryname(String name) {
        try {
            return cateDao.findByCategoryname(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
