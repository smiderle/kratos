package br.com.doubletouch.vendasup.presentation.presenter;

import android.util.Log;

import java.util.Collection;
import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.OrderItem;
import br.com.doubletouch.vendasup.data.entity.PriceTable;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.exception.ErrorBundle;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableListUseCase;
import br.com.doubletouch.vendasup.domain.interactor.pricetable.GetPriceTableListUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.view.dialog.OrderItemDrialog;

/**
 * Created by LADAIR on 14/04/2015.
 */
public class OrderItemDialogPresenter implements  Presenter {

    private final GetPriceTableListUseCase getPriceTableListUseCase;
    private OrderItemDrialog orderItemDrialog;



    public OrderItemDialogPresenter( OrderItemDrialog orderItemDrialog, GetPriceTableListUseCase getPriceTableListUseCase) {


        this.getPriceTableListUseCase = getPriceTableListUseCase;

        this.orderItemDrialog = orderItemDrialog;

    }

    public void initialize(){
        this.loadPriceTableList();
    }

    private void loadPriceTableList(){
        this.getPriceTableListUseCase.execute(getPriceTableListCallback);
    }


    private final  GetPriceTableListUseCase.Callback getPriceTableListCallback = new GetPriceTableListUseCase.Callback() {
        @Override
        public void onPriceTableListLoaded(Collection<PriceTable> priceTableCollection) {
            priceTableCollection.size();
            orderItemDrialog.loadPriceTableList((List<PriceTable>) priceTableCollection);
        }

        @Override
        public void onError(ErrorBundle errorBundle) {
            Log.e(VendasUp.APP_TAG, errorBundle.getErrorMessage(), errorBundle.getException());
            showError(errorBundle.getErrorMessage());
        }
    };

    private void showError(String message){
        //orderItemDrialog.showError(message);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }
}
