package br.com.doubletouch.vendasup.presentation.presenter;

import android.util.Log;

import java.util.Collection;
import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Installment;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.entity.Product;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.installment.GetInstallmentUseCase;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableUseCase;
import br.com.doubletouch.vendasup.presentation.view.fragment.CustomerDetailsFinancial;
import br.com.doubletouch.vendasup.presentation.view.fragment.ProductDetailsPriceTable;

/**
 * Created by LADAIR on 11/03/2015.
 */
public class ProductDetailsPriceTablePresenter implements Presenter {

    private final GetPriceTableListUseCase getPriceTableListUseCase;

    private Product product;
    private ProductDetailsPriceTable productDetailsPriceTable;

    public ProductDetailsPriceTablePresenter(ProductDetailsPriceTable productDetailsPriceTable, GetPriceTableListUseCase getPriceTableListUseCase) {


        this.getPriceTableListUseCase = getPriceTableListUseCase;
        this.productDetailsPriceTable = productDetailsPriceTable;
    }


    public void initialize(Product product){
        this.product = product;
        this.loadPriceTableList();
    }

    private void loadPriceTableList(){
        this.getPriceTableListUseCase.execute(getPriceTableListCallback);
    }

    private void showError(String message){
        productDetailsPriceTable.showError(message);
    }

    private final  GetPriceTableListUseCase.Callback getPriceTableListCallback = new GetPriceTableListUseCase.Callback() {
        @Override
        public void onPriceTableListLoaded(Collection<PriceTable> priceTableCollection) {
            priceTableCollection.size();
            productDetailsPriceTable.loadPriceTableList((List<PriceTable>) priceTableCollection);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            Log.e(VendasUp.APP_TAG, errorBundle.getErrorMessage(), errorBundle.getException());
            showError(errorBundle.getErrorMessage());
        }
    };

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }
}
