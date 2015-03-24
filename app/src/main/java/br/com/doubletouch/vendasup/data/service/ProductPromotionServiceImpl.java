package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.database.dao.BranchDAO;
import br.com.doubletouch.vendasup.data.database.dao.ProductPromotionDAO;
import br.com.doubletouch.vendasup.data.entity.BranchOffice;
import br.com.doubletouch.vendasup.data.entity.ProductPromotion;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class ProductPromotionServiceImpl implements ProductPromotionService {

    private ProductPromotionDAO productPromotionDAO;

    @Override
    public void saveOrUpdateSynchronous(List<ProductPromotion> produtosPromocoes) {
        productPromotionDAO = new ProductPromotionDAO();

        productPromotionDAO.insert(produtosPromocoes);
    }
}
